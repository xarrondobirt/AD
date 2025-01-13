package eus.birt.dam.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import eus.birt.dam.domain.Conductor;
import eus.birt.dam.domain.Viaje;

public class ViajeService {
	private final Session session;
	private final ConductorService conductorService;

	public ViajeService(Session sesion) {
		this.session = sesion;
		this.conductorService = new ConductorService(sesion);
	}

	/**
	 * Crea un nuevo viaje y lo guarda en la base de datos.
	 * 
	 * @param sc Objeto scanner para insertar los datos del viaje.
	 */
	public void crearViaje(Scanner sc) {
		sc.nextLine(); // Limpiar buffer
		System.out.println("Introduce la ciudad de origen del viaje:");
		String ciudadOrigen = sc.nextLine();
		System.out.println("Introduce la ciudad de destino del viaje:");
		String ciudadDestino = sc.nextLine();

		// Usamos el método para pedir la fecha y hora, que valida el formato
		LocalDateTime fechaHora = pedirFechaHora(sc);

		System.out.println("Introduce el número de plazas disponibles:");
		int plazasDisponibles = sc.nextInt();

		System.out.println("Introduce el id del conductor");
		int conductorId = sc.nextInt();
		Conductor conductor = null;

		while (conductor == null) {
			conductor = conductorService.buscarPorId(conductorId);
			if (conductor == null) {
				System.out.println("No se ha encontrado un conductor con ese id. Por favor, introduce un id válido:");
				conductorId = sc.nextInt();
			}
		}

		// Crear el objeto Viaje
		Viaje viaje = new Viaje();
		viaje.setCiudadOrigen(ciudadOrigen);
		viaje.setCiudadDestino(ciudadDestino);
		viaje.setFechaHora(fechaHora);
		viaje.setPlazasDisponibles(plazasDisponibles);
		viaje.setConductor(conductor);

		try {
			session.beginTransaction();
			session.persist(viaje);
			session.getTransaction().commit();
			System.out.println("Viaje creado correctamente");
		} catch (Exception e) {
			// Rollback en caso de error
			System.out.println("Realizando Rollback");
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Busca los viajes disponibles basados en un origen y destino.
	 * 
	 * @param ciudadOrigen  La ciudad de origen.
	 * @param ciudadDestino La ciudad de destino.
	 */
	public void buscarViajes(Scanner sc) {
		sc.nextLine(); // Limpiar buffer
		System.out.println("Introduce la ciudad de origen del viaje:");
		String ciudadOrigen = sc.nextLine();
		System.out.println("Introduce la ciudad de destino del viaje:");
		String ciudadDestino = sc.nextLine();
		try {
			List<Viaje> viajes = session
					.createQuery("FROM Viaje WHERE ciudadOrigen = :origen AND ciudadDestino = :destino", Viaje.class)
					.setParameter("origen", ciudadOrigen).setParameter("destino", ciudadDestino).list();

			if (viajes.isEmpty()) {
				System.out.println("No hay viajes disponibles entre " + ciudadOrigen + " y " + ciudadDestino);
			} else {
				System.out.println("Viajes disponibles:");
				for (Viaje viaje : viajes) {
					System.out.println(viaje);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Devuelve todos los viajes de la base de datos
	 */
	public void listarViajes() {

		try {
			List<Viaje> viajes = session.createQuery("FROM Viaje", Viaje.class).list();

			System.out.println("Lista de viajes:");
			for (Viaje viaje : viajes) {
				System.out.println(viaje);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Busca un viaje por su ID en la base de datos.
	 * 
	 * @param viajeId El ID del conductor que se quiere buscar.
	 * @return El objeto Viaje si se encuentra, null si no existe.
	 */
	public Viaje buscarPorId(int viajeId) {

		return session.get(Viaje.class, viajeId);
	}

	/**
	 * Método que pide la fecha y hora de un viaje y la formatea a LocalDateTime
	 * 
	 * @param sc Objeto Scanner para introducir los datos
	 * @return Objeto LocalDateTime con la fecha y hora introducidos.
	 */
	private static LocalDateTime pedirFechaHora(Scanner sc) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		LocalDateTime fechaHora = null;
		boolean fechaValida = false;

		while (!fechaValida) {
			System.out.println("Introduce la fecha y hora del viaje (formato dd-MM-yyyy HH:mm):");
			String fechaHoraString = sc.nextLine();
			try {

				fechaHora = LocalDateTime.parse(fechaHoraString, formatter);
				fechaValida = true;
			} catch (Exception e) {

				// Si hay un error, mostramos un mensaje y seguimos pidiendo la fecha
				System.out.println("Formato de fecha incorrecto. Por favor, usa el formato dd-MM-yyyy HH:mm");
			}
		}

		return fechaHora;
	}
}
