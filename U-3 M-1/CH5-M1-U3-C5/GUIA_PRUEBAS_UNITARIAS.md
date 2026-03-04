# Guía Básica: Introducción a Pruebas Unitarias con JUnit

## 📚 Introducción

Esta guía te introducirá al mundo de las **pruebas unitarias** en Java usando **JUnit**, el framework de testing más popular. Si eres nuevo en esto, no te preocupes: empezaremos desde lo más básico y te explicaremos cada concepto paso a paso.

**¿Qué aprenderás?**
- Qué son las pruebas unitarias y por qué son importantes
- Cómo configurar JUnit en un proyecto Maven
- Cómo escribir y ejecutar tus primeras pruebas
- Buenas prácticas para escribir tests efectivos

---

## 🎯 ¿Qué son las Pruebas Unitarias?

### Definición Simple

Una **prueba unitaria** es un pequeño programa que verifica que una parte específica de tu código (una "unidad") funciona correctamente.

**Ejemplo simple:**
Imagina que tienes un método que suma dos números:

```java
public int sumar(int a, int b) {
    return a + b;
}
```

Una prueba unitaria sería algo como:

```java
@Test
public void testSumar() {
    int resultado = sumar(2, 3);
    assertEquals(5, resultado);  // Verifica que 2 + 3 = 5
}
```

### ¿Qué es una "Unidad"?

Una **unidad** puede ser:
- Un método individual
- Una función
- Una clase pequeña
- Un componente que hace una tarea específica

**Importante:** Una prueba unitaria prueba **una sola cosa** a la vez.

---

## 🤔 ¿Por Qué son Importantes las Pruebas Unitarias?

### 1. **Detectan Errores Temprano**

Cuando escribes código, es fácil cometer errores. Las pruebas unitarias te ayudan a encontrarlos **antes** de que lleguen a producción.

**Ejemplo:**
```java
// Código con error
public int dividir(int a, int b) {
    return a / b;  // ¿Qué pasa si b es 0?
}

// Prueba que detecta el error
@Test
public void testDividirPorCero() {
    // Esta prueba fallará y te alertará del problema
    dividir(10, 0);
}
```

### 2. **Te Dan Confianza para Cambiar Código**

Cuando necesitas modificar código existente, las pruebas te aseguran que **no rompiste nada**.

**Escenario:**
- Tienes 100 pruebas que pasan
- Modificas un método
- Ejecutas las pruebas de nuevo
- Si todas pasan → ✅ Tu cambio no rompió nada
- Si alguna falla → ⚠️ Sabes exactamente qué se rompió

### 3. **Documentan Cómo Debe Funcionar el Código**

Las pruebas son como **ejemplos vivos** de cómo usar tu código. Si alguien lee tus pruebas, entenderá cómo funciona tu método.

**Ejemplo:**
```java
@Test
public void testValidarEmail() {
    // Esto muestra que validarEmail debe retornar true para emails válidos
    assertTrue(validarEmail("usuario@example.com"));
    assertFalse(validarEmail("email-invalido"));
}
```

### 4. **Ahorran Tiempo a Largo Plazo**

Aunque escribir pruebas toma tiempo al principio, **ahorran mucho más tiempo** después:
- No necesitas probar manualmente cada vez que cambias algo
- Encuentras errores más rápido
- Reduces el tiempo de depuración

### 5. **Mejoran la Calidad del Código**

Cuando sabes que tu código será probado, tiendes a escribir código:
- Más claro
- Más simple
- Mejor estructurado

---

## 🛠️ Instalación y Configuración de JUnit en Maven

### ¿Qué es JUnit?

**JUnit** es un framework (herramienta) que te ayuda a escribir y ejecutar pruebas unitarias en Java. Es el estándar de la industria.

### Paso 1: Agregar Dependencias en pom.xml

Abre tu archivo `pom.xml` y agrega estas dependencias dentro de la etiqueta `<dependencies>`:

```xml
<dependencies>
    <!-- JUnit 5 (Jupiter) - La versión más moderna y recomendada -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

**Explicación:**
- `junit-jupiter`: Es JUnit 5, la versión más moderna
- `version="5.10.0"`: Versión estable y actual
- `<scope>test</scope>`: Solo se usa para pruebas, no se incluye en el código de producción

### Paso 2: Verificar la Estructura de Carpetas

Tu proyecto debe tener esta estructura:

```
tu-proyecto/
├── src/
│   ├── main/
│   │   └── java/          ← Tu código de producción aquí
│   └── test/
│       └── java/          ← Tus pruebas aquí
└── pom.xml
```

**Importante:** 
- El código de producción va en `src/main/java/`
- Las pruebas van en `src/test/java/`

### Paso 3: Compilar y Verificar

Ejecuta en la terminal:

```bash
mvn clean compile
```

Si no hay errores, ¡la configuración está correcta!

---

## 📝 Estructura de un Test Unitario

### Estructura Básica

Un test unitario tiene tres partes principales:

```java
@Test
public void nombreDelTest() {
    // 1. ARRANGE (Preparar): Configura los datos necesarios
    int numero1 = 5;
    int numero2 = 3;
    
    // 2. ACT (Actuar): Ejecuta el método que quieres probar
    int resultado = sumar(numero1, numero2);
    
    // 3. ASSERT (Verificar): Comprueba que el resultado es el esperado
    assertEquals(8, resultado);
}
```

### Explicación de las 3 A's (AAA Pattern)

#### 1. **ARRANGE (Preparar)**
Preparas todo lo necesario para la prueba:
- Creas variables
- Inicializas objetos
- Configuras datos de prueba

#### 2. **ACT (Actuar)**
Ejecutas el método o código que quieres probar.

#### 3. **ASSERT (Verificar)**
Verificas que el resultado sea el esperado usando métodos como `assertEquals`, `assertTrue`, etc.

### Ejemplo Completo

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculadoraTest {
    
    @Test
    public void testSumar() {
        // ARRANGE: Preparar
        Calculadora calc = new Calculadora();
        int a = 10;
        int b = 5;
        
        // ACT: Actuar
        int resultado = calc.sumar(a, b);
        
        // ASSERT: Verificar
        assertEquals(15, resultado);
    }
}
```

---

## 🏷️ Anotaciones Comunes de JUnit

Las **anotaciones** son palabras especiales que empiezan con `@` y le dicen a JUnit qué hacer.

### @Test

**¿Para qué sirve?**
Marca un método como una prueba unitaria. JUnit ejecutará todos los métodos marcados con `@Test`.

**Ejemplo:**
```java
@Test
public void testMultiplicar() {
    Calculadora calc = new Calculadora();
    assertEquals(20, calc.multiplicar(4, 5));
}
```

**Reglas importantes:**
- El método debe ser `public`
- El método debe ser `void` (no retorna nada)
- El nombre puede empezar con `test` (opcional, pero es buena práctica)

### @BeforeEach

**¿Para qué sirve?**
Ejecuta código **antes de cada prueba**. Útil para preparar datos que todas las pruebas necesitan.

**Ejemplo:**
```java
public class CalculadoraTest {
    private Calculadora calc;
    
    @BeforeEach
    public void setUp() {
        // Este método se ejecuta ANTES de cada @Test
        calc = new Calculadora();
        System.out.println("Preparando para la prueba...");
    }
    
    @Test
    public void testSumar() {
        // Aquí calc ya está inicializado gracias a @BeforeEach
        assertEquals(5, calc.sumar(2, 3));
    }
    
    @Test
    public void testRestar() {
        // Aquí también calc está inicializado
        assertEquals(2, calc.restar(5, 3));
    }
}
```

**Ventaja:** No necesitas crear `Calculadora` en cada test, `@BeforeEach` lo hace por ti.

### @AfterEach

**¿Para qué sirve?**
Ejecuta código **después de cada prueba**. Útil para limpiar recursos o resetear estados.

**Ejemplo:**
```java
public class CalculadoraTest {
    private Calculadora calc;
    
    @BeforeEach
    public void setUp() {
        calc = new Calculadora();
    }
    
    @AfterEach
    public void tearDown() {
        // Este método se ejecuta DESPUÉS de cada @Test
        calc = null;  // Limpiar recursos
        System.out.println("Limpieza completada");
    }
    
    @Test
    public void testSumar() {
        assertEquals(5, calc.sumar(2, 3));
    }
}
```

**Cuándo usarlo:**
- Cerrar conexiones a bases de datos
- Limpiar archivos temporales
- Resetear variables globales

### Resumen de Anotaciones

| Anotación | Cuándo se ejecuta | Propósito |
|-----------|-------------------|-----------|
| `@Test` | Cuando ejecutas las pruebas | Marca un método como prueba |
| `@BeforeEach` | Antes de cada `@Test` | Preparar datos/objetos |
| `@AfterEach` | Después de cada `@Test` | Limpiar recursos |

---

## ✅ Métodos de Aserción (Assertions)

Los métodos de aserción verifican que algo sea cierto. Si la aserción falla, la prueba falla.

### assertEquals

Verifica que dos valores sean **iguales**.

```java
@Test
public void testIgualdad() {
    int resultado = 5;
    assertEquals(5, resultado);  // Pasa si resultado == 5
    assertEquals(10, resultado); // Falla si resultado != 10
}
```

**Sintaxis:** `assertEquals(valorEsperado, valorActual)`

### assertTrue / assertFalse

Verifica que una condición sea **verdadera** o **falsa**.

```java
@Test
public void testVerdadero() {
    boolean esMayor = 10 > 5;
    assertTrue(esMayor);  // Pasa si esMayor es true
}

@Test
public void testFalso() {
    boolean esMenor = 10 < 5;
    assertFalse(esMenor);  // Pasa si esMenor es false
}
```

### assertNull / assertNotNull

Verifica que un valor sea **null** o **no null**.

```java
@Test
public void testNull() {
    String texto = null;
    assertNull(texto);  // Pasa si texto es null
}

@Test
public void testNotNull() {
    String texto = "Hola";
    assertNotNull(texto);  // Pasa si texto NO es null
}
```

### assertThrows

Verifica que un método lance una **excepción**.

```java
@Test
public void testExcepcion() {
    Calculadora calc = new Calculadora();
    // Verifica que dividir por 0 lance una excepción
    assertThrows(ArithmeticException.class, () -> {
        calc.dividir(10, 0);
    });
}
```

### Resumen de Assertions Comunes

| Método | Propósito |
|--------|-----------|
| `assertEquals(esperado, actual)` | Verifica igualdad |
| `assertTrue(condicion)` | Verifica que sea true |
| `assertFalse(condicion)` | Verifica que sea false |
| `assertNull(objeto)` | Verifica que sea null |
| `assertNotNull(objeto)` | Verifica que NO sea null |
| `assertThrows(excepcion, codigo)` | Verifica que lance excepción |

---

## 💡 Ejemplo Práctico: Pruebas a Métodos de Validación

Vamos a crear un ejemplo completo desde cero.

### Paso 1: Crear la Clase a Probar

**Archivo:** `src/main/java/org/example/Validador.java`

```java
package org.example;

public class Validador {
    
    /**
     * Valida si un email tiene formato correcto
     */
    public boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }
    
    /**
     * Valida si una contraseña cumple con los requisitos:
     * - Al menos 8 caracteres
     * - Al menos una mayúscula
     * - Al menos un número
     */
    public boolean validarPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean tieneMayuscula = false;
        boolean tieneNumero = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            }
            if (Character.isDigit(c)) {
                tieneNumero = true;
            }
        }
        
        return tieneMayuscula && tieneNumero;
    }
    
    /**
     * Valida si un número está en un rango específico
     */
    public boolean validarRango(int numero, int min, int max) {
        return numero >= min && numero <= max;
    }
}
```

### Paso 2: Crear las Pruebas

**Archivo:** `src/test/java/org/example/ValidadorTest.java`

```java
package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidadorTest {
    
    private Validador validador;
    
    @BeforeEach
    public void setUp() {
        // Se ejecuta antes de cada prueba
        validador = new Validador();
    }
    
    // ========== Pruebas para validarEmail ==========
    
    @Test
    public void testValidarEmail_Correcto() {
        // ARRANGE
        String email = "usuario@example.com";
        
        // ACT
        boolean resultado = validador.validarEmail(email);
        
        // ASSERT
        assertTrue(resultado, "El email válido debe retornar true");
    }
    
    @Test
    public void testValidarEmail_SinArroba() {
        // ARRANGE
        String email = "usuarioexample.com";
        
        // ACT
        boolean resultado = validador.validarEmail(email);
        
        // ASSERT
        assertFalse(resultado, "Email sin @ debe retornar false");
    }
    
    @Test
    public void testValidarEmail_Null() {
        // ARRANGE
        String email = null;
        
        // ACT
        boolean resultado = validador.validarEmail(email);
        
        // ASSERT
        assertFalse(resultado, "Email null debe retornar false");
    }
    
    @Test
    public void testValidarEmail_Vacio() {
        // ARRANGE
        String email = "";
        
        // ACT
        boolean resultado = validador.validarEmail(email);
        
        // ASSERT
        assertFalse(resultado, "Email vacío debe retornar false");
    }
    
    // ========== Pruebas para validarPassword ==========
    
    @Test
    public void testValidarPassword_Correcto() {
        // ARRANGE
        String password = "Password123";
        
        // ACT
        boolean resultado = validador.validarPassword(password);
        
        // ASSERT
        assertTrue(resultado, "Password válido debe retornar true");
    }
    
    @Test
    public void testValidarPassword_MuyCorto() {
        // ARRANGE
        String password = "Pass1";  // Solo 5 caracteres
        
        // ACT
        boolean resultado = validador.validarPassword(password);
        
        // ASSERT
        assertFalse(resultado, "Password muy corto debe retornar false");
    }
    
    @Test
    public void testValidarPassword_SinMayuscula() {
        // ARRANGE
        String password = "password123";  // Sin mayúscula
        
        // ACT
        boolean resultado = validador.validarPassword(password);
        
        // ASSERT
        assertFalse(resultado, "Password sin mayúscula debe retornar false");
    }
    
    @Test
    public void testValidarPassword_SinNumero() {
        // ARRANGE
        String password = "Password";  // Sin número
        
        // ACT
        boolean resultado = validador.validarPassword(password);
        
        // ASSERT
        assertFalse(resultado, "Password sin número debe retornar false");
    }
    
    // ========== Pruebas para validarRango ==========
    
    @Test
    public void testValidarRango_DentroDelRango() {
        // ARRANGE
        int numero = 5;
        int min = 1;
        int max = 10;
        
        // ACT
        boolean resultado = validador.validarRango(numero, min, max);
        
        // ASSERT
        assertTrue(resultado, "Número dentro del rango debe retornar true");
    }
    
    @Test
    public void testValidarRango_MenorAlMinimo() {
        // ARRANGE
        int numero = 0;
        int min = 1;
        int max = 10;
        
        // ACT
        boolean resultado = validador.validarRango(numero, min, max);
        
        // ASSERT
        assertFalse(resultado, "Número menor al mínimo debe retornar false");
    }
    
    @Test
    public void testValidarRango_MayorAlMaximo() {
        // ARRANGE
        int numero = 15;
        int min = 1;
        int max = 10;
        
        // ACT
        boolean resultado = validador.validarRango(numero, min, max);
        
        // ASSERT
        assertFalse(resultado, "Número mayor al máximo debe retornar false");
    }
    
    @Test
    public void testValidarRango_EnElLimite() {
        // ARRANGE
        int numero = 10;
        int min = 1;
        int max = 10;
        
        // ACT
        boolean resultado = validador.validarRango(numero, min, max);
        
        // ASSERT
        assertTrue(resultado, "Número en el límite debe retornar true");
    }
}
```

### Paso 3: Ejecutar las Pruebas

**Opción 1: Desde la terminal (Maven)**
```bash
mvn test
```

**Opción 2: Desde tu IDE**
- Click derecho en la clase de test
- Selecciona "Run Tests" o "Ejecutar Pruebas"

### ¿Qué Deberías Ver?

Si todo está bien, verás algo como:

```
Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
```

Esto significa que **todas las pruebas pasaron** ✅

---

## 🤖 Uso de IA como Apoyo para Generar Casos de Prueba

### ¿Por Qué Usar IA?

La IA puede ayudarte a:
- **Generar casos de prueba** que tal vez no habías pensado
- **Encontrar casos límite** (edge cases)
- **Ahorrar tiempo** escribiendo pruebas repetitivas
- **Aprender** viendo ejemplos generados

### Cómo Usar IA para Generar Pruebas

#### Ejemplo 1: Pedir Casos de Prueba

**Prompt para la IA:**
```
Tengo este método en Java:

public boolean validarEmail(String email) {
    if (email == null || email.isEmpty()) {
        return false;
    }
    return email.contains("@") && email.contains(".");
}

Genera casos de prueba unitarios con JUnit 5 que cubran:
- Casos válidos
- Casos inválidos
- Casos límite (null, vacío, etc.)
```

#### Ejemplo 2: Pedir Casos Límite

**Prompt para la IA:**
```
¿Qué casos límite debería probar para un método que valida contraseñas?
El método requiere:
- Al menos 8 caracteres
- Al menos una mayúscula
- Al menos un número
```

#### Ejemplo 3: Mejorar Pruebas Existentes

**Prompt para la IA:**
```
Revisa estas pruebas y sugiere casos adicionales que debería probar:

@Test
public void testSumar() {
    assertEquals(5, sumar(2, 3));
}
```

### Buenas Prácticas al Usar IA

1. **No copies ciegamente:** Revisa y entiende lo que la IA genera
2. **Aprende de los ejemplos:** Usa la IA para aprender, no solo para copiar
3. **Personaliza:** Adapta los ejemplos a tu código específico
4. **Verifica:** Siempre ejecuta las pruebas generadas para asegurarte de que funcionan

### Ejemplo de Prompt Completo

```
Necesito crear pruebas unitarias para esta clase Java:

public class Calculadora {
    public int sumar(int a, int b) { return a + b; }
    public int restar(int a, int b) { return a - b; }
    public int multiplicar(int a, int b) { return a * b; }
    public int dividir(int a, int b) { return a / b; }
}

Genera:
1. Pruebas para casos normales
2. Pruebas para casos límite (números negativos, cero, etc.)
3. Pruebas para manejo de excepciones (división por cero)
4. Usa JUnit 5 con @BeforeEach para inicializar la calculadora
5. Incluye mensajes descriptivos en las aserciones
```

---

## ✅ Buenas Prácticas de Testing

### 1. Nombres Descriptivos

**❌ Mal:**
```java
@Test
public void test1() { ... }
```

**✅ Bien:**
```java
@Test
public void testValidarEmail_RetornaFalse_CuandoEmailEsNull() { ... }
```

### 2. Una Prueba, Una Verificación

**❌ Mal:**
```java
@Test
public void testTodo() {
    assertTrue(validarEmail("test@test.com"));
    assertTrue(validarPassword("Password123"));
    assertTrue(validarRango(5, 1, 10));
}
```

**✅ Bien:**
```java
@Test
public void testValidarEmail() {
    assertTrue(validarEmail("test@test.com"));
}

@Test
public void testValidarPassword() {
    assertTrue(validarPassword("Password123"));
}
```

### 3. Prueba Casos Límite

Siempre prueba:
- Valores null
- Strings vacíos
- Valores en los límites (mínimo, máximo)
- Valores negativos (si aplica)
- Valores muy grandes

### 4. Usa @BeforeEach para Preparación

**❌ Mal:**
```java
@Test
public void test1() {
    Validador val = new Validador();
    // ...
}

@Test
public void test2() {
    Validador val = new Validador();  // Repetido
    // ...
}
```

**✅ Bien:**
```java
private Validador validador;

@BeforeEach
public void setUp() {
    validador = new Validador();
}

@Test
public void test1() {
    // validador ya está inicializado
}
```

### 5. Mensajes Descriptivos en Aserciones

**❌ Mal:**
```java
assertEquals(5, resultado);
```

**✅ Bien:**
```java
assertEquals(5, resultado, "La suma de 2 + 3 debe ser 5");
```

### 6. Organiza tus Pruebas

Agrupa pruebas relacionadas con comentarios:

```java
// ========== Pruebas para validarEmail ==========

@Test
public void testValidarEmail_Correcto() { ... }

@Test
public void testValidarEmail_Incorrecto() { ... }

// ========== Pruebas para validarPassword ==========

@Test
public void testValidarPassword_Correcto() { ... }
```

---

## 🎓 Resumen

### Conceptos Clave

1. **Prueba unitaria:** Verifica que una parte pequeña de código funciona correctamente
2. **JUnit:** Framework para escribir pruebas en Java
3. **@Test:** Marca un método como prueba
4. **@BeforeEach / @AfterEach:** Ejecuta código antes/después de cada prueba
5. **Assertions:** Métodos que verifican resultados (assertEquals, assertTrue, etc.)

### Estructura de una Prueba

```
ARRANGE (Preparar) → ACT (Actuar) → ASSERT (Verificar)
```

### Comandos Útiles

```bash
# Compilar y ejecutar pruebas
mvn test

# Solo compilar
mvn compile

# Limpiar y compilar
mvn clean compile
```

### Siguientes Pasos

1. ✅ Escribe tus primeras pruebas
2. ✅ Ejecuta las pruebas regularmente
3. ✅ Aprende más anotaciones (@BeforeAll, @AfterAll, @DisplayName)
4. ✅ Explora más assertions (assertArrayEquals, assertSame, etc.)
5. ✅ Aprende sobre cobertura de código

---

## 📚 Recursos Adicionales

- [Documentación oficial de JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [JUnit 5 Assertions](https://junit.org/junit5/docs/current/api/org.junit.jupiter.api/org/junit/jupiter/api/Assertions.html)

---

**¡Felicidades! Ahora estás listo para escribir tus primeras pruebas unitarias. ¡Practica mucho y no tengas miedo de experimentar!** 🚀

