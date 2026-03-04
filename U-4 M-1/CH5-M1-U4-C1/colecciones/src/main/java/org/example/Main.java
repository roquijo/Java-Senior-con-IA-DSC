package org.example;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        demoArrayList();
//        demoHashSet();
//        demoForVsWhile();
//        demoTreeSet();
//        demoHashMap();
        demoTreeMap();
    }


    public static void demoForVsWhile() {
        List<String> nombres = new ArrayList<>();
        nombres.add("Juan");
        nombres.add("Ricardo");
        nombres.add("Jorge");
        nombres.add("Alberto");

        int contador = 0;

        while (contador < nombres.size()) {
            System.out.println("While: " + nombres.get(contador));
            contador++;
        }

        for (String var : nombres) {
            System.out.println("Foreach: " + var);
        }

        for (int i = 0; i < nombres.size(); i++) {
            System.out.println("For: " + nombres.get(i));
        }
    }


    private static void demoArrayList() {

        System.out.println("demoArrayList");

        //Declarar con interfaz e inicializar con implementacion
        //Interface = List -> Implementacion = ArrayList
        //Operador diamante = <>  sirve para heredar el tipo de dato que se va a almacenar
        //que se haya definido del lado izq
        List<String> nombres = new ArrayList<>();


        //Metodo para insertar en ArrayList  -> .add(Object)
        nombres.add("Juan");
        nombres.add("Ricardo");
        nombres.add("Jorge");
        nombres.add("Alberto");

        System.out.println("Add " + nombres);

        //Metodo para eliminar por valor
        nombres.remove("Jorge");
        System.out.println("Remove {Jorge} " + nombres);

        //Metodo para eliminar por posicion
        nombres.remove(1);
        System.out.println("Remove {1} " + nombres);

        //Metodo para verificar si el valor existe en la lista
        System.out.println("contains {Ricardo} " + nombres.contains("Ricardo"));

        //Metodo para verificar si la lista está vacía
        System.out.println("isEmpty " + nombres.isEmpty());

        //Metodo para saber la cantidad de elementos de la lista
        System.out.println("size " + nombres.size());

        //Metodo para limpiar la lista
        nombres.clear();
        System.out.println("Clear: " + nombres);

        List<Persona> personas = new ArrayList<>();

        personas.add(new Persona("Jorge", 23));
        personas.add(new Persona("Juan", 24));

        System.out.println("Add " + personas);
    }


    public static void demoHashSet() {

        System.out.println("demoHashSet");

        Set<String> nombres = new HashSet<>();

        nombres.add("Juan");
        nombres.add("Ricardo");
        nombres.add("Jorge");
        System.out.println("Add " + nombres);

        nombres.add("Jorge");
        System.out.println("Add " + nombres);

        nombres.remove("Jorge");
        System.out.println("Remove " + nombres);

        System.out.println("contains " + nombres.contains("Juan"));
    }

    public static void demoTreeSet() {

        System.out.println("demoTreeSet");

        Set<String> nombres = new TreeSet<>(Comparator.reverseOrder());

        nombres.add("Xavier");
        nombres.add("Juan");
        nombres.add("Alvaro");
        System.out.println("Add " + nombres);

        nombres.add("Brayan");
        System.out.println("Add " + nombres);

        nombres.remove("Jorge");
        System.out.println("Remove " + nombres);

        System.out.println("contains " + nombres.contains("Juan"));
    }


    public static void demoHashMap() {
        System.out.println("demoHashMap");
        Map<String, String> persona = new HashMap<>();
        persona.put("Nombre", "Ricardo");
        persona.put("Apellido", "Villanueva");
        persona.put("Cedula", "132456789");
        System.out.println("put " + persona);

        if (!persona.containsKey("Nombre")) {
            persona.put("Nombre", "Jorge");
        }

        Set<String> llaves = persona.keySet();

        for (String llave : llaves) {
            System.out.println(llave);
        }

        Collection<String> values = persona.values();

        for (String val : values) {
            System.out.println(val);
        }


        persona.remove("Cedula");

        System.out.println("remove " + persona);

        System.out.println("get " + persona.get("Nombre"));


    }


    public static void demoTreeMap() {
        System.out.println("demoTreeMap");
        Map<Integer, String> persona = new TreeMap<>(Comparator.reverseOrder());
        persona.put(1, "Jorge");
        persona.put(5, "Rojas");
        persona.put(0, "98798");
        System.out.println("put " + persona);

        if (!persona.containsKey(1)) {
            persona.put(1, "Jorge");
        }

        Set<Integer> llaves = persona.keySet();

        for (Integer llave : llaves) {
            System.out.println(llave);
        }

        Collection<String> values = persona.values();

        for (String val : values) {
            System.out.println(val);
        }


        persona.remove(1);

        System.out.println("remove " + persona);

        System.out.println("get " + persona.get(5));
    }
}