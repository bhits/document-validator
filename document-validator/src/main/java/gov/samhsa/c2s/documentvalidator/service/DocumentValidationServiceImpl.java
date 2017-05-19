package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.common.validation.XmlValidation;
import gov.samhsa.c2s.common.validation.XmlValidationResult;
import gov.samhsa.c2s.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.c2s.documentvalidator.infrastructure.CcdaValidator;
import gov.samhsa.c2s.documentvalidator.infrastructure.ValidationCriteria;
import gov.samhsa.c2s.documentvalidator.infrastructure.exception.C32ValidatorRunningException;
import gov.samhsa.c2s.documentvalidator.service.dto.*;
import gov.samhsa.c2s.documentvalidator.service.exception.UnsupportedDocumentTypeValidationException;
import gov.samhsa.c2s.documentvalidator.service.schema.CCDAVersion;
import gov.samhsa.c2s.documentvalidator.service.schema.DocumentType;
import gov.samhsa.c2s.documentvalidator.service.schema.ValidationDiagnosticType;
import gov.samhsa.c2s.documentvalidator.service.util.DocumentHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentValidationServiceImpl implements DocumentValidationService {

    private final XmlValidation c32SchemaValidator;

    private final DocumentTypeResolver documentTypeResolver;

    private final CcdaValidator ccdaValidator;

    @Autowired
    public DocumentValidationServiceImpl(XmlValidation c32SchemaValidator, DocumentTypeResolver documentTypeResolver, CcdaValidator ccdaValidator) {
        this.c32SchemaValidator = c32SchemaValidator;
        this.documentTypeResolver = documentTypeResolver;
        this.ccdaValidator = ccdaValidator;
    }

    @Override
    public ValidationResponseDto validateDocument(ValidationRequestDto requestDto) {
        //Determine document type
        final DocumentType documentType = documentTypeResolver.resolve(DocumentHandler
                .convertByteDocumentToString(requestDto.getDocument(), requestDto.getDocumentEncoding()));
        log.info("Identified document as " + documentType);
        ValidationResponseDto responseDto;

        // Support clinical document type: C32, C-CDA R1+ and C-CDA R2+
        if (DocumentType.HITSP_C32.equals(documentType)) {
            responseDto = runC32Validator(requestDto, documentType);
        } else if (documentType.isCCDA(CCDAVersion.R1) || documentType.isCCDA(CCDAVersion.R2)) {
            responseDto = runCCDAValidator(requestDto, documentType);
        } else {
            throw new UnsupportedDocumentTypeValidationException("Unsupported document type to validate");
        }
        return responseDto;
    }

    @Override
    public ValidationResponseDto validateDocumentFile(MultipartFile documentFile) {
        ValidationRequestDto requestDto = new ValidationRequestDto();

        try {
            requestDto.setDocument(documentFile.getBytes());
        } catch (IOException e) {
            log.error("There is no file or invalid file", e);
            throw new UnsupportedDocumentTypeValidationException("There is no file or invalid file", e);
        }
        return validateDocument(requestDto);
    }

    private ValidationResponseDto runC32Validator(ValidationRequestDto requestDto, DocumentType documentType) {
        ValidationResponseDto validationResponseDto = new ValidationResponseDto();
        try {
            XmlValidationResult xmlValidationResult = c32SchemaValidator.validateWithAllErrors(DocumentHandler
                    .convertByteDocumentToString(requestDto.getDocument(), requestDto.getDocumentEncoding()));
            //Map xmlValidationResult to DocumentValidationResultDetail
            List<DocumentValidationResultDetail> validatorResults = xmlValidationResult.getExceptions().stream()
                    .map(e -> DocumentValidationResultDetail.builder()
                            .description(e.getMessage())
                            .isSchemaError(true)
                            .documentLineNumber(Integer.toString(e.getLineNumber()))
                            .build())
                    .collect(Collectors.toList());

            validationResponseDto.setValidationResultDetails(validatorResults);
            validationResponseDto.setDocumentType(documentType);
            validationResponseDto.setDocumentValid(xmlValidationResult.isValid());
            validationResponseDto.setValidationResultSummary(DocumentValidationResultSummary.builder()
                    .validationCriteria(ValidationCriteria.C32_SCHEMA_ONLY)
                    .build());
        } catch (XmlDocumentReadFailureException e) {
            log.error(e.getMessage(), e);
            throw new C32ValidatorRunningException(e);
        }
        return validationResponseDto;
    }

    private ValidationResponseDto runCCDAValidator(ValidationRequestDto requestDto, DocumentType documentType) {
        List<DocumentValidationResultDetail> validatorResults = ccdaValidator.validateCCDA(requestDto.getDocument(), requestDto.getDocumentEncoding());

        return ValidationResponseDto.builder()
                .documentType(documentType)
                .validationResultSummary(prepareValidationSummary(validatorResults))
                .validationResultDetails(validatorResults)
                .build();
    }

    private DocumentValidationResultSummary prepareValidationSummary(List<DocumentValidationResultDetail> validatorResults) {
        Map<String, AtomicInteger> validationSummaryMap = processValidationResultMetaData(validatorResults).getValidationSummaryMap();

        ValidationDiagnosticStatistics ccdaError = ValidationDiagnosticStatistics.builder()
                .diagnosticType(ValidationDiagnosticType.CCDA_ERROR.getTypeName())
                .count(validationSummaryMap.get(ValidationDiagnosticType.CCDA_ERROR.getTypeName()).intValue())
                .build();

        ValidationDiagnosticStatistics ccdaWarning = ValidationDiagnosticStatistics.builder()
                .diagnosticType(ValidationDiagnosticType.CCDA_WARN.getTypeName())
                .count(validationSummaryMap.get(ValidationDiagnosticType.CCDA_WARN.getTypeName()).intValue())
                .build();

        ValidationDiagnosticStatistics ccdaInfo = ValidationDiagnosticStatistics.builder()
                .diagnosticType(ValidationDiagnosticType.CCDA_INFO.getTypeName())
                .count(validationSummaryMap.get(ValidationDiagnosticType.CCDA_INFO.getTypeName()).intValue())
                .build();

        return DocumentValidationResultSummary.builder()
                .validationCriteria(ValidationCriteria.C_CDA_IG_ONLY)
                .diagnosticStatistics(Arrays.asList(ccdaError, ccdaWarning, ccdaInfo))
                .build();
    }

    private ValidationResultMetaData processValidationResultMetaData(List<DocumentValidationResultDetail> validatorResults) {
        ValidationResultMetaData resultMetaData = new ValidationResultMetaData();
        validatorResults
                .forEach(resultDetail -> resultMetaData.addCount(resultDetail.getDiagnosticType()));

        return resultMetaData;
    }
}