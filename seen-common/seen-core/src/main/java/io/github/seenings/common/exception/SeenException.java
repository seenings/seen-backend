package io.github.seenings.common.exception;

/**
 * @author chixh
 * 2021-05-08
 */
public class SeenException extends Exception {

    public SeenException(String message) {
        super(message);
    }

    public SeenException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeenException(Throwable cause) {
        super(cause);
    }
}
