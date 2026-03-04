package com.bancario;

import com.bancario.model.MovimientoBancario;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Flujo de datos: filtros, agrupación por cuenta/tipo y totales (Parte 1 de la guía).
 */
public class ProcesamientoRegistrosService {

    /**
     * Total de importes por cuenta en un rango de fechas.
     */
    public Map<String, Double> totalPorCuenta(List<MovimientoBancario> movimientos, LocalDate desde, LocalDate hasta) {
        return movimientos.stream()
            .filter(m -> !m.fecha().isBefore(desde) && !m.fecha().isAfter(hasta))
            .collect(Collectors.groupingBy(
                MovimientoBancario::cuenta,
                Collectors.summingDouble(MovimientoBancario::importe)
            ));
    }

    /**
     * Total de importes por tipo de movimiento.
     */
    public Map<String, Double> totalPorTipo(List<MovimientoBancario> movimientos) {
        return movimientos.stream()
            .collect(Collectors.groupingBy(
                MovimientoBancario::tipo,
                Collectors.summingDouble(MovimientoBancario::importe)
            ));
    }

    /**
     * Cantidad de movimientos por tipo.
     */
    public Map<String, Long> cantidadPorTipo(List<MovimientoBancario> movimientos) {
        return movimientos.stream()
            .collect(Collectors.groupingBy(
                MovimientoBancario::tipo,
                Collectors.counting()
            ));
    }

    /**
     * Movimientos de una cuenta concreta en un rango de fechas (paso 2 del flujo).
     */
    public List<MovimientoBancario> filtrarPorCuentaYRango(List<MovimientoBancario> movimientos, String cuenta, LocalDate desde, LocalDate hasta) {
        return movimientos.stream()
            .filter(m -> cuenta.equals(m.cuenta()))
            .filter(m -> !m.fecha().isBefore(desde) && !m.fecha().isAfter(hasta))
            .collect(Collectors.toList());
    }
}
