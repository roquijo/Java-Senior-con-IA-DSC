package com.bancario.model;

import java.time.LocalDate;

/**
 * Registro de un movimiento bancario (Parte 0 y 1 de la guía C6).
 */
public record MovimientoBancario(
    String id,
    String cuenta,
    LocalDate fecha,
    String tipo,
    double importe,
    String descripcion
) {
    /** Tipos de movimiento usados en los datos de ejemplo. */
    public static final String INGRESO = "INGRESO";
    public static final String RETIRO = "RETIRO";
    public static final String TRANSFERENCIA = "TRANSFERENCIA";
}
