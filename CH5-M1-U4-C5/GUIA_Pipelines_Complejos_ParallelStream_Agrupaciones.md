# Guía: Pipelines complejos, Streams paralelos y agrupaciones avanzadas

**Carpeta:** CH5-M1-U4-C5

---

## Objetivos de aprendizaje

- Implementar **pipelines funcionales más complejos** (agrupaciones, particiones, estadísticas).
- Ejecutar Streams de forma **paralela** para mejorar el rendimiento cuando sea adecuado.
- Aplicar **agrupación y estadísticas avanzadas** sobre colecciones (groupingBy, partitioningBy, summarizingInt).

---

## Contenidos

1. Agrupaciones y reducciones: `Collectors.groupingBy()`, `partitioningBy()`, `summarizingInt()`.
2. Transformaciones en cascada con múltiples `map` y `filter`.
3. Introducción a `parallelStream()`: ventajas, riesgos, cuándo utilizarlo.
4. Evaluación del costo con `System.nanoTime()` y comparativas.
5. Uso de herramientas para paralelizar y simplificar pipelines complejos.
6. Ejercicio práctico: base de datos simulada de ventas (total por producto, cliente con mayor facturación, productos por categoría).

---

# Parte 1. Agrupaciones y reducciones avanzadas

## 1.1 Collectors.groupingBy()

**Qué hace:** Agrupa los elementos del stream por una **clave** obtenida con una función clasificadora. El resultado es un `Map<K, List<T>>`.

**Formas:**

```java
// Agrupar en listas (valor por defecto)
Map<String, List<Venta>> porCliente = ventas.stream()
    .collect(Collectors.groupingBy(Venta::getCliente));

// Agrupar y aplicar un collector downstream (ej: suma, cuenta)
Map<String, Double> totalPorCliente = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::getCliente,
        Collectors.summingDouble(Venta::getTotal)
    ));

Map<String, Long> cantidadPorCliente = ventas.stream()
    .collect(Collectors.groupingBy(Venta::getCliente, Collectors.counting()));
```

**Para qué sirve:** Total por producto, ventas por cliente, pedidos por categoría, etc.

## 1.2 Collectors.partitioningBy()

**Qué hace:** Divide el stream en **dos grupos** según un `Predicate`: los que cumplen (true) y los que no (false). Resultado: `Map<Boolean, List<T>>`.

```java
Map<Boolean, List<Venta>> pagadasVsPendientes = ventas.stream()
    .collect(Collectors.partitioningBy(v -> "PAGADO".equals(v.getEstado())));

List<Venta> pagadas = pagadasVsPendientes.get(true);
List<Venta> pendientes = pagadasVsPendientes.get(false);
```

**Con downstream:** se puede combinar con counting, summingDouble, etc.

```java
Map<Boolean, Long> cantidadPagadasVsPendientes = ventas.stream()
    .collect(Collectors.partitioningBy(v -> "PAGADO".equals(v.getEstado()), Collectors.counting()));
```

**Cuándo usar:** Cuando la clasificación es binaria (sí/no, mayor/menor que umbral).

## 1.3 summarizingInt() / summarizingDouble() / summarizingLong()

**Qué hace:** Calcula en **un solo paso** varias estadísticas sobre un campo numérico: count, sum, min, max y average. El resultado es un objeto `IntSummaryStatistics` (o Double/Long).

```java
IntSummaryStatistics stats = ventas.stream()
    .collect(Collectors.summarizingInt(Venta::getCantidad));

System.out.println("Cantidad: " + stats.getCount());
System.out.println("Suma: " + stats.getSum());
System.out.println("Mín: " + stats.getMin());
System.out.println("Máx: " + stats.getMax());
System.out.println("Media: " + stats.getAverage());
```

**Por grupo:** se puede combinar con `groupingBy` para tener estadísticas por clave.

```java
Map<String, IntSummaryStatistics> statsPorProducto = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::getProducto,
        Collectors.summarizingInt(Venta::getCantidad)
    ));
```

---

# Parte 2. Transformaciones en cascada (múltiples map y filter)

## 2.1 Encadenar varios filter y map

Un pipeline puede tener **varios** `filter` y **varios** `map` en secuencia. Cada paso reduce o transforma el flujo.

**Ejemplo:** Ventas de un cliente, solo pagadas, quedarnos con el total de cada una, sumar y luego los que superan 100.

```java
double totalGrandes = ventas.stream()
    .filter(v -> "Ana".equals(v.getCliente()))
    .filter(v -> "PAGADO".equals(v.getEstado()))
    .mapToDouble(Venta::getTotal)
    .filter(total -> total > 100)
    .sum();
```

**Buenas prácticas:**
- **Orden:** filtrar primero (menos elementos que transformar), luego map. Si un filter puede aplicarse antes de un map costoso, hacerlo.
- **Legibilidad:** si el pipeline se hace largo, extraer a variables intermedias o a métodos con nombre claro.

## 2.2 Map anidados (map sobre resultados de map)

Cuando cada elemento debe transformarse en **varios** valores o en una estructura que a su vez se procesa:

```java
// Nombres de clientes que tienen al menos una venta > 50
List<String> clientesConVentaAlta = ventas.stream()
    .filter(v -> v.getTotal() > 50)
    .map(Venta::getCliente)
    .distinct()
    .toList();
```

Para “map y luego aplanar” se usa `flatMap` (tema avanzado); aquí con varios `map` y `filter` en cascada basta para la mayoría de casos.

---

# Parte 3. parallelStream(): ventajas, riesgos y cuándo usarlo

## 3.1 Qué es parallelStream()

`coleccion.parallelStream()` devuelve un stream que puede **repartir el trabajo** entre varios hilos (cores). La API es la misma que con `stream()`; solo cambia el origen.

```java
long count = ventas.parallelStream()
    .filter(v -> v.getTotal() > 100)
    .count();
```

## 3.2 Ventajas

- **Rendimiento:** en colecciones **grandes** y operaciones **costosas** (cálculos, I/O simulado), el tiempo puede reducirse al repartir la carga.
- **Sin código de hilos:** no hace falta crear `ExecutorService` ni gestionar `Future`; el framework reparte las tareas.

## 3.3 Riesgos y requisitos

- **Estado compartido:** no modificar variables externas dentro de lambdas (no usar acumuladores mutables compartidos). Las operaciones deben ser **sin efectos secundarios** o thread-safe.
- **Orden:** el orden de procesamiento no está garantizado; si necesitas orden determinista, no uses paralelo para eso o ordena al final.
- **Overhead:** crear y coordinar hilos tiene coste. En listas **pequeñas** (pocos miles de elementos) y operaciones **baratas**, `parallelStream()` puede ser **más lento** que `stream()`.
- **Estructuras no thread-safe:** no modificar la colección fuente ni estructuras compartidas desde dentro del pipeline.

## 3.4 Cuándo utilizarlo

| Usar parallelStream() | Evitar parallelStream() |
|------------------------|---------------------------|
| Muchos elementos (decenas o cientos de miles) | Pocos elementos (cientos, pocos miles) |
| Operación por elemento costosa | Operaciones muy ligeras (filter por campo, map simple) |
| Sin estado compartido mutable | Acumuladores externos, modificar listas/mapas |
| Tareas independientes (embarazosamente paralelas) | Orden estricto o dependencias entre elementos |

**Regla práctica:** medir con `System.nanoTime()` (o JMH) antes y después; si no hay mejora clara, quedarse con `stream()`.

---

# Parte 4. Evaluación del costo con System.nanoTime()

## 4.1 Cómo medir

```java
long inicio = System.nanoTime();
// ... pipeline con stream() o parallelStream()
long fin = System.nanoTime();
double segundos = (fin - inicio) / 1_000_000_000.0;
System.out.println("Tiempo: " + segundos + " s");
```

## 4.2 Comparativa stream() vs parallelStream()

- Ejecutar **varias veces** (por ejemplo 5–10) y quedarse con la mediana o el promedio para suavizar picos.
- **Calentar la JVM:** una o dos ejecuciones previas sin medir para que el JIT compile.
- Usar **listas grandes** (p. ej. 100_000 o 1_000_000 elementos) para que el paralelismo tenga sentido.
- Comparar **mismo pipeline** con `.stream()` y con `.parallelStream()`.

**Ejemplo de estructura:**

```java
List<Venta> muchasVentas = generarVentas(500_000);
// Calentamiento
muchasVentas.stream().filter(v -> v.getTotal() > 50).count();
// Medida stream
long t1 = System.nanoTime();
long c1 = muchasVentas.stream().filter(v -> v.getTotal() > 50).count();
long t2 = System.nanoTime();
// Medida parallelStream
long t3 = System.nanoTime();
long c2 = muchasVentas.parallelStream().filter(v -> v.getTotal() > 50).count();
long t4 = System.nanoTime();
System.out.println("Stream: " + (t2 - t1) / 1e6 + " ms");
System.out.println("Parallel: " + (t4 - t3) / 1e6 + " ms");
```

---

# Parte 5. Herramientas para paralelizar y simplificar pipelines

- **Paralelizar:** se puede pedir “reescribe este pipeline usando parallelStream() y explica cuándo compensa”. Revisar que no haya estado compartido ni modificaciones a colecciones.
- **Simplificar:** “refactoriza este pipeline de ventas: agrupa por cliente y calcula el total por cliente” → sugerencias con `groupingBy` y `summingDouble`. Revisar que el código generado use correctamente los collectors.
- **Estadísticas:** “dame count, sum, min, max de los totales de ventas” → `summarizingDouble`. Verificar que se use el tipo correcto (Int/Double/Long).
- Siempre **probar** con datos reales o simulados y **medir** si la paralelización aporta mejora.

---

# Parte 6. Ejercicio práctico: base de datos simulada de ventas

## 6.1 Enunciado

Con una lista de **ventas** (cada venta tiene: cliente, producto, categoría del producto, cantidad, precio unitario o total), resolver:

1. **Total por producto:** `Map<String, Double>` (nombre producto → suma de totales).
2. **Cliente con mayor facturación:** cliente cuya suma de totales de ventas sea la máxima.
3. **Agrupación de productos por categoría:** `Map<String, List<String>>` (categoría → lista de nombres de producto sin repetir).

## 6.2 Modelo de datos sugerido

```java
// Venta: cliente, producto, categoria, cantidad, precioUnitario (y getTotal() = cantidad * precioUnitario)
record Venta(String cliente, String producto, String categoria, int cantidad, double precioUnitario) {
    public double getTotal() {
        return cantidad * precioUnitario;
    }
}
```

## 6.3 Soluciones con Streams

**Total por producto:**

```java
Map<String, Double> totalPorProducto = ventas.stream()
    .collect(Collectors.groupingBy(Venta::getProducto, Collectors.summingDouble(Venta::getTotal)));
```

**Cliente con mayor facturación:**

```java
Map<String, Double> totalPorCliente = ventas.stream()
    .collect(Collectors.groupingBy(Venta::getCliente, Collectors.summingDouble(Venta::getTotal)));

String clienteMax = totalPorCliente.entrySet().stream()
    .max(Map.Entry.comparingByValue())
    .map(Map.Entry::getKey)
    .orElse("N/A");
```

**Productos por categoría (nombres sin repetir):**

```java
Map<String, List<String>> productosPorCategoria = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::getCategoria,
        Collectors.mapping(Venta::getProducto, Collectors.toCollection(() -> new TreeSet<>(Comparator.naturalOrder())))
    ))
    .entrySet().stream()
    .collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList<>(e.getValue())));
```

Versión más simple si no importa el orden y se aceptan duplicados en la lista (luego se puede hacer distinct):

```java
Map<String, List<String>> productosPorCategoria = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::getCategoria,
        Collectors.mapping(Venta::getProducto, Collectors.toList())
    ));
// Si se quieren sin repetir por categoría: toSet() o distinct() en un paso previo por categoría
```

Para **lista de productos únicos por categoría**:

```java
Map<String, List<String>> productosUnicosPorCategoria = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::getCategoria,
        Collectors.mapping(Venta::getProducto, Collectors.collectingAndThen(Collectors.toSet(), ArrayList::new))
    ));
```

## 6.4 Proyecto de apoyo

En la carpeta **CH5-M1-U4-C5** está el proyecto **ventas-simuladas** con:

- Modelo `Venta` (cliente, producto, categoría, cantidad, precio unitario).
- Datos de prueba generados en memoria.
- Clase de servicio o Main con los tres análisis anteriores (total por producto, cliente con mayor facturación, productos por categoría) y opcionalmente ejemplos con `partitioningBy` y `summarizingInt`.
- Opcional: método que mide con `System.nanoTime()` stream vs parallelStream sobre una lista grande.

Ejecutar el proyecto para ver los resultados; usar el código como referencia para agrupaciones y pipelines más complejos.

---

# Resumen

- **groupingBy:** agrupar por clave (listas o con downstream: counting, summingDouble, summarizingInt).
- **partitioningBy:** dividir en dos grupos (true/false) según un predicado.
- **summarizingInt/Double/Long:** estadísticas (count, sum, min, max, average) en un solo paso.
- **Pipelines en cascada:** varios filter y map; ordenar filtrando antes de transformar cuando sea posible.
- **parallelStream():** usar con muchas datos y operaciones costosas; evitar estado compartido y listas pequeñas.
- **Medir:** `System.nanoTime()` (o JMH) para comparar stream vs parallelStream y validar mejoras.
- **Ejercicio ventas:** total por producto (groupingBy + summingDouble), cliente con mayor facturación (groupingBy + max por valor), productos por categoría (groupingBy + mapping a lista o set).
