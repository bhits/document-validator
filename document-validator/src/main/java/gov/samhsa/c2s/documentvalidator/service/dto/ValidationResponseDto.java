package gov.samhsa.c2s.documentvalidator.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class ValidationResponseDto {
    private boolean isDocumentValid;
    private DocumentValidationResultSummary validationResultSummary;
    private List<DocumentValidationResultDetail> validationResultDetails;
}
