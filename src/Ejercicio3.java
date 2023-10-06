import java.io.*;
import java.net.SocketOption;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio3 {
    public static void main(String[] args) {

        // Buscar el fichero
        String directorioActual = System.getProperty("user.dir");
        File fLeer = new File(directorioActual + "\\Ejercicio3.zip");
        try {
            FileInputStream fi = new FileInputStream(fLeer);
            int[] bytesEsperados = {80,75,3,4};
            int[] bytesLeidos = new int[4];

            // Leer los primeros 4 bytes del archivo
            for (int i = 0; i < 4; i++) {
                bytesLeidos[i] = fi.read();
            }

            // Verificar si los bytes leídos coinciden con los esperados
            boolean bZipValido = true;
            for (int i = 0; i < 4; i++) {
                if (bytesLeidos[i] != bytesEsperados[i]) {
                    bZipValido = false;
                    break;
                }
            }

            // Mostrar por pantalla
            if(bZipValido){
                System.out.println("El fichero SI es un zip");
            }else{
                System.out.println("El fichero NO es un zip");
            }

            // Cerrar flujos
            fi.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
