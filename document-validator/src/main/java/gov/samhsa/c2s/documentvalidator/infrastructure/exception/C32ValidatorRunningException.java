package gov.samhsa.c2s.documentvalidator.infrastructure.exception;

public class C32ValidatorRunningException extends RuntimeException {
    public C32ValidatorRunningException() {
        super();
    }

    public C32ValidatorRunningException(String message) {
        super(message);
    }

    public C32ValidatorRunningException(String message, Throwable cause) {
        super(message, cause);
    }

    public C32ValidatorRunningException(Throwable cause) {
        super(cause);
    }

    protected C32ValidatorRunningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
