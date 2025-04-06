package io.github.seenings.common.exception;

/**
 * @author chixh
 * 2023-01-27
 */
public class SeenRuntimeException extends RuntimeException {

    public SeenRuntimeException(String message) {
        super(message);
    }

    public SeenRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeenRuntimeException(Throwable cause) {
        super(cause);
    }
}
