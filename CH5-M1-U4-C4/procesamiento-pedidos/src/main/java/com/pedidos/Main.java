package com.pedidos;

import com.pedidos.ejemplos.EjemplosFuncionales;
import com.pedidos.model.Pedido;
import com.pedidos.service.ProcesamientoPedidosService;

import java.util.List;

/**
 * Ejecuta los ejemplos con enfoque funcional (Streams).
 * Solo se usan las operaciones indicadas en la guía: filter, map, sorted, distinct, limit, skip, toList, toSet, count, findFirst, reduce, anyMatch, etc.
 */
public class Main {

    public static void main(String[] args) {
        List<Pedido> pedidos = crearPedidosDePrueba();
        ProcesamientoPedidosService servicio = new ProcesamientoPedidosService();

        System.out.println("=== PROCESAMIENTO DE PEDIDOS (enfoque funcional - Streams) ===\n");

        System.out.println("1. Pedidos con importe > 50:");
        System.out.println(servicio.pedidosConImporteMayorA(pedidos, 50));

        System.out.println("\n2. Clientes únicos:");
        System.out.println(servicio.clientesUnicos(pedidos));

        System.out.println("\n3. Total importe de todos los pedidos: " + servicio.totalImporte(pedidos));

        System.out.println("\n4. Top 5 pedidos PAGADOS por importe (desc):");
        System.out.println(servicio.top5PagadosPorImporteDesc(pedidos));

        System.out.println("\n5. Cantidad de pedidos PAGADOS: " + servicio.cantidadPedidosPagados(pedidos));

        System.out.println("\n6. ¿Hay algún pedido con importe > 100? " + servicio.hayPedidoConImporteMayorA(pedidos, 100));

        System.out.println("\n7. Primer pedido de Ana: " + servicio.primerPedidoDeCliente(pedidos, "Ana").orElse(null));

        System.out.println("\n=== EJEMPLOS FUNCIONALES ADICIONALES ===");
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
        System.out.println("Solo pares: " + EjemplosFuncionales.soloPares(nums));
        System.out.println("Cuadrados: " + EjemplosFuncionales.cuadrados(nums));
        System.out.println("Suma: " + EjemplosFuncionales.suma(nums));
        System.out.println("Saltar 2, tomar 2: " + EjemplosFuncionales.saltar2Tomar2(nums));

        List<String> palabras = List.of("sol", "agua", "mesa", "luz", "agua");
        System.out.println("A mayúsculas: " + EjemplosFuncionales.aMayusculas(palabras));
        System.out.println("Contar longitud > 3: " + EjemplosFuncionales.contarLargos(palabras));
        System.out.println("Palabras únicas (Set): " + EjemplosFuncionales.palabrasUnicas(palabras));
        System.out.println("Dos primeras por longitud: " + EjemplosFuncionales.dosPrimerasPorLongitud(palabras));
    }

    private static List<Pedido> crearPedidosDePrueba() {
        return List.of(
                new Pedido("P001", "Ana", 30.0, "PAGADO"),
                new Pedido("P002", "Luis", 75.5, "PAGADO"),
                new Pedido("P003", "Ana", 120.0, "PENDIENTE"),
                new Pedido("P004", "Carmen", 45.0, "PAGADO"),
                new Pedido("P005", "Luis", 200.0, "PAGADO"),
                new Pedido("P006", "Ana", 55.0, "PAGADO"),
                new Pedido("P007", "Pedro", 90.0, "CANCELADO"),
                new Pedido("P008", "Carmen", 33.0, "PENDIENTE"),
                new Pedido("P009", "Luis", 88.0, "PAGADO"),
                new Pedido("P010", "Pedro", 60.0, "PAGADO")
        );
    }
}
