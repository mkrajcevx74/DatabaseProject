package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Customer;

public class CustomerAddW extends JFrame {
	//Component vars
	private JPanel pane;
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField contactField;
	
	//Connection vars
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	Customer cus;

	public CustomerAddW(Connection c, int n) {
		//Panel ini
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pane = new JPanel();
		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pane);
		pane.setLayout(null);
		
		con = c;
		
		//Head label
		JLabel lblAddACustomer = new JLabel("Add a customer:");
		lblAddACustomer.setBounds(171, 35, 128, 14);
		pane.add(lblAddACustomer);
		
		//FName label
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setBounds(45, 81, 89, 14);
		pane.add(lblFirstName);
		
		//LName label
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setBounds(45, 112, 89, 14);
		pane.add(lblLastName);
		
		//Contact label
		JLabel lblContactNumber = new JLabel("Contact number:");
		lblContactNumber.setBounds(45, 143, 89, 14);
		pane.add(lblContactNumber);
		
		//Invalid label
		JLabel lblPleaseEnterValid = new JLabel("Please enter valid information.");
		lblPleaseEnterValid.setBounds(130, 168, 199, 14);
		pane.add(lblPleaseEnterValid);
		lblPleaseEnterValid.setVisible(false);
		
		//FName field
		fNameField = new JTextField();
		fNameField.setBounds(161, 78, 136, 20);
		pane.add(fNameField);
		fNameField.setColumns(10);
		
		//LName field
		lNameField = new JTextField();
		lNameField.setColumns(10);
		lNameField.setBounds(161, 109, 136, 20);
		pane.add(lNameField);
		
		//Contact field
		contactField = new JTextField();
		contactField.setColumns(10);
		contactField.setBounds(161, 140, 136, 20);
		pane.add(contactField);
		
		//Add customer button
		JButton btnAddCustomer = new JButton("Add customer");
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					cus = new Customer(n+1, "NULL", "NULL", "NULL");
					if (fNameField.getText().equals("")) {
						cus.setFName("NULL");
					} else {
						cus.setFName("\"" + fNameField.getText() + "\"");
					}
					if (lNameField.getText().equals("")) {
						cus.setLName("NULL");
					} else {
						cus.setLName("\"" + lNameField.getText() + "\"");
					}
					if (contactField.getText().equals("")) {
						cus.setContact("NULL");
					} else {
						cus.setContact("\"" + contactField.getText() + "\"");
					}
					myStmt = con.createStatement();
					myStmt.executeUpdate("INSERT INTO CUSTOMER VALUES(" + cus.insertString()+ ");");
					CustomerSelectW w = new CustomerSelectW(con);
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
		
		//Cancel button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectW w = new CustomerSelectW(con);
				w.setVisible(true);
				((Window) pane.getTopLevelAncestor()).dispose();
			}
		});
		btnCancel.setBounds(79, 201, 89, 23);
		pane.add(btnCancel);
	}
}
