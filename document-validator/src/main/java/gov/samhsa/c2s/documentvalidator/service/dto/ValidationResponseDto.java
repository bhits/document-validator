package gov.samhsa.c2s.documentvalidator.service.dto;

import gov.samhsa.c2s.documentvalidator.service.schema.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResponseDto {
    private DocumentType documentType;
    private boolean isDocumentValid;
    private DocumentValidationResultSummary validationResultSummary;
    private List<DocumentValidationResultDetail> validationResultDetails;
}
