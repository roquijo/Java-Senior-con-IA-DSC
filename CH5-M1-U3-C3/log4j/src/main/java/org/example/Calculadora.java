package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculadora {
    
    private static final Logger log = LoggerFactory.getLogger(Calculadora.class);
    
    public double sumar(double a, double b) {
        log.info("Iniciando operación de suma: {} + {}", a, b);
        double resultado = a + b;
        log.info("Resultado de la suma: {}", resultado);
        return resultado;
    }
    
    public double restar(double a, double b) {
        log.info("Iniciando operación de resta: {} - {}", a, b);
        double resultado = a - b;
        log.info("Resultado de la resta: {}", resultado);
        return resultado;
    }
    
    public double multiplicar(double a, double b) {
        log.info("Iniciando operación de multiplicación: {} * {}", a, b);
        double resultado = a * b;
        log.info("Resultado de la multiplicación: {}", resultado);
        return resultado;
    }
    
    public double dividir(double a, double b) {
        log.info("Iniciando operación de división: {} / {}", a, b);
        if (b == 0) {
            log.error("Error: División por cero no permitida");
            throw new ArithmeticException("No se puede dividir por cero");
        }
        double resultado = a / b;
        log.info("Resultado de la división: {}", resultado);
        return resultado;
    }
    
    public void realizarOperaciones() {
        log.debug("Calculadora iniciada");
        sumar(10, 5);
        restar(10, 5);
        multiplicar(10, 5);
        dividir(10, 5);
        log.debug("Calculadora finalizada");
    }
}

