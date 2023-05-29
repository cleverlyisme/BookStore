package gui_design;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

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
import process.Process_Publisher;
import process.Publisher;
import process.Book;
import process.Category;
import process.Process_Book;
import process.Process_Category;
import process.Author;
import process.Bill;
import process.Process_Bill;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GUI_Books extends JFrame {

	private JPanel contentPane;
	private JTable tbBooks;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Vector tbContent = new Vector();
	private Vector tbHeader = new Vector();
	private JLabel lbCheck;
	private JTextField txtTitle;
	private JFormattedTextField txtImport;
	private JComboBox cbCategory;
	private JComboBox cbAuthor;
	private JComboBox cbPublisher;
	private JComboBox cbFindCategory;
	private ImageIconHelper imgHelp = new ImageIconHelper();
	
	private ArrayList<Book> lsbook;
	private ArrayList<Author> lsau;
	private ArrayList<Category> lsca;
	private ArrayList<Publisher> lspu;
	private Process_Book pb = new Process_Book();
	private Process_Author pa = new Process_Author();
	private Process_Category pc = new Process_Category();
	private Process_Publisher pp = new Process_Publisher();
	private Book bo = new Book();
	private Author au = new Author();
	private Category ca = new Category();
	private Publisher pu = new Publisher();
	private ArrayList<String> errors = new ArrayList<>();
	
	private JTextField txtFindTitle;
	private JLabel lblName;
	private JTextField txtPrice;
	private JTextField txtQuantity;
	
	private MaskFormatter mf = null;
	private JTextField txtFindAuthor;
	
	private static GUI_Books frame = new GUI_Books();

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
		txtTitle.setText(null);
		txtPrice.setText(null);
		txtImport.setValue(null);
		txtQuantity.setText(null);
		bo = new Book();
		au = new Author();
		ca = new Category();
		pu = new Publisher();
		getListBooks();
	}
	
	public void checkDate(String date) {
		int y = Integer.parseInt(date.split("-")[0]);
		int m = Integer.parseInt(date.split("-")[1]);
		int d = Integer.parseInt(date.split("-")[2]);
		
		if (m < 1 || m > 12 || d < 1 || d > 31) {
			if (m < 1 || m > 12) errors.add("Invalid month.");
			if (d < 1 || d > 31) errors.add("Invalid day.");
			txtImport.setValue(null);
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
				txtImport.setValue(null);
			}
		}
	}
	
	public void loadDataComboBox() {
		lsau = pa.getListAuthors();
		lsca = pc.getListCategories();
		lspu = pp.getListPublishers();
		
		cbFindCategory.addItem("All");
		
		for (int i=0; i<lsau.size(); i++) 
			cbAuthor.addItem(lsau.get(i).getName());
		
		for (int i=0; i<lsca.size(); i++) {
			cbCategory.addItem(lsca.get(i).getName());
			cbFindCategory.addItem(lsca.get(i).getName());
		}
		
		for (int i=0; i<lsau.size(); i++) 
			cbPublisher.addItem(lspu.get(i).getName());
	}
	
	public void getListBooks() {
		tbContent = new Vector();
		lsbook = pb.getListBook();
		for (int i=0; i<lsbook.size(); i++) {
			Book b = lsbook.get(i);
			Vector tbRow = new Vector();
			tbRow.add(b.getId());
			tbRow.add(b.getTitle());
			tbRow.add(b.getAuthor());
			tbRow.add(b.getCategory());
			tbRow.add(b.getPublisher());
			tbRow.add(b.getImportDay());
			tbRow.add(b.getPrice());
			tbRow.add(b.getQuantity());
			tbContent.add(tbRow);
		}
		dtm.setDataVector(tbContent, tbHeader);
		tbBooks.setModel(dtm);
	}
	
	public void getListBooksBy() {
		tbContent = new Vector();

		String title = txtFindTitle.getText().toString();
		String author = txtFindAuthor.getText().toString();
		String category = cbFindCategory.getSelectedItem().toString().equals("All") ? "" : cbFindCategory.getSelectedItem().toString();
		
		lsbook = pb.getListBooksBy(title, author, category);
		
		if (lsbook.size() == 0) 
			showErrorMessage("Can't find any book with title: "+title+", with author: "+author+" and category: "
		+cbFindCategory.getSelectedItem().toString(), "Error");
		
		for (int i=0; i<lsbook.size(); i++) {
			Book b = lsbook.get(i);
			Vector tbRow = new Vector();
			tbRow.add(b.getId());
			tbRow.add(b.getTitle());
			tbRow.add(b.getAuthor());
			tbRow.add(b.getCategory());
			tbRow.add(b.getPublisher());
			tbRow.add(b.getImportDay());
			tbRow.add(b.getPrice());
			tbRow.add(b.getQuantity());
			tbContent.add(tbRow);
		}
		dtm.setDataVector(tbContent, tbHeader);
		tbBooks.setModel(dtm);
	}
	
	public void checkInputValues(String title, String author, String category, 
			String publisher, String importDay, String price, String quantity) {
//		if (title.equals("") || price.equals("") || quantity.equals("")) {					
//			errors.add("Input fields can't be blank.");	
//			throw new Exception();
//		}
//		
//		if (importDay.indexOf('#') != -1) errors.add("Import day is not full filled.");
//		
//		double checkPrice=0; int checkQuantity=0;
//		try {
//			checkPrice = Double.parseDouble(price);
//			
//			if (checkPrice < 0) 
//				throw new Exception("Price must > 0.");
//		} 
//		catch (NumberFormatException ex) {
//			errors.add("Price must be double type.");
//		}
//		catch (Exception ex) {
//			errors.add(ex.getMessage());
//		};
//		
//		try {
//			checkQuantity = Integer.parseInt(quantity);
//			
//			if (checkQuantity > 100 || checkQuantity < 0) 
//				throw new Exception("Quantity must > 0 & < 100.");
//		} 
//		catch (NumberFormatException ex) {
//			errors.add("Quantity must be int type.");
//		}
//		catch (Exception ex) {
//			errors.add(ex.getMessage());
//		};
//														
//		checkDate(importDay);
	}

	public GUI_Books() {
		setTitle("Books Management");
		
		ImageIcon searchIcon = imgHelp.getIcon(GUI_Books.class.getResource("/icons/search.png"), 18, 18);
		
		try {
			mf = new MaskFormatter("####-##-##");
			mf.setPlaceholderCharacter('#');
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255)));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 52, 814, 191);
		contentPane.add(scrollPane);
		
		tbBooks = new JTable();
		scrollPane.setViewportView(tbBooks);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("List Books:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(41, 20, 89, 19);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setDefaultCapable(false);
		btnAdd.setBounds(213, 472, 89, 24);
		contentPane.add(btnAdd);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setDefaultCapable(false);
		btnUpdate.setBounds(402, 472, 89, 24);
		contentPane.add(btnUpdate);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setDefaultCapable(false);
		btnDelete.setBounds(586, 472, 89, 24);
		contentPane.add(btnDelete);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblPassword = new JLabel("Author");
		lblPassword.setBounds(514, 284, 50, 19);
		contentPane.add(lblPassword);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblFindByUsername = new JLabel("Find by:");
		lblFindByUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFindByUsername.setBounds(217, 21, 55, 19);
		contentPane.add(lblFindByUsername);
		
		lbCheck = new JLabel("");
		lbCheck.setOpaque(true);
		lbCheck.setHorizontalAlignment(SwingConstants.CENTER);
		lbCheck.setHorizontalTextPosition(SwingConstants.CENTER);
		lbCheck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbCheck.setBounds(10, 521, 612, 19);
		contentPane.add(lbCheck);
		
		JLabel lblPassword_1 = new JLabel("Publisher");
		lblPassword_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword_1.setBounds(493, 327, 71, 19);
		contentPane.add(lblPassword_1);
		
		lblName = new JLabel("Title");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblName.setBounds(75, 285, 34, 19);
		contentPane.add(lblName);
		
		txtTitle = new JTextField();
		txtTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtTitle.setColumns(10);
		txtTitle.setBounds(121, 284, 198, 21);
		contentPane.add(txtTitle);
		
		JLabel lblAddress = new JLabel("Import day");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAddress.setBounds(475, 372, 93, 19);
		contentPane.add(lblAddress);
		
		JLabel lblName_1 = new JLabel("Category");
		lblName_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName_1.setBounds(620, 21, 60, 19);
		contentPane.add(lblName_1);
		
		txtFindTitle = new JTextField();
		txtFindTitle.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtFindTitle.setColumns(10);
		txtFindTitle.setBounds(315, 20, 111, 21);
		contentPane.add(txtFindTitle);
		
		JLabel lblName_1_1 = new JLabel("Title");
		lblName_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName_1_1.setBounds(283, 21, 27, 19);
		contentPane.add(lblName_1_1);
		
		txtPrice = new JTextField();
		txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtPrice.setColumns(10);
		txtPrice.setBounds(121, 372, 198, 21);
		contentPane.add(txtPrice);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrice.setBounds(68, 373, 43, 19);
		contentPane.add(lblPrice);
		
		JLabel lblPassword_1_1 = new JLabel("Quantity");
		lblPassword_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword_1_1.setBounds(46, 419, 71, 19);
		contentPane.add(lblPassword_1_1);
		
		txtQuantity = new JTextField();
		txtQuantity.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(121, 418, 51, 21);
		contentPane.add(txtQuantity);
		
		txtImport = new JFormattedTextField(mf);
		txtImport.setBounds(574, 370, 198, 21);
		contentPane.add(txtImport);
		
		JLabel lblPassword_1_2 = new JLabel("Category");
		lblPassword_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword_1_2.setBounds(41, 326, 71, 19);
		contentPane.add(lblPassword_1_2);
		
		cbCategory = new JComboBox();
		cbCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cbCategory.setBounds(121, 325, 198, 22);
		contentPane.add(cbCategory);
		
		cbFindCategory = new JComboBox();
		cbFindCategory.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cbFindCategory.setBounds(685, 20, 121, 22);
		contentPane.add(cbFindCategory);
		
		JLabel lbSearchIcon_1 = new JLabel("");
		lbSearchIcon_1.setOpaque(true);
		lbSearchIcon_1.setBounds(231, 11, 20, 20);
		contentPane.add(lbSearchIcon_1);
		
		txtFindAuthor = new JTextField();
		txtFindAuthor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtFindAuthor.setColumns(10);
		txtFindAuthor.setBounds(494, 20, 111, 21);
		contentPane.add(txtFindAuthor);
		
		JLabel lblName_1_1_1 = new JLabel("Author");
		lblName_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName_1_1_1.setBounds(448, 21, 50, 19);
		contentPane.add(lblName_1_1_1);
		
		cbAuthor = new JComboBox();
		cbAuthor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cbAuthor.setBounds(574, 281, 198, 22);
		contentPane.add(cbAuthor);
		
		cbPublisher = new JComboBox();
		cbPublisher.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cbPublisher.setBounds(574, 327, 198, 22);
		contentPane.add(cbPublisher);
		
		tbHeader.add("ID");
		tbHeader.add("Title");
		tbHeader.add("Author");
		tbHeader.add("Category");
		tbHeader.add("Publisher");
		tbHeader.add("Import day");
		tbHeader.add("Price");
		tbHeader.add("Amount");
		loadDataComboBox();
		getListBooks();
		
		tbBooks.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		            int selectedRow = tbBooks.getSelectedRow();
		            if (selectedRow >= 0) {
		                Vector vt = (Vector) tbContent.get(selectedRow);
		                txtTitle.setText(vt.get(1).toString());
		                cbAuthor.setSelectedItem(vt.get(2).toString());
		                cbCategory.setSelectedItem(vt.get(3).toString());
		                cbPublisher.setSelectedItem(vt.get(4).toString());
		                txtImport.setText(vt.get(5).toString());
		                txtPrice.setText(vt.get(6).toString());
		                txtQuantity.setText(vt.get(7).toString());
		                bo = new Book((int) vt.get(0), (String) vt.get(1), (String) vt.get(3), (String) vt.get(2), 
		                		(String) vt.get(4), (Date) vt.get(5), (Double) vt.get(6), (int) vt.get(7));
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
					} else {
						String title = txtTitle.getText();
						String author = cbAuthor.getSelectedItem().toString();
						String category = cbCategory.getSelectedItem().toString();
						String publisher = cbPublisher.getSelectedItem().toString();
						String importDay = txtImport.getText();
						String priceStr = txtPrice.getText();
						String quantityStr = txtQuantity.getText();

						checkInputValues(title, author, category, publisher, importDay, priceStr, quantityStr);
						
						int categoryId = pc.getCategoryByName(category).getId();
						int authorId = pa.getAuthorByName(author).getId();
						int publisherId = pp.getPublisherByName(publisher).getId();
						Date date = Date.valueOf(importDay);
						int price = Integer.parseInt(priceStr);
						int quantity = Integer.parseInt(quantityStr);

						pb.insertBook(title, authorId, categoryId, publisherId, date, price, quantity);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						btnAdd.setText("Add");
						showSuccessMessage("Added book successfully", "Success");
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
				    }
					errors = new ArrayList<>();
				}
				catch (Exception ex) {
					int result = JOptionPane.showConfirmDialog(frame,"Add failed. "
				+String.join("\n", errors)+" Try again?", 
							"Are you sure?",
				             JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE);
				    if(result == JOptionPane.NO_OPTION){
				    	btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						btnAdd.setText("Add");
						clearInforInput();
				    }
					errors = new ArrayList<>();
				}
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (bo.getId() == -1) {
						errors.add("Please select book you want to update");
						throw new Exception();
					}
		
					String title = txtTitle.getText();
					String author = cbAuthor.getSelectedItem().toString();
					String category = cbCategory.getSelectedItem().toString();
					String publisher = cbPublisher.getSelectedItem().toString();
					String importDay = txtImport.getText();
					String priceStr = txtPrice.getText();
					String quantityStr = txtQuantity.getText();

					checkInputValues(title, author, category, publisher, importDay, priceStr, quantityStr);
					
					int categoryId = pc.getCategoryByName(category).getId();
					int authorId = pa.getAuthorByName(author).getId();
					int publisherId = pp.getPublisherByName(publisher).getId();
					Date date = Date.valueOf(importDay);
					int price = Integer.parseInt(priceStr);
					int quantity = Integer.parseInt(quantityStr);
												
					pb.updateBook(bo.getId(), title, authorId, categoryId, publisherId, date, price, quantity);
					showSuccessMessage("Updated book successfully", "Success");
					clearInforInput();
				}
				catch (SQLException ex) {
					showErrorMessage(ex.getMessage(), "Updated fail");
					errors = new ArrayList<>();
				}
				catch (Exception ex) {
					showErrorMessage(String.join("\n", errors), "Updated fail");
					errors = new ArrayList<>();
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (bo.getId() == -1) 
						throw new Exception("Please select book you want to delete");
					else {
						int result = JOptionPane.showConfirmDialog(frame,"Delete this book?", 
								"Are you sure?",
					             JOptionPane.YES_NO_OPTION,
					             JOptionPane.QUESTION_MESSAGE);
					    if(result == JOptionPane.YES_OPTION){
							pb.deleteBook(bo.getId());
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
		
		cbFindCategory.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				 if (e.getStateChange() == ItemEvent.SELECTED) 
			            getListBooksBy();
			}
		});
		
		txtFindTitle.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				getListBooksBy();
			}
		});
		
		txtFindAuthor.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				getListBooksBy();
			}
		});
	}
}
