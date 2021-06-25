package estructuraTP.modelo;

import java.sql.Date;
import java.time.LocalDate;

public class Propuestas extends Post{
	
	private LocalDate fechaCaducidad;
	private Tematica tematica;
	
	public LocalDate getFechaCaducidad() {
		return fechaCaducidad;
	}
	public void setFechaCaducidad(LocalDate fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}
	public Tematica getTematica() {
		return tematica;
	}
	public void setTematica(Tematica tematica) {
		this.tematica = tematica;
	}
		
	public Propuestas() {
		
	}
	public Propuestas(LocalDate fechapublicacion,LocalDate FechaCaducidad){
		this.fechaCaducidad = FechaCaducidad;
		setFechaPublicacion(fechapublicacion);
		
	}
	public Propuestas(int id, String personaQuePosteoNombre, String personaQuePosteoApellido, String titulo,
			LocalDate fechaPublicacion, String detalle, boolean caduco, LocalDate fechaCaducidad, Tematica tematica) {
		super(id, personaQuePosteoNombre, personaQuePosteoApellido, titulo, fechaPublicacion, detalle);
		this.fechaCaducidad = fechaCaducidad;
		this.tematica = tematica;
	}
	
	public boolean caducar() {
		Date d1 = java.sql.Date.valueOf(getFechaPublicacion());
		Date d2 = java.sql.Date.valueOf(fechaCaducidad);
		Date dnow = java.sql.Date.valueOf(LocalDate.now());
		
		
	
		Long dif1 = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
		Long dif2 = (dnow.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
	    
	    if(dif1 > dif2) {
	    	return true;
	    }
		return false;
	}
		
	public void actualizar() {
		
	}
	
	public int bit(boolean x) {
		if(x) {
			return 1;
		}
		return 0;
	}
	
}
