# Clase 2: Estructuras dinámicas en acción — selección y aplicación práctica

## Objetivos de la clase-tutoría

- **Consolidar** el uso adecuado de cada tipo de colección en problemas reales.
- **Identificar** errores comunes en el uso de listas, sets y mapas.

---

## 1. Comparar estructuras: ¿Cuándo usar List, Set o Map?

### 1.1 Pregunta clave: ¿Qué necesito hacer con los datos?

Antes de elegir la colección, responde:

| Pregunta | Si la respuesta es… | Colección típica |
|----------|----------------------|------------------|
| ¿Necesito **orden de inserción** o **posición** (primero, segundo, último)? | Sí | **List** (ArrayList) |
| ¿Necesito **evitar duplicados** y no me importa el orden? | Sí | **Set** (HashSet) |
| ¿Necesito **evitar duplicados** y que estén **ordenados**? | Sí | **Set** (TreeSet) |
| ¿Necesito **buscar por una clave** (nombre, DNI, id) y obtener un valor? | Sí | **Map** (HashMap) |
| ¿Necesito **claves ordenadas** (p. ej. listado alfabético)? | Sí | **Map** (TreeMap) |
| ¿Pueden repetirse elementos y necesito su posición? | Sí | **List** |

### 1.2 ¿Cuándo usar List vs. Set?

- **List** cuando:
  - El orden importa (lista de tareas, historial, turnos).
  - Pueden existir repeticiones (varias ventas del mismo producto, múltiples registros).
  - Necesitas acceso por índice: “el tercer elemento”, “insertar en la posición 0”.

- **Set** cuando:
  - No debe haber duplicados (códigos únicos, usuarios logueados, etiquetas).
  - Solo te interesa saber “¿está o no está?” (contains).
  - No necesitas posición; solo existencia o recorrer todos sin repetir.

### 1.3 ¿Cuándo elegir HashMap?

- **HashMap** (o Map en general) cuando:
  - Tienes **pares clave–valor**: para cada “clave” (nombre, ID, DNI) hay **un** valor asociado (teléfono, edad, objeto).
  - Necesitas **buscar rápido por clave**: “dame el contacto de Ana” → `mapa.get("Ana")`.
  - La clave es **única**; si repites la misma clave, se reemplaza el valor (útil para actualizar).

Ejemplos típicos: agenda (nombre → teléfono), diccionario (palabra → definición), caché (id → resultado), configuración (clave → valor).

### 1.4 Resumen visual

```
¿Necesito posición o orden de inserción?     → List
¿Solo “sin duplicados” / “¿está?”?           → Set
¿Buscar por clave y obtener un valor?        → Map (HashMap o TreeMap si orden)
```

---

## 2. Taller práctico: agenda de contactos con HashMap

### 2.1 Enunciado

Crear una **agenda de contactos** en la que cada contacto tiene:

- **Nombre** (clave única para buscar).
- **Teléfono**.
- **Email** (opcional).

Operaciones:

- **Añadir:** añadir contacto (nombre, teléfono, email).
- **Consulta:** buscar por nombre y mostrar teléfono y email.
- **Actualización:** cambiar teléfono o email de un contacto existente.
- **Eliminar:** eliminar un contacto por nombre.
- **Listado:** mostrar todos los contactos.

### 2.2 Elección de estructura

- Necesitamos **buscar por nombre** y obtener datos del contacto → **Map**.
- La clave es el **nombre** (único por contacto).
- El valor puede ser un objeto **Contacto** (teléfono, email) o solo un String; para practicar POO usamos un objeto `Contacto`.

Por tanto: `Map<String, Contacto>` con **HashMap** es adecuado. Si quisiéramos el listado ordenado por nombre, podríamos usar `TreeMap` o ordenar al mostrar.

## 3. Errores comunes: identificación y solución

Al usar listas, sets y mapas aparecen errores de lógica típicos. A continuación se describen las causas y cómo corregirlos.

### 3.1 Valores repetidos / duplicados

**Problema:** En un **Map**, si haces `put(clave, valor)` dos veces con la misma clave, el segundo valor **reemplaza** al primero. No hay “dos entradas” para la misma clave. Eso es correcto para una agenda (un nombre → un contacto), pero a veces el programador espera “añadir otro” y no “sustituir”.

**En List:** sí se permiten repeticiones; si no quieres duplicados, debes comprobar antes de `add` o usar un **Set**.

**Qué hacer:**

- Si **no** quieres duplicados por clave: Map está bien; solo hay que asumir que put actualiza.
- Si quieres **varios valores por “clave”** (ej. varios teléfonos por nombre), la estructura podría ser `Map<String, List<String>>` o similar.
- Si quieres **conjunto sin repetir** elementos, usa **Set**, no List.

**Aclaración:** Es normal que en un HashMap, al hacer `put` con una clave que ya existe, el valor anterior se reemplace. Eso no es un fallo del programa: el Map asocia una sola valor por clave. Si necesitas varios valores por clave (por ejemplo varios teléfonos por contacto), la estructura adecuada sería algo como `Map<String, List<String>>`: la clave es el nombre y el valor es una lista de teléfonos.

### 3.2 Claves inexistentes / NullPointerException con get(clave)

**Problema:** `mapa.get("NombreQueNoExiste")` devuelve **null**. Si luego haces `mapa.get("X").getTelefono()` sin comprobar, obtienes **NullPointerException**.

**Solución:**

- **Comprobar antes de usar:**
  ```java
  if (mapa.containsKey("Ana")) {
      Contacto c = mapa.get("Ana");
      // usar c
  }
  ```
- O **asignar a variable y comprobar null:**
  ```java
  Contacto c = mapa.get("Ana");
  if (c != null) {
      System.out.println(c.getTelefono());
  }
  ```
- **getOrDefault** si quieres un valor por defecto:
  ```java
  Contacto c = mapa.getOrDefault("Ana", new Contacto("", "", ""));
  ```

**Aclaración:** El método `get(clave)` devuelve `null` cuando la clave no está en el mapa. Cualquier llamada a un método sobre ese resultado (por ejemplo `get(clave).getTelefono()`) lanza NullPointerException. Por eso siempre hay que comprobar antes de usar: con `containsKey(clave)` o con `if (variable != null)` después de asignar el resultado de `get` a una variable.

### 3.3 Referencias nulas (null en listas, sets y mapas)

**Problema:** Guardar `null` en una colección o pasar `null` donde no se espera:

- `lista.add(null);` luego `lista.get(i).toString()` → NPE.
- `mapa.put("A", null);` luego `mapa.get("A").getTelefono()` → NPE.
- No inicializar la colección: `Map<String, Contacto> mapa;` y luego `mapa.put(...)` → NPE.

**Solución:**

- **Inicializar** siempre la colección: `Map<String, Contacto> agenda = new HashMap<>();`
- **Validar** datos antes de añadir: no poner `null` como clave ni como valor si luego no vas a comprobarlo.
- **Comprobar null** al leer: tanto el resultado de `get` como los atributos del objeto si pueden ser null.

**Aclaración:** Las colecciones en Java no se inicializan solas. Si declaras `Map<String, Contacto> agenda;` y no asignas `new HashMap<>()`, la variable vale `null` y cualquier llamada a `agenda.put(...)` o `agenda.get(...)` lanzará NullPointerException. Igualmente, si guardas `null` como valor (o en una lista) y luego usas ese elemento sin comprobar, obtendrás NPE. La norma práctica es: inicializar siempre la colección al declararla o en el constructor, y no usar el resultado de `get` ni elementos de una lista/set sin comprobar si son null cuando pueda serlo.

### 3.4 Resumen de comprobaciones recomendadas

| Situación | Comprobación |
|-----------|---------------|
| Usar resultado de `mapa.get(clave)` | `containsKey(clave)` o `get(clave) != null` |
| Recorrer mapa | Comprobar que el mapa no sea null y esté inicializado |
| Añadir a lista/set/mapa | Validar que el elemento no sea null si no lo quieres permitir |
| Primera vez que usas la colección | Asegurarte de haber hecho `new ArrayList<>()` / `new HashMap<>()` etc. |

---

### 4 Separación de responsabilidades

- **Modelo:** clase `Contacto` solo con datos (getters/setters o registro).
- **Lógica de negocio:** clase `AgendaService` (o similar) que contiene el `HashMap` y los métodos añadir, consulta, actualización, eliminar, listado.
- **Entrada/salida:** clase `Main` o `AgendaApp` que muestra menú, lee teclado y llama al servicio. No poner la lógica del Map dentro del main.

Así puedes probar el servicio sin consola y cambiar la interfaz (consola, ventanas) sin tocar la lógica.

### 4.1 Validaciones en un solo lugar

- Validar nombre no vacío, teléfono con formato razonable, etc., antes de llamar a `put` o de crear el `Contacto`.
- Si la validación falla, no modificar el mapa; informar al usuario o lanzar excepción.

### 4.2 Nombres claros

- Variable del mapa: `agenda`, `contactosPorNombre`, no solo `mapa` o `m`.
- Métodos: `agregarContacto`, `buscarPorNombre`, `actualizarTelefono`, `eliminarContacto`, `listarContactos`.

### 4.3 Uso de la interfaz Map

Declarar con la interfaz para poder cambiar la implementación después:

```java
Map<String, Contacto> agenda = new HashMap<>();
```

### 4.5 Puntos a revisar al refactorizar

Al revisar o refactorizar tu código de agenda, comprueba lo siguiente:

- **Dónde validar null y claves inexistentes:** Antes de usar el resultado de `get(clave)` hay que comprobar que la clave exista (`containsKey`) o que el valor no sea null. Es buena práctica hacer esa comprobación en el servicio (por ejemplo en `buscarPorNombre`) y que el método devuelva null o un Optional cuando no hay resultado, para que quien llama no tenga que recordar comprobar el mapa directamente.
- **Nombres de métodos:** Deben describir la acción (agregarContacto, buscarPorNombre, actualizarTelefono, eliminarContacto, listarContactos). Evita nombres genéricos como “hacer” o “procesar” cuando la operación es concreta.
- **Dónde poner la lógica del Map:** Toda la lógica que usa el HashMap (put, get, remove, containsKey) debe estar en una clase de servicio o repositorio, no en Main. Main solo lee entrada, muestra salida y llama a los métodos del servicio.

---

## 5. Resumen

- **List** para secuencias con orden/posición y posibles repeticiones.
- **Set** para conjuntos sin duplicados.
- **Map (HashMap)** para buscar por clave y obtener un valor; ideal para agendas, diccionarios, cachés.
- Errores frecuentes: no comprobar **null** al usar `get`, no **inicializar** la colección, confundir **reemplazo** en put con “añadir otro”.
- Refactorizar: **modelo**, **servicio** (con el Map) e **interfaz** (consola) separados; **validaciones** y **nombres** claros.

Con esto se consolidan las estructuras dinámicas y se practica su uso correcto en un problema real como la agenda de contactos.
