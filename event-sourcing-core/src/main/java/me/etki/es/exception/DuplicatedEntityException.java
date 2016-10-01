package me.etki.es.exception;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class DuplicatedEntityException extends RuntimeException {

    public DuplicatedEntityException() {
    }

    public DuplicatedEntityException(String message) {
        super(message);
    }

    public DuplicatedEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedEntityException(Throwable cause) {
        super(cause);
    }

    public DuplicatedEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
