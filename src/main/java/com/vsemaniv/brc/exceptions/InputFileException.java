package com.vsemaniv.brc.exceptions;

public class InputFileException extends RuntimeException {
    public InputFileException(String message) {
        super(message);
    }

    public InputFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
