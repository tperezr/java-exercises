package estructuraTP.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import estructuraTP.modelo.Tematica;

public class TematicaDAO {
	
	public Tematica searchId(int id) {
		Tematica tematica = new Tematica();
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM tematicas WHERE idTematica = ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				tematica = new Tematica(rs.getInt("idTematica"),rs.getString("nombre"));
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

		return tematica;
	}
	
	public Tematica searchNombre(String nombre) {
		Tematica tematica = new Tematica();
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM tematicas WHERE nombre LIKE ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, nombre);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				tematica = new Tematica(rs.getInt("idTematica"),rs.getString("nombre"));
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

		return tematica;
	}
	
	public ArrayList<Tematica> obtenerTodos() {
		ArrayList<Tematica> tematicas = new ArrayList<Tematica>();
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM tematicas";

			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				tematicas.add(new Tematica(rs.getInt("idTematica"), rs.getString("nombre")));

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

		return tematicas;
	}
	
	
	public ArrayList<Tematica> tematicas(int id){
		ArrayList<Tematica> tematica = new ArrayList<Tematica>();
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT t.idTematica,t.nombre FROM tematicas AS t"
					+ " INNER JOIN tematicas_has_referentes AS h ON h.tematicas_idTematica = t.idTematica"
					+ " WHERE h.referentes_idReferente = ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				tematica.add(new Tematica(
						rs.getInt("idTematica"),
						rs.getString("nombre")));
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

		return tematica;
	}
}
