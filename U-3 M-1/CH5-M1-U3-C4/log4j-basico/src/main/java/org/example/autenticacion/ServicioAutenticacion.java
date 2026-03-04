package org.example.autenticacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 * Servicio de autenticación - Ejemplo de uso de Markers para categorización
 * <p>
 * Los Markers permiten filtrar logs por categorías específicas (AUDIT, SECURITY, etc.)
 * <p>
 * Este ejemplo demuestra:
 * - Cuándo usar INFO vs WARN vs ERROR
 * - Cómo usar Markers para categorizar logs
 * - Buenas prácticas de logging en autenticación
 */
public class ServicioAutenticacion {

    private static final Logger log = LoggerFactory.getLogger(ServicioAutenticacion.class);

    // Markers para categorización de logs
    private static final Marker AUDIT = MarkerFactory.getMarker("AUDIT");
    private static final Marker SECURITY = MarkerFactory.getMarker("SECURITY");
    private static final Marker AUTHENTICATION = MarkerFactory.getMarker("AUTHENTICATION");

    /**
     * Intenta autenticar un usuario
     * Demuestra cuándo usar INFO vs WARN vs ERROR
     */
    public boolean intentarLogin(String email, String password) {
        // INFO: Evento normal del negocio
        log.info(AUTHENTICATION, "Intento de login iniciado para usuario: {}", email);

        // DEBUG: Información técnica detallada (solo en desarrollo)
        log.debug("Validando credenciales...");

        // Validación de entrada
        if (email == null || email.isEmpty()) {
            // WARN: Situación anómala pero esperada (usuario ingresó datos incorrectos)
            log.warn(AUTHENTICATION, "Intento de login con email vacío");
            return false;
        }

        // Simulación: admin@example.com es válido
        if ("admin@example.com".equals(email) && "admin123".equals(password)) {
            // INFO con Marker AUDIT: Evento importante que debe ser auditado
            log.info(AUDIT, "Login exitoso para usuario: {}", email);
            return true;
        } else {
            // WARN con Marker SECURITY: Intento fallido - puede ser un ataque
            log.warn(SECURITY, "Intento de login fallido para usuario: {}. " +
                    "Credenciales incorrectas", email);
            log.error(SECURITY, "Intento de login fallido para usuario: {}", email);
            return false;
        }
    }

    /**
     * Registra un nuevo usuario
     * Demuestra logging de eventos de auditoría
     */
    public void registrarUsuario(String email, String nombre) {
        // INFO con Marker AUDIT: Evento que debe ser auditado

        log.error(AUDIT, "Error registrando: email={}, nombre={}",
                email, nombre);


        log.warn(AUDIT, "Registro de nuevo usuario iniciado: email={}, nombre={}",
                email, nombre);

        // Validaciones
        if (email == null || !email.contains("@")) {
            // ERROR: Error en la lógica de negocio
            log.error("Email inválido proporcionado: {}", email);
            throw new IllegalArgumentException("Email inválido");
        }

        // DEBUG: Detalles técnicos
        log.debug("Validando unicidad del email en base de datos");
        log.debug("Generando hash de password");

        // Simulación de registro
        // ...

        // INFO con Marker AUDIT: Evento importante auditado
        log.info(AUDIT, "Usuario registrado exitosamente: email={}", email);
    }
}

