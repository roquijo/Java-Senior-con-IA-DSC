package org.example;

import org.example.service.CitaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Main {


    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        CitaService citaService = new CitaService();

        try {
            citaService.registrarCita(1, "JORGE", "123456789", LocalDateTime.now());
            citaService.cancelarCita(1);
            citaService.cancelarCita(1);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}