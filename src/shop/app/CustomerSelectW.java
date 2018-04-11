package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Customer;

public class CustomerSelectW extends JFrame {
	//Component variables
	private JPanel contentPane;
	private JButton btnSelectCustomer;
	
	//Customer selection screen constructor
	public CustomerSelectW(Connection con) {
		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Head label
		JLabel lblHead = new JLabel("Select a customer to work with:");
		lblHead.setHorizontalAlignment(SwingConstants.CENTER);
		lblHead.setBounds(124, 61, 187, 14);
		contentPane.add(lblHead);
		
		//Customer box
		JComboBox<Customer> comboBox_Cus = new JComboBox<Customer>();
		comboBox_Cus.setBounds(92, 86, 244, 20);
		contentPane.add(comboBox_Cus);
		comboBox_Cus.setModel(getCustomers(con));
		
		//Select button
		btnSelectCustomer = new JButton("Select customer");
		btnSelectCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerProfileW cpw = new CustomerProfileW(con, ((Customer) comboBox_Cus.getSelectedItem()));
				cpw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnSelectCustomer.setBounds(294, 225, 130, 25);
		contentPane.add(btnSelectCustomer);
		
		//Add customer button
		JButton btnAddACustomer = new JButton("Add a customer");
		btnAddACustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerAddW caw = new CustomerAddW(con, comboBox_Cus.getItemCount());
				caw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnAddACustomer.setBounds(154, 225, 130, 25);
		contentPane.add(btnAddACustomer);
		
		//Back button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppHomeW ahw = new AppHomeW(con);
				ahw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnBack.setBounds(10, 225, 130, 25);
		contentPane.add(btnBack);
	}
	
	//Return customers
	public ComboBoxModel<Customer> getCustomers(Connection con) {
		DefaultComboBoxModel<Customer> cusList = new DefaultComboBoxModel<Customer>();
		Customer cus = null;
		try {
			Statement myStmt = con.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM CUSTOMER;");
			while (myRs.next()) {
				cus = new Customer(myRs.getInt("CUS_NUM"), myRs.getString("CUS_FNAME"), myRs.getString("CUS_LNAME"), myRs.getString("CUS_CONTACT"));
				cusList.addElement(cus);
			}
		} catch (SQLException eCusGet) {
			eCusGet.printStackTrace();
			System.out.println("Customer retrieval failure");
		}
		return cusList;
	}
}
