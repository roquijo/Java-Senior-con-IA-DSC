package com.pedidos;

import com.pedidos.ejemplos.EjemplosImperativosAdicionales;
import com.pedidos.model.Pedido;
import com.pedidos.service.ProcesamientoPedidosService;

import java.util.List;
import java.util.Set;

/**
 * Ejecuta todos los ejemplos en estilo IMPERATIVO.
 */
public class Main {

    public static void main(String[] args) {
        List<Pedido> pedidos = crearPedidosDePrueba();
        ProcesamientoPedidosService servicio = new ProcesamientoPedidosService();

        System.out.println("=== PROCESAMIENTO DE PEDIDOS (imperativo – resolver en clase con Streams) ===\n");

        System.out.println("Ej 1. Pedidos con importe > 50: " + servicio.pedidosConImporteMayorA(pedidos, 50).size() + " pedidos");
        System.out.println("Ej 2. Clientes únicos: " + servicio.clientesUnicos(pedidos));
        System.out.println("Ej 3. Total importe: " + servicio.totalImporte(pedidos));
        System.out.println("Ej 4. Top 5 PAGADOS por importe desc: " + servicio.top5PagadosPorImporteDesc(pedidos));
        System.out.println("Ej 5. Cantidad PAGADOS: " + servicio.cantidadPedidosPagados(pedidos));
        System.out.println("Ej 6. ¿Hay importe > 100? " + servicio.hayPedidoConImporteMayorA(pedidos, 100));
        System.out.println("Ej 7. Primer pedido de Ana: " + servicio.primerPedidoDeCliente(pedidos, "Ana"));
        System.out.println("Ej 8. ¿Todos pagados? " + servicio.todosSonPagados(pedidos));
        System.out.println("Ej 9. ¿Ninguno cancelado? " + servicio.ningunoCancelado(pedidos));
        List<Pedido> ordenados = servicio.pedidosOrdenadosPorCliente(pedidos);
        System.out.println("Ej 10. Pedidos ordenados por cliente (primeros 3): " + ordenados.subList(0, Math.min(3, ordenados.size())));

        System.out.println("\n=== EJEMPLOS ADICIONALES (imperativo → funcional en clase) ===");
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
        System.out.println("Ej 11. Solo pares: " + EjemplosImperativosAdicionales.soloPares(nums));
        System.out.println("Ej 12. Cuadrados: " + EjemplosImperativosAdicionales.cuadrados(nums));
        System.out.println("Ej 13. Suma: " + EjemplosImperativosAdicionales.suma(nums));
        List<String> palabras = List.of("sol", "agua", "mesa", "luz", "agua");
        System.out.println("Ej 14. Mayúsculas: " + EjemplosImperativosAdicionales.aMayusculas(palabras));
        System.out.println("Ej 15. Contar longitud > 3: " + EjemplosImperativosAdicionales.contarLargos(palabras));
        Set<String> unicas = EjemplosImperativosAdicionales.palabrasUnicas(palabras);
        System.out.println("Ej 16. Palabras únicas (Set): " + unicas);
        System.out.println("Ej 17. Dos primeras por longitud: " + EjemplosImperativosAdicionales.dosPrimerasPorLongitud(palabras));
        System.out.println("Ej 18. Saltar 2, tomar 2: " + EjemplosImperativosAdicionales.saltar2Tomar2(nums));
        System.out.println("Ej 19. ¿Hay negativo? " + EjemplosImperativosAdicionales.hayAlgunNegativo(nums));
        System.out.println("Ej 20. ¿Todos positivos? " + EjemplosImperativosAdicionales.todosPositivos(nums));
        System.out.println("Ej 21. Primer > 3: " + EjemplosImperativosAdicionales.primerMayorQue(nums, 3));
        System.out.println("Ej 22. Producto: " + EjemplosImperativosAdicionales.producto(List.of(1, 2, 3, 4)));
        System.out.println("Ej 23. Longitudes: " + EjemplosImperativosAdicionales.longitudes(palabras));
        System.out.println("Ej 24. ¿Ninguna vacía? " + EjemplosImperativosAdicionales.ningunaVacia(palabras));
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
