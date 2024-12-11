package eus.birt.dam.ad;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Crea un programa Java que consulte la cantidad de asistentes a un evento específico utilizando la función almacenada obtener_numero_asistentes.
 * La función recibe como parámetro el código del evento y devuelve el número de asistentes a dicho evento.
 */
public class Ej04 {

    public static void main(String[] args) throws SQLException {

        DataSource ds = Datasource.createDataSource();
        String queryEventos = "select id_evento, nombre_evento from eventos";
        String procedureCall = "{? = call obtener_numero_asistentes(?)}";

        try (Connection con = ds.getConnection()) {
            try (PreparedStatement stmtEventos = con.prepareStatement(queryEventos)) {
                ResultSet rsEventos = stmtEventos.executeQuery();
                while (rsEventos.next()) {
                    int idEvento = rsEventos.getInt(1);
                    String evento = rsEventos.getString(2);
                    System.out.println(idEvento + ". " + evento);
                }

                System.out.println("Introduce el ID del evento para consultar la cantidad de asistentes:");
                Scanner sc = new Scanner(System.in);
                int numEvento = Integer.parseInt(sc.nextLine());

                try (CallableStatement callable = con.prepareCall(procedureCall)) {

                    callable.setInt(2, numEvento);
                    callable.registerOutParameter(1, java.sql.Types.INTEGER);
                    callable.execute();
                    int cantidadAsistentes = callable.getInt(1);

                    System.out.println("El número de asistentes para el evento seleccionado es: " + cantidadAsistentes);
                }
            }
        }
    }
}


