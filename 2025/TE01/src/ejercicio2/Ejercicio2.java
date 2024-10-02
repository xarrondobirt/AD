
package ejercicio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Ejercicio2 {

	public static void main(String[] args) {

		// Buscar el fichero
		String directorioActual = System.getProperty("user.dir");
		File fLeer = new File(directorioActual + "\\LeerEjercicio2.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(fLeer));
			String linea;
			List<String> listaNombresCompletos = new ArrayList<>();

			// Leer cada línea y añadir a la lista cada nombre y apellido
			while ((linea = br.readLine()) != null) {

				// Separar por espacio en blanco y añadir a la lista
				String[] partes = linea.split(" ");
				if (partes.length >= 2) {
					String nombre = partes[0]; // Primer elemento es el nombre
					String apellido = partes[1]; // Segundo elemento es el apellido
					listaNombresCompletos.add(nombre + " " + apellido);
				}
			}

			// Buscar nombres con 5 caracteres
			List<String> listaNombres = new ArrayList<>();
			for (String nombreApellido : listaNombresCompletos) {
				String[] partes = nombreApellido.split(" ");
				String nombre = partes[0]; // Primer elemento es el nombre
				if (nombre.length() == 5) {
					listaNombres.add(nombreApellido);
				}
			}

			// Escribir fichero
			File fEscribir = new File(directorioActual + "\\EscribirEjercicio2.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fEscribir));
			for (String str : listaNombres) {
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
