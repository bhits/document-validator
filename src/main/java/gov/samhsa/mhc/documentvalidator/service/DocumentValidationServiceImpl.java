/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator.service;

import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResponseDto;
import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResultsMetaData;
import gov.samhsa.mhc.documentvalidator.service.validators.CCDAValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
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
    public ValidationResponseDto validateDocument(MultipartFile documentFile) {
        ValidationResponseDto responseDto = new ValidationResponseDto();
        ValidationResultsMetaData resultsMetaData;
        List<DocumentValidationResult> validatorResults;
        try {
            validatorResults = runValidators(documentFile);
            resultsMetaData = buildValidationMedata(validatorResults);
        } catch (SAXException e) {
            //TODO
            throw new RuntimeException("Error getting CCDA contents from provided file", e);
        }
        responseDto.setResultsMetaData(resultsMetaData);
        responseDto.setValidationDetails(validatorResults);
        return responseDto;
    }

    private List<DocumentValidationResult> runValidators(MultipartFile documentFile) throws SAXException {
        List<DocumentValidationResult> validatorResults = new ArrayList<>();
        InputStream documentFileContents;
        try {
            documentFileContents = documentFile.getInputStream();
            validatorResults.addAll(doValidation(documentFileContents));
        } catch (IOException e) {
            throw new RuntimeException("Error getting CCDA contents from provided file", e);
        }
        return validatorResults;
    }

    private List<DocumentValidationResult> doValidation(InputStream documentFile) throws SAXException {
        return ccdaValidator.validateCCDA(documentFile);
    }

    private ValidationResultsMetaData buildValidationMedata(List<DocumentValidationResult> validatorResults) {
        ValidationResultsMetaData resultsMetaData = new ValidationResultsMetaData();
        for (DocumentValidationResult result : validatorResults) {
            resultsMetaData.addCount(result.getType());
        }
        return resultsMetaData;
    }
}
