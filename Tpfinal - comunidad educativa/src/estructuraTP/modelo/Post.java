package estructuraTP.modelo;

import java.time.LocalDate;

public abstract class Post {

	private int id;
	private String personaQuePosteoNombre;
	private String personaQuePosteoApellido;
	private String titulo;
	private LocalDate fechaPublicacion;
	private String detalle;
	
	public abstract boolean caducar();
	public abstract void actualizar();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPersonaQuePosteoNombre() {
		return personaQuePosteoNombre;
	}
	public void setPersonaQuePosteoNombre(String personaQuePosteoNombre) {
		this.personaQuePosteoNombre = personaQuePosteoNombre;
	}
	public String getPersonaQuePosteoApellido() {
		return personaQuePosteoApellido;
	}
	public void setPersonaQuePosteoApellido(String personaQuePosteoApellido) {
		this.personaQuePosteoApellido = personaQuePosteoApellido;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	public Post(int id, String personaQuePosteoNombre, String personaQuePosteoApellido, String titulo,
			LocalDate fechaPublicacion, String detalle) {
		this.id = id;
		this.personaQuePosteoNombre = personaQuePosteoNombre;
		this.personaQuePosteoApellido = personaQuePosteoApellido;
		this.titulo = titulo;
		this.fechaPublicacion = fechaPublicacion;
		this.detalle = detalle;
	}

	public Post() {
		
	}
	

}

