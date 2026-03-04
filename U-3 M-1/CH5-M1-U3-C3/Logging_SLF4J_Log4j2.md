# Logging en Java con SLF4J y Log4j2

## Objetivos de Aprendizaje

- Comprender la importancia del logging en aplicaciones profesionales
- Configurar SLF4J como fachada y Log4j2 como backend de logging
- Implementar distintos niveles de log en una aplicación
- Depurar aplicaciones mediante logs organizados y legibles

---

## 1. Introducción al Logging y Ventajas frente a System.out.println

### ¿Qué es el Logging?

El **logging** es el proceso de registrar eventos, mensajes y errores que ocurren durante la ejecución de una aplicación. Es una práctica fundamental en el desarrollo de software profesional.

### ¿Por qué NO usar System.out.println()?

Aunque `System.out.println()` es simple y funciona, tiene **limitaciones críticas** en aplicaciones profesionales:

#### ❌ Problemas de System.out.println()

1. **No hay niveles de log**: No puedes filtrar entre información de depuración, errores críticos, etc.
2. **No hay control de salida**: Siempre va a la consola, no puedes redirigir a archivos
3. **No hay formato estructurado**: Difícil de parsear y analizar
4. **Impacto en rendimiento**: No se puede desactivar en producción
5. **No hay contexto**: No incluye información como fecha, hora, clase, método
6. **No es configurable**: Requiere cambios en el código para modificar el comportamiento

#### ✅ Ventajas del Logging Profesional

1. **Niveles de log**: TRACE, DEBUG, INFO, WARN, ERROR permiten filtrar información
2. **Configuración externa**: Cambios sin recompilar el código
3. **Múltiples destinos**: Consola, archivos, bases de datos, sistemas remotos
4. **Formato estructurado**: Fácil de parsear y analizar
5. **Rendimiento optimizado**: Se puede desactivar en producción
6. **Contexto rico**: Incluye fecha, hora, clase, método, hilo, etc.
7. **Rotación de archivos**: Evita que los logs crezcan indefinidamente
8. **Integración con herramientas**: ELK Stack, Splunk, etc.

### Ejemplo Comparativo

```java
// ❌ MAL: Usando System.out.println()
public class ServicioUsuario {
    public void crearUsuario(String nombre) {
        System.out.println("Creando usuario: " + nombre);
        // ... lógica ...
        System.out.println("Usuario creado exitosamente");
    }
    
    public void procesarPago(double monto) {
        System.out.println("Procesando pago: " + monto);
        if (monto < 0) {
            System.out.println("ERROR: Monto negativo");
        }
    }
}

// ✅ BIEN: Usando logging profesional
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServicioUsuario {
    private static final Logger logger = LoggerFactory.getLogger(ServicioUsuario.class);
    
    public void crearUsuario(String nombre) {
        logger.info("Creando usuario: {}", nombre);
        // ... lógica ...
        logger.info("Usuario creado exitosamente");
    }
    
    public void procesarPago(double monto) {
        logger.debug("Procesando pago: {}", monto);
        if (monto < 0) {
            logger.error("Monto negativo recibido: {}", monto);
        }
    }
}
```

---

## 2. Configuración de Dependencias en Maven para SLF4J + Log4j2

### ¿Qué es SLF4J?

**SLF4J (Simple Logging Facade for Java)** es una fachada (facade) que proporciona una API unificada para diferentes frameworks de logging. Permite cambiar el backend de logging sin modificar el código.

### ¿Qué es Log4j2?

**Apache Log4j2** es un framework de logging potente y de alto rendimiento que actúa como implementación (backend) de SLF4J.

### Configuración en pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.ejemplo</groupId>
    <artifactId>aplicacion-logging</artifactId>
    <version>1.0.0</version>
    
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <slf4j.version>2.0.9</slf4j.version>
        <log4j.version>2.20.0</log4j.version>
    </properties>
    
    <dependencies>
        <!-- SLF4J API (Fachada) -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        
        <!-- SLF4J Binding para Log4j2 -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-slf4j2-impl</artifactId>
            <version>${log4j.version}</version>
        </dependency>
        
        <!-- Log4j2 Core -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>${log4j.version}</version>
        </dependency>
        
        <!-- Log4j2 API -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>${log4j.version}</version>
        </dependency>
    </dependencies>
</project>
```

### Explicación de las Dependencias

1. **slf4j-api**: La interfaz/fachada que usaremos en nuestro código
2. **log4j-slf4j2-impl**: El adaptador que conecta SLF4J con Log4j2
3. **log4j-core**: El motor de logging de Log4j2
4. **log4j-api**: La API de Log4j2 (usada internamente)

### Verificación de la Instalación

Después de agregar las dependencias, ejecuta:

```bash
mvn clean install
```

O si usas un IDE como IntelliJ IDEA o Eclipse, las dependencias se descargarán automáticamente.

---

## 3. Archivo de Configuración log4j2.xml

### Ubicación del Archivo

El archivo `log4j2.xml` debe ubicarse en:
- `src/main/resources/log4j2.xml` (para aplicaciones Maven/Gradle)

### Estructura Básica de log4j2.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>

    <Properties>
        <Property name="LOG_PATTERN">
            %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n
        </Property>
    </Properties>

    <Appenders>
        <Console name="Console">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </Console>
    </Appenders>

    <Loggers>
        <Root level="ERROR">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>

</Configuration>
```

### Configuración Completa de Ejemplo

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN" monitorInterval="30">
    
    <!-- Propiedades que se pueden usar en el archivo -->
    <Properties>
        <Property name="LOG_PATTERN">%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n</Property>
        <Property name="LOG_FILE_PATH">logs</Property>
        <Property name="LOG_FILE_NAME">aplicacion</Property>
    </Properties>
    
    <Appenders>
        <!-- Appender para consola -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </Console>
        
        <!-- Appender para archivo con rotación -->
        <RollingFile name="FileAppender" 
                     fileName="${LOG_FILE_PATH}/${LOG_FILE_NAME}.log"
                     filePattern="${LOG_FILE_PATH}/${LOG_FILE_NAME}-%d{yyyy-MM-dd}-%i.log.gz">
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <!-- Rotar diariamente -->
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <!-- Rotar cuando el archivo supera 10MB -->
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <!-- Mantener máximo 10 archivos -->
            <DefaultRolloverStrategy max="10"/>
        </RollingFile>
        
        <!-- Appender solo para errores -->
        <RollingFile name="ErrorFileAppender" 
                     fileName="${LOG_FILE_PATH}/error.log"
                     filePattern="${LOG_FILE_PATH}/error-%d{yyyy-MM-dd}-%i.log.gz">
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="10"/>
        </RollingFile>
    </Appenders>
    
    <Loggers>
        <!-- Logger específico para nuestra aplicación -->
        <Logger name="com.ejemplo" level="DEBUG" additivity="false">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="FileAppender"/>
            <AppenderRef ref="ErrorFileAppender"/>
        </Logger>
        
        <!-- Logger para librerías externas (reducir ruido) -->
        <Logger name="org.apache" level="WARN"/>
        <Logger name="org.springframework" level="WARN"/>
    </Loggers>
    
    <!-- Logger raíz -->
    <Root level="INFO">
        <AppenderRef ref="Console"/>
        <AppenderRef ref="FileAppender"/>
        <AppenderRef ref="ErrorFileAppender"/>
    </Root>
</Configuration>
```

### Explicación de Componentes

#### Appenders (Destinos de Log)

1. **Console**: Escribe en la consola
2. **RollingFile**: Escribe en archivos con rotación automática
3. **ErrorFileAppender**: Solo captura errores (usando ThresholdFilter)

#### PatternLayout (Formato de Log)

- `%d{yyyy-MM-dd HH:mm:ss.SSS}`: Fecha y hora
- `[%t]`: Nombre del hilo
- `%-5level`: Nivel de log (alineado a 5 caracteres)
- `%logger{36}`: Nombre del logger (máximo 36 caracteres)
- `%msg`: Mensaje del log
- `%n`: Salto de línea

#### Policies (Políticas de Rotación)

- **TimeBasedTriggeringPolicy**: Rota por tiempo (diario, semanal, etc.)
- **SizeBasedTriggeringPolicy**: Rota cuando alcanza un tamaño

#### Loggers

- **Logger específico**: Para paquetes/clases específicas
- **Root**: Logger por defecto para todo lo demás

### Configuración Simplificada para Desarrollo

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout 
                pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    
    <Root level="DEBUG">
        <AppenderRef ref="Console"/>
    </Root>
</Configuration>
```

---

## 4. Uso de Niveles de Log: TRACE, DEBUG, INFO, WARN, ERROR

### Jerarquía de Niveles

Los niveles de log en orden de severidad (de menor a mayor):

```
TRACE < DEBUG < INFO < WARN < ERROR < FATAL
```

### Descripción de Cada Nivel

#### TRACE
- **Uso**: Información muy detallada, solo para depuración profunda
- **Cuándo usar**: Seguimiento paso a paso de algoritmos complejos
- **Ejemplo**: "Entrando al método calcular() con parámetros: x=5, y=10"

#### DEBUG
- **Uso**: Información de depuración para desarrollo
- **Cuándo usar**: Flujo de ejecución, valores de variables, decisiones
- **Ejemplo**: "Procesando usuario con ID: 12345"

#### INFO
- **Uso**: Información general sobre el funcionamiento normal de la aplicación
- **Cuándo usar**: Eventos importantes del negocio, inicio/fin de operaciones
- **Ejemplo**: "Usuario autenticado exitosamente", "Pago procesado"

#### WARN
- **Uso**: Advertencias sobre situaciones inusuales pero no críticas
- **Cuándo usar**: Recursos agotándose, configuraciones por defecto, deprecaciones
- **Ejemplo**: "Memoria disponible baja: 15%", "Usando configuración por defecto"

#### ERROR
- **Uso**: Errores que requieren atención pero no detienen la aplicación
- **Cuándo usar**: Excepciones capturadas, fallos en operaciones no críticas
- **Ejemplo**: "Error al enviar email de notificación", "No se pudo conectar a servicio externo"

#### FATAL
- **Uso**: Errores críticos que pueden causar que la aplicación termine
- **Cuándo usar**: Situaciones que requieren intervención inmediata
- **Ejemplo**: "Error crítico en base de datos", "Memoria agotada"

### Ejemplo Práctico de Uso

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServicioPago {
    private static final Logger logger = LoggerFactory.getLogger(ServicioPago.class);
    
    public void procesarPago(String usuarioId, double monto) {
        // TRACE: Información muy detallada
        logger.trace("Iniciando procesamiento de pago. Usuario: {}, Monto: {}", 
                    usuarioId, monto);
        
        // DEBUG: Información de depuración
        logger.debug("Validando datos del pago para usuario: {}", usuarioId);
        
        try {
            // Validaciones
            if (monto <= 0) {
                logger.warn("Intento de pago con monto inválido: {} para usuario: {}", 
                           monto, usuarioId);
                throw new IllegalArgumentException("Monto debe ser positivo");
            }
            
            // INFO: Evento importante del negocio
            logger.info("Procesando pago de ${} para usuario {}", monto, usuarioId);
            
            // Simulación de procesamiento
            validarTarjeta(usuarioId);
            debitarCuenta(usuarioId, monto);
            
            // INFO: Operación exitosa
            logger.info("Pago procesado exitosamente. Usuario: {}, Monto: {}", 
                       usuarioId, monto);
            
        } catch (IllegalArgumentException e) {
            // WARN: Error de validación (no crítico)
            logger.warn("Error de validación en pago: {}", e.getMessage());
            throw e;
            
        } catch (Exception e) {
            // ERROR: Error inesperado
            logger.error("Error al procesar pago para usuario: {}", usuarioId, e);
            throw new RuntimeException("Error procesando pago", e);
        }
    }
    
    private void validarTarjeta(String usuarioId) {
        logger.debug("Validando tarjeta para usuario: {}", usuarioId);
        // Lógica de validación...
    }
    
    private void debitarCuenta(String usuarioId, double monto) {
        logger.debug("Debitando ${} de la cuenta del usuario: {}", monto, usuarioId);
        // Lógica de débito...
    }
}
```

### Configuración de Niveles por Entorno

```xml
<!-- Desarrollo: Todos los niveles visibles -->
<Root level="DEBUG">
    <AppenderRef ref="Console"/>
</Root>

<!-- Producción: Solo INFO y superiores -->
<Root level="INFO">
    <AppenderRef ref="FileAppender"/>
    <AppenderRef ref="ErrorFileAppender"/>
</Root>
```

### Buenas Prácticas para Niveles

1. **TRACE**: Usar solo en desarrollo, nunca en producción
2. **DEBUG**: Útil en desarrollo y staging, desactivar en producción
3. **INFO**: Eventos importantes del negocio, visible en todos los entornos
4. **WARN**: Situaciones que requieren atención pero no son críticas
5. **ERROR**: Siempre registrar con el stack trace completo

---

## 5. Ejemplo Práctico: Logging en una Aplicación con Manejo de Excepciones

### Aplicación Completa: Sistema de Gestión de Usuarios

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

// Excepciones personalizadas
class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

class EmailDuplicadoException extends Exception {
    public EmailDuplicadoException(String mensaje) {
        super(mensaje);
    }
}

// Clase Usuario
class Usuario {
    private String id;
    private String nombre;
    private String email;
    
    public Usuario(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }
    
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    
    @Override
    public String toString() {
        return String.format("Usuario{id='%s', nombre='%s', email='%s'}", 
                           id, nombre, email);
    }
}

// Servicio de Usuarios con Logging
class ServicioUsuario {
    private static final Logger logger = LoggerFactory.getLogger(ServicioUsuario.class);
    private Map<String, Usuario> usuarios;
    private Map<String, String> emailsRegistrados;
    
    public ServicioUsuario() {
        this.usuarios = new HashMap<>();
        this.emailsRegistrados = new HashMap<>();
        logger.info("ServicioUsuario inicializado");
    }
    
    public Usuario crearUsuario(String id, String nombre, String email) 
            throws EmailDuplicadoException {
        logger.debug("Intentando crear usuario. ID: {}, Nombre: {}, Email: {}", 
                    id, nombre, email);
        
        // Validar email único
        if (emailsRegistrados.containsKey(email)) {
            logger.warn("Intento de crear usuario con email duplicado: {}", email);
            throw new EmailDuplicadoException("El email " + email + " ya está registrado");
        }
        
        try {
            Usuario usuario = new Usuario(id, nombre, email);
            usuarios.put(id, usuario);
            emailsRegistrados.put(email, id);
            
            logger.info("Usuario creado exitosamente: {}", usuario);
            return usuario;
            
        } catch (Exception e) {
            logger.error("Error inesperado al crear usuario. ID: {}, Email: {}", 
                        id, email, e);
            throw new RuntimeException("Error al crear usuario", e);
        }
    }
    
    public Usuario buscarUsuario(String id) throws UsuarioNoEncontradoException {
        logger.trace("Buscando usuario con ID: {}", id);
        
        Usuario usuario = usuarios.get(id);
        
        if (usuario == null) {
            logger.warn("Usuario no encontrado. ID: {}", id);
            throw new UsuarioNoEncontradoException("Usuario con ID " + id + " no encontrado");
        }
        
        logger.debug("Usuario encontrado: {}", usuario);
        return usuario;
    }
    
    public void eliminarUsuario(String id) throws UsuarioNoEncontradoException {
        logger.info("Iniciando eliminación de usuario. ID: {}", id);
        
        Usuario usuario = buscarUsuario(id);
        usuarios.remove(id);
        emailsRegistrados.remove(usuario.getEmail());
        
        logger.info("Usuario eliminado exitosamente. ID: {}", id);
    }
    
    public int obtenerCantidadUsuarios() {
        int cantidad = usuarios.size();
        logger.debug("Cantidad actual de usuarios: {}", cantidad);
        return cantidad;
    }
}

// Clase principal con ejemplos
public class AplicacionLogging {
    private static final Logger logger = LoggerFactory.getLogger(AplicacionLogging.class);
    
    public static void main(String[] args) {
        logger.info("=== Iniciando aplicación de gestión de usuarios ===");
        
        ServicioUsuario servicio = new ServicioUsuario();
        
        try {
            // Ejemplo 1: Crear usuarios exitosamente
            logger.info("\n--- Ejemplo 1: Creación exitosa de usuarios ---");
            servicio.crearUsuario("U001", "Juan Pérez", "juan@example.com");
            servicio.crearUsuario("U002", "María García", "maria@example.com");
            
            // Ejemplo 2: Buscar usuario
            logger.info("\n--- Ejemplo 2: Búsqueda de usuario ---");
            Usuario usuario = servicio.buscarUsuario("U001");
            logger.info("Usuario encontrado: {}", usuario);
            
            // Ejemplo 3: Intentar crear usuario con email duplicado
            logger.info("\n--- Ejemplo 3: Intento de email duplicado ---");
            try {
                servicio.crearUsuario("U003", "Pedro López", "juan@example.com");
            } catch (EmailDuplicadoException e) {
                logger.warn("No se pudo crear usuario: {}", e.getMessage());
            }
            
            // Ejemplo 4: Buscar usuario inexistente
            logger.info("\n--- Ejemplo 4: Búsqueda de usuario inexistente ---");
            try {
                servicio.buscarUsuario("U999");
            } catch (UsuarioNoEncontradoException e) {
                logger.warn("Usuario no encontrado: {}", e.getMessage());
            }
            
            // Ejemplo 5: Eliminar usuario
            logger.info("\n--- Ejemplo 5: Eliminación de usuario ---");
            servicio.eliminarUsuario("U002");
            
            // Ejemplo 6: Mostrar estadísticas
            logger.info("\n--- Ejemplo 6: Estadísticas ---");
            int cantidad = servicio.obtenerCantidadUsuarios();
            logger.info("Total de usuarios registrados: {}", cantidad);
            
        } catch (Exception e) {
            logger.error("Error crítico en la aplicación", e);
        } finally {
            logger.info("=== Finalizando aplicación ===");
        }
    }
}
```

### Configuración log4j2.xml para el Ejemplo

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN" monitorInterval="30">
    
    <Properties>
        <Property name="LOG_PATTERN">
            %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n
        </Property>
        <Property name="LOG_FILE_PATH">logs</Property>
    </Properties>
    
    <Appenders>
        <!-- Consola con colores (si el terminal lo soporta) -->
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </Console>
        
        <!-- Archivo general -->
        <RollingFile name="FileAppender" 
                     fileName="${LOG_FILE_PATH}/aplicacion.log"
                     filePattern="${LOG_FILE_PATH}/aplicacion-%d{yyyy-MM-dd}-%i.log.gz">
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="10"/>
        </RollingFile>
        
        <!-- Archivo solo para errores -->
        <RollingFile name="ErrorFile" 
                     fileName="${LOG_FILE_PATH}/error.log"
                     filePattern="${LOG_FILE_PATH}/error-%d{yyyy-MM-dd}-%i.log.gz">
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="10"/>
        </RollingFile>
    </Appenders>
    
    <Loggers>
        <!-- Logger para nuestra aplicación (más detallado) -->
        <Logger name="com.ejemplo" level="DEBUG" additivity="false">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="FileAppender"/>
            <AppenderRef ref="ErrorFile"/>
        </Logger>
    </Loggers>
    
    <Root level="INFO">
        <AppenderRef ref="Console"/>
        <AppenderRef ref="FileAppender"/>
        <AppenderRef ref="ErrorFile"/>
    </Root>
</Configuration>
```

### Salida Esperada de los Logs

```
2024-01-15 10:30:00.123 [main] INFO  AplicacionLogging - === Iniciando aplicación de gestión de usuarios ===
2024-01-15 10:30:00.124 [main] INFO  ServicioUsuario - ServicioUsuario inicializado
2024-01-15 10:30:00.125 [main] INFO  AplicacionLogging - --- Ejemplo 1: Creación exitosa de usuarios ---
2024-01-15 10:30:00.126 [main] DEBUG ServicioUsuario - Intentando crear usuario. ID: U001, Nombre: Juan Pérez, Email: juan@example.com
2024-01-15 10:30:00.127 [main] INFO  ServicioUsuario - Usuario creado exitosamente: Usuario{id='U001', nombre='Juan Pérez', email='juan@example.com'}
2024-01-15 10:30:00.128 [main] DEBUG ServicioUsuario - Intentando crear usuario. ID: U002, Nombre: María García, Email: maria@example.com
2024-01-15 10:30:00.129 [main] INFO  ServicioUsuario - Usuario creado exitosamente: Usuario{id='U002', nombre='María García', email='maria@example.com'}
2024-01-15 10:30:00.130 [main] INFO  AplicacionLogging - --- Ejemplo 2: Búsqueda de usuario ---
2024-01-15 10:30:00.131 [main] TRACE ServicioUsuario - Buscando usuario con ID: U001
2024-01-15 10:30:00.132 [main] DEBUG ServicioUsuario - Usuario encontrado: Usuario{id='U001', nombre='Juan Pérez', email='juan@example.com'}
2024-01-15 10:30:00.133 [main] INFO  AplicacionLogging - Usuario encontrado: Usuario{id='U001', nombre='Juan Pérez', email='juan@example.com'}
2024-01-15 10:30:00.134 [main] INFO  AplicacionLogging - --- Ejemplo 3: Intento de email duplicado ---
2024-01-15 10:30:00.135 [main] DEBUG ServicioUsuario - Intentando crear usuario. ID: U003, Nombre: Pedro López, Email: juan@example.com
2024-01-15 10:30:00.136 [main] WARN  ServicioUsuario - Intento de crear usuario con email duplicado: juan@example.com
2024-01-15 10:30:00.137 [main] WARN  AplicacionLogging - No se pudo crear usuario: El email juan@example.com ya está registrado
```

---

## 6. Buenas Prácticas de Logging en Aplicaciones Empresariales

### ✅ Hacer

#### 1. Usar Logger Estático Final

```java
// ✅ BIEN
public class MiClase {
    private static final Logger logger = LoggerFactory.getLogger(MiClase.class);
}

// ❌ MAL
public class MiClase {
    private Logger logger = LoggerFactory.getLogger(MiClase.class); // No es final ni static
}
```

#### 2. Usar Placeholders en lugar de Concatenación

```java
// ✅ BIEN: Más eficiente, solo evalúa si el nivel está habilitado
logger.debug("Usuario {} procesó {} transacciones", usuarioId, cantidad);

// ❌ MAL: Siempre concatena, incluso si DEBUG está deshabilitado
logger.debug("Usuario " + usuarioId + " procesó " + cantidad + " transacciones");
```

#### 3. Incluir Contexto Relevante

```java
// ✅ BIEN: Incluye información contextual
logger.error("Error al procesar pago. Usuario: {}, Monto: {}, Tarjeta: {}", 
            usuarioId, monto, tarjetaId, exception);

// ❌ MAL: Falta contexto
logger.error("Error al procesar pago", exception);
```

#### 4. Registrar Excepciones Correctamente

```java
// ✅ BIEN: Pasa la excepción como último parámetro
try {
    // código
} catch (Exception e) {
    logger.error("Error al procesar solicitud. Usuario: {}", usuarioId, e);
}

// ❌ MAL: Solo el mensaje, sin stack trace
catch (Exception e) {
    logger.error("Error: " + e.getMessage());
}
```

#### 5. Usar Niveles Apropiados

```java
// ✅ BIEN
logger.info("Usuario autenticado: {}", usuarioId);  // Evento de negocio
logger.debug("Validando token: {}", token);         // Detalle técnico
logger.warn("Memoria disponible baja: {}%", porcentaje); // Advertencia
logger.error("Error al conectar con base de datos", e); // Error

// ❌ MAL
logger.info("Token validado: " + token);  // Debería ser DEBUG
logger.error("Usuario autenticado");     // Debería ser INFO
```

#### 6. Logging Estructurado para Producción

```java
// ✅ BIEN: Logging estructurado (JSON)
logger.info("{\"event\":\"payment_processed\",\"userId\":\"{}\",\"amount\":{},\"timestamp\":\"{}\"}", 
           usuarioId, monto, Instant.now());

// O usar MDC (Mapped Diagnostic Context) para contexto
MDC.put("userId", usuarioId);
MDC.put("transactionId", transactionId);
logger.info("Pago procesado");
MDC.clear();
```

#### 7. Configuración por Entorno

```xml
<!-- Desarrollo: DEBUG visible -->
<Root level="DEBUG">
    <AppenderRef ref="Console"/>
</Root>

<!-- Producción: Solo INFO y superiores -->
<Root level="INFO">
    <AppenderRef ref="FileAppender"/>
</Root>
```

### ❌ Evitar

#### 1. No Registrar Información Sensible

```java
// ❌ MAL: Información sensible
logger.info("Usuario autenticado. Password: {}", password);
logger.debug("Token de acceso: {}", token);
logger.info("Número de tarjeta: {}", numeroTarjeta);

// ✅ BIEN: Información segura
logger.info("Usuario autenticado: {}", usuarioId);
logger.debug("Token validado exitosamente");
logger.info("Pago procesado para tarjeta terminada en: {}", 
           numeroTarjeta.substring(numeroTarjeta.length() - 4));
```

#### 2. No Abusar del Logging

```java
// ❌ MAL: Demasiado logging
public void procesar() {
    logger.debug("Entrando a procesar");
    logger.debug("Línea 1");
    logger.debug("Línea 2");
    logger.debug("Línea 3");
    logger.debug("Saliendo de procesar");
}

// ✅ BIEN: Logging significativo
public void procesar() {
    logger.debug("Iniciando procesamiento de lote con {} elementos", cantidad);
    // ... lógica ...
    logger.info("Procesamiento completado. {} elementos procesados", cantidad);
}
```

#### 3. No Usar System.out.println

```java
// ❌ MAL
System.out.println("Error: " + mensaje);

// ✅ BIEN
logger.error("Error: {}", mensaje);
```

#### 4. No Ignorar Excepciones

```java
// ❌ MAL: Silenciar excepciones
try {
    // código
} catch (Exception e) {
    // Nada
}

// ✅ BIEN: Registrar excepciones
try {
    // código
} catch (Exception e) {
    logger.error("Error inesperado", e);
    // Manejar apropiadamente
}
```

#### 5. No Logging en Loops Críticos

```java
// ❌ MAL: Logging en loop de alto rendimiento
for (int i = 0; i < 1000000; i++) {
    logger.debug("Procesando elemento: {}", i); // Muy costoso
    procesar(i);
}

// ✅ BIEN: Logging selectivo
for (int i = 0; i < 1000000; i++) {
    if (i % 10000 == 0) { // Solo cada 10000
        logger.debug("Procesados {} elementos", i);
    }
    procesar(i);
}
```

### Patrones Avanzados

#### 1. MDC (Mapped Diagnostic Context)

```java
import org.slf4j.MDC;

public class ServicioTransaccion {
    private static final Logger logger = LoggerFactory.getLogger(ServicioTransaccion.class);
    
    public void procesarTransaccion(String usuarioId, String transaccionId) {
        // Agregar contexto
        MDC.put("userId", usuarioId);
        MDC.put("transactionId", transaccionId);
        
        try {
            logger.info("Iniciando transacción");
            // ... lógica ...
            logger.info("Transacción completada");
        } finally {
            // Limpiar contexto
            MDC.clear();
        }
    }
}
```

Configuración en log4j2.xml para incluir MDC:

```xml
<PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss} [%X{userId}] [%X{transactionId}] %-5level %msg%n"/>
```

#### 2. Logging Condicional

```java
if (logger.isDebugEnabled()) {
    logger.debug("Datos complejos: {}", construirMensajeComplejo());
}
```

#### 3. Separación de Logs por Módulo

```xml
<Loggers>
    <Logger name="com.ejemplo.pagos" level="INFO">
        <AppenderRef ref="PagosFile"/>
    </Logger>
    <Logger name="com.ejemplo.usuarios" level="DEBUG">
        <AppenderRef ref="UsuariosFile"/>
    </Logger>
</Loggers>
```

---

## Ejercicios Prácticos

### Ejercicio 1: Configurar Logging Básico

**Objetivo**: Configurar SLF4J + Log4j2 en un proyecto Maven nuevo.

**Tareas**:
1. Crear un proyecto Maven
2. Agregar las dependencias necesarias
3. Crear archivo `log4j2.xml` básico
4. Crear una clase simple que use logging
5. Verificar que los logs aparecen correctamente

### Ejercicio 2: Implementar Logging en Calculadora

**Objetivo**: Agregar logging a una calculadora existente.

**Requisitos**:
- Usar diferentes niveles de log (DEBUG, INFO, WARN, ERROR)
- Registrar todas las operaciones
- Registrar errores (división por cero, etc.)
- Incluir información contextual (operandos, resultado)

### Ejercicio 3: Sistema de Logging con Rotación

**Objetivo**: Configurar un sistema de logging con rotación de archivos.

**Requisitos**:
- Logs en consola y archivo
- Rotación diaria de archivos
- Archivo separado para errores
- Mantener máximo 30 días de logs
- Formato estructurado con fecha, nivel, clase, mensaje

### Ejercicio 4: Logging con MDC

**Objetivo**: Implementar logging contextual usando MDC.

**Requisitos**:
- Crear un servicio que procese pedidos
- Usar MDC para almacenar: orderId, customerId, timestamp
- Incluir estos valores en todos los logs del procesamiento
- Limpiar MDC al finalizar

### Ejercicio 5: Análisis de Logs

**Objetivo**: Crear una aplicación que genere logs y analizarlos.

**Requisitos**:
- Generar logs de diferentes niveles
- Configurar appenders para consola y archivo
- Analizar los archivos de log generados
- Identificar patrones y errores

---

## Soluciones de Ejercicios

### Solución Ejercicio 2: Calculadora con Logging

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculadora {
    private static final Logger logger = LoggerFactory.getLogger(Calculadora.class);
    
    public double sumar(double a, double b) {
        logger.debug("Sumando: {} + {}", a, b);
        double resultado = a + b;
        logger.info("Suma realizada: {} + {} = {}", a, b, resultado);
        return resultado;
    }
    
    public double restar(double a, double b) {
        logger.debug("Restando: {} - {}", a, b);
        double resultado = a - b;
        logger.info("Resta realizada: {} - {} = {}", a, b, resultado);
        return resultado;
    }
    
    public double multiplicar(double a, double b) {
        logger.debug("Multiplicando: {} * {}", a, b);
        double resultado = a * b;
        logger.info("Multiplicación realizada: {} * {} = {}", a, b, resultado);
        return resultado;
    }
    
    public double dividir(double a, double b) {
        logger.debug("Dividiendo: {} / {}", a, b);
        
        if (b == 0) {
            logger.error("Intento de división por cero: {} / {}", a, b);
            throw new ArithmeticException("No se puede dividir por cero");
        }
        
        double resultado = a / b;
        logger.info("División realizada: {} / {} = {}", a, b, resultado);
        return resultado;
    }
    
    public static void main(String[] args) {
        Calculadora calc = new Calculadora();
        
        calc.sumar(10, 5);
        calc.restar(10, 3);
        calc.multiplicar(4, 7);
        
        try {
            calc.dividir(10, 2);
            calc.dividir(10, 0);
        } catch (ArithmeticException e) {
            logger.warn("Operación cancelada: {}", e.getMessage());
        }
    }
}
```

---

## Resumen

### Puntos Clave

1. **SLF4J es la fachada**: Proporciona API unificada, independiente del backend
2. **Log4j2 es el backend**: Implementación potente y configurable
3. **Niveles de log**: TRACE < DEBUG < INFO < WARN < ERROR < FATAL
4. **Configuración externa**: `log4j2.xml` permite cambios sin recompilar
5. **Buenas prácticas**: Usar placeholders, incluir contexto, no registrar información sensible
6. **Appenders**: Consola, archivos, rotación, filtros
7. **MDC**: Contexto compartido entre logs relacionados

### Checklist de Aprendizaje

- [ ] Entiendo por qué no usar `System.out.println()` en producción
- [ ] Puedo configurar SLF4J + Log4j2 en un proyecto Maven
- [ ] Sé crear y configurar archivo `log4j2.xml`
- [ ] Comprendo los diferentes niveles de log y cuándo usar cada uno
- [ ] Puedo implementar logging en aplicaciones con manejo de excepciones
- [ ] Conozco las buenas prácticas de logging empresarial
- [ ] Puedo usar MDC para logging contextual

---

## Recursos Adicionales

- [Documentación oficial de SLF4J](http://www.slf4j.org/)
- [Documentación oficial de Log4j2](https://logging.apache.org/log4j/2.x/)
- [Guía de configuración de Log4j2](https://logging.apache.org/log4j/2.x/manual/configuration.html)
- [Buenas prácticas de logging](https://www.slf4j.org/faq.html)

---

## Configuración Rápida de Referencia

### pom.xml (Dependencias)

```xml
<dependencies>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>2.0.9</version>
    </dependency>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-slf4j2-impl</artifactId>
        <version>2.20.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.20.0</version>
    </dependency>
</dependencies>
```

### log4j2.xml (Mínimo)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Root level="INFO">
        <AppenderRef ref="Console"/>
    </Root>
</Configuration>
```

### Uso en Código

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiClase {
    private static final Logger logger = LoggerFactory.getLogger(MiClase.class);
    
    public void miMetodo() {
        logger.info("Mensaje informativo");
        logger.error("Error ocurrido", exception);
    }
}
```

---

**¡El logging profesional es esencial para el desarrollo de aplicaciones empresariales robustas y mantenibles!**









