package modeloMensajes;

import java.io.Serializable;

/**
 * 1. Mensaje Login
 * <p>
 * 2. Mensaje Registro
 * <p>
 * 3. Unirse al grupo
 * <p>
 * 4. Salir grupo
 * <p>
 * 5. MensajeChat
 * <p>
 * 6. Conectar al grupo
 * <p>
 */
public class Mensaje implements Serializable {
	
	private static final long serialVersionUID = -2420590965478490192L;
	
	private int codigo;
	private Object objecto;
	/**
	 * 1. Mensaje Login
	 * <p>
	 * 2. Mensaje Registro
	 * <p>
	 * 3. Unirse al grupo
	 * <p>
	 * 4. Salir grupo
	 * <p>
	 * 5. MensajeChat
	 * <p>
	 * 6. Conectar al grupo
	 * <p>
	 */
	/*
	 * 1. Mensaje Login
	 * 2. Mensaje Registro
	 * 3. Unirse al grupo
	 * 4. Salir grupo
	 * 5. MensajeChat
	 * */
	public Mensaje(int codigo, Object _objeto) {
		this.codigo = codigo;
		this.objecto = _objeto;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Object getObjecto() {
		return objecto;
	}

	public void setObjecto(Object objecto) {
		this.objecto = objecto;
	}

}
