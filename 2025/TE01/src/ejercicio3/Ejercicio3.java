
package ejercicio3;

import java.io.File;
import java.io.FileInputStream;

public class Ejercicio3 {

	public static void main(String[] args) {

		// Buscar el fichero
		String directorioActual = System.getProperty("user.dir");

		// Fichero bueno
		File fLeer = new File(directorioActual + "\\ejercicio3.pdf");

		// fichero malo
		// File fLeer = new File(directorioActual + "\\ejercicio3.docx");
		try {
			FileInputStream fi = new FileInputStream(fLeer);
			final int[] bytesEsperados = { 37, 80, 68, 70 };
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
			if (bZipValido) {
				System.out.println("El fichero SI es un PDF");
			} else {
				System.out.println("El fichero NO es un PDF");
			}

			// Cerrar flujos
			fi.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
