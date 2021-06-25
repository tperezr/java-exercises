package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.corba.se.spi.orbutil.fsm.State;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import modelo.GrupoDeUsuarios;
import modelo.Usuario;

public class GrupoDAO {

	private Connection conexion;
	private UsuarioDAO uDao = new UsuarioDAO();

	public GrupoDAO() {
	}

	public void abrirConexion() {
		conexion = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
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
	// Borrar, todos, por id, actualziar por nombre

	public ArrayList<GrupoDeUsuarios> seleccionarTodosGrupos() throws SQLException, NullPointerException {
		abrirConexion();
		ArrayList<GrupoDeUsuarios> grupos = new ArrayList<GrupoDeUsuarios>();
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		GrupoDeUsuarios grupo = null;
		try {
			String sql = "SELECT * FROM grupo";
			Statement stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int idGrupo = rs.getInt(1);
				String nombreGrupo = rs.getString(2);
				usuarios = uDao.usuariosPorGrupo(idGrupo);
				grupo = new GrupoDeUsuarios(idGrupo, nombreGrupo, usuarios);
				grupos.add(grupo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		return grupos;
	}

	public String nombresGruposDisponibles(int idBuscar) {		 // NUEVO
		String nombre = "";
		abrirConexion();
		try {
			String sql = "SELECT grupo.nombre_grupo FROM grupo WHERE id_grupo = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idBuscar);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				nombre = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		return nombre;
	}

	public GrupoDeUsuarios seleccionarGrupoPorId(int idBusqueda) {
		abrirConexion();
		GrupoDeUsuarios grupo = null;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			String sql = "SELECT * FROM grupo WHERE id_grupo = (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idBusqueda);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int idGrupo = rs.getInt(1);
				String nombreGrupo = rs.getString(2);
				usuarios = uDao.usuariosPorGrupo(idGrupo);
				grupo = new GrupoDeUsuarios(idGrupo, nombreGrupo, usuarios);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		return grupo;
	}

	public GrupoDeUsuarios seleccionarGrupoPorNombre(String nombreBusqueda) {
		abrirConexion();
		GrupoDeUsuarios grupo = null;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			String sql = "SELECT * FROM grupo WHERE nombre_grupo LIKE ?";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setString(1, nombreBusqueda);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int idGrupo = rs.getInt(1);
				String nombreGrupo = rs.getString(2);
				usuarios = uDao.usuariosPorGrupo(idGrupo);
				grupo = new GrupoDeUsuarios(idGrupo, nombreGrupo, usuarios);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		return grupo;
	}

	public void crearGrupo(String nombre) {
		abrirConexion();
		try {
			String sql = "INSERT INTO grupo VALUES (null,(?))";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setString(1, nombre);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
	}

	public void eliminarGrupoPorNombre(String nombre) {
		abrirConexion();
		try {
			String sql = "DELETE FROM grupo WHERE nombre_grupo LIKE (?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setString(1, nombre);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {

		}
		cerrarConexion();
	}

	public ArrayList<String> gruposPorUsuario(int idUsuario) {
		abrirConexion();
		ArrayList<String> grupos = new ArrayList<String>();
		try {
			String sql = "SELECT g.nombre_grupo FROM grupo AS g "
					+ "INNER JOIN usuario_grupo AS ug ON ug.id_grupo=g.id_grupo "
					+ "INNER JOIN usuario AS u ON u.id_usuario = ug.id_usuario " + "WHERE u.id_usuario=(?)";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idUsuario);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				grupos.add(rs.getString(1));
			}
		} catch (SQLException e) {

		}
		return grupos;
	}

	public ArrayList<String> gruposDisponiblesDelUsuario(int idUser) {
		abrirConexion();
		ArrayList<String> gruposDisponibles = new ArrayList<String>();
		try {
			String sql = "SELECT grupo.nombre_grupo FROM grupo WHERE NOT EXISTS (SELECT usuario_grupo.id_grupo FROM usuario_grupo WHERE grupo.id_grupo = usuario_grupo.id_grupo AND usuario_grupo.id_usuario = (?))";
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			pstmt.setInt(1, idUser);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				gruposDisponibles.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cerrarConexion();
		return gruposDisponibles;
	}

}
