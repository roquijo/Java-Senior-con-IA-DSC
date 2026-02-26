# Procesamiento de pedidos — enfoque funcional (Streams)

Proyecto de apoyo a la **Guía: Optimización de código con Streams y enfoque declarativo** (CH5-M1-U4-C4).

## Contenido

El código está escrito con **enfoque funcional** (Streams). Solo se usan las operaciones indicadas en la guía:

- **Intermedias:** filter, map, sorted, distinct, limit, skip
- **Terminales:** forEach, toList(), collect(Collectors.toSet()), count, findFirst, findAny, reduce, anyMatch, allMatch, noneMatch

No se usan `groupingBy`, `counting()`, `summingDouble()`, `maxBy`, `minBy` ni otros collectors avanzados.

## Estructura

- **model.Pedido:** id, cliente, importe, estado.
- **service.ProcesamientoPedidosService:** métodos con Streams (pedidos por umbral, clientes únicos, total importe, top 5 pagados, cantidad pagados, anyMatch, findFirst por cliente).
- **ejemplos.EjemplosFuncionales:** soloPares, cuadrados, suma, aMayusculas, contarLargos, palabrasUnicas (toSet), dosPrimerasPorLongitud (sorted + limit), saltar2Tomar2 (skip + limit).
- **Main:** datos de prueba e invocación; salida por consola.

## Ejecución

```bash
mvn compile exec:java
```

O ejecutar la clase `com.pedidos.Main` desde el IDE.
