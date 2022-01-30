package com.intpfyqa.logging.impl.log4j_html;

/**
 * Exceptions of this type won't be logged with DefaultListener
 */
public class UnloggingException extends AssertionError {

    public UnloggingException() {
        super();
    }

    public UnloggingException(String message) {
        super(message);
    }

    public UnloggingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnloggingException(Throwable cause) {
        super(cause);
    }

}
