package com.pedidos.model;

/**
 * Modelo de un pedido. Usado en el laboratorio de refactorización imperativo → Streams.
 */
public class Pedido {

    private final String id;
    private final String cliente;
    private final double importe;
    private final String estado;  // PAGADO, PENDIENTE, CANCELADO

    public Pedido(String id, String cliente, double importe, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.importe = importe;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public double getImporte() {
        return importe;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", cliente=" + cliente + ", importe=" + importe + ", estado=" + estado + "}";
    }
}
