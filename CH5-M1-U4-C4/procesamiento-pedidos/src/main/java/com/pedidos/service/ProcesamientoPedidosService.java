package com.pedidos.service;

import com.pedidos.model.Pedido;

import java.util.ArrayList;
import java.util.List;

/**
 * Procesamiento de pedidos escrito ÚNICAMENTE en estilo IMPERATIVO (bucles, variables mutables).
 */
public class ProcesamientoPedidosService {

    /** Ejercicio 1: Lista de pedidos con importe mayor al umbral. */
    public List<Pedido> pedidosConImporteMayorA(List<Pedido> pedidos, double umbral) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getImporte() > umbral) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /** Ejercicio 2: Lista de nombres de clientes sin repetir. */
    public List<String> clientesUnicos(List<Pedido> pedidos) {
        List<String> nombres = new ArrayList<>();
        for (Pedido p : pedidos) {
            String cliente = p.getCliente();
            if (!nombres.contains(cliente)) {
                nombres.add(cliente);
            }
        }
        return nombres;
    }

    /** Ejercicio 3: Total de importe de todos los pedidos. */
    public double totalImporte(List<Pedido> pedidos) {
        double total = 0;
        for (Pedido p : pedidos) {
            total += p.getImporte();
        }
        return total;
    }

    /** Ejercicio 4: Solo pedidos PAGADOS, ordenados por importe desc, los 5 primeros. */
    public List<Pedido> top5PagadosPorImporteDesc(List<Pedido> pedidos) {
        List<Pedido> pagados = new ArrayList<>();
        for (Pedido p : pedidos) {
            if ("PAGADO".equals(p.getEstado())) {
                pagados.add(p);
            }
        }
        for (int i = 0; i < pagados.size() - 1; i++) {
            for (int j = 0; j < pagados.size() - 1 - i; j++) {
                if (pagados.get(j).getImporte() < pagados.get(j + 1).getImporte()) {
                    Pedido temp = pagados.get(j);
                    pagados.set(j, pagados.get(j + 1));
                    pagados.set(j + 1, temp);
                }
            }
        }
        List<Pedido> resultado = new ArrayList<>();
        int limite = Math.min(5, pagados.size());
        for (int i = 0; i < limite; i++) {
            resultado.add(pagados.get(i));
        }
        return resultado;
    }

    /** Ejercicio 5: Cantidad de pedidos pagados. */
    public long cantidadPedidosPagados(List<Pedido> pedidos) {
        long count = 0;
        for (Pedido p : pedidos) {
            if ("PAGADO".equals(p.getEstado())) {
                count++;
            }
        }
        return count;
    }

    /** Ejercicio 6: ¿Hay algún pedido con importe mayor al umbral? */
    public boolean hayPedidoConImporteMayorA(List<Pedido> pedidos, double umbral) {
        for (Pedido p : pedidos) {
            if (p.getImporte() > umbral) {
                return true;
            }
        }
        return false;
    }

    /** Ejercicio 7: Primer pedido de un cliente dado (o null). */
    public Pedido primerPedidoDeCliente(List<Pedido> pedidos, String cliente) {
        for (Pedido p : pedidos) {
            if (cliente.equals(p.getCliente())) {
                return p;
            }
        }
        return null;
    }

    /** Ejercicio 8: ¿Todos los pedidos están pagados? */
    public boolean todosSonPagados(List<Pedido> pedidos) {
        for (Pedido p : pedidos) {
            if (!"PAGADO".equals(p.getEstado())) {
                return false;
            }
        }
        return true;
    }

    /** Ejercicio 9: ¿Ningún pedido está cancelado? */
    public boolean ningunoCancelado(List<Pedido> pedidos) {
        for (Pedido p : pedidos) {
            if ("CANCELADO".equals(p.getEstado())) {
                return false;
            }
        }
        return true;
    }

    /** Ejercicio 10: Pedidos ordenados por nombre de cliente (alfabético). */
    public List<Pedido> pedidosOrdenadosPorCliente(List<Pedido> pedidos) {
        List<Pedido> copia = new ArrayList<>(pedidos);
        copia.sort((a, b) -> a.getCliente().compareTo(b.getCliente()));
        return copia;
    }
}
