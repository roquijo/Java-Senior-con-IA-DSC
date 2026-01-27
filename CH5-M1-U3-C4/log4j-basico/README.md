# Proyecto Básico de Log4j2

Este es un proyecto básico que demuestra los conceptos fundamentales de Log4j2 explicados en la guía.

## 📋 Contenido

Este proyecto incluye ejemplos prácticos de:

1. **Niveles de Log**: TRACE, DEBUG, INFO, WARN, ERROR
2. **MDC (Mapped Diagnostic Context)**: Para agregar contexto a los logs
3. **Markers**: Para categorizar logs (AUDIT, SECURITY, etc.)
4. **Configuración básica de log4j2.xml**: Con consola, archivos y rotación

## 🚀 Cómo Ejecutar

### Requisitos
- Java 21 o superior
- Maven 3.6 o superior

### Pasos

1. **Compilar el proyecto:**
   ```bash
   mvn clean compile
   ```

2. **Ejecutar la aplicación:**
   ```bash
   mvn exec:java -Dexec.mainClass="org.example.Main"
   ```

   O si prefieres ejecutar directamente:
   ```bash
   java -cp target/classes;target/dependency/* org.example.Main
   ```

3. **Ver los logs:**
   - Los logs aparecerán en la consola
   - Los logs también se guardarán en la carpeta `logs/`:
     - `logs/aplicacion.log` - Todos los logs
     - `logs/errores.log` - Solo errores (ERROR y FATAL)

## 📁 Estructura del Proyecto

```
log4j-basico/
├── pom.xml                          # Dependencias Maven (las 4 dependencias)
├── src/
│   └── main/
│       ├── java/
│       │   └── org/
│       │       └── example/
│       │           ├── Main.java                    # Clase principal con ejemplos
│       │           └── ServicioAutenticacion.java   # Ejemplo con Markers
│       └── resources/
│           └── log4j2.xml            # Configuración de Log4j2
└── logs/                             # Carpeta donde se guardan los logs (se crea automáticamente)
```

## 📚 Conceptos Demostrados

### 1. Niveles de Log
El archivo `Main.java` muestra cómo usar cada nivel:
- **TRACE**: Información muy detallada
- **DEBUG**: Información técnica para depurar
- **INFO**: Eventos normales del negocio
- **WARN**: Situaciones anómalas pero manejables
- **ERROR**: Errores que requieren atención

### 2. MDC (Mapped Diagnostic Context)
Ejemplo de cómo agregar contexto (requestId, userId) que aparece automáticamente en todos los logs.

### 3. Markers
El archivo `ServicioAutenticacion.java` muestra cómo usar Markers para categorizar logs:
- **AUDIT**: Eventos importantes para auditoría
- **SECURITY**: Eventos relacionados con seguridad
- **AUTHENTICATION**: Eventos de autenticación

## 🔧 Configuración

El archivo `log4j2.xml` está configurado con:
- **Console Appender**: Muestra logs en la consola
- **RollingFile Appender**: Guarda logs en archivos con rotación diaria
- **Error File Appender**: Archivo separado solo para errores
- **MDC Support**: El patrón incluye `%X{requestId}` para mostrar el contexto

## 📖 Más Información

Para entender mejor cada concepto, consulta la guía `GUIA_LOG4J2.md` en la carpeta principal.

## ⚠️ Notas Importantes

- Los logs se guardan en la carpeta `logs/` (se crea automáticamente)
- Los archivos se rotan diariamente o cuando superan 10MB
- Se mantienen máximo 10 archivos de aplicación y 30 de errores
- El nivel de log por defecto es INFO (cambia en `log4j2.xml` si necesitas DEBUG)

