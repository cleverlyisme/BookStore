package gui_design;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.sql.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class GUI_Customers extends JFrame {

	private JPanel contentPane;
	private JTable tbCustomers;
	private JTextField txtPhone;
	private JTextField txtFind;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Vector tbContent = new Vector();
	private Vector tbHeader = new Vector();
	private JLabel lbCheck;
	private JTextField txtEmail;
	private JTextField txtName;
	private JFormattedTextField txtBirth;
	
	private Customer c = new Customer();
	private ArrayList<Customer> lscus;
	private ArrayList<String> errors = new ArrayList<>();
	private Process_Customer pc = new Process_Customer();
	private ImageIconHelper imgHelp = new ImageIconHelper();
	
	private MaskFormatter mf = null;
	
	private static GUI_Customers frame = new GUI_Customers();

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
	
	public void clearInforInput() {
    	txtName.setText(null);
    	txtPhone.setText(null);
    	txtEmail.setText(null);
    	txtBirth.setText(null);
    	txtFind.setText(null);
		c = new Customer();
		errors = new ArrayList<>();
		getListCustomers();
	}
	
	public void getListCustomers() {
		tbContent = new Vector();
		lscus = pc.getListCustomer();
		for (int i=0; i<lscus.size(); i++) {
			Customer cus = lscus.get(i);
			Vector tbRow = new Vector();
			tbRow.add(cus.getId());
			tbRow.add(cus.getName());
			tbRow.add(cus.getPhone());
			tbRow.add(cus.getEmail());
			tbRow.add(cus.getBirth());
			tbRow.add(cus.getBookPurhased());
			tbRow.add(cus.getRank());
			tbContent.add(tbRow);
		}
		dtm.setDataVector(tbContent, tbHeader);
		tbCustomers.setModel(dtm);
	}
	
	public void getListCustomersByUsername(String name) {
			tbContent = new Vector();
			lscus = pc.getListCustomerByname(name);
			
			if (lscus.size() == 0) 
				showErrorMessage("Can't find any customer with name '"+name+"'", "Error");
				
			for (int i=0; i<lscus.size(); i++) {
				Customer cus = lscus.get(i);
				Vector tbRow = new Vector();
				tbRow.add(cus.getId());
				tbRow.add(cus.getName());
				tbRow.add(cus.getPhone());
				tbRow.add(cus.getEmail());
				tbRow.add(cus.getBirth());
				tbRow.add(cus.getBookPurhased());
				tbRow.add(cus.getRank());
				tbContent.add(tbRow);
			}
			dtm.setDataVector(tbContent, tbHeader);
			tbCustomers.setModel(dtm);
	}
	
	public void checkDate(String date) {
		int y = Integer.parseInt(date.split("-")[0]);
		int m = Integer.parseInt(date.split("-")[1]);
		int d = Integer.parseInt(date.split("-")[2]);
		
		if (m < 1 || m > 12 || d < 1 || d > 31) {
			if (m < 1 || m > 12) errors.add("Invalid month.");
			if (d < 1 || d > 31) errors.add("Invalid day.");
			txtBirth.setValue(null);
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
				txtBirth.setValue(null);
			}
		}
	}
	
	public boolean isValidEmail(String email) {
        String regex = "[A-Za-z0-9-_.]+@[A-Za-z0-9-_]+(?:\\.[A-Za-z0-9]+)+";

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
	
	public void checkInputValues(String name, String phone, String email, String birth) {
		String pattern = "(?i)^(03|05|07|08).*";
		
		if (name.equals("") || phone.equals("") || email.equals("")) {					
			errors.add("Input fields can't be blank.");	
		}
		
		if (name.length() < 3 || name.length() > 25) errors.add("Invalid name's length.");	
		
		if (!isValidEmail(email)) errors.add("Invalid email.");
		
		if (birth.indexOf('#') != -1) errors.add("Invalid birthday.");
		
		if (!phone.matches(pattern)) 
			errors.add("Phone must start at 03, 05, 07, 08, 09."); 
		
		try {
			int checkPhone = Integer.parseInt(phone);
		}
		catch (Exception ex) {
			errors.add("Phone must be numbers.");
		}
								
		if (phone.length() != 10) errors.add("Phone's length must be 10.");
		
		checkDate(birth);
	}

	public GUI_Customers() {
		setTitle("Customers Management");
		
		ImageIcon searchIcon = imgHelp.getIcon(GUI_Customers.class.getResource("/icons/search.png"), 18, 18);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 505);
		contentPane = new JPanel();
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255)));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
			mf = new MaskFormatter("####-##-##");
			mf.setPlaceholderCharacter('#');
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 612, 202);
		contentPane.add(scrollPane);
		
		tbCustomers = new JTable();
		scrollPane.setViewportView(tbCustomers);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("List Customer:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(41, 11, 111, 19);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setDefaultCapable(false);
		btnAdd.setBounds(94, 391, 89, 24);
		contentPane.add(btnAdd);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setDefaultCapable(false);
		btnUpdate.setBounds(283, 391, 89, 24);
		contentPane.add(btnUpdate);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setDefaultCapable(false);
		btnDelete.setBounds(467, 391, 89, 24);
		contentPane.add(btnDelete);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblPassword = new JLabel("Phone");
		lblPassword.setBounds(340, 283, 50, 19);
		contentPane.add(lblPassword);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		txtPhone = new JTextField();
		txtPhone.setBounds(410, 282, 172, 21);
		contentPane.add(txtPhone);
		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPhone.setColumns(10);
		
		txtFind = new JTextField();
		txtFind.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtFind.setColumns(10);
		txtFind.setBounds(401, 11, 172, 21);
		contentPane.add(txtFind);
		
		JLabel lblFindByUsername = new JLabel("Find by name:");
		lblFindByUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFindByUsername.setBounds(305, 11, 94, 19);
		contentPane.add(lblFindByUsername);
		
		lbCheck = new JLabel("");
		lbCheck.setOpaque(true);
		lbCheck.setHorizontalAlignment(SwingConstants.CENTER);
		lbCheck.setHorizontalTextPosition(SwingConstants.CENTER);
		lbCheck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbCheck.setBounds(10, 467, 612, 19);
		contentPane.add(lbCheck);
		
		JLabel lbSearchIcon = new JLabel("");
		lbSearchIcon.setOpaque(true);
		lbSearchIcon.setIcon(searchIcon);
		lbSearchIcon.setBounds(579, 11, 20, 20);
		contentPane.add(lbSearchIcon);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtEmail.setColumns(10);
		txtEmail.setBounds(94, 338, 172, 21);
		contentPane.add(txtEmail);
		
		JLabel lblPassword_1 = new JLabel("Email");
		lblPassword_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword_1.setBounds(41, 339, 43, 19);
		contentPane.add(lblPassword_1);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblName.setBounds(41, 284, 43, 19);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setColumns(10);
		txtName.setBounds(94, 283, 172, 21);
		contentPane.add(txtName);
		
		txtBirth = new JFormattedTextField(mf);
		txtBirth.setBounds(410, 338, 172, 21);
		contentPane.add(txtBirth);
		
		JLabel lblBirthday = new JLabel("Birthday");
		lblBirthday.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBirthday.setBounds(334, 339, 66, 19);
		contentPane.add(lblBirthday);
		
		tbHeader.add("ID");
		tbHeader.add("Name");
		tbHeader.add("Phone");
		tbHeader.add("Email");
		tbHeader.add("Birthday");
		tbHeader.add("Books Purchased");
		tbHeader.add("Rank");
		getListCustomers();
		
		tbCustomers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		            int selectedRow = tbCustomers.getSelectedRow();
		            if (selectedRow >= 0) {
		                Vector vt = (Vector) tbContent.get(selectedRow);
		                txtName.setText(vt.get(1).toString());
		                txtPhone.setText(vt.get(2).toString());
		                txtEmail.setText(vt.get(3).toString());
		                txtBirth.setText(vt.get(4).toString());
		                c = new Customer((int) vt.get(0), (String) vt.get(1), (String) vt.get(2), 
		                        (String) vt.get(3), (Date) vt.get(4), (int) vt.get(5), (String) vt.get(6));
		            }
		        }
		    }
		});
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					if (btnAdd.getText().equals("Add")) {
						clearInforInput();
						btnAdd.setText("Save");
						btnUpdate.setEnabled(false);
						btnDelete.setEnabled(false);
					}
					else {
						String name = txtName.getText();
						String phone = txtPhone.getText();
						String email = txtEmail.getText();
						String birth = txtBirth.getText();
						
						checkInputValues(name, phone, email, birth);
						
						Date birthday = Date.valueOf(birth);
													
						pc.insertCustomer(name, phone, email, birthday);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						btnAdd.setText("Add");
						showSuccessMessage("Added customer successfully", "Success");
						clearInforInput();
					}
				}
				catch (SQLException ex) {
					int result = JOptionPane.showConfirmDialog(frame,"Add failed. "
				+ex.getMessage()+" Try again?", 
							"Are you sure?",
				             JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE);
				    if(result == JOptionPane.NO_OPTION){
				    	btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						btnAdd.setText("Add");
						clearInforInput();
				    } else txtBirth.setValue(null);
					errors = new ArrayList<>();
				}
				catch (Exception ex) {
					int result = JOptionPane.showConfirmDialog(frame,ex.getMessage()+" Try again?", 
							"Added fail",
				             JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE);
				    if(result == JOptionPane.NO_OPTION){
				    	btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						btnAdd.setText("Add");
						clearInforInput();
				    } else txtBirth.setValue(null);
				}
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (c.getId() == -1) {
						errors.add("Please select customer you want to update");
						throw new Exception();
					}
		
					String name = txtName.getText();
					String phone = txtPhone.getText();
					String email = txtEmail.getText();
					String birth = txtBirth.getText();
					
					checkInputValues(name, phone, email, birth);
					
					Date birthday = Date.valueOf(birth);
												
					pc.updateCustomer(c.getId(), name, phone, email, birthday);
					showSuccessMessage("Updated customer successfully", "Success");
					clearInforInput();
				}
				catch (Exception ex) {
					showErrorMessage(ex.getMessage(), "Updated fail");
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (c.getId() == -1) 
						throw new Exception("Please select customer you want to delete");
					else {
						int result = JOptionPane.showConfirmDialog(frame,"Delete this customer?", 
								"Are you sure?",
					             JOptionPane.YES_NO_OPTION,
					             JOptionPane.QUESTION_MESSAGE);
					    if(result == JOptionPane.YES_OPTION){
							pc.deleteCustomer(c.getId());
							showSuccessMessage("Deleted successfully", "Success");
							clearInforInput();
					    }
					}
				}
				catch (Exception ex) {
					showErrorMessage(ex.getMessage(), "Deleted fail");
				}
			}
		});
		
		txtFind.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (txtFind.getText().equals("")) getListCustomers();
				else getListCustomersByUsername(txtFind.getText());
			}
		});
	}
}
