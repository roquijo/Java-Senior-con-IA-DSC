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
        log.info("Cita registrada {} {} {} {}", id, nombre, telefono, fecha);
        validaciones(id, fecha, nombre, telefono);
        citaActual = new Cita(id, nombre, telefono, fecha);
        log.info("Cita registrada con exito");
    }


    public void cancelarCita(int id) throws CitaYaCanceladaException {
        log.error("Cita cancelada {}", id);
        Cita cita = getCitaById(id);
        if (cita.isActivo()) {
            cita.setActivo(false);
        } else {
            throw new CitaYaCanceladaException("La cita ya fue cancelada");
        }
    }

    public Cita getCitaById(int id) {
        log.info("Cita getById {}", id);
        if (citaActual != null && citaActual.getId() == id) {
            return citaActual;
        } else {
            throw new CitaNoEncontradaException("La cita no existe");
        }
    }

    public Cita actualizarCita(Cita cita) {
        Cita cita2 = getCitaById(cita.getId());
        validaciones(cita.getId(), cita.getFecha(), cita.getNombre(), cita.getTelefono());
        cita2.setNombre(cita.getNombre());
        cita2.setTelefono(cita.getTelefono());
        cita2.setFecha(cita.getFecha());
        citaActual = cita2;
        return citaActual;
    }

    public void validaciones(int id, LocalDateTime fecha, String nombre, String telefono) {
        validacionService.validarId(id);
        validacionService.validarFechaHoraNoPasada(fecha);
        validacionService.validarNombrePaciente(nombre);
        validacionService.validarTelefonoPaciente(telefono);
    }
}
