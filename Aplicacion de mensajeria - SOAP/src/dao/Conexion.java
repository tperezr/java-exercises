package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	public Connection isConnected() throws ClassNotFoundException, SQLException {	
		Class.forName("com.mysql.jdbc.Driver");
		String url2 = "jdbc:mysql://localhost:3306/chatgrupos?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		Connection con = DriverManager.getConnection(url2, "root", "");
		return con;
	}
}
