package com.biblioteca.service;

import com.biblioteca.exception.LibroNoDisponibleException;
import com.biblioteca.exception.LibroNoEncontradoException;
import com.biblioteca.exception.LimitePrestamosException;
import com.biblioteca.exception.PrestamoNoEncontradoException;
import com.biblioteca.model.Prestamo;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio para gestión de préstamos
 */
public class PrestamoService {
    
    private static final int MAX_PRESTAMOS_POR_USUARIO = 3;
    
    private final Map<String, Prestamo> prestamos;
    private final LibroService libroService;
    private int contadorId;
    
    public PrestamoService(LibroService libroService) {
        this.prestamos = new HashMap<>();
        this.libroService = libroService;
        this.contadorId = 1;
    }
    
    /**
     * Presta un libro a un usuario
     * 
     * @param isbn ISBN del libro a prestar
     * @param idUsuario ID del usuario
     * @return El préstamo creado
     * @throws LibroNoEncontradoException Si el libro no existe
     * @throws LibroNoDisponibleException Si el libro no está disponible
     * @throws LimitePrestamosException Si el usuario ya tiene el máximo de préstamos
     */
    public Prestamo prestarLibro(String isbn, String idUsuario) {
        // Validar que el libro existe
        if (!libroService.existeLibro(isbn)) {
            throw new LibroNoEncontradoException("Libro con ISBN " + isbn + " no encontrado.");
        }
        
        // Obtener el libro
        var libro = libroService.buscarPorISBN(isbn);
        
        // Validar disponibilidad
        if (!libro.estaDisponible()) {
            throw new LibroNoDisponibleException("El libro con ISBN " + isbn + " no está disponible.");
        }
        
        // Validar límite de préstamos
        int prestamosActivos = contarPrestamosActivos(idUsuario);
        if (prestamosActivos >= MAX_PRESTAMOS_POR_USUARIO) {
            throw new LimitePrestamosException(
                "El usuario " + idUsuario + " ya tiene " + MAX_PRESTAMOS_POR_USUARIO + " préstamos activos. " +
                "No puede prestar más libros."
            );
        }
        
        // Crear el préstamo
        String idPrestamo = generarIdPrestamo();
        Prestamo prestamo = new Prestamo(idPrestamo, isbn, idUsuario, LocalDate.now());
        prestamos.put(idPrestamo, prestamo);
        
        // Actualizar disponibilidad del libro
        libro.prestar();
        
        return prestamo;
    }
    
    /**
     * Devuelve un libro prestado
     * 
     * @param isbn ISBN del libro a devolver
     * @param idUsuario ID del usuario que devuelve el libro
     * @throws PrestamoNoEncontradoException Si no existe un préstamo activo para ese libro y usuario
     */
    public void devolverLibro(String isbn, String idUsuario) {
        // Buscar el préstamo activo
        Prestamo prestamo = buscarPrestamoActivo(isbn, idUsuario);
        
        if (prestamo == null) {
            throw new PrestamoNoEncontradoException(
                "No se encontró un préstamo activo del libro " + isbn + " para el usuario " + idUsuario
            );
        }
        
        // Marcar como devuelto
        prestamo.devolver(LocalDate.now());
        
        // Actualizar disponibilidad del libro
        var libro = libroService.buscarPorISBN(isbn);
        libro.devolver();
    }
    
    /**
     * Obtiene todos los préstamos activos de un usuario
     * 
     * @param idUsuario ID del usuario
     * @return Lista de préstamos activos
     */
    public List<Prestamo> obtenerPrestamosActivos(String idUsuario) {
        return prestamos.values().stream()
                .filter(p -> p.getIdUsuario().equals(idUsuario) && p.isActivo())
                .collect(Collectors.toList());
    }
    
    /**
     * Cuenta los préstamos activos de un usuario
     * 
     * @param idUsuario ID del usuario
     * @return Número de préstamos activos
     */
    public int contarPrestamosActivos(String idUsuario) {
        return (int) prestamos.values().stream()
                .filter(p -> p.getIdUsuario().equals(idUsuario) && p.isActivo())
                .count();
    }
    
    /**
     * Lista todos los préstamos (activos e inactivos)
     * 
     * @return Lista de todos los préstamos
     */
    public List<Prestamo> listarTodosLosPrestamos() {
        return new ArrayList<>(prestamos.values());
    }
    
    /**
     * Busca un préstamo activo por ISBN y usuario
     */
    private Prestamo buscarPrestamoActivo(String isbn, String idUsuario) {
        return prestamos.values().stream()
                .filter(p -> p.getIsbnLibro().equals(isbn) 
                        && p.getIdUsuario().equals(idUsuario) 
                        && p.isActivo())
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Genera un ID único para un préstamo
     */
    private String generarIdPrestamo() {
        return "PREST-" + contadorId++;
    }
}

