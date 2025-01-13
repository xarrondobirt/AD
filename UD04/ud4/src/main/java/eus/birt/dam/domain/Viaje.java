package eus.birt.dam.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "viaje")
public class Viaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "ciudadDestino", nullable = false)
	private String ciudadDestino;

	@Column(name = "ciudadOrigen", nullable = false)
	private String ciudadOrigen;

	@Column(name = "fechaHora", nullable = false)
	private LocalDateTime fechaHora;

	@Column(name = "plazasDisponibles", nullable = false)
	private int plazasDisponibles;

	@ManyToOne
	@JoinColumn(name = "conductor", nullable = false)
	private Conductor conductor;

	@OneToMany(mappedBy = "viaje")
	private List<Reserva> reservas = new ArrayList<>();

	public Viaje() {
	}

	public Viaje(int id, String ciudadDestino, String ciudadOrigen, LocalDateTime fechaHora, int plazasDisponibles,
			Conductor conductor, List<Reserva> reservas) {
		this.id = id;
		this.ciudadDestino = ciudadDestino;
		this.ciudadOrigen = ciudadOrigen;
		this.fechaHora = fechaHora;
		this.plazasDisponibles = plazasDisponibles;
		this.conductor = conductor;
		this.reservas = reservas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudadDestino(String ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	public String getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(String ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}

	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}

	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	@Override
	public String toString() {
		return "Viaje [id=" + id + ", ciudadDestino=" + ciudadDestino + ", ciudadOrigen=" + ciudadOrigen
				+ ", fechaHora=" + fechaHora + ", plazasDisponibles=" + plazasDisponibles + ", conductor="
				+ conductor.getNombre() + "]";
	}
}
