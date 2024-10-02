package ejercicio4;

public class Superheroe {

	private String dni;
	private String nombre;
	private String identidad;
	private String tipo;
	private int peso;
	private int altura;

	public Superheroe(String dni, String nombre, String identidad, String tipo, int peso, int altura) {
		this.dni = dni;
		this.nombre = nombre;
		this.identidad = identidad;
		this.tipo = tipo;
		this.peso = peso;
		this.altura = altura;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Personaje [" + "dni='" + dni + '\'' + ", nombre='" + nombre + '\'' + ", identidad='" + identidad + '\''
				+ ", tipo='" + tipo + '\'' + ", peso=" + peso + ", altura=" + altura + ']';
	}
}
