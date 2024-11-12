import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ContadorDePalabras {
    public static void main(String[] args) {
        String rutaArchivo = "texto.txt"; // Especifica la ruta del archivo

        Map<String, Long> frecuenciaPalabras = new ConcurrentHashMap<>(); // Mapa concurrente para almacenar la frecuencia de palabras

        try {
            // Leer el archivo usando Files.lines() y procesamiento paralelo
            frecuenciaPalabras = Files.lines(Paths.get(rutaArchivo))
                    .parallel()
                    .flatMap(linea -> Arrays.stream(linea.toLowerCase().split("\\W+"))) // Separar palabras ignorando mayúsculas/minúsculas
                    .filter(palabra -> !palabra.isEmpty()) // Filtrar palabras vacías
                    .collect(Collectors.groupingByConcurrent(palabra -> palabra, Collectors.counting()));
            // Mostrar resultados
            frecuenciaPalabras.forEach((palabra, frecuencia) ->
                System.out.println(palabra + ": " + frecuencia));
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
