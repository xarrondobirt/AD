import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ejercicio2 {
    public static void main(String[] args) {

        // Buscar el fichero
        String directorioActual = System.getProperty("user.dir");
        File fLeer = new File(directorioActual + "\\LeerEjercicio2.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(fLeer));
            String linea;
            List<String> lista = new ArrayList<>();

            // Leer cada línea y añadir a la lista cada palabra separando por espacio en blanco
            while ((linea = br.readLine()) != null) {
                lista = List.of(linea.split(" "));
            }

            // Buscar palindromos
            List<String> palindromos = new ArrayList<>();
            for(String str : lista){
                String contrario = new StringBuilder(str).reverse().toString();
                if(contrario.equals(str)){
                    palindromos.add(str);
                }
            }

            // Escribir fichero
            File fEscribir = new File(directorioActual + "\\EscribirEjercicio2.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(fEscribir));
            for (String str : palindromos) {
                bw.write(str);
                bw.write("\n");
            }

            // Cerrar flujos
            br.close();
            bw.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
