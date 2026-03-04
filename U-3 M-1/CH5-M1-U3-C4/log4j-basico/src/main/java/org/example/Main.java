package org.example;

import org.example.autenticacion.ServicioAutenticacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * Clase principal que demuestra los conceptos básicos de Log4j2
 * 
 * Este proyecto incluye ejemplos de:
 * - Niveles de log (TRACE, DEBUG, INFO, WARN, ERROR)
 * - MDC (Mapped Diagnostic Context)
 * - Markers para categorización
 * - Uso apropiado de cada nivel
 */
public class Main {
    
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {
        System.out.println("=== Proyecto Básico de Log4j2 ===\n");
//
//        // Ejemplo 1: Niveles de log básicos
//        demostrarNivelesLog();
//
        // Ejemplo 2: MDC (Mapped Diagnostic Context)
//        demostrarMDC();
//
        // Ejemplo 3: Markers para categorización
        demostrarMarkers();

        log.info("prueba {} con {} parametros {}", 1 , 2 , 3);

        // Ejemplo 4: Caso de uso real - Autenticación
//        demostrarAutenticacion();
        
        System.out.println("\n=== Fin de la demostración ===");
        System.out.println("Revisa los logs en la carpeta 'logs/'");
    }
    
    /**
     * Demuestra los diferentes niveles de log
     */
    private static void demostrarNivelesLog() {
        System.out.println("\n--- Ejemplo 1: Niveles de Log ---");
        
        // TRACE: Información muy detallada (solo para desarrollo profundo)
        log.info("Este es un mensaje TRACE - muy detallado");
        
        // DEBUG: Información útil para depurar
        log.debug("Este es un mensaje DEBUG - información técnica");
        
        // INFO: Información general sobre el funcionamiento normal
        log.info("Este es un mensaje INFO - evento normal del negocio");
        
        // WARN: Advertencias - algo no está bien pero la app sigue funcionando
        log.warn("Este es un mensaje WARN - situación anómala pero manejable");
        
        // ERROR: Errores que impiden que algo funcione correctamente
        log.error("Este es un mensaje ERROR - error que requiere atención");
    }
    
    /**
     * Demuestra el uso de MDC para agregar contexto a los logs
     */
    private static void demostrarMDC() {
        System.out.println("\n--- Ejemplo 2: MDC (Mapped Diagnostic Context) ---");
        
        // Simulamos un request con ID único
        String requestId = "REQ-" + System.currentTimeMillis();

        MDC.put("requestId", "JUAN");

        MDC.put("USER", "USR-12345");
        
        log.info("Request iniciado");
        log.debug("Procesando datos del usuario");
        log.info("Request procesado exitosamente");
        
        // Los logs ahora incluirán automáticamente el requestId
        // Ejemplo: "2024-01-15 14:30:25.123 [main] INFO  org.example.Main [REQ-12345] - Request iniciado"
        
        // IMPORTANTE: Limpiar el MDC al finalizar
        MDC.clear();
    }
    
    /**
     * Demuestra el uso de Markers para categorizar logs
     */
    private static void demostrarMarkers() {
        System.out.println("\n--- Ejemplo 3: Markers para Categorización ---");
        
        ServicioAutenticacion auth = new ServicioAutenticacion();
        auth.intentarLogin("usuario@example.com", "password123");
        auth.registrarUsuario("nuevo@example.com", "Nuevo Usuario");
    }
    
    /**
     * Demuestra un caso de uso real con logging apropiado
     */
    private static void demostrarAutenticacion() {
        System.out.println("\n--- Ejemplo 4: Caso de Uso Real - Autenticación ---");
        
        ServicioAutenticacion auth = new ServicioAutenticacion();
        
        // Login exitoso
        boolean resultado1 = auth.intentarLogin("admin@example.com", "admin123");
        System.out.println("Login exitoso: " + resultado1);
        
        // Login fallido
        boolean resultado2 = auth.intentarLogin("usuario@example.com", "password");
        System.out.println("Login fallido: " + resultado2);
        
        // Intento con email vacío
        boolean resultado3 = auth.intentarLogin("", "password");
        System.out.println("Login con email vacío: " + resultado3);
    }
}

