# Guía: Optimización de código con Streams y enfoque declarativo

**Carpeta:** CH5-M1-U4-C4

En esta guía y en el proyecto se usan **únicamente** las siguientes operaciones de Streams.

---

## Operaciones permitidas (referencia rápida)

| Tipo        | Operación | Uso |
|-------------|-----------|-----|
| **Intermedias** | `filter(Predicate<T>)` | Dejar solo los elementos que cumplen la condición. |
| | `map(Function<T, R>)` | Transformar cada elemento. |
| | `sorted()` / `sorted(Comparator<T>)` | Ordenar (natural o con comparador). |
| | `distinct()` | Eliminar duplicados. |
| | `limit(long n)` | Quedarse con los primeros n elementos. |
| | `skip(long n)` | Saltar los primeros n elementos. |
| **Terminales** | `forEach(Consumer<T>)` | Ejecutar una acción por cada elemento. |
| | `toList()` | Recoger en una lista: `stream.filter(...).toList()`. |
| | `collect(Collectors.toSet())` | Recoger en un set: `Set<String> set = stream.collect(Collectors.toSet())`. |
| | `count()` | Número de elementos. |
| | `findFirst()` / `findAny()` | Primer elemento (o cualquiera) en un `Optional`. |
| | `reduce(identidad, BinaryOperator<T>)` / `reduce(BinaryOperator<T>)` | Combinar todos en uno (suma, producto, etc.). |
| | `anyMatch` / `allMatch` / `noneMatch(Predicate<T>)` | ¿Alguno cumple? / ¿Todos? / ¿Ninguno? |

---

## Objetivos de la clase-tutoría

- Diseñar flujos de datos con Streams usando **solo** las operaciones de la tabla anterior.
- Resolver problemas de procesamiento de pedidos con pipelines simples (filter, map, sorted, limit, reduce, toList, etc.).
- Laboratorio: proyecto **procesamiento-pedidos** con ejemplos en **enfoque funcional** (Streams) que sirven de referencia.

---

## 1. Diseñar flujos con las operaciones permitidas

### 1.1 Orden típico del pipeline

1. **Origen:** `lista.stream()`.
2. **Filtros:** `filter(...)`.
3. **Transformaciones:** `map(...)`.
4. **Orden / duplicados / límites:** `sorted()`, `distinct()`, `limit(n)`, `skip(n)`.
5. **Resultado:** `toList()`, `toSet()`, `count()`, `findFirst()`, `reduce(...)`, `forEach(...)`, o `anyMatch`/`allMatch`/`noneMatch`.

### 1.2 Ejemplos por operación

- **Lista filtrada:** `pedidos.stream().filter(p -> p.getImporte() > 50).toList()`.
- **Lista transformada:** `pedidos.stream().map(Pedido::getCliente).toList()`.
- **Clientes sin repetir:** `pedidos.stream().map(Pedido::getCliente).distinct().toList()`.
- **Conjunto de nombres:** `nombres.stream().collect(Collectors.toSet())`.
- **Total (reduce):** `pedidos.stream().map(Pedido::getImporte).reduce(0.0, Double::sum)`.
- **Cantidad:** `pedidos.stream().filter(p -> "PAGADO".equals(p.getEstado())).count()`.
- **Primer pedido que cumple:** `pedidos.stream().filter(...).findFirst()`.
- **¿Hay alguno con importe > 100?** `pedidos.stream().anyMatch(p -> p.getImporte() > 100)`.
- **¿Todos pagados?** `pedidos.stream().allMatch(p -> "PAGADO".equals(p.getEstado()))`.
- **Ordenar y tomar 5:** `pedidos.stream().sorted(Comparator.comparingDouble(Pedido::getImporte).reversed()).limit(5).toList()`.
- **Imprimir cada uno:** `pedidos.stream().forEach(p -> System.out.println(p))`.

---

## 2. Patrones frecuentes (solo operaciones permitidas)

| Objetivo | Cómo hacerlo |
|----------|----------------|
| Lista filtrada | `stream().filter(...).toList()` |
| Lista transformada | `stream().map(...).toList()` |
| Sin duplicados (lista) | `stream().map(...).distinct().toList()` |
| Conjunto (sin duplicados) | `stream().collect(Collectors.toSet())` |
| Suma / total | `stream().map(...).reduce(identidad, BinaryOperator)` o para números `mapToDouble(...).sum()` |
| Cantidad de elementos | `stream().filter(...).count()` |
| Primer elemento que cumple | `stream().filter(...).findFirst()` |
| ¿Alguno cumple? | `stream().anyMatch(...)` |
| ¿Todos cumplen? | `stream().allMatch(...)` |
| ¿Ninguno cumple? | `stream().noneMatch(...)` |
| Ordenar | `stream().sorted()` o `sorted(Comparator...)` |
| Los N primeros | `stream()....limit(N).toList()` |
| Saltar los N primeros | `stream().skip(N)....` |
| Ejecutar algo por cada elemento | `stream().forEach(...)` |

---

## 3. Transformación imperativo → funcional (solo operaciones permitidas)

### 3.1 Filtrar y obtener lista

**Imperativo:** bucle con `if` y `add` a una lista.  
**Funcional:** `pedidos.stream().filter(p -> p.getImporte() > 100).toList()`.

### 3.2 Transformar cada elemento a lista

**Imperativo:** bucle que hace `add(transformación(e))`.  
**Funcional:** `pedidos.stream().map(Pedido::getCliente).toList()`.

### 3.3 Sumar o acumular (reduce)

**Imperativo:** variable `total` y bucle `total += ...`.  
**Funcional:** `pedidos.stream().map(Pedido::getImporte).reduce(0.0, Double::sum)`.

### 3.4 Clientes sin repetir

**Imperativo:** bucle y comprobar `contains` antes de añadir.  
**Funcional:** `pedidos.stream().map(Pedido::getCliente).distinct().toList()`.

### 3.5 Filtrar, ordenar y tomar N

**Imperativo:** filtrar a lista, ordenar, tomar los N primeros.  
**Funcional:** `pedidos.stream().filter(p -> "PAGADO".equals(p.getEstado())).sorted(Comparator.comparingDouble(Pedido::getImporte).reversed()).limit(5).toList()`.

---

## 4. Laboratorio: procesamiento de pedidos

### 4.1 Enunciado (con operaciones permitidas)

- **Entrada:** lista de pedidos (id, cliente, importe, estado).
- **Salidas a obtener con Streams (solo las operaciones de la guía):**
  1. Lista de pedidos con importe mayor a un umbral → `filter` + `toList()`.
  2. Lista de clientes sin repetir → `map` + `distinct` + `toList()`.
  3. Total de importe de todos los pedidos → `map` + `reduce` (o `mapToDouble` + `sum`).
  4. Solo pedidos PAGADOS, ordenados por importe descendente, los 5 primeros → `filter` + `sorted` + `limit` + `toList()`.
  5. Cantidad de pedidos pagados → `filter` + `count()`.
  6. ¿Hay algún pedido con importe > 100? → `anyMatch`.
  7. Primer pedido de un cliente dado → `filter` + `findFirst()`.

### 4.2 Proyecto de la carpeta

En **CH5-M1-U4-C4** está el proyecto **procesamiento-pedidos** con **enfoque funcional**: todos los métodos usan Streams y **solo** las operaciones listadas al inicio de esta guía (filter, map, sorted, distinct, limit, skip, forEach, toList, toSet, count, findFirst, findAny, reduce, anyMatch, allMatch, noneMatch).

- **model.Pedido:** id, cliente, importe, estado.
- **service.ProcesamientoPedidosService:** métodos que resuelven los puntos anteriores con pipelines.
- **ejemplos.EjemplosFuncionales:** más ejemplos (pares, cuadrados, suma, mayúsculas, contar) con Streams.
- **Main:** datos de prueba e invocación de los métodos; salida por consola.

Usa el proyecto como referencia de cómo escribir los pipelines con las operaciones permitidas.

---

## 5. De instrucción en lenguaje natural a pipeline

Con las operaciones permitidas puedes traducir:

- “Solo los pedidos pagados” → `filter(p -> "PAGADO".equals(p.getEstado()))`.
- “Los nombres de cliente” → `map(Pedido::getCliente)`.
- “Sin repetir” → `distinct()`.
- “Ordenados por importe de mayor a menor” → `sorted(Comparator.comparingDouble(Pedido::getImporte).reversed())`.
- “Los 5 primeros” → `limit(5)`.
- “Recoger en lista” → `toList()`.
- “Recoger en conjunto” → `collect(Collectors.toSet())`.
- “¿Cuántos?” → `count()`.
- “El primero que…” → `findFirst()`.
- “Sumar importes” → `reduce(0.0, Double::sum)` (sobre importes mapeados).
- “¿Hay alguno que…?” → `anyMatch(...)`.

---

## 6. Resumen

- El proyecto **procesamiento-pedidos** está en enfoque funcional y sirve como ejemplo de aplicación de estas operaciones.
- Diseña el flujo como: origen → filter → map → sorted/distinct/limit/skip → terminal (toList, count, reduce, etc.).
