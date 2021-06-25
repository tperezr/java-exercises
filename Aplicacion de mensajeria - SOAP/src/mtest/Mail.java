package mtest;

import java.time.LocalDateTime;

public class Mail {
	private int idMail;
	private int fromUser;
	private int toUser;
	private String fecha;
	private String asunto;
	private String cuerpo;
	
	public Mail(int idMail, int fromUser, int toUser, String fecha, String asunto, String cuerpo) {
		this.idMail = idMail;
		this.fromUser = fromUser;
		this.toUser = toUser;
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

	public int getFromUser() {
		return fromUser;
	}

	public void setFromUser(int fromUser) {
		this.fromUser = fromUser;
	}

	public int getToUser() {
		return toUser;
	}

	public void setToUser(int toUser) {
		this.toUser = toUser;
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
