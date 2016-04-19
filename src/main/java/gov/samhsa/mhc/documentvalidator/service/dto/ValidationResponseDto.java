/**
 * Created by Jiahao.Li on 4/13/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.dto;

import gov.samhsa.mhc.documentvalidator.service.DocumentValidationResult;

import java.util.List;

public class ValidationResponseDto {
    private ValidationResultsMetaData resultsMetaData;
    private List<DocumentValidationResult> validationResults;

    public List<DocumentValidationResult> getValidationDetails() {
        return validationResults;
    }

    public void setValidationDetails(List<DocumentValidationResult> validationDetails) {
        this.validationResults = validationDetails;
    }

    public ValidationResultsMetaData getResultsMetaData() {
        return resultsMetaData;
    }

    public void setResultsMetaData(ValidationResultsMetaData resultsMetaData) {
        this.resultsMetaData = resultsMetaData;
    }
}
