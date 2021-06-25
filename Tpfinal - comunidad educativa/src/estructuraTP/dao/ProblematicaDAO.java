package estructuraTP.dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.Date;

import estructuraTP.modelo.Problematica;
import estructuraTP.modelo.Referente;
import estructuraTP.modelo.Tematica;


public class ProblematicaDAO {
	
	public void eliminar(int id) {

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String delete = "DELETE FROM problematica WHERE idProblematica = (?)";

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
	
	public ArrayList<Problematica> obtenerTodos() {
		TematicaDAO tdao = new TematicaDAO();
		ReferenteDAO rdao = new ReferenteDAO();
		
		ArrayList<Problematica> problematicas = new ArrayList<Problematica>();
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM problematica";

			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Tematica tematica = tdao.searchId(rs.getInt("tematicas_idTematica"));
				Referente referente = rdao.searchId(rs.getInt("referentes_idReferente"));
				problematicas.add(new Problematica(
						rs.getInt("idProblematica"), 
						rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getString("titulo"),
						rs.getDate("fechaPublicacion").toLocalDate(),
						rs.getString("detalle"),
						rs.getBoolean("fuePlanteada"),
						rs.getBoolean("cerrada"),
						rs.getString("observacion"),
						tematica,
						referente));
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

		return problematicas;
	}
	
	public void cerrar(int id) {

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String update = "UPDATE problematica SET cerrada = 0 WHERE idProblematica = ?";

			PreparedStatement pstmt = connection.prepareStatement(update);
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
	
	public ArrayList<Problematica> searchPorTematica(String palabra) {
		TematicaDAO tdao = new TematicaDAO();
		ReferenteDAO rdao = new ReferenteDAO();
		
		ArrayList<Problematica> problematicas = new ArrayList<Problematica>();
		
		palabra += "%";
		
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM problematica WHERE tematicas_idTematica IN (SELECT idTematica FROM tematicas WHERE nombre LIKE ?)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1,palabra);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Tematica tematica = tdao.searchId(rs.getInt("tematicas_idTematica"));
				Referente referente = rdao.searchId(rs.getInt("referentes_idReferente"));
				problematicas.add(new Problematica(
						rs.getInt("idProblematica"), 
						rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getString("titulo"),
						rs.getDate("fechaPublicacion").toLocalDate(),
						rs.getString("detalle"),
						rs.getBoolean("fuePlanteada"),
						rs.getBoolean("cerrada"),
						rs.getString("observacion"),
						tematica,
						referente));
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

		return problematicas;
	}
	
	public ArrayList<Problematica> searchPorFecha(Date f1, Date f2) {
		TematicaDAO tdao = new TematicaDAO();
		ReferenteDAO rdao = new ReferenteDAO();
		ArrayList<Problematica> problematicas = new ArrayList<Problematica>();	
		
		if(f1.after(f2)) {
			Date aux = f1;
			f1 = f2;
			f2 = aux;
		}	
				
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM problematica WHERE fechaPublicacion BETWEEN ? AND ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDate(1, f1);
			stmt.setDate(2, f2);
			

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Tematica tematica = tdao.searchId(rs.getInt("tematicas_idTematica"));
				Referente referente = rdao.searchId(rs.getInt("referentes_idReferente"));
				problematicas.add(new Problematica(
						rs.getInt("idProblematica"), 
						rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getString("titulo"),
						rs.getDate("fechaPublicacion").toLocalDate(),
						rs.getString("detalle"),
						rs.getBoolean("fuePlanteada"),
						rs.getBoolean("cerrada"),
						rs.getString("observacion"),
						tematica,
						referente));
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

		return problematicas;
	}
	
	
	public void guardar(Problematica p) {
		
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String instruccion = "INSERT INTO problematica (nombre,apellido,titulo,fechaPublicacion,detalle,fuePlanteada,cerrada,observacion,tematicas_idTematica,referentes_idReferente)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement pstmt = connection.prepareStatement(instruccion);
			pstmt.setString(1, p.getPersonaQuePosteoNombre());
			pstmt.setString(2, p.getPersonaQuePosteoApellido());
			pstmt.setString(3, p.getTitulo());
			pstmt.setString(4, p.getFechaPublicacion().toString());
			pstmt.setString(5, p.getDetalle());
			pstmt.setInt(6 , (p.getFuePlanteada()) == true ? 1 : 0);
			pstmt.setInt(7, (p.getCerrada()) == true ? 1 : 0);
			pstmt.setString(8, "");
			pstmt.setInt(9,p.getTematica().getId());	
			pstmt.setInt(10, p.getReferente().getId());
			
			
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
	
	
	public void actualizar(int id, int idReferente, String descripcion) {

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String update = "UPDATE problematica SET observacion = (?) WHERE idProblematica = (?)";

			PreparedStatement pstmt = connection.prepareStatement(update);
			pstmt.setString(1, descripcion);
			pstmt.setInt(2, id);

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
	
	public Problematica busquedaUnaProblematica(int id) {
		TematicaDAO tdao = new TematicaDAO();
		ReferenteDAO rdao = new ReferenteDAO();
		
		Problematica problematica = new Problematica();
		
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM problematica WHERE idProblematica = ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Tematica tematica = tdao.searchId(rs.getInt("tematicas_idTematica"));
				Referente referente = rdao.searchId(rs.getInt("referentes_idReferente"));
				problematica = new Problematica(
						rs.getInt("idProblematica"), 
						rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getString("titulo"),
						rs.getDate("fechaPublicacion").toLocalDate(),
						rs.getString("detalle"),
						rs.getBoolean("fuePlanteada"),
						rs.getBoolean("cerrada"),
						rs.getString("observacion"),
						tematica,
						referente);
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

		return problematica;
	}
	
}
