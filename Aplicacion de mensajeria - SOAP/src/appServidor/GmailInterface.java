package appServidor;

import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

import modelo.Mail;

@WebService
public interface GmailInterface {
	
	@WebMethod public int iniciarSesion(String email, String contraseña);
	@WebMethod public int registrarse(String nombre, String apellido, String email, String contraseña);
	@WebMethod public ArrayList<Mail> solicitarMailsInbox(String email);
	@WebMethod public int enviarMail(String asunto, String cuerpo, String emailDestinatario, String emailEmisor);
	@WebMethod public ArrayList<Mail> solicitarMailsOutbox(String email);
	@WebMethod public int borrarMailInbox(int idMail, String email);
	@WebMethod public int borrarMailOutbox(int idMail, String email);
	@WebMethod public void desconexion(String email);
	
}
