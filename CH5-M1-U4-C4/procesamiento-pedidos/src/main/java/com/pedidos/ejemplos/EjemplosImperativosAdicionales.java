package com.pedidos.ejemplos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Más ejercicios en estilo IMPERATIVO para resolver en clase con Streams (enfoque funcional).
 * No se usa ningún Stream en este archivo.
 */
public class EjemplosImperativosAdicionales {

    /** Ejercicio 11: Filtrar números pares. */
    public static List<Integer> soloPares(List<Integer> numeros) {
        List<Integer> resultado = new ArrayList<>();
        for (Integer n : numeros) {
            if (n % 2 == 0) {
                resultado.add(n);
            }
        }
        return resultado;
    }

    /** Ejercicio 12: Transformar cada número en su cuadrado. */
    public static List<Integer> cuadrados(List<Integer> numeros) {
        List<Integer> resultado = new ArrayList<>();
        for (Integer n : numeros) {
            resultado.add(n * n);
        }
        return resultado;
    }

    /** Ejercicio 13: Suma de todos los números. */
    public static int suma(List<Integer> numeros) {
        int total = 0;
        for (Integer n : numeros) {
            total += n;
        }
        return total;
    }

    /** Ejercicio 14: Nombres en mayúsculas. */
    public static List<String> aMayusculas(List<String> nombres) {
        List<String> resultado = new ArrayList<>();
        for (String s : nombres) {
            resultado.add(s.toUpperCase());
        }
        return resultado;
    }

    /** Ejercicio 15: Contar cuántos elementos tienen longitud > 3. */
    public static long contarLargos(List<String> palabras) {
        long count = 0;
        for (String s : palabras) {
            if (s.length() > 3) {
                count++;
            }
        }
        return count;
    }

    /** Ejercicio 16: Conjunto de palabras (sin duplicados). */
    public static Set<String> palabrasUnicas(List<String> palabras) {
        Set<String> resultado = new HashSet<>();
        for (String s : palabras) {
            resultado.add(s);
        }
        return resultado;
    }

    /** Ejercicio 17: Palabras ordenadas por longitud, las 2 primeras. */
    public static List<String> dosPrimerasPorLongitud(List<String> palabras) {
        List<String> copia = new ArrayList<>(palabras);
        copia.sort((a, b) -> Integer.compare(a.length(), b.length()));
        List<String> resultado = new ArrayList<>();
        int n = Math.min(2, copia.size());
        for (int i = 0; i < n; i++) {
            resultado.add(copia.get(i));
        }
        return resultado;
    }

    /** Ejercicio 18: Saltar los 2 primeros números y tomar los siguientes 2. */
    public static List<Integer> saltar2Tomar2(List<Integer> numeros) {
        List<Integer> resultado = new ArrayList<>();
        int tomados = 0;
        for (int i = 2; i < numeros.size() && tomados < 2; i++) {
            resultado.add(numeros.get(i));
            tomados++;
        }
        return resultado;
    }

    /** Ejercicio 19: ¿Hay algún número negativo? */
    public static boolean hayAlgunNegativo(List<Integer> numeros) {
        for (Integer n : numeros) {
            if (n < 0) {
                return true;
            }
        }
        return false;
    }

    /** Ejercicio 20: ¿Todos los números son positivos? */
    public static boolean todosPositivos(List<Integer> numeros) {
        for (Integer n : numeros) {
            if (n <= 0) {
                return false;
            }
        }
        return true;
    }

    /** Ejercicio 21: Primer número mayor que un umbral (o null). */
    public static Integer primerMayorQue(List<Integer> numeros, int umbral) {
        for (Integer n : numeros) {
            if (n > umbral) {
                return n;
            }
        }
        return null;
    }

    /** Ejercicio 22: Producto de todos los números. */
    public static int producto(List<Integer> numeros) {
        int prod = 1;
        for (Integer n : numeros) {
            prod *= n;
        }
        return prod;
    }

    /** Ejercicio 23: Longitudes de cada palabra. */
    public static List<Integer> longitudes(List<String> palabras) {
        List<Integer> resultado = new ArrayList<>();
        for (String s : palabras) {
            resultado.add(s.length());
        }
        return resultado;
    }

    /** Ejercicio 24: ¿Ninguna palabra está vacía? */
    public static boolean ningunaVacia(List<String> palabras) {
        for (String s : palabras) {
            if (s == null || s.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
