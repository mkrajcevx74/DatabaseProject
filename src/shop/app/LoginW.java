package shop.app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class LoginW extends JFrame{

	private JFrame frame;
	private JTextField userName;
	private JPasswordField passwordField;
	private Connection con;
	
	String host = "localhost";
	String dbName = "carInfo";
	String user = null;
	String password = null;
	String dburl = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginW window = new LoginW();
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
	public LoginW() {
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
		
		JLabel lblInvalidUsernameOr = new JLabel("Invalid Username or Password");
		lblInvalidUsernameOr.setBounds(136, 177, 153, 14);
		frame.getContentPane().add(lblInvalidUsernameOr);
		lblInvalidUsernameOr.setVisible(false);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				user = userName.getText();
				password = passwordField.getText();
				dburl = "jdbc:mysql://" + host + "/"+ dbName + "?user=" + user + "&password=" + password;
				try {
					con = DriverManager.getConnection(dburl);
					CustomerW c= new CustomerW(con);
					c.setVisible(true);
					frame.dispose();
				} catch (SQLException e) {
					e.printStackTrace();
					lblInvalidUsernameOr.setVisible(true);
					System.out.println("1");
				}
			}
		});
		btnLogIn.setBounds(169, 202, 89, 23);
		frame.getContentPane().add(btnLogIn);
	}

}
