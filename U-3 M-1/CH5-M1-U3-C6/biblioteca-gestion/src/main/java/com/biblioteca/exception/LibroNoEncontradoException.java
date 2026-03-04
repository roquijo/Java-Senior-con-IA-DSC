package com.biblioteca.exception;

/**
 * Excepción lanzada cuando se intenta operar con un libro que no existe
 */
public class LibroNoEncontradoException extends RuntimeException {
    
    public LibroNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

