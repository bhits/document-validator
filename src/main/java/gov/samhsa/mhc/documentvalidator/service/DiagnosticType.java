/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator.service;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DiagnosticType {
    CCDA_ERROR("C-CDA Error"),
    CCDA_WARN("C-CDA Warning"),
    CCDA_INFO("C-CDA Info");

    private String errorTypeName;

    private DiagnosticType(String type) {
        errorTypeName = type;
    }

    @JsonValue
    public String getTypeName() {
        return errorTypeName;
    }

    public String getValidationResultType() {
        return name();
    }

}
