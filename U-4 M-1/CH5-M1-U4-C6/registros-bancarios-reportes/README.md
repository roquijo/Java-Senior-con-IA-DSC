# Registros bancarios y reporte mensual (CH5-M1-U4-C6)

Proyecto de apoyo para la **clase-tutoría** de consolidación de Streams y escenarios empresariales.

## Objetivos

- **Análisis guiado:** seguir el flujo de datos en una app que procesa registros bancarios (origen → filtros → agrupación → totales).
- **Taller:** sistema de reporte mensual con agrupación por `YearMonth` y cálculos estadísticos (total, cantidad, min, max, average por mes; desglose por tipo).

## Cómo ejecutar

```bash
mvn compile exec:java
```

## Estructura

- **model.MovimientoBancario:** record con id, cuenta, fecha, tipo, importe, descripcion.
- **DatosBancarios:** genera movimientos de ejemplo (lista pequeña fija o lista grande aleatoria).
- **ProcesamientoRegistrosService:** total por cuenta (con rango de fechas), total y cantidad por tipo, filtro por cuenta y rango (Parte 1 de la guía).
- **ReporteMensualService:** total por mes, cantidad por mes, estadísticas por mes, total por mes y tipo, reporte ordenado por mes (Parte 2).
- **Main:** ejecuta el análisis guiado y el taller e imprime resultados.

## Guía

Ver en la misma carpeta (CH5-M1-U4-C6): **GUIA_Consolidacion_Streams_Escenarios_Empresariales.md**

Incluye además: diagnóstico de cuellos de botella (Parte 3) y refactorización de proyectos previos con Streams (Parte 4).
