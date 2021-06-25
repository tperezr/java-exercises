package estructuraTP.modelo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Problematica extends Post{
	
	private boolean fuePlanteada; // 1-true-yes  0-false-no
	private boolean cerrada; // 1-true-no 0-false-yes
	private String observacion;
	private Tematica tematica;
	private Referente referente;
		
	public Referente getReferente() {
		return referente;
	}
	public void setReferente(Referente referente) {
		this.referente = referente;
	}
	public boolean getFuePlanteada() {
		return fuePlanteada;
	}
	public void setFuePlanteada(boolean fuePlanteada) {
		this.fuePlanteada = fuePlanteada;
	}
	public boolean getCerrada() {
		return cerrada;
	}
	public void setCerrada(boolean cerrada) {
		this.cerrada = cerrada;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Tematica getTematica() {
		return tematica;
	}
	public void setTematica(Tematica tematica) {
		this.tematica = tematica;
	}
	
	public Problematica() {
		super();
	}
	
	public Problematica(int id, String personaQuePosteoNombre, String personaQuePosteoApellido, String titulo,
			LocalDate fechaPublicacion, String detalle, boolean fuePlanteada, boolean cerrada,
			String observacion, Tematica tematica, Referente referente) {
		super(id, personaQuePosteoNombre, personaQuePosteoApellido, titulo, fechaPublicacion, detalle);
		this.fuePlanteada = fuePlanteada;
		this.cerrada = cerrada;
		this.observacion = observacion;
		this.tematica = tematica;
		this.referente = referente;
	}
	
	//1-true-si 2-false-no
	public boolean caducar() {
		Date d1 = java.sql.Date.valueOf(getFechaPublicacion());
		Date d2 = java.sql.Date.valueOf(LocalDate.now());
	
		Long dif = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
	    
	    if(dif >= 45) {
	    	return true;
	    }
		return false;
	}
	
	public void actualizar() {
		
	}	
}
