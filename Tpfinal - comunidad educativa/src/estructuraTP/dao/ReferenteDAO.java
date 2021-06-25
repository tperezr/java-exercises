package estructuraTP.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import estructuraTP.modelo.Problematica;
import estructuraTP.modelo.Referente;
import estructuraTP.modelo.Tematica;

public class ReferenteDAO {

	public Referente searchId(int id) {
		TematicaDAO dao = new TematicaDAO();
		Referente referente = new Referente();
		ArrayList<Tematica> tematicas;
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM referentes WHERE idReferente = ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				tematicas = dao.tematicas(rs.getInt("idReferente"));
				referente = new Referente(rs.getInt("idReferente"), rs.getString("nombre"), rs.getString("apellido"),
						rs.getString("rol"), tematicas);

			}

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

		return referente;
	}

	public void conectar() {
		Connection conn = null;
		Statement st;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			conn = DriverManager.getConnection(url, "root", "admin");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// DAR DE ALTA

	public void guardar(Referente c) {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			conn = DriverManager.getConnection(url, "root", "admin");

			String instruccion = "INSERT INTO referentes (nombre,apellido,rol) VALUES (?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(instruccion);
			pstmt.setString(1, c.getNombre());
			pstmt.setString(2, c.getApellido());
			pstmt.setString(3, c.getRol());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// DAR DE BAJA

	public void eliminar(int id) {

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String delete = "DELETE FROM referentes WHERE id = (?)";

			PreparedStatement pstmt = connection.prepareStatement(delete);
			pstmt.setInt(1, id);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}

	}

	// CAMBIAR DATOS DEL REFERENTE

	// TRAER TODOS LOS REFERENTES
	public ArrayList<Referente> obtenerTodas() {
		TematicaDAO dao = new TematicaDAO();
		ArrayList<Tematica> tematicas;
		ArrayList<Referente> referentes = new ArrayList<Referente>();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			conn = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM referentes";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				tematicas = dao.tematicas(rs.getInt("idReferente"));
				referentes.add( new Referente(rs.getInt("idReferente"), rs.getString("nombre"), rs.getString("apellido"),
						rs.getString("rol"), tematicas));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return referentes;

	}

	public ArrayList<Referente> obtenerTodasPorTematica(String nombre) {
		TematicaDAO dao = new TematicaDAO();
		ArrayList<Tematica> tematicas;
		ArrayList<Referente> referentes = new ArrayList<Referente>();
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			conn = DriverManager.getConnection(url, "root", "admin");

			// TODO Editar consulta según nombres actuales de la tabla.
			String sql = "SELECT * FROM referentes INNER JOIN nombrelargo ON referentes.idReferente = nombrelargo.idReferente WHERE nombrelargo.idtematica IN (SELECT idTematica FROM tematicas WHERE nombre = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, nombre);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				tematicas = dao.tematicas(rs.getInt("idReferente"));
				referentes.add( new Referente(rs.getInt("idReferente"), rs.getString("nombre"), rs.getString("apellido"),
						rs.getString("rol"), tematicas));
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return referentes;
	}

}
