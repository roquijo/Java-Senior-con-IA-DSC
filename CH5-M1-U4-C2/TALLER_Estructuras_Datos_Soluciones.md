# Soluciones del taller: Uso básico de cada estructura de datos

Este documento contiene la **solución** de cada ejercicio del archivo **TALLER_Estructuras_Datos_Ejemplos_Basicos.md**.

---

## 1. ArrayList — lista ordenada con duplicados

**Qué usar:** `List<String>` implementado con `ArrayList`.

**Acciones:**
- Crear un `ArrayList` de `String`.
- Añadir por ejemplo: "Manzana", "Pera", "Uva", "Pera" (repetida).
- Imprimir el primer elemento (`get(0)`).
- Imprimir el tamaño de la lista (`size()`).
- Imprimir la lista completa.

**Salida esperada (ejemplo):**  
Primera: Manzana. Total: 4. Lista: [Manzana, Pera, Uva, Pera].

**Código de referencia:**
```java
List<String> frutas = new ArrayList<>();
frutas.add("Manzana");
frutas.add("Pera");
frutas.add("Uva");
frutas.add("Pera");
System.out.println("Primera: " + frutas.get(0));
System.out.println("Total: " + frutas.size());
System.out.println("Lista: " + frutas);
```

---

## 2. LinkedList — lista con inserción al inicio

**Qué usar:** `List<String>` implementado con `LinkedList`.

**Acciones:**
- Crear un `LinkedList` de `String`.
- Añadir "Ana", "Luis", "Carmen".
- Insertar "Prioridad" en la posición 0 (`add(0, "Prioridad")`).
- Recorrer e imprimir todos en orden.

**Salida esperada (ejemplo):**  
[Prioridad, Ana, Luis, Carmen].

**Código de referencia:**
```java
List<String> fila = new LinkedList<>();
fila.add("Ana");
fila.add("Luis");
fila.add("Carmen");
fila.add(0, "Prioridad");
System.out.println(fila);
```

---

## 3. HashSet — conjunto sin duplicados (orden no importa)

**Qué usar:** `Set<String>` implementado con `HashSet`.

**Acciones:**
- Crear un `HashSet` de `String`.
- Añadir "P001", "P002", "P001", "P003".
- Imprimir el tamaño (debe ser 3).
- Imprimir el resultado de `contains("P002")`.
- Imprimir el conjunto completo (el orden puede variar).

**Salida esperada (ejemplo):**  
Tamaño: 3. ¿Existe P002? true. Conjunto: [P001, P002, P003] (orden no garantizado).

**Código de referencia:**
```java
Set<String> codigos = new HashSet<>();
codigos.add("P001");
codigos.add("P002");
codigos.add("P001");
codigos.add("P003");
System.out.println("Tamaño: " + codigos.size());
System.out.println("¿Existe P002? " + codigos.contains("P002"));
System.out.println("Conjunto: " + codigos);
```

---

## 4. TreeSet — conjunto sin duplicados y ordenado

**Qué usar:** `Set<String>` implementado con `TreeSet`.

**Acciones:**
- Crear un `TreeSet` de `String`.
- Añadir "Madrid", "Barcelona", "Alicante", "Barcelona".
- Imprimir el tamaño y el conjunto completo.

**Salida esperada (ejemplo):**  
Tamaño: 3. Conjunto: [Alicante, Barcelona, Madrid].

**Código de referencia:**
```java
Set<String> ciudades = new TreeSet<>();
ciudades.add("Madrid");
ciudades.add("Barcelona");
ciudades.add("Alicante");
ciudades.add("Barcelona");
System.out.println("Tamaño: " + ciudades.size());
System.out.println("Conjunto: " + ciudades);
```

---

## 5. HashMap — pares clave-valor (buscar por clave)

**Qué usar:** `Map<String, String>` implementado con `HashMap`. Clave = nombre, valor = teléfono.

**Acciones:**
- Crear un `HashMap` con clave `String` y valor `String`.
- Hacer `put("Ana", "600111222")`, `put("Luis", "600333444")`, `put("Ana", "600999888")`.
- Obtener e imprimir el teléfono de "Luis" con `get("Luis")`.
- Recorrer el mapa con `entrySet()` e imprimir cada par clave-valor.

**Salida esperada (ejemplo):**  
Teléfono de Luis: 600333444. Entradas: Ana=600999888, Luis=600333444 (orden no garantizado).

**Código de referencia:**
```java
Map<String, String> agenda = new HashMap<>();
agenda.put("Ana", "600111222");
agenda.put("Luis", "600333444");
agenda.put("Ana", "600999888");
System.out.println("Teléfono de Luis: " + agenda.get("Luis"));
for (Map.Entry<String, String> e : agenda.entrySet()) {
    System.out.println(e.getKey() + "=" + e.getValue());
}
```

---

## 6. TreeMap — pares clave-valor con claves ordenadas

**Qué usar:** `Map<String, String>` implementado con `TreeMap`.

**Acciones:**
- Crear un `TreeMap` con clave y valor `String`.
- Hacer `put("Zaragoza", "Z")`, `put("Madrid", "M")`, `put("Barcelona", "B")`.
- Recorrer con `entrySet()` e imprimir (las claves deben salir en orden alfabético).

**Salida esperada (ejemplo):**  
Barcelona=B, Madrid=M, Zaragoza=Z.

**Código de referencia:**
```java
Map<String, String> mapa = new TreeMap<>();
mapa.put("Zaragoza", "Z");
mapa.put("Madrid", "M");
mapa.put("Barcelona", "B");
for (Map.Entry<String, String> e : mapa.entrySet()) {
    System.out.println(e.getKey() + "=" + e.getValue());
}
```

---

## Resumen

| Ejercicio | Estructura        | Uso básico que se practica                          |
|-----------|-------------------|-----------------------------------------------------|
| 1         | ArrayList         | Lista ordenada, duplicados, `get(i)`, `size()`      |
| 2         | LinkedList        | Lista con `add(0, e)` para inserción al inicio      |
| 3         | HashSet           | Conjunto sin duplicados, `contains()`               |
| 4         | TreeSet           | Conjunto sin duplicados y ordenado                  |
| 5         | HashMap           | Pares clave-valor, `put`, `get(clave)`, `entrySet()`|
| 6         | TreeMap           | Pares clave-valor con claves ordenadas              |
