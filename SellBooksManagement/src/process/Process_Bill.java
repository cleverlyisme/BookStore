package process;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Process_Bill {
	private static Connection cn;
	private LocalDate today = LocalDate.now();

	public static Connection getCon() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			cn = DriverManager.getConnection("jdbc:mysql://localhost/db_bookstore", "root", "galaga286");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return cn;
	}

	public ArrayList<Bill> getBillsByDay(Date date) {
		Connection con = getCon();
		String sql = "select * from viewBills where Date=?";
		ArrayList<Bill> lsbill = new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setDate(1, date);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Bill b = new Bill();
				b.setId(rs.getInt("id"));
				b.setName(rs.getString("name"));
				b.setDate(rs.getDate("date"));
				b.setAmount(rs.getInt("amount"));
				b.setDiscount(rs.getInt("discount"));
				b.setTotal(rs.getDouble("total"));
				lsbill.add(b);
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
		return lsbill;
	}

	public ArrayList<Bill> getBillsBy(String y, String m, String d) {
		Connection con = getCon();
		String sql = "select * from viewBills where Date like ? and Date like ? and Date like ?";
		ArrayList<Bill> lsbill = new ArrayList<>();
		try {
			String year = y.equals("") ? "%%" : y+"-%";
			String month = m.equals("") ? "%%" : "%-"+m+"-%";
			String day = d.equals("") ? "%%" : "%-"+d;
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, year);
			ps.setString(2, month);
			ps.setString(3, day);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Bill b = new Bill();
				b.setId(rs.getInt("id"));
				b.setName(rs.getString("name") == null ? "Anonymous" : rs.getString("name"));
				b.setDate(rs.getDate("date"));
				b.setAmount(rs.getInt("amount"));
				b.setDiscount(rs.getInt("discount"));
				b.setTotal(rs.getDouble("total"));
				lsbill.add(b);
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
		return lsbill;
	}

	public ArrayList<Bill> getListBills() {
		Connection con = getCon();
		String sql = "select * from viewBills";
		ArrayList<Bill> lsbill = new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Bill b = new Bill();
				b.setId(rs.getInt("id"));
				b.setName(rs.getString("name") == null ? "Anonymous" : rs.getString("name"));
				b.setDate(rs.getDate("date"));
				b.setAmount(rs.getInt("amount"));
				b.setDiscount(rs.getInt("discount"));
				b.setTotal(rs.getDouble("total"));
				lsbill.add(b);
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
		return lsbill;
	}
	
	public ArrayList<Bill> getListBillsToday(Date date) {
		Connection con = getCon();
		String sql = "select * from viewBills where date = ?";
		ArrayList<Bill> lsbill = new ArrayList<>();
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setDate(1, date);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Bill b = new Bill();
				b.setId(rs.getInt("id"));
				b.setName(rs.getString("name") == null ? "Anonymous" : rs.getString("name"));
				b.setDate(rs.getDate("date"));
				b.setAmount(rs.getInt("amount"));
				b.setDiscount(rs.getInt("discount"));
				b.setTotal(rs.getDouble("total"));
				lsbill.add(b);
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
		return lsbill;
	}

//	public ArrayList<Bill> getListBillsByID(int ID) {
//		Connection con = getCon();
//		String sql = "select * from tbBills where IDBill=?";
//		ArrayList<Bill> lsbill= new ArrayList<>();
//		try {
//			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
//			ps.setInt(1, ID);
//			ResultSet rs = ps.executeQuery();
//			while (rs.next()) {
//				Bill b = new Bill();
//				b.setID(rs.getInt("IDBill"));
//				b.setIDCustomer(rs.getString("IDCustomer"));
//				b.setIDBook(rs.getString("IDBook"));
//				b.setDate(rs.getDate("Date"));
//				b.setAmount(rs.getInt("Amount"));
//				b.setTotal(rs.getDouble("Total"));
//				lsbill.add(b);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally { 
//			if(con != null)
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//		}
//		return lsbill;
//	}
	
//	public Bill getBillByID(int IDBill) {
//		Connection con = getCon();
//		String sql = "select * from tbBills where IDBill=?";
//		try {
//			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
//			ps.setInt(1, IDBill);
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				Bill b = new Bill();
//				b.setID(rs.getInt("IDBill"));
//				b.setIDCustomer(rs.getString("IDCustomer"));
//				b.setIDBook(rs.getString("IDBook"));
//				b.setDate(rs.getDate("Date"));
//				b.setAmount(rs.getInt("Amount"));
//				b.setTotal(rs.getDouble("Total"));
//				return b;
//			}
//		} catch (SQLException e) {
//			return null;
//		} finally { 
//			if(con != null)
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//		}
//		return null;
//	}
	
	public boolean insertBill(Integer customerId, int discount, ArrayList<BillDetail> bd) {
	    Connection con = getCon();
	    String sql = "insert into bills(customer_id, date, discount) values(?, curdate(), ?)";
	    try {
	        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        if (customerId == null) {
	            ps.setNull(1, java.sql.Types.INTEGER);
	        } else {
	            ps.setInt(1, customerId);
	        }
	        ps.setInt(2, discount);
	        ps.executeUpdate();

	        ResultSet rs = ps.getGeneratedKeys();

	        int billId = -1;

	        if (rs.next()) {
	            billId = rs.getInt(1);
	        } else {
	            return false;
	        }

	        sql = "insert into bill_details(bill_id, book_id, quantity) values(?, ?, ?)";
	        ps = con.prepareStatement(sql);

	        for (int i = 0; i < bd.size(); i++) {
	            BillDetail b = bd.get(i);
	            ps.setInt(1, billId);
	            ps.setInt(2, b.getBookId());
	            ps.setInt(3, b.getAmount());
	            ps.executeUpdate();
	        }

	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        if (con != null) {
	            try {
	                con.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}


//	public boolean updateBill(int IDBill, String IDCustomer, String IDBook, Date date, int Amount, double Total) {
//		Connection con = getCon();
//		String sql = "update tbBills set Date=?, Amount=?, Total=? where IDBill=? and IDBook=? and IDCustomer=?";
//		try {
//			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
//			ps.setDate(1, date);
//			ps.setInt(2, Amount);
//			ps.setDouble(3, Total);
//			ps.setInt(4, IDBill);
//			ps.setString(5, IDBook);
//			ps.setString(6, IDCustomer);
//			ps.executeUpdate();
//			return true;
//		} catch (SQLException e) {
//			return false;
//		} finally { 
//			if(con != null)
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//		}
//	}

	public boolean deleteBill(int id) {
		Connection con = getCon();
		String sql = "delete from bills where id=?";
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

	public double getTotalDay(Date d) {
		Connection con = getCon();
		String sql = "select sum(total) from viewBills where Date=?";
		double total = 0;
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setDate(1, d);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				total += rs.getDouble("sum(Total)");
				return total;
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
		return total;
	}

	public double getTotalMonth(int m, int y) {
		Connection con = getCon();
		String sql = "select sum(total) from viewBills where Date like ? or Date like ?";
		double total = 0;
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, y + "-" + m + "-%");
			ps.setString(2, y + "-0" + m + "%");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				total += rs.getDouble("sum(total)");
				return total;
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
		return total;
	}
	
	public double getTotalYear(int y) {
		Connection con = getCon();
		String sql = "select sum(total) from viewBills where Date like ?";
		double total = 0;
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, y+"%");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				total += rs.getDouble("sum(total)");
				return total;
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
		return total;
	}
}
