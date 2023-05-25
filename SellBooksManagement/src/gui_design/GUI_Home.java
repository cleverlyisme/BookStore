package gui_design;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class GUI_Home extends JFrame {

	private JPanel contentPane;
	private ImageIconHelper imgHelp = new ImageIconHelper();
	
	private static GUI_Home frame = new GUI_Home();

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

	public GUI_Home() {
		setTitle("Book Store - Home Page");
		ImageIcon logoutIcon = imgHelp.getIcon(GUI_Home.class.getResource("/icons/logout.png"), 30, 25);
		ImageIcon statisticIcon = imgHelp.getIcon(GUI_Home.class.getResource("/icons/statistic.png"), 30, 25);
		ImageIcon booksIcon = imgHelp.getIcon(GUI_Home.class.getResource("/icons/books.png"), 30, 25);
		ImageIcon customersIcon = imgHelp.getIcon(GUI_Home.class.getResource("/icons/customers.png"), 30, 25);
		ImageIcon billsIcon = imgHelp.getIcon(GUI_Home.class.getResource("/icons/bills.png"), 30, 25);
		ImageIcon authorsIcon = imgHelp.getIcon(GUI_Home.class.getResource("/icons/authors.png"), 30, 25);
		ImageIcon categoriesIcon = imgHelp.getIcon(GUI_Home.class.getResource("/icons/categories.png"), 30, 25);
		ImageIcon publishersIcon = imgHelp.getIcon(GUI_Home.class.getResource("/icons/publishers.png"), 30, 25);
		ImageIcon payIcon = imgHelp.getIcon(GUI_Home.class.getResource("/icons/sellbooks.png"), 30, 25);
		ImageIcon logoIcon = imgHelp.getIcon(GUI_Home.class.getResource("/icons/bookstore.png"), 60, 55);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnBills = new JButton("Bills");
		btnBills.setBounds(261, 95, 89, 88);
		contentPane.add(btnBills);
		btnBills.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBills.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBills.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBills.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBills.setFocusable(false);
		btnBills.setFocusTraversalKeysEnabled(false);
		btnBills.setFocusPainted(false);
		btnBills.setContentAreaFilled(false);
		btnBills.setBorder(new MatteBorder(0, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnBills.setIcon(billsIcon);
		
		JButton btnCustomers = new JButton("Customers");
		btnCustomers.setBounds(386, 95, 89, 88);
		contentPane.add(btnCustomers);
		btnCustomers.setContentAreaFilled(false);
		btnCustomers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCustomers.setFocusable(false);
		btnCustomers.setFocusTraversalKeysEnabled(false);
		btnCustomers.setFocusPainted(false);
		btnCustomers.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCustomers.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCustomers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCustomers.setBorder(new MatteBorder(0, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnCustomers.setIcon(customersIcon);
		
		JButton btnBooks = new JButton("Books");
		btnBooks.setBounds(136, 210, 89, 88);
		contentPane.add(btnBooks);
		btnBooks.setContentAreaFilled(false);
		btnBooks.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBooks.setFocusPainted(false);
		btnBooks.setFocusTraversalKeysEnabled(false);
		btnBooks.setFocusable(false);
		btnBooks.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBooks.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBooks.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBooks.setBorder(new MatteBorder(0, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnBooks.setIcon(booksIcon);
		
		JButton btnBillStatistic = new JButton("Bill Statistic");
		btnBillStatistic.setBounds(261, 210, 89, 88);
		contentPane.add(btnBillStatistic);
		btnBillStatistic.setContentAreaFilled(false);
		btnBillStatistic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBillStatistic.setFocusPainted(false);
		btnBillStatistic.setFocusTraversalKeysEnabled(false);
		btnBillStatistic.setFocusable(false);
		btnBillStatistic.setBorder(new MatteBorder(0, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnBillStatistic.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBillStatistic.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBillStatistic.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBillStatistic.setIcon(statisticIcon);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.setBounds(261, 442, 89, 88);
		contentPane.add(btnLogOut);
		btnLogOut.setContentAreaFilled(false);
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogOut.setFocusPainted(false);
		btnLogOut.setFocusTraversalKeysEnabled(false);
		btnLogOut.setFocusable(false);
		btnLogOut.setBorder(new MatteBorder(0, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogOut.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnLogOut.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLogOut.setIcon(logoutIcon);
		
		JButton btnAuthors = new JButton("Authors");
		btnAuthors.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAuthors.setToolTipText("");
		btnAuthors.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAuthors.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAuthors.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnAuthors.setFocusable(false);
		btnAuthors.setFocusTraversalKeysEnabled(false);
		btnAuthors.setFocusPainted(false);
		btnAuthors.setContentAreaFilled(false);
		btnAuthors.setBorder(new MatteBorder(0, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnAuthors.setIcon(authorsIcon);
		btnAuthors.setBounds(386, 210, 89, 88);
		contentPane.add(btnAuthors);
		
		JButton btnPayment = new JButton("Payment");
		btnPayment.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contentPane.add(btnPayment);
		btnPayment.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnPayment.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPayment.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPayment.setFocusable(false);
		btnPayment.setFocusTraversalKeysEnabled(false);
		btnPayment.setFocusPainted(false);
		btnPayment.setContentAreaFilled(false);
		btnPayment.setBorder(new MatteBorder(0, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnPayment.setIcon(payIcon);
		btnPayment.setBounds(136, 95, 89, 88);
		
		JButton btnCategories = new JButton("Categories");
		btnCategories.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCategories.setVerticalTextPosition(SwingConstants.BOTTOM);
		contentPane.add(btnCategories);
		btnCategories.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCategories.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCategories.setFocusable(false);
		btnCategories.setFocusTraversalKeysEnabled(false);
		btnCategories.setFocusPainted(false);
		btnCategories.setContentAreaFilled(false);
		btnCategories.setBorder(new MatteBorder(0, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnCategories.setIcon(categoriesIcon);
		btnCategories.setBounds(136, 330, 89, 88);
		
		JButton btnPublishers = new JButton("Publishers");
		btnPublishers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPublishers.setVerticalTextPosition(SwingConstants.BOTTOM);
		contentPane.add(btnPublishers);
		btnPublishers.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPublishers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPublishers.setFocusTraversalKeysEnabled(false);
		btnPublishers.setFocusPainted(false);
		btnPublishers.setContentAreaFilled(false);
		btnPublishers.setBorder(new MatteBorder(0, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnPublishers.setIcon(publishersIcon);
		btnPublishers.setBounds(261, 330, 89, 88);
		
		JLabel lblBookstore = new JLabel("Bookstore Management");
		lblBookstore.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookstore.setForeground(new Color(250, 128, 114));
		lblBookstore.setFont(new Font(".VnVogue", Font.BOLD, 25));
		lblBookstore.setBounds(121, 69, 370, 25);
		contentPane.add(lblBookstore);
		
		JLabel lbLogo = new JLabel("");
		lbLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lbLogo.setIcon(logoIcon);
		lbLogo.setBounds(270, 11, 66, 58);
		contentPane.add(lbLogo);
		
		JButton btnBookstatistic = new JButton("Book Statistic");
		btnBookstatistic.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnBookstatistic.setHorizontalTextPosition(SwingConstants.CENTER);
		btnBookstatistic.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBookstatistic.setFocusTraversalKeysEnabled(false);
		btnBookstatistic.setFocusPainted(false);
		btnBookstatistic.setContentAreaFilled(false);
		btnBookstatistic.setBorder(new MatteBorder(0, 0, 1, 1, (Color) Color.LIGHT_GRAY));
		btnBookstatistic.setBounds(386, 330, 89, 88);
		contentPane.add(btnBookstatistic);
		
		btnPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Pay sb = new GUI_Pay();
				sb.setLocationRelativeTo(null); 
				sb.setVisible(true);
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
		
		btnAuthors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUI_Authors acc = new GUI_Authors();
				acc.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				acc.setLocationRelativeTo(null); 
				acc.setVisible(true);
			}
		});
		
		btnCategories.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Categories sb = new GUI_Categories();
				sb.setLocationRelativeTo(null); 
				sb.setVisible(true);
			}
		});
		
		btnPublishers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_Publishers sb = new GUI_Publishers();
				sb.setLocationRelativeTo(null); 
				sb.setVisible(true);
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
		
		btnBillStatistic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GUI_BillStatistic s = new GUI_BillStatistic();
				s.setLocationRelativeTo(null); 
				s.setVisible(true);
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
