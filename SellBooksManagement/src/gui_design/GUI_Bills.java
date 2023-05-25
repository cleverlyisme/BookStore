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
import javax.swing.event.ChangeListener;
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
	
	private MaskFormatter mfDay = null, mfMonth = null, mfMonthYear = null, mfYear = null;

	private DefaultTableModel dtm = new DefaultTableModel();
	private Vector tbHeader = new Vector();
	private Vector tbContent = new Vector();
	private Process_Bill pb = new Process_Bill();
	private Process_Book pbook = new Process_Book();
	private Process_Customer pc = new Process_Customer();
	private Bill billChoose;
	private ArrayList<Bill> lsbill = new ArrayList<>();
	
	private static GUI_Bills frame = new GUI_Bills();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					GUI_Login l = new GUI_Login();
//					l.setLocationRelativeTo(null); 
//					l.setVisible(true);
					frame.setLocationRelativeTo(null); 
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void clearInfor() {
//		txtIDBill.setText(null);
//		txtIDCustomer.setText(null);
//		txtIDBook.setText(null);
//		txtDate.setText(null);
//		txtAmount.setText(null);
//		txtTotal.setText(null);
	}
	
//	public void getAllBills() {
//		tbContent = new Vector();
//		lsbill = pb.getListBills();
//		for (int i=0; i<lsbill.size(); i++) {
//			Bill b = lsbill.get(i);
//			Vector tbRow = new Vector();
//			tbRow.add(i+1);
//			tbRow.add(b.getID());
//			tbRow.add(b.getIDCustomer());
//			tbRow.add(b.getIDBook());
//			tbRow.add(pc.getCustomerByID(b.getIDCustomer()).getName());
//			tbRow.add(pbook.getBookByID(b.getIDBook()).getTitle());
//			tbRow.add(b.getDate());
//			tbRow.add(b.getAmount());
//			tbRow.add(b.getTotal());
//			tbContent.add(tbRow);
//		}
//		dtm.setDataVector(tbContent, tbHeader);
//		tbBills.setModel(dtm);
//	}
	
	public void updateBill(int IDBill, String IDCustomer, String IDBook, Date date, int Amount) {
		Book b = pbook.getBookByID(IDBook);
//		if (Amount > (billChoose.getAmount() + b.getAmount())) {
//			lbCheckBill.setText("There are not enough books.");
//			lbCheckBill.setForeground(Color.red);
//		}
//		else {
//			if (pb.updateBill(IDBill, IDCustomer, IDBook, date, Amount, b.getPrice()*Amount)) {
//				pbook.updateAmountOfBook(IDBook, billChoose.getAmount()+b.getAmount()-Amount);
//				lbCheckBill.setText("Updated successfully.");
//				lbCheckBill.setForeground(Color.blue);
// 				getAllBills();
//				clearInfor();
//			}
//		}
	}
	
//	public void deleteBill(int IDBill, String IDBook) {
//		boolean checkDelete = pb.deleteBill(IDBill, IDBook);
//		if (checkDelete) {
//			lbCheckBill.setText("Deleted bill successfully.");
//			lbCheckBill.setForeground(Color.blue);
//			clearInfor();
//			getAllBills();
//		}
//		else {
//			lbCheckBill.setText("Please select a bill from table that you want to delete.");
//			lbCheckBill.setForeground(Color.red);
//		}
//	}
	
//	public void getAllBillsBy(ArrayList<Bill> lsbill) {
//		tbContent = new Vector();
//		for (int i=0; i<lsbill.size(); i++) {
//			Bill b = lsbill.get(i);
//			Vector tbRow = new Vector();
//			tbRow.add(i+1);
//			tbRow.add(b.getID());
//			tbRow.add(b.getIDCustomer());
//			tbRow.add(b.getIDBook());
//			tbRow.add(pc.getCustomerByID(b.getIDCustomer()).getName());
//			tbRow.add(pbook.getBookByID(b.getIDBook()).getTitle());
//			tbRow.add(b.getDate());
//			tbRow.add(b.getAmount());
//			tbRow.add(b.getTotal());
//			tbContent.add(tbRow);
//		}
//		dtm.setDataVector(tbContent, tbHeader);
//		tbBills.setModel(dtm);
//	}
	
//	public void searchByIDAndDate() {
//		int IDBill = -1;
//		try {
//			IDBill = Integer.parseInt(txtIDSearch.getText());
//		} catch (Exception ex) {}
//		if (IDBill == -1) {
//			lbCheckSearch.setText("Invalid ID.");
//			lbCheckSearch.setForeground(Color.red);
//		}
//		else {
//			try {
//				lbCheckSearch.setText(null);
//				ArrayList<Bill> lsbill = radioBtnDay.isSelected() ? pb.getBillsByIDAndDay(IDBill, Date.valueOf(txtDateSearch.getText()))
//						: radioBtnMonth.isSelected() && radioBtnYear.isSelected() ? pb.getBillsByIDAndMonthYear(IDBill, txtDateSearch.getText())
//								: radioBtnMonth.isSelected() && !radioBtnYear.isSelected() ? pb.getBillsByIDAndMonth(IDBill, txtDateSearch.getText())
//										: !radioBtnMonth.isSelected() && radioBtnYear.isSelected() ? pb.getBillsByIDAndYear(IDBill, txtDateSearch.getText())
//												: pb.getListBillsByID(IDBill);
//				getAllBillsBy(lsbill);
//			} catch(Exception ex) {
//				lbCheckSearch.setText("Invalid date input.");
//				lbCheckSearch.setForeground(Color.red);
//			}
//		}
//	}
	
	public void searchByDate() {
		try {
			lbCheckSearch.setText(null);
			ArrayList<Bill> lsbill = radioBtnDay.isSelected() ? pb.getBillsByDay(Date.valueOf(txtDateSearch.getText()))
					: radioBtnMonth.isSelected() && radioBtnYear.isSelected() ? pb.getBillsByMonthYear(txtDateSearch.getText())
							: radioBtnMonth.isSelected() && !radioBtnYear.isSelected() ? pb.getBillsByMonth(txtDateSearch.getText())
									: !radioBtnMonth.isSelected() && radioBtnYear.isSelected() ? pb.getBillsByYear(txtDateSearch.getText())
											: pb.getListBills();
//			getAllBillsBy(lsbill);
		} catch(Exception ex) {
			lbCheckSearch.setText("Invalid date input.");
			lbCheckSearch.setForeground(Color.red);
		}
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
	
		try {
			mfDay = new MaskFormatter("####-##-##");
			mfYear = new MaskFormatter("####");
			mfMonth = new MaskFormatter("##"); 
			mfMonthYear = new MaskFormatter("####-##");
			mfDay.setPlaceholderCharacter('#');
			mfMonth.setPlaceholderCharacter('#');
			mfYear.setPlaceholderCharacter('#');
			mfMonthYear.setPlaceholderCharacter('#');
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	     
		
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
		
		txtDateSearch = new JFormattedTextField(mfDay);
		txtDateSearch.setBounds(444, 160, 156, 20);
		contentPane.add(txtDateSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 235, 886, 114);
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
		
		tbHeader.add("Index");
		tbHeader.add("ID Bill");
		tbHeader.add("ID Customer");
		tbHeader.add("ID Book");
		tbHeader.add("Customer's name");
		tbHeader.add("Book's name");
		tbHeader.add("Date");
		tbHeader.add("Amount");
		tbHeader.add("Total");
		
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
		
		JButton btnDelete_1 = new JButton("Delete");
		btnDelete_1.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDelete_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDelete_1.setBounds(401, 544, 89, 30);
		contentPane.add(btnDelete_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 395, 886, 122);
		contentPane.add(scrollPane_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Details:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2_1.setBounds(10, 374, 160, 14);
		contentPane.add(lblNewLabel_2_1);
		
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g1.clearSelection();
				radioBtnYear.setSelected(false);
//				checkBoxID.setSelected(false);
			}
		});
		
//		btnDelete.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int IDBill = -1;
//				String IDBook = txtIDBook.getText();
//				try {
//					IDBill = Integer.parseInt(txtIDBill.getText());
//				} catch(Exception ex) {
//					lbCheckBill.setText("Please select a bill from table that you want to delete.");
//					lbCheckBill.setForeground(Color.red);
//				}
//				if (IDBill != -1) {
//					int result = JOptionPane.showConfirmDialog(frame,"Delete all bills have ID Bill: "+IDBill+"\nand ID Book:"+IDBook+"?", 
//							"Are you sure?",
//				             JOptionPane.YES_NO_OPTION,
//				             JOptionPane.QUESTION_MESSAGE);
//				    if(result == JOptionPane.YES_OPTION){
//				    	deleteBill(IDBill, IDBook);
//				    }
//				}
//			}
//		});
		
//		btnDeleteAllBill.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				int IDBill = -1;
//				try {
//					IDBill = Integer.parseInt(txtIDBill.getText());
//				} catch(Exception ex) {
//					lbCheckBill.setText("Please select a bill from table that you want to update.");
//					lbCheckBill.setForeground(Color.red);
//				}
//				if (IDBill != -1) {
//					int result = JOptionPane.showConfirmDialog(frame,"Delete all bills have ID: "+IDBill+"?", 
//							"Are you sure?",
//				             JOptionPane.YES_NO_OPTION,
//				             JOptionPane.QUESTION_MESSAGE);
//				    if(result == JOptionPane.YES_OPTION){
//				    	pb.deleteAllBillByID(IDBill);
//				    	lbCheckBill.setText("Deleted successfully.");
//						lbCheckBill.setForeground(Color.blue);
//				    	getAllBills();
//				    }
//				}
//			}
//		});
		
		radioBtnMonth.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txtDateSearch.setVisible(false);
				txtDateSearch = radioBtnDay.isSelected() ? new JFormattedTextField(mfDay)
						: radioBtnMonth.isSelected() && radioBtnYear.isSelected() ? new JFormattedTextField(mfMonthYear)
								: radioBtnMonth.isSelected() && !radioBtnYear.isSelected() ? new JFormattedTextField(mfMonth)
										: !radioBtnMonth.isSelected() && radioBtnYear.isSelected() ? new JFormattedTextField(mfYear) 
												: new JFormattedTextField(mfDay);
				txtDateSearch.setBounds(395, 154, 156, 20);
				contentPane.add(txtDateSearch);
			}
		});
		
		radioBtnDay.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txtDateSearch.setVisible(false);
				txtDateSearch = radioBtnDay.isSelected() ? new JFormattedTextField(mfDay)
						: radioBtnMonth.isSelected() && radioBtnYear.isSelected() ? new JFormattedTextField(mfMonthYear)
								: radioBtnMonth.isSelected() && !radioBtnYear.isSelected() ? new JFormattedTextField(mfMonth)
										: !radioBtnMonth.isSelected() && radioBtnYear.isSelected() ? new JFormattedTextField(mfYear) 
												: new JFormattedTextField(mfDay);
				txtDateSearch.setBounds(395, 154, 156, 20);
				contentPane.add(txtDateSearch);
			}
		});
		
		radioBtnYear.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				txtDateSearch.setVisible(false);
				txtDateSearch = radioBtnDay.isSelected() ? new JFormattedTextField(mfDay)
						: radioBtnMonth.isSelected() && radioBtnYear.isSelected() ? new JFormattedTextField(mfMonthYear)
								: radioBtnMonth.isSelected() && !radioBtnYear.isSelected() ? new JFormattedTextField(mfMonth)
										: !radioBtnMonth.isSelected() && radioBtnYear.isSelected() ? new JFormattedTextField(mfYear) 
												: new JFormattedTextField(mfDay);
				txtDateSearch.setBounds(395, 154, 156, 20);
				contentPane.add(txtDateSearch);
			}
		});
		
//		btnSearch.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (checkBoxID.isSelected()) {
//					searchByIDAndDate();
//				}
//				else searchByDate();
//			}
//		});
		
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
		
		btnBookStatistic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_BookStatistic b = new GUI_BookStatistic();
				b.setLocationRelativeTo(null);
				b.setVisible(true);
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
		
		btnBillStatistic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_BillStatistic s = new GUI_BillStatistic();
				s.setLocationRelativeTo(null); 
				s.setVisible(true);
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
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
