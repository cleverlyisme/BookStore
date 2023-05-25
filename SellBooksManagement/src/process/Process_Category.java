package process;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Process_Category {
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
	
	public ArrayList<Category> getListCategories() {
		Connection con = getCon();
		String sql = "select * from categories order by id";
		ArrayList<Category> lsc= new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				lsc.add(c);
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
		return lsc;
	}
	
	public ArrayList<Category> getListCategoriesByName(String name) {
		Connection con = getCon();
		String sql = "select * from categories where name like ? order by id";
		ArrayList<Category> lsc= new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				lsc.add(c);
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
		return lsc;
	}
	
	public boolean insertCategory(String name) throws Exception {
		Connection con = getCon();
		String sql = "insert into categories(name) values(?)";
				
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ps.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
	        if (e.getErrorCode() == 1062) 
	            throw new SQLException("Category with name " + name + " already exists.");
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
	
	public boolean deleteCategory(int id) {
		Connection con = getCon();
		String sql = "delete from categories where id=?";
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
	
	public boolean updateCategory(int id, String name) throws Exception{
		Connection con = getCon();
		String sql = "update categories set name=? where id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setInt(2, id);
			ps.executeUpdate();
			return true;
		}
		catch (SQLException e) {
	        if (e.getErrorCode() == 1062) 
	            throw new SQLException("Category with name " + name + " already exists.");
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
	
	public Category getCategoryByName(String name) {
		Connection con = getCon();
		String sql = "select * from categories where name=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				return c;
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
