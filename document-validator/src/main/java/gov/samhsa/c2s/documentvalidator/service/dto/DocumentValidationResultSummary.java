package gov.samhsa.c2s.documentvalidator.service.dto;

import gov.samhsa.c2s.documentvalidator.infrastructure.ValidationCriteria;
import gov.samhsa.c2s.documentvalidator.infrastructure.ValidationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentValidationResultSummary {
    private ValidationCriteria validationCriteria;
    private ValidationType validationType;
    private List<ValidationDiagnosticStatistics> diagnosticStatistics;
}