# Guía detallada: Consolidación profesional de Streams y escenarios empresariales

**Carpeta:** CH5-M1-U4-C6  
**Para:** Consolidar el uso de Streams en análisis de datos y escenarios reales (registros bancarios, reportes mensuales, diagnóstico y refactorización).

---

## Objetivos de la clase-tutoría

- **Consolidar** el uso profesional de Streams para análisis de datos.
- **Aplicar** paralelismo y agrupación para resolver escenarios empresariales.
- **Actividades:**
  1. Análisis guiado: flujo de datos en una aplicación que procesa registros bancarios.
  2. Taller: sistema de reporte mensual con agrupación por fechas y cálculos estadísticos.
  3. Diagnóstico de cuellos de botella y puntos de mejora sugeridos.
  4. Refactorización de proyectos previos aplicando Streams para mayor claridad y rendimiento.

---

## Contenidos de esta guía

1. **Parte 0 — Punto de partida:** qué se asume que ya sabes y qué vamos a usar.
2. **Parte 1 — Flujo de datos en una app de registros bancarios:** desde el origen hasta los resultados, paso a paso.
3. **Parte 2 — Taller: reporte mensual** con agrupación por fechas y estadísticas.
4. **Parte 3 — Diagnóstico de cuellos de botella** y mejoras sugeridas.
5. **Parte 4 — Refactorización** de código previo con Streams.

---

# Parte 0. Punto de partida: qué necesitas saber

Para seguir esta guía sin saltar ningún aspecto clave, conviene tener claros estos conceptos. Si alguno te suena nuevo, revísalo en las guías C3, C4 o C5 antes de continuar.

---

## 0.1 Conceptos que se dan por sabidos

| Concepto | Breve recordatorio |
|----------|--------------------|
| **Stream** | Secuencia de elementos sobre la que se aplican operaciones en cadena. No almacena datos; fluyen desde un origen (lista, set, etc.) hasta una operación final. |
| **Operaciones intermedias** | `filter`, `map`, `sorted`, `distinct`, `limit`, `skip`. No ejecutan el flujo; solo definen la tubería. |
| **Operación terminal** | `collect`, `toList()`, `count()`, `reduce()`, `forEach()`, etc. Son las que “encienden” el stream y producen un resultado (o efecto). |
| **Collector** | Objeto que indica cómo “recoger” los elementos del stream en una estructura (lista, mapa, número, etc.). Se usa dentro de `collect(Collectors.xxx)`. |
| **groupingBy** | Agrupa elementos por una clave (p. ej. por cliente, por categoría). Parámetros: clasificador y opcionalmente un “downstream” (qué hacer con cada grupo: toList, counting, summingDouble, etc.). |
| **partitioningBy** | Divide en dos grupos según un predicado (true/false). |
| **summarizingInt / summarizingDouble** | Calcula en una pasada: count, sum, min, max, average sobre un campo numérico. |
| **parallelStream()** | Igual que `stream()` pero el trabajo puede repartirse entre varios hilos. Solo usarlo cuando hay muchos datos y sin modificar estado compartido; medir antes. |

---

## 0.2 Qué vamos a aplicar en esta clase

- **Flujo de datos:** de dónde salen los datos (lista de registros bancarios), qué filtros y transformaciones se aplican, y qué resultado final se obtiene (resúmenes, totales, reportes).
- **Agrupación por fechas:** agrupar movimientos por mes (o por día) para reportes mensuales y estadísticas por periodo.
- **Cálculos estadísticos:** totales, promedios, conteos, mínimos y máximos por grupo, usando collectors ya vistos.
- **Paralelismo:** cuándo tiene sentido usar `parallelStream()` en estos escenarios y cómo medir.
- **Cuellos de botella:** qué partes del código suelen ser lentas o poco claras y cómo mejorarlas.
- **Refactorización:** pasar de bucles imperativos a pipelines declarativos para ganar claridad y, en algunos casos, rendimiento.

---

## 0.3 Modelo de datos que usaremos en los ejemplos

En las Partes 1 y 2 trabajaremos con **registros bancarios** (movimientos). Cada registro tendrá al menos:

- **Identificador** del movimiento.
- **Cuenta** (número o código de cuenta).
- **Fecha** del movimiento (LocalDate o similar).
- **Tipo** (INGRESO, RETIRO, TRANSFERENCIA, etc.).
- **Importe** (cantidad positiva o negativa según el tipo).
- **Descripción** (opcional).

En código podría ser un record o una clase, por ejemplo:

```java
public record MovimientoBancario(
    String id,
    String cuenta,
    LocalDate fecha,
    String tipo,       // "INGRESO", "RETIRO", "TRANSFERENCIA"
    double importe,
    String descripcion
) {}
```

Con esto ya podemos describir el flujo de datos y el taller de reportes sin saltar pasos.

---

# Parte 1. Análisis guiado: flujo de datos en una app de registros bancarios

Esta parte explica **desde cero** cómo fluyen los datos en una aplicación que procesa registros bancarios: origen, filtros, agrupaciones y resultados. No se salta ningún aspecto clave del flujo.

---

## 1.1 Objetivo del análisis

Queremos una aplicación que:

1. **Cargue** una lista de movimientos bancarios (en memoria o desde un origen simulado).
2. **Filtre** movimientos según criterios (por cuenta, por tipo, por rango de fechas).
3. **Agrupe** por cuenta, por tipo o por periodo (mes/año) según lo que pida el negocio.
4. **Calcule** totales, cantidades y estadísticas por grupo.
5. **Exponga** esos resultados para mostrarlos en pantalla, enviarlos a un reporte o guardarlos.

Todo esto lo haremos con Streams para que el flujo sea declarativo y fácil de seguir.

---

## 1.2 Origen de los datos (paso 0)

Los datos **entran** al sistema desde algún sitio. En nuestro caso de estudio:

- **En memoria:** una `List<MovimientoBancario>` que puede venir de un servicio, un repositorio o datos de prueba.
- En un sistema real podría ser: base de datos, fichero CSV, API REST, etc. En todos los casos, al final tendrás una **colección** (o un stream) de movimientos.

Ejemplo de lista de entrada (conceptual):

```text
movimiento1: cuenta "ES12...", fecha 2024-01-15, tipo INGRESO, importe 500.0
movimiento2: cuenta "ES12...", fecha 2024-01-20, tipo RETIRO, importe -100.0
movimiento3: cuenta "ES99...", fecha 2024-02-01, tipo INGRESO, importe 1200.0
...
```

No aplicamos todavía ningún Stream; solo tenemos la **fuente** de datos.

---

## 1.3 Flujo paso a paso: qué hace cada etapa

El flujo típico en una app que procesa registros bancarios con Streams sigue esta estructura. Cada etapa es un paso que no debemos saltarnos si queremos entender todo.

---

### Paso 1 — Obtener el stream desde la fuente

- **Qué hacemos:** Convertir la colección de movimientos en un `Stream<MovimientoBancario>`.
- **Cómo:** `lista.stream()` (o `lista.parallelStream()` si más adelante decidimos paralelizar y medimos).
- **Resultado:** Un stream “en bruto” con todos los elementos. Aún no hemos filtrado ni agrupado nada.

```java
Stream<MovimientoBancario> flujo = movimientos.stream();
```

En la práctica no suele guardarse en variable; se encadena directamente: `movimientos.stream().filter(...)...`.

---

### Paso 2 — Filtrar (opcional pero muy común)

- **Qué hacemos:** Quedarnos solo con los movimientos que cumplan ciertas condiciones (por ejemplo una cuenta concreta, un rango de fechas, un tipo de operación).
- **Por qué:** Reducir el volumen de datos antes de agrupar o calcular; así las siguientes etapas trabajan solo con lo relevante.
- **Cómo:** uno o varios `filter(predicado)`.

Ejemplos de predicados:

- Por cuenta: `m -> "ES121234567890".equals(m.cuenta())`
- Por tipo: `m -> "INGRESO".equals(m.tipo())`
- Por rango de fechas: `m -> !m.fecha().isBefore(desde) && !m.fecha().isAfter(hasta)`

Código de ejemplo (solo movimientos de una cuenta en un rango de fechas):

```java
LocalDate desde = LocalDate.of(2024, 1, 1);
LocalDate hasta = LocalDate.of(2024, 12, 31);
String cuentaCliente = "ES121234567890";

List<MovimientoBancario> filtrados = movimientos.stream()
    .filter(m -> cuentaCliente.equals(m.cuenta()))
    .filter(m -> !m.fecha().isBefore(desde) && !m.fecha().isAfter(hasta))
    .toList();
```

**Resultado de esta etapa:** una lista (o stream) de movimientos ya filtrados. El siguiente paso (agrupar o calcular) trabajará solo con estos.

---

### Paso 3 — Agrupar según el criterio de negocio

- **Qué hacemos:** Repartir los movimientos en grupos. Cada grupo tiene una **clave** (por ejemplo: cuenta, tipo, o “mes-año” para reportes mensuales).
- **Cómo:** `collect(Collectors.groupingBy(clasificador))` o `groupingBy(clasificador, downstream)` si en cada grupo queremos un número o una estadística en vez de la lista.

**Clasificador por cuenta:**

- `MovimientoBancario::cuenta` → agrupa por número de cuenta.  
  Resultado: `Map<String, List<MovimientoBancario>>`.

**Clasificador por tipo:**

- `MovimientoBancario::tipo` → agrupa por INGRESO, RETIRO, etc.  
  Resultado: `Map<String, List<MovimientoBancario>>`.

**Clasificador por mes (para reportes mensuales):**

- Necesitamos una clave “mes-año”. Por ejemplo un `YearMonth` desde la fecha del movimiento:
  - `m -> YearMonth.from(m.fecha())`  
  Resultado: `Map<YearMonth, List<MovimientoBancario>>`.

Ejemplo: total de importes por cuenta (aquí ya combinamos agrupación y reducción):

```java
Map<String, Double> totalPorCuenta = movimientos.stream()
    .filter(m -> !m.fecha().isBefore(desde) && !m.fecha().isAfter(hasta))
    .collect(Collectors.groupingBy(
        MovimientoBancario::cuenta,
        Collectors.summingDouble(MovimientoBancario::importe)
    ));
```

- **Entrada:** stream de movimientos (posiblemente filtrado por fecha).
- **groupingBy(cuenta):** reparte por cuenta.
- **summingDouble(importe):** en cada grupo suma el campo `importe`.
- **Salida:** `Map<String, Double>` → cuenta → suma de importes.

No saltamos el paso de “qué clave uso” (cuenta, tipo, YearMonth): eso define la forma del resultado y del reporte.

---

### Paso 4 — Calcular totales o estadísticas por grupo

- **Qué hacemos:** En cada grupo no queremos la lista cruda, sino un **resumen**: suma, cantidad, media, etc.
- **Cómo:** usando el segundo parámetro de `groupingBy`: el **downstream collector**.

Ejemplos ya vistos en C5 aplicados aquí:

- **Total por grupo:** `Collectors.summingDouble(MovimientoBancario::importe)`.
- **Cantidad por grupo:** `Collectors.counting()`.
- **Estadísticas por grupo:** `Collectors.summarizingDouble(MovimientoBancario::importe)` → en cada grupo tienes count, sum, min, max, average.

Ejemplo: por cada tipo de movimiento, total y cantidad:

```java
Map<String, Double> totalPorTipo = movimientos.stream()
    .collect(Collectors.groupingBy(
        MovimientoBancario::tipo,
        Collectors.summingDouble(MovimientoBancario::importe)
    ));

Map<String, Long> cantidadPorTipo = movimientos.stream()
    .collect(Collectors.groupingBy(
        MovimientoBancario::tipo,
        Collectors.counting()
    ));
```

Paso clave: **elegir bien el downstream** (summingDouble, counting, summarizingDouble) según lo que el negocio pida (total, cantidad, estadísticas).

---

### Paso 5 — Consumir el resultado (salida del flujo)

- **Qué hacemos:** Usar el resultado del `collect` para mostrarlo, enviarlo a un reporte, escribirlo en fichero, etc.
- **Forma del resultado:** según lo que hayamos colectado: un `Map<K, Double>`, `Map<K, Long>`, `Map<YearMonth, List<...>>`, etc.

Ejemplo de “consumo” en consola:

```java
totalPorCuenta.forEach((cuenta, total) ->
    System.out.println("Cuenta " + cuenta + ": " + total));
```

O construir un DTO o un objeto de reporte a partir del mapa y devolverlo al usuario o a otra capa.

---

## 1.4 Resumen del flujo (sin saltar nada)

| Paso | Qué hacemos | Cómo (con Streams) | Resultado típico |
|------|-------------|---------------------|------------------|
| 0 | Tener los datos | Lista o colección de movimientos | `List<MovimientoBancario>` |
| 1 | Abrir el flujo | `.stream()` | `Stream<MovimientoBancario>` |
| 2 | Filtrar | `.filter(...)` (cuenta, fechas, tipo) | Menos elementos en el stream |
| 3 | Agrupar | `.collect(groupingBy(clasificador))` | Map con listas por grupo |
| 4 | Reducir por grupo | `groupingBy(clasificador, downstream)` | Map con totales/cantidades/estadísticas |
| 5 | Usar el resultado | `.forEach`, construir reporte, etc. | Salida para usuario o sistema |

Con esto tienes el flujo completo de datos en una app que procesa registros bancarios, desde el origen hasta el resultado final, sin omitir ninguna etapa.

**Proyecto de apoyo:** en CH5-M1-U4-C6 el proyecto **registros-bancarios-reportes** implementa este flujo: `ProcesamientoRegistrosService` (total por cuenta, por tipo, cantidad por tipo, filtro por cuenta y rango) y `Main` que lo ejecuta con datos de ejemplo.

---

# Parte 2. Taller: sistema de reporte mensual (agrupación por fechas y estadísticas)

En este taller construimos un **reporte mensual** sobre los mismos movimientos bancarios: agrupamos por mes y calculamos estadísticas por periodo. Todo desde cero y paso a paso.

---

## 2.1 Objetivo del taller

- Agrupar movimientos por **mes** (y año).
- Para cada mes: **total de importes**, **cantidad de movimientos**, **promedio**, **mínimo y máximo** (y si hace falta, desglose por tipo).
- Opcional: filtrar por cuenta o por rango de meses.

No se salta el “cómo” de la clave de agrupación (mes-año) ni el tipo de estadísticas.

---

## 2.2 Clave de agrupación: mes y año

Los movimientos tienen una **fecha** (`LocalDate`). Para agrupar por “mes” necesitamos una clave que agrupe todos los días de ese mes. En Java podemos usar:

- **YearMonth** (java.time): representa un mes concreto de un año (p. ej. enero de 2024). Ideal para reportes mensuales.

**Obtener YearMonth desde un movimiento:**

```java
YearMonth mes = YearMonth.from(movimiento.fecha());
```

Ese `mes` será el **classifier** de nuestro `groupingBy`: todos los movimientos del mismo mes caen en el mismo grupo.

---

## 2.3 Reporte mensual: total y cantidad por mes

**Objetivo:** Un mapa donde la clave es el mes (YearMonth) y el valor es el **total** de importes de ese mes (y en otro mapa, la **cantidad** de movimientos).

```java
Map<YearMonth, Double> totalPorMes = movimientos.stream()
    .collect(Collectors.groupingBy(
        m -> YearMonth.from(m.fecha()),
        Collectors.summingDouble(MovimientoBancario::importe)
    ));

Map<YearMonth, Long> cantidadPorMes = movimientos.stream()
    .collect(Collectors.groupingBy(
        m -> YearMonth.from(m.fecha()),
        Collectors.counting()
    ));
```

- **Classifier:** `m -> YearMonth.from(m.fecha())` → cada movimiento se asigna al mes de su fecha.
- **Downstream:** summingDouble para el total, counting para la cantidad.

Si además quieres **filtrar** por rango de meses o por cuenta, aplicas `filter` antes del `collect` (igual que en la Parte 1).

---

## 2.4 Reporte mensual: estadísticas completas por mes

Para cada mes queremos: **count, sum, min, max, average** del importe. Usamos **summarizingDouble** como downstream:

```java
Map<YearMonth, DoubleSummaryStatistics> estadisticasPorMes = movimientos.stream()
    .collect(Collectors.groupingBy(
        m -> YearMonth.from(m.fecha()),
        Collectors.summarizingDouble(MovimientoBancario::importe)
    ));
```

- **Tipo del valor:** `DoubleSummaryStatistics`. Para cada mes puedes hacer:
  - `getCount()`, `getSum()`, `getMin()`, `getMax()`, `getAverage()`.

Ejemplo de uso para imprimir el reporte:

```java
estadisticasPorMes.forEach((mes, stats) -> {
    System.out.println("Mes " + mes + ":");
    System.out.println("  Movimientos: " + stats.getCount());
    System.out.println("  Total: " + stats.getSum());
    System.out.println("  Mínimo: " + stats.getMin());
    System.out.println("  Máximo: " + stats.getMax());
    System.out.println("  Promedio: " + stats.getAverage());
});
```

---

## 2.5 Desglose por tipo dentro de cada mes (agrupación anidada)

Si quieres “por cada mes, desglose por tipo de movimiento (INGRESO, RETIRO, etc.)”, puedes:

**Opción A — Dos niveles con groupingBy anidado:**  
Agrupar primero por mes y dentro de cada grupo agrupar por tipo. Eso requiere un collector que agrupe otra vez:

```java
Map<YearMonth, Map<String, Double>> totalPorMesYTipo = movimientos.stream()
    .collect(Collectors.groupingBy(
        m -> YearMonth.from(m.fecha()),
        Collectors.groupingBy(
            MovimientoBancario::tipo,
            Collectors.summingDouble(MovimientoBancario::importe)
        )
    ));
```

- **Resultado:** `Map<YearMonth, Map<String, Double>>`. Para un mes dado, el mapa interno te da el total por tipo.

**Opción B — Primero filtrar por mes y luego agrupar por tipo:**  
Si ya tienes un mes concreto, filtras por ese mes y agrupas por tipo en un stream aparte. Ambas opciones son válidas; la A te da todo el reporte de una vez.

---

## 2.6 Ordenar el reporte por mes

Los mapas no garantizan orden. Si quieres mostrar los meses en orden cronológico, conviertes las entradas del mapa en una lista ordenada:

```java
List<Map.Entry<YearMonth, DoubleSummaryStatistics>> reporteOrdenado = estadisticasPorMes.entrySet().stream()
    .sorted(Map.Entry.comparingByKey())
    .toList();
```

`comparingByKey()` ordena por la clave (YearMonth tiene orden natural cronológico). Luego recorres `reporteOrdenado` para imprimir o generar el reporte.

---

## 2.7 Resumen del taller

- **Clave de agrupación mensual:** `YearMonth.from(movimiento.fecha())`.
- **Totales por mes:** `groupingBy(mes, summingDouble(importe))`.
- **Cantidad por mes:** `groupingBy(mes, counting())`.
- **Estadísticas por mes:** `groupingBy(mes, summarizingDouble(importe))` → DoubleSummaryStatistics.
- **Por mes y tipo:** `groupingBy(mes, groupingBy(tipo, summingDouble(importe)))`.
- **Orden:** ordenar `entrySet().stream()` con `sorted(Map.Entry.comparingByKey())` para salida cronológica.

Con esto tienes un sistema de reporte mensual completo con agrupación por fechas y cálculos estadísticos, sin saltar ningún aspecto clave.

**Proyecto de apoyo:** en el mismo proyecto **registros-bancarios-reportes**, la clase **ReporteMensualService** implementa total por mes, cantidad por mes, estadísticas por mes, total por mes y tipo, y reporte ordenado por mes. El `Main` imprime estos resultados.

---

# Parte 3. Diagnóstico de cuellos de botella y puntos de mejora

Esta parte ayuda a **identificar** qué suele ir lento o ser confuso en aplicaciones que procesan muchos registros con Streams, y **qué se puede mejorar**.

---

## 3.1 Qué es un cuello de botella

Un **cuello de botella** es la parte del sistema que limita el rendimiento o la claridad: donde se pierde más tiempo o donde el código es más difícil de mantener. En nuestro contexto (Streams y análisis de datos) los cuellos suelen estar en:

- **Origen de datos:** leer muchos registros de disco o red.
- **Volumen:** procesar listas muy grandes sin filtrar antes.
- **Operaciones costosas** dentro de `map` o `filter` (llamadas a BD, cálculos pesados).
- **Falta de paralelismo** cuando el problema es “embarazosamente paralelo” y hay muchos datos.
- **Código imperativo** repetitivo que podría simplificarse con Streams y volverse más legible.

---

## 3.2 Puntos típicos de cuello de botella

| Área | Qué suele pasar | Síntoma |
|------|------------------|--------|
| **Carga inicial** | Cargar toda la lista en memoria de una vez | Uso alto de memoria, espera al cargar |
| **Filtros tarde** | Hacer `map` costoso antes de `filter` | Se procesan elementos que luego se descartan |
| **Múltiples recorridos** | Varios `stream()` sobre la misma lista para cosas relacionadas | Tiempo total = suma de cada recorrido |
| **Operaciones en map/filter** | Llamadas a BD o I/O dentro de lambdas | Cada elemento paga el coste de la llamada |
| **Listas pequeñas con parallelStream** | Usar paralelo con pocos elementos | Overhead mayor que beneficio |
| **Estado compartido** | Modificar variables externas en parallelStream | Resultados incorrectos o inestables |

---

## 3.3 Mejoras sugeridas (sin saltar el “por qué”)

**1) Filtrar lo antes posible**  
- **Problema:** Si filtras después de un `map` costoso, estás gastando tiempo en elementos que luego descartas.  
- **Mejora:** Poner todos los `filter` que puedas **antes** de cualquier `map` pesado. Así el resto del pipeline trabaja con menos elementos.

**2) Un solo recorrido cuando sea posible**  
- **Problema:** Hacer `movimientos.stream().collect(groupingBy(...))` para total por mes, y otro `movimientos.stream().collect(groupingBy(...))` para cantidad por mes, son dos recorridos completos.  
- **Mejora:** Si necesitas varias cosas del mismo stream, valora un solo recorrido que rellene una estructura (p. ej. un mapa con un DTO que tenga total y cantidad), o combina resultados con collectors más avanzados (ej. `teeing`) si te resulta claro. En muchos casos dos recorridos son aceptables y más legibles; en listas enormes, un solo recorrido puede reducir tiempo.

**3) Paralelismo solo donde compensa**  
- **Problema:** `parallelStream()` en listas pequeñas o con operaciones muy ligeras puede ser más lento que `stream()`.  
- **Mejora:** Usar `parallelStream()` cuando la lista sea grande (decenas o cientos de miles) y la operación por elemento no sea trivial. **Siempre medir** con `System.nanoTime()` o varias ejecuciones antes de dejarlo fijo.

**4) Evitar trabajo costoso dentro de lambdas**  
- **Problema:** Dentro de `map` o `filter` hacer llamadas a base de datos, lectura de ficheros o cálculos muy pesados.  
- **Mejora:** Cargar o precalcular lo necesario antes del stream; dentro del pipeline solo acceder a datos ya en memoria o hacer cálculos ligeros.

**5) Claridad y mantenibilidad**  
- **Problema:** Pipelines muy largos en una sola línea difíciles de leer.  
- **Mejora:** Extraer a variables con nombres claros (p. ej. `Predicate<MovimientoBancario> enRangoFechas = m -> ...`) o a métodos (`filtrarPorCuenta(...)`, `totalPorMes(...)`). Así el flujo se entiende sin saltar pasos.

**6) Orden de las operaciones**  
- **Problema:** Orden confuso (p. ej. ordenar antes de filtrar cuando hay muchos elementos).  
- **Mejora:** Orden lógico: origen → filtros → transformaciones → ordenación/límites → terminal. Documentar con un comentario breve si el orden es importante por rendimiento.

---

## 3.4 Cómo diagnosticar en tu propio código

1. **Medir:** Envuelve en `System.nanoTime()` las partes sospechosas (carga, primer stream, segundo stream, etc.) y quédate con qué bloque consume más tiempo.
2. **Revisar filtros:** ¿Están antes de los `map` costosos? ¿Se puede reducir el volumen antes?
3. **Revisar recorridos:** ¿Cuántos `stream()` sobre la misma colección hay? ¿Se puede unificar en uno?
4. **Revisar paralelismo:** ¿La colección es grande y la operación costosa? Si sí, probar `parallelStream()` y medir de nuevo.
5. **Revisar claridad:** ¿Un desarrollador nuevo entendería el flujo de datos en 2 minutos? Si no, extraer métodos o variables con nombres descriptivos.

Con esto tienes un diagnóstico claro de cuellos de botella y un conjunto de mejoras concretas sin saltar el por qué de cada una.

---

# Parte 4. Refactorización de proyectos previos con Streams

Esta parte explica **desde cero** cómo llevar código imperativo (bucles, acumuladores) a Streams para ganar claridad y, en algunos casos, rendimiento. No se salta el “antes”, “después” ni el “por qué”.

---

## 4.1 Objetivo de la refactorización

- **Claridad:** Expresar *qué* se hace (filtrar, agrupar, sumar) en lugar de *cómo* (índices, variables auxiliares).
- **Menos errores:** Evitar índices mal actualizados o condiciones complejas dentro de un for.
- **Rendimiento:** En algunos casos un solo pipeline bien construido puede ser más eficiente que varios bucles; en otros la ganancia es sobre todo de legibilidad.

No refactorizar por refactorizar: solo donde el código se entienda mejor o se mida una mejora.

---

## 4.2 Patrones típicos: de imperativo a Streams

**Patrón 1 — “Recorrer y filtrar”**  
- **Antes:** for/foreach que recorre una lista y, si cumple condición, añade a otra lista.  
- **Después:** `lista.stream().filter(predicado).toList()`.  
- **Clave:** El predicado del `filter` es la misma condición que tenías en el if.

**Patrón 2 — “Recorrer y transformar”**  
- **Antes:** for que recorre y va construyendo una lista de otro tipo (p. ej. de Movimiento a Double).  
- **Después:** `lista.stream().map(función).toList()`.  
- **Clave:** La función del `map` es la que antes aplicabas dentro del bucle a cada elemento.

**Patrón 3 — “Recorrer y sumar/contar”**  
- **Antes:** variable acumuladora (suma o contador) que se actualiza en cada iteración.  
- **Después:** `stream().mapToDouble(...).sum()` o `stream().filter(...).count()` o `stream().reduce(0, (a,b)->a+b)`.  
- **Clave:** La condición del contador pasa a `filter`; la expresión que sumas pasa a `mapToDouble` o al `reduce`.

**Patrón 4 — “Agrupar y luego total por grupo”**  
- **Antes:** Map que recorres con un for; para cada elemento calculas la clave, buscas la lista en el mapa, añades o actualizas un total.  
- **Después:** `stream().collect(Collectors.groupingBy(clasificador, summingDouble(getter)))`.  
- **Clave:** El clasificador es “cómo calculabas la clave”; el getter es el campo que sumabas.

**Patrón 5 — “Encontrar el máximo por algún criterio”**  
- **Antes:** variable “mejor” que actualizas comparando en cada iteración.  
- **Después:** `stream().max(comparador).orElse(...)` o, si es sobre un mapa, `entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(...)`.

---

## 4.3 Ejemplo completo: antes y después

**Antes (imperativo):** Total de importes por cuenta en un rango de fechas.

```java
Map<String, Double> totalPorCuenta = new HashMap<>();
for (MovimientoBancario m : movimientos) {
    if (m.fecha().isBefore(desde) || m.fecha().isAfter(hasta)) continue;
    String c = m.cuenta();
    totalPorCuenta.merge(c, m.importe(), Double::sum);
}
```

**Después (Streams):**

```java
Map<String, Double> totalPorCuenta = movimientos.stream()
    .filter(m -> !m.fecha().isBefore(desde) && !m.fecha().isAfter(hasta))
    .collect(Collectors.groupingBy(
        MovimientoBancario::cuenta,
        Collectors.summingDouble(MovimientoBancario::importe)
    ));
```

- **Qué ganamos:** La intención (“filtrar por fecha, agrupar por cuenta, sumar importe”) se lee en una sola expresión. No hay que seguir un bucle ni un `merge` para entender el resultado.
- **Qué no cambia:** El resultado (mapa cuenta → total) es el mismo. Si medimos y la lista es muy grande, podemos probar `parallelStream()` y comparar.

---

## 4.4 Checklist para refactorizar un método propio

1. **Identificar** qué hace el método: ¿filtra? ¿agrupa? ¿suma? ¿busca un máximo?
2. **Asignar** cada parte a una operación de stream: filter, map, groupingBy, summingDouble, max, etc.
3. **Escribir** el pipeline paso a paso (primero filter, luego collect con groupingBy, etc.).
4. **Probar** con los mismos datos que el código antiguo y comprobar que el resultado coincide.
5. **Opcional:** medir tiempo si el método es crítico; si hace falta, probar parallelStream.
6. **Documentar** brevemente (comentario o nombre de método) qué hace el pipeline si no es obvio.

Con esto puedes refactorizar proyectos previos aplicando Streams de forma ordenada y sin saltar ningún aspecto clave (intención, equivalencia de resultado, y dónde puede mejorar el rendimiento).

---

# Resumen de la guía

- **Parte 0:** Repasamos los conceptos necesarios (stream, collect, groupingBy, partitioningBy, summarizing, parallelStream) y el modelo de datos de movimientos bancarios.
- **Parte 1:** Flujo completo en una app de registros bancarios: origen → stream → filtros → agrupación → reducción por grupo → consumo del resultado. Cada paso explicado sin saltar ninguno.
- **Parte 2:** Taller de reporte mensual: agrupación por `YearMonth`, totales y cantidad por mes, estadísticas con summarizingDouble, desglose por tipo dentro del mes, y ordenación del reporte.
- **Parte 3:** Diagnóstico de cuellos de botella (origen, filtros tarde, múltiples recorridos, paralelismo mal usado, estado compartido) y mejoras sugeridas (filtrar antes, unificar recorridos cuando compense, medir paralelismo, claridad).
- **Parte 4:** Refactorización de código previo: patrones (filtrar, transformar, sumar/contar, agrupar, máximo) y ejemplo antes/después; checklist para aplicar Streams en tus propios métodos.

Con esta guía puedes consolidar el uso profesional de Streams en análisis de datos, aplicar paralelismo y agrupación en escenarios empresariales (bancarios y reportes mensuales), diagnosticar cuellos de botella y refactorizar proyectos previos con criterio y sin omitir aspectos clave.
