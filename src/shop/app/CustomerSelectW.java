package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.util.*;

import shop.core.Customer;

public class CustomerSelectW extends JFrame {

	private JPanel contentPane;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	int listSize = 0;
	
	public CustomerSelectW(Connection c) {
		con = c;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Select a customer to work with");
		lblNewLabel_1.setBounds(136, 28, 162, 14);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel = new JLabel("Customer:");
		lblNewLabel.setBounds(29, 69, 68, 14);
		contentPane.add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox(getCustomers());
		comboBox.setBounds(122, 66, 244, 20);
		contentPane.add(comboBox);
		
		btnNewButton = new JButton("Select customer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerProfileW cpw = new CustomerProfileW(con, comboBox.getSelectedIndex()+1);
				cpw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnNewButton.setBounds(149, 138, 137, 23);
		contentPane.add(btnNewButton);
		
		JButton btnAddACustomer = new JButton("Add a customer");
		btnAddACustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerAddW caw = new CustomerAddW(con, listSize);
				caw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnAddACustomer.setBounds(166, 187, 107, 23);
		contentPane.add(btnAddACustomer);
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
			System.out.println("Customer retrieval failure");
		}
		listSize = temp.size();
		Customer[] cusNames = new Customer[listSize];
		return temp.toArray(cusNames);
	}
}
