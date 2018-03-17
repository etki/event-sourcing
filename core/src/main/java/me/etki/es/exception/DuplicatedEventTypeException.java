package me.etki.es.exception;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class DuplicatedEventTypeException extends RuntimeException {

    public DuplicatedEventTypeException() {
    }

    public DuplicatedEventTypeException(String message) {
        super(message);
    }

    public DuplicatedEventTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedEventTypeException(Throwable cause) {
        super(cause);
    }

    public DuplicatedEventTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
