package gov.samhsa.c2s.documentvalidator.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentValidationResultSummary {
    private ValidationCriteria validationCriteria;
    private ValidationType validationType;
    private List<ValidationDiagnosticStatistics> diagnosticStatistics;
}