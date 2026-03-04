package com.biblioteca.exception;

/**
 * Excepción lanzada cuando un usuario intenta exceder el límite de préstamos
 */
public class LimitePrestamosException extends RuntimeException {
    
    public LimitePrestamosException(String mensaje) {
        super(mensaje);
    }
}

