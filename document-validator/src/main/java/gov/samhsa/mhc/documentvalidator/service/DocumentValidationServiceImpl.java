/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator.service;

import gov.samhsa.mhc.documentvalidator.service.dto.DocumentValidationResult;
import gov.samhsa.mhc.documentvalidator.service.dto.ValidationRequestDto;
import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResponseDto;
import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResultsMetaData;
import gov.samhsa.mhc.documentvalidator.service.validators.CCDAValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentValidationServiceImpl implements DocumentValidationService {

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
        } catch (SAXException e) {
            //TODO
            throw new RuntimeException("Error getting CCDA contents from provided file", e);
        }
        responseDto.setValidationSummary(buildValidationMedata(validatorResults).getValidationSummary());
        responseDto.setValidationDetails(validatorResults);
        return responseDto;
    }

    private List<DocumentValidationResult> runValidators(ValidationRequestDto requestDto) throws SAXException {
        List<DocumentValidationResult> validatorResults = new ArrayList<>();
        InputStream documentFileContents = new ByteArrayInputStream(requestDto.getDocument());
        validatorResults.addAll(ccdaValidator.validateCCDA(documentFileContents));
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
