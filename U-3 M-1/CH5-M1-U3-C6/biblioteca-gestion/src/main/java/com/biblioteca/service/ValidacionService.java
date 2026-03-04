package com.biblioteca.service;

import java.time.LocalDate;

/**
 * Servicio para validaciones de negocio
 */
public class ValidacionService {
    
    private static final int ISBN_LENGTH = 13;
    
    /**
     * Valida que un ISBN tenga exactamente 13 dígitos numéricos
     * 
     * @param isbn El ISBN a validar
     * @return true si el ISBN es válido, false en caso contrario
     */
    public boolean validarISBN(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }
        
        // Debe tener exactamente 13 caracteres
        if (isbn.length() != ISBN_LENGTH) {
            return false;
        }
        
        // Todos los caracteres deben ser dígitos
        return isbn.matches("\\d{13}");
    }
    
    /**
     * Valida que un email tenga formato correcto
     * Debe contener @ y al menos un punto después del @
     * 
     * @param email El email a validar
     * @return true si el email es válido, false en caso contrario
     */
    public boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        // Debe contener @
        if (!email.contains("@")) {
            return false;
        }
        
        // Debe tener al menos un punto después del @
        int indexArroba = email.indexOf("@");
        String parteDespuesArroba = email.substring(indexArroba + 1);
        
        return parteDespuesArroba.contains(".");
    }
    
    /**
     * Valida que una fecha no sea futura
     * 
     * @param fecha La fecha a validar
     * @return true si la fecha no es futura, false en caso contrario
     */
    public boolean validarFechaNoFutura(LocalDate fecha) {
        if (fecha == null) {
            return false;
        }
        
        return !fecha.isAfter(LocalDate.now());
    }
    
    /**
     * Valida que una cantidad sea positiva
     * 
     * @param cantidad La cantidad a validar
     * @return true si la cantidad es positiva, false en caso contrario
     */
    public boolean validarCantidadPositiva(int cantidad) {
        return cantidad > 0;
    }
}

