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
import java.text.SimpleDateFormat;
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
import javax.swing.table.DefaultTableModel;
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
import process.Process_Bill;
import javax.swing.JCheckBox;

public class GUI_Pay extends JFrame {

	private JPanel contentPane;
	private JTextField txtIDCus;
	private JTextField txtIDBook;
	private JTextField txtPhone;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtAddress;
	private JTextField txtAuthor;
	private JTextField txtTitle;
	private JTextField txtPublisher;
	private JTextField txtPublished;
	private JTextField txtAmount;
	private JTextField txtPrice;
	private JTable JTableBill;
	private JLabel lbFetchBook;
	private JLabel lbBill;
	private JLabel lbInsertBills;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Vector tbHeader = new Vector();
	private Vector tbContent = new Vector();
	private Vector tbBooksAdded = new Vector();
	private ImageIconHelper imgHelp = new ImageIconHelper();
	private Process_Customer pc = new Process_Customer();
	private Process_Book pb = new Process_Book();
	private Customer ct = new Customer();
	private Book b = new Book();
	private Process_Bill pbill = new Process_Bill();
	
	private static GUI_Pay frame = new GUI_Pay();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
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
		txtIDCus.setText(null);
		txtName.setText(null);
		txtPhone.setText(null);
		txtEmail.setText(null);
		txtAddress.setText(null);
		txtIDBook.setText(null);
		txtTitle.setText(null);
		txtAuthor.setText(null);
		txtPublisher.setText(null);
		txtPublished.setText(null);
		txtPrice.setText(null);
		txtAmount.setText(null);
		tbContent.clear();
		tbBooksAdded.clear();
		ct = new Customer();
		b = new Book();
		dtm.setDataVector(tbContent, tbHeader);
		JTableBill.setModel(dtm);
	}
	
	public void getTotalBill() {
		if (tbContent.size() > 0) {
			double totalBill = 0;
			Vector totalRow = new Vector();
			for (int i=0; i<tbContent.size(); i++) {
				Vector tbRow = (Vector) tbContent.get(i);
				if (tbRow.get(0).equals("Total bill:")) {
					tbContent.remove(i);
					i -= 1;
				}
				else totalBill += (double) tbRow.get(5);
			}
			if (tbContent.size() > 0) {
				totalRow.add(0, "Total bill:");
				for (int i=1; i<5; i++) totalRow.add(i, null);
				totalRow.add(5, totalBill);
				tbContent.add(totalRow);
			}
		}
	}
	
	public void getCusInfor() {
		String ID = txtIDCus.getText();
		ct = pc.getCustomerByID(ID);
		if (ct == null) lbFetchCus.setText("ID not found.");
		else {
			lbFetchCus.setText("");
			txtName.setText(ct.getName());
			txtEmail.setText(ct.getEmail());
			txtPhone.setText(Integer.toString(ct.getPhone()));
			txtAddress.setText(ct.getAddress());
		}
	}
	
	public void getBookInfor() {
		String ID = txtIDBook.getText();
		b = pb.getBookByID(ID);
		if (b == null) lbFetchBook.setText("ID not found.");
		else {
			lbFetchBook.setText("");
			txtTitle.setText(b.getTitle());
			txtAuthor.setText(b.getAuthor());
			txtPublisher.setText(b.getPublisher());
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
		    String strDate = formatter.format(b.getPublishedAt());  
			txtPublished.setText(strDate);
			txtPrice.setText(Double.toString(b.getPrice()));
		}
	}
	
	public void addToBill() {
		if (ct.getId() == null || b.getId() == null) lbBill.setText("Please fetch customer's infor and book's infor.");
		else {
			lbBill.setText(null); 
			int amount = -1;
			try {
				amount = Integer.parseInt(txtAmount.getText());
			} catch (NumberFormatException e) {
				lbBill.setText("Invalid amount. Please enter correct type.");
			}
			if (amount <= 0) lbBill.setText("Invalid amount. Amount must larger than 0.");
			else {
				if (amount > b.getAmount()) lbBill.setText("There are not enough books. Only has " + b.getAmount() + " left.");
				else {
					boolean checkExist = false;
					for (int i=0; i<tbBooksAdded.size(); i++) {
						Book cb = (Book) tbBooksAdded.get(i);
						if (cb.getId().equals(b.getId())) checkExist = true;
					}
					if (checkExist) lbBill.setText("This book has already in the bill.");
					else {
						txtIDCus.setEditable(false); 
						tbBooksAdded.add(b);
						Vector tbRow = new Vector();
						tbRow.add(pbill.getNumberOfBills()+1);
						tbRow.add(ct.getName());
						tbRow.add(b.getTitle());
						tbRow.add(b.getPrice());
						tbRow.add(amount);
						tbRow.add(b.getPrice()*amount);
						tbContent.add(tbRow);
						getTotalBill();
						dtm.setDataVector(tbContent, tbHeader);
						JTableBill.setModel(dtm);
					}
				}
			}
		}
	}
	
	public void updateBill() {
		if (tbBooksAdded.size() == 0) lbBill.setText("Updated fail. There's nothing in bill.");
		else {
			int amount = -1;
			try {
				amount = Integer.parseInt(txtAmount.getText());
			} catch (NumberFormatException e) {
				lbBill.setText("Invalid amount. Please enter correct type.");
			}
			if (amount > b.getAmount()) lbBill.setText("There are not enough books. Only has " + b.getAmount() + " left.");
			else {
				lbBill.setText(null);
				for (int i=0; i<tbBooksAdded.size(); i++) {
					Book cb = (Book) tbBooksAdded.get(i);
					if (cb.getId().equals(b.getId())) {
						Vector tbRow = new Vector();
						tbRow.add(pbill.getNumberOfBills()+1);
						tbRow.add(ct.getName());
						tbRow.add(b.getTitle());
						tbRow.add(b.getPrice());
						tbRow.add(amount);
						tbRow.add(b.getPrice()*amount);
						tbContent.set(i, tbRow);
						dtm.setDataVector(tbContent, tbHeader);
						getTotalBill();
						JTableBill.setModel(dtm);
					}
				}
			}
		}
	}
	
	public void deleteBill() {
		if (tbBooksAdded.size() == 0) {
			lbBill.setText("There's nothing in bill.");
			lbBill.setForeground(Color.red);
		}
		else {
			int result = JOptionPane.showConfirmDialog(frame,"Delete book has ID: "+b.getId()+" in bill?", 
					"Are you sure?",
		             JOptionPane.YES_NO_OPTION,
		             JOptionPane.QUESTION_MESSAGE);
		    if(result == JOptionPane.YES_OPTION){
		    	lbBill.setText(null);
				for (int i=0; i<tbBooksAdded.size(); i++) {
					Book cb = (Book) tbBooksAdded.get(i);
					if (cb.getId().equals(b.getId())) {
						tbBooksAdded.remove(i);
						tbContent.remove(i);
						getTotalBill();
						dtm.setDataVector(tbContent, tbHeader);
						JTableBill.setModel(dtm);
					}
				}
		    }
		}
	}
	
	public void insertBill() {
		if (tbBooksAdded.size() == 0) {
			lbInsertBills.setText("There's nothing in bill.");
			lbInsertBills.setForeground(Color.red);
		}
		else {
			lbInsertBills.setText(null);
			for (int i=0; i<tbBooksAdded.size(); i++) {
				Book cb = (Book) tbBooksAdded.get(i);
				Vector bill = (Vector) tbContent.get(i);
				int idBill = (int) bill.get(0);
				int amount = (int) bill.get(4);
				double total = (double) bill.get(5);
				boolean checkInsert = pbill.insertBill(idBill, ct.getId(), cb.getId(), amount, total);
				boolean checkUpdate = pb.updateAmountOfBook(cb.getId(), cb.getAmount()-amount);
			}
			lbInsertBills.setText("Inserted successfully");
			lbInsertBills.setForeground(Color.blue);
			clearInforInput();
		}
	}

	public GUI_Pay() {
		setTitle("Book Store - Payment");
		ImageIcon logoutIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/logout.png"), 30, 25);
		ImageIcon statisticIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/statistic.png"), 30, 25);
		ImageIcon accountIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/signup.png"), 30, 25);
		ImageIcon booksIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/books.png"), 30, 25);
		ImageIcon customersIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/customers.png"), 30, 25);
		ImageIcon billsIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/bills.png"), 30, 25);
		ImageIcon homeIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/bookstore.png"), 30, 25);
		ImageIcon cusInforIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/cusInfor.png"), 120, 75);
		ImageIcon bookInforIcon = imgHelp.getIcon(GUI_Pay.class.getResource("/icons/bookInfor.png"), 120, 75);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 824, 1030);
		contentPane = new JPanel();
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255)));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBounds(95, 10, 632, 94);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.setContentAreaFilled(false);
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogOut.setFocusPainted(false);
		btnLogOut.setFocusTraversalKeysEnabled(false);
		btnLogOut.setFocusable(false);
		btnLogOut.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogOut.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLogOut.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogOut.setIcon(logoutIcon);
		btnLogOut.setBounds(541, 0, 89, 88);
		panel.add(btnLogOut);
		
		JButton btnStatistic = new JButton("Statistic");
		btnStatistic.setContentAreaFilled(false);
		btnStatistic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnStatistic.setFocusPainted(false);
		btnStatistic.setFocusTraversalKeysEnabled(false);
		btnStatistic.setFocusable(false);
		btnStatistic.setBounds(451, 0, 89, 88);
		panel.add(btnStatistic);
		btnStatistic.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnStatistic.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnStatistic.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnStatistic.setHorizontalTextPosition(SwingConstants.CENTER);
		btnStatistic.setIcon(statisticIcon);
		
		JButton btnCustomers = new JButton("Customers");
		btnCustomers.setContentAreaFilled(false);
		btnCustomers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCustomers.setFocusable(false);
		btnCustomers.setFocusTraversalKeysEnabled(false);
		btnCustomers.setFocusPainted(false);
		btnCustomers.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCustomers.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCustomers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCustomers.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnCustomers.setIcon(customersIcon);
		btnCustomers.setBounds(180, 0, 89, 88);
		panel.add(btnCustomers);
		
		JButton btnBooks = new JButton("Books");
		btnBooks.setContentAreaFilled(false);
		btnBooks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBooks.setFocusPainted(false);
		btnBooks.setFocusTraversalKeysEnabled(false);
		btnBooks.setFocusable(false);
		btnBooks.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBooks.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBooks.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBooks.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnBooks.setIcon(booksIcon);
		btnBooks.setBounds(270, 0, 89, 88);
		panel.add(btnBooks);
		
		JButton btnBills = new JButton("Bills");
		btnBills.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBills.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBills.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBills.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBills.setFocusable(false);
		btnBills.setFocusTraversalKeysEnabled(false);
		btnBills.setFocusPainted(false);
		btnBills.setContentAreaFilled(false);
		btnBills.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnBills.setIcon(billsIcon);
		btnBills.setBounds(90, 0, 89, 88);
		panel.add(btnBills);
		
		JButton btnHome = new JButton("Home");
		btnHome.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnHome.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnHome.setHorizontalTextPosition(SwingConstants.CENTER);
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnHome.setFocusable(false);
		btnHome.setFocusTraversalKeysEnabled(false);
		btnHome.setFocusPainted(false);
		btnHome.setContentAreaFilled(false);
		btnHome.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(192, 192, 192)));
		btnHome.setIcon(homeIcon);
		btnHome.setBounds(0, 0, 89, 88);
		panel.add(btnHome);
		
		JButton btnAccounts = new JButton("Accounts");
		btnAccounts.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAccounts.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAccounts.setToolTipText("");
		btnAccounts.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAccounts.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAccounts.setFocusable(false);
		btnAccounts.setFocusTraversalKeysEnabled(false);
		btnAccounts.setFocusPainted(false);
		btnAccounts.setContentAreaFilled(false);
		btnAccounts.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(192, 192, 192)));
		btnAccounts.setIcon(accountIcon);
		btnAccounts.setBounds(360, 0, 89, 88);
		panel.add(btnAccounts);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_1.setBounds(0, 123, 814, 758);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Customer Information", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 11, 790, 193);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		txtIDCus = new JTextField();
		txtIDCus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtIDCus.setBounds(323, 18, 118, 20);
		panel_2.add(txtIDCus);
		txtIDCus.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(296, 21, 26, 14);
		panel_2.add(lblNewLabel);
		
		JButton btnFetchCus = new JButton("Fetch");
		btnFetchCus.setHorizontalTextPosition(SwingConstants.CENTER);
		btnFetchCus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFetchCus.setBounds(451, 19, 89, 18);
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
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAddress.setBounds(50, 157, 52, 14);
		panel_2.add(lblAddress);
		
		txtAddress = new JTextField();
		txtAddress.setEditable(false);
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAddress.setColumns(10);
		txtAddress.setBounds(108, 156, 189, 20);
		panel_2.add(txtAddress);
		
		JLabel lbCusInfor = new JLabel("");
		lbCusInfor.setBounds(332, 49, 120, 75);
		lbCusInfor.setIcon(cusInforIcon);
		panel_2.add(lbCusInfor);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(596, 151, 78, 20);
		panel_2.add(textField);
		
		JLabel lblRank = new JLabel("Rank");
		lblRank.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRank.setBounds(540, 152, 44, 14);
		panel_2.add(lblRank);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(596, 117, 78, 20);
		panel_2.add(textField_1);
		
		JLabel lblPhone_1_1 = new JLabel("Books purchased");
		lblPhone_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPhone_1_1.setBounds(473, 120, 118, 14);
		panel_2.add(lblPhone_1_1);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setBounds(476, 87, 59, 14);
		panel_2.add(lblBirthday);
		lblBirthday.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		textField_2 = new JTextField();
		textField_2.setBounds(540, 86, 189, 20);
		panel_2.add(textField_2);
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("New check box");
		chckbxNewCheckBox.setBounds(87, 19, 97, 23);
		panel_2.add(chckbxNewCheckBox);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setLayout(null);
		panel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panel_2_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Book Information", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2_1.setBounds(10, 259, 790, 172);
		panel_1.add(panel_2_1);
		
		txtIDBook = new JTextField();
		txtIDBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtIDBook.setColumns(10);
		txtIDBook.setBounds(108, 20, 118, 20);
		panel_2_1.add(txtIDBook);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(81, 23, 26, 14);
		panel_2_1.add(lblNewLabel_1);
		
		JButton btnFetchBook = new JButton("Fetch");
		btnFetchBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnFetchBook.setBounds(236, 21, 89, 18);
		panel_2_1.add(btnFetchBook);
		
		txtAuthor = new JTextField();
		txtAuthor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAuthor.setEditable(false);
		txtAuthor.setColumns(10);
		txtAuthor.setBounds(532, 51, 189, 20);
		panel_2_1.add(txtAuthor);
		
		JLabel lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAuthor.setBounds(483, 52, 44, 14);
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
		lblPublishedAt.setBounds(448, 88, 78, 25);
		panel_2_1.add(lblPublishedAt);
		
		txtPublished = new JTextField();
		txtPublished.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPublished.setEditable(false);
		txtPublished.setColumns(10);
		txtPublished.setBounds(532, 90, 189, 20);
		panel_2_1.add(txtPublished);
		
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
		lblPrice.setBounds(489, 122, 37, 14);
		panel_2_1.add(lblPrice);
		
		txtPrice = new JTextField();
		txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPrice.setEditable(false);
		txtPrice.setColumns(10);
		txtPrice.setBounds(532, 121, 189, 20);
		panel_2_1.add(txtPrice);
		
		JLabel lbBookInfor = new JLabel("");
		lbBookInfor.setHorizontalTextPosition(SwingConstants.CENTER);
		lbBookInfor.setHorizontalAlignment(SwingConstants.CENTER);
		lbBookInfor.setBounds(318, 61, 120, 75);
		lbBookInfor.setIcon(bookInforIcon);
		panel_2_1.add(lbBookInfor);
		
		lbFetchBook = new JLabel("");
		lbFetchBook.setForeground(Color.RED);
		lbFetchBook.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbFetchBook.setBounds(335, 22, 209, 15);
		panel_2_1.add(lbFetchBook);
		
		Button btnAddBill = new Button("Add to bill");
		btnAddBill.setBounds(161, 437, 87, 33);
		panel_1.add(btnAddBill);
		
		Button btnDeleteBill = new Button("Delete from bill");
		btnDeleteBill.setBounds(563, 437, 111, 33);
		panel_1.add(btnDeleteBill);
		
		Button btnUpdateBill = new Button("Update from bill");
		btnUpdateBill.setActionCommand("Update from bill");
		btnUpdateBill.setBounds(355, 437, 111, 33);
		panel_1.add(btnUpdateBill);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 508, 790, 172);
		panel_1.add(scrollPane);
		
		JTableBill = new JTable();
		scrollPane.setViewportView(JTableBill);
		
		JLabel lblNewLabel_2 = new JLabel("List books of bill:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(20, 481, 160, 14);
		panel_1.add(lblNewLabel_2);
		
		Button btnPay = new Button("Pay bill");
		btnPay.setBounds(274, 693, 87, 33);
		panel_1.add(btnPay);
		
		Button btnDelete = new Button("Delete bill");
		btnDelete.setBounds(417, 693, 111, 33);
		panel_1.add(btnDelete);
		
		JLabel lblNewLabel_2_1 = new JLabel("Sell Books Management");
		lblNewLabel_2_1.setBounds(10, 105, 177, 14);
		contentPane.add(lblNewLabel_2_1);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		tbHeader.add("ID Bill");
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
		
		JLabel lblNewLabel_3 = new JLabel("Them phan discount");
		lblNewLabel_3.setBounds(539, 483, 174, 14);
		panel_1.add(lblNewLabel_3);
		
		btnFetchCus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getCusInfor();
			}
		});
		
		btnFetchBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getBookInfor();
			}
		});
		
		btnAddBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addToBill();
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
				int result = JOptionPane.showConfirmDialog(frame,"Delete all products in bill?", 
						"Are you sure?",
			             JOptionPane.YES_NO_OPTION,
			             JOptionPane.QUESTION_MESSAGE);
			    if(result == JOptionPane.YES_OPTION){
			    	clearInforInput();
			    }
			}
		});
		
		btnPay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertBill();
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
		
		btnAccounts.addActionListener(new ActionListener() {
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
