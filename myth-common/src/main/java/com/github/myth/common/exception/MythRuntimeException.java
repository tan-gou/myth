package com.github.myth.common.exception;


public class MythRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -1949770547060521702L;

    public MythRuntimeException() {
    }

    public MythRuntimeException(String message) {
        super(message);
    }

    public MythRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MythRuntimeException(Throwable cause) {
        super(cause);
    }
}
