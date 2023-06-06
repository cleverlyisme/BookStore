package gui_design;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Caret;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Button;
import java.awt.Cursor;
import java.awt.ComponentOrientation;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JScrollPane;
import java.awt.Point;
import javax.swing.JTable;

import process.Customer;
import process.Process_Customer;
import process.Book;
import process.Process_Book;
import process.Bill;
import process.BillDetail;
import process.Process_Bill;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GUI_Pay extends JFrame {

	private JPanel contentPane;
	private JTextField txtIDCus;
	private JTextField txtIDBook;
	private JTextField txtPhone;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtAuthor;
	private JTextField txtTitle;
	private JTextField txtPublisher;
	private JTextField txtImport;
	private JTextField txtAmount;
	private JTextField txtPrice;
	private JButton btnFetchCus;
	private JButton btnFetchBook;
	private JCheckBox checkAnonymous;
	private JTable JTableBill;
	private JLabel lbBill;
	private JLabel lbInsertBills;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Vector tbHeader = new Vector();
	private Vector tbContent = new Vector();
	public ArrayList<BillDetail> tbBooksAdded = new ArrayList();
	private ImageIconHelper imgHelp = new ImageIconHelper();
	private Process_Customer pc = new Process_Customer();
	private Process_Book pb = new Process_Book();
	private Customer ct = new Customer();
	private Book b = new Book();
	private Book bChoose = new Book();
	private Process_Bill pbill = new Process_Bill();
	public int totalAmount = 0;
	private LocalDate today = LocalDate.now();
	
	private static GUI_Pay frame = new GUI_Pay();
	private JTextField txtRank;
	private JTextField txtBookPurchased;
	private JTextField txtBirthday;
	private JTextField txtRemain;
	
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
	
	public void clearInforInput() {
		txtIDCus.setEditable(true);
		txtIDBook.setEditable(true);
		txtIDCus.setText(null);
		txtName.setText(null);
		txtPhone.setText(null);
		txtEmail.setText(null);
		txtBirthday.setText(null);
		txtIDBook.setText(null);
		txtTitle.setText(null);
		txtAuthor.setText(null);
		txtPublisher.setText(null);
		txtImport.setText(null);
		txtPrice.setText(null);
		txtAmount.setText(null);
		txtRemain.setText(null);
		checkAnonymous.setEnabled(true);
		checkAnonymous.setSelected(false);
		btnFetchCus.setText("Fetch");
		btnFetchBook.setText("Fetch");
		txtBirthday.setForeground(Color.black);
		txtRank.setForeground(Color.black);
		ct = new Customer();
		b = new Book();
		totalAmount = 0;
		tbBooksAdded.clear();
		tbContent.clear();
		dtm.setDataVector(tbContent, tbHeader);
		JTableBill.setModel(dtm);
	}
	
	public void clearInforCustomer() {
		txtIDCus.setText(null);
		txtName.setText(null);
		txtPhone.setText(null);
		txtEmail.setText(null);
		txtBirthday.setText(null);
		txtBookPurchased.setText(null);
		txtRank.setText(null);
		btnFetchCus.setText("Fetch");
		txtBirthday.setForeground(Color.black);
		txtRank.setForeground(Color.black);
		ct = new Customer();
		totalAmount = 0;
		tbBooksAdded.clear();
		tbContent.clear();
		dtm.setDataVector(tbContent, tbHeader);
		JTableBill.setModel(dtm);
	}
	
	public void clearInforBook() {
		txtIDBook.setText(null);
		txtTitle.setText(null);
		txtAuthor.setText(null);
		txtPublisher.setText(null);
		txtImport.setText(null);
		txtPrice.setText(null);
		txtAmount.setText(null);
		txtRemain.setText(null);
		btnFetchBook.setText("Fetch");
		b = new Book();
	}
	
	public void getTotalBill() {
		int discount = 0;

		if (!checkAnonymous.isSelected() && LocalDate.parse(txtBirthday.getText()).equals(today)
				&& ct.getBookPurhased() > 30) {
			discount += 3;
			txtBirthday.setForeground(Color.red);
		} else txtBirthday.setForeground(Color.black);

		if (!checkAnonymous.isSelected() && ct.getRank().equals("vip")) {
			discount += 2;
			txtRank.setForeground(Color.red);
		} else
			txtRank.setForeground(Color.black);

		if (totalAmount >= 30)
			discount += 2;

		if (tbContent.size() > 0) {
			double totalBill = 0;
			Vector totalRow = new Vector();
			for (int i = 0; i < tbContent.size(); i++) {
				Vector tbRow = (Vector) tbContent.get(i);
				if (tbRow.get(3).equals("Total bill:") || tbRow.get(3).equals("Discount:")) {
					tbContent.remove(i);
					i -= 1;
				} else
					totalBill += (double) tbRow.get(4);
			}
			
			double total = totalBill - (totalBill * discount / 100);
			for (int i = 0; i < 2; i++) {
				if (i == 0) {
					for (int j = 0; j < 3; j++)
						totalRow.add(j, null);
					totalRow.add(3, "Discount:");
					totalRow.add(4, discount + "%");
					tbContent.add(totalRow);
				} else {
					totalRow = new Vector();
					for (int j = 0; j < 3; j++)
					totalRow.add(j, null);
					totalRow.add(3, "Total bill:");
					totalRow.add(4, total);
					tbContent.add(totalRow);
				}
			}
		}
	}
	
	public void getCusInfor() {
		try {
			String idString = txtIDCus.getText();
			
			if (idString.trim().equals(""))
				throw new Exception("ID customer must not be empty.");
			
			int id = -1;
			
			try {
				id = Integer.parseInt(idString);
			}
			catch (Exception ex) {
				throw new Exception("ID customer must be int type");
			}
			
			ct = pc.getCustomerById(id);
			
			if (ct == null) 
				throw new Exception("Customer with ID "+id+" not found.");
			
			else {
				txtName.setText(ct.getName());
				txtEmail.setText(ct.getEmail());
				txtPhone.setText(ct.getPhone());
				txtBirthday.setText(""+ct.getBirth());
				txtBookPurchased.setText(""+ct.getBookPurhased());
				txtRank.setText(ct.getRank());
				btnFetchCus.setText("Clear");
				txtIDCus.setEditable(false);
			}
		} 
		catch (Exception ex) {
			showErrorMessage(ex.getMessage(), "Error");
			clearInforCustomer();
		}
	}
	
	public void getBookInfor() {
		try {
			String idString = txtIDBook.getText();
			
			if (idString.trim().equals(""))
				throw new Exception("ID book must not be empty.");
			
			int id = -1;
			
			try {
				id = Integer.parseInt(idString);
			}
			catch (Exception ex) {
				throw new Exception("ID book must be int type");
			}
			
			b = pb.getBookById(id);
			
			if (b == null)
				throw new Exception("Book with ID "+id+" not found.");
			
			else {
				txtTitle.setText(b.getTitle());
				txtAuthor.setText(b.getAuthor());
				txtPublisher.setText(b.getPublisher()); 
				txtImport.setText(""+b.getImportDay());
				txtPrice.setText(Double.toString(b.getPrice()));
				txtRemain.setText(""+b.getQuantity());
				btnFetchBook.setText("Clear");
				txtIDBook.setEditable(false);
			}
		}
		catch (Exception ex) {
			showErrorMessage(ex.getMessage(), "Error");
			clearInforBook();
		}
	}
	
	public void addBookToBillTable(int customerId, int bookId, int amount) {
		checkAnonymous.setEnabled(false);

		totalAmount += amount;

		double total = b.getPrice() * amount;

		tbBooksAdded.add(new BillDetail(bookId, amount));
		Vector tbRow = new Vector();
		tbRow.add(ct.getName() == null ? "Anonymous" : ct.getName());
		tbRow.add(b.getTitle());
		tbRow.add(b.getPrice());
		tbRow.add(amount);
		tbRow.add(total);
		tbContent.add(tbRow);
		getTotalBill();
		dtm.setDataVector(tbContent, tbHeader);
		JTableBill.setModel(dtm);
	}
	
	public void addToBill(int customerId, int bookId, String amountString) {
		try {					
			if (!checkAnonymous.isSelected() && customerId == -1)
				throw new Exception("Please fetch customer's infor.");
			
			if (bookId == -1)
				throw new Exception("Please fetch book's infor.");
			
			if (amountString.trim().equals(""))
				throw new Exception("Amount can't be blank.");

			int amount = -1;
			try {
				amount = Integer.parseInt(amountString);
			} catch (Exception ex) {
				throw new Exception("Amount must be int type.");
			}
			
			if (amount < 1)
				throw new Exception("Amount must be greater than 0.");
			
			if (amount > b.getQuantity())
				throw new Exception("There are not enough books. Only has " + b.getQuantity() + " left.");

			boolean checkExist = false;
			for (int i = 0; i < tbBooksAdded.size(); i++) {
				BillDetail bd = (BillDetail) tbBooksAdded.get(i);
				if (bd.getBookId() == (b.getId()))
					checkExist = true;
			}

			if (checkExist)
				throw new Exception("This book has already existed in the bill.");

			addBookToBillTable(customerId, bookId, amount);
		} catch (Exception ex) {
			showErrorMessage(ex.getMessage(), "Added to bill fail");
		}
	}
	
	public void updateBill() {
		try {
			if (tbBooksAdded.size() == 0) 
				throw new Exception("There's nothing in bill.");
				
			else {
				if (b.getId() == -1) 
					throw new Exception("Please select or fetch book that you want to update from bill.");
				
				boolean checkExist = false;
				for (int i=0; i<tbBooksAdded.size(); i++) {
					BillDetail bd = (BillDetail) tbBooksAdded.get(i);
					if (bd.getBookId() == b.getId()) 
						checkExist = true;
				}
				
				if (!checkExist) 
					throw new Exception("Book with title "+ b.getTitle() +" doesn't exist in bill.");
				
				if (txtAmount.getText().trim().equals("")) 
					throw new Exception("Amount must not be blank.");
				
				int amount = -1;
				try {
					amount = Integer.parseInt(txtAmount.getText());
				} catch (Exception e) {
					throw new Exception("Invalid amount type.");
				}
				
				if (amount < 1)
					throw new Exception("Amount must be greater than 0.");
				
				if (amount > b.getQuantity()) 
					throw new Exception("There are not enough books "+ b.getTitle() +". Only has " + b.getQuantity() + " left.");
				
				else {
					for (int i=0; i<tbBooksAdded.size(); i++) {
						BillDetail bd = (BillDetail) tbBooksAdded.get(i);
						if (b.getId() == bd.getBookId()) 
							tbBooksAdded.set(i, new BillDetail(bd.getBookId(), amount));
					}
					
					for (int i=0; i<tbContent.size(); i++) {
		                Vector vt = (Vector) tbContent.get(i);
		                Vector tbNewContent = new Vector();
						if (b.getTitle().equals(vt.get(1))) {
							vt.set(3, amount);
							vt.set(4, amount*b.getPrice());
							tbContent.set(i, vt);
							dtm.setDataVector(tbContent, tbHeader);
							getTotalBill();
							JTableBill.setModel(dtm);
							showSuccessMessage("Updated successfully", getName());
						}
					}
				}
			}
		}
		catch (Exception ex) {
			showErrorMessage(ex.getMessage(), "Updated from bill fail");
		}
	}
	
	public void deleteBill() {
		try {
			if (tbBooksAdded.size() == 0) 
				throw new Exception("There's nothing in bill.");
			
			if (b.getId() == -1)
				throw new Exception("Please select or fetch book's infor you want to delete from bill.");
			
			else {
				int result = JOptionPane.showConfirmDialog(frame,"Delete book has ID: "+b.getId()+" in bill?", 
						"Are you sure?",
			             JOptionPane.YES_NO_OPTION,
			             JOptionPane.QUESTION_MESSAGE);
			    if(result == JOptionPane.YES_OPTION){
			    	boolean checkDelete = false;
					for (int i=0; i<tbBooksAdded.size(); i++) {
						BillDetail bd = (BillDetail) tbBooksAdded.get(i);
						if (bd.getBookId() == b.getId()) {
							tbBooksAdded.remove(i);
							tbContent.remove(i);
							getTotalBill();
							dtm.setDataVector(tbContent, tbHeader);
							JTableBill.setModel(dtm);
							checkDelete = true;
						}
					}
					
					if (!checkDelete) 
						throw new Error("Deleted faild. There's no book has ID: "+b.getId());
					
					showSuccessMessage("Deleted successfully.", "Success");
			    }
			}
		}
		catch (Exception ex) {
			showErrorMessage(ex.getMessage(), "Updated from bill fail");
		}
	}
	
	public void payBill() {
		try {
			if (tbBooksAdded.size() == 0) 
				throw new Exception("There's nothing in bill.");
			
			else {
				Integer customerId = ct.getId() == -1 ? null : ct.getId();
				int discount = 0;
				
				if (!checkAnonymous.isSelected()  && ct.getBookPurhased()> 30 && LocalDate.parse(txtBirthday.getText()).equals(today)) 
					discount += 3;
				
				if (!checkAnonymous.isSelected()  && ct.getRank().equals("vip")) 
					discount += 2; 
				
				if (totalAmount >= 30) discount += 2;
				
				boolean checkInsert = pbill.insertBill(customerId, discount, tbBooksAdded);
				
				if (!checkInsert) throw new Exception("Pay fail.");
				
				showSuccessMessage("Pay bill successfully.", "Success");
				clearInforInput();
			}
		}
		catch (Exception ex) {
			showErrorMessage(ex.getMessage(), "Pay fail");
		}
	}

	public GUI_Pay() {
		setTitle("Book Store - Payment");
		ImageIcon logoutIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/logout.png"), 30, 25);
		ImageIcon billStatisticIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/billstatistic.png"), 30, 25);
		ImageIcon bookStatisticIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/bookstatistic.png"), 30, 25);
		ImageIcon authorsIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/authors.png"), 30, 25);
		ImageIcon booksIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/books.png"), 30, 25);
		ImageIcon customersIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/customers.png"), 30, 25);
		ImageIcon billsIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/bills.png"), 30, 25);
		ImageIcon homeIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/bookstore.png"), 30, 25);
		ImageIcon cusInforIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/cusInfor.png"), 120, 75);
		ImageIcon bookInforIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/bookInfor.png"), 120, 75);
		ImageIcon categoriesIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/categories.png"), 30, 25);
		ImageIcon publishersIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/publishers.png"), 30, 25);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 922, 858);
		contentPane = new JPanel();
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255)));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(0, 10, 906, 94);
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
		btnLogOut.setBounds(813, 0, 89, 88);
		panel.add(btnLogOut);
		
		JButton btnStatistic = new JButton("Bill Statistic");
		btnStatistic.setContentAreaFilled(false);
		btnStatistic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnStatistic.setFocusPainted(false);
		btnStatistic.setFocusTraversalKeysEnabled(false);
		btnStatistic.setFocusable(false);
		btnStatistic.setBounds(723, 0, 89, 88);
		panel.add(btnStatistic);
		btnStatistic.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnStatistic.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnStatistic.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnStatistic.setHorizontalTextPosition(SwingConstants.CENTER);
		btnStatistic.setIcon(billStatisticIcon);
		
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
		btnCustomers.setBounds(185, 0, 89, 88);
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
		btnBooks.setBounds(275, 0, 89, 88);
		panel.add(btnBooks);
		
		JButton btnBills = new JButton("Bills");
		btnBills.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBills.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBills.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBills.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBills.setFocusable(false);
		btnBills.setFocusTraversalKeysEnabled(false);
		btnBills.setFocusPainted(false);
		btnBills.setContentAreaFilled(false);
		btnBills.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnBills.setIcon(billsIcon);
		btnBills.setBounds(95, 0, 89, 88);
		panel.add(btnBills);
		
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
		
		JButton btnAuthors = new JButton("Authors");
		btnAuthors.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAuthors.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAuthors.setToolTipText("");
		btnAuthors.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAuthors.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAuthors.setFocusable(false);
		btnAuthors.setFocusTraversalKeysEnabled(false);
		btnAuthors.setFocusPainted(false);
		btnAuthors.setContentAreaFilled(false);
		btnAuthors.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnAuthors.setIcon(authorsIcon);
		btnAuthors.setBounds(365, 0, 89, 88);
		panel.add(btnAuthors);
		
		JButton btnBookStatistic = new JButton("BookStatistic");
		btnBookStatistic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBookStatistic.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBookStatistic.setToolTipText("");
		btnBookStatistic.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBookStatistic.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnBookStatistic.setFocusable(false);
		btnBookStatistic.setFocusTraversalKeysEnabled(false);
		btnBookStatistic.setFocusPainted(false);
		btnBookStatistic.setContentAreaFilled(false);
		btnBookStatistic.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnBookStatistic.setIcon(bookStatisticIcon);
		btnBookStatistic.setBounds(634, 0, 89, 88);
		panel.add(btnBookStatistic);
		
		JButton btnPublishers = new JButton("Publishers");
		btnPublishers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPublishers.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPublishers.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPublishers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnPublishers.setFocusable(false);
		btnPublishers.setFocusTraversalKeysEnabled(false);
		btnPublishers.setFocusPainted(false);
		btnPublishers.setContentAreaFilled(false);
		btnPublishers.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnPublishers.setIcon(publishersIcon);
		btnPublishers.setBounds(544, 0, 89, 88);
		panel.add(btnPublishers);
		
		JButton btnCategories = new JButton("Categories");
		btnCategories.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCategories.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCategories.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCategories.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCategories.setFocusTraversalKeysEnabled(false);
		btnCategories.setFocusPainted(false);
		btnCategories.setContentAreaFilled(false);
		btnCategories.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnCategories.setIcon(categoriesIcon);
		btnCategories.setBounds(454, 0, 89, 88);
		panel.add(btnCategories);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_1.setBounds(0, 123, 906, 758);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Customer Information", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 11, 886, 172);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		txtIDCus = new JTextField();
		txtIDCus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtIDCus.setBounds(355, 24, 118, 20);
		panel_2.add(txtIDCus);
		txtIDCus.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(328, 27, 26, 14);
		panel_2.add(lblNewLabel);
		
		btnFetchCus = new JButton("Fetch");
		btnFetchCus.setHorizontalTextPosition(SwingConstants.CENTER);
		btnFetchCus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFetchCus.setBounds(483, 24, 89, 20);
		panel_2.add(btnFetchCus);
		
		txtPhone = new JTextField();
		txtPhone.setEditable(false);
		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPhone.setColumns(10);
		txtPhone.setBounds(108, 91, 189, 20);
		panel_2.add(txtPhone);
		
		JLabel lblName = new JLabel("Phone");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(59, 92, 44, 14);
		panel_2.add(lblName);
		
		txtName = new JTextField();
		txtName.setEditable(false);
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setColumns(10);
		txtName.setBounds(108, 60, 189, 20);
		panel_2.add(txtName);
		
		JLabel lblPhone = new JLabel("Name");
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPhone.setBounds(61, 61, 44, 14);
		panel_2.add(lblPhone);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(61, 126, 44, 14);
		panel_2.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setEditable(false);
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtEmail.setColumns(10);
		txtEmail.setBounds(108, 125, 189, 20);
		panel_2.add(txtEmail);
		
		JLabel lbCusInfor = new JLabel("");
		lbCusInfor.setHorizontalAlignment(SwingConstants.CENTER);
		lbCusInfor.setBounds(360, 65, 163, 106);
		lbCusInfor.setIcon(cusInforIcon);
		panel_2.add(lbCusInfor);
		
		txtRank = new JTextField();
		txtRank.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtRank.setEditable(false);
		txtRank.setColumns(10);
		txtRank.setBounds(612, 130, 78, 20);
		panel_2.add(txtRank);
		
		JLabel lblRank = new JLabel("Rank");
		lblRank.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRank.setBounds(556, 131, 44, 14);
		panel_2.add(lblRank);
		
		txtBookPurchased = new JTextField();
		txtBookPurchased.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtBookPurchased.setEditable(false);
		txtBookPurchased.setColumns(10);
		txtBookPurchased.setBounds(679, 91, 78, 20);
		panel_2.add(txtBookPurchased);
		
		JLabel lblPhone_1_1 = new JLabel("Books purchased");
		lblPhone_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPhone_1_1.setBounds(556, 94, 118, 14);
		panel_2.add(lblPhone_1_1);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(559, 61, 59, 14);
		panel_2.add(lblBirthday);
		lblBirthday.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		txtBirthday = new JTextField();
		txtBirthday.setBounds(623, 60, 189, 20);
		panel_2.add(txtBirthday);
		txtBirthday.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtBirthday.setEditable(false);
		txtBirthday.setColumns(10);
		
		checkAnonymous = new JCheckBox("Anonymous customer");
		checkAnonymous.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkAnonymous.setBounds(153, 22, 169, 23);
		panel_2.add(checkAnonymous);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Book Information", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2_1.setBounds(10, 194, 886, 172);
		panel_1.add(panel_2_1);
		
		txtIDBook = new JTextField();
		txtIDBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtIDBook.setColumns(10);
		txtIDBook.setBounds(107, 20, 118, 20);
		panel_2_1.add(txtIDBook);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(80, 23, 26, 14);
		panel_2_1.add(lblNewLabel_1);
		
		btnFetchBook = new JButton("Fetch");
		btnFetchBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFetchBook.setBounds(235, 20, 89, 20);
		panel_2_1.add(btnFetchBook);
		
		txtAuthor = new JTextField();
		txtAuthor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAuthor.setEditable(false);
		txtAuthor.setColumns(10);
		txtAuthor.setBounds(640, 20, 189, 20);
		panel_2_1.add(txtAuthor);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAuthor.setBounds(591, 21, 44, 14);
		panel_2_1.add(lblAuthor);
		
		txtTitle = new JTextField();
		txtTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtTitle.setEditable(false);
		txtTitle.setColumns(10);
		txtTitle.setBounds(108, 51, 189, 20);
		panel_2_1.add(txtTitle);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTitle.setBounds(63, 52, 44, 14);
		panel_2_1.add(lblTitle);
		
		JLabel lblPublisher = new JLabel("Publisher");
		lblPublisher.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPublisher.setBounds(47, 90, 60, 14);
		panel_2_1.add(lblPublisher);
		
		txtPublisher = new JTextField();
		txtPublisher.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPublisher.setEditable(false);
		txtPublisher.setColumns(10);
		txtPublisher.setBounds(108, 87, 189, 20);
		panel_2_1.add(txtPublisher);
		
		JLabel lblPublishedAt = new JLabel("Import day");
		lblPublishedAt.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPublishedAt.setBounds(556, 54, 78, 25);
		panel_2_1.add(lblPublishedAt);
		
		txtImport = new JTextField();
		txtImport.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtImport.setEditable(false);
		txtImport.setColumns(10);
		txtImport.setBounds(640, 56, 189, 20);
		panel_2_1.add(txtImport);
		
		JLabel lblNewLabel_1_1 = new JLabel("Amount");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setBounds(49, 127, 53, 14);
		panel_2_1.add(lblNewLabel_1_1);
		
		txtAmount = new JTextField();
		txtAmount.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAmount.setColumns(10);
		txtAmount.setBounds(108, 124, 86, 20);
		panel_2_1.add(txtAmount);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrice.setBounds(597, 91, 37, 14);
		panel_2_1.add(lblPrice);
		
		txtPrice = new JTextField();
		txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPrice.setEditable(false);
		txtPrice.setColumns(10);
		txtPrice.setBounds(640, 90, 189, 20);
		panel_2_1.add(txtPrice);
		
		JLabel lbBookInfor = new JLabel("");
		lbBookInfor.setHorizontalTextPosition(SwingConstants.CENTER);
		lbBookInfor.setHorizontalAlignment(SwingConstants.CENTER);
		lbBookInfor.setBounds(387, 51, 120, 92);
		lbBookInfor.setIcon(bookInforIcon);
		panel_2_1.add(lbBookInfor);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("(> 30 discount 2%)");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(205, 126, 130, 14);
		panel_2_1.add(lblNewLabel_1_1_1);
		
		txtRemain = new JTextField();
		txtRemain.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtRemain.setEditable(false);
		txtRemain.setColumns(10);
		txtRemain.setBounds(640, 127, 60, 20);
		panel_2_1.add(txtRemain);
		
		JLabel lblRemain = new JLabel("Remain");
		lblRemain.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRemain.setBounds(581, 128, 53, 14);
		panel_2_1.add(lblRemain);
		
		Button btnAddToBill = new Button("Add to bill");
		btnAddToBill.setBounds(230, 393, 87, 33);
		panel_1.add(btnAddToBill);
		
		Button btnDeleteBill = new Button("Delete from bill");
		btnDeleteBill.setBounds(632, 393, 111, 33);
		panel_1.add(btnDeleteBill);
		
		Button btnUpdateBill = new Button("Update from bill");
		btnUpdateBill.setActionCommand("Update from bill");
		btnUpdateBill.setBounds(424, 393, 111, 33);
		panel_1.add(btnUpdateBill);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 459, 886, 172);
		panel_1.add(scrollPane);
		
		JTableBill = new JTable();
		scrollPane.setViewportView(JTableBill);
		
		JLabel lblNewLabel_2 = new JLabel("List books of bill:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(20, 432, 160, 14);
		panel_1.add(lblNewLabel_2);
		
		Button btnPay = new Button("Pay bill");
		btnPay.setBounds(336, 651, 87, 33);
		panel_1.add(btnPay);
		
		Button btnDelete = new Button("Delete bill");
		btnDelete.setBounds(479, 651, 111, 33);
		panel_1.add(btnDelete);
		
		JLabel lblNewLabel_2_1 = new JLabel("Sell Books Management");
		lblNewLabel_2_1.setBounds(10, 105, 177, 14);
		contentPane.add(lblNewLabel_2_1);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		tbHeader.add("Customer's Name");
		tbHeader.add("Book's Name");
		tbHeader.add("Price");
		tbHeader.add("Amount");
		tbHeader.add("Total");
		dtm.setDataVector(null, tbHeader);
		JTableBill.setModel(dtm);
		
		lbBill = new JLabel("");
		lbBill.setHorizontalAlignment(SwingConstants.CENTER);
		lbBill.setForeground(Color.RED);
		lbBill.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbBill.setBounds(250, 366, 340, 20);
		panel_1.add(lbBill);
		
		lbInsertBills = new JLabel("");
		lbInsertBills.setHorizontalAlignment(SwingConstants.CENTER);
		lbInsertBills.setForeground(Color.RED);
		lbInsertBills.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbInsertBills.setBounds(230, 625, 400, 20);
		panel_1.add(lbInsertBills);
		
		JTableBill.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		            int selectedRow = JTableBill.getSelectedRow();
		            if (selectedRow >= 0) {
		                Vector vt = (Vector) tbContent.get(selectedRow);
		                if (vt.get(1) != null) {
		                	b = pb.getBookByName((String) vt.get(1));
			                txtIDBook.setText(""+b.getId());
			                txtTitle.setText(b.getTitle());
			                txtAuthor.setText(b.getAuthor());
			                txtPublisher.setText(b.getPublisher());
			                txtImport.setText(""+b.getImportDay());
			                txtPrice.setText(""+b.getPrice());
							txtRemain.setText(""+b.getQuantity());
			                txtIDBook.setEditable(false);
			                btnFetchBook.setText("Clear");
		                }
		            }
		        }
		    }
		});
		
		checkAnonymous.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (checkAnonymous.isSelected()) {
					clearInforCustomer();
					txtIDCus.setEditable(false);
					btnFetchCus.setEnabled(false);
				} else {
					txtIDCus.setEditable(true);
					btnFetchCus.setEnabled(true);
				}
			}
		});
		
		btnFetchCus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnFetchCus.getText().equals("Fetch")) 
					getCusInfor();
				else {
					checkAnonymous.setSelected(false);
					btnFetchCus.setText("Fetch");
					txtIDCus.setEditable(true);
					clearInforCustomer();
				}
			}
		});
		
		btnFetchBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnFetchBook.getText().equals("Fetch")) 
					getBookInfor();
				else {
					btnFetchBook.setText("Fetch");
					txtIDBook.setEditable(true);
					clearInforBook();
				}
			}
		});
		
		btnAddToBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int customerId = ct.getId();
				int bookId = b.getId();
				String amountString = txtAmount.getText();

				addToBill(customerId, bookId, amountString);
			}
		});

		btnUpdateBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateBill();
			}
		});
		
		btnDeleteBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBill();
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (tbBooksAdded.size() == 0) 
						throw new Exception("There's nothing in bill to delete.");
					
					int result = JOptionPane.showConfirmDialog(frame,"Delete all books in this bill?", 
							"Are you sure?",
				             JOptionPane.YES_NO_OPTION,
				             JOptionPane.QUESTION_MESSAGE);
				    if(result == JOptionPane.YES_OPTION){
				    	clearInforInput();
				    }
				}
				catch (Exception ex) {
					showErrorMessage(ex.getMessage(), "Delete fail");
				}
			}
		});
		
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				payBill();
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
		
		btnCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Categories c = new GUI_Categories();
				c.setLocationRelativeTo(null);
				c.setVisible(true);
			}
		});
		
		btnPublishers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Publishers p = new GUI_Publishers();
				p.setLocationRelativeTo(null);
				p.setVisible(true);
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
		
		btnBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Bills b = new GUI_Bills();
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
		
		btnAuthors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Authors acc = new GUI_Authors();
				acc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				acc.setLocationRelativeTo(null); 
				acc.setVisible(true);
			}
		});
		
		btnStatistic.addActionListener(new ActionListener() {
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
	}
}
