package process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Process_Author {
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
	
	public ArrayList<Author> getListAuthors() {
		Connection con = getCon();
		String sql = "select * from authors";
		ArrayList<Author> lsa= new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Author a = new Author();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setAge(rs.getInt("age"));
				lsa.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return lsa;
	}
	
	public ArrayList<Author> getListAuthorsByName(String name) {
		Connection con = getCon();
		String sql = "select * from authors where name like ?";
		ArrayList<Author> lsa= new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Author a = new Author();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setAge(rs.getInt("age"));
				lsa.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return lsa;
	}
	
	public boolean insertAuthor(String name, int age) throws Exception {
		Connection con = getCon();
		String sql = "insert into authors(name, age) values(?, ?)";
				
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.executeUpdate();
			return true;
		}
		catch (SQLException e) {
	        if (e.getErrorCode() == 1062) 
	            throw new SQLException("Author with name " + name + " already exists.");
	        else 
	            throw e;
		}
	    catch(Exception ex) {
	    	throw ex;
	    } 
		finally { 
	        if(con != null) {
	            try {
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	public boolean deleteAuthor(int id) {
		Connection con = getCon();
		String sql = "delete from authors where id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			return true;
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
	}
	
	public boolean updateAuthor(int id, String name, int age) throws Exception {
		Connection con = getCon();
		String sql = "update authors set name=?, age=? where id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, age);
			ps.setInt(3, id);
			ps.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
	        if (e.getErrorCode() == 1062) 
	            throw new SQLException("Author with name " + name + " already exists.");
	        else 
	            throw e;
		}
	    catch(Exception ex) {
	    	throw ex;
	    } 
		finally { 
	        if(con != null) {
	            try {
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	public Author getAuthorByName(String name) {
		Connection con = getCon();
		String sql = "select * from authors where name=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Author a = new Author();
				a.setId(rs.getInt("id"));
				a.setName(rs.getString("name"));
				a.setAge(rs.getInt("age"));
				return a;
			}
		} catch (SQLException e) {
			return null;
		} finally { 
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
}
