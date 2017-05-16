package gov.samhsa.c2s.documentvalidator.service.dto;

import lombok.Data;

@Data
public class DocumentValidationResultDetail {
    private String description;
    private ValidationDiagnosticType diagnosticType;
    private String xPath;
    private String documentLineNumber;
    private boolean isSchemaError;
    private boolean isIGIssue;
}