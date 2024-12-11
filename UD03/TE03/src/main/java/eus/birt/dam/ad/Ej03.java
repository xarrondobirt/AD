package eus.birt.dam.ad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.sql.DataSource;

/**
 * Crea un programa que permita registrar un asistente a un evento. Se pedirá el
 * dni. Se mostrará en consola el listado de eventos, cada uno con un número y
 * se le pedirá al usuario que elija el evento. Si el dni no se encuentra en la
 * BD se guarda en la tabla de asistentes. Si el evento ya alcanzó su capacidad
 * máxima (basado en la ubicación), el programa debe negar la entrada. Se debe
 * controlar que el dni tenga formato de 8 números y una letra seguido.
 */
public class Ej03 {

	public static void main(String[] args) throws SQLException {

		String queryEventos = "SELECT e.id_evento, e.nombre_evento, COUNT(a.dni) AS aforo, u.nombre, u.direccion, u.capacidad, (u.capacidad - COUNT(a.dni)) AS huecos "
				+ "FROM eventos e " + "LEFT JOIN ubicaciones u ON e.id_ubicacion = u.id_ubicacion "
				+ "LEFT JOIN asistentes_eventos a ON e.id_evento = a.id_evento "
				+ "GROUP BY e.id_evento, e.nombre_evento, u.nombre, u.direccion, u.capacidad";
		String queryDni = "select * from asistentes where dni = ?";
		DataSource ds = Datasource.createDataSource();

		// Patrón para comprobar DNI correcto
		String patronDNI = "^\\d{8}[a-zA-Z]$";
		Pattern pattern = Pattern.compile(patronDNI);

		try (Connection con = ds.getConnection()) {
			String nombreAsistente = "";
			Scanner sc = new Scanner(System.in);
			boolean bDniCorrecto = false;
			String dni = "";

			// Se pide DNI mientras el formato introducido no sea correcto
			while (!bDniCorrecto) {
				System.out.println("Introduce el DNI del asistente");
				dni = sc.nextLine();
				if (pattern.matcher(dni).matches()) {
					bDniCorrecto = true;
				}
			}

			// Se busca el DNI en la BD, si no existe se pide el nombre para registrarlo
			try (PreparedStatement stmtSelect = con.prepareStatement(queryDni)) {
				stmtSelect.setString(1, dni);
				ResultSet rsSelect = stmtSelect.executeQuery();
				if (!rsSelect.next()) {
					System.out.println("No se encontró un asistente con el DNI proporcionado");
					System.out.println("Introduce el nombre del asistente:");
					String nombre = sc.nextLine();
					String queryInsertAsistente = "insert into asistentes (dni, nombre) values (?,?)";
					try (PreparedStatement stmt = con.prepareStatement(queryInsertAsistente)) {
						stmt.setString(1, dni);
						stmt.setString(2, nombre);
						stmt.executeUpdate();
						nombreAsistente = nombre;
					}
				} else {
					nombreAsistente = rsSelect.getString(2);

				}
				System.out.println("Estás haciendo la reserva para: " + nombreAsistente);
			}

			// Se muestran los eventos disponibles
			try (PreparedStatement stmtSelectEventos = con.prepareStatement(queryEventos,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
				ResultSet rsEventos = stmtSelectEventos.executeQuery();
				while (rsEventos.next()) {
					int idEvento = rsEventos.getInt(1);
					String evento = rsEventos.getString(2);
					int huecos = rsEventos.getInt(7);

					System.out.println(idEvento + ". " + evento + " - " + huecos);
				}

				System.out.println("Listado de eventos:");
				System.out.println("Elige el número del evento al que se quiere asistir:");
				int idEventoIn = Integer.parseInt(sc.nextLine());

				// Busca el evento con el id seleccionado en el ResultSet
				rsEventos.beforeFirst(); // Coloca el cursor antes de la primera fila
				boolean bEvento = false;

				// Se busca el evento introducido y se añade el asistente si es posible
				while (rsEventos.next() && !bEvento) {
					int idEvento = rsEventos.getInt(1);
					if (idEvento == idEventoIn) {
						bEvento = true;
						int asistentes = rsEventos.getInt(3);
						int capacidad = rsEventos.getInt(6);
						if (asistentes >= capacidad) {
							System.out.println("No hay más entradas para el evento seleccionado");
						} else {
							String queryInsertEvento = "insert into asistentes_eventos (dni, id_evento) values (?,?)";
							try (PreparedStatement stmt = con.prepareStatement(queryInsertEvento)) {
								stmt.setString(1, dni);
								stmt.setInt(2, idEvento);
								stmt.executeUpdate();
								System.out.println(nombreAsistente + " ha sido registrado para el evento seleccionado");
							}
						}
					}
				}
				if (!bEvento) {
					System.out.println("El evento no existe");
				}
			} catch (SQLException e) {
				System.out.println("Error con la base de datos");
			}
		}
	}
}
