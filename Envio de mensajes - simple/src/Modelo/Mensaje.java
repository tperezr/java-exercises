package Modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mensaje implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3907090890754626514L;
	private String msj;
	private String emisor;
	LocalDateTime fechayhora;
	

	public Mensaje(String _msj, LocalDateTime _fechayhora, String _emisor) {
		this.msj = _msj;
		this.fechayhora = _fechayhora;
		this.emisor = _emisor;
	}

	public Mensaje() {
	}

	public String toString() {
		String newString = "[" + getFormat().substring(0, 10) + "] [" + getFormat().substring(11, 19) + "]";
		return ("" + newString);
	}

	public String getFormat() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return dtf.format(fechayhora);
	}
	public LocalDateTime getFechayhora() {
		return fechayhora;
	}

	public void setFechayhora(LocalDateTime fechayhora) {
		this.fechayhora = fechayhora;
	}

	public String getMsj() {
		return msj;
	}

	public void setMsj(String msj) {
		this.msj = msj;
	}

	public String getEmisor() {
		return emisor;
	}

	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}

}
