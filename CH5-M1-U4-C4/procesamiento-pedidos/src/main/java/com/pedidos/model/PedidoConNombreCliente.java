package com.pedidos.model;

public class PedidoConNombreCliente {
    private final String id;
    private final String cliente;


    public PedidoConNombreCliente(String id, String cliente) {
        this.id = id;
        this.cliente = cliente;

    }

    public String getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }


    @Override
    public String toString() {
        return "Pedido{id=" + id + ", cliente=" + cliente + "}";
    }
}
