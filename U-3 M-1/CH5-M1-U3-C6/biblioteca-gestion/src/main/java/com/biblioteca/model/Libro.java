package com.biblioteca.model;

import java.util.Objects;

/**
 * Modelo que representa un libro en la biblioteca
 */
public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int cantidadTotal;
    private int cantidadDisponible;

    public Libro() {
    }

    public Libro(String isbn, String titulo, String autor, int cantidadTotal) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.cantidadTotal = cantidadTotal;
        this.cantidadDisponible = cantidadTotal;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(int cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    /**
     * Verifica si el libro está disponible para préstamo
     */
    //true - false
    public boolean estaDisponible() {
        return cantidadDisponible > 0;
    }

    /**
     * Disminuye la cantidad disponible (cuando se presta)
     */
    public void prestar() {
        if (cantidadDisponible > 0) {
            cantidadDisponible--;
        }
    }

    /**
     * Aumenta la cantidad disponible (cuando se devuelve)
     */
//    public void devolver() {
//        if (cantidadDisponible < cantidadTotal) {
//            cantidadDisponible++;
//        }
//    }
    public void devolver() {
        if (cantidadDisponible < cantidadTotal) {
            cantidadDisponible++;
        }
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Libro libro = (Libro) o;
//        return Objects.equals(isbn, libro.isbn);
//    }
//
//
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(isbn);
//    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return cantidadTotal == libro.cantidadTotal && cantidadDisponible == libro.cantidadDisponible && Objects.equals(isbn, libro.isbn) && Objects.equals(titulo, libro.titulo) && Objects.equals(autor, libro.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, titulo, autor, cantidadTotal, cantidadDisponible);
    }

    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", cantidadTotal=" + cantidadTotal +
                ", cantidadDisponible=" + cantidadDisponible +
                '}';
    }
}

