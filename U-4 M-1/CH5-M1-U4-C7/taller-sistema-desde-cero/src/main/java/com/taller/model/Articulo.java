package com.taller.model;

import com.taller.enums.Categoria;
import com.taller.exception.DatosNotValidException;

public class Articulo {
    private Integer id;
    private String nombre;
    private Categoria categoria;
    private Double precio;

    public Articulo(Integer id, String nombre, Categoria categoria, Double precio) {
        if (id == null || nombre == null || categoria == null || precio == null) {
            throw new DatosNotValidException("Datos invalidos");
        }
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Articulo{" + "id=" + id + ", nombre='" + nombre + '\'' + ", categoria='" + categoria + '\'' + ", precio=" + precio + '}';
    }
}
