# Especificación de Pruebas Unitarias

Este documento detalla todas las pruebas unitarias que deben implementarse y ejecutarse para el Sistema de Gestión de Biblioteca.

## 📋 Índice

1. [ValidacionServiceTest](#validacionservicetest)
2. [LibroServiceTest](#libroservicetest)
3. [PrestamoServiceTest](#prestamoservicetest)
4. [LibroTest](#librotest)

---

## ValidacionServiceTest

### Objetivo
Verificar que todas las validaciones de negocio funcionen correctamente.

### Pruebas para `validarISBN(String isbn)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testValidarISBN_Correcto_13Digitos` | Validar ISBN con exactamente 13 dígitos | `true` |
| 2 | `testValidarISBN_Incorrecto_MenosDigitos` | Validar ISBN con menos de 13 dígitos | `false` |
| 3 | `testValidarISBN_Incorrecto_MasDigitos` | Validar ISBN con más de 13 dígitos | `false` |
| 4 | `testValidarISBN_Incorrecto_ConLetras` | Validar ISBN que contiene letras | `false` |
| 5 | `testValidarISBN_Null` | Validar ISBN null | `false` |
| 6 | `testValidarISBN_Vacio` | Validar ISBN vacío | `false` |
| 7 | `testValidarISBN_ConEspacios` | Validar ISBN con espacios | `false` |

**Casos de prueba:**
- ✅ ISBN válido: `"1234567890123"`
- ❌ ISBN inválido: `"123456789012"` (12 dígitos)
- ❌ ISBN inválido: `"12345678901234"` (14 dígitos)
- ❌ ISBN inválido: `"123456789012A"` (con letra)
- ❌ ISBN null
- ❌ ISBN vacío: `""`
- ❌ ISBN con espacios: `"123456789012 "`

### Pruebas para `validarEmail(String email)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testValidarEmail_Correcto` | Validar email con formato correcto | `true` |
| 2 | `testValidarEmail_SinArroba` | Validar email sin @ | `false` |
| 3 | `testValidarEmail_SinPunto` | Validar email sin punto después del @ | `false` |
| 4 | `testValidarEmail_PuntoAntesDelArroba` | Validar email con punto antes del @ | `false` |
| 5 | `testValidarEmail_Null` | Validar email null | `false` |
| 6 | `testValidarEmail_Vacio` | Validar email vacío | `false` |
| 7 | `testValidarEmail_MultiplePuntos` | Validar email con múltiples puntos después del @ | `true` |

**Casos de prueba:**
- ✅ Email válido: `"usuario@example.com"`
- ✅ Email válido: `"usuario@example.co.uk"`
- ❌ Email inválido: `"usuarioexample.com"` (sin @)
- ❌ Email inválido: `"usuario@examplecom"` (sin punto)
- ❌ Email null
- ❌ Email vacío: `""`

### Pruebas para `validarFechaNoFutura(LocalDate fecha)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testValidarFechaNoFutura_FechaPasada` | Validar fecha del pasado | `true` |
| 2 | `testValidarFechaNoFutura_FechaHoy` | Validar fecha de hoy | `true` |
| 3 | `testValidarFechaNoFutura_FechaFutura` | Validar fecha futura | `false` |
| 4 | `testValidarFechaNoFutura_Null` | Validar fecha null | `false` |

**Casos de prueba:**
- ✅ Fecha pasada: `LocalDate.now().minusDays(1)`
- ✅ Fecha hoy: `LocalDate.now()`
- ❌ Fecha futura: `LocalDate.now().plusDays(1)`
- ❌ Fecha null

### Pruebas para `validarCantidadPositiva(int cantidad)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testValidarCantidadPositiva_Positiva` | Validar cantidad positiva | `true` |
| 2 | `testValidarCantidadPositiva_Cero` | Validar cantidad cero | `false` |
| 3 | `testValidarCantidadPositiva_Negativa` | Validar cantidad negativa | `false` |

**Casos de prueba:**
- ✅ Cantidad positiva: `5`
- ❌ Cantidad cero: `0`
- ❌ Cantidad negativa: `-1`

---

## LibroServiceTest

### Objetivo
Verificar que la gestión de libros funcione correctamente, incluyendo registro, búsqueda y actualización.

### Pruebas para `registrarLibro(String isbn, String titulo, String autor, int cantidad)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testRegistrarLibro_Exitoso` | Registrar un libro válido | Libro creado correctamente |
| 2 | `testRegistrarLibro_ISBNDuplicado` | Intentar registrar ISBN duplicado | `IllegalArgumentException` |
| 3 | `testRegistrarLibro_ISBNInvalido` | Registrar con ISBN inválido | `IllegalArgumentException` |
| 4 | `testRegistrarLibro_CantidadNegativa` | Registrar con cantidad negativa | `IllegalArgumentException` |
| 5 | `testRegistrarLibro_CantidadCero` | Registrar con cantidad cero | `IllegalArgumentException` |

**Casos de prueba:**
- ✅ Registro exitoso: ISBN válido, datos correctos
- ❌ ISBN duplicado: Intentar registrar mismo ISBN dos veces
- ❌ ISBN inválido: `"123456789012"` (12 dígitos)
- ❌ Cantidad negativa: `-1`
- ❌ Cantidad cero: `0`

### Pruebas para `buscarPorISBN(String isbn)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testBuscarPorISBN_Encontrado` | Buscar libro existente | Libro encontrado |
| 2 | `testBuscarPorISBN_NoEncontrado` | Buscar libro inexistente | `LibroNoEncontradoException` |

**Casos de prueba:**
- ✅ Libro encontrado: ISBN que existe en el sistema
- ❌ Libro no encontrado: ISBN que no existe

### Pruebas para `buscarPorTitulo(String titulo)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testBuscarPorTitulo_Encontrado` | Buscar por título existente | Lista con resultados |
| 2 | `testBuscarPorTitulo_NoEncontrado` | Buscar título inexistente | Lista vacía |
| 3 | `testBuscarPorTitulo_MultiplesResultados` | Buscar que retorna múltiples libros | Lista con múltiples resultados |
| 4 | `testBuscarPorTitulo_CaseInsensitive` | Búsqueda case-insensitive | Encuentra resultados |
| 5 | `testBuscarPorTitulo_Null` | Buscar con null | Lista vacía |
| 6 | `testBuscarPorTitulo_Vacio` | Buscar con string vacío | Lista vacía |

**Casos de prueba:**
- ✅ Título encontrado: Búsqueda parcial funciona
- ✅ Múltiples resultados: Varios libros con título similar
- ✅ Case-insensitive: `"quijote"` encuentra `"El Quijote"`
- ❌ No encontrado: Título que no existe
- ❌ Null o vacío: Retorna lista vacía

### Pruebas para `buscarPorAutor(String autor)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testBuscarPorAutor_Encontrado` | Buscar por autor existente | Lista con resultados |
| 2 | `testBuscarPorAutor_MultiplesLibros` | Autor con múltiples libros | Lista con múltiples resultados |

**Casos de prueba:**
- ✅ Autor encontrado: Búsqueda parcial funciona
- ✅ Múltiples libros: Autor con varios libros

### Pruebas para `listarTodos()`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testListarTodos_ListaVacia` | Listar sin libros | Lista vacía |
| 2 | `testListarTodos_ConLibros` | Listar con libros registrados | Lista con todos los libros |

**Casos de prueba:**
- ✅ Lista vacía: Sin libros registrados
- ✅ Con libros: Retorna todos los libros

### Pruebas para `actualizarDisponibilidad(String isbn, int nuevaCantidad)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testActualizarDisponibilidad_Exitoso` | Actualizar disponibilidad válida | Disponibilidad actualizada |
| 2 | `testActualizarDisponibilidad_LibroNoExiste` | Actualizar libro inexistente | `LibroNoEncontradoException` |
| 3 | `testActualizarDisponibilidad_CantidadNegativa` | Actualizar con cantidad negativa | `IllegalArgumentException` |
| 4 | `testActualizarDisponibilidad_CantidadMayorATotal` | Cantidad disponible > cantidad total | `IllegalArgumentException` |

**Casos de prueba:**
- ✅ Actualización exitosa: Cantidad válida
- ❌ Libro no existe: ISBN inexistente
- ❌ Cantidad negativa: `-1`
- ❌ Cantidad excede total: Disponible > Total

### Pruebas para `existeLibro(String isbn)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testExisteLibro_Existe` | Verificar libro existente | `true` |
| 2 | `testExisteLibro_NoExiste` | Verificar libro inexistente | `false` |

---

## PrestamoServiceTest

### Objetivo
Verificar que la gestión de préstamos funcione correctamente, incluyendo validaciones de disponibilidad y límites.

### Pruebas para `prestarLibro(String isbn, String idUsuario)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testPrestarLibro_Exitoso` | Prestar libro disponible | Préstamo creado, disponibilidad disminuye |
| 2 | `testPrestarLibro_LibroNoDisponible` | Prestar libro sin disponibilidad | `LibroNoDisponibleException` |
| 3 | `testPrestarLibro_LimiteAlcanzado` | Prestar cuando usuario tiene 3 libros | `LimitePrestamosException` |
| 4 | `testPrestarLibro_LibroNoExiste` | Prestar libro inexistente | `LibroNoEncontradoException` |
| 5 | `testPrestarLibro_UsuarioCon2Libros_PuedePrestar` | Usuario con 2 libros puede prestar 1 más | Préstamo exitoso |
| 6 | `testPrestarLibro_UsuarioCon3Libros_NoPuedePrestar` | Usuario con 3 libros no puede prestar más | `LimitePrestamosException` |

**Casos de prueba:**
- ✅ Préstamo exitoso: Libro disponible, usuario con menos de 3 préstamos
- ✅ Disponibilidad actualizada: Cantidad disponible disminuye
- ❌ Libro no disponible: Cantidad disponible = 0
- ❌ Límite alcanzado: Usuario ya tiene 3 préstamos activos
- ❌ Libro no existe: ISBN inexistente
- ✅ Usuario con 2 libros: Puede prestar un 3er libro
- ❌ Usuario con 3 libros: No puede prestar un 4to libro

### Pruebas para `devolverLibro(String isbn, String idUsuario)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testDevolverLibro_Exitoso` | Devolver libro prestado | Préstamo marcado como inactivo, disponibilidad aumenta |
| 2 | `testDevolverLibro_NoPrestado` | Devolver libro no prestado | `PrestamoNoEncontradoException` |
| 3 | `testDevolverLibro_LibroNoExiste` | Devolver libro inexistente | `LibroNoEncontradoException` |

**Casos de prueba:**
- ✅ Devolución exitosa: Libro estaba prestado
- ✅ Disponibilidad actualizada: Cantidad disponible aumenta
- ✅ Préstamo inactivo: Préstamo marcado como devuelto
- ❌ No prestado: Libro no está prestado por ese usuario
- ❌ Libro no existe: ISBN inexistente

### Pruebas para `contarPrestamosActivos(String idUsuario)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testContarPrestamosActivos_UsuarioSinPrestamos` | Usuario sin préstamos | `0` |
| 2 | `testContarPrestamosActivos_UsuarioCon1Prestamo` | Usuario con 1 préstamo | `1` |
| 3 | `testContarPrestamosActivos_UsuarioCon3Prestamos` | Usuario con 3 préstamos | `3` |
| 4 | `testContarPrestamosActivos_NoCuentaDevueltos` | No contar préstamos devueltos | Solo cuenta activos |

**Casos de prueba:**
- ✅ Sin préstamos: Retorna `0`
- ✅ Con 1 préstamo: Retorna `1`
- ✅ Con 3 préstamos: Retorna `3`
- ✅ No cuenta devueltos: Solo cuenta préstamos activos

### Pruebas para `obtenerPrestamosActivos(String idUsuario)`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testObtenerPrestamosActivos_ListaVacia` | Usuario sin préstamos activos | Lista vacía |
| 2 | `testObtenerPrestamosActivos_ListaConPrestamos` | Usuario con préstamos activos | Lista con préstamos activos |
| 3 | `testObtenerPrestamosActivos_NoIncluyeOtrosUsuarios` | Solo préstamos del usuario especificado | Lista filtrada por usuario |

**Casos de prueba:**
- ✅ Lista vacía: Usuario sin préstamos
- ✅ Con préstamos: Lista con todos los préstamos activos del usuario
- ✅ Filtrado por usuario: No incluye préstamos de otros usuarios
- ✅ Solo activos: No incluye préstamos devueltos

### Pruebas para `listarTodosLosPrestamos()`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testListarTodosLosPrestamos_IncluyeActivosEInactivos` | Listar todos los préstamos | Incluye activos e inactivos |

**Casos de prueba:**
- ✅ Todos los préstamos: Activos e inactivos
- ✅ Múltiples usuarios: Préstamos de todos los usuarios

---

## LibroTest

### Objetivo
Verificar que el modelo Libro funcione correctamente, incluyendo métodos de negocio.

### Pruebas para `estaDisponible()`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testEstaDisponible_ConDisponibilidad` | Libro con disponibilidad > 0 | `true` |
| 2 | `testEstaDisponible_SinDisponibilidad` | Libro con disponibilidad = 0 | `false` |

**Casos de prueba:**
- ✅ Con disponibilidad: `cantidadDisponible > 0` → `true`
- ❌ Sin disponibilidad: `cantidadDisponible = 0` → `false`

### Pruebas para `prestar()`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testPrestar_DisminuyeDisponibilidad` | Prestar disminuye disponibilidad | Disponibilidad - 1 |
| 2 | `testPrestar_NoPuedeSerNegativo` | Prestar cuando disponibilidad = 0 | Disponibilidad sigue en 0 |

**Casos de prueba:**
- ✅ Disminuye disponibilidad: `5` → `4`
- ✅ No negativo: Si `disponible = 0`, sigue en `0`

### Pruebas para `devolver()`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testDevolver_AumentaDisponibilidad` | Devolver aumenta disponibilidad | Disponibilidad + 1 |
| 2 | `testDevolver_NoPuedeExcederTotal` | Devolver cuando ya está al máximo | Disponibilidad no excede total |

**Casos de prueba:**
- ✅ Aumenta disponibilidad: `3` → `4`
- ✅ No excede total: Si `disponible = total`, sigue igual

### Pruebas para `equals()` y `hashCode()`

| # | Nombre de Prueba | Descripción | Resultado Esperado |
|---|------------------|-------------|-------------------|
| 1 | `testEquals_MismoISBN` | Libros con mismo ISBN son iguales | `true` |
| 2 | `testEquals_DiferenteISBN` | Libros con diferente ISBN no son iguales | `false` |
| 3 | `testHashCode_MismoISBN` | Libros con mismo ISBN tienen mismo hashCode | Mismo hashCode |

**Casos de prueba:**
- ✅ Mismo ISBN: Libros iguales independientemente de otros campos
- ❌ Diferente ISBN: Libros diferentes
- ✅ HashCode consistente: Mismo ISBN = mismo hashCode

---

## 📊 Resumen de Pruebas

### Total de Pruebas por Clase

| Clase de Prueba | Número de Pruebas |
|----------------|-------------------|
| `ValidacionServiceTest` | 20+ |
| `LibroServiceTest` | 25+ |
| `PrestamoServiceTest` | 20+ |
| `LibroTest` | 10+ |
| **TOTAL** | **75+** |

### Cobertura Esperada

- ✅ **Cobertura de líneas**: > 90%
- ✅ **Cobertura de métodos**: 100%
- ✅ **Cobertura de clases**: 100%
- ✅ **Cobertura de ramas**: > 85%

### Tipos de Pruebas Incluidas

1. **Pruebas de éxito** (Happy Path): Funcionalidad normal
2. **Pruebas de error**: Manejo de excepciones
3. **Pruebas de límite**: Valores extremos (0, máximo, mínimo)
4. **Pruebas de validación**: Datos inválidos (null, vacío, formato incorrecto)
5. **Pruebas de integración**: Interacción entre métodos

---

## ✅ Criterios de Aceptación

Para considerar que las pruebas están completas, deben cumplirse:

1. ✅ Todas las pruebas pasan (verde)
2. ✅ Cobertura de código > 80%
3. ✅ Todos los casos límite cubiertos
4. ✅ Todas las excepciones probadas
5. ✅ Nombres descriptivos en todas las pruebas
6. ✅ Patrón AAA aplicado consistentemente
7. ✅ Uso correcto de `@BeforeEach` para setup