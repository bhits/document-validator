/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator.service;

public class DocumentValidationResult {

    private String message;
    private int code;
    private DiagnosticType type;

    public DocumentValidationResult(String message, int code, DiagnosticType type) {
        this.message = message;
        this.code = code;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DiagnosticType getType() {
        return type;
    }

    public void setType(DiagnosticType type) {
        this.type = type;
    }
}
