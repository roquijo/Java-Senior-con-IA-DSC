# Registros bancarios - VERSIÓN IMPERATIVA (CH5-M1-U4-C6)

Este proyecto es una **copia** de **registros-bancarios-reportes** con la misma funcionalidad implementada usando **programación imperativa** (bucles `for`, mapas manuales con `merge` / `computeIfAbsent`, etc.).

## Objetivo del taller en clase

1. **Abrir este proyecto** (imperativo) y revisar las clases:
   - `ProcesamientoRegistrosService`: total por cuenta, por tipo, cantidad por tipo, filtro por cuenta y rango.
   - `ReporteMensualService`: total por mes, cantidad por mes, estadísticas por mes, total por mes y tipo, reporte ordenado.

2. **Refactorizar** cada método a **Streams** (usando `stream()`, `collect(Collectors.groupingBy(...))`, `summingDouble`, `counting`, `summarizingDouble`, etc.).

3. **Comprobar** que el resultado es el mismo ejecutando `Main` antes y después de la refactorización.

4. La guía **GUIA_Consolidacion_Streams_Escenarios_Empresariales.md** no muestra las soluciones del taller; solo objetivos y pistas. El proyecto **registros-bancarios-reportes** (con Streams) sirve como referencia una vez resuelto.

## Cómo ejecutar

```bash
mvn compile exec:java
```

## Estructura

- **model.MovimientoBancario**, **DatosBancarios**: iguales que en el proyecto con Streams.
- **ProcesamientoRegistrosService**, **ReporteMensualService**: misma API (mismos métodos y firmas), implementación imperativa para refactorizar.
- **Main**: igual que en el proyecto con Streams; solo usa los servicios anteriores.
