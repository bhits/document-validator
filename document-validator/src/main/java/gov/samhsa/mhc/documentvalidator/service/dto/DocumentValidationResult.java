/**
 * Created by Jiahao.Li on 4/16/2016.
 */
package gov.samhsa.mhc.documentvalidator.service.dto;

public class DocumentValidationResult {

    private DiagnosticType type;
    private String message;
    private String xPath;
    private String documentLineNumber;

    public DocumentValidationResult(DiagnosticType type, String message, String xPath,
                                    String documentLineNumber) {
        this.type = type;
        this.message = message;
        this.xPath = xPath;
        this.documentLineNumber = documentLineNumber;
    }

    public DiagnosticType getType() {
        return type;
    }

    public void setType(DiagnosticType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getxPath() {
        return xPath;
    }

    public void setxPath(String xPath) {
        this.xPath = xPath;
    }

    public String getDocumentLineNumber() {
        return documentLineNumber;
    }

    public void setDocumentLineNumber(String documentLineNumber) {
        this.documentLineNumber = documentLineNumber;
    }
}
