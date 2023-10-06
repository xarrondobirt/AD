import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ejercicio1 {
    public static void main(String[] args) {

        // Buscar el fichero
        String directorioActual = System.getProperty("user.dir");
        File fLeer = new File(directorioActual + "\\LeerEjercicio1.txt");
        try  {
            FileReader fr = new FileReader(fLeer);
            int i;
            List<Character> lista = new ArrayList<>();

            // Leer cada caracter y añadir a la lista
            while((i = fr.read()) != -1){
                lista.add(Character.valueOf((char)i));
            }
            // Darle la vuelta
            Collections.reverse(lista);

            // Escribir fichero
            File fEscribir = new File(directorioActual + "\\EscribirEjercicio1.txt");
            FileWriter fw = new FileWriter(fEscribir);
            for(Character c : lista){
                fw.write(c.charValue());
            }

            // Cerrar flujos
            fr.close();
            fw.close();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}