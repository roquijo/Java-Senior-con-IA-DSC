package com.biblioteca.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidacionServiceTest {

    @Test
    void testValidarISBN_Correcto_13Digitos() {
        ValidacionService vs = new ValidacionService();

        boolean isValid = vs.validarISBN("1234567890123");

        assertTrue(isValid);
    }
}
