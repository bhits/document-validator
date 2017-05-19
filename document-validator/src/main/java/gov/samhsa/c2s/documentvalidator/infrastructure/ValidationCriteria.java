package gov.samhsa.c2s.documentvalidator.infrastructure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ValidationCriteria {
    C_CDA_IG_ONLY("C-CDA_IG_Only"),
    C32_SCHEMA_ONLY("C32_Schema_Only");

    private String criteriaTypeName;

    ValidationCriteria(String type) {
        criteriaTypeName = type;
    }

    @JsonValue
    public String getTypeName() {
        return criteriaTypeName;
    }
}