package org.example.service;


import org.example.exception.DatosInvalidosException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidacionServiceTest {

    ValidacionService vs;

    @BeforeEach
    void setUp() {
        vs = new ValidacionService();
    }

    @Test
    void testvalidarNombrePaciente_incorrecto_null() {
        assertThrows(DatosInvalidosException.class, () -> vs.validarNombrePaciente(null));
    }

    @Test
    void testvalidarNombrePaciente_incorrecto_vacio() {
        assertThrows(DatosInvalidosException.class, () -> vs.validarNombrePaciente(""));
    }

    @Test
    void testvalidarNombrePaciente_correcto_nombre() {
        String nombrePaciente = "Paciente";
        assertDoesNotThrow(() -> vs.validarNombrePaciente(nombrePaciente));
    }
}
