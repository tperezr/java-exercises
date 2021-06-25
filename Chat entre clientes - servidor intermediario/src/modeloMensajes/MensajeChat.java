package modeloMensajes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MensajeChat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5371134132517767914L;
	
	private String msj;
	private String emisor;
	private String nombreGrupo;
	LocalDateTime fechayhora;
	
	public MensajeChat(String _msj, LocalDateTime _fechayhora, String _emisor, String _nombreGrupo) {
		
		this.msj = _msj;
		this.fechayhora = _fechayhora;
		this.emisor = _emisor;
		this.nombreGrupo = _nombreGrupo;
	}

	public MensajeChat() {
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

	public String getNombreGrupo() {
		return nombreGrupo;
	}

	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}

}
