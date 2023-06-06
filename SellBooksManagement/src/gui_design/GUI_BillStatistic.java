package gui_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.util.zip.DataFormatException;

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

import process.Customer;
import process.Process_Author;
import process.Process_Customer;
import process.Book;
import process.Process_Book;
import process.Author;
import process.Bill;
import process.Process_Bill;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

public class GUI_BillStatistic extends JFrame {

	private JPanel contentPane;
	private JFormattedTextField txtDateSearch;
	private JRadioButton radioBtnDay;
	private JRadioButton radioBtnMonth;
	private JRadioButton radioBtnYear;

	private MaskFormatter mf = null;

	private DefaultTableModel dtm = new DefaultTableModel();
	private Vector tbHeader = new Vector();
	private Vector tbContent = new Vector();
	private Process_Bill pb = new Process_Bill();
	private Process_Customer pc = new Process_Customer();
	private ArrayList<String> errors = new ArrayList<>(); 
	private ArrayList<Bill> lsbill = new ArrayList<>();
	private ImageIconHelper imgHelp = new ImageIconHelper();
	private LocalDate today = LocalDate.now();
	private LocalDate yesterday = today.minusDays(1);
	
	private static GUI_BillStatistic frame = new GUI_BillStatistic();
	private JTable tbStatistic;
	
	public void showErrorMessage(String message, String err) {
	    JOptionPane.showMessageDialog(this, message, err, JOptionPane.ERROR_MESSAGE);
	}
	
	public void showSuccessMessage(String message, String suc) {
	    JOptionPane.showMessageDialog(this, message, suc, JOptionPane.INFORMATION_MESSAGE);
	}
	
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
	
	public void checkDate(String date) {
		int y = Integer.parseInt(date.split("-")[0]);
		int m = Integer.parseInt(date.split("-")[1]);
		int d = Integer.parseInt(date.split("-")[2]);
		
		if (m < 1 || m > 12 || d < 1 || d > 31) {
			if (m < 1 || m > 12) errors.add("Invalid month.");
			if (d < 1 || d > 31) errors.add("Invalid day.");
			txtDateSearch.setValue(null);
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
				errors.add("Invalid day.");
				txtDateSearch.setValue(null);
			}
		}
	}
	
	public void checkMonth(String date) {
		int m = Integer.parseInt(date);
		
		if (m < 1 || m > 12) {
			errors.add("Invalid month");
			txtDateSearch.setValue(null);
		}
	}
	
	public void checkDay(String date) {
		int d = Integer.parseInt(date);
		
		if (d < 1 || d > 31) {
			errors.add("Invalid day");
			txtDateSearch.setValue(null);
		}
	}
	
	public void checkDayMonth(String date) {
		int d = Integer.parseInt(date.split("-")[1]);
		int m = Integer.parseInt(date.split("-")[0]);
		
		if (m < 1 || m > 12) {
			errors.add("Invalid month");
			txtDateSearch.setValue(null);
		} 
		
		switch(m) {
			case 4:
			case 6:
			case 9:
			case 11:
				if (d < 1 || d > 30) {
					errors.add("Invalid day");
					txtDateSearch.setValue(null);
				}
				break;
			case 2:
				if (d < 1 || d > 29) {
					errors.add("Invalid day");
					txtDateSearch.setValue(null);
				}
				break;
			default:
				if (d < 1 || d > 31) {
					errors.add("Invalid day");
					txtDateSearch.setValue(null);
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
			tbRow.add(b.getTotal() + " $");
			total += b.getTotal();
			tbContent.add(tbRow);
		}
		Vector tbRow = new Vector();
		for (int i=0; i<4; i++) tbRow.add(i, null);
		tbRow.add(4, total + " $");
		tbContent.add(tbRow);
		dtm.setDataVector(tbContent, tbHeader);
		tbStatistic.setModel(dtm);
	}
	
	public void getAllBillsBy(ArrayList<Bill> lsbill) {
		try {
			if (lsbill.size() == 0)
				throw new Exception("Can't find any bill.");
			
			tbContent = new Vector();
			double total = 0;

			for (int i = 0; i < lsbill.size(); i++) {
				Bill b = lsbill.get(i);
				Vector tbRow = new Vector();
				tbRow.add(b.getId());
				tbRow.add(b.getName());
				tbRow.add(b.getDate());
				tbRow.add(b.getAmount());
				tbRow.add(b.getTotal() + " $");
				total += b.getTotal();
				tbContent.add(tbRow);
			}
			Vector tbRow = new Vector();
			for (int i = 0; i < 4; i++)
				tbRow.add(i, null);
			tbRow.add(4, total);
			tbContent.add(tbRow);
			txtDateSearch.setValue(null);
			dtm.setDataVector(tbContent, tbHeader);
			tbStatistic.setModel(dtm);
		}
		catch (Exception ex) {
			errors.add(ex.getMessage());
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

	public GUI_BillStatistic() {
		setTitle("Book Store - Bill Statistic");
		ImageIcon logoutIcon = imgHelp.getIcon(GUI_BillStatistic.class.getResource("/icons/logout.png"), 30, 25);
		ImageIcon billsIcon = imgHelp.getIcon(GUI_BillStatistic.class.getResource("/icons/bills.png"), 30, 25);
		ImageIcon authorsIcon = imgHelp.getIcon(GUI_BillStatistic.class.getResource("/icons/authors.png"), 30, 25);
		ImageIcon booksIcon = imgHelp.getIcon(GUI_BillStatistic.class.getResource("/icons/books.png"), 30, 25);
		ImageIcon customersIcon = imgHelp.getIcon(GUI_BillStatistic.class.getResource("/icons/customers.png"), 30, 25);
		ImageIcon sellBooksIcon = imgHelp.getIcon(GUI_BillStatistic.class.getResource("/icons/sellbooks.png"), 30, 25);
		ImageIcon homeIcon = imgHelp.getIcon(GUI_BillStatistic.class.getResource("/icons/bookstore.png"), 30, 25);
		ImageIcon analysisIcon = imgHelp.getIcon(GUI_BillStatistic.class.getResource("/icons/analysisIcon.png"), 90, 80);
		ImageIcon publishersIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/publishers.png"), 30, 25);
		ImageIcon categoriesIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/categories.png"), 30, 25);
		ImageIcon bookStatisticIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/bookstatistic.png"), 30, 25);
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 922, 693);
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
		btnLogOut.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogOut.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLogOut.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogOut.setIcon(logoutIcon);
		btnLogOut.setBounds(812, 0, 89, 88);
		panel.add(btnLogOut);
		
		JButton btnCustomers = new JButton("Customers");
		btnCustomers.setContentAreaFilled(false);
		btnCustomers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCustomers.setFocusable(false);
		btnCustomers.setFocusTraversalKeysEnabled(false);
		btnCustomers.setFocusPainted(false);
		btnCustomers.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCustomers.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCustomers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCustomers.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnCustomers.setIcon(customersIcon);
		btnCustomers.setBounds(273, 0, 89, 88);
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
		btnBooks.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnBooks.setIcon(booksIcon);
		btnBooks.setBounds(363, 0, 89, 88);
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
		btnPay.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnPay.setIcon(sellBooksIcon);
		btnPay.setBounds(95, 0, 89, 88);
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
		btnHome.setBounds(5, 0, 89, 88);
		panel.add(btnHome);
		
		JButton btnBills = new JButton("Bills");
		btnBills.setContentAreaFilled(false);
		btnBills.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBills.setFocusPainted(false);
		btnBills.setFocusTraversalKeysEnabled(false);
		btnBills.setFocusable(false);
		btnBills.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnBills.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBills.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBills.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBills.setIcon(billsIcon);
		btnBills.setBounds(183, 0, 89, 88);
		panel.add(btnBills);
		
		JButton btnAuthors = new JButton("Authors");
		btnAuthors.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAuthors.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAuthors.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAuthors.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAuthors.setFocusable(false);
		btnAuthors.setFocusTraversalKeysEnabled(false);
		btnAuthors.setFocusPainted(false);
		btnAuthors.setContentAreaFilled(false);
		btnAuthors.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnAuthors.setIcon(authorsIcon);
		btnAuthors.setBounds(453, 0, 89, 88);
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
		btnCategories.setBounds(542, 0, 89, 88);
		panel.add(btnCategories);
		
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
		btnPublishers.setBounds(632, 0, 89, 88);
		panel.add(btnPublishers);
		
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
		btnBookStatistic.setBounds(722, 0, 89, 88);
		panel.add(btnBookStatistic);
		
		JLabel lblSearchBy = new JLabel("Analysis by:");
		lblSearchBy.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy.setBounds(274, 132, 80, 20);
		contentPane.add(lblSearchBy);
		
		radioBtnDay = new JRadioButton("Day");
		radioBtnDay.setFont(new Font("Tahoma", Font.PLAIN, 15));
		radioBtnDay.setBounds(366, 130, 51, 25);
		contentPane.add(radioBtnDay);
		
		radioBtnMonth = new JRadioButton("Month");
		radioBtnMonth.setFont(new Font("Tahoma", Font.PLAIN, 15));
		radioBtnMonth.setBounds(429, 130, 70, 25);
		contentPane.add(radioBtnMonth);
		
		radioBtnYear = new JRadioButton("Year");
		radioBtnYear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		radioBtnYear.setBounds(506, 130, 70, 25);
		contentPane.add(radioBtnYear);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setHorizontalTextPosition(SwingConstants.CENTER);
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnClear.setBounds(596, 133, 89, 20);
		contentPane.add(btnClear);
		
		JLabel lblYyyymmddExample = new JLabel("yyyy-mm-dd");
		lblYyyymmddExample.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblYyyymmddExample.setBounds(614, 157, 89, 25);
		contentPane.add(lblYyyymmddExample);
		
		JLabel lblExample = new JLabel("(Example: 2002-6-28)");
		lblExample.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblExample.setBounds(597, 179, 133, 20);
		contentPane.add(lblExample);
		
		JLabel lblDayMonth = new JLabel("Day / Month / Year:");
		lblDayMonth.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDayMonth.setBounds(274, 163, 137, 20);
		contentPane.add(lblDayMonth);
		
		JButton btnAnalysis = new JButton("Analysis");
		btnAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAnalysis.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAnalysis.setBounds(441, 194, 89, 20);
		contentPane.add(btnAnalysis);
		
		JLabel lbIconAnalysis = new JLabel("");
		lbIconAnalysis.setHorizontalAlignment(SwingConstants.CENTER);
		lbIconAnalysis.setIcon(analysisIcon);
		lbIconAnalysis.setBounds(130, 132, 111, 86);
		contentPane.add(lbIconAnalysis);
		
		JLabel lblNewLabel_2 = new JLabel("Revenue Statistics:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(20, 256, 160, 14);
		contentPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 291, 886, 292);
		contentPane.add(scrollPane);
		
		tbStatistic = new JTable();
		scrollPane.setViewportView(tbStatistic);
		
		JLabel lblSearchBy_1 = new JLabel("Yesterday:");
		lblSearchBy_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy_1.setBounds(20, 623, 80, 20);
		contentPane.add(lblSearchBy_1);
		
		JLabel lblSearchBy_2 = new JLabel("Last month:");
		lblSearchBy_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy_2.setBounds(402, 623, 80, 20);
		contentPane.add(lblSearchBy_2);
		
		JLabel lblSearchBy_3 = new JLabel("Last year:");
		lblSearchBy_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy_3.setBounds(699, 623, 70, 20);
		contentPane.add(lblSearchBy_3);
		
		JLabel lblNewLabel_2_1 = new JLabel("Revenue Over:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2_1.setBounds(20, 594, 160, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lbTotalYesterday = new JLabel("");
		lbTotalYesterday.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbTotalYesterday.setBounds(95, 623, 125, 20);
		contentPane.add(lbTotalYesterday);
		
		JLabel lbTotalMonth = new JLabel("");
		lbTotalMonth.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbTotalMonth.setBounds(484, 623, 125, 20);
		contentPane.add(lbTotalMonth);
		
		JLabel lbTotalToday = new JLabel("190.0 $");
		lbTotalToday.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbTotalToday.setBounds(465, 594, 125, 20);
		contentPane.add(lbTotalToday);
		
		JLabel lblSearchBy_1_1 = new JLabel("Today:");
		lblSearchBy_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy_1_1.setBounds(415, 594, 51, 20);
		contentPane.add(lblSearchBy_1_1);
		
		JLabel lbTotalYear = new JLabel("");
		lbTotalYear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbTotalYear.setBounds(771, 623, 125, 20);
		contentPane.add(lbTotalYear);
		
		tbHeader.add("ID Bill");
		tbHeader.add("Customer's name");
		tbHeader.add("Date");
		tbHeader.add("Amount");
		tbHeader.add("Total");
		
		loadDateField();
		getAllBills();
		
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
		
		btnAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String date = txtDateSearch.getText().toString();
					if (date.indexOf('#') != -1) 
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
		
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Home h = new GUI_Home();
				h.setLocationRelativeTo(null); 
				h.setVisible(true);
			}
		});
		
		btnPublishers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Publishers p = new GUI_Publishers();
				p.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				p.setLocationRelativeTo(null); 
				p.setVisible(true);
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
		
		btnBookStatistic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_BookStatistic b = new GUI_BookStatistic();
				b.setLocationRelativeTo(null);
				b.setVisible(true);
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
		
		btnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Customers cus = new GUI_Customers();
				cus.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				cus.setLocationRelativeTo(null); 
				cus.setVisible(true);
			}
		});
		
		btnAuthors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Authors au = new GUI_Authors();
				au.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				au.setLocationRelativeTo(null); 
				au.setVisible(true);
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
		
		btnBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Bills b = new GUI_Bills();
				b.setLocationRelativeTo(null); 
				b.setVisible(true);
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
		
		int month = today.getMonthValue() != 1 ? today.getMonthValue()-1 : 12;
		int year = today.getMonthValue() != 1 ? today.getYear() : today.getYear()-1;
		double totalToday = pb.getTotalDay(Date.valueOf(today));
		double totalYesterday = pb.getTotalDay(Date.valueOf(yesterday));
		double totalMonth = pb.getTotalMonth(month, year);
		double totalYear = pb.getTotalYear(today.getYear()-1);
		lbTotalToday.setText(Double.toString(totalToday)+" $");
		lbTotalYesterday.setText(Double.toString(totalYesterday)+" $");
		lbTotalMonth.setText(Double.toString(totalMonth)+" $");
		lbTotalYear.setText(Double.toString(totalYear)+" $");
	}
}
