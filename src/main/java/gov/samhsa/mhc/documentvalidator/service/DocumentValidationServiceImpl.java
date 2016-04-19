/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator.service;

import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResponseDto;
import gov.samhsa.mhc.documentvalidator.service.dto.ValidationResultsMetaData;
import gov.samhsa.mhc.documentvalidator.service.validators.CCDAValidator;
import org.apache.commons.io.IOUtils;
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
    public ValidationResponseDto validateDocument(MultipartFile ccdaFile) {
        ValidationResponseDto responseDto = new ValidationResponseDto();
        ValidationResultsMetaData resultsMetaData = new ValidationResultsMetaData();
        List<DocumentValidationResult> validatorResults = new ArrayList<DocumentValidationResult>();
        try {
            validatorResults = runValidators(ccdaFile);
            resultsMetaData = buildValidationMedata(validatorResults);
        } catch (SAXException e) {
            //TODO
            throw new RuntimeException("Error getting CCDA contents from provided file", e);
        }
        responseDto.setResultsMetaData(resultsMetaData);
        responseDto.setValidationDetails(validatorResults);
        return responseDto;
    }

    private List<DocumentValidationResult> runValidators(MultipartFile ccdaFile) throws SAXException {
        List<DocumentValidationResult> validatorResults = new ArrayList<DocumentValidationResult>();
        String ccdaFileContents;
        InputStream fileIs = null;
        try {
            fileIs = ccdaFile.getInputStream();
            ccdaFileContents = IOUtils.toString(fileIs);
            validatorResults.addAll(doValidation(ccdaFileContents));

        } catch (IOException e) {
            throw new RuntimeException("Error getting CCDA contents from provided file", e);
        } finally {
            closeFileInputStream(fileIs);
        }
        return validatorResults;
    }

    private List<DocumentValidationResult> doValidation(String ccdaFileContents) throws SAXException {
        return ccdaValidator.validateCCDA(ccdaFileContents);
    }

    private ValidationResultsMetaData buildValidationMedata(List<DocumentValidationResult> validatorResults) {
        ValidationResultsMetaData resultsMetaData = new ValidationResultsMetaData();
        for (DocumentValidationResult result : validatorResults) {
            resultsMetaData.addCount(result.getType());
        }
        return resultsMetaData;
    }

    private void closeFileInputStream(InputStream fileIs) {
        if (fileIs != null) {
            try {
                fileIs.close();
            } catch (IOException e) {
                throw new RuntimeException("Error closing CCDA file input stream", e);
            }
        }
    }
}
