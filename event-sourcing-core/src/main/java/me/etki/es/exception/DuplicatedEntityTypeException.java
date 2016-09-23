package me.etki.es.exception;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class DuplicatedEntityTypeException extends RuntimeException {

    public DuplicatedEntityTypeException() {
    }

    public DuplicatedEntityTypeException(String message) {
        super(message);
    }

    public DuplicatedEntityTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedEntityTypeException(Throwable cause) {
        super(cause);
    }

    public DuplicatedEntityTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
