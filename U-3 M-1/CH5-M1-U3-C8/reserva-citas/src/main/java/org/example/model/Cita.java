package org.example.model;

import java.time.LocalDateTime;

public class Cita {

    private int id;
    private String nombre;
    private String telefono;
    private LocalDateTime fecha;
    private boolean activo;

    public Cita() {
    }

    public Cita(int id, String nombre, String telefono, LocalDateTime fecha) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.fecha = fecha;
        this.activo = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fecha=" + fecha +
                ", activo=" + activo +
                '}';
    }
}
