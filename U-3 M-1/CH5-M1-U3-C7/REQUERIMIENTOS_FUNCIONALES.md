# Requerimientos Funcionales – Sistema de Reserva de Citas

## 1. Descripción del mini-proyecto

Sistema pequeño para **reserva de citas** que permite registrar, consultar y cancelar citas.  
Se aplican: **excepciones personalizadas**, **logging (SLF4J + Log4j2)** y **pruebas unitarias**.

---

## 2. Requerimientos funcionales

| ID   | Requerimiento | Criterio de aceptación |
|------|----------------|-------------------------|
| **RF01** | Registrar una cita | Se puede crear una cita con: identificador, nombre del paciente, teléfono, fecha y hora. Los datos deben ser validados antes de guardar. |
| **RF02** | Validar fecha y hora | La fecha y hora de la cita no pueden ser en el pasado. Si lo son, se debe lanzar una excepción de negocio y registrar en log. |
| **RF03** | Validar datos del paciente | El nombre no puede estar vacío. El teléfono debe tener formato numérico válido (mínimo 8 dígitos). En caso de error, lanzar excepción y registrar en log. |
| **RF04** | Consultar cita por ID | Dado un ID, el sistema devuelve la cita si existe. Si no existe, lanzar excepción y registrar en log. |
| **RF05** | Cancelar cita | Dado un ID, se marca la cita como cancelada. Si la cita no existe o ya está cancelada, lanzar excepción y registrar en log. |
| **RF06** | Trazabilidad (logging) | Todas las operaciones relevantes (alta, consulta, cancelación, validaciones fallidas) deben quedar registradas en log con nivel adecuado (INFO, WARN, ERROR). |

---

## 3. Reglas de negocio

- **RN01:** Una cita tiene: `id`, `nombrePaciente`, `telefono`, `fechaHora`, `cancelada`.
- **RN02:** El sistema mantiene **una única cita** en memoria (una sola referencia a un objeto `Cita`). No se usan listas ni mapas; si se registra una nueva cita, reemplaza a la anterior.
- **RN03:** Solo se pueden crear citas con fecha/hora futura o actual (mismo día).
- **RN04:** Una cita cancelada no puede volver a cancelarse.

---

## 4. Excepciones de negocio (personalizadas)

| Excepción | Cuándo se lanza |
|-----------|------------------|
| `DatosInvalidosException` | Nombre vacío, teléfono inválido u otro dato de entrada incorrecto. |
| `CitaEnFechaPasadaException` | La fecha/hora de la cita es anterior a “ahora”. |
| `CitaNoEncontradaException` | No existe una cita con el ID indicado. |
| `CitaYaCanceladaException` | Se intenta cancelar una cita que ya está cancelada. |

Todas extienden de una base (por ejemplo `RuntimeException`) y permiten mensaje y causa.

---

## 5. Logging

- **Framework:** SLF4J como API + Log4j2 como implementación.
- **Niveles sugeridos:**
  - **INFO:** Cita registrada, cita cancelada, consulta exitosa.
  - **WARN:** Intento de cancelar cita ya cancelada, datos al borde de lo válido.
  - **ERROR:** Validación fallida, excepciones de negocio (con mensaje y/o causa).

La configuración (appenders, niveles por paquete) se define en `log4j2.xml`.

---

## 6. Pruebas unitarias

- **Validación:** Pruebas para el servicio/lógica que valida nombre, teléfono y fecha (casos válidos e inválidos).
- **Servicio de citas:** Pruebas para registrar cita (éxito y fallo por fecha pasada), consultar por ID (existe / no existe), cancelar (éxito y cita ya cancelada).
- **Objetivo:** Cubrir las validaciones y los flujos principales del servicio con JUnit 5.

---

## 7. Objetivos de aprendizaje aplicados

- Integrar manejo de excepciones, logging y pruebas unitarias en una aplicación Java pequeña pero completa.
- Aplicar excepciones personalizadas para errores de negocio.
- Integrar SLF4J + Log4j2 para depuración y trazabilidad.
- Crear pruebas unitarias que validen validaciones y flujos del servicio.
- Seguir buenas prácticas: paquetes por capa, validación centralizada, logs consistentes.
