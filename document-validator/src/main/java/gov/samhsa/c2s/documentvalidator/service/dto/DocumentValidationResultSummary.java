package gov.samhsa.c2s.documentvalidator.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class DocumentValidationResultSummary {
    private String validationCriteria;
    private String documentType;
    private List<ValidationDiagnosticStatistics> diagnosticStatistics;
}