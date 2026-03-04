# Procesamiento de pedidos — ejercicios imperativos para resolver en clase con Streams

Proyecto de apoyo a la **Guía: Optimización de código con Streams y enfoque declarativo** (CH5-M1-U4-C4).

## Contenido

Todo el código está escrito **solo en estilo imperativo** (bucles `for`, `ArrayList`, `HashSet`, variables mutables). **No se usa ningún Stream.** El objetivo es resolver en clase (2 horas) cada método pasando a enfoque funcional con Streams, usando las operaciones de la guía: filter, map, sorted, distinct, limit, skip, toList, toSet, count, findFirst, reduce, anyMatch, allMatch, noneMatch.

## Ejercicios (24 en total)

### ProcesamientoPedidosService (Ej 1–10) — sobre lista de Pedido

| Ej | Método | Operaciones funcionales a usar |
|----|--------|---------------------------------|
| 1 | pedidosConImporteMayorA | filter + toList |
| 2 | clientesUnicos | map + distinct + toList |
| 3 | totalImporte | map + reduce |
| 4 | top5PagadosPorImporteDesc | filter + sorted + limit + toList |
| 5 | cantidadPedidosPagados | filter + count |
| 6 | hayPedidoConImporteMayorA | anyMatch |
| 7 | primerPedidoDeCliente | filter + findFirst |
| 8 | todosSonPagados | allMatch |
| 9 | ningunoCancelado | noneMatch |
| 10 | pedidosOrdenadosPorCliente | sorted + toList |

### EjemplosImperativosAdicionales (Ej 11–24) — números y strings

| Ej | Método | Operaciones funcionales a usar |
|----|--------|---------------------------------|
| 11 | soloPares | filter + toList |
| 12 | cuadrados | map + toList |
| 13 | suma | reduce |
| 14 | aMayusculas | map + toList |
| 15 | contarLargos | filter + count |
| 16 | palabrasUnicas | collect(Collectors.toSet()) |
| 17 | dosPrimerasPorLongitud | sorted + limit + toList |
| 18 | saltar2Tomar2 | skip + limit + toList |
| 19 | hayAlgunNegativo | anyMatch |
| 20 | todosPositivos | allMatch |
| 21 | primerMayorQue | filter + findFirst |
| 22 | producto | reduce |
| 23 | longitudes | map + toList |
| 24 | ningunaVacia | noneMatch |

Salida: resultados de todos los métodos imperativos. Tras reescribir cada método con Streams, el resultado debe coincidir.

## Estructura

- **model.Pedido** — id, cliente, importe, estado.
- **service.ProcesamientoPedidosService** — 10 métodos imperativos (pedidos).
- **ejemplos.EjemplosImperativosAdicionales** — 14 métodos imperativos (números y strings).
- **Main** — crea datos de prueba, llama a todos los métodos e imprime por consola.
