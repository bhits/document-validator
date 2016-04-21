/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator.service;

import gov.samhsa.mhc.common.log.Logger;
import gov.samhsa.mhc.common.log.LoggerFactory;
import gov.samhsa.mhc.documentvalidator.service.dto.DocumentValidationResult;
import gov.samhsa.mhc.documentvalidator.service.dto.ValidationRequestDto;
import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResponseDto;
import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResultsMetaData;
import gov.samhsa.mhc.documentvalidator.service.exception.ValidationFailedException;
import gov.samhsa.mhc.documentvalidator.service.validators.CCDAValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentValidationServiceImpl implements DocumentValidationService {

    private Logger logger = LoggerFactory.getLogger(this);

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
            validatorResults = runValidators(requestDto);
            responseDto.setValidationSummary(buildValidationMedata(validatorResults).getValidationSummary());
            responseDto.setValidationDetails(validatorResults);
            return responseDto;
        } catch (Exception e) {
            logger.info(() -> "Validation failed: " + e.getMessage());
            logger.debug(e::getMessage, e);
            throw new ValidationFailedException();
        }
    }

    private List<DocumentValidationResult> runValidators(ValidationRequestDto requestDto) throws Exception {
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
}
