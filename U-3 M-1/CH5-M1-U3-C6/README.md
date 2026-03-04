# Sistema de Gestión de Biblioteca

## 📚 Descripción del Proyecto

Este es un proyecto educativo diseñado para demostrar cómo desarrollar un sistema completo desde cero aplicando **pruebas unitarias** con JUnit 5. El sistema gestiona una biblioteca pequeña que permite registrar libros, realizar préstamos y devoluciones, con validaciones de negocio.

## 🎯 Objetivos del Proyecto

- Demostrar el desarrollo de un proyecto real desde cero
- Aplicar Test-Driven Development (TDD) y pruebas unitarias
- Implementar reglas de negocio con validaciones
- Practicar diseño de clases y servicios
- Manejar excepciones personalizadas
- Alcanzar alta cobertura de código con pruebas

## 🏗️ Arquitectura del Proyecto

```
biblioteca-gestion/
├── src/
│   ├── main/java/com/biblioteca/
│   │   ├── model/              # Modelos de datos
│   │   │   ├── Libro.java
│   │   │   ├── Usuario.java
│   │   │   └── Prestamo.java
│   │   ├── service/            # Lógica de negocio
│   │   │   ├── ValidacionService.java
│   │   │   ├── LibroService.java
│   │   │   └── PrestamoService.java
│   │   └── exception/           # Excepciones personalizadas
│   │       ├── LibroNoDisponibleException.java
│   │       ├── LimitePrestamosException.java
│   │       ├── LibroNoEncontradoException.java
│   │       └── PrestamoNoEncontradoException.java
│   └── test/java/com/biblioteca/
│       ├── service/            # Pruebas de servicios
│       │   ├── ValidacionServiceTest.java
│       │   ├── LibroServiceTest.java
│       │   └── PrestamoServiceTest.java
│       └── model/              # Pruebas de modelos
│           └── LibroTest.java
└── pom.xml
```

## 📋 Funcionalidades

### 1. Gestión de Libros

- **Registrar libros**: ISBN único, título, autor, cantidad total
- **Buscar libros**: Por ISBN, título o autor
- **Listar libros**: Todos los libros registrados
- **Actualizar disponibilidad**: Modificar cantidad disponible

### 2. Gestión de Préstamos

- **Prestar libros**: Con validación de disponibilidad
- **Devolver libros**: Actualizar disponibilidad
- **Consultar préstamos**: Activos por usuario
- **Límite de préstamos**: Máximo 3 libros por usuario

### 3. Validaciones

- **ISBN**: Exactamente 13 dígitos numéricos
- **Email**: Formato válido (@ y punto después del @)
- **Disponibilidad**: No prestar si no hay ejemplares
- **Límites**: No exceder 3 préstamos por usuario

## 🔧 Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación
- **JUnit 5**: Framework de pruebas unitarias
- **Maven**: Gestión de dependencias y construcción
- **JUnit Jupiter**: API moderna de JUnit 5

## 🚀 Cómo Ejecutar el Proyecto

### Requisitos Previos

- Java 17 o superior
- Maven 3.6 o superior
- IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 📖 Reglas de Negocio Implementadas

### Reglas de Libros

1. **ISBN único**: No puede haber dos libros con el mismo ISBN
2. **ISBN válido**: Debe tener exactamente 13 dígitos numéricos
3. **Cantidad positiva**: La cantidad total debe ser mayor a cero
4. **Disponibilidad**: No puede exceder la cantidad total

### Reglas de Préstamos

1. **Disponibilidad**: Solo se puede prestar si hay ejemplares disponibles
2. **Límite de préstamos**: Máximo 3 libros activos por usuario
3. **Unicidad**: Un usuario no puede tener el mismo libro prestado dos veces
4. **Devolución**: Solo se puede devolver un libro que está prestado

### Reglas de Validación

1. **Email**: Debe contener @ y al menos un punto después del @
2. **Fecha**: No puede ser futura
3. **Cantidad**: Debe ser positiva

## 🎓 Casos de Uso Principales

### Caso de Uso 1: Registrar y Prestar un Libro

```java
// 1. Registrar libro
LibroService libroService = new LibroService();
Libro libro = libroService.registrarLibro(
    "1234567890123", 
    "El Quijote", 
    "Cervantes", 
    5
);

// 2. Prestar libro
PrestamoService prestamoService = new PrestamoService(libroService);
Prestamo prestamo = prestamoService.prestarLibro("1234567890123", "USR-001");

// 3. Verificar disponibilidad
Libro libroActualizado = libroService.buscarPorISBN("1234567890123");
assertEquals(4, libroActualizado.getCantidadDisponible());
```

### Caso de Uso 2: Validar Límite de Préstamos

```java
// Usuario con 3 préstamos activos
prestamoService.prestarLibro("1234567890123", "USR-001");
prestamoService.prestarLibro("1234567890124", "USR-001");
prestamoService.prestarLibro("1234567890125", "USR-001");

// Intentar prestar un 4to libro debe fallar
assertThrows(LimitePrestamosException.class, () -> {
    prestamoService.prestarLibro("1234567890126", "USR-001");
});
```

### Caso de Uso 3: Devolver un Libro

```java
// Prestar
prestamoService.prestarLibro("1234567890123", "USR-001");

// Devolver
prestamoService.devolverLibro("1234567890123", "USR-001");

// Verificar que ya no está activo
List<Prestamo> activos = prestamoService.obtenerPrestamosActivos("USR-001");
assertTrue(activos.isEmpty());
```

## 🧪 Estrategia de Pruebas

### Patrón AAA (Arrange-Act-Assert)

Todas las pruebas siguen el patrón AAA:

```java
@Test
public void testEjemplo() {
    // ARRANGE: Preparar datos
    String isbn = "1234567890123";
    
    // ACT: Ejecutar acción
    Libro libro = libroService.buscarPorISBN(isbn);
    
    // ASSERT: Verificar resultado
    assertNotNull(libro);
}
```

## 📝 Convenciones de Código

### Nombres de Pruebas

```java
test[NombreMetodo]_[Condicion]_[ResultadoEsperado]()
```

Ejemplos:
- `testValidarISBN_Correcto_13Digitos()`
- `testPrestarLibro_LimiteAlcanzado()`
- `testBuscarPorISBN_NoEncontrado()`

### Organización

- Cada clase de servicio tiene su clase de prueba correspondiente
- Pruebas agrupadas por método con comentarios
- Uso de `@BeforeEach` para inicialización común

## 📚 Recursos de Aprendizaje

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Maven Getting Started](https://maven.apache.org/guides/getting-started/)
- [Test-Driven Development](https://martinfowler.com/bliki/TestDrivenDevelopment.html)