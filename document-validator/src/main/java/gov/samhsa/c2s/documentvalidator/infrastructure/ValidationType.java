package gov.samhsa.c2s.documentvalidator.infrastructure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ValidationType {
    FULL_VALIDATION("Schema_And_Schematron_Validation"),
    SCHEMA_VALIDATION_ONLY("Schema_Validation_Only");

    private String validationTypeName;

    ValidationType(String type) {
        validationTypeName = type;
    }

    @JsonValue
    public String getTypeName() {
        return validationTypeName;
    }
}
