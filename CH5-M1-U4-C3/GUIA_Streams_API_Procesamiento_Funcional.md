# Guía: Streams API — Procesamiento funcional de datos

---

## Objetivos de aprendizaje

- Procesar colecciones de forma **eficiente y declarativa**.
- Aplicar transformaciones funcionales: **map**, **filter**, **reduce**.
- Comprender cómo los Streams cambian el enfoque **imperativo** por uno **funcional**.

---

## Contenidos

1. Introducción a `Stream<T>`: características, operaciones y flujo.
2. Operaciones intermedias: map, filter, sorted, distinct, limit, skip.
3. Operaciones terminales: forEach, collect, count, findFirst, reduce.
4. Comparación entre bucles tradicionales y programación funcional.

---

# Parte 1. Introducción a `Stream<T>`

## 1.1 ¿Qué es un Stream?

Un **Stream** en Java no es una estructura que guarda datos: es una **secuencia de elementos** sobre la que se pueden definir operaciones de procesamiento. Los elementos “fluyen” una vez; no se puede rebobinar ni reutilizar el mismo stream.

- **Origen:** suele ser una colección (`lista.stream()`), un array (`Arrays.stream(arr)`), o valores sueltos (`Stream.of(1, 2, 3)`).
- **Operaciones:** se encadenan en un **pipeline** (tubo): primero operaciones **intermedias** (transforman o filtran) y al final una **terminal** (produce resultado o efecto).
- **Estilo:** **declarativo** (“qué quiero obtener”) en lugar de imperativo (“cómo lo hago paso a paso”).

## 1.2 Características principales

| Característica | Significado |
|----------------|-------------|
| **No almacena** | El stream no es un contenedor; los datos siguen en la colección original. |
| **Inmutable** | Las operaciones no modifican la fuente; devuelven un nuevo stream. |
| **Un solo uso** | Una vez consumido con una operación terminal, el stream no se puede reutilizar. |
| **Puede ser lazy** | Las operaciones intermedias no se ejecutan hasta que hay una operación terminal. |
| **Encadenable** | Se encadenan varias operaciones: `lista.stream().filter(...).map(...).collect(...)`. |

## 1.3 Flujo típico de un pipeline

```
[Colección] → stream() → [intermedia 1] → [intermedia 2] → ... → [terminal] → resultado
```

- **Intermedias:** devuelven otro `Stream` (filter, map, sorted, distinct, limit, skip). Se pueden encadenar.
- **Terminal:** devuelve un valor concreto o produce un efecto (forEach, collect, count, findFirst, reduce). Cierra el stream.

## 1.4 Crear un Stream

```java
// Desde una lista
List<String> nombres = List.of("Ana", "Luis", "Carmen");
Stream<String> s1 = nombres.stream();

// Desde valores sueltos
Stream<Integer> s2 = Stream.of(1, 2, 3, 4, 5);

// Desde un array
String[] arr = { "a", "b", "c" };
Stream<String> s3 = Arrays.stream(arr);

// Rango de números (IntStream)
IntStream numeros = IntStream.range(1, 6);  // 1, 2, 3, 4, 5
```

**Importaciones habituales:** `java.util.stream.Stream`, `java.util.List`, `java.util.Arrays`; para `IntStream`, `java.util.stream.IntStream`.

---

# Parte 2. Operaciones intermedias

Las operaciones intermedias **transforman o filtran** el flujo y devuelven un nuevo `Stream`. No ejecutan el recorrido hasta que hay una operación terminal.

## 2.1 filter(`Predicate<T>`)

**Qué hace:** Deja pasar solo los elementos que cumplen la condición.

**`Predicate<T>`:** interfaz funcional con método `boolean test(T t)`.

```java
List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
List<Integer> pares = nums.stream()
    .filter(n -> n % 2 == 0)
    .toList();  // [2, 4, 6]
```

```java
List<String> palabras = List.of("hola", "sol", "agua", "mesa");
List<String> largas = palabras.stream()
    .filter(p -> p.length() > 3)
    .toList();  // [hola, agua, mesa]
```

## 2.2 map(`Function<T, R>`)

**Qué hace:** Transforma cada elemento en otro (mismo tipo o distinto).

**`Function<T, R>`:** `R apply(T t)`.

```java
List<String> nombres = List.of("ana", "luis", "carmen");
List<String> mayusculas = nombres.stream()
    .map(s -> s.toUpperCase())
    .toList();  // [ANA, LUIS, CARMEN]
```

```java
List<String> numeros = List.of("1", "2", "3");
List<Integer> enteros = numeros.stream()
    .map(s -> Integer.parseInt(s))
    .toList();  // [1, 2, 3]
```

## 2.3 sorted() / sorted(`Comparator<T>`)

**Qué hace:** Ordena los elementos. Sin argumentos usa el orden natural (los elementos deben ser `Comparable`). Con `Comparator` defines el criterio.

```java
List<Integer> nums = List.of(3, 1, 4, 1, 5);
List<Integer> ordenados = nums.stream()
    .sorted()
    .toList();  // [1, 1, 3, 4, 5]
```

```java
List<Integer> nums = List.of(3, 1, 4, 1, 5);
        List<Integer> ordenadosInverso = nums.stream()
                .sorted(Comparator.reverseOrder())
                .toList(); // [5, 4, 3, 1, 1]
```

```java
List<String> palabras = List.of("sol", "agua", "mesa");
List<String> porLongitud = palabras.stream()
    .sorted(Comparator.comparingInt(String::length))
    .toList();  // [sol, mesa, agua]
```

```java
List<String> palabras = List.of("sol", "agua", "mesa");
List<String> porLongitud = palabras.stream()
    .sorted(Comparator.comparingInt(String::length).reversed())
    .toList();  // [sol, mesa, agua]
```


## 2.4 distinct()

**Qué hace:** Elimina duplicados (según `equals`).

```java
List<Integer> nums = List.of(1, 2, 2, 3, 1, 4);
List<Integer> unicos = nums.stream()
    .distinct()
    .toList();  // [1, 2, 3, 4]
```

## 2.5 limit(long n)

**Qué hace:** Se queda solo con los primeros `n` elementos.

```java
List<Integer> nums = List.of(10, 20, 30, 40, 50);
List<Integer> primeros3 = nums.stream()
    .limit(3)
    .toList();  // [10, 20, 30]
```

## 2.6 skip(long n)

**Qué hace:** Omite los primeros `n` elementos.

```java
List<Integer> nums = List.of(10, 20, 30, 40, 50);
List<Integer> sinLosDosPrimeros = nums.stream()
    .skip(2)
    .toList();  // [30, 40, 50]
```

## 2.7 Encadenar varias intermedias

El orden puede cambiar el resultado y el rendimiento. Primero filtrar suele reducir la cantidad de elementos antes de mapear u ordenar.

```java
List<String> nombres = List.of("Ana", "Luis", "Alba", "Antonio", "Lola");
List<String> resultado = nombres.stream()
    .filter(n -> n.startsWith("A"))
    .map(String::toUpperCase)
    .sorted()
    .limit(2)
    .toList();  // [ALBA, ANA]
```

---

# Parte 3. Operaciones terminales

Las operaciones terminales **cierran** el stream y producen un resultado o un efecto secundario. Solo puede haber una terminal por pipeline.

## 3.1 forEach(`Consumer<T>`)

**Qué hace:** Aplica una acción a cada elemento (efecto secundario). No devuelve un valor de negocio; sirve para imprimir o modificar estado externo.

```java
List<String> nombres = List.of("Ana", "Luis");
nombres.stream()
    .forEach(n -> System.out.println("Hola, " + n));
```

**`Consumer<T>`:** `void accept(T t)`.

## 3.2 collect(Collector)

**Qué hace:** Agrupa los elementos del stream en una estructura (lista, set, mapa, etc.).

**Formas muy usadas:**

```java
// Lista (Java 16+)
List<String> lista = stream.filter(...).toList();

// Lista con collect explícito (cualquier versión)
List<String> lista = stream.filter(...).collect(Collectors.toList());

// Set (sin duplicados)
Set<String> set = stream.collect(Collectors.toSet());

// String concatenado
String joined = stream.collect(Collectors.joining(", "));
```

## 3.3 count()

**Qué hace:** Devuelve el número de elementos que hay en el stream (después de las intermedias).

```java
long cantidad = List.of(1, 2, 3, 4, 5).stream()
    .filter(n -> n > 2)
    .count();  // 3
```

## 3.4 findFirst() / findAny()

**Qué hace:** Devuelven un `Optional` con el primer elemento (o cualquiera, en streams paralelos). Útil tras filter.

```java
Optional<String> primero = List.of("Ana", "Luis", "Carmen").stream()
    .filter(n -> n.length() > 3)
    .findFirst();
// Optional["Carmen"]
primero.ifPresent(s -> System.out.println(s));
```

## 3.5 reduce(identidad, `BinaryOperator<T>`) / reduce(`BinaryOperator<T>`)

**Qué hace:** Combina todos los elementos en uno solo: suma, producto, concatenación, máximo, etc.

**Forma con valor inicial (identidad):**

```java
List<Integer> nums = List.of(1, 2, 3, 4, 5);
int suma = nums.stream()
    .reduce(0, (a, b) -> a + b);  // 15
```

**`BinaryOperator<T>`** es una función `(T, T) -> T`. La identidad es el valor cuando el stream está vacío.

```java
// Producto
int producto = nums.stream().reduce(1, (a, b) -> a * b);  // 120

// Concatenar strings
List<String> palabras = List.of("Hola", " ", "mundo");
String frase = palabras.stream().reduce("", (a, b) -> a + b);  // "Hola mundo"
```

**Forma sin identidad:** devuelve `Optional` porque el stream puede estar vacío.

```java
Optional<Integer> max = nums.stream().reduce((a, b) -> a > b ? a : b);
```

## 3.6 anyMatch / allMatch / noneMatch(`Predicate<T>`)

**Qué hace:** Comprueban si algún elemento cumple la condición, todos, o ninguno. Devuelven `boolean`.

```java
boolean hayNegativos = lista.stream().anyMatch(n -> n < 0);
boolean todosPares = lista.stream().allMatch(n -> n % 2 == 0);
boolean ningunoVacio = lista.stream().noneMatch(s -> s.isEmpty());
```

---

# Parte 4. Imperativo vs funcional

## 4.1 Mismo problema: dos estilos

**Objetivo:** De una lista de números, obtener los pares, multiplicarlos por 2 y sumarlos.

**Enfoque imperativo (bucle):**

```java
List<Integer> nums = List.of(1, 2, 3, 4, 5, 6);
int suma = 0;
for (Integer n : nums) {
    if (n % 2 == 0) {
        suma += n * 2;
    }
}
// suma = 24 (2*2 + 4*2 + 6*2)
```

**Enfoque funcional (Stream):**

```java
int suma = nums.stream()
    .filter(n -> n % 2 == 0)
    .map(n -> n * 2)
    .reduce(0, Integer::sum);
// suma = 24
```

## 4.2 Comparación

| Aspecto | Imperativo (bucles) | Funcional (Streams) |
|---------|----------------------|----------------------|
| **Estilo** | “Cómo”: pasos y mutación de variables | “Qué”: filtro, transformación, reducción |
| **Legibilidad** | Depende del bucle; puede ser verboso | Pipeline claro: filter → map → reduce |
| **Reutilización** | Lógica mezclada en el bucle | Operaciones reutilizables y composables |
| **Mutación** | Variable acumuladora que cambia | Sin mutación visible; resultado final |
| **Paralelismo** | Hay que gestionarlo a mano | `parallelStream()` en colecciones |

## 4.3 Cuándo usar cada uno

- **Streams:** transformaciones y consultas sobre colecciones (filtrar, mapear, buscar, sumar, agrupar). Código más declarativo y fácil de leer cuando la lógica es “cadena de pasos”.
- **Bucles:** cuando necesitas estado complejo, varios acumuladores, o salir antes con `break`/`continue` de forma muy clara. También si el código ya es simple y un for es más directo.

---

# Parte 5. Práctica guiada y ejercicios

## 5.1 Ejemplo completo: lista de personas

Supongamos una lista de nombres con edades (por simplicidad, dos listas paralelas o una lista de objetos). Objetivo: nombres de quienes tienen 18 o más, en mayúsculas, ordenados, los 3 primeros.

Con una clase simple:

```java
record Persona(String nombre, int edad) {}

List<Persona> personas = List.of(
    new Persona("Ana", 20),
    new Persona("Luis", 17),
    new Persona("Carmen", 22),
    new Persona("Pedro", 16),
    new Persona("Laura", 19)
);

List<String> resultado = personas.stream()
    .filter(p -> p.edad() >= 18)
    .map(p -> p.nombre().toUpperCase())
    .sorted()
    .limit(3)
    .toList();
// [ANA, CARMEN, LAURA]
```

## 5.2 Ejercicios rápidos para clase

1. **filter + count:** Dada una lista de palabras, ¿cuántas tienen más de 4 letras?
2. **map + collect:** Dada una lista de números, obtener una lista con sus cuadrados.
3. **filter + findFirst:** Dado una lista de enteros, obtener el primero que sea negativo (Optional).
4. **reduce:** Dada una lista de enteros, calcular el producto de todos.
5. **filter + map + toList:** De una lista de Persona (nombre, edad), obtener los nombres (solo String) de los mayores de edad.

---

# Resumen de la sesión

- **Stream:** secuencia de elementos, un solo uso, pipeline de operaciones intermedias + una terminal.
- **Intermedias:** filter, map, sorted, distinct, limit, skip — devuelven Stream.
- **Terminales:** forEach, collect/toList, count, findFirst, reduce, anyMatch/allMatch/noneMatch — cierran el stream y dan resultado.
- **Estilo:** declarativo (qué quiero) frente a imperativo (cómo lo hago); los Streams favorecen transformaciones encadenadas sin mutar estado.
