package process;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Process_Customer {
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
	
	public ArrayList<Customer> getListCustomer() {
		Connection con = getCon();
		String sql = "select * from customers";
		ArrayList<Customer> lscus= new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Customer cus = new Customer();
				cus.setId(rs.getInt("id"));
				cus.setName(rs.getString("name"));
				cus.setPhone(rs.getString("phone"));
				cus.setEmail(rs.getString("email"));
				cus.setAddress(rs.getString("address"));
				cus.setBirth(rs.getDate("birth"));
				cus.setBookPurchased(rs.getInt("books_purchased"));
				cus.setRank(rs.getString("rank"));
				lscus.add(cus);
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
		return lscus;
	}
	
	public ArrayList<Customer> getListCustomerByname(String name) {
		Connection con = getCon();
		String sql = "select * from customers where name like ?";
		ArrayList<Customer> lscus= new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Customer cus = new Customer();
				cus.setId(rs.getInt("id"));
				cus.setName(rs.getString("name"));
				cus.setPhone(rs.getString("phone"));
				cus.setEmail(rs.getString("email"));
				cus.setAddress(rs.getString("address"));
				cus.setBirth(rs.getDate("birth"));
				cus.setBookPurchased(rs.getInt("books_purchased"));
				cus.setRank(rs.getString("rank"));
				lscus.add(cus);
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
		return lscus;
	}
	
	public boolean insertCustomer(String name, String phone, String email, String address, Date birth) throws Exception {
		Connection con = getCon();
		String sql = "insert into customers(name, phone, email, address, birth) values(?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, phone);
			ps.setString(3, email);
			ps.setString(4, address);
			ps.setDate(5, birth);
			ps.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				if (e.getMessage().contains("email")) 
					throw new SQLException("Email " + email + " already exists.");
				if (e.getMessage().contains("phone")) 
					throw new SQLException("Phone " + phone + " already exists.");
				throw e;
			} else {
				throw e;
			}
		}
		catch(Exception ex) {
	    	throw ex;
	    } 
		finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean updateCustomer(int id, String name, String phone, String email, String address, Date birth) throws Exception {
		Connection con = getCon();
		String sql = "update customers set name=?, phone=?, email=?, address=?, birth=? where id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, phone);
			ps.setString(3, email);
			ps.setString(4, address);
			ps.setDate(5, birth);
			ps.setInt(6, id);
			ps.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				if (e.getMessage().contains("email")) 
					throw new SQLException("Email " + email + " already exists.");
				if (e.getMessage().contains("phone")) 
					throw new SQLException("Phone " + phone + " already exists.");
				throw e;
			} else {
				throw e;
			}
		}
		catch(Exception ex) {
	    	throw ex;
	    } 
		finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean deleteCustomer(int id) {
		Connection con = getCon();
		String sql = "delete from customers where id=?";
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
	
	public Customer getCustomerById(int id) {
		Connection con = getCon();
		String sql = "select * from customers where id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Customer ct = new Customer();
				ct.setId(rs.getInt("id"));
				ct.setName(rs.getString("name"));
				ct.setPhone(rs.getString("phone"));
				ct.setEmail(rs.getString("email"));
				ct.setAddress(rs.getString("address"));
				ct.setBirth(rs.getDate("birth"));
				ct.setBookPurchased(rs.getInt("books_purchased"));
				ct.setRank(rs.getString("rank"));
				return ct;
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
