package eus.birt.dam.ad;

import org.vibur.dbcp.ViburDBCPDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Ejercicio SGBD embebida y pool de conexiones
 */
public class EJ05 {
    public static void main(String[] args) {
        ViburDBCPDataSource ds = createDataSource();
        ds.start();
        try (Connection con = ds.getConnection()) {
            System.out.println("Conexión válida: " + con.isValid(0));
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private static ViburDBCPDataSource createDataSource() {
        ViburDBCPDataSource ds = new ViburDBCPDataSource();
        ds.setJdbcUrl("jdbc:hsqldb:file:/ruta/a/tu/base/de/datos");
        ds.setUsername("root");
        ds.setPassword("root");

        // Configuración adicional: número máximo de conexiones
        ds.setPoolMaxSize(50);
        return ds;
    }
}
