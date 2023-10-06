import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ejercicio4 {
    public static void main(String[] args) {


        // Datos de los superhéroes
        int[] ids = {1, 2, 3, 4, 5, 6, 7};
        String[] dnis = {"01010101A", "03030303C", "05050505E", "07070707G", "02020202B", "04040404D", "06060606F"};
        String[] noms = {"Spiderman", "Green Goblin", "Storm", "Wolverine", "Mystique", "IronMan", "Mandarin"};
        String[] identidades = {"Peter Parker", "Norman Osborn", "Ororo Munroe", "James Howlett", "Raven Darkholme", "Tony Stark", "Zhang Tong"};
        String[] tipos = {"heroe", "villano", "heroe", "heroe", "villano", "heroe", "villano"};
        int[] pesos = {76, 84, 66, 136, 78, 102, 70};
        int[] alturas = {178, 183, 156, 152, 177, 182, 188};

        //----------------------APARTADO A---------------------//

        // Buscar el fichero
        String directorioActual = System.getProperty("user.dir");
        File f = new File(directorioActual + "\\Marvel.dat");
        try {
            RandomAccessFile rf = new RandomAccessFile(f, "rw");
            StringBuffer sbDni = null;
            StringBuffer sbNoms = null;
            StringBuffer sbId = null;
            StringBuffer sbTipos = null;
            int arraysLength = dnis.length;
            for (int i = 0; i < arraysLength; i++) {
                rf.writeInt(ids[i]);

                sbDni = new StringBuffer(dnis[i]);
                sbDni.setLength(9);
                rf.writeChars(sbDni.toString());

                sbNoms = new StringBuffer(noms[i]);
                sbNoms.setLength(10);
                rf.writeChars(sbNoms.toString());

                sbId = new StringBuffer(identidades[i]);
                sbId.setLength(20);
                rf.writeChars(sbId.toString());

                sbTipos = new StringBuffer(tipos[i]);
                sbTipos.setLength(10);
                rf.writeChars(sbTipos.toString());

                rf.writeInt(pesos[i]);
                rf.writeInt(alturas[i]);
            }
            System.out.println("La carga de de los personajes ha terminado correctamente");

            //---------------------APARTADO B--------------------------//
            Scanner scanner = new Scanner(System.in);

            System.out.print("Introduzca el DNI (con letra) del personaje para el control de peso: ");
            String scannerDni = scanner.nextLine();

            int posicion = 0;

            int id;
            char[] dni = new char[9];
            char[] alterEgo = new char[10];
            char[] nombre = new char[20];
            char[] tipo = new char[10];
            int peso;
            int altura;
            boolean bExiste = false;

            rf.seek(posicion);
            while (rf.getFilePointer() != rf.length()) {

                id = rf.readInt();

                for (int i = 0; i < dni.length; i++) {
                    dni[i] = rf.readChar();
                }
                String strDni = new String(dni);

                for (int i = 0; i < alterEgo.length; i++) {
                    alterEgo[i] = rf.readChar();
                }
                String strEgo = new String(alterEgo).trim();

                for (int i = 0; i < nombre.length; i++) {
                    nombre[i] = rf.readChar();
                }

                for (int i = 0; i < tipo.length; i++) {
                    tipo[i] = rf.readChar();
                }

                peso = rf.readInt();
                altura = rf.readInt();

                // Verificar si el dni introducido es el de algún superhéroe
                if (strDni.equals(scannerDni)) {
                    bExiste = true;
                    System.out.print("Introduzca su peso actual: ");
                    int scannerPeso = Integer.parseInt(scanner.nextLine());

                    // Calcular la diferencia de peso
                    int diferenciaPeso = peso - scannerPeso;
                    if (diferenciaPeso == 0) {
                        System.out.println(strEgo + " se mantiene en su peso");
                    } else if (diferenciaPeso < 0) {
                        System.out.println(strEgo + " ha engordado " + Math.abs(diferenciaPeso) + " kilos");
                    } else {
                        System.out.println(strEgo + " ha adelgazado " + Math.abs(diferenciaPeso) + " kilos");
                    }

                    break;
                }
                posicion += 110;
            }

            // El dni introducido no corresponde a ningún superhéroe
            if (!bExiste) {
                System.err.println("El dni introducido no existe");
                System.exit(0);
            }

            //---------------------------------APARTADO C-------------------------------//
            System.out.println("Introduce un tipo de personaje: ");
            String scnTipo = scanner.nextLine();

            posicion = 0;
            rf.seek(posicion);
            List<Superheroe> superheroes = new ArrayList<>();
            bExiste = false;  // reseteamos el valor para el último apartado

            while (rf.getFilePointer() != rf.length()) {
                id = rf.readInt();

                for (int i = 0; i < dni.length; i++) {
                    dni[i] = rf.readChar();
                }
                String strDni = new String(dni);

                for (int i = 0; i < alterEgo.length; i++) {
                    alterEgo[i] = rf.readChar();
                }
                String strEgo = new String(alterEgo).trim();

                for (int i = 0; i < nombre.length; i++) {
                    nombre[i] = rf.readChar();
                }
                String strNombre = new String(nombre).trim();

                for (int i = 0; i < tipo.length; i++) {
                    tipo[i] = rf.readChar();
                }
                String strTipo = new String(tipo).trim();

                peso = rf.readInt();
                altura = rf.readInt();

                if (strTipo.equals(scnTipo)) {
                    bExiste = true;
                    superheroes.add(new Superheroe(strDni, strEgo, strNombre, strTipo, peso, altura));
                }
                posicion += 110;
            }
            if (!bExiste) {
                System.err.println("No existen " + scnTipo + "s en el fichero");
            } else {
                System.out.println("Se han encontrado " + superheroes.size() + " " + scnTipo + "s");
                for (Superheroe heroe : superheroes) {
                    System.out.println(heroe.toString());
                }
            }

            // Cerrar flujos
            rf.close();
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
