package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Conexion {
	/*
	public static void main(String args[]) {
		

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/chat?user=root&password=");		
			
			String sql= "show tables";
			Statement consulta;
			consulta= con.createStatement();
			ResultSet rs= consulta.executeQuery(sql);
			while (rs.next()) {						
				System.out.println(rs.getString("Tables_in_chat"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("No se encontró la base de datos");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No se encontró la base de datos");
		}
	}
	*/
	public Connection isConnected() throws ClassNotFoundException, SQLException {	
		Class.forName("com.mysql.jdbc.Driver");
		String url2 = "jdbc:mysql://localhost:3306/chatgrupos?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url2, "root", "");
		return con;
	}
}
