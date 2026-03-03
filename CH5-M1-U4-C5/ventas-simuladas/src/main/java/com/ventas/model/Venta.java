package com.ventas.model;

/**
 * Representa una venta: cliente, producto, categoría, cantidad y precio unitario.
 * getTotal() = cantidad * precioUnitario.
 */
public record Venta(String cliente, String producto, String categoria, int cantidad, double precioUnitario) {

    public double getTotal() {
        return cantidad * precioUnitario;
    }
}
