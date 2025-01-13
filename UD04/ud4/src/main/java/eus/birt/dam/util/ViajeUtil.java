package eus.birt.dam.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import eus.birt.dam.domain.Conductor;
import eus.birt.dam.domain.Pasajero;
import eus.birt.dam.domain.Reserva;
import eus.birt.dam.domain.Viaje;

public class ViajeUtil {

	private static final SessionFactory sessionFactory;

	static {
		try {

			// Crea sessionFactory y session
			StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml")
					.build();

			// Añadir las entidades
			MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Conductor.class)
					.addAnnotatedClass(Viaje.class).addAnnotatedClass(Pasajero.class).addAnnotatedClass(Reserva.class);

			Metadata metadata = sources.getMetadataBuilder().build();
			sessionFactory = metadata.getSessionFactoryBuilder().build();

		} catch (Exception ex) {
			throw new ExceptionInInitializerError("Error al inicializar Hibernate: " + ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
