package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> nombres = List.of("Ana", "Luis", "Alba", "Antonio", "Lola");
        List<String> resultado = nombres.stream()
                .filter(n -> n.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .limit(2)
                .toList();

        System.out.println(resultado);
    }
}