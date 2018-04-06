package shop.app;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class LoginW extends JFrame{
	//Component variables
	private JFrame frame;
	private JTextField userName;
	private JPasswordField passwordField;
	
	//Database info
	String host = "localhost";
	String dbName = "carInfo";
	String user = null;
	String password = null;
	String dburl = null;
	private Connection con;

	//Launch application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginW window = new LoginW();
					window.frame.setVisible(true);
				} catch (Exception eMain) {
					eMain.printStackTrace();
				}
			}
		});
	}
	
	//Login screen constructor
	public LoginW() {
		initialize();
	}
	
	//Initialize content of frame
	private void initialize() {
		//Panel ini
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Head label
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(178, 23, 46, 14);
		frame.getContentPane().add(lblWelcome);
		
		//Username label
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(46, 85, 100, 20);
		frame.getContentPane().add(lblUsername);
		
		//Password label
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(46, 127, 100, 20);
		frame.getContentPane().add(lblPassword);
		
		//Invalid label
		JLabel lblInvalidUsernameOr = new JLabel("Invalid Username or Password");
		lblInvalidUsernameOr.setBounds(136, 177, 153, 14);
		frame.getContentPane().add(lblInvalidUsernameOr);
		lblInvalidUsernameOr.setVisible(false);
		
		//Username field
		userName = new JTextField();
		userName.setBounds(208, 85, 100, 20);
		frame.getContentPane().add(userName);
		userName.setColumns(10);
		
		//Password field
		passwordField = new JPasswordField();
		passwordField.setBounds(208, 127, 100, 20);
		frame.getContentPane().add(passwordField);
		
		//Login button
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				user = userName.getText();
				password = passwordField.getText();
				dburl = "jdbc:mysql://" + host + "/"+ dbName + "?user=" + user + "&password=" + password;
				try {
					con = DriverManager.getConnection(dburl);
					AppHomeW ahw= new AppHomeW(con);
					ahw.setVisible(true);
					frame.dispose();
				} catch (SQLException e1) {
					e1.printStackTrace();
					lblInvalidUsernameOr.setVisible(true);
					System.out.println("Login failure");
				}
			}
		});
		btnLogIn.setBounds(169, 202, 89, 23);
		frame.getContentPane().add(btnLogIn);
	}
}
