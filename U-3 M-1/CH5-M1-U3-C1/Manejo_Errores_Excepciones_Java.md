# Manejo de Errores y Excepciones en Java

## Objetivos de Aprendizaje

- Comprender la importancia del manejo de errores en aplicaciones Java
- Diferenciar entre errores, excepciones verificadas y no verificadas
- Aplicar try-catch-finally en situaciones prácticas
- Crear y lanzar excepciones personalizadas

---

## 1. Concepto de Errores y Excepciones en Java

### ¿Qué son las Excepciones?

Una **excepción** es un evento que interrumpe el flujo normal de ejecución de un programa. En Java, las excepciones son objetos que representan condiciones anómalas que pueden ocurrir durante la ejecución.

### ¿Por qué son Importantes?

- **Robustez**: Permiten que el programa maneje situaciones inesperadas sin terminar abruptamente
- **Depuración**: Facilitan la identificación de problemas
- **Mantenibilidad**: Mejoran la legibilidad y mantenimiento del código
- **Experiencia de usuario**: Permiten mostrar mensajes de error comprensibles

---

## 2. Jerarquía de Clases de Excepciones

```
Throwable
├── Error (No verificadas)
│   ├── OutOfMemoryError
│   ├── StackOverflowError
│   └── ...
└── Exception
    ├── RuntimeException (No verificadas)
    │   ├── NullPointerException
    │   ├── ArrayIndexOutOfBoundsException
    │   ├── IllegalArgumentException
    │   └── ...
    └── Otras Excepciones (Verificadas)
        ├── IOException
        ├── SQLException
        ├── ClassNotFoundException
        └── ...
```

### Tipos de Excepciones

#### **Errores (Error)**
- Representan problemas graves del sistema
- No deben ser capturadas normalmente
- Ejemplos: `OutOfMemoryError`, `StackOverflowError`

#### **Excepciones Verificadas (Checked Exceptions)**
- Deben ser manejadas explícitamente (try-catch o throws)
- El compilador las verifica
- Heredan de `Exception` pero NO de `RuntimeException`
- Ejemplos: `IOException`, `SQLException`, `FileNotFoundException`

#### **Excepciones No Verificadas (Unchecked Exceptions)**
- No requieren manejo explícito
- Heredan de `RuntimeException`
- Ejemplos: `NullPointerException`, `ArrayIndexOutOfBoundsException`, `IllegalArgumentException`

---

## 3. Uso de Bloques try, catch, finally y throw

### Sintaxis Básica

```java
try {
    // Código que puede lanzar una excepción
} catch (TipoExcepcion e) {
    // Manejo de la excepción
} finally {
    // Código que siempre se ejecuta
}
```

### Ejemplo 1: Manejo Básico

```java
public class EjemploBasico {
    public static void main(String[] args) {
        try {
            int resultado = 10 / 0; // Lanza ArithmeticException
            System.out.println("Resultado: " + resultado);
        } catch (ArithmeticException e) {
            System.out.println("Error: División por cero");
            System.out.println("Mensaje: " + e.getMessage());
        } finally {
            System.out.println("Este bloque siempre se ejecuta");
        }
    }
}
```

### Ejemplo 2: Múltiples catch

```java
public class MultiplesCatch {
    public static void main(String[] args) {
        try {
            int[] numeros = {1, 2, 3};
            System.out.println(numeros[5]); // ArrayIndexOutOfBoundsException
            String texto = null;
            System.out.println(texto.length()); // NullPointerException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Índice fuera de rango");
        } catch (NullPointerException e) {
            System.out.println("Referencia nula");
        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }
    }
}
```

### Ejemplo 3: try-with-resources (Java 7+)

```java
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class TryWithResources {
    public static void main(String[] args) {
        // try-with-resources cierra automáticamente los recursos
        try (BufferedReader br = new BufferedReader(
                new FileReader("archivo.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        // No es necesario cerrar el BufferedReader manualmente
    }
}
```

### Ejemplo 4: Uso de throw

```java
public class UsoThrow {
    public static void validarEdad(int edad) {
        if (edad < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        if (edad > 120) {
            throw new IllegalArgumentException("La edad no puede ser mayor a 120");
        }
        System.out.println("Edad válida: " + edad);
    }
    
    public static void main(String[] args) {
        try {
            validarEdad(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

### Ejemplo 5: throws en la Declaración de Métodos

```java
import java.io.FileReader;
import java.io.IOException;

public class UsoThrows {
    // El método declara que puede lanzar IOException
    public static void leerArchivo(String nombre) throws IOException {
        FileReader fr = new FileReader(nombre);
        // ... código para leer
        fr.close();
    }
    
    public static void main(String[] args) {
        try {
            leerArchivo("datos.txt");
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
    }
}
```

---

## 4. Creación de Excepciones Personalizadas

### Ejemplo 1: Excepción Personalizada Simple

```java
// Excepción personalizada no verificada
public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

// Uso
public class CuentaBancaria {
    private double saldo;
    
    public void retirar(double cantidad) {
        if (cantidad > saldo) {
            throw new SaldoInsuficienteException(
                "Saldo insuficiente. Saldo actual: " + saldo
            );
        }
        saldo -= cantidad;
    }
}
```

### Ejemplo 2: Excepción Personalizada Verificada

```java
// Excepción personalizada verificada
public class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
    public UsuarioNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

// Uso
public class ServicioUsuario {
    public Usuario buscarUsuario(String id) throws UsuarioNoEncontradoException {
        // Simulación de búsqueda
        if (id == null || id.isEmpty()) {
            throw new UsuarioNoEncontradoException("ID de usuario inválido");
        }
        // Lógica de búsqueda...
        throw new UsuarioNoEncontradoException("Usuario con ID " + id + " no encontrado");
    }
}
```

### Ejemplo 3: Excepción con Información Adicional

```java
public class ValidacionException extends Exception {
    private String campo;
    private Object valor;
    
    public ValidacionException(String campo, Object valor, String mensaje) {
        super(mensaje);
        this.campo = campo;
        this.valor = valor;
    }
    
    public String getCampo() {
        return campo;
    }
    
    public Object getValor() {
        return valor;
    }
    
    @Override
    public String toString() {
        return String.format("Error en campo '%s' con valor '%s': %s", 
                           campo, valor, getMessage());
    }
}
```

---

## 5. Buenas Prácticas para la Gestión de Errores

### ✅ Hacer

1. **Usar excepciones específicas**: Capturar excepciones específicas antes que generales
   ```java
   try {
       // código
   } catch (FileNotFoundException e) {
       // Manejo específico
   } catch (IOException e) {
       // Manejo más general
   }
   ```

2. **Proporcionar mensajes descriptivos**: Incluir información útil en los mensajes
   ```java
   throw new IllegalArgumentException("El valor debe ser positivo, pero se recibió: " + valor);
   ```

3. **Registrar excepciones**: Usar logging para excepciones importantes
   ```java
   catch (Exception e) {
       logger.error("Error al procesar solicitud", e);
       throw e;
   }
   ```

4. **Limpiar recursos**: Usar try-with-resources o finally para liberar recursos
   ```java
   try (Connection conn = getConnection()) {
       // usar conexión
   } // Se cierra automáticamente
   ```

5. **No capturar y silenciar**: Evitar catch vacíos sin manejo
   ```java
   // ❌ MAL
   catch (Exception e) {
       // Nada
   }
   
   // ✅ BIEN
   catch (Exception e) {
       logger.error("Error procesado", e);
       // o re-lanzar, o manejar apropiadamente
   }
   ```

### ❌ Evitar

1. **Capturar Exception genérica sin necesidad**
2. **Ignorar excepciones silenciosamente**
3. **Usar excepciones para control de flujo normal**

```java
❌ Evitar
try {
    if (edad < 18) {
        throw new RuntimeException();
    }
    procesar();
} catch (RuntimeException e) {
    System.out.println("Menor de edad");
}

✅ Forma correcta
if (edad < 18) {
    System.out.println("Menor de edad");
} else {
    procesar();
}
```

4. **Exponer detalles internos al usuario final**
5. **No cerrar recursos adecuadamente**

---

## 6. Ejemplo Práctico: Validación de Datos de Usuario

```java
import java.util.regex.Pattern;

// Excepciones personalizadas
class EmailInvalidoException extends Exception {
    public EmailInvalidoException(String mensaje) {
        super(mensaje);
    }
}

class EdadInvalidaException extends Exception {
    public EdadInvalidaException(String mensaje) {
        super(mensaje);
    }
}

class PasswordDebilException extends Exception {
    public PasswordDebilException(String mensaje) {
        super(mensaje);
    }
}

// Clase Usuario
class Usuario {
    private String nombre;
    private String email;
    private int edad;
    private String password;
    
    public Usuario(String nombre, String email, int edad, String password) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
        this.password = password;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public int getEdad() { return edad; }
}

// Servicio de validación
class ServicioValidacion {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    public static void validarEmail(String email) throws EmailInvalidoException {
        if (email == null || email.trim().isEmpty()) {
            throw new EmailInvalidoException("El email no puede estar vacío");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new EmailInvalidoException("Formato de email inválido: " + email);
        }
    }
    
    public static void validarEdad(int edad) throws EdadInvalidaException {
        if (edad < 18) {
            throw new EdadInvalidaException(
                "La edad debe ser mayor o igual a 18 años. Edad recibida: " + edad
            );
        }
        if (edad > 100) {
            throw new EdadInvalidaException(
                "La edad no puede ser mayor a 100 años. Edad recibida: " + edad
            );
        }
    }
    
    public static void validarPassword(String password) throws PasswordDebilException {
        if (password == null || password.length() < 8) {
            throw new PasswordDebilException(
                "La contraseña debe tener al menos 8 caracteres"
            );
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new PasswordDebilException(
                "La contraseña debe contener al menos una letra mayúscula"
            );
        }
        if (!password.matches(".*[0-9].*")) {
            throw new PasswordDebilException(
                "La contraseña debe contener al menos un número"
            );
        }
    }
    
    public static Usuario crearUsuario(String nombre, String email, int edad, String password) 
            throws EmailInvalidoException, EdadInvalidaException, PasswordDebilException {
        
        validarEmail(email);
        validarEdad(edad);
        validarPassword(password);
        
        return new Usuario(nombre, email, edad, password);
    }
}

// Clase principal con ejemplos
public class ValidacionUsuario {
    public static void main(String[] args) {
        // Ejemplo 1: Usuario válido
        System.out.println("=== Ejemplo 1: Usuario válido ===");
        try {
            Usuario usuario1 = ServicioValidacion.crearUsuario(
                "Juan Pérez", 
                "juan@example.com", 
                25, 
                "Password123"
            );
            System.out.println("Usuario creado exitosamente: " + usuario1.getNombre());
        } catch (EmailInvalidoException | EdadInvalidaException | PasswordDebilException e) {
            System.out.println("Error de validación: " + e.getMessage());
        }
        
        // Ejemplo 2: Email inválido
        System.out.println("\n=== Ejemplo 2: Email inválido ===");
        try {
            Usuario usuario2 = ServicioValidacion.crearUsuario(
                "María García", 
                "email-invalido", 
                30, 
                "SecurePass123"
            );
        } catch (EmailInvalidoException e) {
            System.out.println("Error de email: " + e.getMessage());
        } catch (EdadInvalidaException e) {
            System.out.println("Error de edad: " + e.getMessage());
        } catch (PasswordDebilException e) {
            System.out.println("Error de contraseña: " + e.getMessage());
        }
        
        // Ejemplo 3: Edad inválida
        System.out.println("\n=== Ejemplo 3: Edad inválida ===");
        try {
            Usuario usuario3 = ServicioValidacion.crearUsuario(
                "Pedro López", 
                "pedro@example.com", 
                15, 
                "StrongPass456"
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Ejemplo 4: Password débil
        System.out.println("\n=== Ejemplo 4: Password débil ===");
        try {
            Usuario usuario4 = ServicioValidacion.crearUsuario(
                "Ana Martínez", 
                "ana@example.com", 
                28, 
                "weak"
            );
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Ejemplo 5: Múltiples errores
        System.out.println("\n=== Ejemplo 5: Múltiples errores ===");
        try {
            Usuario usuario5 = ServicioValidacion.crearUsuario(
                "Carlos Ruiz", 
                "mal-email", 
                10, 
                "123"
            );
        } catch (EmailInvalidoException e) {
            System.out.println("Error de email: " + e.getMessage());
        } catch (EdadInvalidaException e) {
            System.out.println("Error de edad: " + e.getMessage());
        } catch (PasswordDebilException e) {
            System.out.println("Error de contraseña: " + e.getMessage());
        } finally {
            System.out.println("Validación completada");
        }
    }
}
```

---

## Ejercicios Prácticos

### Ejercicio 1: Calculadora con Manejo de Errores

Crea una clase `Calculadora` que realice operaciones matemáticas básicas (suma, resta, multiplicación, división) con manejo adecuado de excepciones.

**Requisitos:**
- La división debe lanzar una excepción personalizada `DivisionPorCeroException` si el divisor es cero
- Validar que los números no sean negativos (lanzar `NumeroNegativoException`)
- Usar try-catch-finally para manejar las excepciones

**Solución sugerida:**
```java
// Crea las excepciones personalizadas y la clase Calculadora
```

### Ejercicio 2: Gestor de Archivos

Crea una clase `GestorArchivos` que lea y escriba archivos de texto con manejo de excepciones.

**Requisitos:**
- Usar try-with-resources para manejar archivos
- Capturar `FileNotFoundException` e `IOException`
- Crear una excepción personalizada `ArchivoNoLegibleException` si el archivo no se puede leer

### Ejercicio 3: Sistema de Inventario

Crea un sistema de inventario que gestione productos con las siguientes validaciones:

**Requisitos:**
- Crear excepción `StockInsuficienteException` cuando no hay suficiente stock
- Crear excepción `ProductoNoEncontradoException` cuando se busca un producto inexistente
- Crear excepción `PrecioInvalidoException` cuando el precio es negativo o cero
- Implementar métodos para agregar, buscar y vender productos

### Ejercicio 4: Validador de Formulario

Crea un validador de formulario que valide los siguientes campos:

**Requisitos:**
- **Nombre**: No vacío, mínimo 3 caracteres
- **Teléfono**: Formato válido (10 dígitos)
- **Código postal**: Formato válido (5 dígitos)
- Crear excepciones personalizadas para cada tipo de error de validación
- Mostrar todos los errores encontrados (no detenerse en el primero)

### Ejercicio 5: Banco de Datos Simulado

Crea un sistema bancario simple que maneje cuentas y transacciones:

**Requisitos:**
- Crear excepción `CuentaNoEncontradaException`
- Crear excepción `SaldoInsuficienteException`
- Crear excepción `MontoInvalidoException` (monto negativo o cero)
- Implementar métodos para depositar, retirar y transferir dinero
- Usar try-catch-finally para registrar todas las transacciones

---

## Soluciones de Ejercicios

### Solución Ejercicio 1: Calculadora

```java
// Excepciones personalizadas
class DivisionPorCeroException extends Exception {
    public DivisionPorCeroException(String mensaje) {
        super(mensaje);
    }
}

class NumeroNegativoException extends Exception {
    public NumeroNegativoException(String mensaje) {
        super(mensaje);
    }
}

// Clase Calculadora
class Calculadora {
    public static double sumar(double a, double b) throws NumeroNegativoException {
        validarNumero(a);
        validarNumero(b);
        return a + b;
    }
    
    public static double restar(double a, double b) throws NumeroNegativoException {
        validarNumero(a);
        validarNumero(b);
        return a - b;
    }
    
    public static double multiplicar(double a, double b) throws NumeroNegativoException {
        validarNumero(a);
        validarNumero(b);
        return a * b;
    }
    
    public static double dividir(double a, double b) 
            throws DivisionPorCeroException, NumeroNegativoException {
        validarNumero(a);
        validarNumero(b);
        if (b == 0) {
            throw new DivisionPorCeroException("No se puede dividir por cero");
        }
        return a / b;
    }
    
    private static void validarNumero(double numero) throws NumeroNegativoException {
        if (numero < 0) {
            throw new NumeroNegativoException("El número no puede ser negativo: " + numero);
        }
    }
}

// Clase de prueba
public class TestCalculadora {
    public static void main(String[] args) {
        try {
            System.out.println("Suma: " + Calculadora.sumar(10, 5));
            System.out.println("División: " + Calculadora.dividir(10, 2));
            System.out.println("División por cero: " + Calculadora.dividir(10, 0));
        } catch (DivisionPorCeroException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumeroNegativoException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Operación completada");
        }
    }
}
```

### Solución Ejercicio 3: Sistema de Inventario

```java
import java.util.HashMap;
import java.util.Map;

// Excepciones personalizadas
class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

class ProductoNoEncontradoException extends Exception {
    public ProductoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

class PrecioInvalidoException extends Exception {
    public PrecioInvalidoException(String mensaje) {
        super(mensaje);
    }
}

// Clase Producto
class Producto {
    private String codigo;
    private String nombre;
    private double precio;
    private int stock;
    
    public Producto(String codigo, String nombre, double precio, int stock) 
            throws PrecioInvalidoException {
        if (precio <= 0) {
            throw new PrecioInvalidoException("El precio debe ser mayor a cero");
        }
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }
    
    // Getters y setters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
}

// Clase Inventario
class Inventario {
    private Map<String, Producto> productos;
    
    public Inventario() {
        this.productos = new HashMap<>();
    }
    
    public void agregarProducto(Producto producto) {
        productos.put(producto.getCodigo(), producto);
    }
    
    public Producto buscarProducto(String codigo) throws ProductoNoEncontradoException {
        Producto producto = productos.get(codigo);
        if (producto == null) {
            throw new ProductoNoEncontradoException(
                "Producto con código " + codigo + " no encontrado"
            );
        }
        return producto;
    }
    
    public void venderProducto(String codigo, int cantidad) 
            throws ProductoNoEncontradoException, StockInsuficienteException {
        Producto producto = buscarProducto(codigo);
        
        if (producto.getStock() < cantidad) {
            throw new StockInsuficienteException(
                String.format("Stock insuficiente. Disponible: %d, Solicitado: %d",
                    producto.getStock(), cantidad)
            );
        }
        
        producto.setStock(producto.getStock() - cantidad);
        System.out.println("Venta realizada: " + cantidad + " unidades de " + producto.getNombre());
    }
}

// Clase de prueba
public class TestInventario {
    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        
        try {
            // Agregar productos
            Producto p1 = new Producto("P001", "Laptop", 15000.0, 10);
            Producto p2 = new Producto("P002", "Mouse", 500.0, 50);
            inventario.agregarProducto(p1);
            inventario.agregarProducto(p2);
            
            // Vender productos
            inventario.venderProducto("P001", 5);
            inventario.venderProducto("P001", 10); // Stock insuficiente
            
        } catch (PrecioInvalidoException e) {
            System.out.println("Error de precio: " + e.getMessage());
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error de producto: " + e.getMessage());
        } catch (StockInsuficienteException e) {
            System.out.println("Error de stock: " + e.getMessage());
        }
    }
}
```

---

## Resumen

### Puntos Clave

1. **Jerarquía de Excepciones**: `Throwable` → `Error` y `Exception` → `RuntimeException` y otras
2. **Tipos**: Verificadas (checked) y No verificadas (unchecked)
3. **Manejo**: try-catch-finally, try-with-resources, throw, throws
4. **Excepciones Personalizadas**: Extender `Exception` o `RuntimeException`
5. **Buenas Prácticas**: Especificidad, mensajes claros, logging, limpieza de recursos

### Checklist de Aprendizaje

- [ ] Entiendo la diferencia entre errores, excepciones verificadas y no verificadas
- [ ] Puedo usar try-catch-finally correctamente
- [ ] Sé crear y usar excepciones personalizadas
- [ ] Comprendo cuándo usar throw vs throws
- [ ] Puedo aplicar manejo de excepciones en proyectos reales

---

## Recursos Adicionales

- [Documentación oficial de Java - Excepciones](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- Java: The Complete Reference - Capítulo sobre Excepciones
- Effective Java - Item 69: Use exceptions only for exceptional conditions

---

**¡Práctica constante es la clave para dominar el manejo de excepciones en Java!**

