# Ventas simuladas (CH5-M1-U4-C5)

Proyecto de apoyo para el **ejercicio práctico** de la guía *Pipelines complejos, Streams paralelos y agrupaciones avanzadas*.

## Objetivo

Analizar una base de datos simulada de ventas usando:

- **Total por producto:** `groupingBy` + `summingDouble`
- **Cliente con mayor facturación:** `groupingBy` por cliente y máximo por suma de totales
- **Productos por categoría:** `groupingBy` por categoría + `mapping` a lista de nombres (sin repetir)

Además incluye ejemplos de `partitioningBy`, `summarizingInt` y comparativa `stream()` vs `parallelStream()` con `System.nanoTime()`.

## Cómo ejecutar

```bash
mvn compile exec:java
```

## Estructura

- `model.Venta`: record con cliente, producto, categoría, cantidad, precio unitario y `getTotal()`.
- `DatosVentas`: genera listas de ventas (ejemplo pequeño fijo o muchas aleatorias).
- `AnalisisVentasService`: métodos que resuelven los tres análisis y los ejemplos adicionales.
- `Main`: imprime los resultados y lanza la comparativa de tiempos.

## Guía

Ver en la misma carpeta: **GUIA_Pipelines_Complejos_ParallelStream_Agrupaciones.md**.
