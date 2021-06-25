package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.sun.corba.se.spi.orbutil.fsm.State;

import modelo.Mail;

public class MailDAO {

	private Connection conexion;

	public MailDAO() {

	}

	public void abrirConexion() {
		conexion = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// String url = "jdbc:mysql://localhost:3306/chatgrupos";
			String url2 = "jdbc:mysql://localhost:3306/gmail?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			conexion = DriverManager.getConnection(url2, "root", "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cerrarConexion() {
		try {
			if (conexion != null) {
				conexion.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// SELECT all, select by id, update, delete, insert.

	public ArrayList<Mail> mailsRecibidosInboxPorId(int idB, boolean unique) {
		abrirConexion();
		ArrayList<Mail> mailsInbox = new ArrayList<Mail>();
		Mail mail = null;
		try {
			String sql = "SELECT * FROM inbox WHERE id_user = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idB);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id_mail = rs.getInt(1);
				String asunto = rs.getString(2);
				String cuerpo = rs.getString(3);
				String fecha = rs.getString(4);
				String from_user = rs.getString(5); //idemisor
				int id_user = rs.getInt(6);
				mail = new Mail(id_mail, from_user, id_user, fecha, asunto, cuerpo);
				mailsInbox.add(mail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(unique) {
			cerrarConexion();
		}		
		return mailsInbox;
	}

	public ArrayList<Mail> mailsEnviadosOutboxPorId(int idB, boolean unique) {
		abrirConexion();
		ArrayList<Mail> mailsOutbox = new ArrayList<Mail>();
		try {
			String sql = "SELECT * FROM outbox WHERE id_user = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idB);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id_mail = rs.getInt(1);
				String asunto = rs.getString(2);
				String cuerpo = rs.getString(3);
				String fecha = rs.getString(4);
				String to_user = rs.getString(5);
				int id_user = rs.getInt(6);
				mailsOutbox.add(new Mail(id_mail, to_user, id_user, fecha, asunto, cuerpo));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(unique) {
			cerrarConexion();
		}
		return mailsOutbox;
	}

	public void crearNuevoMail(String nombreEmisor, String nombreReceptor, LocalDateTime fecha, String asunto, String cuerpoMail) {
		abrirConexion();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		UsuarioDAO uDao = new UsuarioDAO();
		String fechaFormat = dtf.format(fecha);
		int idEmisor = uDao.usuarioPorEmail(nombreEmisor, false).getIdUsuario();
		int idReceptor = uDao.usuarioPorEmail(nombreReceptor, false).getIdUsuario();
		try {
			String sql = "INSERT INTO `outbox`(`asunto`, `cuerpo`, `fecha`, `id_receptor`, `id_user`) VALUES ((?),(?),(?),(?),(?))";
			String sql2 = "INSERT INTO `inbox`(`asunto`, `cuerpo`, `fecha`, `id_emisor`, `id_user`) VALUES ((?),(?),(?),(?),(?))";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			PreparedStatement pstmt2 = conexion.prepareStatement(sql2);
			pstmt.setString(1, asunto);
			pstmt.setString(2, cuerpoMail);
			pstmt.setString(3, fechaFormat);
			pstmt.setString(4, nombreReceptor);
			pstmt.setInt(5, idEmisor);

			pstmt2.setString(1, asunto);
			pstmt2.setString(2, cuerpoMail);
			pstmt2.setString(3, fechaFormat);
			pstmt2.setString(4, nombreEmisor);
			pstmt2.setInt(5, idReceptor);
			pstmt.executeUpdate();
			pstmt2.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}

	public void eliminarMailOutboxPorId(int idMail) { // outbox
		abrirConexion();
		try {
			String sql = "DELETE FROM `outbox` WHERE id_mail = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idMail);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}

	public void eliminarMailInboxPorId(int idMail) { // INBOX
		abrirConexion();
		try {
			String sql = "DELETE FROM `inbox` WHERE id_mail = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idMail);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}

}
