package com.taller.exception;

public class DatosNotValidException extends RuntimeException {

    public DatosNotValidException(String mensaje) {
        super(mensaje);
    }
}
