package com.pedidos.model;

import java.util.Objects;

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

    public boolean pagado() {
        return estado.equals("PAGADO");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id) && Objects.equals(cliente, pedido.cliente) && Objects.equals(estado, pedido.estado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cliente, importe, estado);
    }

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", cliente=" + cliente + ", importe=" + importe + ", estado=" + estado + "}";
    }
}
