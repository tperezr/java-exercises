package estructuraTP.modelo;

import java.util.ArrayList;

public class Referente {
	
	private int id;
	private String nombre;
	private String apellido;
	private String rol;	
	private ArrayList<Tematica> tematicas = new ArrayList<Tematica>();
	
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public ArrayList<Tematica> getTematicas() {
		return tematicas;
	}
	public void setTematicas(ArrayList<Tematica> tematicas) {
		this.tematicas = tematicas;
	}
	public Referente(int id, String nombre, String apellido, String rol, ArrayList<Tematica> tematicas) {
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.rol = rol;
		this.tematicas = tematicas;
	}
	public Referente() {
		
	}
	
	
	
	
	
}
