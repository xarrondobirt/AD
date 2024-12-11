package eus.birt.dam.ad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * Crea un programa Java que muestre por pantalla un listado de eventos, junto
 * con el número de asistentes, ubicación y dirección.
 */
public class Ej01 {

	public static void main(String[] args) throws SQLException {

		String query = "select nombre_evento, count(dni) as aforo,nombre,direccion from eventos e "
				+ "left join ubicaciones u on e.id_ubicacion = u.id_ubicacion left join asistentes_eventos a on e.id_evento = a.id_evento"
				+ " group by nombre_evento,nombre,direccion";

		DataSource ds = Datasource.createDataSource();
		try (Connection con = ds.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {

			ResultSet resultSet = stmt.executeQuery();
			System.out.println("Evento\t| Asistentes\t| Ubicacion\t| Direccion");
			while (resultSet.next()) {
				String evento = resultSet.getString(1);
				int asistentes = resultSet.getInt(2);
				String ubicacion = resultSet.getString(3);
				String direccion = resultSet.getString(4);

				System.out.println(evento + " | " + asistentes + " | " + ubicacion + " | " + direccion);
			}
		}
	}
}
