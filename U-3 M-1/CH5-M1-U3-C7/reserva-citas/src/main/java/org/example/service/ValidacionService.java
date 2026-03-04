package org.example.service;


import org.example.exception.DatosInvalidosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidacionService {

    public static final Logger log = LoggerFactory.getLogger(ValidacionService.class);

    public void validarNombrePaciente(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new DatosInvalidosException("El nombre no puede ser nulo o estar vacio");
        }
    }

    public void validarTelefonoPaciente(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            throw new DatosInvalidosException("El telefono no puede ser nulo o estar vacio");
        }

        String soloDigitos = telefono.replaceAll("[\\s\\-]", "");
        boolean siTieneSoloDigitos = soloDigitos.matches("\\d+");

        if (!siTieneSoloDigitos) {
            throw new DatosInvalidosException("El telefono no tiene el formato esperado");
        }

        if (soloDigitos.length() < 8) {
            log.error("El telefono no tiene la cantidad de digitos esperada");
            throw new DatosInvalidosException("El telefono no tiene la cantidad de digitos esperada");
        }
    }
}
