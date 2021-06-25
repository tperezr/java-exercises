package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.org.apache.regexp.internal.recompile;

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
			String url2 = "jdbc:mysql://localhost:3306/chatgrupos?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
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

	public Usuario usuarioPorId(int idBusqueda) { // anda
		abrirConexion();

		Usuario user = null;
		try {
			String seleccionar = "SELECT * FROM usuario WHERE id_usuario = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(seleccionar);
			pstmt.setInt(1, idBusqueda);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int idUser = rs.getInt(1);
				String aliasUser = rs.getString(2);
				String nombreUser = rs.getString(3);
				String contraseña = rs.getString(4);
				user = new Usuario(idUser, aliasUser, nombreUser, contraseña);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		return user;

	}

	public ArrayList<Usuario> seleccionarTodosUsuarios() { // anda
		abrirConexion();
		ArrayList<Usuario> users = new ArrayList<Usuario>();
		try {
			String seleccionar = "SELECT * FROM usuario";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(seleccionar);

			while (rs.next()) {
				int idUser = rs.getInt(1);
				String aliasUser = rs.getString(2);
				String nombreUser = rs.getString(3);
				String contraseña = rs.getString(4);
				Usuario user = new Usuario(idUser, aliasUser, nombreUser, contraseña);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		return users;
	}

	public void borrarUsuarioPorId(int idBorrar) { // anda
		abrirConexion();
		try {
			String sql = "DELETE FROM usuario WHERE id_usuario = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idBorrar);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}

	// Borrar, todos, por id, actualziar por nombre

	public void actualizarUsuarioPorUsername(String nombreBuscar, String nuevoAlias, String nuevoUsername,
			String nuevaContraseña) {
		abrirConexion();
		try {
			// String x = "%%"; despues

			String sql = "UPDATE `usuario` SET `alias_usuario`= (?),`nombre_usuario`= (?),`contraseña`= (?) WHERE nombre_usuario LIKE ?";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setString(1, nuevoAlias);
			pstmt.setString(2, nuevoUsername);
			pstmt.setString(3, nuevaContraseña);
			pstmt.setString(4, nombreBuscar);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}

	public Usuario usuarioPorNombre(String nombreUsuario) { // anda
		abrirConexion();

		Usuario user = null;
		try {
			String seleccionar = "SELECT * FROM usuario WHERE nombre_usuario = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(seleccionar);
			pstmt.setString(1, nombreUsuario);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int idUser = rs.getInt(1);
				String aliasUser = rs.getString(2);
				String nombreUser = rs.getString(3);
				String contraseña = rs.getString(4);
				user = new Usuario(idUser, aliasUser, nombreUser, contraseña);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		return user;

	}

	public void registrarUsuario(String nombre, String alias, String contraseña) {
		abrirConexion();
		try {
			String sql = "INSERT INTO usuario VALUES (null,(?),(?),(?))";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setString(1, alias);
			pstmt.setString(2, nombre);
			pstmt.setString(3, contraseña);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}

	public ArrayList<Usuario> usuariosPorGrupo(int idGrupo) {
		abrirConexion();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			String sql = "SELECT u.* FROM usuario AS u INNER JOIN usuario_grupo AS ug ON ug.id_usuario = u.id_usuario "
					+ "INNER JOIN grupo AS g ON g.id_grupo = ug.id_grupo WHERE g.id_grupo=(?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idGrupo);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				usuarios.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		} catch(SQLException e) {
			
		}
		cerrarConexion();
		return usuarios;
	}
	
	public void asignarUsuarioAlGrupo(int idUsuario, int idGrupo) {
		abrirConexion();
		try {
			String sql = "INSERT INTO usuario_grupo VALUES (?,?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idUsuario);
			pstmt.setInt(2, idGrupo);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}
	
	public void eliminarUsuarioDelGrupo(int idUsuario, int idGrupo) {
		abrirConexion();
		try {
			String sql = "DELETE FROM usuario_grupo WHERE id_usuario=(?) AND id_grupo=(?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idUsuario);
			pstmt.setInt(2, idGrupo);
			pstmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}
}
