# Taller: Proyecto desde cero integrando excepciones, logging, estructuras de datos y Streams

**Carpeta:** CH5-M1-U4-C7  

---

## Objetivo del taller

Desarrollar **desde cero** un proyecto que cumpla una **especificación de requisitos** redactada como si un cliente nos hubiera contratado. En clase aplicarás **POO**, **excepciones**, **logging**, **estructuras de datos** y **Streams** para cumplir cada requisito.

El proyecto se entrega solo con la **estructura inicial** (pom, logback en consola, Main en blanco). El resto lo implementas tú siguiendo la especificación del cliente.

---

# Parte 1. Simulación: el cliente nos contrata

Imagina que una pequeña empresa de venta de productos te contrata para desarrollar un **sistema de gestión de artículos** en consola. El responsable del negocio te envía la siguiente especificación. Tu trabajo es implementarla al pie de la letra.

---

# Parte 2. Especificación de requisitos del software (documento del cliente)

A continuación se detallan los requisitos que el cliente exige. Cada uno tiene un **identificador**, una **descripción** y **criterios de aceptación** para saber cuándo está cumplido.

---

## 2.1 Alcance y entidad principal

**REQ-ALC-01 — Entidad Artículo**  
El sistema debe gestionar **artículos** (productos del catálogo). Cada artículo se identifica de forma única y tiene al menos los siguientes datos:

- **Identificador (id):** código único del artículo. No puede estar vacío ni ser nulo. El cliente puede proporcionar el id al dar de alta el artículo.
- **Nombre:** nombre del artículo. No puede estar vacío ni ser nulo.
- **Categoría:** categoría a la que pertenece el artículo (por ejemplo: "Electrónica", "Ropa", "Alimentación"). No puede estar vacía ni ser nula.
- **Precio unitario:** precio de venta del artículo. Debe ser mayor o igual a cero. No se aceptan precios negativos.

El cliente exige que **no se puedan ingresar dos artículos con el mismo id**. Si alguien intenta dar de alta un artículo con un id que ya existe en el sistema, la aplicación **no debe guardarlo** y debe informar al usuario de forma clara que ese id ya está en uso.

**Criterios de aceptación:**  
- Existe una representación en código (clase o record) del artículo con los campos indicados.  
- Al intentar agregar un artículo con un id que ya existe, el sistema no lo agrega y muestra un mensaje de error entendible (por ejemplo: "Ya existe un artículo con id X").  
- No hay dos artículos en el sistema con el mismo id en ningún momento.

---

## 2.2 Alta de artículos y validaciones

**REQ-ALT-01 — Agregar artículo**  
El sistema debe permitir **agregar** un nuevo artículo indicando: id, nombre, categoría y precio unitario.

**REQ-VAL-01 — Validaciones al agregar**  
Al agregar un artículo, el sistema debe comprobar lo siguiente. Si **alguna** de estas condiciones no se cumple, el artículo **no se debe guardar** y el usuario debe recibir un mensaje de error **explícito** que indique qué está mal:

- El **id** no puede ser nulo ni una cadena vacía (ni solo espacios en blanco). Mensaje esperado tipo: "El id del artículo es obligatorio" o similar.
- El **nombre** no puede ser nulo ni vacío (ni solo espacios). Mensaje esperado tipo: "El nombre del artículo es obligatorio" o similar.
- La **categoría** no puede ser nula ni vacía. Mensaje esperado tipo: "La categoría es obligatoria" o similar.
- El **precio** debe ser mayor o igual a cero. Si se envía un número negativo, no se guarda y se debe mostrar un mensaje tipo: "El precio no puede ser negativo" o similar.

**Criterios de aceptación:**  
- Si se intenta agregar con id/nombre/categoría vacíos o precio negativo, el sistema no guarda el artículo y muestra el mensaje de error correspondiente.  
- Si todas las validaciones pasan y el id no está duplicado, el artículo se guarda correctamente.

---

## 2.3 Búsqueda de artículos

**REQ-BUS-01 — Buscar artículo por id**  
El sistema debe permitir **buscar un artículo por su id**.  

- Si **existe** un artículo con ese id, se debe devolver (o mostrar) ese artículo.  
- Si **no existe** ningún artículo con ese id, el sistema debe indicarlo de forma clara al usuario. No debe fallar de forma silenciosa ni devolver un artículo inventado. El mensaje puede ser del tipo: "No se encontró ningún artículo con id X" o similar.

**Criterios de aceptación:**  
- Dado un id existente, la búsqueda devuelve ese artículo.  
- Dado un id que no existe, el sistema informa claramente que no hay resultado (por ejemplo mediante mensaje en consola o lanzando una excepción con mensaje que se muestre al usuario).

**REQ-BUS-02 — Buscar artículos por nombre**  
El sistema debe permitir **buscar artículos cuyo nombre contenga** un texto determinado (búsqueda parcial, sin distinguir mayúsculas y minúsculas). Por ejemplo, si se busca "lap", deben aparecer artículos como "Laptop" o "Lápiz". Si no hay coincidencias, se debe devolver una lista vacía o indicar que no hay resultados.

**REQ-BUS-03 — Buscar artículos por categoría**  
El sistema debe permitir **listar todos los artículos de una categoría** dada. Si la categoría no tiene artículos, se devuelve una lista vacía o se indica que no hay resultados.

**Criterios de aceptación:**  
- Las búsquedas por nombre y por categoría devuelven solo los artículos que cumplan el criterio.  
- El cliente espera que estas búsquedas se implementen de forma eficiente y clara (se sugiere usar Streams en la capa de servicio).

---

## 2.4 Listado y eliminación

**REQ-LIS-01 — Listar todos los artículos**  
El sistema debe permitir **listar todos** los artículos almacenados, mostrando al menos id, nombre, categoría y precio de cada uno. Si no hay artículos, se debe mostrar una lista vacía o el mensaje "No hay artículos registrados" (o similar).

**REQ-ELI-01 — Eliminar artículo por id**  
El sistema debe permitir **eliminar un artículo indicando su id**.

- Si **existe** un artículo con ese id, se debe **eliminar** del sistema y el usuario debe recibir una confirmación (por ejemplo: "Artículo con id X eliminado correctamente" o similar).  
- Si **no existe** ningún artículo con ese id, **no se debe modificar nada** en el sistema y el usuario debe recibir un mensaje de error claro, por ejemplo: "No se encontró ningún artículo con id X. No se realizó ninguna eliminación."

**Criterios de aceptación:**  
- Eliminar por id existente quita el artículo y confirma la acción.  
- Eliminar por id inexistente no modifica el sistema y muestra un mensaje explícito de error.

---

## 2.5 Registro de operaciones (logging)

**REQ-LOG-01 — Registro en consola**  
El cliente exige tener un **registro (log)** de las operaciones importantes para poder auditar el uso del sistema. El log debe imprimirse en **consola** y debe incluir al menos lo siguiente:

- **Al iniciar la aplicación:** un mensaje indicando que el sistema ha iniciado.  
- **Al finalizar la aplicación:** un mensaje indicando que el sistema ha finalizado.  
- **Cuando se agrega un artículo correctamente:** un mensaje con nivel informativo (INFO) indicando que se agregó un artículo (incluyendo, por ejemplo, el id o el nombre).  
- **Cuando se elimina un artículo correctamente:** un mensaje informativo indicando que se eliminó el artículo (por ejemplo, el id).  
- **Cuando se intenta agregar un artículo con id duplicado:** un mensaje de advertencia (WARN) indicando el intento de id duplicado y el id en cuestión.  
- **Cuando falla una validación** (id vacío, precio negativo, etc.) o cuando se busca/elimina por un id que no existe: un mensaje de error o advertencia (WARN o ERROR) que permita identificar qué ocurrió.

El cliente ya ha proporcionado un proyecto base con la dependencia de logging y un archivo de configuración para que el log salga por consola. Debes usar ese mecanismo y no imprimir solo con `System.out` para estas trazas de auditoría.

**Criterios de aceptación:**  
- Al ejecutar las operaciones indicadas, en consola aparecen los mensajes de log correspondientes con un formato legible (fecha/hora, nivel, mensaje).

---

## 2.6 Reportes y resúmenes

**REQ-INF-01 — Cantidad de artículos por categoría**  
El sistema debe poder generar un **resumen** que indique **cuántos artículos hay en cada categoría**. Por ejemplo: "Electrónica: 5 artículos", "Ropa: 3 artículos". El cliente espera que este resumen se calcule a partir de los datos almacenados (se sugiere usar Streams para agrupar y contar).

**REQ-INF-02 — Valor total del inventario**  
El sistema debe poder calcular e informar el **valor total del inventario**: la suma del precio de todos los artículos registrados. Si no hay artículos, el total debe ser cero. El cliente espera que este cálculo se haga sobre los datos almacenados (se sugiere usar Streams).

**Criterios de aceptación:**  
- El resumen por categoría muestra la cantidad correcta por cada categoría.  
- El valor total del inventario es la suma correcta de los precios de todos los artículos.

---

## 2.7 Comportamiento ante errores y presentación

**REQ-ERR-01 — Mensajes claros al usuario**  
Cuando ocurra cualquier error de validación o de negocio (id duplicado, id no encontrado, datos inválidos, etc.), el usuario **siempre** debe ver un mensaje en consola (o en la interfaz de presentación) que explique qué ha pasado. No se acepta que la aplicación termine sin mensaje (por ejemplo, que una excepción no capturada imprima solo el stack trace sin un mensaje amigable).

**REQ-ORG-01 — Organización del código**  
El cliente solicita que el código esté organizado de forma que:

- Las **entidades** (por ejemplo, la clase Artículo) estén en un paquete o capa de **modelo**.  
- La **lógica** de negocio (validaciones, comprobación de id duplicado, uso de colecciones, búsquedas con Streams) esté en una o más clases de **servicio**, no en la clase principal que muestra menús o mensajes.  
- La clase o clases que muestran mensajes al usuario y leen opciones (por ejemplo, Main o un menú) solo **llamen** a los servicios y **capturen** las excepciones para mostrar el mensaje al usuario. Es decir, la presentación no debe contener reglas de negocio ni acceder directamente a las estructuras donde se guardan los artículos.

**Criterios de aceptación:**  
- Existe una separación clara entre modelo, servicio(s) y presentación (Main/vista).  
- Los errores se capturan en la capa de presentación y se muestra un mensaje entendible al usuario.

---

# Parte 3. Resumen de requisitos (checklist para el desarrollador)

Para que no quede nada en el tintero, aquí va la lista cerrada de lo que el software debe cumplir:

| Id        | Requisito |
|-----------|-----------|
| REQ-ALC-01 | Artículo con id, nombre, categoría, precio. No permitir dos artículos con el mismo id. |
| REQ-ALT-01 | Operación para agregar artículo. |
| REQ-VAL-01 | Validar id, nombre, categoría no vacíos y precio ≥ 0 al agregar; mensaje claro si falla. |
| REQ-BUS-01 | Buscar artículo por id; si no existe, informar claramente. |
| REQ-BUS-02 | Buscar artículos por nombre (contiene texto, sin distinguir mayúsculas/minúsculas). |
| REQ-BUS-03 | Listar artículos por categoría. |
| REQ-LIS-01 | Listar todos los artículos. |
| REQ-ELI-01 | Eliminar artículo por id; si no existe, no modificar nada e informar. |
| REQ-LOG-01 | Log en consola: inicio/fin, alta correcta, baja correcta, id duplicado (WARN), validaciones/errores (WARN/ERROR). |
| REQ-INF-01 | Resumen: cantidad de artículos por categoría (Streams). |
| REQ-INF-02 | Valor total del inventario = suma de precios (Streams). |
| REQ-ERR-01 | Todo error de negocio o validación debe mostrar mensaje claro al usuario. |
| REQ-ORG-01 | Separación modelo / servicio(s) / presentación. |

---

# Parte 4. Cómo implementar estos requisitos (guía técnica)

A continuación se indica **dónde** aplicar cada tema del taller para cumplir la especificación del cliente.

---

## 4.1 POO y modelo

- **Clase Artículo:** crear una clase (o record) con atributos `id`, `nombre`, `categoría`, `precioUnitario`. Validaciones de tipo "nombre no vacío" o "precio >= 0" pueden ir en el constructor; si fallan, lanzar una excepción con el mensaje que exige el cliente (REQ-VAL-01).
- **Excepción de negocio:** crear por ejemplo `NegocioException` (o `ArticuloException`) para lanzar cuando: id duplicado (REQ-ALC-01), id no encontrado al buscar o eliminar (REQ-BUS-01, REQ-ELI-01). El mensaje del constructor debe ser el texto que verá el usuario (REQ-ERR-01).
- **Servicio:** una clase `ArticuloService` (o similar) que mantenga la colección de artículos y ofrezca métodos: `agregar`, `buscarPorId`, `buscarPorNombre`, `buscarPorCategoria`, `listarTodos`, `eliminarPorId`, `cantidadPorCategoria`, `valorTotalInventario`. En `agregar`, antes de insertar: validar datos y comprobar que el id no exista ya; si algo falla, loguear (REQ-LOG-01) y lanzar la excepción de negocio.

---

## 4.2 Estructuras de datos

- **Almacenamiento:** puedes usar una `List<Articulo>` para mantener el orden de alta, o un `Map<String, Articulo>` (clave = id) para garantizar unicidad de id y búsqueda por id muy rápida.
- **Id duplicado:** si usas `List`, antes de agregar debes comprobar con un `stream().anyMatch(a -> a.getId().equals(id))` (o un método `existeId`) que no haya ya un artículo con ese id. Si usas `Map`, `map.containsKey(id)` indica si el id ya existe. En ambos casos, si existe, loguear WARN y lanzar `NegocioException` (REQ-ALC-01, REQ-LOG-01).
- **Eliminar por id:** con `List`, `list.removeIf(a -> a.getId().equals(id))` (y comprobar antes si existía para lanzar excepción si no); con `Map`, `map.remove(id)` devuelve el valor eliminado o null si no existía (REQ-ELI-01).

---

## 4.3 Excepciones

- **Lanzar en el servicio:** al agregar con id duplicado; al buscar por id que no existe (si tu contrato es "lanzo excepción cuando no hay resultado"); al eliminar por id que no existe. Mensaje en cada caso según REQ-ERR-01.
- **Capturar en la presentación:** en Main (o menú), envolver las llamadas al servicio en try-catch de tu excepción de negocio y en el catch mostrar `e.getMessage()` al usuario. Opcionalmente loguear también en el catch (ERROR o WARN) si no lo has hecho ya en el servicio.

---

## 4.4 Logging

- **Logger:** usar el logger del proyecto (SLF4J + Logback), por ejemplo `LoggerFactory.getLogger(ArticuloService.class)`.
- **Dónde loguear:**  
  - INFO: al agregar un artículo correctamente (incluir id o nombre); al eliminar correctamente (incluir id).  
  - WARN: al intentar agregar con id duplicado; al intentar eliminar o buscar por id inexistente.  
  - INFO (o el nivel que prefieras): al iniciar y al finalizar la aplicación en Main.  
Con esto cumples REQ-LOG-01.

---

# Parte 5. Estructura inicial del proyecto

En la carpeta **CH5-M1-U4-C7** está el proyecto **taller-sistema-desde-cero** con:

- **pom.xml:** Java 17, dependencias de **logging** (SLF4J + Logback) y plugin para ejecutar la clase Main.
- **src/main/resources/logback.xml:** configuración del log para que la salida vaya a **consola**.
- **src/main/java:** paquete principal con una clase **Main** en blanco (solo esqueleto para compilar y ejecutar).

No hay clases de modelo, ni servicios, ni excepciones: **debes crearlas tú** para cumplir la especificación del cliente.

---

# Parte 6. Pasos sugeridos para desarrollar en clase

1. **Leer la especificación (10 min)**  
   Revisar cada requisito (REQ-ALC-01 hasta REQ-ORG-01) y marcar en el checklist qué implica en código (clases, métodos, validaciones, mensajes).

2. **Diseño (15–20 min)**  
   Definir la clase Artículo (atributos y validaciones en constructor). Definir la excepción de negocio. Decidir si usas List o Map y por qué. Listar los métodos del servicio (agregar, buscarPorId, eliminarPorId, etc.).

3. **Implementación por bloques**  
   - **Modelo y excepción:** clase Artículo con validaciones; clase NegocioException (o similar).  
   - **Servicio:** colección + método agregar (con validación de id duplicado y validaciones de datos); métodos buscar por id, por nombre, por categoría; listar todos; eliminar por id; cantidad por categoría; valor total. En cada operación que falle, loguear y lanzar excepción según REQ-LOG-01 y REQ-ERR-01.  
   - **Main:** crear servicio, llamar a operaciones de prueba (agregar, listar, buscar, eliminar, reportes), capturar excepciones y mostrar mensaje; loguear inicio y fin.

4. **Pruebas**  
   Probar: agregar con id duplicado (mensaje + log WARN); buscar/eliminar por id inexistente (mensaje + log); agregar con nombre vacío o precio negativo (mensaje); listar, buscar por nombre, por categoría, cantidad por categoría, valor total. Comprobar que todos los logs aparecen en consola.

---

# Parte 7. Criterios de evaluación (referencia)

| Criterio | Qué se espera |
|----------|----------------|
| Requisitos del cliente | Cumplimiento de todos los REQ indicados en la especificación (id único, validaciones, búsqueda, eliminación, logs, reportes, mensajes claros, organización). |
| POO | Clase Artículo coherente; excepción de negocio con mensajes útiles; servicio con responsabilidad única. |
| Excepciones | Uso de excepción de negocio en id duplicado, id no encontrado; captura en presentación con mensaje al usuario. |
| Logging | Log en consola según REQ-LOG-01 (inicio/fin, altas/bajas, WARN en duplicado y en no encontrado). |
| Estructuras de datos | Uso de List o Map justificado; garantía de unicidad de id. |
| Streams | Búsqueda por nombre, por categoría, cantidad por categoría y valor total implementados con Streams. |
| Organización | Separación modelo / servicio / presentación; Main sin lógica de negocio. |