package gov.samhsa.c2s.documentvalidator.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationDiagnosticStatistics {
    private String diagnosticType;
    private int count;
}