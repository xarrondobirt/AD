package eus.birt.dam.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "pasajero", uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class Pasajero {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	public Pasajero() {

	}

	public Pasajero(int id, String email, String nombre) {
		super();
		this.id = id;
		this.email = email;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Pasajero [id=" + id + ", email=" + email + ", nombre=" + nombre + "]";
	}
}
