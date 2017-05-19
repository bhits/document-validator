package gov.samhsa.c2s.documentvalidator.service.exception;

public class DocumentTypeResolverException extends RuntimeException {
    public DocumentTypeResolverException() {
        super();
    }

    public DocumentTypeResolverException(String message) {
        super(message);
    }

    public DocumentTypeResolverException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentTypeResolverException(Throwable cause) {
        super(cause);
    }

    protected DocumentTypeResolverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
