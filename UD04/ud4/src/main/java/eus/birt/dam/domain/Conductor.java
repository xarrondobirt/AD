package eus.birt.dam.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "conductor")
public class Conductor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "vehiculo", nullable = false)
	private String vehiculo;

	@OneToMany(mappedBy = "conductor")
	private List<Viaje> viajes = new ArrayList<>();

	public Conductor() {
	}

	public Conductor(int id, String nombre, String vehiculo, List<Viaje> viajes) {
		this.id = id;
		this.nombre = nombre;
		this.vehiculo = vehiculo;
		this.viajes = viajes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(String vehiculo) {
		this.vehiculo = vehiculo;
	}

	public List<Viaje> getViajes() {
		return viajes;
	}

	public void setViajes(List<Viaje> viajes) {
		this.viajes = viajes;
	}

}
