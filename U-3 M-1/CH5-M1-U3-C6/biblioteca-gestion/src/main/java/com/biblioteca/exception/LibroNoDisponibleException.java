package com.biblioteca.exception;

/**
 * Excepción lanzada cuando se intenta prestar un libro que no está disponible
 */
public class LibroNoDisponibleException extends RuntimeException {
    
    public LibroNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}

