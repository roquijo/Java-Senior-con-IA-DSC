# Taller: sistema desde cero (CH5-M1-U4-C7)

Estructura inicial para el **taller en clase**. Solo incluye:

- **pom.xml:** Java 17 y dependencias de logging (SLF4J + Logback).
- **src/main/resources/logback.xml:** configuración para que el log se imprima en **consola**.
- **Main:** esqueleto con un logger de prueba; el resto está en blanco para implementar en clase.

## Objetivo

Desarrollar desde cero un sistema sencillo integrando **POO**, **excepciones**, **logging**, **estructuras de datos** (List/Set/Map) y **Streams**, según el enunciado del taller.

## Cómo ejecutar

```bash
mvn compile exec:java
```

Deberías ver en consola líneas de log con hora, nivel y mensaje. A partir de ahí, crea tus clases de modelo, excepciones, servicios y la lógica en Main según el documento **TALLER_Proyecto_Desde_Cero_Integracion_Temas.md**.
