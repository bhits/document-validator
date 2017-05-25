package gov.samhsa.c2s.documentvalidator.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gov.samhsa.c2s.documentvalidator.service.schema.ValidationDiagnosticType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentValidationResultDetail {
    private String description;
    private ValidationDiagnosticType diagnosticType;
    private String xPath;
    private String documentLineNumber;
    private boolean isSchemaError;
    private boolean isIGIssue;
}