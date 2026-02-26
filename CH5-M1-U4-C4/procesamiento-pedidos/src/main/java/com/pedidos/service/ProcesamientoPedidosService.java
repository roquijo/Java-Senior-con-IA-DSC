package com.pedidos.service;

import com.pedidos.model.Pedido;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Procesamiento de pedidos con enfoque funcional (Streams).
 * Solo se usan: filter, map, sorted, distinct, limit, skip, toList, count, findFirst, reduce, anyMatch.
 */
public class ProcesamientoPedidosService {

    /**
     * 1. Lista de pedidos con importe mayor al umbral.
     * filter + toList
     */
    public List<Pedido> pedidosConImporteMayorA(List<Pedido> pedidos, double umbral) {
        return pedidos.stream()
                .filter(p -> p.getImporte() > umbral)
                .toList();
    }

    /**
     * 2. Lista de nombres de clientes sin repetir.
     * map + distinct + toList
     */
    public List<String> clientesUnicos(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(Pedido::getCliente)
                .distinct()
                .toList();
    }

    /**
     * 3. Total de importe de todos los pedidos.
     * map + reduce
     */
    public double totalImporte(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(Pedido::getImporte)
                .reduce(0.0, Double::sum);
    }

    /**
     * 4. Solo pedidos PAGADOS, ordenados por importe descendente, los 5 primeros.
     * filter + sorted + limit + toList
     */
    public List<Pedido> top5PagadosPorImporteDesc(List<Pedido> pedidos) {
        return pedidos.stream()
                .filter(p -> "PAGADO".equals(p.getEstado()))
                .sorted(Comparator.comparingDouble(Pedido::getImporte).reversed())
                .limit(5)
                .toList();
    }

    /**
     * 5. Cantidad de pedidos pagados.
     * filter + count
     */
    public long cantidadPedidosPagados(List<Pedido> pedidos) {
        return pedidos.stream()
                .filter(p -> "PAGADO".equals(p.getEstado()))
                .count();
    }

    /**
     * 6. ¿Hay algún pedido con importe mayor al umbral?
     * anyMatch
     */
    public boolean hayPedidoConImporteMayorA(List<Pedido> pedidos, double umbral) {
        return pedidos.stream()
                .anyMatch(p -> p.getImporte() > umbral);
    }

    /**
     * 7. Primer pedido de un cliente dado (si existe).
     * filter + findFirst
     */
    public Optional<Pedido> primerPedidoDeCliente(List<Pedido> pedidos, String cliente) {
        return pedidos.stream()
                .filter(p -> cliente.equals(p.getCliente()))
                .findFirst();
    }
}
