package gov.samhsa.c2s.documentvalidator.service.dto;

public class DocumentValidationSummary {
    private String validatorType;
    private String documentType;
    private int error;
    private int warning;
    private int info;

    public DocumentValidationSummary(String validatorType, String documentType, int error, int warning, int info) {
        this.validatorType = validatorType;
        this.documentType = documentType;
        this.error = error;
        this.warning = warning;
        this.info = info;
    }

    public String getValidatorType() {
        return validatorType;
    }

    public void setValidatorType(String validatorType) {
        this.validatorType = validatorType;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }
}
