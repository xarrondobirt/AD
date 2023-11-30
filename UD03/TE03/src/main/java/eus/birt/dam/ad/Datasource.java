package eus.birt.dam.ad;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * Configuración de Hikari Datasource para ser llamado desde los ejercicios
 */
public class Datasource {
    protected static DataSource createDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/dbeventos");
        ds.setUsername("root");
        ds.setPassword("root");
        return ds;
    }
}
