package com.ventas;

import com.ventas.model.Venta;

import java.util.List;
import java.util.Map;

/**
 * Punto de entrada: analiza la base de datos simulada de ventas y muestra
 * total por producto, cliente con mayor facturación, productos por categoría,
 * ejemplos de partitioningBy/summarizingInt y comparativa stream vs parallelStream.
 */
public class Main {

    public static void main(String[] args) {
        List<Venta> ventas = DatosVentas.ejemploPequeño();
        AnalisisVentasService servicio = new AnalisisVentasService();
//
//        System.out.println("=== Total por producto ===");
//        Map<String, Long> totalPorProducto = servicio.totalPorProducto(ventas);
//        totalPorProducto.forEach((producto, total) -> System.out.println("  " + producto + ": " + total));
//
//        System.out.println("\n=== Cliente con mayor facturación ===");
//        String clienteMax = servicio.clienteConMayorFacturacion(ventas);
//        System.out.println("  " + clienteMax);
//
//        System.out.println("\n=== Productos por categoría (nombres únicos) ===");
//        Map<String, List<String>> porCategoria = servicio.productosPorCategoria(ventas);
//        porCategoria.forEach((cat, productos) -> System.out.println("  " + cat + ": " + productos));
//
//        System.out.println("\n=== PartitioningBy: ventas con total > 200 vs resto ===");
//        Map<Boolean, List<Venta>> grandesVsResto = servicio.ventasGrandesVsResto(ventas, 200);
//        System.out.println("  Grandes (>200): " + grandesVsResto.get(true).size());
//        System.out.println("  Resto: " + grandesVsResto.get(false).size());
//
//        Map<Boolean, Long> grandesVsResto2 = servicio.ventasGrandesVsResto(ventas);
//
//        System.out.println("  Grandes (>200): " + grandesVsResto2.get(true));
//        System.out.println("  Resto: " + grandesVsResto2.get(false));
//
//        System.out.println("\n=== SummarizingInt: estadísticas de cantidad ===");
//        IntSummaryStatistics stats = servicio.estadisticasCantidad(ventas);
//        System.out.println("  count=" + stats.getCount() + " sum=" + stats.getSum() + " min=" + stats.getMin()
//                + " max=" + stats.getMax() + " average=" + stats.getAverage());
//
//        System.out.println("\n=== Estadísticas de cantidad por categoría ===");
//        Map<String, IntSummaryStatistics> statsPorCat = servicio.estadisticasCantidadPorCategoria(ventas);
//        statsPorCat.forEach((cat, s) -> System.out.println("  " + cat + ": count=" + s.getCount() + " sum=" + s.getSum()));
//
        System.out.println("\n=== Comparativa stream() vs parallelStream() (lista grande) ===");
        List<Venta> muchasVentas = DatosVentas.generar(1000_000);
        servicio.compararStreamVsParallel(muchasVentas, 5);
    }
}
