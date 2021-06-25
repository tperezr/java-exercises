package mtest;

public class Inbox {
	private int idMail;
	private String nombreApellido;
	private String email;
	private String fecha;
	private String asunto;
	private String cuerpo;
	
	public Inbox(int idMail, String nombreApellido, String email, String fecha, String asunto, String cuerpo) {
		this.idMail = idMail;
		this.nombreApellido = nombreApellido;
		this.email = email;
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
	public String getNombreApellido() {
		return nombreApellido;
	}
	public void setNombreApellido(String nombreApellido) {
		this.nombreApellido = nombreApellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
