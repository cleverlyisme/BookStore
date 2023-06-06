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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import process.Bill;
import process.Book;
import process.Process_Bill;
import process.Process_Book;
import process.Process_Customer;

public class GUI_BookStatistic extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Vector tbHeader = new Vector();
	private Vector tbContent = new Vector();
	private Process_Book pb = new Process_Book();
	private ArrayList<String> errors = new ArrayList<>(); 
	private ArrayList<Book> lsbook = new ArrayList<>();
	private ImageIconHelper imgHelp = new ImageIconHelper();
	private LocalDate today = LocalDate.now();
	private LocalDate yesterday = today.minusDays(1);
	
	private static GUI_BookStatistic frame = new GUI_BookStatistic();
	private JTable tbStatistic;
	private JTextField txtNameSearch;
	
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
	
	public void getAllBookStatistic() {
		tbContent = new Vector();
		lsbook = pb.getAllBookStatistic();
		for (int i=0; i<lsbook.size(); i++) {
			Book b = lsbook.get(i);
			Vector tbRow = new Vector();
			tbRow.add(b.getId());
			tbRow.add(b.getTitle());
			tbRow.add(b.getAuthor());
			tbRow.add(b.getPublisher());
			tbRow.add(b.getPrice());
			tbRow.add(b.getBookSold());
			tbRow.add(b.getQuantity());
			tbContent.add(tbRow);
		}
		dtm.setDataVector(tbContent, tbHeader);
		tbStatistic.setModel(dtm);
	}
	
	public void getAllBooksByName(String name) {
		try {
			ArrayList<Book> lsbook = pb.getAllBookStatisticByName(name);
			
			if (lsbook.size() == 0) 
				throw new Exception("Can't find any book with name "+name+".");
			
			tbContent = new Vector();

			for (int i = 0; i < lsbook.size(); i++) {
				Book b = lsbook.get(i);
				Vector tbRow = new Vector();
				tbRow.add(b.getId());
				tbRow.add(b.getTitle());
				tbRow.add(b.getAuthor());
				tbRow.add(b.getPublisher());
				tbRow.add(b.getPrice());
				tbRow.add(b.getBookSold());
				tbRow.add(b.getQuantity());
				tbContent.add(tbRow);
			}
			dtm.setDataVector(tbContent, tbHeader);
			tbStatistic.setModel(dtm);
		}
		catch (Exception ex) {
			errors.add(ex.getMessage());
		}
	}
	
	public GUI_BookStatistic() {
		setTitle("Book Store - Book Statistic");
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
		ImageIcon billStatisticIcon = imgHelp.getIcon(GUI_Bills.class.getResource("/icons/billstatistic.png"), 30, 25);
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 922, 750);
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
		
		JButton btnBookStatistic = new JButton("Bill Statistic");
		btnBookStatistic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBookStatistic.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBookStatistic.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBookStatistic.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBookStatistic.setFocusable(false);
		btnBookStatistic.setFocusTraversalKeysEnabled(false);
		btnBookStatistic.setFocusPainted(false);
		btnBookStatistic.setContentAreaFilled(false);
		btnBookStatistic.setBorder(new MatteBorder(1, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnBookStatistic.setIcon(billStatisticIcon);
		btnBookStatistic.setBounds(722, 0, 89, 88);
		panel.add(btnBookStatistic);
		
		JLabel lblSearchBy = new JLabel("Name:");
		lblSearchBy.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy.setBounds(343, 160, 57, 20);
		contentPane.add(lblSearchBy);
		
		JButton btnAnalysis = new JButton("Analysis");
		btnAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAnalysis.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAnalysis.setBounds(410, 204, 89, 20);
		contentPane.add(btnAnalysis);
		
		JLabel lbIconAnalysis = new JLabel("");
		lbIconAnalysis.setHorizontalAlignment(SwingConstants.CENTER);
		lbIconAnalysis.setIcon(analysisIcon);
		lbIconAnalysis.setBounds(180, 137, 111, 86);
		contentPane.add(lbIconAnalysis);
		
		JLabel lblNewLabel_2 = new JLabel("Quantity Statistics:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(20, 256, 160, 14);
		contentPane.add(lblNewLabel_2);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 291, 886, 276);
		contentPane.add(scrollPane);
		
		tbStatistic = new JTable();
		scrollPane.setViewportView(tbStatistic);
		
		JLabel lblSearchBy_1 = new JLabel("Yesterday:");
		lblSearchBy_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy_1.setBounds(20, 623, 80, 20);
		contentPane.add(lblSearchBy_1);
		
		JLabel lblSearchBy_2 = new JLabel("Last month:");
		lblSearchBy_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy_2.setBounds(20, 652, 80, 20);
		contentPane.add(lblSearchBy_2);
		
		JLabel lblSearchBy_3 = new JLabel("Last year:");
		lblSearchBy_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy_3.setBounds(20, 683, 876, 20);
		contentPane.add(lblSearchBy_3);
		
		JLabel lblNewLabel_2_1 = new JLabel("Most Book Sold Over:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2_1.setBounds(20, 594, 160, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lbTotalYesterday = new JLabel("");
		lbTotalYesterday.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbTotalYesterday.setBounds(95, 623, 801, 20);
		contentPane.add(lbTotalYesterday);
		
		JLabel lbTotalMonth = new JLabel("");
		lbTotalMonth.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbTotalMonth.setBounds(102, 652, 794, 20);
		contentPane.add(lbTotalMonth);
		
		JLabel lbTotalToday = new JLabel("190.0 $");
		lbTotalToday.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbTotalToday.setBounds(245, 592, 651, 20);
		contentPane.add(lbTotalToday);
		
		JLabel lblSearchBy_1_1 = new JLabel("Today:");
		lblSearchBy_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSearchBy_1_1.setBounds(195, 592, 701, 20);
		contentPane.add(lblSearchBy_1_1);
		
		JLabel lbTotalYear = new JLabel("");
		lbTotalYear.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbTotalYear.setBounds(89, 683, 807, 20);
		contentPane.add(lbTotalYear);
		
		txtNameSearch = new JTextField();
		txtNameSearch.setBounds(410, 162, 210, 20);
		contentPane.add(txtNameSearch);
		txtNameSearch.setColumns(10);
		
		tbHeader.add("ID");
		tbHeader.add("Title");
		tbHeader.add("Author");
		tbHeader.add("Publisher");
		tbHeader.add("Price");
		tbHeader.add("Book Sold");
		tbHeader.add("Remain");
		
		getAllBookStatistic();
		
		btnAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = txtNameSearch.getText().toString();
					
					if (name.length() < 3 || name.length() > 50)  
						throw new Exception("Name's length must >3 and <50.");
					
					if (name.contains("  "))
						throw new Exception("Name can't have two consecutive space letters.");

					getAllBooksByName(name);
				}
				catch(Exception ex) {
					showErrorMessage(ex.getMessage(), "Find fail");
					txtNameSearch.setText("");
					getAllBookStatistic();
					errors = new ArrayList<>();
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
				GUI_BillStatistic b = new GUI_BillStatistic();
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
				GUI_Authors acc = new GUI_Authors();
				acc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				acc.setLocationRelativeTo(null); 
				acc.setVisible(true);
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
		String mostBookToday = pb.getTotalDay(Date.valueOf(today));
		String mostBookYesterday = pb.getTotalDay(Date.valueOf(yesterday));
		String mostBookMonth = pb.getTotalMonth(month, year);
		String mostBookYear = pb.getTotalYear(today.getYear()-1);
		lbTotalToday.setText(mostBookToday);
		lbTotalYesterday.setText(mostBookYesterday);
		lbTotalMonth.setText(mostBookMonth);
		lbTotalYear.setText(mostBookYear);
	}
}
