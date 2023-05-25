package process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Process_Publisher {
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
	
	public ArrayList<Publisher> getListPublishers() {
		Connection con = getCon();
		String sql = "select * from publishers order by id";
		ArrayList<Publisher> lsp= new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Publisher b = new Publisher();
				b.setId(rs.getInt("id"));
				b.setName(rs.getString("name"));
				lsp.add(b);
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
		return lsp;
	}
	
	public ArrayList<Publisher> getPublishersByName(String name) {
		Connection con = getCon();
		String sql = "select * from publishers where name like ? order by id";
		ArrayList<Publisher> lsp= new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Publisher b = new Publisher();
				b.setId(rs.getInt("id"));
				b.setName(rs.getString("name"));
				lsp.add(b);
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
		return lsp;
	}
	
	public boolean insertPublisher(String name) throws Exception {
		Connection con = getCon();
		String sql = "insert into publishers(name) values(?)";
				
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ps.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
	        if (e.getErrorCode() == 1062) 
	            throw new SQLException("Publisher with name " + name + " already exists.");
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
	
	public boolean deletePublisher(int id) {
		Connection con = getCon();
		String sql = "delete from publishers where id=?";
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
	
	public boolean updatePublisher(int id, String name) throws Exception {
		Connection con = getCon();
		String sql = "update publishers set name=? where id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, id);
			ps.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
	        if (e.getErrorCode() == 1062) 
	            throw new SQLException("Publisher with name " + name + " already exists.");
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
	
	public Publisher getPublisherByName(String name) {
		Connection con = getCon();
		String sql = "select * from publishers where name=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Publisher p = new Publisher();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				return p;
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
