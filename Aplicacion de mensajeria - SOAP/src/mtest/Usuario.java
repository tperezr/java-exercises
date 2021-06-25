package mtest;

import java.util.ArrayList;

public class Usuario {	
	private int idUsuario;
	private String nombre;
	private String apellido;
	private String email;
	private String contraseña;
	private ArrayList<Inbox> inbox;
	private ArrayList<Outbox> outbox;

	public Usuario(int idUsuario, String nombre, String apellido, String email, String contraseña,
			ArrayList<Inbox> inbox, ArrayList<Outbox> outbox) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellido = apellido;
		this.email = email;
		this.contraseña = contraseña;
		this.inbox = inbox;
		this.outbox = outbox;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public ArrayList<Inbox> getInbox() {
		return inbox;
	}

	public void setInbox(ArrayList<Inbox> inbox) {
		this.inbox = inbox;
	}

	public ArrayList<Outbox> getOutbox() {
		return outbox;
	}

	public void setOutbox(ArrayList<Outbox> outbox) {
		this.outbox = outbox;
	}

}
