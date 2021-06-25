package modeloMensajes;

import java.io.Serializable;

public class MensajeRegistro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4556369406230308235L;
	
	private String alias;
	private String username;
	private String password;

	public MensajeRegistro(String alias, String username, String password) {
		this.alias = alias;
		this.username = username;
		this.password = password;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
