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

public class CustomerAddW extends JFrame {

	private JPanel pane;
	
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField contactField;
	
	Customer cus;
	String fName = null;
	String lName = null;
	String contact = null;

	public CustomerAddW(Connection c, int n) {
		con = c;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pane = new JPanel();
		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pane);
		pane.setLayout(null);
		
		JLabel lblAddACustomer = new JLabel("Add a customer");
		lblAddACustomer.setBounds(171, 35, 89, 14);
		pane.add(lblAddACustomer);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setBounds(45, 81, 89, 14);
		pane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setBounds(45, 112, 89, 14);
		pane.add(lblLastName);
		
		JLabel lblContactNumber = new JLabel("Contact number:");
		lblContactNumber.setBounds(45, 143, 89, 14);
		pane.add(lblContactNumber);
		
		JLabel lblPleaseEnterValid = new JLabel("Please enter valid information.");
		lblPleaseEnterValid.setBounds(130, 168, 169, 14);
		pane.add(lblPleaseEnterValid);
		lblPleaseEnterValid.setVisible(false);
		
		fNameField = new JTextField();
		fNameField.setBounds(161, 78, 136, 20);
		pane.add(fNameField);
		fNameField.setColumns(10);
		
		lNameField = new JTextField();
		lNameField.setColumns(10);
		lNameField.setBounds(161, 109, 136, 20);
		pane.add(lNameField);
		
		contactField = new JTextField();
		contactField.setColumns(10);
		contactField.setBounds(161, 140, 136, 20);
		pane.add(contactField);
		
		JButton btnAddCustomer = new JButton("Add customer");
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fName != null) {
					fName = fNameField.getText();
				} else {
					fName = "NULL";
				}
				if (lName != null) {
					lName = lNameField.getText();
				} else {
					lName = "NULL";
				}
				if (contact != null) {
					contact = contactField.getText();
				} else {
					contact = "NULL";
				}
				cus = new Customer(n+1, fName, lName, contact);
				try {
					myStmt = con.createStatement();
					myStmt.executeUpdate("INSERT INTO CUSTOMER VALUES(" + cus.updateString()+ ");");
					CustomerW w = new CustomerW(con);
					w.setVisible(true);
					((Window) pane.getTopLevelAncestor()).dispose();
				} catch(SQLException e3) {
					e3.printStackTrace();
					System.out.println("3");
					lblPleaseEnterValid.setVisible(true);
				}
			}
		});
		btnAddCustomer.setBounds(229, 201, 111, 23);
		pane.add(btnAddCustomer);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerW w = new CustomerW(con);
				w.setVisible(true);
				((Window) pane.getTopLevelAncestor()).dispose();
			}
		});
		btnCancel.setBounds(79, 201, 89, 23);
		pane.add(btnCancel);
	}
}
