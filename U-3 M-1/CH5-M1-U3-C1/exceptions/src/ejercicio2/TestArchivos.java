package ejercicio2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestArchivos {

    public static void main(String[] args) {
        GestorArchivoService gestorArchivoService = new GestorArchivoService();
        try {
            List<String> lineas = gestorArchivoService.leerArchivo("archivo.txt");
            lineas.forEach(lin -> System.out.println(lin));
        } catch (ArchivoNoLegibleException e) {
            System.out.println(e.getMessage());
        }


        List<String> conten = new ArrayList<>();

        conten.add("Hola programadores!");
        conten.add("De devsenior");
        conten.add("Esto es un ejemplo de escritura de archivos");

        try {
            gestorArchivoService.escribirArchivo("archivo2.txt", conten);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
