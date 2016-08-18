package gov.samhsa.c2s.documentvalidator.service.exception;

/**
 * Created by Jiahao.Li on 4/20/2016.
 */
public class ValidationFailedException extends RuntimeException{
    public ValidationFailedException() {
        super();
    }

    public ValidationFailedException(String message) {
        super(message);
    }

    public ValidationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationFailedException(Throwable cause) {
        super(cause);
    }

    protected ValidationFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
