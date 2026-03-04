# CH5-M1-U3-C4: Logging con Log4j2 y SLF4J

Este módulo contiene material introductorio sobre logging en Java usando SLF4J y Log4j2.

## 📚 Contenido

### 📖 GUIA_LOG4J2.md
Guía completa e introductoria que explica:
- Configuración de las 4 dependencias Maven necesarias
- Estructura y componentes del archivo `log4j2.xml`
- Niveles de log (TRACE, DEBUG, INFO, WARN, ERROR)
- MDC (Mapped Diagnostic Context) - explicación detallada
- Markers para categorización - explicación detallada
- Ejemplos prácticos y buenas prácticas

### 💻 log4j-basico/
Proyecto Maven básico que incluye ejemplos prácticos de todos los conceptos explicados en la guía:
- Configuración completa de `log4j2.xml`
- Ejemplos de uso de niveles de log
- Ejemplos de MDC
- Ejemplos de Markers
- Casos de uso reales (autenticación)

## 🚀 Cómo Empezar

1. **Lee la guía**: Comienza con `GUIA_LOG4J2.md` para entender los conceptos
2. **Explora el proyecto**: Revisa el código en `log4j-basico/` para ver ejemplos prácticos
3. **Ejecuta el proyecto**: Sigue las instrucciones en `log4j-basico/README.md`

## 📁 Estructura

```
CH5-M1-U3-C4/
├── README.md                    # Este archivo
├── GUIA_LOG4J2.md               # Guía completa de Log4j2
└── log4j-basico/                # Proyecto básico con ejemplos
    ├── pom.xml                  # Dependencias Maven
    ├── README.md                # Instrucciones del proyecto
    └── src/
        └── main/
            ├── java/            # Código fuente con ejemplos
            └── resources/
                └── log4j2.xml   # Configuración de Log4j2
```

## 🎯 Objetivos de Aprendizaje

Al completar este módulo, deberías ser capaz de:
- ✅ Configurar SLF4J y Log4j2 en un proyecto Maven
- ✅ Entender para qué sirve cada una de las 4 dependencias
- ✅ Configurar un archivo `log4j2.xml` básico
- ✅ Usar apropiadamente los diferentes niveles de log
- ✅ Implementar MDC para agregar contexto a los logs
- ✅ Usar Markers para categorizar logs
- ✅ Aplicar buenas prácticas de logging en aplicaciones Java

## 📖 Temario Relacionado

Este contenido cubre:
- **CLASE 3**: Logs y depuración con Log4j y SLF4J
- **CLASE 4**: Validaciones, rutas y comunicación visual fluida (tutoría de logging)

## 🔗 Recursos Adicionales

- [Documentación oficial de Log4j2](https://logging.apache.org/log4j/2.x/)
- [Guía de SLF4J](http://www.slf4j.org/manual.html)

