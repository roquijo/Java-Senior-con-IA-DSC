package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {

//        List<String> nombres = List.of("Ana", "Luis", "Carmen");
//        List<String> nombres2 = nombres.stream().filter(a -> filter(a)).toList();
//
//        System.out.println(nombres);
//        System.out.println(s1);


//        List<String> nombres = List.of("ana", "luis", "carmen");
//        List<String> mayusculas = nombres.stream()
//                .map(s -> mayuscula(s))
//                .toList();
//
//        System.out.println(mayusculas);


//        List<String> numeros = List.of("1", "2", "3");
//        List<Integer> enteros = numeros.stream()
//                .map(s -> Integer.parseInt(s))
//                .toList();
//
//        System.out.println("Integer" + enteros);
//
//        List<Integer> numeros2 = List.of(1, 2, 3);
//        List<String> enteros2 = numeros2.stream()
//                .map(s -> numero2(s))
//                .toList();
//
//        System.out.println("String" + enteros2);

//
//        List<Integer> nums = List.of(3, 1, 4, 1, 5);
//        List<Integer> ordenados = nums.stream()
//                .sorted(Comparator.reverseOrder())
//                .toList();
//
//        System.out.println(nums);
//        System.out.println(ordenados);
//
//
//        List<String> palabras = List.of("afasdfsdf", "agua", "mesa4");
//        List<String> porLongitud = palabras.stream()
//                .sorted(Comparator.comparingInt(String::length).reversed())
//                .toList();
//
//        System.out.println(palabras);
//        System.out.println(porLongitud);
//
//        List<Integer> nums = List.of(5, 4, 1, 2, 2, 3, 1, 4);
//        List<Integer> unicos = nums.stream()
//                .sorted()
//                .distinct()
//                .toList();
//
//        List<Integer> unicos2 = nums.stream()
//                .distinct()
//                .sorted()
//                .toList();
//
//        System.out.println(unicos);
//        System.out.println(unicos2);


//        List<Integer> nums = List.of(10, 20, 30, 40, 50);
//        List<Integer> primeros3 = nums.stream()
//                .sorted(Comparator.reverseOrder())
//                .limit(1)
//                .toList();
//
//        System.out.println(primeros3);


//        List<Integer> nums = List.of(10, 20, 30, 40, 50);
//        List<Integer> sinLosDosPrimeros = nums.stream()
//                .skip(2) //omitir n cantidad de elementos
//                .toList();
//
//        System.out.println(sinLosDosPrimeros);

//
//        List<String> nombres = List.of("Ana", "Luis", "Alba", "Antonio", "Lola");
//        List<String> resultado = nombres.stream()
//                .filter(n -> n.startsWith("A"))
//                .map(String::toUpperCase)
//                .sorted()
//                .limit(1)
//                .skip(1)
//                .limit(2).toList();
//
//        System.out.println(resultado);

//
//        List<String> nombres = List.of("Ana", "Luis");
//        nombres.stream().map(s -> s.toUpperCase()).forEach(System.out::println);
//
//        String joined =  nombres.stream().map(s -> s.toUpperCase()).collect(Collectors.joining("|"));


//        long cantidad = List.of(1, 2, 3, 4, 5).stream()
//                .filter(n -> n > 2)
//                .count();
//
//        System.out.println(cantidad);

//        Optional<String> primero = List.of("Bna", "Auis", "Carmen").stream()
//                .sorted()
//                .filter(n -> n.length() > 1)
//                .findFirst();
//// Optional["Carmen"]
//        primero.ifPresent(s -> System.out.println(s));

        List<Integer> nums = List.of(1, 2, 3, 4, 5);
        int suma = nums.stream().filter(
                n -> n > 5)
                .reduce(1, (a, b) -> a * b);
        System.out.println(suma);

     /*    boolean hayNegativos = lista.stream().anyMatch(n -> n < 0); //algun elemento que cumpla la condicion que se esta evaluando
        boolean todosPares = lista.stream().allMatch(n -> n % 2 == 0);
        boolean ningunoVacio = lista.stream().noneMatch(s -> s.isEmpty()); */

        //filter, map, sorted, distinct, limit, skip
        //forEach, collect, count, findFirst, reduce
    }

    public static String mayuscula(String nom) {
        return nom.toUpperCase();
    }

    public static Integer numero(String nom) {
        return Integer.parseInt(nom);
    }

    public static String numero2(Integer nom) {
        return nom.toString();
    }

    public static boolean filter(String a) {
        return a.startsWith("A");
    }
}