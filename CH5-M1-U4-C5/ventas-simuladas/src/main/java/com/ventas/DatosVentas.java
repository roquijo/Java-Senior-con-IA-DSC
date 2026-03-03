package com.ventas;

import com.ventas.model.Venta;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Genera listas de ventas simuladas para el ejercicio práctico.
 */
public final class DatosVentas {

    private static final String[] CLIENTES = { "Ana", "Luis", "María", "Carlos", "Elena", "Pedro", "Sofia" };
    private static final String[] PRODUCTOS = { "Laptop", "Monitor", "Teclado", "Ratón", "USB", "Disco SSD", "Webcam" };
    private static final String[] CATEGORIAS = { "Informática", "Periféricos", "Almacenamiento" };

    private static final Map<String, String> PRODUCTO_A_CATEGORIA = Map.ofEntries(
            Map.entry("Laptop", "Informática"),
            Map.entry("Monitor", "Informática"),
            Map.entry("Teclado", "Periféricos"),
            Map.entry("Ratón", "Periféricos"),
            Map.entry("USB", "Almacenamiento"),
            Map.entry("Disco SSD", "Almacenamiento"),
            Map.entry("Webcam", "Periféricos")
    );

    /**
     * Genera una lista de ventas aleatorias del tamaño indicado.
     */
    public static List<Venta> generar(int cantidad) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        List<Venta> lista = new ArrayList<>(cantidad);
        for (int i = 0; i < cantidad; i++) {
            String producto = PRODUCTOS[rnd.nextInt(PRODUCTOS.length)];
            String categoria = PRODUCTO_A_CATEGORIA.getOrDefault(producto, "Informática");
            String cliente = CLIENTES[rnd.nextInt(CLIENTES.length)];
            int cant = 1 + rnd.nextInt(5);
            double precio = 10 + rnd.nextDouble(500);
            lista.add(new Venta(cliente, producto, categoria, cant, precio));
        }
        return lista;
    }

    /**
     * Conjunto pequeño de ventas fijas para pruebas y ejemplos en clase.
     */
    public static List<Venta> ejemploPequeño() {
        return List.of(
                new Venta("Ana", "Laptop", "Informática", 1, 899.0),
                new Venta("Luis", "Monitor", "Informática", 2, 199.0),
                new Venta("Ana", "Teclado", "Periféricos", 1, 49.0),
                new Venta("María", "Laptop", "Informática", 1, 899.0),
                new Venta("Carlos", "Disco SSD", "Almacenamiento", 2, 89.0),
                new Venta("Ana", "Monitor", "Informática", 1, 199.0),
                new Venta("Elena", "Webcam", "Periféricos", 1, 59.0),
                new Venta("Luis", "Ratón", "Periféricos", 3, 25.0)
        );
    }

    private DatosVentas() {}
}
