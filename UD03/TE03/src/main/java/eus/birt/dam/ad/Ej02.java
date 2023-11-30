package eus.birt.dam.ad;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Crea un programa Java que te permita modificar el nombre de una asistente.
 * El programa recibe desde la línea de comandos o consola el dni del asistente.
 * A partir de ese dato mostrará por pantalla el nombre almacenado actualmente y nos pedirá un nuevo nombre.
 * Si el usuario no escribe un nuevo nombre no se realizará ninguna modificación en la BBDD.
 */
public class Ej02 {

    public static void main(String[] args) throws SQLException {

        String querySelect = "select * from asistentes where dni = ?";
        String queryUpdate = "update asistentes set nombre = ? where dni = ?";
        DataSource ds = Datasource.createDataSource();
        try (Connection con = ds.getConnection()) {
            try (PreparedStatement stmtSelect = con.prepareStatement(querySelect)) {

                Scanner sc = new Scanner(System.in);
                System.out.println("Introduce un dni");

                // Se busca en la BD, si no existe se para la ejecución, si existe se pide un nombre
                String dni = sc.nextLine();
                stmtSelect.setString(1, dni);
                ResultSet rsSelect = stmtSelect.executeQuery();
                if (!rsSelect.next()) {
                    System.out.println("No hay asistentes con ese DNI");
                    System.exit(0);
                } else {
                    System.out.println("DNI: " + rsSelect.getString(1) + " " + "Nombre: " + rsSelect.getString(2));
                }

                try (PreparedStatement stmtUpdate = con.prepareStatement(queryUpdate)) {
                    System.out.println("Introduce un nuevo nombre");
                    String nuevoNombre = sc.nextLine();
                    stmtUpdate.setString(1, nuevoNombre);
                    stmtUpdate.setString(2, dni);
                    int updatedRows = stmtUpdate.executeUpdate();

                    System.out.println("Número de filas afectadas: " + updatedRows);
                }
            }
        }
    }
}


