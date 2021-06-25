package appServidor;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.jws.WebService;

import dao.MailDAO;
import dao.UsuarioDAO;
import modelo.Mail;
import modelo.Usuario;

@WebService(endpointInterface = "appServidor.GmailInterface")
public class GmailImpl implements GmailInterface {
	ArrayList<String> usuariosConectados = new ArrayList<String>(); //guardo los emails
	MailDAO mDao = new MailDAO();
	UsuarioDAO uDao = new UsuarioDAO();
	ArrayList<Usuario> usuarios = uDao.todosLosUsuarios();
	
	public int iniciarSesion(String email, String contraseña) {
		for (String uc : usuariosConectados) {
			if(uc.equals(email)) {
				return 1; //usuario conectado
			}
		}
		for (Usuario u : usuarios) {
			if(u.getEmail().equals(email) && u.getContraseña().equals(contraseña)) {
				usuariosConectados.add(email);
				return 3; //conexion exitosa
			}
		}
		return 2; //ocurrio un error
	}

	public int registrarse(String nombre, String apellido, String email, String contraseña) {
		for (Usuario u : usuarios) {
			if(u.getEmail().equals(email)) {
				return 1; // ya existe el email
			} else {
				uDao.crearNuevoUsuario(nombre, apellido, email, contraseña);
				usuarios = uDao.todosLosUsuarios();
				return 2; //registro exitoso
			}
		}
		return 0;
	}

	public ArrayList<Mail> solicitarMailsInbox(String email) {
		ArrayList<Mail> mails = mDao.mailsRecibidosInboxPorId(uDao.usuarioPorEmail(email,false).getIdUsuario(),true);
		return mails;
	}
	
	public ArrayList<Mail> solicitarMailsOutbox(String email) {
		ArrayList<Mail> mails = mDao.mailsEnviadosOutboxPorId(uDao.usuarioPorEmail(email,false).getIdUsuario(),true);
		return mails;
	}

	public int enviarMail(String asunto, String cuerpo, String emailDestinatario, String emailEmisor) {
		for (Usuario u : usuarios) {
			if(u.getEmail().equals(emailDestinatario)) {
				mDao.crearNuevoMail(emailEmisor, emailDestinatario, LocalDateTime.now(), asunto, cuerpo);
				return 1;
			}
		}
		return 0;
	}

	public int borrarMailInbox(int idMail, String email) {
		mDao.eliminarMailInboxPorId(idMail);
		return 0;
	}
	
	public int borrarMailOutbox(int idMail, String email) {
		mDao.eliminarMailOutboxPorId(idMail);
		return 0;
	}

	public void desconexion(String email) {
		usuariosConectados.remove(email);
	}

}
