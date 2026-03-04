package com.ventas;

import com.ventas.model.Venta;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Análisis sobre una base de datos simulada de ventas:
 * - Total por producto
 * - Cliente con mayor facturación
 * - Productos agrupados por categoría (nombres únicos)
 * Además: ejemplos con partitioningBy, summarizingInt y comparativa stream vs parallelStream.
 */
public class AnalisisVentasService {

    /**
     * Total facturado por producto: Map&lt;nombreProducto, suma de totales&gt;
     */
    public Map<String, Long> totalPorProducto(List<Venta> ventas) {
        return ventas.stream()
                .collect(Collectors.groupingBy(Venta::cliente, Collectors.counting()));
    }

    /**
     * Cliente cuya suma de totales de ventas es la máxima.
     */
    public String clienteConMayorFacturacion(List<Venta> ventas) {
        Map<String, Double> totalPorCliente = ventas.stream()
                .collect(Collectors.groupingBy(Venta::cliente, Collectors.summingDouble(Venta::getTotal)));
        return totalPorCliente.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    /**
     * Productos agrupados por categoría: Map&lt;categoria, lista de nombres de producto sin repetir&gt;
     */
    public Map<String, List<String>> productosPorCategoria(List<Venta> ventas) {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        Venta::categoria,
                        Collectors.mapping(Venta::producto,
                                Collectors.collectingAndThen(Collectors.toSet(), ArrayList::new))
                ));
    }

    /**
     * Ejemplo partitioningBy: ventas con total &gt; 100 vs resto.
     */
    public Map<Boolean, List<Venta>> ventasGrandesVsResto(List<Venta> ventas, double umbral) {



        return ventas.stream()
                .collect(Collectors.partitioningBy(v -> v.getTotal() > umbral));
    }

    /**
     * Ejemplo summarizingInt: estadísticas de cantidad (count, sum, min, max, average).
     */
    public IntSummaryStatistics estadisticasCantidad(List<Venta> ventas) {
//        System.out.println(Runtime.getRuntime().availableProcessors() - 1);
        return ventas.stream()
                .collect(Collectors.summarizingInt(Venta::cantidad));
    }

    /**
     * Estadísticas de cantidad por categoría.
     */
    public Map<String, IntSummaryStatistics> estadisticasCantidadPorCategoria(List<Venta> ventas) {
        return ventas.stream()
                .collect(Collectors.groupingBy(
                        Venta::categoria,
                        Collectors.summarizingInt(Venta::cantidad)
                ));
    }

    /**
     * Comparativa de tiempo: stream() vs parallelStream() sobre el mismo pipeline.
     * Ejecuta varias pasadas y muestra tiempos en ms (sin calentamiento exhaustivo).
     */
    public void compararStreamVsParallel(List<Venta> ventas, int repeticiones) {
        if (ventas.isEmpty()) {
            System.out.println("Lista vacía, no hay comparativa.");
            return;
        }
        // Pipeline: filter + map + sum
        long sumStream = 0, sumParallel = 0;
        for (int i = 0; i < repeticiones; i++) {
            long t1 = System.nanoTime();
            double s1 = ventas.stream()
                    .filter(v -> v.getTotal() > 50)
                    .mapToDouble(Venta::getTotal)
                    .sum();
            long t2 = System.nanoTime();
            sumStream += (t2 - t1) / 1_000_000;

            long t3 = System.nanoTime();
            double s2 = ventas.parallelStream()
                    .filter(v -> v.getTotal() > 50)
                    .mapToDouble(Venta::getTotal)
                    .sum();
            long t4 = System.nanoTime();
            sumParallel += (t4 - t3) / 1_000_000;
        }
        System.out.println("Elementos: " + ventas.size() + ", repeticiones: " + repeticiones);
        System.out.println("stream()      media: " + (sumStream / repeticiones) + " ms");
        System.out.println("parallelStream() media: " + (sumParallel / repeticiones) + " ms");
    }
}
