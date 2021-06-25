package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modelo.Mail;
import modelo.Usuario;

public class UsuarioDAO {

	private Connection conexion;

	public UsuarioDAO() {
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

	public ArrayList<Usuario> todosLosUsuarios() { // anda
		abrirConexion();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario user = null;
		MailDAO mailDao = new MailDAO();
		try {
			String seleccionar = "SELECT * FROM `usuarios`";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(seleccionar);
			while (rs.next()) {
				int id_user = rs.getInt(1);
				String nombre = rs.getString(2);
				String apellido = rs.getString(3);
				String email = rs.getString(4);
				String contraseña = rs.getString(5);
				ArrayList<Mail> mailsRecibidos = mailDao.mailsRecibidosInboxPorId(id_user,false);
				ArrayList<Mail> mailsEnviados = mailDao.mailsEnviadosOutboxPorId(id_user,false);
				user = new Usuario(id_user, nombre, apellido, email, contraseña, mailsRecibidos, mailsEnviados);
				usuarios.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		return usuarios;
	}

	public Usuario usuarioPorId(int idUser) {
		abrirConexion();
		Usuario user = null;
		MailDAO mailDao = new MailDAO();
		try {
			String sql = "SELECT * FROM usuarios WHERE id_usuario = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idUser);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id_user = rs.getInt(1);
				String nombre = rs.getString(2);
				String apellido = rs.getString(3);
				String email = rs.getString(4);
				String contraseña = rs.getString(5);
				ArrayList<Mail> mailsRecibidos = mailDao.mailsRecibidosInboxPorId(id_user,false);
				ArrayList<Mail> mailsEnviados = mailDao.mailsEnviadosOutboxPorId(id_user,false);
				user = new Usuario(id_user, nombre, apellido, email, contraseña, mailsRecibidos, mailsEnviados);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		return user;
	}

	public void crearNuevoUsuario(String nombre, String apellido, String email, String contra) {
		abrirConexion();
		try {
			String sql = "INSERT INTO `usuarios`(`nombre`, `apellido`, `email`, `contraseña`) VALUES ( (?),(?), (?), (?))";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setString(1, nombre);
			pstmt.setString(2, apellido);
			pstmt.setString(3, email);
			pstmt.setString(4, contra);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}

	public void borrarUsuarioPorId(int idB) {
		abrirConexion();
		try {
			String sql = "DELETE FROM usuarios WHERE id_usuario = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idB);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}

	public Usuario usuarioPorEmail(String _email, boolean unique) {
		abrirConexion();
		Usuario user = null;
		MailDAO mailDao = new MailDAO();
		try {
			String sql = "SELECT * FROM usuarios WHERE email LIKE (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setString(1, _email);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id_user = rs.getInt(1);
				String nombre = rs.getString(2);
				String apellido = rs.getString(3);
				String email = rs.getString(4);
				String contraseña = rs.getString(5);
				ArrayList<Mail> mailsRecibidos = mailDao.mailsRecibidosInboxPorId(id_user,false);
				ArrayList<Mail> mailsEnviados = mailDao.mailsEnviadosOutboxPorId(id_user,false);

				user = new Usuario(id_user, nombre, apellido, email, contraseña, mailsRecibidos, mailsEnviados);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(unique) {
			cerrarConexion();
		}		
		return user;
	}

}
