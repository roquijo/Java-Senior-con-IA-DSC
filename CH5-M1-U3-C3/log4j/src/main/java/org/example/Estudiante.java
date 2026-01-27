package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Estudiante {
    
    private static final Logger log = LoggerFactory.getLogger(Estudiante.class);
    
    private String nombre;
    private int edad;
    private double promedio;
    
    public Estudiante(String nombre, int edad) {
        log.info("Creando nuevo estudiante: {} de {} años", nombre, edad);
        this.nombre = nombre;
        this.edad = edad;
        this.promedio = 0.0;
    }
    
    public void agregarCalificacion(double calificacion) {
        log.info("Agregando calificación {} para el estudiante {}", calificacion, nombre);
        if (calificacion < 0 || calificacion > 10) {
            log.warn("Calificación {} fuera del rango válido (0-10) para {}", calificacion, nombre);
        }
    }
    
    public void calcularPromedio(double[] calificaciones) {
        log.info("Calculando promedio para el estudiante {}", nombre);
        if (calificaciones == null || calificaciones.length == 0) {
            log.error("No hay calificaciones para calcular el promedio");
            return;
        }
        
        double suma = 0;
        for (double cal : calificaciones) {
            suma += cal;
        }
        this.promedio = suma / calificaciones.length;
        log.info("Promedio calculado para {}: {}", nombre, promedio);
    }
    
    public void mostrarInformacion() {
        log.debug("Mostrando información del estudiante");
        log.info("Estudiante: {}, Edad: {}, Promedio: {}", nombre, edad, promedio);
    }
    
    public void realizarActividades() {
        log.debug("Estudiante iniciando actividades");
        agregarCalificacion(8.5);
        agregarCalificacion(9.0);
        agregarCalificacion(7.5);
        calcularPromedio(new double[]{8.5, 9.0, 7.5});
        mostrarInformacion();
        log.debug("Estudiante finalizando actividades");
    }
    
    // Getters
    public String getNombre() {
        return nombre;
    }
    
    public int getEdad() {
        return edad;
    }
    
    public double getPromedio() {
        return promedio;
    }
}

