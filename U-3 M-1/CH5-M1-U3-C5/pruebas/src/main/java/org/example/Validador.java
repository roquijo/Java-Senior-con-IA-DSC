package org.example;

public class Validador {
    
    /**
     * Valida si un email tiene formato correcto
     */
    public boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }
    
    /**
     * Valida si una contraseña cumple con los requisitos:
     * - Al menos 8 caracteres
     * - Al menos una mayúscula
     * - Al menos un número
     */
    public boolean validarPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean tieneMayuscula = false;
        boolean tieneNumero = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            }
            if (Character.isDigit(c)) {
                tieneNumero = true;
            }
        }
        
        return tieneMayuscula && tieneNumero;
    }
    
    /**
     * Valida si un número está en un rango específico
     */
    public boolean validarRango(int numero, int min, int max) {
        return numero >= min && numero <= max;
    }
}