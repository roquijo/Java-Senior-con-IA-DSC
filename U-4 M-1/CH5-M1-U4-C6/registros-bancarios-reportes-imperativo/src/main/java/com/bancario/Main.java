package com.bancario;

import com.bancario.model.MovimientoBancario;

import java.time.LocalDate;
import java.util.List;

/**
 * Punto de entrada: análisis guiado y taller de reporte mensual (versión imperativa).
 */
public class Main {

    public static void main(String[] args) {
        LocalDate desde = LocalDate.of(2024, 1, 1);
        LocalDate hasta = LocalDate.of(2024, 2, 29);

        System.out.println("=== PARTE 1: Análisis guiado - Flujo de datos (ejemplo pequeño) ===\n");
        List<MovimientoBancario> movimientos = DatosBancarios.ejemploPequeño();

        ProcesamientoRegistrosService procesamiento = new ProcesamientoRegistrosService();

//        System.out.println("Total por cuenta (rango " + desde + " a " + hasta + "):");
//        Map<String, DoubleSummaryStatistics> totalPorCuenta = procesamiento.totalPorCuenta(movimientos, desde, hasta);
//        totalPorCuenta.forEach((cuenta, total) -> System.out.println("  " + cuenta + ": " + total));
//
//        System.out.println("\nTotal por tipo de movimiento:");
//        Map<String, Double> totalPorTipo = procesamiento.totalPorTipo(movimientos);
//        totalPorTipo.forEach((tipo, total) -> System.out.println("  " + tipo + ": " + total));
//
//        System.out.println("\nCantidad por tipo:");
//        procesamiento.cantidadPorTipo(movimientos).forEach((tipo, n) -> System.out.println("  " + tipo + ": " + n));
//
//        System.out.println(procesamiento.filtrarPorCuentaYRango(movimientos, "ES121234567890", desde, hasta));

        System.out.println("\n=== PARTE 2: Taller - Reporte mensual ===\n");
        ReporteMensualService reporte = new ReporteMensualService();
//
//        System.out.println("Total por mes:");
//        reporte.totalPorMes(movimientos).forEach((mes, total) -> System.out.println("  " + mes + ": " + total));
//

        System.out.println(reporte.cantidadPorMes(movimientos));
//        System.out.println("\nEstadísticas por mes (count, sum, min, max, average):");
//        List<Map.Entry<YearMonth, DoubleSummaryStatistics>> ordenado = reporte.reporteMensualOrdenado(movimientos);
//        for (Map.Entry<YearMonth, DoubleSummaryStatistics> e : ordenado) {
//            DoubleSummaryStatistics s = e.getValue();
//            System.out.println("  " + e.getKey() + " -> count=" + s.getCount() + " sum=" + s.getSum() + " min=" + s.getMin() + " max=" + s.getMax() + " avg=" + s.getAverage());
//        }
//
//        System.out.println("\nTotal por mes y tipo (agrupación anidada):");
//        reporte.totalPorMesYTipo(movimientos).forEach((mes, porTipo) -> {
//            System.out.println("  " + mes + ": " + porTipo);
//        });
//
//        System.out.println("\n=== Con más datos (generados) ===\n");
//        List<MovimientoBancario> muchos = DatosBancarios.generar(500, desde, hasta);
//        System.out.println("Movimientos generados: " + muchos.size());
//        System.out.println("Total por mes (primeros 3 meses):");
//        reporte.totalPorMes(muchos).entrySet().stream()
//            .sorted(Map.Entry.comparingByKey())
//            .limit(3)
//            .forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue()));
    }
}
