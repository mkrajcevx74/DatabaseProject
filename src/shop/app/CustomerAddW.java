package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Customer;

public class CustomerAddW extends JFrame {
	//Component variables
	private JPanel contentPane;
	private JLabel lblInvalid;
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField contactField;

	public CustomerAddW(Connection con, int n) {		
		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Title label
		JLabel lblNewLabel = new JLabel("Add a customer");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(154, 30, 130, 14);
		contentPane.add(lblNewLabel);
		
		//FName label
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setBounds(35, 80, 100, 14);
		contentPane.add(lblFirstName);
		
		//LName label
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setBounds(35, 115, 100, 14);
		contentPane.add(lblLastName);
		
		//Contact label
		JLabel lblContactNumber = new JLabel("Contact number:");
		lblContactNumber.setBounds(35, 150, 100, 14);
		contentPane.add(lblContactNumber);
		
		//Invalid label
		lblInvalid = new JLabel("*Invalid information*");
		lblInvalid.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalid.setBounds(140, 170, 160, 14);
		contentPane.add(lblInvalid);
		lblInvalid.setVisible(false);
		
		//FName field
		fNameField = new JTextField();
		fNameField.setBounds(140, 77, 160, 20);
		contentPane.add(fNameField);
		fNameField.setColumns(10);
		
		//LName field
		lNameField = new JTextField();
		lNameField.setColumns(10);
		lNameField.setBounds(140, 112, 160, 20);
		contentPane.add(lNameField);
		
		//Contact field
		contactField = new JTextField();
		contactField.setColumns(10);
		contactField.setBounds(140, 147, 160, 20);
		contentPane.add(contactField);
		
		//Add customer button
		JButton btnAddCustomer = new JButton("Add customer");
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (addCustomer(con)) {
					CustomerSelectW w = new CustomerSelectW(con);
					w.setVisible(true);
					((Window) contentPane.getTopLevelAncestor()).dispose();
				}
			}
		});
		btnAddCustomer.setBounds(294, 225, 130, 25);
		contentPane.add(btnAddCustomer);
		
		//Cancel button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectW w = new CustomerSelectW(con);
				w.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnCancel.setBounds(10, 225, 130, 25);
		contentPane.add(btnCancel);
	}
	
	//Add customer to database
	public boolean addCustomer(Connection con) {
		Customer cus = new Customer(0, "NULL", "NULL", "NULL");
		if (!fNameField.getText().equals("")) {
			cus.setFName("\"" + fNameField.getText() + "\"");
		}
		if (!lNameField.getText().equals("")) {
			cus.setLName("\"" + lNameField.getText() + "\"");
		}
		if (!contactField.getText().equals("")) {
			cus.setContact("\"" + contactField.getText() + "\"");
		}
		try {
			Statement myStmt = con.createStatement();
			myStmt.executeUpdate(cus.insertString());
			return true;
		} catch (SQLException eCusAdd) {
			eCusAdd.printStackTrace();
			System.out.println("Error adding customer to database");
			lblInvalid.setVisible(true);
			return false;
		}
	}
}