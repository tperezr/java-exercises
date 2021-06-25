package dao;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import modelo.Mail;
import modelo.Usuario;

public class Pruebas {

	public static void main(String[] args) {
	//	LocalDateTime ldc = LocalDateTime.now();
		// DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
		 //String fecha12 = dtf.format(ldc);
		//LocalDateTime lc = LocalDateTime.parse(dtf.format(ldc));

		UsuarioDAO uDao = new UsuarioDAO();
		MailDAO mailDao = new MailDAO();
		
		//System.out.println(mailDao.buscarIdPorCorreo("fab.sosa"));
		
		
	}

}
