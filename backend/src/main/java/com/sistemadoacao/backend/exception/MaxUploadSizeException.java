package com.sistemadoacao.backend.exception;

public class MaxUploadSizeException extends RuntimeException {
    public MaxUploadSizeException(String message) {
        super(message);
    }

}
