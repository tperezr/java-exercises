package modelo;

import java.time.LocalDateTime;

public class Mail {
	private int idMail;
	private String fromUser;
	private int idUser;
	private String fecha;
	private String asunto;
	private String cuerpo;

	public Mail(int idMail, String fromUser, int idUser, String fecha, String asunto, String cuerpo) {
		this.idMail = idMail;
		this.fromUser = fromUser;
		this.idUser = idUser;
		this.fecha = fecha;
		this.asunto = asunto;
		this.cuerpo = cuerpo;
	}

	public int getIdMail() {
		return idMail;
	}

	public void setIdMail(int idMail) {
		this.idMail = idMail;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public int getToUser() {
		return idUser;
	}

	public void setToUser(int idUser) {
		this.idUser = idUser;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
}
