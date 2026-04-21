package com.sistemadoacao.backend.exception;

public class UserJaExisteException extends RuntimeException {
    public UserJaExisteException(String message) {
        super(message);
    }

}
