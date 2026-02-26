package com.pedidos.ejemplos;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Ejemplos con enfoque funcional (Streams).
 * Solo se usan: filter, map, sorted, distinct, limit, skip, toList, toSet, count, reduce, forEach.
 */
public class EjemplosFuncionales {

    /**
     * Filtrar números pares.
     * filter + toList
     */
    public static List<Integer> soloPares(List<Integer> numeros) {
        return numeros.stream()
                .filter(n -> n % 2 == 0)
                .toList();
    }

    /**
     * Transformar cada número en su cuadrado.
     * map + toList
     */
    public static List<Integer> cuadrados(List<Integer> numeros) {
        return numeros.stream()
                .map(n -> n * n)
                .toList();
    }

    /**
     * Suma de todos los números.
     * reduce
     */
    public static int suma(List<Integer> numeros) {
        return numeros.stream()
                .reduce(0, Integer::sum);
    }

    /**
     * Nombres en mayúsculas.
     * map + toList
     */
    public static List<String> aMayusculas(List<String> nombres) {
        return nombres.stream()
                .map(String::toUpperCase)
                .toList();
    }

    /**
     * Contar cuántos elementos tienen longitud > 3.
     * filter + count
     */
    public static long contarLargos(List<String> palabras) {
        return palabras.stream()
                .filter(s -> s.length() > 3)
                .count();
    }

    /**
     * Conjunto de palabras (sin duplicados).
     * collect(Collectors.toSet())
     */
    public static Set<String> palabrasUnicas(List<String> palabras) {
        return palabras.stream()
                .collect(Collectors.toSet());
    }

    /**
     * Palabras ordenadas por longitud y luego las 2 primeras.
     * sorted + limit + toList
     */
    public static List<String> dosPrimerasPorLongitud(List<String> palabras) {
        return palabras.stream()
                .sorted(Comparator.comparingInt(String::length))
                .limit(2)
                .toList();
    }

    /**
     * Saltar los 2 primeros números y tomar los siguientes 2.
     * skip + limit + toList
     */
    public static List<Integer> saltar2Tomar2(List<Integer> numeros) {
        return numeros.stream()
                .skip(2)
                .limit(2)
                .toList();
    }
}
