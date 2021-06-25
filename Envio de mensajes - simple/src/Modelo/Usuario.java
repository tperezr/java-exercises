package Modelo;

import java.io.Serializable;

import Sockets.progredes.*;

public class Usuario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3441770795791815947L;
	private String nombreUsuario;
	private SocketClienteTCP socket;

	public Usuario(String nombreUsuario, SocketClienteTCP socket) {
		this.nombreUsuario = nombreUsuario;
		this.socket = socket;
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

}
