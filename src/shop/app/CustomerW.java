package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Window;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.util.*;

import shop.core.Customer;

public class CustomerW extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	public CustomerW(Connection c) {
		con = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox(getCustomers());
		comboBox.setBounds(122, 66, 244, 20);
		contentPane.add(comboBox);
		
		btnNewButton = new JButton("New button");
		btnNewButton.setBounds(161, 170, 89, 23);
		contentPane.add(btnNewButton);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(29, 69, 46, 14);
		contentPane.add(lblNewLabel);
	}
	
	public String[] getCustomers() {
		ArrayList<String> temp = new ArrayList<String>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM CUSTOMER");
			while (myRs.next()) {
				temp.add(myRs.getString("CUS_FNAME") + myRs.getString("CUS_LNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("2");
		}
		String[] cusNames = new String[temp.size()];
		return temp.toArray(cusNames);
	}
}
