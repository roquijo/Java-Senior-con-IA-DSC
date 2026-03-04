package org.example.service;


import org.example.exception.CitaEnFechaPasadaException;
import org.example.exception.DatosInvalidosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class ValidacionService {

    public static final Logger log = LoggerFactory.getLogger(ValidacionService.class);

    public void validarNombrePaciente(String nombre) {
        log.info("Validando nombre de paciente");
        if (nombre == null || nombre.isEmpty()) {
            throw new DatosInvalidosException("El nombre no puede ser nulo o estar vacio");
        }
    }

    public void validarTelefonoPaciente(String telefono) {
        log.info("Validando el telefono del Paciente");
        if (telefono == null || telefono.isEmpty()) {
            throw new DatosInvalidosException("El telefono no puede ser nulo o estar vacio");
        }

        String soloDigitos = telefono.replaceAll("[\\s\\-]", "");
        boolean siTieneSoloDigitos = soloDigitos.matches("\\d+");

        if (!siTieneSoloDigitos) {
            throw new DatosInvalidosException("El telefono no tiene el formato esperado");
        }

        if (soloDigitos.length() < 8) {
            throw new DatosInvalidosException("El telefono no tiene la cantidad de digitos esperada");
        }
    }

    public void validarFechaHoraNoPasada(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            log.warn("Validación fallida: fechaHora null");
            throw new DatosInvalidosException("La fecha y hora de la cita son obligatorias.");
        }
        if (fechaHora.isBefore(LocalDateTime.now())) {
            log.warn("Validación fallida: fecha/hora en el pasado: {}", fechaHora);
            throw new CitaEnFechaPasadaException("La fecha y hora de la cita no pueden ser en el pasado.");
        }
    }

    public void validarId(Integer id) {
        if (id == null || id == 0) {
            log.warn("Validación fallida: id vacío o null");
            throw new DatosInvalidosException("El ID de la cita no puede estar vacío.");
        }
    }
}
