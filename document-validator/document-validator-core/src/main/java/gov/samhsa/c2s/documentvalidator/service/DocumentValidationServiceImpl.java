package gov.samhsa.c2s.documentvalidator.service;

import gov.samhsa.c2s.common.log.Logger;
import gov.samhsa.c2s.common.log.LoggerFactory;
import gov.samhsa.c2s.documentvalidator.service.dto.*;
import gov.samhsa.c2s.documentvalidator.service.exception.ValidationFailedException;
import gov.samhsa.c2s.documentvalidator.service.validators.CCDAValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DocumentValidationServiceImpl implements DocumentValidationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${validator.version}")
    private String validatorType;

    @Autowired
    private CCDAValidator ccdaValidator;

    @Autowired
    public DocumentValidationServiceImpl(CCDAValidator ccdaValidator) {
        this.ccdaValidator = ccdaValidator;
    }

    @Override
    public ValidationResponseDto validateDocument(ValidationRequestDto requestDto) {
        ValidationResponseDto responseDto = new ValidationResponseDto();
        List<DocumentValidationResult> validatorResults;
        try {
            validatorResults = runValidator(requestDto);
            DocumentValidationSummary validationSummary = buildValidationSummary(validatorResults);
            responseDto.setValidationSummary(validationSummary);
            responseDto.setValidationDetails(validatorResults);
            return responseDto;
        } catch (Exception e) {
            logger.info(() -> "Validation failed: " + e.getMessage());
            logger.debug(e::getMessage, e);
            throw new ValidationFailedException();
        }
    }

    private List<DocumentValidationResult> runValidator(ValidationRequestDto requestDto) throws Exception {
        List<DocumentValidationResult> validatorResults = new ArrayList<>();
        validatorResults.addAll(ccdaValidator.validateCCDA(requestDto.getDocument(), requestDto.getDocumentEncoding()));
        return validatorResults;
    }

    private ValidationResultsMetaData buildValidationMedata(List<DocumentValidationResult> validatorResults) {
        ValidationResultsMetaData resultsMetaData = new ValidationResultsMetaData();
        for (DocumentValidationResult result : validatorResults) {
            resultsMetaData.addCount(result.getType());
        }
        return resultsMetaData;
    }

    private DocumentValidationSummary buildValidationSummary(List<DocumentValidationResult> validatorResults) {
        Map<String, AtomicInteger> validationSummaryMap = buildValidationMedata(validatorResults).getValidationSummaryMap();

        String documentType = "C-CDA";
        int error = validationSummaryMap.get(DiagnosticType.CCDA_ERROR.getTypeName()).intValue();
        int warning = validationSummaryMap.get(DiagnosticType.CCDA_WARN.getTypeName()).intValue();
        int info = validationSummaryMap.get(DiagnosticType.CCDA_INFO.getTypeName()).intValue();
        return new DocumentValidationSummary(validatorType, documentType, error, warning, info);
    }
}
