package com.bancario;

import com.bancario.model.MovimientoBancario;

import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Flujo de datos: filtros, agrupación por cuenta/tipo y totales (Parte 1).
 * Implementación IMPERATIVA: refactorizar a Streams en el taller.
 */
public class ProcesamientoRegistrosService {

    /**
     * Total de importes por cuenta en un rango de fechas.
     */
    public Map<String, DoubleSummaryStatistics> totalPorCuenta(List<MovimientoBancario> movimientos, LocalDate desde, LocalDate hasta) {

//        Total por cuenta (rango 2024-01-01 a 2024-02-29):
//        ES112233445566: 300.0
//        ES121234567890: 150.0
//        ES998877665544: 1200.0

//        Total por cuenta (rango 2024-01-01 a 2024-02-29):
//        ES112233445566: 300.0
//        ES121234567890: 150.0
//        ES998877665544: 1200.0

//        ES112233445566: DoubleSummaryStatistics{count=1, sum=300.000000, min=300.000000, average=300.000000, max=300.000000}
//        ES121234567890: DoubleSummaryStatistics{count=3, sum=150.000000, min=-250.000000, average=50.000000, max=500.000000}
//        ES998877665544: DoubleSummaryStatistics{count=1, sum=1200.000000, min=1200.000000, average=1200.000000, max=1200.000000}

        return movimientos.stream()
                .filter(m -> m.fecha().isBefore(hasta) && m.fecha().isAfter(desde))
                .collect(Collectors.groupingBy(MovimientoBancario::cuenta, Collectors.summarizingDouble(MovimientoBancario::importe)));

//                desde ->  fecha inicio = 05/03/2026  != 04/03/2026
//                  hasta -> fecha fin = 05/03/2026  != 06/03/2026
//        Map<String, Double> totalPorCuenta = new HashMap<>();
//        for (MovimientoBancario m : movimientos) {
//            if (m.fecha().isBefore(hasta) && m.fecha().isAfter(desde)) {
//                continue;
//            }
//            String c = m.cuenta();
//            totalPorCuenta.merge(c, m.importe(), Double::sum);
//        }
//        return totalPorCuenta;
    }

    /**
     * Total de importes por tipo de movimiento.
     */
    public Map<String, Double> totalPorTipo(List<MovimientoBancario> movimientos) {

//        Total por tipo de movimiento:
//        TRANSFERENCIA: -250.0
//        RETIRO: -100.0
//        INGRESO: 2000.0

//        Total por tipo de movimiento:
//        TRANSFERENCIA: -250.0
//        RETIRO: -100.0
//        INGRESO: 2000.0

        return movimientos.stream().collect(Collectors.groupingBy(MovimientoBancario::tipo, Collectors.summingDouble(MovimientoBancario::importe)));

//        Map<String, Double> totalPorTipo = new HashMap<>();
//        for (MovimientoBancario m : movimientos) {
//            String t = m.tipo();
//            totalPorTipo.merge(t, m.importe(), Double::sum);
//        }
//        return totalPorTipo;
    }

    /**
     * Cantidad de movimientos por tipo.
     */
    public Map<String, Long> cantidadPorTipo(List<MovimientoBancario> movimientos) {


//        Cantidad por tipo:
//        TRANSFERENCIA: 1
//        RETIRO: 1
//        INGRESO: 3

        return movimientos.stream().collect(Collectors.groupingBy(MovimientoBancario::tipo, Collectors.counting()));

//        Map<String, Long> cantidadPorTipo = new HashMap<>();
//        for (MovimientoBancario m : movimientos) {
//            String t = m.tipo();
//            cantidadPorTipo.merge(t, 1L, Long::sum);
//        }
//        return cantidadPorTipo;
    }

    /**
     * Movimientos de una cuenta concreta en un rango de fechas (paso 2 del flujo).
     */
    public List<MovimientoBancario> filtrarPorCuentaYRango(List<MovimientoBancario> movimientos, String cuenta, LocalDate desde, LocalDate hasta) {
//       [MovimientoBancario[id=M1, cuenta=ES121234567890, fecha=2024-01-15, tipo=INGRESO, importe=500.0, descripcion=Nómina],
//       MovimientoBancario[id=M2, cuenta=ES121234567890, fecha=2024-01-20, tipo=RETIRO, importe=-100.0, descripcion=Cajero],
//       MovimientoBancario[id=M4, cuenta=ES121234567890, fecha=2024-02-05, tipo=TRANSFERENCIA, importe=-250.0, descripcion=Transferencia]]

//        [MovimientoBancario[id=M1, cuenta=ES121234567890, fecha=2024-01-15, tipo=INGRESO, importe=500.0, descripcion=Nómina],
//        MovimientoBancario[id=M2, cuenta=ES121234567890, fecha=2024-01-20, tipo=RETIRO, importe=-100.0, descripcion=Cajero],
//        MovimientoBancario[id=M4, cuenta=ES121234567890, fecha=2024-02-05, tipo=TRANSFERENCIA, importe=-250.0, descripcion=Transferencia]]


        return movimientos.stream()
                .filter(m -> validacion(m, cuenta, desde, hasta))
                .toList();


//        List<MovimientoBancario> resultado = new ArrayList<>();
//        for (MovimientoBancario m : movimientos) {
//            if (!cuenta.equals(m.cuenta())) {
//                continue;
//            }
//            if (m.fecha().isBefore(desde) || m.fecha().isAfter(hasta)) {
//                continue;
//            }
//            resultado.add(m);
//        }
//        return resultado;
    }


    public boolean validacion(MovimientoBancario m, String cuenta, LocalDate desde, LocalDate hasta) {
        return cuenta.equals(m.cuenta()) && m.fecha().isBefore(hasta) && m.fecha().isAfter(desde);
    }


}
