package estructuraTP.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.Date;

import estructuraTP.modelo.Propuestas;
import estructuraTP.modelo.Tematica;

public class PropuestaDAO {
	
	public void eliminar(int id) {

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String delete = "DELETE FROM propuestas WHERE idPropuesta = (?)";

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

	
	public ArrayList<Propuestas> obtenerTodos() {
		TematicaDAO dao = new TematicaDAO();
		
		ArrayList<Propuestas> propuestas = new ArrayList<Propuestas>();
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM propuestas";

			Statement stmt = connection.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Propuestas p = new Propuestas(rs.getDate("fechaPublicacion").toLocalDate(),rs.getDate("fechaCaducidad").toLocalDate());
				
				Tematica tematica = dao.searchId(rs.getInt("tematicas_idTematica"));
				propuestas.add(new Propuestas(
						rs.getInt("idPropuesta"), 
						rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getString("titulo"),
						rs.getDate("fechaPublicacion").toLocalDate(),
						rs.getString("detalle"),
						p.caducar(),
						rs.getDate("fechaCaducidad").toLocalDate(),
						tematica));
						
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

		return propuestas;
	}
	
	
	public ArrayList<Propuestas> searchPorTematica(String palabra) {
		TematicaDAO dao = new TematicaDAO();
		
		ArrayList<Propuestas> propuestas = new ArrayList<Propuestas>();
		
		palabra += "%";
		
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM propuestas WHERE tematicas_idTematica IN (SELECT idTematica FROM tematicas WHERE nombre LIKE ?)";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, palabra);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Date d1 = rs.getDate("fechaPublicacion");
			    Date d2 = rs.getDate("fechaCaducidad");
			    
			    Long dif = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
				
			    boolean aux = false;
			    if(dif > 45) {
			    	aux = true;
			    }
			    
				Tematica tematica = dao.searchId(rs.getInt("tematicas_idTematica"));
				propuestas.add(new Propuestas(
						rs.getInt("idPropuesta"), 
						rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getString("titulo"),
						rs.getDate("fechaPublicacion").toLocalDate(),
						rs.getString("detalle"),
						aux,
						rs.getDate("fechaCaducidad").toLocalDate(),
						tematica));
						
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

		return propuestas;
	}
	
	
	public ArrayList<Propuestas> searchPorFecha(Date f1, Date f2) {
		TematicaDAO dao = new TematicaDAO();
		
		ArrayList<Propuestas> propuestas = new ArrayList<Propuestas>();
		
		Connection connection = null;
		
		if(f1.after(f2)) {
			Date aux = f1;
			f1 = f2;
			f2 = aux;
		}	
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM propuestas WHERE fechaPublicacion BETWEEN ? AND ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDate(1, f1);
			stmt.setDate(2, f2);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Date d1 = rs.getDate("fechaPublicacion");
			    Date d2 = rs.getDate("fechaCaducidad");
			    
			    Long dif = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
				
			    boolean aux = false;
			    if(dif > 45) {
			    	aux = true;
			    }
			    
				Tematica tematica = dao.searchId(rs.getInt("tematicas_idTematica"));
				propuestas.add(new Propuestas(
						rs.getInt("idPropuesta"), 
						rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getString("titulo"),
						rs.getDate("fechaPublicacion").toLocalDate(),
						rs.getString("detalle"),
						aux,
						rs.getDate("fechaCaducidad").toLocalDate(),
						tematica));
						
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

		return propuestas;
	}
	
	
	public void guardar(Propuestas p) {

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String instruccion = "INSERT INTO propuestas (nombre,apellido,titulo,fechaPublicacion,detalle,fechaCaducidad,tematicas_idTematica)"
					+ " VALUES (?,?,?,?,?,?,?)";

			PreparedStatement pstmt = connection.prepareStatement(instruccion);
			pstmt.setString(1, p.getPersonaQuePosteoNombre());
			pstmt.setString(2, p.getPersonaQuePosteoApellido());
			pstmt.setString(3, p.getTitulo());
			pstmt.setString(4, p.getFechaPublicacion().toString());
			pstmt.setString(5, p.getDetalle());
			pstmt.setString(6,p.getFechaCaducidad().toString());
			pstmt.setInt(7,p.getTematica().getId());			
			
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
	
	
	
	
	public Propuestas busquedaUnaPropuesta(int id) {
		TematicaDAO dao = new TematicaDAO();
		
		Propuestas propuesta = new Propuestas();
		
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String sql = "SELECT * FROM propuestas WHERE idPropuesta = ?";

			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Date d1 = rs.getDate("fechaPublicacion");
			    Date d2 = rs.getDate("fechaCaducidad");
			    
			    Long dif = (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
				
			    boolean aux = false;
			    if(dif > 45) {
			    	aux = true;
			    }
			    
				Tematica tematica = dao.searchId(rs.getInt("tematicas_idTematica"));
				propuesta = new Propuestas(
						rs.getInt("idPropuesta"), 
						rs.getString("nombre"),
						rs.getString("apellido"),
						rs.getString("titulo"),
						rs.getDate("fechaPublicacion").toLocalDate(),
						rs.getString("detalle"),
						aux,
						rs.getDate("fechaCaducidad").toLocalDate(),
						tematica);
						
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

		return propuesta;
	}
	
	
	public void actualizar(Propuestas p) {

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/tpforo";
			connection = DriverManager.getConnection(url, "root", "admin");

			String update = "UPDATE propuestas SET nombre = ?,apellido = ?,titulo = ?,detalle = ?, tematicas_idTematica = ? WHERE idPropuesta = (?)";

			PreparedStatement pstmt = connection.prepareStatement(update);
			pstmt.setString(1, p.getPersonaQuePosteoNombre());
			pstmt.setString(2, p.getPersonaQuePosteoApellido());
			pstmt.setString(3, p.getTitulo());
			pstmt.setString(4, p.getDetalle());
			pstmt.setInt(5, p.getTematica().getId());
			pstmt.setInt(6, p.getId());

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
}
