package com.biblioteca.exception;

/**
 * Excepción lanzada cuando se intenta devolver un préstamo que no existe o no está activo
 */
public class PrestamoNoEncontradoException extends RuntimeException {
    
    public PrestamoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

