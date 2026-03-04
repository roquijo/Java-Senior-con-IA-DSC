package com.biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibroTest {

    Libro libro;

    @BeforeEach
    void setUp() {
        libro = new Libro();
    }

    @Test
    void testEstaDisponible_ConDisponibilidad() {
        libro.setCantidadDisponible(10);
        boolean disponible = libro.estaDisponible();
        assertTrue(disponible, "El libro deberia estar disponible");
    }

    @Test
    void testEstaDisponible_SinDisponibilidad() {
        boolean disponible = libro.estaDisponible();
        assertFalse(disponible, "El libro deberia no estar disponible");
    }

    @Test
    void testPrestar_DisminuyeDisponibilidad() {
        libro.setCantidadDisponible(50);
        int cantAntes = libro.getCantidadDisponible();

        libro.prestar();

        assertEquals(cantAntes - 1, libro.getCantidadDisponible());
    }

    @Test
    void testPrestar_NoPuedeSerNegativo() {
        libro.setCantidadDisponible(0);
        int cantAntes = libro.getCantidadDisponible();

        libro.prestar();

        int cantDespues = libro.getCantidadDisponible();

        assertEquals(cantAntes, cantDespues, "No se deberia haber podido prestar el libro");
    }

    @Test
    void testDevolver_AumentaDisponibilidad() {
        libro.setCantidadDisponible(50);
        libro.setCantidadTotal(libro.getCantidadDisponible() + 1);
        int cantAntes = libro.getCantidadDisponible();

        libro.devolver();

        assertEquals(cantAntes + 1, libro.getCantidadDisponible());
    }


    @Test
    void testDevolver_NoPuedeExcederTotal() {
        libro.setCantidadDisponible(50);
        libro.setCantidadTotal(libro.getCantidadDisponible());
        int cantAntes = libro.getCantidadDisponible();

        libro.devolver();

        assertEquals(cantAntes, libro.getCantidadDisponible());
    }

    @Test
    void testEquals_MismoISBN() {
        libro.setIsbn("123");
        libro.setAutor("Alex");
        Libro libro2 = new Libro();
        libro2.setIsbn("123");

        assertEquals(libro.getIsbn(), libro2.getIsbn());
    }
}
