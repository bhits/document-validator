package gov.samhsa.c2s.documentvalidator.service.schema;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ValidationDiagnosticType {
    CCDA_ERROR("C-CDA MDHT Conformance ERROR"),
    CCDA_WARN("C-CDA MDHT Conformance WARNING"),
    CCDA_INFO("C-CDA MDHT Conformance INFO");

    private String errorTypeName;

    ValidationDiagnosticType(String type) {
        errorTypeName = type;
    }

    @JsonValue
    public String getTypeName() {
        return errorTypeName;
    }
}