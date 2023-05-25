package process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Process_User {
	private Connection cn;
	
	public Connection getCon() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cn = DriverManager.getConnection("jdbc:mysql://localhost/db_bookstore", "root", "galaga286");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return cn;
	}
	
	public boolean checkLogin(String username, String password) {
		Connection con = getCon();
		String sql = "select * from users where username=? and password=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) throw new SQLException("Wrong username or password"); 
		} catch (SQLException e) {
			return false;
		} finally { 
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return true;
	}
}
