package eus.birt.dam.service;

import java.util.Scanner;

import org.hibernate.Session;

import eus.birt.dam.domain.Conductor;

public class ConductorService {
	private final Session session;

	public ConductorService(Session sesion) {
		this.session = sesion;
	}

	/**
	 * Añade un conductor a la base de datos.
	 * 
	 * @param sc Objeto scanner para insertar los datos.
	 */
	public void crearConductor(Scanner sc) {
		sc.nextLine(); // Limpiar buffer
		System.out.println("Introduce el nombre del conductor:");
		String nombre = sc.nextLine();
		System.out.println("Introduce el vehículo del conductor:");
		String vehiculo = sc.nextLine();

		Conductor conductor = new Conductor();
		conductor.setNombre(nombre);
		conductor.setVehiculo(vehiculo);

		try {
			session.beginTransaction();
			session.persist(conductor);
			session.getTransaction().commit();
			System.out.println("Conductor creado correctamente");
		} catch (Exception e) {
			// Rollback ante alguna excepci�n
			System.out.println("Realizando Rollback");
			session.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	/**
	 * Busca un conductor por su ID en la base de datos.
	 * 
	 * @param conductorId El ID del conductor que se quiere buscar.
	 * @return El objeto Conductor si se encuentra, null si no existe.
	 */
	public Conductor buscarPorId(int conductorId) {

		return session.get(Conductor.class, conductorId);
	}
}
