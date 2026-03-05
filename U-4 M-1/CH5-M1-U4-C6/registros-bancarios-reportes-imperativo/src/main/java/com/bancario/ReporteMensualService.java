package com.bancario;

import com.bancario.model.MovimientoBancario;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Taller: reporte mensual con agrupación por mes y estadísticas (Parte 2).
 * Implementación IMPERATIVA: refactorizar a Streams en clase.
 */
public class ReporteMensualService {

    /**
     * Total de importes por mes (YearMonth).
     */
    public Map<YearMonth, Double> totalPorMes(List<MovimientoBancario> movimientos) {
        Map<YearMonth, Double> totalPorMes = new HashMap<>();
        for (MovimientoBancario m : movimientos) {
            YearMonth mes = YearMonth.from(m.fecha());
            totalPorMes.merge(mes, m.importe(), Double::sum);
        }
        return totalPorMes;
    }

    /**
     * Cantidad de movimientos por mes.
     */
    public Map<YearMonth, Long> cantidadPorMes(List<MovimientoBancario> movimientos) {
        Map<YearMonth, Long> cantidadPorMes = new HashMap<>();
        for (MovimientoBancario m : movimientos) {
            YearMonth mes = YearMonth.from(m.fecha());
            cantidadPorMes.merge(mes, 1L, Long::sum);
        }
        return cantidadPorMes;
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
