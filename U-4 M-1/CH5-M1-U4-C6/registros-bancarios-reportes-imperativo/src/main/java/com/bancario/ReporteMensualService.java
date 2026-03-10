package com.bancario;

import com.bancario.model.MovimientoBancario;

import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Taller: reporte mensual con agrupación por mes y estadísticas (Parte 2).
 * Implementación IMPERATIVA: refactorizar a Streams en clase.
 */
public class ReporteMensualService {

    /**
     * Total de importes por mes (YearMonth).
     */
    public Map<YearMonth, Double> totalPorMes(List<MovimientoBancario> movimientos) {
        return movimientos.stream()
                .collect(Collectors.groupingBy(m -> YearMonth.from(m.fecha()),
                        Collectors.summingDouble(MovimientoBancario::importe)));
//        Total por mes:
//        2024-02: 950.0
//        2024-01: 700.0
//        Map<YearMonth, Double> totalPorMes = new HashMap<>();
//        for (MovimientoBancario m : movimientos) {
//            YearMonth mes = YearMonth.from(m.fecha());
//            totalPorMes.merge(mes, m.importe(), Double::sum);
//        }
//        return totalPorMes;
    }

    /**
     * Cantidad de movimientos por mes.
     */
    public Map<YearMonth, Long> cantidadPorMes(List<MovimientoBancario> movimientos) {

        return movimientos.stream()
                .collect(Collectors.groupingBy(m -> YearMonth.from(m.fecha()), Collectors.counting()));
//        {2024-02=2, 2024-01=3}
//        Map<YearMonth, Long> cantidadPorMes = new HashMap<>();
//        for (MovimientoBancario m : movimientos) {
//            YearMonth mes = YearMonth.from(m.fecha());
//            cantidadPorMes.merge(mes, 1L, Long::sum);
//        }
//        return cantidadPorMes;
    }

    /**
     * Estadísticas (count, sum, min, max, average) por mes.
     */
    public Map<YearMonth, DoubleSummaryStatistics> estadisticasPorMes(List<MovimientoBancario> movimientos) {
        Map<YearMonth, DoubleSummaryStatistics> estadisticasPorMes = new HashMap<>();
        for (MovimientoBancario m : movimientos) {
            YearMonth mes = YearMonth.from(m.fecha());
            estadisticasPorMes.computeIfAbsent(mes, k -> new DoubleSummaryStatistics()).accept(m.importe());
        }
        return estadisticasPorMes;
    }

    /**
     * Por cada mes, total por tipo de movimiento (agrupación anidada).
     */
    public Map<YearMonth, Map<String, Double>> totalPorMesYTipo(List<MovimientoBancario> movimientos) {
        Map<YearMonth, Map<String, Double>> totalPorMesYTipo = new HashMap<>();
        for (MovimientoBancario m : movimientos) {
            YearMonth mes = YearMonth.from(m.fecha());
            Map<String, Double> porTipo = totalPorMesYTipo.computeIfAbsent(mes, k -> new HashMap<>());
            porTipo.merge(m.tipo(), m.importe(), Double::sum);
        }
        return totalPorMesYTipo;
    }

    /**
     * Reporte mensual ordenado por mes (entradas en orden cronológico).
     */
    public List<Map.Entry<YearMonth, DoubleSummaryStatistics>> reporteMensualOrdenado(List<MovimientoBancario> movimientos) {
        Map<YearMonth, DoubleSummaryStatistics> stats = estadisticasPorMes(movimientos);
        List<Map.Entry<YearMonth, DoubleSummaryStatistics>> lista = new ArrayList<>(stats.entrySet());
        lista.sort(Comparator.comparing(Map.Entry::getKey));
        return lista;
    }
}
