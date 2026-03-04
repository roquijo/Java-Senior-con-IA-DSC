package ejercicio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivoService {


    public List<String> leerArchivo(String rutaArchivo) throws ArchivoNoLegibleException {
        Path path = Paths.get(rutaArchivo);
        List<String> lineas = new ArrayList<>();

        if (!Files.exists(path)) {
            throw new ArchivoNoLegibleException("El archivo no existe");
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            throw new ArchivoNoLegibleException(
                    "Error al leer el archivo: " + rutaArchivo
            );
        }
        return lineas;
    }


    public void escribirArchivo(String ruta, List<String> conten) throws IOException {

        Path path = Paths.get(ruta);

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String linea : conten) {
                writer.write(linea);
                writer.newLine();
            }
        }
    }
}
