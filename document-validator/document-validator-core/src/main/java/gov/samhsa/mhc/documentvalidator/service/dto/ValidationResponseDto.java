/**
 * Created by Jiahao.Li on 4/13/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.dto;

import java.util.List;

public class ValidationResponseDto {
    private DocumentValidationSummary validationSummary;
    private List<DocumentValidationResult> validationDetails;

    public DocumentValidationSummary getValidationSummary() {
        return validationSummary;
    }

    public void setValidationSummary(DocumentValidationSummary validationSummary) {
        this.validationSummary = validationSummary;
    }

    public List<DocumentValidationResult> getValidationDetails() {
        return validationDetails;
    }

    public void setValidationDetails(List<DocumentValidationResult> validationDetails) {
        this.validationDetails = validationDetails;
    }
}
