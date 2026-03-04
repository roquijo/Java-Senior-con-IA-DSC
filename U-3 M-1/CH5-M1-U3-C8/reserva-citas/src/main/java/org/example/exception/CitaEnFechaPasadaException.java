package org.example.exception;

/**
 * Excepción de negocio cuando se intenta registrar una cita
 * con fecha y/o hora en el pasado.
 */
public class CitaEnFechaPasadaException extends RuntimeException {

    public CitaEnFechaPasadaException(String mensaje) {
        super(mensaje);
    }
}
