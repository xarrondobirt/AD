package eus.birt.dam.ad;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

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
