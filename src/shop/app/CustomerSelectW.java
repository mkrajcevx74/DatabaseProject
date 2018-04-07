package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Customer;

public class CustomerSelectW extends JFrame {
	//Component vars
	private JPanel contentPane;
	private JButton btnSelectCustomer;
	private JLabel lblCustomer;
	
	//Connection vars
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	//Customer selection screen constructor
	public CustomerSelectW(Connection c) {
		//Panel ini
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		con = c;
		
		//Head label
		JLabel lblHead = new JLabel("Select a customer to work with:");
		lblHead.setBounds(136, 28, 200, 14);
		contentPane.add(lblHead);
		
		//Customer label
		lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(29, 69, 68, 14);
		contentPane.add(lblCustomer);
		
		//Customer box
		JComboBox<Customer> comboBox_Cus = new JComboBox<Customer>();
		comboBox_Cus.setBounds(122, 66, 244, 20);
		contentPane.add(comboBox_Cus);
		comboBox_Cus.setModel(getCustomers());
		
		//Select button
		btnSelectCustomer = new JButton("Select customer");
		btnSelectCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerProfileW cpw = new CustomerProfileW(con, ((Customer) comboBox_Cus.getSelectedItem()));
				cpw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnSelectCustomer.setBounds(149, 133, 137, 23);
		contentPane.add(btnSelectCustomer);
		
		//Add button
		JButton btnAddACustomer = new JButton("Add a customer");
		btnAddACustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerAddW caw = new CustomerAddW(con, comboBox_Cus.getItemCount());
				caw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnAddACustomer.setBounds(149, 169, 137, 23);
		contentPane.add(btnAddACustomer);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppHomeW ahw = new AppHomeW(con);
				ahw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnHome.setBounds(149, 205, 137, 23);
		contentPane.add(btnHome);
	}
	
	//Return customers
	public ComboBoxModel<Customer> getCustomers() {
		DefaultComboBoxModel<Customer> cusList = new DefaultComboBoxModel<Customer>();
		Customer cus;
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM CUSTOMER;");
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
