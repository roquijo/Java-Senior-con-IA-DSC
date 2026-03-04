package org.example.service;

import org.example.exception.CitaNoEncontradaException;
import org.example.exception.CitaYaCanceladaException;
import org.example.model.Cita;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


public class CitaService {

    private static final Logger log = LoggerFactory.getLogger(CitaService.class);
    private Cita citaActual;
    private final ValidacionService validacionService = new ValidacionService();

    public void registrarCita(int id, String nombre, String telefono, LocalDateTime fecha) {

        validacionService.validarNombrePaciente(nombre);
        validacionService.validarTelefonoPaciente(telefono);

        citaActual = new Cita(id, nombre, telefono, fecha);
        log.info("Cita registrada con exito");
    }


    public void cancelarCita(int id) {
        Cita cita = getCitaById(id);
        if (cita != null) {
            if (cita.isActivo()) {
                cita.setActivo(false);
            } else {
                throw new CitaYaCanceladaException("La cita ya fue cancelada");
            }
        }
    }

    public Cita getCitaById(int id) {
        if (citaActual != null && citaActual.getId() == id) {
            return citaActual;
        } else {
            throw new CitaNoEncontradaException("La cita no existe");
        }
    }
}
