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

## 3. Laboratorio: procesamiento de pedidos

-En **CH5-M1-U4-C4** está el proyecto **procesamiento-pedidos** con **enfoque imperativo**

- Utiliza las operaciones listadas al inicio de esta guía (filter, map, sorted, distinct, limit, skip, forEach, toList, toSet, count, findFirst, findAny, reduce, anyMatch, allMatch, noneMatch), para diseñar el flujo como: origen → filter → map → sorted/distinct/limit/skip → terminal (toList, count, reduce, etc.). Por ultimo, cambia el **enfoque imperativo** por un **enfoque funcional**
