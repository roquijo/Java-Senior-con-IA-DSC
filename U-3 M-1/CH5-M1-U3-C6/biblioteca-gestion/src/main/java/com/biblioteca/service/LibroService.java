package com.biblioteca.service;

import com.biblioteca.exception.LibroNoEncontradoException;
import com.biblioteca.model.Libro;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de libros
 */
public class LibroService {
    
    private final Map<String, Libro> libros;
    private final ValidacionService validacionService;
    
    public LibroService() {
        this.libros = new HashMap<>();
        this.validacionService = new ValidacionService();
    }
    
    /**
     * Registra un nuevo libro en el sistema
     * 
     * @param isbn ISBN del libro (debe ser único y válido)
     * @param titulo Título del libro
     * @param autor Autor del libro
     * @param cantidad Cantidad total de ejemplares
     * @return El libro registrado
     * @throws IllegalArgumentException Si el ISBN es inválido o ya existe
     * @throws IllegalArgumentException Si la cantidad es negativa o cero
     */
    public Libro registrarLibro(String isbn, String titulo, String autor, int cantidad) {
        // Validar ISBN
        if (!validacionService.validarISBN(isbn)) {
            throw new IllegalArgumentException("ISBN inválido. Debe tener exactamente 13 dígitos numéricos.");
        }
        
        // Validar que no exista
        if (libros.containsKey(isbn)) {
            throw new IllegalArgumentException("Ya existe un libro con el ISBN: " + isbn);
        }
        
        // Validar cantidad
        if (!validacionService.validarCantidadPositiva(cantidad)) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        
        Libro libro = new Libro(isbn, titulo, autor, cantidad);
        libros.put(isbn, libro);
        return libro;
    }
    
    /**
     * Busca un libro por su ISBN
     * 
     * @param isbn ISBN del libro a buscar
     * @return El libro encontrado
     * @throws LibroNoEncontradoException Si el libro no existe
     */
    public Libro buscarPorISBN(String isbn) {
        Libro libro = libros.get(isbn);
        if (libro == null) {
            throw new LibroNoEncontradoException("Libro con ISBN " + isbn + " no encontrado.");
        }
        return libro;
    }
    
    /**
     * Busca libros por título (búsqueda parcial, case-insensitive)
     * 
     * @param titulo Título o parte del título a buscar
     * @return Lista de libros que coinciden con el título
     */
    public List<Libro> buscarPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String tituloLower = titulo.toLowerCase().trim();
        return libros.values().stream()
                .filter(libro -> libro.getTitulo().toLowerCase().contains(tituloLower))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca libros por autor (búsqueda parcial, case-insensitive)
     * 
     * @param autor Nombre del autor o parte del nombre
     * @return Lista de libros del autor
     */
    public List<Libro> buscarPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String autorLower = autor.toLowerCase().trim();
        return libros.values().stream()
                .filter(libro -> libro.getAutor().toLowerCase().contains(autorLower))
                .collect(Collectors.toList());
    }
    
    /**
     * Lista todos los libros registrados
     * 
     * @return Lista de todos los libros
     */
    public List<Libro> listarTodos() {
        return new ArrayList<>(libros.values());
    }
    
    /**
     * Actualiza la cantidad disponible de un libro
     * 
     * @param isbn ISBN del libro
     * @param nuevaCantidad Nueva cantidad disponible
     * @throws LibroNoEncontradoException Si el libro no existe
     * @throws IllegalArgumentException Si la nueva cantidad es inválida
     */
    public void actualizarDisponibilidad(String isbn, int nuevaCantidad) {
        Libro libro = buscarPorISBN(isbn);
        
        if (nuevaCantidad < 0) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser negativa.");
        }
        
        if (nuevaCantidad > libro.getCantidadTotal()) {
            throw new IllegalArgumentException("La cantidad disponible no puede ser mayor a la cantidad total.");
        }
        
        libro.setCantidadDisponible(nuevaCantidad);
    }
    
    /**
     * Verifica si un libro existe
     * 
     * @param isbn ISBN del libro
     * @return true si existe, false en caso contrario
     */
    public boolean existeLibro(String isbn) {
        return libros.containsKey(isbn);
    }
}

