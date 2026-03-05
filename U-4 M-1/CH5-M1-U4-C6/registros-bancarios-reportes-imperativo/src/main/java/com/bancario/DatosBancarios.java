package com.bancario;

import com.bancario.model.MovimientoBancario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Genera listas de movimientos bancarios para el análisis guiado y el taller de reportes.
 */
public final class DatosBancarios {

    private static final String[] CUENTAS = { "ES121234567890", "ES998877665544", "ES112233445566" };
    private static final String[] TIPOS = { MovimientoBancario.INGRESO, MovimientoBancario.RETIRO, MovimientoBancario.TRANSFERENCIA };

    /**
     * Genera movimientos aleatorios en un rango de fechas para varias cuentas.
     */
    public static List<MovimientoBancario> generar(int cantidad, LocalDate desde, LocalDate hasta) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        List<MovimientoBancario> lista = new ArrayList<>(cantidad);
        for (int i = 0; i < cantidad; i++) {
            long dias = desde.toEpochDay() + rnd.nextLong(0, hasta.toEpochDay() - desde.toEpochDay() + 1);
            LocalDate fecha = LocalDate.ofEpochDay(dias);
            String cuenta = CUENTAS[rnd.nextInt(CUENTAS.length)];
            String tipo = TIPOS[rnd.nextInt(TIPOS.length)];
            double importe = (tipo.equals(MovimientoBancario.RETIRO) ? -1 : 1) * (10 + rnd.nextDouble(500));
            lista.add(new MovimientoBancario(
                "MOV-" + (i + 1),
                cuenta,
                fecha,
                tipo,
                importe,
                tipo + " " + cuenta
            ));
        }
        return lista;
    }

    /**
     * Conjunto pequeño y fijo para seguir el flujo paso a paso en clase.
     */
    public static List<MovimientoBancario> ejemploPequeño() {
        return List.of(
            new MovimientoBancario("M1", "ES121234567890", LocalDate.of(2024, 1, 15), MovimientoBancario.INGRESO, 500.0, "Nómina"),
            new MovimientoBancario("M2", "ES121234567890", LocalDate.of(2024, 1, 20), MovimientoBancario.RETIRO, -100.0, "Cajero"),
            new MovimientoBancario("M3", "ES998877665544", LocalDate.of(2024, 2, 1), MovimientoBancario.INGRESO, 1200.0, "Ingreso"),
            new MovimientoBancario("M4", "ES121234567890", LocalDate.of(2024, 2, 5), MovimientoBancario.TRANSFERENCIA, -250.0, "Transferencia"),
            new MovimientoBancario("M5", "ES112233445566", LocalDate.of(2024, 1, 10), MovimientoBancario.INGRESO, 300.0, "Ingreso")
        );
    }

    private DatosBancarios() {}
}
