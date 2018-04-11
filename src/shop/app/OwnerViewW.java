package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import shop.core.Customer;
import shop.core.Vehicle;
import shop.core.Ownership;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class OwnerViewW extends JFrame {
	//Component variables
	private JPanel contentPane;
	private JTextField textField_Mileage;
	private JButton btnUpdate;
	private JButton btnEdit;
	private JTextArea carRecord;
	
	//Connection variables
	private Connection con;
	private Statement myStmt = null;
	private ResultSet myRs = null;

	public OwnerViewW(Connection c, Ownership own) {
		//Parameter pass
		con = c;
		Customer cus = getCustomer(own);
		Vehicle vcl = getVehicle(own);

		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		//Customer label
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(24, 5, 72, 16);
		contentPane.add(lblCustomer);
		
		//Vehicle label
		JLabel lblVehicle = new JLabel("Vehicle:");
		lblVehicle.setBounds(24, 34, 72, 16);
		contentPane.add(lblVehicle);
		
		//VIN label
		JLabel lblVIN = new JLabel("VIN:");
		lblVIN.setBounds(24, 63, 72, 16);
		contentPane.add(lblVIN);
		
		//Mileage label
		JLabel lblMileage = new JLabel("Mileage:");
		lblMileage.setBounds(24, 92, 72, 16);
		contentPane.add(lblMileage);
		
		//History label
		JLabel lblHistory = new JLabel("Car History:");
		lblHistory.setBounds(24, 121, 72, 16);
		contentPane.add(lblHistory);
		
		//Customer display label
		JLabel lblCusDisplay = new JLabel(cus.toString());	    		  
		lblCusDisplay.setBounds(108, 5, 150, 16);
		contentPane.add(lblCusDisplay);
		
		//Vehicle display label
		JLabel lblVclDisplay = new JLabel(vcl.toString());
		lblVclDisplay.setBounds(108, 34, 154, 16);
		contentPane.add(lblVclDisplay);
		
		//VIN display label
		JLabel vlblVINDisplay = new JLabel(own.getVin());
		vlblVINDisplay.setBounds(108, 63, 150, 16);
		contentPane.add(vlblVINDisplay);
		
		//Mileage text field
		textField_Mileage = new JTextField(Integer.toString(own.getMiles()));
		textField_Mileage.setBounds(108, 89, 123, 22);
		contentPane.add(textField_Mileage);
		textField_Mileage.setColumns(10);
		textField_Mileage.setEditable(false);
		
		//Record text field
		carRecord = new JTextArea(own.getRecord());
		carRecord.setBounds(24, 143, 385, 57);
		carRecord.setEditable(false);
		contentPane.add(carRecord);
		
		//Services window button
		JButton btnServices = new JButton("Services");
		btnServices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiceViewW svw = new ServiceViewW(con, own);
				svw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnServices.setBounds(287, 225, 137, 25);
		contentPane.add(btnServices);
		
		//Edit owner info button
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_Mileage.setEditable(true);
				carRecord.setEditable(true);
				btnUpdate.setVisible(true);
				btnEdit.setVisible(false);
			}
		});
		btnEdit.setBounds(287, 11, 137, 25);
		contentPane.add(btnEdit);
		
		//Update owner info button
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_Mileage.setEditable(false);
				updateInfo(own);
				carRecord.setEditable(false);
				btnUpdate.setVisible(false);
				btnEdit.setVisible(true);
			}
		});
		btnUpdate.setBounds(287, 11, 137, 25);
		contentPane.add(btnUpdate);
		btnUpdate.setVisible(false);
		
		//Back button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerProfileW cpw = new CustomerProfileW(con, cus);
				cpw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnBack.setBounds(10, 225, 137, 25);
		contentPane.add(btnBack);
	}
	
	//Update owner info in database
	public void updateInfo(Ownership own) {
		own = new Ownership(own.getVin(), own.getCNum(), own.getVNum(), ((int) Integer.parseInt(textField_Mileage.getText())), carRecord.getText());
		try {
			myStmt = con.createStatement();
			myStmt.executeUpdate(own.updateString());
		} catch (SQLException eOwnInsert) {
			eOwnInsert.printStackTrace();
			System.out.println("Owner insertion failure");
		}
	}
	
	//Get customer from owner
	public Customer getCustomer(Ownership own) {
		Customer cus = null;
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery(own.selectCusString());
			myRs.next();
			cus = new Customer(myRs.getInt("CUS_NUM"), myRs.getString("CUS_FNAME"), myRs.getString("CUS_LNAME"), myRs.getString("CUS_CONTACT"));
		} catch (SQLException eCusGet) {
			eCusGet.printStackTrace();
			System.out.println("Error retrieving customer");
		}
		return cus;
	}
	
	//Get vehicle from owner
	public Vehicle getVehicle(Ownership own) {
		Vehicle vcl = null;
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery(own.selectVclString());
			myRs.next();
			vcl = new Vehicle(myRs.getInt("VCL_NUM"), myRs.getString("VCL_MAKE"), myRs.getString("VCL_MODEL"), myRs.getInt("VCL_YEAR"), myRs.getString("VCL_MISC"));
		} catch (SQLException eVclGet) {
			eVclGet.printStackTrace();
			System.out.println("Error retrieving vehicle");
		}
		return vcl;
	}
}
