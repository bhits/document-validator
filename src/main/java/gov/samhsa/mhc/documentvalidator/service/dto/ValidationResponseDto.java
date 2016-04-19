/**
 * Created by Jiahao.Li on 4/13/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.dto;

import gov.samhsa.mhc.documentvalidator.service.DocumentValidationResult;

import java.util.List;

public class ValidationResponseDto {
    private ValidationResultsMetaData resultsMetaData;
    private List<DocumentValidationResult> validationResults;

    public List<DocumentValidationResult> getCcdaValidationResults() {
        return validationResults;
    }

    public void setCcdaValidationResults(List<DocumentValidationResult> ccdaValidationResults) {
        this.validationResults = ccdaValidationResults;
    }

    public ValidationResultsMetaData getResultsMetaData() {
        return resultsMetaData;
    }

    public void setResultsMetaData(ValidationResultsMetaData resultsMetaData) {
        this.resultsMetaData = resultsMetaData;
    }
}
