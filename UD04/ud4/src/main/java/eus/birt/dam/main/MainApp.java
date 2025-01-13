package eus.birt.dam.main;

import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import eus.birt.dam.service.ConductorService;
import eus.birt.dam.service.PasajeroService;
import eus.birt.dam.service.ReservaService;
import eus.birt.dam.service.ViajeService;
import eus.birt.dam.util.HibernateUtil;

public class MainApp {

	public static void main(String[] args) {

		// Inicializar sesión
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session sesion = sessionFactory.openSession();

		// Servicios
		ConductorService conductorService = new ConductorService(sesion);
		ViajeService viajeService = new ViajeService(sesion);
		PasajeroService pasajeroService = new PasajeroService(sesion);
		ReservaService reservaService = new ReservaService(sesion);

		try {

			Scanner sc = new Scanner(System.in);
			int opcion = 0;
			while (opcion != 8) {
				System.out.println("=== Menú de Gestión de Viajes Compartidos");
				System.out.println("1. Crear conductor");
				System.out.println("2. Crear viaje");
				System.out.println("3. Buscar viajes disponibles");
				System.out.println("4. Crear pasajero");
				System.out.println("5. Crear reserva");
				System.out.println("6. Cancelar reserva");
				System.out.println("7. Listar viajes");
				System.out.println("8. Salir");
				opcion = sc.nextInt();

				switch (opcion) {
				case 1:
					// Crear conductor
					conductorService.crearConductor(sc);
					break;
				case 2:
					// Crear viaje
					viajeService.crearViaje(sc);
					break;
				case 3:
					// Buscar viajes disponibles
					viajeService.buscarViajes(sc);
					break;
				case 4:
					// Crear pasajero
					pasajeroService.crearPasajero(sc);
					break;
				case 5:
					// Crear reserva
					reservaService.crearReserva(sc);
					break;
				case 6:
					// Cancelar reserva
					reservaService.cancelarReserva(sc);
					break;
				case 7:
					// Listar viajes
					viajeService.listarViajes();
					break;
				case 8:
					// Salir
					System.out.println("Saliendo...");
					sesion.close();
					sessionFactory.close();
					System.exit(0);
					break;
				default:
					sesion.close();
					sessionFactory.close();
					System.out.println("Opción no válida");
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Opción no válida");
		}
	}
}
