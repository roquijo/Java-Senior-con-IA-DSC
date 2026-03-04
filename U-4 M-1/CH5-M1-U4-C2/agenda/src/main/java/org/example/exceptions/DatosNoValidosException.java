package org.example.exceptions;

public class DatosNoValidosException extends RuntimeException {
    public DatosNoValidosException(String message) {
        super(message);
    }
}
