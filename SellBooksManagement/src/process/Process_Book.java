package process;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Process_Book {
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
	
	public ArrayList<Book> getListBook() {
		Connection con = getCon();
		String sql = "select * from viewBooks";
		ArrayList<Book> lsbook = new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author_name"));
				b.setCategory(rs.getString("category_name"));
				b.setPublisher(rs.getString("publisher_name"));
				b.setImportDay(rs.getDate("import_day"));
				b.setPrice(rs.getDouble("price"));
				b.setQuantity(rs.getInt("quantity"));
				lsbook.add(b);
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
		return lsbook;
	}
	
	public ArrayList<Book> getAllBookStatistic() {
		Connection con = getCon();
		String sql = "select * from viewBooks";
		ArrayList<Book> lsbook = new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author_name"));
				b.setCategory(rs.getString("category_name"));
				b.setPublisher(rs.getString("publisher_name"));
				b.setPrice(rs.getDouble("price"));
				b.setBookSold(rs.getInt("book_sold"));
				b.setQuantity(rs.getInt("quantity"));
				lsbook.add(b);
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
		return lsbook;
	}
	
	public ArrayList<Book> getAllBookStatisticByName(String name) {
		Connection con = getCon();
		String sql = "select * from viewBooks where title like ?";
		ArrayList<Book> lsbook = new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author_name"));
				b.setCategory(rs.getString("category_name"));
				b.setPublisher(rs.getString("publisher_name"));
				b.setPrice(rs.getDouble("price"));
				b.setBookSold(rs.getInt("book_sold"));
				b.setQuantity(rs.getInt("quantity"));
				lsbook.add(b);
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
		return lsbook;
	}
	
	public ArrayList<Book> getListBooksBy(String title, String author, String category) {
		Connection con = getCon();
		String sql = "select * from viewBooks where title like ? and author_name like ? and category_name like ?";
		ArrayList<Book> lsbook = new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, "%"+title+"%");
			ps.setString(2, "%"+author+"%");
			ps.setString(3, "%"+category+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author_name"));
				b.setCategory(rs.getString("category_name"));
				b.setPublisher(rs.getString("publisher_name"));
				b.setImportDay(rs.getDate("import_day"));
				b.setPrice(rs.getDouble("price"));
				b.setQuantity(rs.getInt("quantity"));
				lsbook.add(b);
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
		return lsbook;
	}
	
	public boolean insertBook(String title, int author_id, int cate_id, int publisher_id, Date importDay, double price, int quantity) throws Exception {
		Connection con = getCon();
		String sql = "insert into books(title, author_id, category_id, publisher_id, import_day, "
				+ "price, quantity) values(?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, title);
			ps.setInt(2, author_id);
			ps.setInt(3, cate_id);
			ps.setInt(4, publisher_id);
			ps.setDate(5, importDay);
			ps.setDouble(6, price);
			ps.setInt(7, quantity);
			ps.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
	        if (e.getErrorCode() == 1062) 
	            throw new SQLException("Book with title " + title + " already exists.");
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
	
	public boolean updateBook(int id, String title, int author_id, int cate_id, int publisher_id, Date importDay, double price, int quantity) throws Exception {
		Connection con = getCon();
		String sql = "update books set title=?, author_id=?, category_id=?, publisher_id=?, import_day=?, price=?, quantity=? where id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, title);
			ps.setInt(2, author_id);
			ps.setInt(3, cate_id);
			ps.setInt(4, publisher_id);
			ps.setDate(5, importDay);
			ps.setDouble(6, price);
			ps.setInt(7, quantity);
			ps.setInt(8, id);
			ps.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
	        if (e.getErrorCode() == 1062) 
	            throw new SQLException("Book with title " + title + " already exists.");
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
	
	public boolean deleteBook(int id) {
		Connection con = getCon();
		String sql = "delete from books where id=?";
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
	
	public Book getBookById(int id) {
		Connection con = getCon();
		String sql = "select * from viewBooks where id=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author_name"));
				b.setCategory(rs.getString("category_name"));
				b.setPublisher(rs.getString("publisher_name"));
				b.setImportDay(rs.getDate("import_day"));
				b.setPrice(rs.getDouble("price"));
				b.setQuantity(rs.getInt("quantity"));
				con.close();
				return b;
			}
		} catch (SQLException e) {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		} finally{ 
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
	
	public Book getBookByName(String title) {
		Connection con = getCon();
		String sql = "select * from viewBooks where title=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, title);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Book b = new Book();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor(rs.getString("author_name"));
				b.setCategory(rs.getString("category_name"));
				b.setPublisher(rs.getString("publisher_name"));
				b.setImportDay(rs.getDate("import_day"));
				b.setPrice(rs.getDouble("price"));
				b.setQuantity(rs.getInt("quantity"));
				con.close();
				return b;
			}
		} catch (SQLException e) {
			try {
				con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		} finally{ 
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return null;
	}
	
	public String getTotalDay(Date d) {
		Connection con = getCon();
		String sql = "select * from viewBooksStatistic where book_sold = (SELECT MAX(book_sold) FROM viewBooksStatistic) and Date like ? order by price desc limit 1";
		String name = "Null";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setDate(1, d);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				name = rs.getString("title");
				return name;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return name;
	}

	public String getTotalMonth(int m, int y) {
		Connection con = getCon();
		String sql = "select * from viewBooksStatistic where book_sold = (SELECT MAX(book_sold) FROM viewBooksStatistic) and (Date like ? or Date like ?) order by price desc limit 1";
		String name = "Null";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, y + "-" + m + "-%");
			ps.setString(2, y + "-0" + m + "%");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				name = rs.getString("title");
				return name;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return name;
	}
	
	public String getTotalYear(int y) {
		Connection con = getCon();
		String sql = "select * from viewBooksStatistic where book_sold = (SELECT MAX(book_sold) FROM viewBooksStatistic) and Date like ? order by price desc limit 1";
		String name = "Null";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, y+"%");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				name = rs.getString("title");
				return name;
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
		return name;
	}
}
