package modeloMensajes;

import java.io.Serializable;

public class MensajeGrupo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6825208963027709689L;

	private String username;
	private String nombreGrupo;

	public MensajeGrupo(String username, String nombreGrupo) {
		this.username = username;
		this.nombreGrupo = nombreGrupo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

}
