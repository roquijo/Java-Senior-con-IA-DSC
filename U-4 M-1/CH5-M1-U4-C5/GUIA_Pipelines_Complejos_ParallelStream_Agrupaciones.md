# Guía detallada: Pipelines complejos, Streams paralelos y agrupaciones avanzadas

**Carpeta:** CH5-M1-U4-C5  

---

## Objetivos de aprendizaje

- Implementar **pipelines funcionales más complejos** (agrupaciones, particiones, estadísticas).
- Ejecutar Streams de forma **paralela** para mejorar el rendimiento cuando sea adecuado.
- Aplicar **agrupación y estadísticas avanzadas** sobre colecciones.

---

## Contenidos

1. Agrupaciones y reducciones: `groupingBy()`, `partitioningBy()`, `summarizingInt()` (parámetros, ejemplos paso a paso).
2. Transformaciones en cascada con múltiples `map` y `filter`.
3. Introducción a `parallelStream()`: ventajas, riesgos, cuándo utilizarlo.
4. Evaluación del costo con `System.nanoTime()` y comparativas.
5. Sugerencias de herramientas para paralelizar y simplificar pipelines.
6. Ejercicio práctico: base de datos simulada de ventas.

---

# Parte 1. Agrupaciones y reducciones avanzadas

Antes de entrar en código, vamos a ver la **idea con plastilina**.

---

## La idea con plastilina: ¿qué es “agrupar”?

Imagina que tienes **muchas bolitas de plastilina** de colores (rojo, azul, verde) y quieres:

- **Solo separarlas por color:** pones una caja por color y en cada caja guardas la **lista** de bolitas de ese color.  
  Resultado: *Map&lt;Color, Lista de bolitas&gt;*.

- **Contar cuántas bolitas hay de cada color:** misma separación, pero en vez de guardar la lista guardas un **número** (cuántas hay).  
  Resultado: *Map&lt;Color, Número&gt;*.

- **Sumar el “peso” de las bolitas de cada color:** misma separación, pero en cada caja guardas la **suma** del peso de las bolitas de ese color.  
  Resultado: *Map&lt;Color, Suma&gt;*.

En Java, esa “separación por cajas” se hace con **collect** y unos ayudantes llamados **Collectors**. Los más útiles para agrupar son:

- **groupingBy:** “agrupa por esta regla y mete cada grupo en una caja”.
- **partitioningBy:** “solo hay dos cajas: la de los que cumplen la condición y la de los que no”.
- **summarizingInt / summarizingDouble:** “en una sola pasada dame cuenta, suma, mínimo, máximo y media”.

A partir de aquí vemos **cada función con detalle**: qué parámetros recibe, qué devuelve y ejemplos con datos pequeños para que puedas seguir el flujo.

---

## 1.1 Collectors.groupingBy()

### ¿Para qué sirve?

**groupingBy** sirve para **repartir todos los elementos del stream en grupos**. Cada grupo tiene una **clave** (por ejemplo: nombre de cliente, categoría, producto). Dentro de cada grupo puedes guardar una lista de elementos, o aplicar otra operación (contar, sumar, etc.).

### Analogía

Tienes muchas **fichas de ventas**. Quieres poner en **un montón** todas las ventas de "Ana", en **otro montón** las de "Luis", en **otro** las de "María", etc. Cada montón está etiquetado con el nombre del cliente. Eso es **agrupar por cliente**. Si en vez de guardar el montón entero solo quieres **cuántas fichas hay** en cada montón, o **cuánto suman** en total, lo indicas con un segundo parámetro (el “downstream collector”).

---

### Firma y parámetros (versión con un solo parámetro)

```text
Collectors.groupingBy(Function<? super T, ? extends K> classifier)
```

- **Parámetro único:**
  - **classifier** (clasificador): es una **Function**. Recibe cada elemento del stream (por ejemplo una `Venta`) y devuelve la **clave** por la que quieres agrupar (por ejemplo el nombre del cliente, tipo `String`).
  - En código suele ser un **método por referencia**: `Venta::cliente` significa “para cada venta, usa su método `cliente()` como clave”.

- **Qué devuelve:** un **Collector** que, al usarlo dentro de `.collect(...)`, produce un **Map&lt;K, List&lt;T&gt;&gt;**:
  - **K** = tipo de la clave (ej. `String` si agrupas por cliente).
  - **List&lt;T&gt;** = lista de elementos que cayeron en ese grupo (ej. lista de `Venta`).

**Resumen:** “Agrupa los elementos del stream usando la clave que devuelve `classifier`; en cada clave guarda una **lista** de elementos.”

---

### Ejemplo paso a paso (solo classifier)

Supongamos esta lista de ventas (simplificada):

| Cliente | Producto | Total |
|--------|----------|-------|
| Ana    | Laptop   | 899   |
| Luis   | Monitor  | 398   |
| Ana    | Teclado  | 49    |
| María  | Laptop   | 899   |

Código:

```java
Map<String, List<Venta>> porCliente = ventas.stream()
    .collect(Collectors.groupingBy(Venta::cliente));
```

- **Venta::cliente** es el clasificador: para cada `Venta` devuelve el `String` del cliente.
- El resultado será algo así:
  - clave `"Ana"`  → lista con 2 ventas (Laptop 899, Teclado 49)
  - clave `"Luis"` → lista con 1 venta (Monitor 398)
  - clave `"María"` → lista con 1 venta (Laptop 899)

**Tipo del resultado:** `Map<String, List<Venta>>`. Así puedes hacer `porCliente.get("Ana")` y obtienes la lista de ventas de Ana.

---

### Firma con dos parámetros (classifier + downstream)

```text
Collectors.groupingBy(
    Function<? super T, ? extends K> classifier,
    Collector<? super T, A, D> downstream
)
```

- **Primer parámetro — classifier:** igual que antes; define **por qué clave** agrupar.

- **Segundo parámetro — downstream:** es **otro Collector**. Indica qué hacer con los elementos que caen en **cada grupo**:
  - Si pones `Collectors.toList()` (por defecto), en cada grupo guardas una lista (como en la versión de un parámetro).
  - Si pones `Collectors.counting()`, en cada grupo guardas un **Long** (cuántos elementos hay).
  - Si pones `Collectors.summingDouble(Venta::getTotal)`, en cada grupo guardas un **Double** (la suma de los totales de esas ventas).

- **Qué devuelve:** un Collector que produce un **Map&lt;K, D&gt;** donde **D** es el tipo que produce el downstream (List, Long, Double, etc.).

---

### Ejemplo: total facturado por cliente

Queremos un mapa: *cliente → suma de totales de sus ventas*.

```java
Map<String, Double> totalPorCliente = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::cliente,                           // 1) Agrupa por cliente
        Collectors.summingDouble(Venta::getTotal) // 2) En cada grupo: suma los getTotal()
    ));
```

- **Parámetro 1 — classifier:** `Venta::cliente`. Agrupa por nombre de cliente.
- **Parámetro 2 — downstream:** `Collectors.summingDouble(Venta::getTotal)`. Para cada grupo, toma las ventas, aplica `getTotal()` a cada una y **suma** esos valores. El resultado por grupo es un `Double`.
- **Resultado:** `Map<String, Double>`. Por ejemplo: `"Ana" → 948.0`, `"Luis" → 398.0`, `"María" → 899.0`.

---

### Ejemplo: cantidad de ventas por cliente

Queremos: *cliente → cuántas ventas tiene*.

```java
Map<String, Long> cantidadPorCliente = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::cliente,      // Agrupa por cliente
        Collectors.counting() // En cada grupo: cuenta cuántos elementos hay
    ));
```

- **downstream:** `Collectors.counting()` no recibe parámetros; solo cuenta los elementos del grupo y devuelve un **Long**.
- **Resultado:** `Map<String, Long>`. Ejemplo: `"Ana" → 2`, `"Luis" → 1`, `"María" → 1`.

---

### Resumen de groupingBy

| Qué quieres                         | Collector downstream típico                    | Tipo del resultado          |
|------------------------------------|-----------------------------------------------|-----------------------------|
| Lista de elementos por clave       | (ninguno; por defecto es toList)              | `Map<K, List<T>>`           |
| Suma de un campo numérico por clave | `Collectors.summingDouble(Function)`          | `Map<K, Double>`            |
| Cantidad de elementos por clave    | `Collectors.counting()`                       | `Map<K, Long>`              |
| Estadísticas por clave             | `Collectors.summarizingInt(...)` (ver más abajo) | `Map<K, IntSummaryStatistics>` |

**Errores frecuentes:**

- Usar `getCliente()` en un **record**: en records los “getters” se llaman como el componente, es decir `cliente()`, no `getCliente()`. Si tu clase tiene método `getCliente()`, entonces sí usarías `Venta::getCliente`.
- Confundir el orden de los parámetros: primero siempre va el **clasificador** (por qué agrupar), luego el **downstream** (qué hacer con cada grupo).

---

## 1.2 Collectors.partitioningBy()

### ¿Para qué sirve?

**partitioningBy** divide el stream en **solo dos grupos**: los elementos que **cumplen** una condición (clave `true`) y los que **no** la cumplen (clave `false`). Es un caso particular de agrupación cuando la pregunta es de sí/no.

### Analogía

Tienes las mismas fichas de ventas y una pregunta: “¿Está pagada?”. Solo hay **dos cajas**: una para “Sí, pagada” y otra para “No, pendiente”. No hay más categorías. Eso es **partitioningBy**.

---

### Firma y parámetros (versión con un solo parámetro)

```text
Collectors.partitioningBy(Predicate<? super T> predicate)
```

- **Parámetro único:**
  - **predicate:** un **Predicate&lt;T&gt;**. Recibe cada elemento del stream y devuelve **true** o **false**. Los que dan `true` van al grupo `true`; los que dan `false`, al grupo `false`.
  - En código suele ser una lambda: `v -> v.getTotal() > 100` o `v -> "PAGADO".equals(v.getEstado())`.

- **Qué devuelve:** un Collector que produce un **Map&lt;Boolean, List&lt;T&gt;&gt;**:
  - `map.get(true)` → lista de elementos que cumplieron la condición.
  - `map.get(false)` → lista de elementos que no la cumplieron.

---

### Ejemplo: ventas mayores o menores que 100

```java
Map<Boolean, List<Venta>> grandesVsPequeñas = ventas.stream()
    .collect(Collectors.partitioningBy(v -> v.getTotal() > 100));
```

- **Parámetro:** `v -> v.getTotal() > 100`. Para cada venta, pregunta: “¿el total es mayor que 100?”.
- **Resultado:** 
  - `grandesVsPequeñas.get(true)`  → lista de ventas con total &gt; 100
  - `grandesVsPequeñas.get(false)` → lista de ventas con total ≤ 100

---

### Firma con dos parámetros (predicate + downstream)

```text
Collectors.partitioningBy(
    Predicate<? super T> predicate,
    Collector<? super T, A, D> downstream
)
```

- **Primer parámetro:** el **Predicate** que define los dos grupos (true / false).
- **Segundo parámetro:** un **Collector** que se aplica **a cada uno de los dos grupos** (igual que el downstream de groupingBy). Puede ser `Collectors.counting()`, `Collectors.summingDouble(...)`, etc.

**Resultado:** `Map<Boolean, D>`. Por ejemplo `Map<Boolean, Long>` si usas `counting()`.

---

### Ejemplo: cantidad de ventas mayores vs menores que 100

```java
Map<Boolean, Long> cantidadGrandesVsPequeñas = ventas.stream()
    .collect(Collectors.partitioningBy(
        v -> v.getTotal() > 100,  // 1) ¿Mayor que 100? → true/false
        Collectors.counting()     // 2) En cada grupo: cuenta cuántos hay
    ));
```

- **Resultado:** `cantidadGrandesVsPequeñas.get(true)` = cuántas ventas &gt; 100, `get(false)` = cuántas ≤ 100.

---

### Cuándo usar partitioningBy frente a groupingBy

- Usa **partitioningBy** cuando la clasificación sea **binaria**: sí/no, mayor/menor que un umbral, activo/inactivo, etc. Solo existen dos “cajas”.
- Usa **groupingBy** cuando haya **varias categorías** (varios clientes, varias categorías de producto, etc.).

---

## 1.3 summarizingInt() / summarizingDouble() / summarizingLong()

### ¿Para qué sirve?

Estos collectors calculan **en una sola pasada** varias estadísticas sobre un campo **numérico** del stream: **cuenta** (count), **suma** (sum), **mínimo** (min), **máximo** (max) y **media** (average). Así no tienes que hacer cinco recorridos (uno para count, otro para sum, etc.); el stream se recorre una vez y obtienes un objeto con todos los valores.

### Analogía

En vez de contar las bolitas, luego sumar sus pesos, luego buscar la más ligera y la más pesada por separado, haces **un solo recorrido** y anotas todo a la vez. Al final tienes un “resumen” con los cinco números.

---

### Firma (por ejemplo summarizingInt)

```text
Collectors.summarizingInt(ToIntFunction<? super T> mapper)
```

- **Parámetro único:**
  - **mapper:** un **ToIntFunction&lt;T&gt;**. Recibe cada elemento del stream y devuelve un **int** que es el valor del que quieres las estadísticas. Típicamente un método que devuelve int: `Venta::cantidad` (cantidad de unidades), o un getter que devuelve int.

- **Qué devuelve:** un Collector que produce un **IntSummaryStatistics**, que tiene métodos:
  - `getCount()`  → número de elementos (long)
  - `getSum()`    → suma (long)
  - `getMin()`    → mínimo (int)
  - `getMax()`    → máximo (int)
  - `getAverage()` → media (double)

Para **double** y **long** existen `summarizingDouble(ToDoubleFunction)` y `summarizingLong(ToLongFunction)`; devuelven `DoubleSummaryStatistics` y `LongSummaryStatistics` (con los mismos conceptos: count, sum, min, max, average).

---

### Ejemplo: estadísticas de la cantidad de unidades vendidas

```java
IntSummaryStatistics stats = ventas.stream()
    .collect(Collectors.summarizingInt(Venta::cantidad));

System.out.println("Cantidad de ventas: " + stats.getCount());
System.out.println("Total unidades vendidas: " + stats.getSum());
System.out.println("Mínimo en una venta: " + stats.getMin());
System.out.println("Máximo en una venta: " + stats.getMax());
System.out.println("Media de unidades por venta: " + stats.getAverage());
```

- **Parámetro:** `Venta::cantidad`. Para cada venta toma el campo `cantidad` (int).
- **Resultado:** un solo objeto con count, sum, min, max y average calculados en una pasada.

---

### Combinado con groupingBy: estadísticas por categoría

Si quieres las **mismas estadísticas pero por grupo** (por ejemplo por categoría de producto), usas **groupingBy** y como downstream pones **summarizingInt** (o Double/Long):

```java
Map<String, IntSummaryStatistics> statsPorCategoria = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::categoria,                    // Agrupa por categoría
        Collectors.summarizingInt(Venta::cantidad)  // En cada grupo: estadísticas de cantidad
    ));
```

- **Resultado:** `Map<String, IntSummaryStatistics>`. Para cada categoría tienes su count, sum, min, max y average de `cantidad`.

---

### Resumen de summarizingXxx

| Collector              | Parámetro (tipo)     | Tipo del resultado        |
|------------------------|----------------------|----------------------------|
| summarizingInt(...)    | ToIntFunction&lt;T&gt;   | IntSummaryStatistics       |
| summarizingDouble(...) | ToDoubleFunction&lt;T&gt; | DoubleSummaryStatistics    |
| summarizingLong(...)   | ToLongFunction&lt;T&gt;   | LongSummaryStatistics      |

**Cuidado:** el campo del que haces el resumen debe ser del tipo correcto (int para summarizingInt, double para summarizingDouble, long para summarizingLong). Si tu campo es `double` (por ejemplo `getTotal()`), usa `summarizingDouble`.

---

# Parte 2. Transformaciones en cascada (múltiples map y filter)

## Idea con plastilina

Un **pipeline** es como una cadena de filtros y transformaciones. Primero pasas por un **filtro** (solo pasan los que cumplen), luego por **otro filtro**, luego **cambias** cada elemento (map), luego tal vez otro filtro, y al final **recoges** el resultado (toList, sum, etc.). Cada paso recibe lo que salió del anterior. Por eso el **orden** importa: filtrar antes de transformar suele ser más barato porque trabajas con menos elementos.

---

## 2.1 Encadenar varios filter y map

Puedes poner **varios** `filter` seguidos y **varios** `map` (o `mapToDouble`, etc.). Cada operación se aplica al resultado de la anterior.

### Orden típico recomendado

1. **Origen:** `lista.stream()`.
2. **Filtros:** uno o más `filter(...)` para reducir la cantidad de elementos.
3. **Transformaciones:** uno o más `map(...)` para cambiar cada elemento (o extraer un campo).
4. **Orden / duplicados / límites:** `sorted()`, `distinct()`, `limit(n)`, `skip(n)` si los necesitas.
5. **Operación final:** `collect(...)`, `toList()`, `sum()`, `count()`, `reduce(...)`, etc.

### Ejemplo detallado

Objetivo: “Del cliente Ana, solo ventas pagadas; de esas, quedarme con el total de cada una; de esos totales, solo los mayores que 100; y sumarlos.”

```java
double totalGrandes = ventas.stream()
    .filter(v -> "Ana".equals(v.cliente()))           // 1) Solo ventas de Ana
    .filter(v -> "PAGADO".equals(v.getEstado()))      // 2) Solo pagadas (si tienes estado)
    .mapToDouble(Venta::getTotal)                     // 3) De cada venta, tomar el total (ahora es stream de double)
    .filter(total -> total > 100)                      // 4) Solo totales > 100
    .sum();                                           // 5) Sumar
```

- **Parámetros de filter:** cada `filter` recibe un **Predicate&lt;T&gt;**: una función que devuelve true/false. Solo pasan los que dan true.
- **Parámetros de mapToDouble:** una **ToDoubleFunction&lt;T&gt;**: de cada venta devuelves un double (aquí `getTotal()`).
- Después del primer `mapToDouble` el stream es de tipo **DoubleStream**; por eso el siguiente `filter` recibe un `double` (el total).

### Por qué filtrar antes de map

Si primero filtras, menos elementos pasan al `map`. Si el `map` hace algo costoso, es mejor que se aplique solo a los elementos que ya cumplen el filtro. Por eso: **primero filters, luego maps** cuando sea posible.

---

## 2.2 Varios map y extracción de campos

Cuando quieres **solo un dato** de cada elemento (por ejemplo el nombre del cliente), usas `map` con ese getter. Si después quieres eliminar repetidos, usas `distinct()`.

Ejemplo: “Nombres de clientes que tienen al menos una venta con total mayor que 50, sin repetir.”

```java
List<String> clientesConVentaAlta = ventas.stream()
    .filter(v -> v.getTotal() > 50)   // Solo ventas > 50
    .map(Venta::cliente)              // De cada venta, el nombre del cliente
    .distinct()                       // Sin repetir nombres
    .toList();
```

- **map(Venta::cliente):** el stream pasa de `Stream<Venta>` a `Stream<String>`. Cada elemento es ya solo el nombre del cliente.
- **distinct():** elimina duplicados según `equals` del tipo (aquí String).

---

# Parte 3. parallelStream(): ventajas, riesgos y cuándo usarlo

## 3.1 Qué es parallelStream() — idea con plastilina

Imagina que tienes que **contar** miles de bolitas. Puedes hacerlo tú solo (recorrer todas) o repartir las bolitas entre **varios amigos**: cada uno cuenta su montón y al final sumas los resultados. **parallelStream()** hace algo así: reparte el trabajo entre varios **hilos** (workers) para que se ejecute en paralelo. La API es la misma que `stream()`; solo cambias `stream()` por `parallelStream()`.

---

## 3.2 Cómo se usa

En vez de:

```java
ventas.stream().filter(...).count()
```

escribes:

```java
ventas.parallelStream().filter(...).count()
```

Los parámetros de `filter`, `map`, etc. son **exactamente los mismos**. Solo cambia que el trabajo puede repartirse entre varios núcleos de la CPU.

---

## 3.3 Ventajas

- Con **muchos datos** y operaciones **algo costosas**, el tiempo total puede **bajar** porque varios núcleos trabajan a la vez.
- No tienes que crear hilos a mano ni usar `ExecutorService`; el framework se encarga de repartir las tareas.

---

## 3.4 Riesgos y qué no hacer

- **No modificar variables “de fuera” dentro del pipeline.**  
  Ejemplo de error:
  ```java
  int[] total = {0};
  ventas.parallelStream().forEach(v -> total[0] += v.getTotal()); // MAL: varios hilos escribiendo total[0]
  ```
  Varios hilos leyendo y escribiendo la misma variable puede dar resultados incorrectos. Las operaciones deben ser **sin efectos secundarios** sobre variables compartidas, o usar estructuras thread-safe si realmente necesitas acumular.

- **El orden de procesamiento no está garantizado.** Si necesitas un orden concreto (por ejemplo por fecha), no dependas del orden en que se procesan los elementos; si hace falta, ordena al final con `sorted()`.

- **Overhead:** crear y coordinar hilos tiene coste. Si la lista es **pequeña** (pocos miles de elementos) y la operación es **muy simple** (un filter o un map trivial), `parallelStream()` puede ser **más lento** que `stream()`.

- **No modificar la colección fuente** ni otras estructuras compartidas mientras se ejecuta el stream (ni con stream() ni con parallelStream()).

---

## 3.5 Cuándo usar parallelStream()

| Conviene usar parallelStream()      | Mejor quedarse con stream()           |
|-------------------------------------|----------------------------------------|
| Muchos elementos (decenas/miles)    | Pocos elementos (cientos)              |
| Operación por elemento costosa      | Operaciones muy ligeras                |
| Sin variables externas modificables | Acumuladores externos, modificar listas |
| Tareas independientes por elemento  | Orden estricto o dependencias entre elementos |

**Regla práctica:** si dudas, **mide** con `System.nanoTime()` (o varias ejecuciones) y compara. Si no hay mejora clara, usa `stream()`.

---

# Parte 4. Evaluación del costo con System.nanoTime()

## 4.1 Qué es nanoTime()

`System.nanoTime()` devuelve un **long** con un valor en **nanosegundos** (1 nanosegundo = 0,000000001 segundos). No es un “reloj del mundo real”, sino un cronómetro para **medir intervalos**: tomas el valor antes de ejecutar el código y después; la diferencia es el tiempo (en ns) que tardó.

---

## 4.2 Cómo medir un bloque de código

```java
long inicio = System.nanoTime();
// Aquí va tu pipeline, por ejemplo:
double suma = ventas.stream().mapToDouble(Venta::getTotal).sum();
long fin = System.nanoTime();

long nanosegundos = fin - inicio;
double segundos = nanosegundos / 1_000_000_000.0;
double milisegundos = nanosegundos / 1_000_000.0;

System.out.println("Tiempo: " + milisegundos + " ms");
```

- **inicio** y **fin** son instantes en nanosegundos. **fin - inicio** = duración en ns.
- Para leerlo en segundos: dividir entre 1_000_000_000.  
  Para milisegundos: dividir entre 1_000_000.

---

## 4.3 Comparar stream() y parallelStream()

- Ejecuta el **mismo** pipeline varias veces (por ejemplo 5) y calcula la media (o usa la mediana) para suavizar picos.
- Opcional: una o dos ejecuciones “en caliente” sin medir, para que la JVM compile el código (JIT).
- Usa una **lista grande** (por ejemplo 100_000 o 500_000 elementos) para que el paralelismo tenga sentido.
- Mide por separado:
  - con `lista.stream()...`
  - con `lista.parallelStream()...`

Ejemplo de estructura:

```java
List<Venta> muchasVentas = DatosVentas.generar(500_000);
int repeticiones = 5;

// Calentamiento (opcional)
muchasVentas.stream().filter(v -> v.getTotal() > 50).count();

long tiempoStream = 0;
for (int i = 0; i < repeticiones; i++) {
    long t1 = System.nanoTime();
    muchasVentas.stream().filter(v -> v.getTotal() > 50).mapToDouble(Venta::getTotal).sum();
    long t2 = System.nanoTime();
    tiempoStream += (t2 - t1) / 1_000_000; // en ms
}
System.out.println("stream() media: " + (tiempoStream / repeticiones) + " ms");

long tiempoParallel = 0;
for (int i = 0; i < repeticiones; i++) {
    long t1 = System.nanoTime();
    muchasVentas.parallelStream().filter(v -> v.getTotal() > 50).mapToDouble(Venta::getTotal).sum();
    long t2 = System.nanoTime();
    tiempoParallel += (t2 - t1) / 1_000_000;
}
System.out.println("parallelStream() media: " + (tiempoParallel / repeticiones) + " ms");
```

Así ves si en tu máquina y con tus datos el paralelo realmente mejora.

---

# Parte 5. Sugerencias de herramientas para paralelizar y simplificar pipelines

- **Paralelizar:** puedes pedir “reescribe este pipeline usando parallelStream() y dime cuándo compensa”. Revisa que no se modifiquen variables externas ni colecciones compartidas.
- **Simplificar:** “refactoriza: agrupa por cliente y calcula el total por cliente” → suelen sugerir `groupingBy` + `summingDouble`. Comprueba que los parámetros (classifier y downstream) sean los correctos.
- **Estadísticas:** “dame count, sum, min, max de los totales” → `summarizingDouble`. Verifica que uses el tipo correcto (Int/Double/Long) según el tipo del campo.
- Siempre **probar** con datos reales o simulados y **medir** si la paralelización aporta mejora en tu caso.

---

# Parte 6. Ejercicio práctico: base de datos simulada de ventas

## 6.1 Enunciado

Tienes una lista de **ventas**. Cada venta tiene: cliente, producto, categoría del producto, cantidad, precio unitario (y puedes calcular el total como cantidad × precio unitario). Debes implementar:

1. **Total por producto:** un mapa donde la clave es el nombre del producto y el valor es la **suma** de los totales de todas las ventas de ese producto.
2. **Cliente con mayor facturación:** el cliente cuya **suma** de totales de todas sus ventas sea la **máxima**.
3. **Productos por categoría:** un mapa donde la clave es la categoría y el valor es la **lista de nombres de producto** que aparecen en esa categoría **sin repetir**.

---

## 6.2 Modelo de datos

Puedes usar un record como este (los “getters” de un record se llaman como el componente: `cliente()`, `producto()`, etc.):

```java
public record Venta(String cliente, String producto, String categoria, int cantidad, double precioUnitario) {
    public double getTotal() {
        return cantidad * precioUnitario;
    }
}
```

---

## 6.3 Solución 1: Total por producto

**Qué queremos:** `Map<String, Double>` → para cada producto, la suma de `getTotal()` de todas las ventas de ese producto.

**Collector:** Agrupar por **producto** (classifier) y en cada grupo **sumar** los totales (downstream: summingDouble).

```java
Map<String, Double> totalPorProducto = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::producto,                      // Clave: nombre del producto
        Collectors.summingDouble(Venta::getTotal)  // Valor: suma de getTotal() en ese grupo
    ));
```

- **groupingBy** primer parámetro: `Venta::producto` → cada venta se clasifica por su nombre de producto.
- **groupingBy** segundo parámetro: `Collectors.summingDouble(Venta::getTotal)` → para cada grupo, se aplica getTotal() a cada venta y se suman esos doubles.

---

## 6.4 Solución 2: Cliente con mayor facturación

**Qué queremos:** primero la suma de totales por cliente; luego el cliente cuya suma sea la mayor.

**Paso 1:** Total por cliente (igual que antes pero agrupando por cliente):

```java
Map<String, Double> totalPorCliente = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::cliente,
        Collectors.summingDouble(Venta::getTotal)
    ));
```

**Paso 2:** Del mapa, tomar la entrada (clave-valor) con el valor máximo. `entrySet()` da el conjunto de parejas (cliente, total). Sobre ese stream buscamos el max comparando por valor y nos quedamos con la clave:

```java
String clienteConMayorFacturacion = totalPorCliente.entrySet().stream()
    .max(Map.Entry.comparingByValue())   // Compara por el valor (el total)
    .map(Map.Entry::getKey)              // Nos quedamos con la clave (nombre cliente)
    .orElse("N/A");                      // Si el mapa estaba vacío
```

- **Map.Entry.comparingByValue()** devuelve un Comparator que ordena las entradas por su valor (el Double).  
- **max(...)** devuelve un `Optional<Map.Entry<String, Double>>`.  
- **map(Map.Entry::getKey)** pasa de la entrada a solo la clave (el String del cliente).  
- **orElse("N/A")** por si no hay ninguna venta.

---

## 6.5 Solución 3: Productos por categoría (nombres únicos)

**Qué queremos:** `Map<String, List<String>>` → categoría → lista de nombres de producto **sin repetir**.

**Idea:** Agrupar por categoría. Dentro de cada grupo no queremos la lista de ventas, sino la lista de **nombres de producto** y sin duplicados. Para eso usamos **mapping**: primero transformamos cada venta en su producto (String), y luego aplicamos un collector que junta esos strings en una colección sin repetir. Un modo sencillo es usar **toSet()** y luego convertir a lista si hace falta:

```java
Map<String, List<String>> productosPorCategoria = ventas.stream()
    .collect(Collectors.groupingBy(
        Venta::categoria,
        Collectors.mapping(
            Venta::producto,
            Collectors.collectingAndThen(Collectors.toSet(), ArrayList::new)
        )
    ));
```

- **groupingBy(Venta::categoria)** → agrupa por categoría.
- **Collectors.mapping(Venta::producto, ...)** → dentro de cada grupo, transforma cada venta en su nombre de producto (String).
- **Collectors.collectingAndThen(Collectors.toSet(), ArrayList::new)** → primero recoge esos nombres en un **Set** (sin repetir) y luego transforma ese Set en **ArrayList** (para tener List como pide el enunciado).

Si te conformas con `Map<String, Set<String>>`, puedes dejar solo:

```java
Collectors.mapping(Venta::producto, Collectors.toSet())
```

y el tipo será `Map<String, Set<String>>`.

---

## 6.6 Proyecto de apoyo

En **CH5-M1-U4-C5** está el proyecto **ventas-simuladas** con:

- El record **Venta** y datos de ejemplo (**DatosVentas**).
- **AnalisisVentasService** con los tres análisis anteriores y ejemplos de partitioningBy, summarizingInt y comparativa stream vs parallelStream.
- **Main** que imprime todos los resultados.

Puedes ejecutarlo con:

```bash
cd ventas-simuladas
mvn compile exec:java
```

y usar el código como referencia para repasar parámetros y uso de cada collector.

---

# Resumen rápido

- **groupingBy(classifier)** → agrupa por clave y guarda listas.  
  **groupingBy(classifier, downstream)** → agrupa y en cada grupo aplica otro collector (counting, summingDouble, summarizingInt, etc.).  
  Parámetros: 1) por qué agrupar, 2) qué hacer con cada grupo.

- **partitioningBy(predicate)** → dos grupos: true / false.  
  **partitioningBy(predicate, downstream)** → lo mismo pero aplicando un collector en cada grupo.

- **summarizingInt(mapper)** (y Double/Long) → en una pasada obtienes count, sum, min, max, average del campo que devuelve mapper.

- **Pipelines en cascada:** varios filter y map; conviene filtrar primero y luego transformar.

- **parallelStream():** mismo API que stream(); usar con muchos datos y sin modificar estado compartido; medir para comprobar mejora.

- **System.nanoTime():** tomar valor antes y después del código; la diferencia es el tiempo en nanosegundos; dividir entre 1_000_000 para ms.

- **Ejercicio ventas:** total por producto (groupingBy + summingDouble), cliente con mayor facturación (groupingBy por cliente + max por valor en entrySet), productos por categoría (groupingBy + mapping a producto + toSet o collectingAndThen toSet → ArrayList).
