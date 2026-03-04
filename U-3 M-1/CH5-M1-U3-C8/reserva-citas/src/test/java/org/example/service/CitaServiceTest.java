package org.example.service;

import org.example.exception.CitaNoEncontradaException;
import org.example.model.Cita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CitaServiceTest {

    CitaService citaService;
    LocalDateTime manana;

    @BeforeEach
    void init() {
        citaService = new CitaService();
        manana = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        citaService.registrarCita(1, "JORGE", "132456789", manana);
    }

    @Test
    void testActualizarCita_cita_no_existe_id() {
        Cita cita = new Cita(1, "JORGE", "318", manana);
        assertThrows(CitaNoEncontradaException.class, () -> citaService.actualizarCita(cita));
    }

    @Test
    void testActualizarCita_cambio_nombre() {
        Cita cita = new Cita(1, "JORGE", "318456789", manana);
        Cita citaActualizada = citaService.actualizarCita(cita);
        assertEquals(cita.getNombre(), citaActualizada.getNombre());
    }

    @Test
    void testActualizarCita_cambio_telefono() {
        Cita cita = new Cita(1, "JUAN", "", manana);
        Cita citaActualizada = citaService.actualizarCita(cita);
        assertEquals(cita.getTelefono(), citaActualizada.getTelefono());
    }

    @Test
    void testActualizarCita_cambio_fecha() {
        Cita cita = new Cita(1, "JORGE", "318456789", manana.plusDays(1));
        Cita citaActualizada = citaService.actualizarCita(cita);
        assertEquals(cita.getFecha(), citaActualizada.getFecha());
    }


    @Test
    void testActualizarCita_cambio_cita() {
        Cita cita = new Cita(1, "JORGE", "318456789", manana.plusDays(1));

        Cita citaActualizada = citaService.actualizarCita(cita);

        assertEquals(cita.getNombre(), citaActualizada.getNombre());
        assertEquals(cita.getTelefono(), citaActualizada.getTelefono());
        assertEquals(cita.getFecha(), citaActualizada.getFecha());
    }
}
