package com.taller.service;

import com.taller.enums.Categoria;
import com.taller.exception.ArticuloException;
import com.taller.model.Articulo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticuloService {


    private Map<Integer, Articulo> articulos;

    public ArticuloService() {
        this.articulos = new HashMap<>();
    }


    public Articulo crearArticulo(Articulo articulo) {
        if (this.articulos.containsKey(articulo.getId())) {
            throw new ArticuloException("Ya existe un articulo con id " + articulo.getId());
        }
        this.articulos.put(articulo.getId(), articulo);
        return articulo;
    }

    public Articulo buscarArticuloPorId(Integer id) {
        if (!this.articulos.containsKey(id)) {
            throw new ArticuloException("No existe un articulo con id " + id);
        }
        return this.articulos.get(id);
    }

    public Articulo buscarArticuloPorNombreExacto(String nombre) {
        return this.articulos.values()
                .stream()
                .filter(a -> a.getNombre().equals(nombre))
                .findFirst()
                .orElseThrow(() -> new ArticuloException("El articulo con el nombre " + nombre + " no existe"));
    }

    public List<Articulo> buscarArticuloPorNombre(String nombre) {
        return this.articulos.values()
                .stream()
                .filter(a -> a.getNombre().contains(nombre)).toList();
    }

    public List<Articulo> buscarArticuloPorCategoria(Categoria cat) {
        return this.articulos.values()
                .stream()
                .filter(a -> a.getCategoria().equals(cat)).toList();
    }

    public Map<Integer, Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(Map<Integer, Articulo> articulos) {
        this.articulos = articulos;
    }
}
