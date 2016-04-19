/**
 * Created by Jiahao.Li on 4/13/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.dto;

import java.util.List;

public class ValidationResponseDto {
    private ValidationResultsMetaData resultsMetaData;
    private List<DocumentValidationResult> validationDetails;

    public List<DocumentValidationResult> getValidationDetails() {
        return validationDetails;
    }

    public void setValidationDetails(List<DocumentValidationResult> validationDetails) {
        this.validationDetails = validationDetails;
    }

    public ValidationResultsMetaData getResultsMetaData() {
        return resultsMetaData;
    }

    public void setResultsMetaData(ValidationResultsMetaData resultsMetaData) {
        this.resultsMetaData = resultsMetaData;
    }
}
