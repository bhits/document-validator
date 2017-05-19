package gov.samhsa.c2s.documentvalidator.service.schema;

public class TemplateIdRootNotFoundException extends RuntimeException {
    public TemplateIdRootNotFoundException() {
    }

    public TemplateIdRootNotFoundException(String message) {
        super(message);
    }

    public TemplateIdRootNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TemplateIdRootNotFoundException(Throwable cause) {
        super(cause);
    }

    public TemplateIdRootNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
