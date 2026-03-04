package org.example;

import org.example.exception.CitaYaCanceladaException;
import org.example.service.CitaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Main {


    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {


        CitaService citaService = new CitaService();

        citaService.registrarCita(1, "JORGE", "123456789", LocalDateTime.now());
        try {
            citaService.cancelarCita(1);
            citaService.cancelarCita(1);
        } catch (CitaYaCanceladaException e) {
            log.info("Error en la aplicacion");
            log.debug("FALLO POR: {}", e.getMessage());
        }
    }
}