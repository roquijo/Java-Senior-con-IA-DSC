# Guía Introductoria: Configuración de Log4j2.xml

## 📚 Introducción

Esta guía te ayudará a entender paso a paso cómo funciona el archivo `log4j2.xml`, que es el corazón de la configuración de logging en tus aplicaciones Java. 

**¿Por qué es importante?** 
- Te permite controlar dónde se guardan los logs (consola, archivos, etc.)
- Define qué información se muestra en cada log
- Organiza los logs por niveles de importancia
- Facilita la depuración de problemas en tu aplicación

---

## 📦 Configuración de Dependencias en Maven

Antes de empezar a usar Log4j2, necesitas agregar las dependencias correctas en tu archivo `pom.xml`. Son **4 dependencias** y cada una tiene un propósito específico.

### ¿Por qué 4 dependencias?

Para entender esto, primero debes saber que hay dos conceptos importantes:

1. **SLF4J (Simple Logging Facade for Java)**: Es una "fachada" o interfaz común para logging. Te permite escribir código que funcione con diferentes sistemas de logging sin cambiar tu código.

2. **Log4j2**: Es el sistema de logging real que hace el trabajo pesado (escribe en archivos, formatea los logs, etc.).

**La idea es:** Tu código usa SLF4J (la interfaz), y Log4j2 es la implementación que hace el trabajo real.

---

### Las 4 Dependencias Explicadas:

#### 1️⃣ SLF4J API (Fachada)

```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.9</version>
</dependency>
```

**¿Para qué sirve?**
- Es la **interfaz** que usas en tu código Java
- Contiene las clases como `Logger`, `LoggerFactory`, `MDC`, `Marker`
- **NO hace el logging real**, solo define cómo debes escribir el código
- Es como un "contrato" o "interfaz" que define los métodos disponibles

**¿Por qué es importante?**
- Te permite cambiar de sistema de logging (Log4j2, Logback, etc.) sin cambiar tu código
- Si mañana quieres usar Logback en lugar de Log4j2, solo cambias las dependencias, tu código Java sigue igual
- Es el estándar de la industria para logging en Java

**¿Qué contiene?**
- `Logger`: La interfaz para escribir logs
- `LoggerFactory`: Para obtener instancias de Logger
- `MDC`: Para el contexto de diagnóstico (Mapped Diagnostic Context)
- `Marker`: Para categorizar logs

**Ejemplo de uso:**
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiClase {
    // Todas estas clases vienen de slf4j-api
    private static final Logger log = LoggerFactory.getLogger(MiClase.class);
    
    public void miMetodo() {
        log.info("Este mensaje usa SLF4J API");
    }
}
```

**Sin esta dependencia:** Tu código no compilará. No podrás usar `Logger` ni `LoggerFactory`.

---

#### 2️⃣ Log4j2 API

```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.20.0</version>
</dependency>
```

**¿Para qué sirve?**
- Es la **interfaz específica de Log4j2**
- Define cómo Log4j2 debe funcionar internamente
- Contiene las clases base que Log4j2 Core necesita para funcionar
- Es como el "esqueleto" o "estructura" de Log4j2

**¿Por qué es importante?**
- Log4j2 Core necesita esta API para saber cómo estructurar su código
- Define las interfaces y clases base que usa Log4j2 internamente
- Sin esta dependencia, Log4j2 Core no puede funcionar

**¿Cuándo la usas directamente?**
- Normalmente **NO la usas directamente** en tu código
- Tu código usa SLF4J API, no Log4j2 API
- Pero es **necesaria** porque Log4j2 Core la necesita internamente

**Sin esta dependencia:** Log4j2 Core no funcionará. Obtendrás errores de clase no encontrada.

---

#### 3️⃣ Log4j2 Core

```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.20.0</version>
</dependency>
```

**¿Para qué sirve?**
- Es el **motor real** de Log4j2
- Lee el archivo `log4j2.xml` y aplica la configuración
- Escribe los logs en archivos, consola, bases de datos, etc.
- Formatea los mensajes según el patrón que definas en `log4j2.xml`
- Maneja la rotación de archivos, filtros, políticas de retención, etc.
- Implementa todos los Appenders (Console, RollingFile, Async, etc.)

**¿Por qué es importante?**
- Sin esto, **nada funciona**. Es el que hace todo el trabajo pesado.
- Es como el "motor" de un carro: sin él, el carro no se mueve.
- Es la implementación real que ejecuta todas las operaciones de logging

**¿Qué hace específicamente?**
- Cuando defines un `RollingFile` en `log4j2.xml`, es Log4j2 Core quien:
  - Crea el archivo físico
  - Escribe los mensajes en el archivo
  - Rota el archivo cuando es necesario
  - Aplica los filtros y políticas que configuraste

**Sin esta dependencia:** Aunque tengas SLF4J API y el binding, los logs no se escribirán en ningún lado. No funcionará nada.

---

#### 4️⃣ SLF4J Binding para Log4j2 (log4j-slf4j2-impl)

```xml
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j2-impl</artifactId>
    <version>2.20.0</version>
</dependency>
```

**¿Para qué sirve?**
- Es el **"puente" o "adaptador"** que conecta SLF4J con Log4j2
- Cuando tu código llama a `log.info()` (usando SLF4J API), esta dependencia:
  1. Recibe la llamada de SLF4J
  2. La traduce al formato que Log4j2 entiende
  3. La pasa a Log4j2 Core para que la procese
- Sin esto, SLF4J no sabría cómo comunicarse con Log4j2

**¿Por qué es importante?**
- Es el "traductor" entre SLF4J (tu código) y Log4j2 (el motor)
- Sin esto, aunque tengas SLF4J API y Log4j2 Core, no se comunicarían
- Es el "pegamento" que une las dos partes

**Analogía:** 
- Es como un traductor entre dos personas que hablan idiomas diferentes
- Tu código habla "SLF4J" y Log4j2 Core habla "Log4j2", esta dependencia traduce entre ambos

**¿Qué pasa sin esta dependencia?**
- Tu código compilará (porque tienes SLF4J API)
- Pero al ejecutar, verás un error como: "SLF4J: No SLF4J providers were found"
- Los logs no funcionarán porque no hay conexión entre SLF4J y Log4j2

**Nota importante:** El nombre incluye "slf4j2" porque es para SLF4J versión 2.x. Si usas SLF4J 1.x, necesitarías `log4j-slf4j-impl` (sin el "2").

---

### 🔄 ¿Cómo Funcionan las 4 Dependencias Juntas?

Aquí tienes un diagrama visual de cómo fluye la información:

```
Tu Código Java
    │
    │ usa
    ▼
┌─────────────────┐
│  SLF4J API      │  ← 1️⃣ Interfaz que usas en tu código
│  (Logger, etc.) │
└─────────────────┘
    │
    │ llama a log.info()
    ▼
┌─────────────────────────┐
│  log4j-slf4j2-impl      │  ← 4️⃣ Traductor/Puente
│  (SLF4J Binding)        │
└─────────────────────────┘
    │
    │ traduce y pasa
    ▼
┌─────────────────┐
│  Log4j2 API     │  ← 2️⃣ Interfaz interna de Log4j2
└─────────────────┘
    │
    │ usa
    ▼
┌─────────────────┐
│  Log4j2 Core    │  ← 3️⃣ Motor que hace el trabajo real
│                 │     (lee log4j2.xml, escribe archivos)
└─────────────────┘
    │
    │ escribe
    ▼
Archivos de Log / Consola
```

**Flujo completo cuando escribes `log.info("Hola")`:**

1. Tu código llama a `log.info("Hola")` usando **SLF4J API**
2. **log4j-slf4j2-impl** recibe la llamada y la traduce
3. Pasa la llamada a **Log4j2 API** (interfaz interna)
4. **Log4j2 Core** procesa la llamada:
   - Lee `log4j2.xml` para ver la configuración
   - Decide dónde escribir (consola, archivo, etc.)
   - Formatea el mensaje según el patrón
   - Escribe el log en el destino correspondiente

**Resumen en una frase:**
- **SLF4J API**: Lo que usas en tu código
- **log4j-slf4j2-impl**: El traductor
- **Log4j2 API**: La interfaz interna
- **Log4j2 Core**: El que hace el trabajo

---

### 📋 Ejemplo Completo de pom.xml

Aquí tienes un ejemplo completo con las 4 dependencias configuradas correctamente:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>mi-proyecto-logging</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        
        <!-- Versiones centralizadas (fácil de actualizar) -->
        <slf4j.version>2.0.9</slf4j.version>
        <log4j.version>2.20.0</log4j.version>
    </properties>

    <dependencies>
        <!-- 1. SLF4J API: La interfaz que usas en tu código -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>

        <!-- 2. SLF4J Binding: El puente entre SLF4J y Log4j2 -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-slf4j2-impl</artifactId>
            <version>${log4j.version}</version>
        </dependency>

        <!-- 3. Log4j2 API: La interfaz de Log4j2 -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>${log4j.version}</version>
        </dependency>

        <!-- 4. Log4j2 Core: El motor que hace el trabajo real -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>${log4j.version}</version>
        </dependency>
    </dependencies>
</project>
```

---

### 🔄 Flujo de Funcionamiento

Para que entiendas mejor cómo trabajan juntas, aquí está el flujo:

```
Tu Código Java
    ↓
    log.info("Mensaje")  ← Usa SLF4J API
    ↓
log4j-slf4j2-impl  ← El puente/traductor
    ↓
Log4j2 API  ← La interfaz de Log4j2
    ↓
Log4j2 Core  ← Lee log4j2.xml y escribe el log
    ↓
Archivo/Consola  ← El log aparece aquí
```

**Ejemplo práctico:**
```java
// 1. Tu código (usa SLF4J API)
Logger log = LoggerFactory.getLogger(MiClase.class);
log.info("Usuario autenticado");

// 2. log4j-slf4j2-impl toma esa llamada y la pasa a Log4j2

// 3. Log4j2 Core lee log4j2.xml para saber:
//    - ¿Dónde escribir? (consola, archivo, etc.)
//    - ¿Qué formato usar? (fecha, nivel, mensaje, etc.)
//    - ¿Qué nivel está permitido? (INFO, DEBUG, etc.)

// 4. El log aparece en la consola o archivo según la configuración
```

---

### ⚠️ Errores Comunes

#### Error 1: Falta el Binding
```xml
<!-- ❌ MAL: Solo tienes SLF4J API y Log4j2 Core -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
</dependency>
<!-- Falta log4j-slf4j2-impl -->
```

**Síntoma:** Verás un error como: `SLF4J: No SLF4J providers were found`

**Solución:** Agrega `log4j-slf4j2-impl`

---

#### Error 2: Versiones Incompatibles
```xml
<!-- ❌ MAL: Versiones diferentes pueden causar problemas -->
<dependency>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.36</version>  <!-- Versión antigua -->
</dependency>
<dependency>
    <artifactId>log4j-slf4j2-impl</artifactId>
    <version>2.20.0</version>  <!-- Versión nueva -->
</dependency>
```

**Solución:** Usa versiones compatibles. Para Log4j2 2.20.0, usa SLF4J 2.0.9

---

#### Error 3: Dependencias Duplicadas o Conflictivas
Si tienes otras librerías que también usan logging (como Logback), pueden causar conflictos.

**Solución:** Revisa tu `pom.xml` y asegúrate de tener solo un sistema de logging.

---

### ✅ Resumen de Dependencias

| Dependencia | Propósito | ¿La usas directamente? |
|------------|-----------|------------------------|
| **slf4j-api** | Interfaz para escribir código de logging | ✅ Sí, en tu código Java |
| **log4j-slf4j2-impl** | Puente entre SLF4J y Log4j2 | ❌ No, funciona automáticamente |
| **log4j-api** | Interfaz interna de Log4j2 | ❌ No, es interna |
| **log4j-core** | Motor que hace el trabajo real | ❌ No directamente, pero lee tu log4j2.xml |

**Regla de oro:** Necesitas las 4 dependencias para que todo funcione correctamente.

---

## 🏗️ Estructura Básica del log4j2.xml

El archivo `log4j2.xml` tiene tres partes principales:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration>
    <!-- 1. Properties: Variables reutilizables -->
    <Properties>...</Properties>
    
    <!-- 2. Appenders: Dónde se escriben los logs -->
    <Appenders>...</Appenders>
    
    <!-- 3. Loggers: Qué clases pueden escribir logs -->
    <Loggers>...</Loggers>
</Configuration>
```

---

## 1️⃣ Properties (Propiedades)

### ¿Para qué sirve?
Las **Properties** son como variables que puedes reutilizar en todo el archivo. Te evitan repetir el mismo texto muchas veces.

### Ejemplo Básico:

```xml
<Properties>
    <!-- Patrón de formato para los logs -->
    <Property name="LOG_PATTERN">
        %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n
    </Property>
    
    <!-- Carpeta donde se guardan los logs -->
    <Property name="LOG_PATH">logs</Property>
</Properties>
```

### Explicación del Patrón:
- `%d{yyyy-MM-dd HH:mm:ss.SSS}` → Fecha y hora (ejemplo: 2024-01-15 14:30:25.123)
- `[%t]` → Nombre del hilo (thread) que ejecutó el código
- `%-5level` → Nivel del log (INFO, ERROR, DEBUG, etc.) con 5 espacios
- `%logger{36}` → Nombre de la clase que generó el log (máximo 36 caracteres)
- `%msg` → El mensaje que escribiste en tu código
- `%n` → Salto de línea

**Ejemplo de salida:**
```
2024-01-15 14:30:25.123 [main] INFO  org.example.Main - Usuario autenticado correctamente
```

---

## 2️⃣ Appenders (Destinos de los Logs)

### ¿Para qué sirve?
Los **Appenders** definen **dónde** se van a escribir los logs. Puedes tener varios appenders para escribir en diferentes lugares.

### Tipos de Appenders Comunes:

#### A) Console Appender (Consola)
Escribe los logs en la consola/terminal.

```xml
<Console name="Console">
    <PatternLayout pattern="${LOG_PATTERN}"/>
</Console>
```

**Cuándo usarlo:** Durante el desarrollo, para ver los logs mientras programas.

---

#### B) RollingFile Appender (Archivo con Rotación)
Escribe los logs en un archivo. Cuando el archivo crece mucho, crea uno nuevo automáticamente.

```xml
<RollingFile name="ApplicationFile"
             fileName="${LOG_PATH}/aplicacion.log"
             filePattern="${LOG_PATH}/aplicacion-%d{yyyy-MM-dd}-%i.log">
    <PatternLayout pattern="${LOG_PATTERN}"/>
    <Policies>
        <!-- Crea un archivo nuevo cada día -->
        <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
        <!-- O cuando el archivo supera 10MB -->
        <SizeBasedTriggeringPolicy size="10MB"/>
    </Policies>
    <!-- Mantiene máximo 10 archivos antiguos -->
    <DefaultRolloverStrategy max="10"/>
</RollingFile>
```

**Explicación:**
- `fileName` → Nombre del archivo actual
- `filePattern` → Patrón para archivos antiguos (ejemplo: `aplicacion-2024-01-15-1.log`)
- `TimeBasedTriggeringPolicy` → Crea archivo nuevo cada día
- `SizeBasedTriggeringPolicy` → Crea archivo nuevo si supera 10MB
- `max="10"` → Solo guarda los 10 archivos más recientes, borra los más antiguos

**Cuándo usarlo:** En producción, para guardar un historial de lo que pasa en tu aplicación.

---

#### C) ThresholdFilter (Filtro por Nivel)
Solo escribe logs de un nivel específico o superior.

```xml
<RollingFile name="ErrorFile"
             fileName="${LOG_PATH}/errores.log">
    <PatternLayout pattern="${LOG_PATTERN}"/>
    <!-- Solo acepta ERROR y FATAL, rechaza todo lo demás -->
    <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
</RollingFile>
```

**Explicación:**
- `level="ERROR"` → Solo logs de nivel ERROR o superior (ERROR, FATAL)
- `onMatch="ACCEPT"` → Si coincide, acepta el log
- `onMismatch="DENY"` → Si no coincide, rechaza el log

**Cuándo usarlo:** Para tener un archivo separado solo con errores críticos.

---

#### D) Async Appender (Asíncrono)
Escribe los logs de forma asíncrona para no ralentizar tu aplicación.

```xml
<Async name="AsyncFile" bufferSize="512">
    <AppenderRef ref="ApplicationFile"/>
</Async>
```

**Explicación:**
- `bufferSize="512"` → Guarda hasta 512 logs en memoria antes de escribirlos
- `AppenderRef ref="ApplicationFile"` → Usa el appender "ApplicationFile" que definiste antes

**Cuándo usarlo:** En aplicaciones con muchos logs, para mejorar el rendimiento.

---

## 3️⃣ Loggers (Quién Puede Escribir Logs)

### ¿Para qué sirve?
Los **Loggers** definen **qué clases** pueden escribir logs y **qué nivel** de detalle pueden usar.

### Niveles de Log (de menos a más detallado):

1. **TRACE** → Información muy detallada (solo para desarrollo profundo)
2. **DEBUG** → Información útil para depurar problemas
3. **INFO** → Información general sobre el funcionamiento normal
4. **WARN** → Advertencias: algo no está bien pero la app sigue funcionando
5. **ERROR** → Errores que impiden que algo funcione correctamente
6. **FATAL** → Errores críticos que pueden hacer caer la aplicación

### Ejemplo de Logger Específico:

```xml
<Logger name="org.example.pedidos" level="DEBUG" additivity="false">
    <AppenderRef ref="Console"/>
    <AppenderRef ref="ApplicationFile"/>
</Logger>
```

**Explicación:**
- `name="org.example.pedidos"` → Aplica a todas las clases del paquete `org.example.pedidos`
- `level="DEBUG"` → Permite logs de nivel DEBUG, INFO, WARN, ERROR, FATAL
- `additivity="false"` → No hereda los appenders del Root Logger
- `AppenderRef ref="Console"` → Escribe en la consola
- `AppenderRef ref="ApplicationFile"` → Escribe en el archivo

**Ejemplo en código:**
```java
// En la clase org.example.pedidos.ServicioPedidos
log.debug("Validando pedido...");  // ✅ Se mostrará (nivel DEBUG permitido)
log.info("Pedido procesado");       // ✅ Se mostrará
log.warn("Stock bajo");            // ✅ Se mostrará
```

---

### Root Logger (Logger Principal)

El **Root Logger** es el logger por defecto para todas las clases que no tengan un logger específico.

```xml
<Root level="INFO">
    <AppenderRef ref="Console"/>
    <AppenderRef ref="ApplicationFile"/>
</Root>
```

**Explicación:**
- `level="INFO"` → Por defecto, solo muestra INFO, WARN, ERROR, FATAL
- No muestra DEBUG ni TRACE (para eso necesitas cambiar el nivel)

**Ejemplo:**
```java
// En cualquier clase sin logger específico
log.debug("Mensaje debug");  // ❌ NO se mostrará (nivel INFO)
log.info("Mensaje info");    // ✅ Se mostrará
log.error("Error ocurrido"); // ✅ Se mostrará
```

---

## 🎯 MDC (Mapped Diagnostic Context)

### ¿Qué es MDC?
**MDC** es como un "mapa" temporal donde puedes guardar información que se agregará automáticamente a todos tus logs. Es muy útil para rastrear requests o sesiones de usuario.

### ¿Para qué sirve?
Imagina que tienes 100 usuarios usando tu aplicación al mismo tiempo. ¿Cómo sabes qué logs pertenecen a cada usuario? Con MDC puedes agregar un ID único a cada request y todos los logs de ese request tendrán ese ID.

### Ejemplo Práctico:

#### 1. Configurar el patrón para incluir MDC:

```xml
<Property name="LOG_PATTERN">
    %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} [%X{requestId}] - %msg%n
</Property>
```

**Nota:** `%X{requestId}` mostrará el valor de "requestId" del MDC.

#### 2. Usar MDC en tu código:

```java
import org.slf4j.MDC;

public class ServicioPedidos {
    private static final Logger log = LoggerFactory.getLogger(ServicioPedidos.class);
    
    public void procesarPedido(int pedidoId, String producto) {
        // Agregar información al MDC
        MDC.put("requestId", "REQ-" + System.currentTimeMillis());
        MDC.put("userId", "USR-12345");
        MDC.put("pedidoId", String.valueOf(pedidoId));
        
        log.info("Iniciando procesamiento del pedido");
        log.debug("Validando producto: {}", producto);
        
        // ... código del método ...
        
        log.info("Pedido procesado exitosamente");
        
        // IMPORTANTE: Limpiar el MDC al finalizar
        MDC.clear();
    }
}
```

#### 3. Salida de los logs:

```
2024-01-15 14:30:25.123 [main] INFO  org.example.ServicioPedidos [REQ-1705327825123] - Iniciando procesamiento del pedido
2024-01-15 14:30:25.125 [main] DEBUG org.example.ServicioPedidos [REQ-1705327825123] - Validando producto: Laptop
2024-01-15 14:30:25.200 [main] INFO  org.example.ServicioPedidos [REQ-1705327825123] - Pedido procesado exitosamente
```

**Ventaja:** Todos los logs del mismo request tienen el mismo `requestId`, así puedes filtrarlos fácilmente.

### Casos de Uso Comunes:

1. **Rastrear requests HTTP:**
```java
MDC.put("requestId", UUID.randomUUID().toString());
MDC.put("ipAddress", request.getRemoteAddr());
```

2. **Rastrear sesiones de usuario:**
```java
MDC.put("userId", usuario.getId());
MDC.put("sessionId", session.getId());
```

3. **Rastrear transacciones:**
```java
MDC.put("transactionId", "TXN-12345");
MDC.put("accountId", cuenta.getId());
```

### ⚠️ Importante:
**Siempre limpia el MDC** al finalizar con `MDC.clear()`, especialmente en aplicaciones web. Si no lo haces, la información puede "quedarse pegada" y aparecer en otros requests.

---

## 🏷️ Markers (Marcadores)

### ¿Qué son los Markers?
Los **Markers** son etiquetas que puedes agregar a tus logs para categorizarlos. Te permiten filtrar logs por tipo de evento (auditoría, seguridad, negocio, etc.).

### ¿Para qué sirven?
Imagina que quieres ver solo los logs relacionados con seguridad, o solo los de auditoría. Con Markers puedes hacerlo fácilmente.

### Ejemplo Práctico:

#### 1. Crear Markers en tu código:

```java
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class ServicioAutenticacion {
    private static final Logger log = LoggerFactory.getLogger(ServicioAutenticacion.class);
    
    // Crear markers para diferentes categorías
    private static final Marker AUDIT = MarkerFactory.getMarker("AUDIT");
    private static final Marker SECURITY = MarkerFactory.getMarker("SECURITY");
    private static final Marker AUTHENTICATION = MarkerFactory.getMarker("AUTHENTICATION");
    
    public boolean intentarLogin(String email, String password) {
        // Log con marker AUTHENTICATION
        log.info(AUTHENTICATION, "Intento de login para usuario: {}", email);
        
        if (email == null || email.isEmpty()) {
            // Log con marker AUTHENTICATION y nivel WARN
            log.warn(AUTHENTICATION, "Intento de login con email vacío");
            return false;
        }
        
        if (validarCredenciales(email, password)) {
            // Log con marker AUDIT (evento importante para auditoría)
            log.info(AUDIT, "Login exitoso para usuario: {}", email);
            return true;
        } else {
            // Log con marker SECURITY (intento fallido - posible ataque)
            log.warn(SECURITY, "Intento de login fallido para usuario: {}", email);
            return false;
        }
    }
}
```

#### 2. Filtrar logs por Marker en log4j2.xml:

Puedes crear appenders que solo capturen logs con markers específicos:

```xml
<!-- Appender solo para logs de auditoría -->
<RollingFile name="AuditFile"
             fileName="${LOG_PATH}/auditoria.log">
    <PatternLayout pattern="${LOG_PATTERN}"/>
    <!-- Solo acepta logs con marker AUDIT -->
    <MarkerFilter marker="AUDIT" onMatch="ACCEPT" onMismatch="DENY"/>
</RollingFile>

<!-- Appender solo para logs de seguridad -->
<RollingFile name="SecurityFile"
             fileName="${LOG_PATH}/seguridad.log">
    <PatternLayout pattern="${LOG_PATTERN}"/>
    <!-- Solo acepta logs con marker SECURITY -->
    <MarkerFilter marker="SECURITY" onMatch="ACCEPT" onMismatch="DENY"/>
</RollingFile>
```

#### 3. Usar el appender en un Logger:

```xml
<Logger name="org.example.ServicioAutenticacion" level="INFO">
    <AppenderRef ref="AuditFile"/>
    <AppenderRef ref="SecurityFile"/>
</Logger>
```

### Casos de Uso Comunes:

1. **AUDIT (Auditoría):**
   - Logs de eventos importantes que deben ser auditados
   - Ejemplo: Login exitoso, cambios de contraseña, transacciones importantes

2. **SECURITY (Seguridad):**
   - Logs relacionados con seguridad
   - Ejemplo: Intentos de login fallidos, intentos de acceso no autorizado

3. **BUSINESS (Negocio):**
   - Logs de eventos de negocio importantes
   - Ejemplo: Pedidos procesados, pagos realizados

4. **PERFORMANCE (Rendimiento):**
   - Logs relacionados con el rendimiento
   - Ejemplo: Tiempos de ejecución de métodos lentos

### Ejemplo Completo con Markers:

```java
public class ServicioPedidos {
    private static final Logger log = LoggerFactory.getLogger(ServicioPedidos.class);
    private static final Marker BUSINESS = MarkerFactory.getMarker("BUSINESS");
    private static final Marker AUDIT = MarkerFactory.getMarker("AUDIT");
    
    public void procesarPedido(int pedidoId, double monto) {
        // Log de negocio
        log.info(BUSINESS, "Procesando pedido {} con monto ${}", pedidoId, monto);
        
        // Validaciones...
        
        if (monto > 1000) {
            // Log de auditoría para pedidos grandes
            log.info(AUDIT, "Pedido grande procesado: ID={}, Monto=${}", pedidoId, monto);
        }
        
        log.info(BUSINESS, "Pedido {} procesado exitosamente", pedidoId);
    }
}
```

---

## 📋 Ejemplo Completo de log4j2.xml Simplificado

Aquí tienes un ejemplo completo pero sencillo que puedes usar como base:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    
    <!-- 1. PROPERTIES: Variables reutilizables -->
    <Properties>
        <!-- Patrón de formato para los logs -->
        <Property name="LOG_PATTERN">
            %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} [%X{requestId}] - %msg%n
        </Property>
        
        <!-- Carpeta donde se guardan los logs -->
        <Property name="LOG_PATH">logs</Property>
    </Properties>

    <!-- 2. APPENDERS: Dónde se escriben los logs -->
    <Appenders>
        
        <!-- Appender para consola -->
        <Console name="Console">
            <PatternLayout pattern="${LOG_PATTERN}"/>
        </Console>

        <!-- Appender para archivo principal -->
        <RollingFile name="ApplicationFile"
                     fileName="${LOG_PATH}/aplicacion.log"
                     filePattern="${LOG_PATH}/aplicacion-%d{yyyy-MM-dd}-%i.log">
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <Policies>
                <!-- Crea archivo nuevo cada día -->
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <!-- O cuando supera 10MB -->
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <!-- Mantiene máximo 10 archivos -->
            <DefaultRolloverStrategy max="10"/>
        </RollingFile>

        <!-- Appender solo para errores -->
        <RollingFile name="ErrorFile"
                     fileName="${LOG_PATH}/errores.log"
                     filePattern="${LOG_PATH}/errores-%d{yyyy-MM-dd}-%i.log">
            <PatternLayout pattern="${LOG_PATTERN}"/>
            <!-- Solo acepta ERROR y FATAL -->
            <ThresholdFilter level="ERROR" onMatch="ACCEPT" onMismatch="DENY"/>
            <Policies>
                <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                <SizeBasedTriggeringPolicy size="10MB"/>
            </Policies>
            <DefaultRolloverStrategy max="30"/>
        </RollingFile>
    </Appenders>

    <!-- 3. LOGGERS: Qué clases pueden escribir logs -->
    <Loggers>
        
        <!-- Logger específico para el paquete de autenticación -->
        <Logger name="org.example.autenticacion" level="DEBUG" additivity="false">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="ApplicationFile"/>
            <AppenderRef ref="ErrorFile"/>
        </Logger>

        <!-- Root Logger: Para todas las demás clases -->
        <Root level="INFO">
            <AppenderRef ref="Console"/>
            <AppenderRef ref="ApplicationFile"/>
            <AppenderRef ref="ErrorFile"/>
        </Root>
    </Loggers>

</Configuration>
```

---

## ✅ Buenas Prácticas

### 1. Niveles de Log por Ambiente:

- **Desarrollo:** Usa `DEBUG` para ver todo
- **Producción:** Usa `INFO` o `WARN` para no saturar los logs

### 2. Cuándo usar cada nivel:

- **TRACE:** Casi nunca, solo para depuración muy profunda
- **DEBUG:** Información técnica útil para depurar (valores de variables, flujo de ejecución)
- **INFO:** Eventos normales del negocio (usuario autenticado, pedido creado)
- **WARN:** Situaciones anómalas pero manejables (intento de login fallido, datos inválidos)
- **ERROR:** Errores que impiden funcionalidad (excepciones, fallos de conexión)
- **FATAL:** Errores críticos que pueden hacer caer la app (muy raro de usar)

### 3. Performance:

```java
// ❌ MAL: Siempre construye el string, aunque no se loggee
log.debug("Usuario: " + usuario + " tiene " + pedidos.size() + " pedidos");

// ✅ BIEN: Solo construye el string si DEBUG está habilitado
if (log.isDebugEnabled()) {
    log.debug("Usuario: {} tiene {} pedidos", usuario, pedidos.size());
}

// ✅ MEJOR: Usa parámetros (más eficiente)
log.debug("Usuario: {} tiene {} pedidos", usuario, pedidos.size());
```

### 4. No loggear información sensible:

```java
// ❌ MAL: Nunca loggear passwords
log.info("Password del usuario: {}", password);

// ✅ BIEN: Loggear solo información no sensible
log.info("Usuario autenticado: {}", email);
```

### 5. Siempre limpiar MDC:

```java
try {
    MDC.put("requestId", requestId);
    // ... tu código ...
} finally {
    MDC.clear(); // Siempre limpiar, incluso si hay excepciones
}
```

---

## 🎓 Resumen

1. **Properties:** Variables reutilizables para patrones y rutas
2. **Appenders:** Definen dónde se escriben los logs (consola, archivos)
3. **Loggers:** Definen qué clases pueden escribir y qué nivel usar
4. **MDC:** Agrega información contextual a todos los logs (requestId, userId, etc.)
5. **Markers:** Etiquetas para categorizar y filtrar logs (AUDIT, SECURITY, etc.)

---

## 📚 Recursos Adicionales

- [Documentación oficial de Log4j2](https://logging.apache.org/log4j/2.x/)
- [Guía de SLF4J](http://www.slf4j.org/manual.html)

---

**¡Ahora estás listo para configurar tus propios logs de forma profesional!** 🚀

