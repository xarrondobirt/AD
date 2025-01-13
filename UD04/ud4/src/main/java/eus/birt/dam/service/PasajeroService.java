package eus.birt.dam.service;

import java.util.Scanner;

import org.hibernate.Session;

import eus.birt.dam.domain.Pasajero;

public class PasajeroService {
	private final Session session;

	public PasajeroService(Session sesion) {
		this.session = sesion;
	}

	/**
	 * Añade un pasajero a la base de datos.
	 * 
	 * @param sc Objeto scanner para insertar los datos.
	 */
	public void crearPasajero(Scanner sc) {
		sc.nextLine(); // Limpiar buffer
		System.out.println("Introduce el nombre del pasajero:");
		String nombre = sc.nextLine();
		System.out.println("Introduce el email del pasajero:");
		String email = sc.nextLine();

		// Verificar que el email no esté en uso
		while (this.buscarPorEmail(email) != null) {
			System.out.println("El email ya está en uso. Introduce otro email:");
			email = sc.nextLine();
		}

		Pasajero pasajero = new Pasajero();
		pasajero.setNombre(nombre);
		pasajero.setEmail(email);

		try {
			session.beginTransaction();
			session.persist(pasajero);
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
	 * Busca un pasajero por su ID en la base de datos.
	 * 
	 * @param pasajeroId El ID del conductor que se quiere buscar.
	 * @return El objeto Pasajero si se encuentra, null si no existe.
	 */
	public Pasajero buscarPorId(int pasajeroId) {

		return session.get(Pasajero.class, pasajeroId);
	}

	/**
	 * Busca un pasajero por su email
	 * 
	 * @param email Email del pasajero a buscar.
	 * @return Un pasajero con el email, si existe
	 */
	private Pasajero buscarPorEmail(String email) {
		return session.createQuery("FROM Pasajero p WHERE p.email = :email", Pasajero.class)
				.setParameter("email", email).uniqueResult();
	}
}
