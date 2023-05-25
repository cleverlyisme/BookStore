package gui_design;

import java.awt.EventQueue;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Button;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import process.Author;
import process.Process_Author;
import process.Process_User;

public class GUI_Login extends JFrame {

	private JPanel contentPane;
	
	private ImageIconHelper imgHelp = new ImageIconHelper();
	private JTextField txtUser;
	private JPasswordField txtPass;
	private Process_User pu = new Process_User();
	private static GUI_Login frame = new GUI_Login();
	
	public void showErrorMessage(String message, String err) {
	    JOptionPane.showMessageDialog(this, message, err, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setLocationRelativeTo(null); 
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI_Login() {
		setTitle("Login System");
		
		ImageIcon logoIcon = imgHelp.getIcon(GUI_Login.class.getResource("/icons/bookstore.png"), 75, 70);
		
		setBounds(100, 100, 585, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbLogo = new JLabel("");
		lbLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lbLogo.setIcon(logoIcon);
		lbLogo.setBounds(241, 11, 107, 85);
		contentPane.add(lbLogo);
		
		JLabel lblNewLabel = new JLabel("Bookstore");
		lblNewLabel.setForeground(new Color(250, 128, 114));
		lblNewLabel.setFont(new Font(".VnVogue", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(121, 91, 350, 34);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(124, 175, 75, 24);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(124, 215, 83, 24);
		contentPane.add(lblNewLabel_2);
		
		txtUser = new JTextField();
		txtUser.setBounds(217, 176, 215, 25);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(217, 216, 215, 25);
		contentPane.add(txtPass);
		
		JCheckBox checkShowPass = new JCheckBox("Show password");
		checkShowPass.setFont(new Font("Tahoma", Font.PLAIN, 13));
		checkShowPass.setBounds(221, 248, 136, 23);
		contentPane.add(checkShowPass);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFocusTraversalKeysEnabled(false);
		btnLogin.setFocusPainted(false);
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnLogin.setBounds(239, 290, 91, 30);
		contentPane.add(btnLogin);
		
		JLabel lbLogin = new JLabel("");
		lbLogin.setForeground(Color.RED);
		lbLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbLogin.setBounds(190, 330, 215, 19);
		contentPane.add(lbLogin);
		
		JLabel lblLoginSystem = new JLabel("Login System");
		lblLoginSystem.setHorizontalAlignment(SwingConstants.CENTER);
		lblLoginSystem.setForeground(new Color(250, 128, 114));
		lblLoginSystem.setFont(new Font(".VnVogue", Font.BOLD, 25));
		lblLoginSystem.setBounds(121, 122, 350, 34);
		contentPane.add(lblLoginSystem);
		
		checkShowPass.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!checkShowPass.isSelected()) txtPass.setEchoChar('*');
				else txtPass.setEchoChar((char)0);
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = txtUser.getText();
					char[] passInput = txtPass.getPassword();
					String pass = new String(passInput);

					if (!pu.checkLogin(username, pass)) {
						throw new Exception("Invalid username or password");
					}
					
					dispose();
					GUI_Home h = new GUI_Home();
					h.setLocationRelativeTo(null); 
					h.setVisible(true);
				}
				catch(Exception ex) {
					showErrorMessage(ex.getMessage(), "Login fail");
				}
			}
		});
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enterPressed");
	    getRootPane().getActionMap().put("enterPressed", new AbstractAction() {
	        public void actionPerformed(ActionEvent e) {
	            btnLogin.doClick(); // Simulate a button click when Enter is pressed
	        }
	    });
	}
}
