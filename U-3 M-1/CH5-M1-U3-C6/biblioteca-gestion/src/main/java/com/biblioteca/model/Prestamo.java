package com.biblioteca.model;

import java.time.LocalDate;

/**
 * Modelo que representa un préstamo de libro
 */
public class Prestamo {
    private String id;
    private String isbnLibro;
    private String idUsuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean activo;
    
    public Prestamo() {
    }
    
    public Prestamo(String id, String isbnLibro, String idUsuario, LocalDate fechaPrestamo) {
        this.id = id;
        this.isbnLibro = isbnLibro;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.activo = true;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getIsbnLibro() {
        return isbnLibro;
    }
    
    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }
    
    public String getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }
    
    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    /**
     * Marca el préstamo como devuelto
     */
    public void devolver(LocalDate fechaDevolucion) {
        this.activo = false;
        this.fechaDevolucion = fechaDevolucion;
    }
    
    @Override
    public String toString() {
        return "Prestamo{" +
                "id='" + id + '\'' +
                ", isbnLibro='" + isbnLibro + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion +
                ", activo=" + activo +
                '}';
    }
}

