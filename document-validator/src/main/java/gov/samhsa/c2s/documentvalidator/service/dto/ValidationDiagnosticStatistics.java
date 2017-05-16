package gov.samhsa.c2s.documentvalidator.service.dto;

import lombok.Data;

@Data
public class ValidationDiagnosticStatistics {
    private String diagnosticType;
    private int count;
}