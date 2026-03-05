package com.bancario;

import com.bancario.model.MovimientoBancario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Flujo de datos: filtros, agrupación por cuenta/tipo y totales (Parte 1).
 * Implementación IMPERATIVA: refactorizar a Streams en el taller.
 */
public class ProcesamientoRegistrosService {

    /**
     * Total de importes por cuenta en un rango de fechas.
     */
    public Map<String, Double> totalPorCuenta(List<MovimientoBancario> movimientos, LocalDate desde, LocalDate hasta) {
        Map<String, Double> totalPorCuenta = new HashMap<>();
        for (MovimientoBancario m : movimientos) {
            if (m.fecha().isBefore(desde) || m.fecha().isAfter(hasta)) {
                continue;
            }
            String c = m.cuenta();
            totalPorCuenta.merge(c, m.importe(), Double::sum);
        }
        return totalPorCuenta;
    }

    /**
     * Total de importes por tipo de movimiento.
     */
    public Map<String, Double> totalPorTipo(List<MovimientoBancario> movimientos) {
        Map<String, Double> totalPorTipo = new HashMap<>();
        for (MovimientoBancario m : movimientos) {
            String t = m.tipo();
            totalPorTipo.merge(t, m.importe(), Double::sum);
        }
        return totalPorTipo;
    }

    /**
     * Cantidad de movimientos por tipo.
     */
    public Map<String, Long> cantidadPorTipo(List<MovimientoBancario> movimientos) {
        Map<String, Long> cantidadPorTipo = new HashMap<>();
        for (MovimientoBancario m : movimientos) {
            String t = m.tipo();
            cantidadPorTipo.merge(t, 1L, Long::sum);
        }
        return cantidadPorTipo;
    }

    /**
     * Movimientos de una cuenta concreta en un rango de fechas (paso 2 del flujo).
     */
    public List<MovimientoBancario> filtrarPorCuentaYRango(List<MovimientoBancario> movimientos, String cuenta, LocalDate desde, LocalDate hasta) {
        List<MovimientoBancario> resultado = new ArrayList<>();
        for (MovimientoBancario m : movimientos) {
            if (!cuenta.equals(m.cuenta())) {
                continue;
            }
            if (m.fecha().isBefore(desde) || m.fecha().isAfter(hasta)) {
                continue;
            }
            resultado.add(m);
        }
        return resultado;
    }
}
