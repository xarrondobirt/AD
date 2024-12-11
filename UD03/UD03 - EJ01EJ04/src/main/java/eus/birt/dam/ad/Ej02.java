package eus.birt.dam.ad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.sql.DataSource;

/**
 * Crea un programa Java que permita cambiar la capacidad máxima de una
 * ubicación. El programa recibirá el nombre de la ubicación, mostrará la
 * capacidad actual y luego permitirá al usuario actualizar la capacidad máxima.
 * Si la ubicación no existe, se informará al usuario.
 */
public class Ej02 {

	public static void main(String[] args) throws SQLException {

		String querySelect = "select * from ubicaciones where nombre = ?";
		String queryUpdate = "update ubicaciones set capacidad = ? where nombre = ?";
		DataSource ds = Datasource.createDataSource();
		boolean ubicacionValida = false;

		// Mientras no se introduzca una ubicación que exista en BD
		while (!ubicacionValida) {
			try (Connection con = ds.getConnection();
					PreparedStatement stmtSelect = con.prepareStatement(querySelect)) {

				Scanner sc = new Scanner(System.in);
				System.out.println("Introduce el nombre de la ubicación: ");

				String nombre = sc.nextLine();
				stmtSelect.setString(1, nombre);
				ResultSet rsSelect = stmtSelect.executeQuery();
				if (!rsSelect.next()) {
					System.out.println("No hay ubicaciones con ese nombre");
				} else {
					System.out.println("La capacidad actual de la ubicación '" + rsSelect.getString(2) + "' es:  "
							+ rsSelect.getInt(4));

					try (PreparedStatement stmtUpdate = con.prepareStatement(queryUpdate)) {
						System.out.println("Introduce la nueva capacidad máxima: ");
						int nuevaCapacidad = sc.nextInt();
						stmtUpdate.setInt(1, nuevaCapacidad);
						stmtUpdate.setString(2, nombre);
						stmtUpdate.executeUpdate();
						System.out.println("Capacidad actualizada correctamente");
					}
					ubicacionValida = true;
				}

			} catch (SQLException e) {
				System.out.println("Error al realizar la operación: " + e.getMessage());
			}
		}
	}
}
