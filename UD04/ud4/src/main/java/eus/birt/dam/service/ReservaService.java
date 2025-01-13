package eus.birt.dam.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.hibernate.Session;

import eus.birt.dam.domain.Pasajero;
import eus.birt.dam.domain.Reserva;
import eus.birt.dam.domain.Viaje;

public class ReservaService {
	private final Session session;
	private final ViajeService viajeService;
	private final PasajeroService pasajeroService;

	public ReservaService(Session sesion) {
		this.session = sesion;
		this.viajeService = new ViajeService(sesion);
		this.pasajeroService = new PasajeroService(sesion);
	}

	/**
	 * Añade una reserva a la base de datos.
	 * 
	 * @param sc Objeto scanner para insertar los datos.
	 */
	public void crearReserva(Scanner sc) {
		System.out.println("Introduce el id del pasajero:");
		int pasajeroId = sc.nextInt();

		// Verificar que el pasajero existe
		Pasajero pasajero = pasajeroService.buscarPorId(pasajeroId);
		while (pasajero == null) {
			System.out.println("Pasajero no encontrado. Introduce un id de pasajero válido:");
			pasajeroId = sc.nextInt();
			pasajero = pasajeroService.buscarPorId(pasajeroId);
		}

		System.out.println("Introduce el id del viaje:");
		int viajeId = sc.nextInt();

		// Verificar que el viaje existe
		Viaje viaje = viajeService.buscarPorId(viajeId);
		while (viaje == null) {
			System.out.println("Viaje no encontrado. Introduce un id de viaje válido:");
			viajeId = sc.nextInt();
			viaje = viajeService.buscarPorId(viajeId);
		}

		// Usamos el método para pedir la fecha y hora, que valida el formato
		LocalDate fecha = obtenerFecha(sc);

		System.out.println("Introduce el número de plazas reservadas:");
		int numeroPlazasReservadas = sc.nextInt();

		// Crear la reserva
		Reserva reserva = new Reserva();
		reserva.setFechaReserva(fecha);
		reserva.setNumeroPlazasReservadas(numeroPlazasReservadas);
		reserva.setPasajero(pasajero);
		reserva.setViaje(viaje);

		try {
			session.beginTransaction();
			session.persist(reserva);
			session.getTransaction().commit();
			System.out.println("Pasajero creado correctamente");
		} catch (Exception e) {
			// Rollback ante alguna excepci�n
			System.out.println("Realizando Rollback");
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Cancela (borra) una reserva de la base de datos.
	 * 
	 * @param sc Objeto scanner para introducir los datos
	 */
	public void cancelarReserva(Scanner sc) {
		System.out.println("Introduce el id de la reserva que deseas cancelar:");
		int reservaId = sc.nextInt();

		// Buscar la reserva por su id
		Reserva reserva = this.buscarPorId(reservaId);
		while (reserva == null) {
			System.out.println("Reserva no encontrada. Introduce un id válido:");
			reservaId = sc.nextInt();
			reserva = this.buscarPorId(reservaId);
		}

		try {
			session.beginTransaction();
			session.remove(reserva);
			session.getTransaction().commit();
			System.out.println("Reserva con id " + reservaId + " cancelada correctamente.");
		} catch (Exception e) {
			// Rollback ante cualquier excepción
			System.out.println("Error al cancelar la reserva. Realizando Rollback.");
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Busca una reserva por su ID en la base de datos.
	 * 
	 * @param reservaId El ID del conductor que se quiere buscar.
	 * @return El objeto Reserva si se encuentra, null si no existe.
	 */
	public Reserva buscarPorId(int reservaId) {

		return session.get(Reserva.class, reservaId);
	}

	/**
	 * Método que pide la fecha de una reserva y la formatea a LocalDate
	 * 
	 * @param sc Objeto Scanner para introducir los datos
	 * @return Objeto LocalDate con la fecha introducida.
	 */
	private static LocalDate obtenerFecha(Scanner sc) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate fecha = null;
		boolean fechaValida = false;

		sc.nextLine(); // Limpiar buffer

		while (!fechaValida) {
			System.out.println("Introduce la fecha de la reserva (formato dd-MM-yyyy):");
			String fechaString = sc.nextLine();
			try {

				fecha = LocalDate.parse(fechaString, formatter);
				fechaValida = true;
			} catch (Exception e) {

				// Si hay un error, mostramos un mensaje y seguimos pidiendo la fecha
				System.out.println("Formato de fecha incorrecto. Por favor, usa el formato dd-MM-yyyy");
			}
		}

		return fecha;
	}
}
