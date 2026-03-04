# Clase 1: Introducción a Colecciones en Java (List, Set, Map)

## Objetivos de aprendizaje

- Comprender las estructuras de datos más usadas en Java.
- Aplicar la colección adecuada según el problema a resolver.
- Manipular listas, conjuntos y mapas con operaciones básicas.

---

## 1. Configuración y observaciones básicas

### 1.1 Dónde están las colecciones en Java

Las colecciones forman parte del **Java Collections Framework** y están en el paquete `java.util`. No necesitas dependencias externas: vienen con el JDK.

**Imports típicos:**

```java
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.HashMap;
import java.util.TreeMap;
```

### 1.2 Uso de genéricos (obligatorio)

Las colecciones usan **genéricos** para indicar el tipo de elementos que guardan. Esto evita casts y errores en tiempo de ejecución.

```java
// Correcto: se indica el tipo de elemento
List<String> nombres = new ArrayList<>();
List<Integer> numeros = new ArrayList<>();

// Incorrecto o desaconsejado: "raw type" (sin genérico)
List lista = new ArrayList();  // el compilador avisa; evitar en código nuevo
```

**Observación:** A la derecha del `=` se suele escribir `ArrayList<>()` (diamond operator). Los `<>` indican “el mismo tipo que el de la izquierda”.

### 1.3 Interfaces vs implementaciones

- **Interfaces:** `List`, `Set`, `Map` — definen *qué* operaciones existen (contrato).
- **Implementaciones:** `ArrayList`, `LinkedList`, `HashSet`, `TreeSet`, `HashMap`, `TreeMap` — definen *cómo* se guardan los datos y su rendimiento.

Es buena práctica declarar la variable con la **interfaz** y crear el objeto con la **clase concreta**:

```java
List<String> lista = new ArrayList<>();   // variable tipo List, objeto ArrayList
Set<Integer> conjunto = new HashSet<>();
Map<String, Integer> mapa = new HashMap<>();
```

Así puedes cambiar la implementación más adelante sin tocar el resto del código.

---

## 2. Interfaces y clases del framework de colecciones

### 2.1 Resumen visual

| Interfaz | ¿Orden? | ¿Duplicados? | Acceso típico | Uso principal |
|----------|---------|--------------|----------------|----------------|
| **List** | Sí (por índice) | Sí | Por índice (`get(i)`) | Secuencias ordenadas, listas con repetición |
| **Set** | Depende de la impl. | No | Por valor (¿está o no?) | Conjuntos sin duplicados |
| **Map** | Depende de la impl. | Claves únicas | Por clave (`get(clave)`) | Pares clave–valor |

### 2.2 List

- **Orden:** los elementos tienen posición (índice 0, 1, 2, …).
- **Duplicados:** permitidos.
- **Interfaz:** `List<E>`.
- **Implementaciones habituales:** `ArrayList`, `LinkedList`.

### 2.3 Set

- **Orden:** en `HashSet` no hay orden garantizado; en `TreeSet` hay orden (natural o por comparador).
- **Duplicados:** no permitidos (un mismo elemento no se repite).
- **Interfaz:** `Set<E>`.
- **Implementaciones habituales:** `HashSet`, `TreeSet`.

### 2.4 Map

- **Orden:** en `HashMap` no hay orden garantizado; en `TreeMap` las claves están ordenadas.
- **Duplicados:** las **claves** son únicas; los **valores** pueden repetirse.
- **Interfaz:** `Map<K, V>` (K = tipo de clave, V = tipo de valor).
- **Implementaciones habituales:** `HashMap`, `TreeMap`.

---

## 3. Implementaciones: cuándo usar cada una

### 3.1 List

| Clase | Estructura interna | Cuándo usarla |
|-------|--------------------|----------------|
| **ArrayList** | Array redimensionable | Acceso por índice frecuente, añadir al final. La más usada por defecto. |
| **LinkedList** | Lista enlazada (nodos) | Inserción/eliminación frecuente al inicio o en medio (según el caso). |

**Observación:** Para la mayoría de casos, `ArrayList` es la opción más simple y suele ser suficiente.

### 3.2 Set

| Clase | Orden | Cuándo usarla |
|-------|--------|----------------|
| **HashSet** | No garantizado | Conjunto sin duplicados y no te importa el orden. Muy usado. |
| **TreeSet** | Ordenado (natural o comparador) | Conjunto sin duplicados que debe estar ordenado. |

#### Requisito para TreeSet y TreeMap: comparación de elementos (Comparable o Comparator)

**TreeSet** y **TreeMap** ordenan sus elementos (o las claves, en el Map) internamente. Para saber “quién va antes y quién después”, Java necesita una forma de **comparar** dos elementos. Hay dos formas de proporcionarla:

**1. Orden natural: la clase implementa `Comparable<T>`**

La interfaz `Comparable<T>` tiene un único método: `int compareTo(T otro)`. Devuelve un número negativo si “this” va antes que `otro`, cero si son iguales para el orden, o positivo si va después. Así el TreeSet/TreeMap puede ordenar sin saber nada más.

Muchas clases de Java ya implementan `Comparable`: `String` (orden alfabético), `Integer`, `LocalDate`, etc. Con esas puedes usar `TreeSet` o `TreeMap` directamente:

```java
// String ya implementa Comparable → orden alfabético
Set<String> nombres = new TreeSet<>();
nombres.add("Carlos");
nombres.add("Ana");
nombres.add("Bruno");
System.out.println(nombres);  // [Ana, Bruno, Carlos]
```

Si usas **tu propia clase** (por ejemplo `Producto`, `Alumno`), tienes dos opciones:

- **Opción A:** Hacer que la clase implemente `Comparable<Producto>` y definir `compareTo` (por nombre, por id, etc.). Entonces `new TreeSet<Producto>()` usará ese orden.
- **Opción B:** No tocar la clase y pasar al TreeSet/TreeMap un **Comparator** que compare dos elementos.

**2. Orden personalizado: usar un `Comparator<T>`**

Un `Comparator<T>` es un objeto que sabe comparar dos elementos de tipo `T`. Tiene el método `int compare(T a, T b)`. Puedes usarlo cuando:
- La clase no implementa `Comparable`, o
- Quieres un orden distinto al natural (por ejemplo, orden inverso, o ordenar por otro campo).

Se pasa en el constructor del TreeSet o TreeMap:

```java
// Orden alfabético inverso
Set<String> nombres = new TreeSet<>(Comparator.reverseOrder());

// TreeSet de enteros ordenados de mayor a menor
Set<Integer> numeros = new TreeSet<>(Comparator.reverseOrder());
numeros.add(5);
numeros.add(2);
numeros.add(8);
System.out.println(numeros);  // [8, 5, 2]
```

Para una clase propia sin `Comparable`, pasas un comparator:

```java
// Ordenar productos por nombre
Comparator<Producto> porNombre = (p1, p2) -> p1.getNombre().compareTo(p2.getNombre());
Set<Producto> productos = new TreeSet<>(porNombre);
```

**Resumen:**

| Situación | Qué hacer |
|-----------|-----------|
| Elementos/claves son `String`, `Integer`, etc. | Ya son `Comparable` → `new TreeSet<>()` o `new TreeMap<>()` funciona. |
| Tu clase debe tener “orden natural” | Implementar `Comparable<T>` en la clase y definir `compareTo`. |
| Quieres otro orden (inverso, por otro campo) | Pasar un `Comparator<T>` en el constructor: `new TreeSet<>(comparador)`. |
| No implementas nada y no pasas comparator | Al añadir el primer elemento, **ClassCastException**: TreeSet/TreeMap no saben cómo ordenar. |

**TreeMap:** lo mismo aplica para las **claves**. Las claves deben ser comparables (o pasas un `Comparator` que compare claves). Los valores no se comparan para el orden.

### 3.3 Map

| Clase | Orden de las claves | Cuándo usarla |
|-------|----------------------|----------------|
| **HashMap** | No garantizado | Diccionario clave–valor estándar. La más usada. |
| **TreeMap** | Ordenado por clave | Necesitas las claves ordenadas (recorrido, rangos, etc.). |

---

## 4. Métodos fundamentales

### 4.1 List (ArrayList / LinkedList)

| Método | Descripción | Ejemplo |
|--------|-------------|---------|
| `add(e)` | Añade un elemento al final | `lista.add("hola");` |
| `add(i, e)` | Inserta en la posición `i` | `lista.add(0, "primero");` |
| `get(i)` | Devuelve el elemento en la posición `i` | `String s = lista.get(0);` |
| `set(i, e)` | Reemplaza el elemento en la posición `i` | `lista.set(0, "nuevo");` |
| `remove(i)` | Elimina el elemento en la posición `i` | `lista.remove(0);` |
| `remove(e)` | Elimina la primera ocurrencia del objeto `e` | `lista.remove("hola");` |
| `contains(e)` | Indica si el elemento está en la lista | `lista.contains("hola");` |
| `isEmpty()` | Indica si la lista está vacía | `lista.isEmpty();` |
| `clear()` | Elimina todos los elementos | `lista.clear();` |
| `size()` | Número de elementos | `int n = lista.size();` |

**Observación:** Los índices van de `0` a `size() - 1`. Usar un índice fuera de rango lanza `IndexOutOfBoundsException`.

### 4.2 Set (HashSet / TreeSet)

| Método | Descripción | Ejemplo |
|--------|-------------|---------|
| `add(e)` | Añade el elemento si no está (evita duplicados) | `conjunto.add(5);` |
| `remove(e)` | Elimina el elemento | `conjunto.remove(5);` |
| `contains(e)` | Indica si está en el conjunto | `conjunto.contains(5);` |
| `isEmpty()` | Indica si está vacío | `conjunto.isEmpty();` |
| `clear()` | Elimina todos los elementos | `conjunto.clear();` |
| `size()` | Número de elementos | `int n = conjunto.size();` |

**Observación:** En un `Set` no existe “posición”; no hay `get(i)`. Solo puedes preguntar si un valor está o no.

### 4.3 Map (HashMap / TreeMap)

| Método | Descripción | Ejemplo |
|--------|-------------|---------|
| `put(clave, valor)` | Inserta o reemplaza el valor para esa clave | `mapa.put("nombre", 42);` |
| `get(clave)` | Devuelve el valor asociado a la clave (o `null`) | `Integer v = mapa.get("nombre");` |
| `remove(clave)` | Elimina la entrada con esa clave | `mapa.remove("nombre");` |
| `containsKey(clave)` | Indica si existe esa clave | `mapa.containsKey("nombre");` |
| `containsValue(valor)` | Indica si existe ese valor | `mapa.containsValue(42);` |
| `isEmpty()` | Indica si no hay entradas | `mapa.isEmpty();` |
| `clear()` | Elimina todas las entradas | `mapa.clear();` |
| `size()` | Número de pares clave-valor | `int n = mapa.size();` |
| `keySet()` | Conjunto de claves | `Set<String> claves = mapa.keySet();` |
| `values()` | Colección de valores | `Collection<Integer> vals = mapa.values();` |

**Observación:** Si usas `get(clave)` y la clave no existe, devuelve `null`. Si guardas `Integer` u otros objetos, comprueba null o usa `getOrDefault(clave, valorPorDefecto)`.

---

## 5. Comparación entre estructuras

| Criterio | List | Set | Map |
|----------|------|-----|-----|
| **Orden** | Sí (por índice) | HashSet: no; TreeSet: sí | HashMap: no; TreeMap: por clave |
| **Duplicados** | Sí | No | Claves únicas; valores pueden repetirse |
| **Acceso** | Por índice: `get(i)` | Por valor: `contains(e)` | Por clave: `get(clave)` |
| **Uso típico** | Lista ordenada, repetición permitida | Conjunto sin repetición | Diccionario: buscar por clave |

**Ejemplo rápido:**

```java
// List: varios "Juan"
List<String> lista = new ArrayList<>();
lista.add("Juan");
lista.add("Juan");  // permitido
System.out.println(lista.size());  // 2

// Set: un solo "Juan"
Set<String> conjunto = new HashSet<>();
conjunto.add("Juan");
conjunto.add("Juan");  // no se duplica
System.out.println(conjunto.size());  // 1

// Map: claves únicas
Map<String, Integer> edades = new HashMap<>();
edades.put("Juan", 20);
edades.put("Juan", 25);  // reemplaza el 20
System.out.println(edades.get("Juan"));  // 25
```

---

## 6. Iteración: bucles clásicos y for-each

### 6.1 List — for con índice y for-each

```java
List<String> frutas = List.of("manzana", "pera", "uva");

// Bucle clásico (cuando necesitas el índice)
for (int i = 0; i < frutas.size(); i++) {
    System.out.println(i + ": " + frutas.get(i));
}

// For-each (recomendado cuando solo necesitas el valor)
for (String f : frutas) {
    System.out.println(f);
}
```

**Observación:** `List.of(...)` crea una lista inmutable (no se puede modificar). Para una lista mutable usa `new ArrayList<>(List.of(...))` o `add` sobre un `ArrayList` nuevo.

### 6.2 Set — for-each (no hay índice)

```java
Set<Integer> numeros = new HashSet<>();
numeros.add(10);
numeros.add(20);

for (Integer n : numeros) {
    System.out.println(n);
}
```

### 6.3 Map — recorrer claves, valores o entradas

```java
Map<String, Integer> edades = new HashMap<>();
edades.put("Ana", 22);
edades.put("Luis", 30);

// Solo claves
for (String nombre : edades.keySet()) {
    System.out.println("Clave: " + nombre);
}

// Solo valores
for (Integer edad : edades.values()) {
    System.out.println("Valor: " + edad);
}

// Pares clave-valor (lo más útil)
for (Map.Entry<String, Integer> entrada : edades.entrySet()) {
    System.out.println(entrada.getKey() + " -> " + entrada.getValue());
}
```

**Observación:** No modifiques una colección mientras la recorres con for-each (añadir/eliminar); puede lanzar `ConcurrentModificationException`. Para eliminar durante el recorrido se usan iteradores o recorrer una copia.

---

## 7. Ejemplo completo: uso básico de List, Set y Map

```java
import java.util.*;

public class EjemploColecciones {

    public static void main(String[] args) {
        // ----- List -----
        List<String> tareas = new ArrayList<>();
        tareas.add("Estudiar");
        tareas.add("Programar");
        tareas.add("Estudiar");
        System.out.println("Lista: " + tareas);
        System.out.println("Tamaño: " + tareas.size());
        System.out.println("Primera: " + tareas.get(0));

        // ----- Set -----
        Set<String> sinRepetir = new HashSet<>(tareas);
        System.out.println("Set (sin duplicados): " + sinRepetir);

        // ----- Map -----
        Map<String, Integer> notas = new HashMap<>();
        notas.put("Matemáticas", 8);
        notas.put("Programación", 9);
        notas.put("Matemáticas", 7);  // reemplaza el 8
        System.out.println("Nota Matemáticas: " + notas.get("Matemáticas"));
    }
}
```

---

## 8. Uso de IA para generar estructuras de datos

Puedes usar herramientas de IA (por ejemplo, asistentes en el IDE o ChatGPT) para generar código de colecciones a partir de una descripción en lenguaje natural.

### 8.1 Cómo plantear la petición

- Indica **qué quieres guardar** (tipos: nombres, números, pares clave–valor).
- Indica **si importa el orden** y **si pueden haber duplicados**.
- Indica **cómo accederás** (por posición, por valor, por clave).

**Ejemplos de descripciones:**

- *“Una lista de nombres de alumnos en el orden en que llegan.”* → `List<String>` (p. ej. `ArrayList`).
- *“Un conjunto de códigos de producto sin repetir.”* → `Set<String>` (p. ej. `HashSet`).
- *“Un diccionario que asocia DNI con edad.”* → `Map<String, Integer>` (p. ej. `HashMap`).
- *“Nombres de ciudades sin duplicados y ordenados alfabéticamente.”* → `Set<String>` con `TreeSet`.

### 8.2 Qué revisar en el código generado

- **Imports:** que use `java.util` correcto.
- **Genéricos:** que declare el tipo, p. ej. `List<String>`, no `List` sin tipo.
- **Interfaz vs implementación:** que declare con la interfaz (`List`, `Set`, `Map`) y cree con la clase concreta (`ArrayList`, `HashSet`, `HashMap`, etc.).
- **Métodos:** que use `add`, `get`, `contains`, etc. según lo que necesites.

La IA te ayuda a bajar la idea al código; tú debes entender por qué se elige esa estructura y comprobar que el código sea correcto y legible.

---

## 9. Resumen y recomendaciones

| Necesitas… | Colección recomendada |
|------------|------------------------|
| Secuencia ordenada, tal vez con repetición | `List` → `ArrayList` |
| Conjunto sin duplicados y sin importar orden | `Set` → `HashSet` |
| Conjunto sin duplicados y ordenado | `Set` → `TreeSet` |
| Buscar/guardar por clave (diccionario) | `Map` → `HashMap` |
| Diccionario con claves ordenadas | `Map` → `TreeMap` |

- Siempre usar **genéricos** (`List<String>`, no `List`).
- Declarar con **interfaz** y crear con **implementación**.
- Tener claro si necesitas **orden**, **duplicados** y **acceso por índice o por clave** para elegir bien entre List, Set y Map.

---

## 10. Proyecto de demo: CRUD con las seis implementaciones

En la misma carpeta **CH5-M1-U4-C1** está el proyecto **colecciones-demo**, que muestra en código las diferencias entre cada implementación:

| Colección    | Qué demuestra |
|-------------|----------------|
| **ArrayList**  | Create (add), Read (get por índice), Update (set), Delete (remove). Duplicados permitidos. |
| **LinkedList** | Misma interfaz `List`; inserción al inicio con `add(0, e)`. |
| **HashSet**    | add, contains, remove; sin duplicados; orden no garantizado. “Update” = remove + add. |
| **TreeSet**    | Orden automático (String implementa Comparable). Ejemplo con `Comparator.reverseOrder()`. |
| **HashMap**    | put, get(clave), remove(clave); keySet(), values(), entrySet(). |
| **TreeMap**    | Mismo CRUD que Map; claves ordenadas (p. ej. alfabético). |

Ejecutar el proyecto: `mvn compile exec:java` desde la carpeta `colecciones-demo`, o ejecutar la clase `com.colecciones.Main` desde el IDE. Toda la salida es por consola para comparar el comportamiento de cada colección.