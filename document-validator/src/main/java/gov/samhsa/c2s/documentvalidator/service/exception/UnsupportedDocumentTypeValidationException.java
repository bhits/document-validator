package gov.samhsa.c2s.documentvalidator.service.exception;

public class UnsupportedDocumentTypeValidationException extends RuntimeException {
    public UnsupportedDocumentTypeValidationException() {
        super();
    }

    public UnsupportedDocumentTypeValidationException(String message) {
        super(message);
    }

    public UnsupportedDocumentTypeValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedDocumentTypeValidationException(Throwable cause) {
        super(cause);
    }

    protected UnsupportedDocumentTypeValidationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
