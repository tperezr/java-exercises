package modelo;

import java.util.ArrayList;


public class Usuario {
	private int idUsuario;
	private String nombre;
	private String apellido;
	private String email;
	private String contraseña;
	private ArrayList<Mail> inbox;
	private ArrayList<Mail> outbox;
	
	public Usuario(int idUsuario, String nombre, String apellido, String email, String contraseña,
			ArrayList<Mail> inbox, ArrayList<Mail> outbox) {
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

	public ArrayList<Mail> getInbox() {
		return inbox;
	}

	public void setInbox(ArrayList<Mail> inbox) {
		this.inbox = inbox;
	}

	public ArrayList<Mail> getOutbox() {
		return outbox;
	}

	public void setOutbox(ArrayList<Mail> outbox) {
		this.outbox = outbox;
	}


}
