package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.common.validation.XmlValidation;
import gov.samhsa.c2s.common.validation.XmlValidationResult;
import gov.samhsa.c2s.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.c2s.documentvalidator.infrastructure.CcdaValidator;
import gov.samhsa.c2s.documentvalidator.infrastructure.ValidationCriteria;
import gov.samhsa.c2s.documentvalidator.infrastructure.exception.C32ValidatorRunningException;
import gov.samhsa.c2s.documentvalidator.service.dto.DocumentValidationResultDetail;
import gov.samhsa.c2s.documentvalidator.service.dto.DocumentValidationResultSummary;
import gov.samhsa.c2s.documentvalidator.service.dto.ValidationRequestDto;
import gov.samhsa.c2s.documentvalidator.service.dto.ValidationResponseDto;
import gov.samhsa.c2s.documentvalidator.service.exception.UnsupportedDocumentTypeValidationException;
import gov.samhsa.c2s.documentvalidator.service.schema.CCDAVersion;
import gov.samhsa.c2s.documentvalidator.service.schema.DocumentType;
import gov.samhsa.c2s.documentvalidator.service.util.DocumentHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DocumentValidationServiceImpl implements DocumentValidationService {

    @Autowired
    private XmlValidation c32SchemaValidator;

    @Autowired
    private DocumentTypeResolver documentTypeResolver;

    @Autowired
    private CcdaValidator ccdaValidator;

    @Override
    public ValidationResponseDto validateDocument(ValidationRequestDto requestDto) {
        //Determine document type
        final DocumentType documentType = documentTypeResolver.resolve(DocumentHandler
                .convertByteDocumentToString(requestDto.getDocument(), requestDto.getDocumentEncoding()));
        log.info("Identified document as " + documentType);
        ValidationResponseDto responseDto;

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

        ValidationResponseDto validationResponseDto = new ValidationResponseDto();
        validationResponseDto.setDocumentType(documentType);
        validationResponseDto.setValidationResultDetails(validatorResults);
        return validationResponseDto;
    }
}