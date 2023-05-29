package gui_design;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EtchedBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.zip.DataFormatException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import process.Author;
import process.Bill;
import process.Process_Author;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.CaretEvent;

public class GUI_Authors extends JFrame {

	private JPanel contentPane;
	private ImageIconHelper imgHelp = new ImageIconHelper();
	private JTextField txtName;
	private JTextField txtAge;
	private JTable JTableAu;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Vector tbContent = new Vector();
	private Vector tbHeader = new Vector();
	private Process_Author pa = new Process_Author();
	private Author au = new Author();
	private ArrayList<String> errors = new ArrayList<>();
	private ArrayList<Author> lau = new ArrayList<>();
	private JTextField txtFind;
	private JLabel lbCheck;
	
	private static GUI_Authors frame = new GUI_Authors();

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
	
	public void getListAuthors() {
		tbContent = new Vector();
		lau = pa.getListAuthors();
		for (int i=0; i<lau.size(); i++) {
			Author a = lau.get(i);
			Vector tbRow = new Vector();
			tbRow.add(a.getId());
			tbRow.add(a.getName());
			tbRow.add(a.getAge());
			tbContent.add(tbRow);
		}
		dtm.setDataVector(tbContent, tbHeader);
		JTableAu.setModel(dtm);
	}
	
	public void getListAuthorsByName(String name) {
		tbContent = new Vector();
		lau = pa.getListAuthorsByName(name);
		
		if (lau.size() == 0) 
			showErrorMessage("Can't find any author with name "+name, "Error");
		
		for (int i=0; i<lau.size(); i++) {
			Author a = lau.get(i);
			Vector tbRow = new Vector();
			tbRow.add(a.getId());
			tbRow.add(a.getName());
			tbRow.add(a.getAge());
			tbContent.add(tbRow);
		}
		dtm.setDataVector(tbContent, tbHeader);
		JTableAu.setModel(dtm);
	}
	
	public void clearInforInput() {
		txtName.setText(null);
		txtAge.setText(null);
		au = new Author();
		errors = new ArrayList<>();
		getListAuthors();
	}

	public GUI_Authors() {
		setTitle("Authors Management");
		
		ImageIcon searchIcon = imgHelp.getIcon(GUI_Authors.class.getResource("/icons/search.png"), 18, 18);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255)));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 612, 202);
		contentPane.add(scrollPane);
		
		JTableAu = new JTable();
		scrollPane.setViewportView(JTableAu);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("List Authors:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(41, 11, 111, 19);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setDefaultCapable(false);
		btnAdd.setBounds(86, 386, 89, 24);
		contentPane.add(btnAdd);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setDefaultCapable(false);
		btnUpdate.setBounds(275, 386, 89, 24);
		contentPane.add(btnUpdate);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setDefaultCapable(false);
		btnDelete.setBounds(459, 386, 89, 24);
		contentPane.add(btnDelete);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JLabel lblPassword = new JLabel("Age");
		lblPassword.setBounds(199, 323, 51, 19);
		contentPane.add(lblPassword);
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		txtAge = new JTextField();
		txtAge.setBounds(260, 322, 198, 21);
		contentPane.add(txtAge);
		txtAge.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtAge.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBounds(260, 276, 198, 21);
		contentPane.add(txtName);
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(199, 277, 51, 19);
		contentPane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		txtFind = new JTextField();
		txtFind.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtFind.setColumns(10);
		txtFind.setBounds(401, 11, 172, 21);
		contentPane.add(txtFind);
		
		JLabel lblFindByUsername = new JLabel("Find by name:");
		lblFindByUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFindByUsername.setBounds(290, 11, 101, 19);
		contentPane.add(lblFindByUsername);
		
		tbHeader.add("ID");
		tbHeader.add("Name");
		tbHeader.add("Age");
		getListAuthors();
				
		lbCheck = new JLabel("");
		lbCheck.setOpaque(true);
		lbCheck.setHorizontalAlignment(SwingConstants.CENTER);
		lbCheck.setHorizontalTextPosition(SwingConstants.CENTER);
		lbCheck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbCheck.setBounds(10, 433, 612, 19);
		contentPane.add(lbCheck);
		
		JLabel lbSearchIcon = new JLabel("");
		lbSearchIcon.setOpaque(true);
		lbSearchIcon.setIcon(searchIcon);
		lbSearchIcon.setBounds(579, 11, 20, 20);
		contentPane.add(lbSearchIcon);
		
		JTableAu.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		            int selectedRow = JTableAu.getSelectedRow();
		            if (selectedRow >= 0) {
		                Vector vt = (Vector) tbContent.get(selectedRow);
		                txtName.setText(vt.get(1).toString());
		                txtAge.setText(vt.get(2).toString());
		                au = new Author((int) vt.get(0), (String) vt.get(1), (int) vt.get(2));
		            }
		        }
		    }
		});

		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (au.getId() == -1) 
						throw new Exception("Please select author you want to delete");
					else {
						int result = JOptionPane.showConfirmDialog(frame,"Delete this author?", 
								"Are you sure?",
					             JOptionPane.YES_NO_OPTION,
					             JOptionPane.QUESTION_MESSAGE);
					    if(result == JOptionPane.YES_OPTION){
							pa.deleteAuthor(au.getId());
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
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (au.getId() == -1) {
						errors.add("Please select author you want to update");
						throw new Exception();
					}
					
					String name = txtName.getText().toString();
					String ageString = txtAge.getText().toString();
					
					if (name.equals("") || ageString.equals("")) {				
						errors.add("Input fields can't be blank");
						throw new Exception();
					}
					
					if (errors.size() > 0) throw new Exception();
					
					int age = Integer.parseInt(ageString);
						
					pa.updateAuthor(au.getId(), name, age);
					showSuccessMessage("Updated author successfully", "Success");
					clearInforInput();
				}
				catch (NumberFormatException ex) {
					showErrorMessage("Age must be int type", "Updated fail");
					errors = new ArrayList<>();
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
						String name = txtName.getText().toString();
						String ageString = txtAge.getText().toString();
						
						if (name.equals("") || ageString.equals("")) {
							errors.add("Input fields can't be blank.");
							throw new Exception();
						}
						
						int age = Integer.parseInt(txtAge.getText().toString());
						
						pa.insertAuthor(name, age);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						btnAdd.setText("Add");
						showSuccessMessage("Added author successfully", "Success");
						clearInforInput();
					}
				}
				catch (NumberFormatException ex) {
					int result = JOptionPane.showConfirmDialog(frame,"Add failed. Age must be number. Try again?", 
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
				catch (SQLException ex) {
					int result = JOptionPane.showConfirmDialog(frame,"Add failed. "+ex.getMessage()+ " Try again?", 
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
					int result = JOptionPane.showConfirmDialog(frame,"Add failed. "+String.join("\n", errors)+ " Try again?", 
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
		
		txtFind.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (txtFind.getText().equals("")) getListAuthors();
				else getListAuthorsByName(txtFind.getText());
			}
		});
	}
}
