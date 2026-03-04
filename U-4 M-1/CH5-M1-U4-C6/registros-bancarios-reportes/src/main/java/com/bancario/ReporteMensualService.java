package com.bancario;

import com.bancario.model.MovimientoBancario;

import java.time.YearMonth;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Taller: reporte mensual con agrupación por mes y estadísticas (Parte 2 de la guía).
 */
public class ReporteMensualService {

    /**
     * Total de importes por mes (YearMonth).
     */
    public Map<YearMonth, Double> totalPorMes(List<MovimientoBancario> movimientos) {
        return movimientos.stream()
            .collect(Collectors.groupingBy(
                m -> YearMonth.from(m.fecha()),
                Collectors.summingDouble(MovimientoBancario::importe)
            ));
    }

    /**
     * Cantidad de movimientos por mes.
     */
    public Map<YearMonth, Long> cantidadPorMes(List<MovimientoBancario> movimientos) {
        return movimientos.stream()
            .collect(Collectors.groupingBy(
                m -> YearMonth.from(m.fecha()),
                Collectors.counting()
            ));
    }

    /**
     * Estadísticas (count, sum, min, max, average) por mes.
     */
    public Map<YearMonth, DoubleSummaryStatistics> estadisticasPorMes(List<MovimientoBancario> movimientos) {
        return movimientos.stream()
            .collect(Collectors.groupingBy(
                m -> YearMonth.from(m.fecha()),
                Collectors.summarizingDouble(MovimientoBancario::importe)
            ));
    }

    /**
     * Por cada mes, total por tipo de movimiento (agrupación anidada).
     */
    public Map<YearMonth, Map<String, Double>> totalPorMesYTipo(List<MovimientoBancario> movimientos) {
        return movimientos.stream()
            .collect(Collectors.groupingBy(
                m -> YearMonth.from(m.fecha()),
                Collectors.groupingBy(
                    MovimientoBancario::tipo,
                    Collectors.summingDouble(MovimientoBancario::importe)
                )
            ));
    }

    /**
     * Reporte mensual ordenado por mes (entradas en orden cronológico).
     */
    public List<Map.Entry<YearMonth, DoubleSummaryStatistics>> reporteMensualOrdenado(List<MovimientoBancario> movimientos) {
        return estadisticasPorMes(movimientos).entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .collect(Collectors.toList());
    }
}
