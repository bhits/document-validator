/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.c2s.documentvalidator.service.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DiagnosticType {
    CCDA_ERROR("C-CDA ERROR"),
    CCDA_WARN("C-CDA WARNING"),
    CCDA_INFO("C-CDA INFO");

    private String errorTypeName;

    DiagnosticType(String type) {
        errorTypeName = type;
    }

    @JsonValue
    public String getTypeName() {
        return errorTypeName;
    }
}
