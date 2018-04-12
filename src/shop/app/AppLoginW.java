package shop.app;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class AppLoginW extends JFrame {
	//Component variables
	private JFrame frame;
	private JTextField userName;
	private JPasswordField passwordField;
	
	//Database info
	private String host = "localhost";
	private String dbName = "mechanic_project";

	//Launch application
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppLoginW window = new AppLoginW();
					window.frame.setVisible(true);
				} catch (Exception eMain) {
					eMain.printStackTrace();
				}
			}
		});
	}
	
	//Login screen constructor
	public AppLoginW() {
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
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(154, 30, 130, 14);
		frame.getContentPane().add(lblWelcome);
		
		//Username label
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(35, 90, 100, 20);
		frame.getContentPane().add(lblUsername);
		
		//Password label
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(35, 125, 100, 20);
		frame.getContentPane().add(lblPassword);
		
		//Invalid label
		JLabel lblInvalid = new JLabel("*Invalid Username or Password*");
		lblInvalid.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalid.setBounds(120, 157, 200, 14);
		frame.getContentPane().add(lblInvalid);
		lblInvalid.setVisible(false);
		
		//Username field
		userName = new JTextField();
		userName.setBounds(140, 92, 160, 20);
		frame.getContentPane().add(userName);
		userName.setColumns(10);
		
		//Password field
		passwordField = new JPasswordField();
		passwordField.setBounds(140, 127, 160, 20);
		frame.getContentPane().add(passwordField);
		
		//Login button
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String dburl = "jdbc:mysql://" + host + "/"+ dbName + "?user=" + userName.getText() + "&password=" + passwordField.getText();
				Connection con = null;
				try {
					con = DriverManager.getConnection(dburl);
					AppHomeW ahw= new AppHomeW(con);
					ahw.setVisible(true);
					frame.dispose();
				} catch (SQLException eLogin) {
					eLogin.printStackTrace();
					System.out.println("Login failure");
					lblInvalid.setVisible(true);
				}
			}
		});
		btnLogIn.setBounds(294, 225, 130, 25);
		frame.getContentPane().add(btnLogIn);
	}
}