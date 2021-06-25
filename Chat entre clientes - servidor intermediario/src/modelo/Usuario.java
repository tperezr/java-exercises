package modelo;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Sockets.progredes.SocketClienteTCP;

public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3441770795791815947L;
	
	private int id;
	private String alias;
	private String nombreUsuario;
	private String contraseña;
	private SocketClienteTCP socket;
	private String grupo;
	
	public Usuario(int _id, String _alias, String _nombreUsuario, String _contraseña, SocketClienteTCP _socket) {
		this.id = _id;
		this.alias = _alias;
		this.nombreUsuario = _nombreUsuario;
		this.contraseña = _contraseña;
		this.socket = _socket;
	}
	
	public Usuario(int id, String alias, String nombreUsuario, String contraseña) {
		this.id = id;
		this.alias = alias;
		this.nombreUsuario = nombreUsuario;
		this.contraseña = contraseña;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public SocketClienteTCP getSocket() {
		return socket;
	}

	public void setSocket(SocketClienteTCP socket) {
		this.socket = socket;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

}
