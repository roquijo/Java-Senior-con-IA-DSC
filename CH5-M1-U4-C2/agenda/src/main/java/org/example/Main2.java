package org.example;

import java.util.*;

public class Main2 {

    public static void main(String[] args) {
//        primerEnunciado();
//        segundoEnunciado();
//        tercerEnunciado();
        cuartoEnunciado();
    }

    public static void primerEnunciado() {
        List<String> frutas = new ArrayList<>();

        frutas.add("Banano");
        frutas.add("Pera");
        frutas.add("Banano");

        System.out.println("Primer fruta: " + frutas.getFirst());
        System.out.println("Fruta en posicion 1: " + frutas.get(1));
        System.out.println("Tamaño de lista: " + frutas.size());

        for (String fruta : frutas) {
            System.out.println(fruta);
        }
    }

    public static void segundoEnunciado() {
        List<String> personas = new LinkedList<>();

        personas.add("Jorge");
        personas.add("Juan");
        personas.add("Laura");

        personas.addFirst("Felipe");

        System.out.println(personas);
    }

    public static void tercerEnunciado() {
        Set<String> codigos = new HashSet<>();

        codigos.add("P001");
        codigos.add("P002");
        codigos.add("P001");

        codigos.add("P003");

        System.out.println(codigos.size());
        System.out.println(codigos);
        System.out.println(codigos.contains("P002"));
    }

    public static void cuartoEnunciado() {
        Set<String> codigos = new TreeSet<>();

        codigos.add("Madrid");
        codigos.add("Barcelona");
        codigos.add("Alicante");

        System.out.println(codigos);
    }
}
