package shop.app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DBLogin {

	private JFrame frame;
	private JTextField userName;
	private JPasswordField passwordField;
	private String user = null;
	private String password = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DBLogin window = new DBLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DBLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(46, 85, 100, 20);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(46, 127, 100, 20);
		frame.getContentPane().add(lblPassword);
		
		userName = new JTextField();
		userName.setBounds(208, 85, 100, 20);
		frame.getContentPane().add(userName);
		userName.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(208, 127, 100, 20);
		frame.getContentPane().add(passwordField);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				user = userName.getText();
				password = passwordField.getText();
				if (user.equals("mkrajcevx74") && password.equals("swordfish")) {
					JOptionPane.showMessageDialog(frame, "Login successful.");
				}
				else {
					JOptionPane.showMessageDialog(frame, "Invalid Username or Password.");
				}
			}
		});
		btnLogIn.setBounds(169, 202, 89, 23);
		frame.getContentPane().add(btnLogIn);
	}
	
}
