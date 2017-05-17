package gov.samhsa.c2s.documentvalidator.service.dto;

import gov.samhsa.c2s.documentvalidator.service.schema.DocumentType;
import lombok.Data;

import java.util.List;

@Data
public class ValidationResponseDto {
    private DocumentType documentType;
    private boolean isDocumentValid;
    private DocumentValidationResultSummary validationResultSummary;
    private List<DocumentValidationResultDetail> validationResultDetails;
}
