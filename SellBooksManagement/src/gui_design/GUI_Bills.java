package gui_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.security.auth.login.AppConfigurationEntry;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import process.Customer;
import process.Process_Author;
import process.Process_Customer;
import process.Book;
import process.Process_Book;
import process.Author;
import process.Bill;
import process.Process_Bill;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;

import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GUI_Bills extends JFrame {

	private JPanel contentPane;
	private ImageIconHelper imgHelp = new ImageIconHelper();
	private JTable tbBills;
	private JLabel lbCheckSearch;
	private JFormattedTextField txtDateSearch;
	private JRadioButton radioBtnDay;
	private JRadioButton radioBtnMonth;
	private JRadioButton radioBtnYear;
	
	private MaskFormatter mf = null;
	private LocalDate today = LocalDate.now();

	private DefaultTableModel dtm = new DefaultTableModel();
	private Vector tbHeader = new Vector();
	private Vector tbContent = new Vector();
	private Process_Bill pb = new Process_Bill();
	private Process_Book pbook = new Process_Book();
	private Process_Customer pc = new Process_Customer();
	private Bill billChoose = new Bill();
	private ArrayList<Bill> lsbill = new ArrayList<>();
	
	private static GUI_Bills frame = new GUI_Bills();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Login l = new GUI_Login();
					l.setLocationRelativeTo(null); 
					l.setVisible(true);
//					frame.setLocationRelativeTo(null); 
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void showErrorMessage(String message, String err) {
	    JOptionPane.showMessageDialog(this, message, err, JOptionPane.ERROR_MESSAGE);
	}
	
	public void showSuccessMessage(String message, String suc) {
	    JOptionPane.showMessageDialog(this, message, suc, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void checkDate(String date) throws Exception {
		int y = Integer.parseInt(date.split("-")[0]);
		int m = Integer.parseInt(date.split("-")[1]);
		int d = Integer.parseInt(date.split("-")[2]);
		
		if (m < 1 || m > 12 || d < 1 || d > 31) {
			txtDateSearch.setValue(null);
			if (m < 1 || m > 12) throw new Exception("Invalid month.");
			if (d < 1 || d > 31) throw new Exception("Invalid day.");
		}
		else {
			int maxDay;
			if (m == 4 || m == 6 || m == 9 || m == 11) {
	            maxDay = 30;
	        } else if (m == 2) {
	        	if (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0))
	        		maxDay = 29;
	        	else maxDay = 28; 
	        } else maxDay = 31;
			
			if (d > maxDay) { 
				txtDateSearch.setValue(null);
				throw new Exception("Invalid day.");
			}
		}
	}
	
	public void checkMonth(String date) throws Exception {
		int m = Integer.parseInt(date);
		
		if (m < 1 || m > 12) {
			txtDateSearch.setValue(null);
			throw new Exception("Invalid month");
		}
	}
	
	public void checkDay(String date) throws Exception {
		int d = Integer.parseInt(date);
		
		if (d < 1 || d > 31) {
			txtDateSearch.setValue(null);
			throw new Exception("Invalid day");
		}
	}
	
	public void checkDayMonth(String date) throws Exception {
		int d = Integer.parseInt(date.split("-")[1]);
		int m = Integer.parseInt(date.split("-")[0]);
		
		if (m < 1 || m > 12) {
			txtDateSearch.setValue(null);
			throw new Exception("Invalid month");
		} 
		
		switch(m) {
			case 4:
			case 6:
			case 9:
			case 11:
				if (d < 1 || d > 30) {
					txtDateSearch.setValue(null);
					throw new Exception("Invalid day");
				}
				break;
			case 2:
				if (d < 1 || d > 29) {
					txtDateSearch.setValue(null);
					throw new Exception("Invalid day");
				}
				break;
			default:
				if (d < 1 || d > 31) {
					txtDateSearch.setValue(null);
					throw new Exception("Invalid day");
				}
				break;
		}
	}
	
	public void getAllBills() {
		tbContent = new Vector();
		lsbill = pb.getListBillsToday(Date.valueOf(today));
		double total = 0;
		for (int i=0; i<lsbill.size(); i++) {
			Bill b = lsbill.get(i);
			Vector tbRow = new Vector();
			tbRow.add(b.getId());
			tbRow.add(b.getName());
			tbRow.add(b.getDate());
			tbRow.add(b.getAmount());
			tbRow.add(b.getDiscount() + " %");
			tbRow.add(b.getTotal() + " $");
			tbContent.add(tbRow);
		}
		dtm.setDataVector(tbContent, tbHeader);
		tbBills.setModel(dtm);
	}
	
	public void getAllBillsBy(ArrayList<Bill> lsbill) throws Exception {
		try {
			if (lsbill.size() == 0)
				throw new Exception("Can't find any bill.");
			
			tbContent = new Vector();

			for (int i = 0; i < lsbill.size(); i++) {
				Bill b = lsbill.get(i);
				Vector tbRow = new Vector();
				tbRow.add(b.getId());
				tbRow.add(b.getName());
				tbRow.add(b.getDate());
				tbRow.add(b.getAmount());
				tbRow.add(b.getDiscount() + " %");
				tbRow.add(b.getTotal() + " $");
				tbContent.add(tbRow);
			}
			txtDateSearch.setValue(null);
			dtm.setDataVector(tbContent, tbHeader);
			tbBills.setModel(dtm);
		}
		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}
	
	public void loadDateField() {
		try {
			String y = radioBtnYear.isSelected() ? "####" : null;
			String m = radioBtnMonth.isSelected() ? "##" : null;
			String d = radioBtnDay.isSelected() ? "##" : null;
			
			ArrayList<String> dateList = new ArrayList<>();
			if (y != null) 
			    dateList.add(y);
			
			if (m != null) 
			    dateList.add(m);
			
			if (d != null) 
			    dateList.add(d);

			String[] date = dateList.toArray(new String[0]);

			
			mf = (!radioBtnYear.isSelected() && !radioBtnMonth.isSelected() && !radioBtnDay.isSelected()) ?
					new MaskFormatter("####-##-##") : new MaskFormatter(String.join("-", date));
			mf.setPlaceholderCharacter('#');
			
			txtDateSearch = new JFormattedTextField(mf);
			txtDateSearch.setBounds(441, 161, 156, 20);
			contentPane.add(txtDateSearch);
		} catch (ParseException ex) {
			showErrorMessage(ex.getMessage(), "Error");
		}
	}
	
	public void searchByDate() throws Exception {	
		String date = txtDateSearch.getValue().toString();
		
		boolean d = radioBtnDay.isSelected();
		boolean m = radioBtnMonth.isSelected();
		boolean y = radioBtnYear.isSelected();

		String year = "", month = "", day = "";
		
		try {
			if (d && m && y) {
				year = date.substring(0, 4);
			    month = date.substring(5, 7);
			    day = date.substring(8, 10);
			    checkDate(txtDateSearch.getValue().toString());
			}
			else if (d && m) {
			    month = date.substring(0, 2);
			    day = date.substring(3, 5);
			    checkDayMonth(txtDateSearch.getValue().toString());
			} else if (d && y) {
			    year = date.substring(0, 4);
			    day = date.substring(5, 7);
			    checkDay(day);
			} else if (d) {
			    day = date;
			    checkDay(day);
			}
			else if (m && y) {
			    year = date.substring(0, 4);
			    month = date.substring(5, 7);
			    checkMonth(month);
			} else if (m) {
			    month = date;
			    checkMonth(month);
			}
			else if (y) 
			    year = date;
			else {
			    year = date.substring(0, 4);
			    month = date.substring(5, 7);
			    day = date.substring(8, 10);
			    checkDate(txtDateSearch.getValue().toString());
			}
		}
		catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
						
		ArrayList<Bill> lsbill = pb.getBillsBy(year, month, day);
		getAllBillsBy(lsbill);
	}

	public GUI_Bills() {
		setTitle("Book Store - Bills");
		ImageIcon logoutIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/logout.png"), 30, 25);
		ImageIcon billStatisticIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/billstatistic.png"), 30, 25);
		ImageIcon publishersIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/publishers.png"), 30, 25);
		ImageIcon booksIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/books.png"), 30, 25);
		ImageIcon authorsIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/authors.png"), 30, 25);
		ImageIcon customersIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/customers.png"), 30, 25);
		ImageIcon sellBooksIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/sellbooks.png"), 30, 25);
		ImageIcon categoriesIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/categories.png"), 30, 25);
		ImageIcon bookStatisticIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/bookstatistic.png"), 30, 25);
		ImageIcon homeIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/bookstore.png"), 30, 25);
		ImageIcon billIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/billsIcon.png"), 60, 55);
	     
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 922, 635);
		contentPane = new JPanel();
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255)));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(0, 11, 906, 94);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.setContentAreaFilled(false);
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogOut.setFocusPainted(false);
		btnLogOut.setFocusTraversalKeysEnabled(false);
		btnLogOut.setFocusable(false);
		btnLogOut.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogOut.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLogOut.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogOut.setIcon(logoutIcon);
		btnLogOut.setBounds(810, 0, 89, 88);
		panel.add(btnLogOut);
		
		JButton btnBillStatistic = new JButton("Bill Statistic");
		btnBillStatistic.setContentAreaFilled(false);
		btnBillStatistic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBillStatistic.setFocusPainted(false);
		btnBillStatistic.setFocusTraversalKeysEnabled(false);
		btnBillStatistic.setFocusable(false);
		btnBillStatistic.setBounds(721, 0, 89, 88);
		btnBillStatistic.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnBillStatistic.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBillStatistic.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBillStatistic.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBillStatistic.setIcon(billStatisticIcon);
		panel.add(btnBillStatistic);
		
		JButton btnCustomers = new JButton("Customers");
		btnCustomers.setContentAreaFilled(false);
		btnCustomers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCustomers.setFocusable(false);
		btnCustomers.setFocusTraversalKeysEnabled(false);
		btnCustomers.setFocusPainted(false);
		btnCustomers.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCustomers.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCustomers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCustomers.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnCustomers.setIcon(customersIcon);
		btnCustomers.setBounds(187, 0, 89, 88);
		panel.add(btnCustomers);
		
		JButton btnBooks = new JButton("Books");
		btnBooks.setContentAreaFilled(false);
		btnBooks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBooks.setFocusPainted(false);
		btnBooks.setFocusTraversalKeysEnabled(false);
		btnBooks.setFocusable(false);
		btnBooks.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBooks.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBooks.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBooks.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnBooks.setIcon(booksIcon);
		btnBooks.setBounds(276, 0, 89, 88);
		panel.add(btnBooks);
		
		JButton btnPay = new JButton("Payment");
		btnPay.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPay.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPay.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPay.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPay.setFocusable(false);
		btnPay.setFocusTraversalKeysEnabled(false);
		btnPay.setFocusPainted(false);
		btnPay.setContentAreaFilled(false);
		btnPay.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnPay.setIcon(sellBooksIcon);
		btnPay.setBounds(98, 0, 89, 88);
		panel.add(btnPay);
		
		JButton btnHome = new JButton("Home");
		btnHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHome.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnHome.setHorizontalTextPosition(SwingConstants.CENTER);
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnHome.setFocusable(false);
		btnHome.setFocusTraversalKeysEnabled(false);
		btnHome.setFocusPainted(false);
		btnHome.setContentAreaFilled(false);
		btnHome.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(192, 192, 192)));
		btnHome.setIcon(homeIcon);
		btnHome.setBounds(9, 0, 89, 88);
		panel.add(btnHome);
		
		JButton btnPublishers = new JButton("Publishers");
		btnPublishers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPublishers.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPublishers.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPublishers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPublishers.setFocusable(false);
		btnPublishers.setFocusTraversalKeysEnabled(false);
		btnPublishers.setFocusPainted(false);
		btnPublishers.setContentAreaFilled(false);
		btnPublishers.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnPublishers.setIcon(publishersIcon);
		btnPublishers.setBounds(543, 0, 89, 88);
		panel.add(btnPublishers);
		
		JButton btnAuthors = new JButton("Authors");
		btnAuthors.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAuthors.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAuthors.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAuthors.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAuthors.setFocusable(false);
		btnAuthors.setFocusTraversalKeysEnabled(false);
		btnAuthors.setFocusPainted(false);
		btnAuthors.setContentAreaFilled(false);
		btnAuthors.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnAuthors.setIcon(authorsIcon);
		btnAuthors.setBounds(365, 0, 89, 88);
		panel.add(btnAuthors);
		
		JButton btnCategories = new JButton("Categories");
		btnCategories.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCategories.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCategories.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCategories.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCategories.setFocusable(false);
		btnCategories.setFocusTraversalKeysEnabled(false);
		btnCategories.setFocusPainted(false);
		btnCategories.setContentAreaFilled(false);
		btnCategories.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnCategories.setIcon(categoriesIcon);
		btnCategories.setBounds(454, 0, 89, 88);
		panel.add(btnCategories);
		
		JButton btnBookStatistic = new JButton("Book Statistic");
		btnBookStatistic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBookStatistic.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBookStatistic.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBookStatistic.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBookStatistic.setFocusable(false);
		btnBookStatistic.setFocusTraversalKeysEnabled(false);
		btnBookStatistic.setFocusPainted(false);
		btnBookStatistic.setContentAreaFilled(false);
		btnBookStatistic.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnBookStatistic.setIcon(bookStatisticIcon);
		btnBookStatistic.setBounds(632, 0, 89, 88);
		panel.add(btnBookStatistic);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setHorizontalTextPosition(SwingConstants.CENTER);
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSearch.setBounds(444, 193, 89, 25);
		contentPane.add(btnSearch);
		
		JLabel lblSearchBy = new JLabel("Search by:");
		lblSearchBy.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy.setBounds(277, 131, 80, 20);
		contentPane.add(lblSearchBy);
		
		radioBtnDay = new JRadioButton("Day");
		radioBtnDay.setFont(new Font("Tahoma", Font.PLAIN, 15));
		radioBtnDay.setBounds(363, 129, 51, 25);
		contentPane.add(radioBtnDay);
		
		radioBtnMonth = new JRadioButton("Month");
		radioBtnMonth.setFont(new Font("Tahoma", Font.PLAIN, 15));
		radioBtnMonth.setBounds(432, 129, 70, 25);
		contentPane.add(radioBtnMonth);
		
		radioBtnYear = new JRadioButton("Year");
		radioBtnYear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		radioBtnYear.setBounds(514, 129, 70, 25);
		contentPane.add(radioBtnYear);
		
		JLabel lblDayMonth = new JLabel("Day / Month / Year:");
		lblDayMonth.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDayMonth.setBounds(277, 162, 137, 20);
		contentPane.add(lblDayMonth);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 235, 886, 287);
		contentPane.add(scrollPane);
		
		tbBills = new JTable();
		scrollPane.setViewportView(tbBills);
		
		JLabel lblNewLabel_2 = new JLabel("List bills:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, 214, 160, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setHorizontalTextPosition(SwingConstants.CENTER);
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnClear.setBounds(617, 129, 89, 25);
		contentPane.add(btnClear);
		
		JLabel lblYyyymmddExample = new JLabel("yyyy-mm-dd");
		lblYyyymmddExample.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblYyyymmddExample.setBounds(617, 156, 89, 25);
		contentPane.add(lblYyyymmddExample);
		
		JLabel lblExample = new JLabel("(Example: 2002-06-28)");
		lblExample.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblExample.setBounds(599, 183, 133, 20);
		contentPane.add(lblExample);
		
		JLabel lbIconBill = new JLabel("");
		lbIconBill.setHorizontalAlignment(SwingConstants.CENTER);
		lbIconBill.setIcon(billIcon);
		lbIconBill.setBounds(150, 116, 117, 97);
		contentPane.add(lbIconBill);
		
		lbCheckSearch = new JLabel("");
		lbCheckSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lbCheckSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbCheckSearch.setBounds(292, 214, 313, 20);
		contentPane.add(lbCheckSearch);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete.setBounds(401, 544, 89, 30);
		contentPane.add(btnDelete);
		
		tbHeader.add("ID Bill");
		tbHeader.add("Customer's name");
		tbHeader.add("Date");
		tbHeader.add("Amount");
		tbHeader.add("Discount");
		tbHeader.add("Total");
		
		loadDateField();
		getAllBills();
		
		tbBills.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		            int selectedRow = tbBills.getSelectedRow();
		            if (selectedRow >= 0) {
		                Vector vt = (Vector) tbContent.get(selectedRow);
		                billChoose.setId((int) vt.get(0));
		            }
		        }
		    }
		});
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioBtnDay.setSelected(false);
				radioBtnMonth.setSelected(false);
				radioBtnYear.setSelected(false);
				getAllBills();
			}
		});
		
		radioBtnMonth.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txtDateSearch.setVisible(false);
				loadDateField();
			}
		});
		
		radioBtnDay.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txtDateSearch.setVisible(false);
				loadDateField();
			}
		});
		
		radioBtnYear.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txtDateSearch.setVisible(false);
				loadDateField();
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtDateSearch.getText().toString().indexOf('#') != -1) 
						throw new Exception("Search field is not full filled.");
					
					searchByDate();
				}
				catch(Exception ex) {
					showErrorMessage(ex.getMessage(), "Find fail");
					getAllBills();
					txtDateSearch.setValue(null);
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (billChoose.getId() == -1) 
						throw new Exception("Please choose bill you want to delete.");
					
					int result = JOptionPane.showConfirmDialog(frame,"Delete this customer?", 
							"Are you sure?",
				             JOptionPane.YES_NO_OPTION,
				             JOptionPane.QUESTION_MESSAGE);
				    if(result == JOptionPane.YES_OPTION){
				    	boolean checkDelete = pb.deleteBill(billChoose.getId());
						
						if (!checkDelete) 
							throw new Error("Something not wrong.");
						
						showSuccessMessage("Deleted successfully", "Success");
						billChoose = new Bill();
						getAllBills();
				    }
				}
				catch(Exception ex) {
					showErrorMessage(ex.getMessage(), "Deleted fail");
				}
			}
		});
		
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Home h = new GUI_Home();
				h.setLocationRelativeTo(null); 
				h.setVisible(true);
			}
		});
		
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Login l = new GUI_Login();
				l.setLocationRelativeTo(null); 
				l.setVisible(true);
			}
		});
		
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Pay p = new GUI_Pay();
				p.setLocationRelativeTo(null); 
				p.setVisible(true);
			}
		});
		
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Customers cus = new GUI_Customers();
				cus.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				cus.setLocationRelativeTo(null); 
				cus.setVisible(true);
			}
		});
		
		btnPublishers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Authors acc = new GUI_Authors();
				acc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				acc.setLocationRelativeTo(null); 
				acc.setVisible(true);
			}
		});
		
		btnCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Categories c = new GUI_Categories();
				c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				c.setLocationRelativeTo(null); 
				c.setVisible(true);
			}
		});
		
		btnBillStatistic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_BillStatistic s = new GUI_BillStatistic();
				s.setLocationRelativeTo(null); 
				s.setVisible(true);
			}
		});
		
		btnBookStatistic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_BookStatistic c = new GUI_BookStatistic();
				c.setLocationRelativeTo(null); 
				c.setVisible(true);
			}
		});
		
		btnBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Books b = new GUI_Books();
				b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				b.setLocationRelativeTo(null); 
				b.setVisible(true);
			}
		});
	}
}
