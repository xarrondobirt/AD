
package ejercicio1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Ejercicio1 {

	public static void main(String[] args) {

		// Buscar el fichero
		String directorioActual = System.getProperty("user.dir");
		File fLeer = new File(directorioActual + "\\LeerEjercicio1.txt");
		try {
			FileReader fr = new FileReader(fLeer);
			int i;

			StringBuilder contenido = new StringBuilder();

			// Leer cada caracter y añadir al StringBuilder
			while ((i = fr.read()) != -1) {
				contenido.append((char) i);
			}

			// Dividir el contenido en palabras, separar por espacio en blanco
			String[] palabras = contenido.toString().split("\\s+");

			// Invertir cada palabra individualmente
			StringBuilder resultado = new StringBuilder();
			for (String palabra : palabras) {
				String palabraInvertida = new StringBuilder(palabra).reverse().toString();
				resultado.append(palabraInvertida).append(" ");
			}

			// Escribir fichero
			File fEscribir = new File(directorioActual + "\\EscribirEjercicio1.txt");
			FileWriter fw = new FileWriter(fEscribir);
			fw.write(resultado.toString().trim());

			// Cerrar flujos
			fr.close();
			fw.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
