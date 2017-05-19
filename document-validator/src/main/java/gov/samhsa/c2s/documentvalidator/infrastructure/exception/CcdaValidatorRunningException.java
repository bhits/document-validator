package gov.samhsa.c2s.documentvalidator.infrastructure.exception;

public class CcdaValidatorRunningException extends RuntimeException {
    public CcdaValidatorRunningException() {
        super();
    }

    public CcdaValidatorRunningException(String message) {
        super(message);
    }

    public CcdaValidatorRunningException(String message, Throwable cause) {
        super(message, cause);
    }

    public CcdaValidatorRunningException(Throwable cause) {
        super(cause);
    }

    protected CcdaValidatorRunningException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
