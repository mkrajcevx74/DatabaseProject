package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Window;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.util.*;

import shop.core.Customer;

public class CustomerW extends JFrame {

	private JPanel pane;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	int listSize = 0;
	
	public CustomerW(Connection c) {
		con = c;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pane = new JPanel();
		pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pane);
		pane.setLayout(null);
		
		JComboBox comboBox = new JComboBox(getCustomers());
		comboBox.setBounds(122, 66, 244, 20);
		pane.add(comboBox);
		
		btnNewButton = new JButton("Select customer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(149, 138, 137, 23);
		pane.add(btnNewButton);
		
		lblNewLabel = new JLabel("Customer:");
		lblNewLabel.setBounds(29, 69, 68, 14);
		pane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Select a customer to work with");
		lblNewLabel_1.setBounds(136, 28, 162, 14);
		pane.add(lblNewLabel_1);
		
		JButton btnAddACustomer = new JButton("Add a customer");
		btnAddACustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerAddW w = new CustomerAddW(con, listSize);
				w.setVisible(true);
				((Window) pane.getTopLevelAncestor()).dispose();
			}
		});
		btnAddACustomer.setBounds(166, 187, 107, 23);
		pane.add(btnAddACustomer);
	}
	
	public Customer[] getCustomers() {
		ArrayList<Customer> temp = new ArrayList<Customer>();
		Customer cust;
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM CUSTOMER;");
			while (myRs.next()) {
				cust = new Customer(myRs.getInt("CUS_NUM"), myRs.getString("CUS_FNAME"), myRs.getString("CUS_LNAME"), myRs.getString("CUS_CONTACT"));
				temp.add(cust);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			System.out.println("2");
		}
		listSize = temp.size();
		Customer[] cusNames = new Customer[listSize];
		return temp.toArray(cusNames);
	}
}
