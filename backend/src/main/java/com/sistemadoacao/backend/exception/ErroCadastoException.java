package com.sistemadoacao.backend.exception;

public class ErroCadastoException extends RuntimeException {
    public ErroCadastoException(String message) {
        super(message);
    }

    public ErroCadastoException(String message, Throwable cause) {
        super(message, cause);
    }

}
