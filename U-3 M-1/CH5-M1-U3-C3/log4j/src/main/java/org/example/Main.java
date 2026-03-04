package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("=== Iniciando aplicación con módulos Calculadora y Estudiante ===\n");
        
        // Módulo Calculadora
        log.info("Iniciando módulo Calculadora");
        Calculadora calculadora = new Calculadora();
        calculadora.realizarOperaciones();
        log.info("Módulo Calculadora finalizado\n");
        
        // Módulo Estudiante
        log.info("Iniciando módulo Estudiante");
        Estudiante estudiante = new Estudiante("Juan Pérez", 20);
        estudiante.realizarActividades();
        log.info("Módulo Estudiante finalizado");
        
        System.out.println("\n=== Aplicación finalizada ===");
        System.out.println("Revisa los logs en:");
        System.out.println("- logsCalculadora/ para los logs de Calculadora");
        System.out.println("- logsEstudiantes/ para los logs de Estudiante");
    }
}