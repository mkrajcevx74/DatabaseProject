package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Customer;
import shop.core.Ownership;
import shop.core.Vehicle;

public class CustomerProfileW extends JFrame {
	//Component variables
	private JPanel contentPane;
	private JButton btnEdit;
	private JButton btnConfirm;
	private JTextField textField_Contact;
	
	//Connection variables
	private Connection con;
	private Statement myStmt = null;
	private ResultSet myRs = null;
	
	//Entity variables
	private Vehicle vcl;

	public CustomerProfileW(Connection c, Customer cus) {
		con = c;
		
		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Title label
		JLabel lblNewLabel = new JLabel(cus.toString());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(141, 11, 131, 14);
		contentPane.add(lblNewLabel);
		
		//Contact label
		JLabel lblContact = new JLabel("Contact #:");
		lblContact.setBounds(25, 70, 100, 14);
		contentPane.add(lblContact);
		
		//Vehicle label
		JLabel lblSelectAVehicle = new JLabel("Owned vehicle:");
		lblSelectAVehicle.setBounds(25, 110, 100, 17);
		contentPane.add(lblSelectAVehicle);
		
		//Contact # field
		textField_Contact = new JTextField(cus.getContact());
		textField_Contact.setBounds(135, 67, 163, 20);
		contentPane.add(textField_Contact);
		textField_Contact.setColumns(10);
		textField_Contact.setEditable(false);
		
		//Vehicle box
		JComboBox<Vehicle> comboBox = new JComboBox<Vehicle>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vcl = (Vehicle) comboBox.getSelectedItem();
			}
		});
		comboBox.setBounds(135, 108, 163, 20);
		contentPane.add(comboBox);
		comboBox.setModel(getVehicles(cus.getNum()));
		
		//Edit info button
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_Contact.setEditable(true);
				btnEdit.setVisible(false);
				btnConfirm.setVisible(true);
			}
		});
		btnEdit.setBounds(325, 64, 90, 25);
		contentPane.add(btnEdit);
		
		//Confirm update button
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cus.setContact(textField_Contact.getText());
				updateCusInfo(cus);
				textField_Contact.setEditable(false);
				btnConfirm.setVisible(false);
				btnEdit.setVisible(true);
			}
		});
		btnConfirm.setBounds(325, 64, 90, 25);
		contentPane.add(btnConfirm);
		btnConfirm.setVisible(false);
		
		//Select owned vehicle button
		JButton btnSelectVehicle = new JButton("Select Vehicle");
		btnSelectVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				OwnerViewW ovw = new OwnerViewW(con, getOwn(cus, vcl));
				ovw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnSelectVehicle.setBounds(294, 225, 130, 25);
		contentPane.add(btnSelectVehicle);
		
		//Add vehicle button
		JButton btnAddAVehicle = new JButton("Add a Vehicle");
		btnAddAVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehicleSelectW vsw = new VehicleSelectW(con, cus);
				vsw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnAddAVehicle.setBounds(154, 225, 130, 25);
		contentPane.add(btnAddAVehicle);
		
		//Back button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectW w = new CustomerSelectW(con);
				w.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnBack.setBounds(10, 225, 130, 25);
		contentPane.add(btnBack);
	}
	
	//Return owned vehicles
	public ComboBoxModel<Vehicle> getVehicles(int i) {
		DefaultComboBoxModel<Vehicle> vclsList = new DefaultComboBoxModel<Vehicle>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT VIN, VEHICLE.VCL_NUM, VCL_MAKE, VCL_MODEL, VCL_YEAR, VCL_MISC FROM OWNER, VEHICLE WHERE OWNER.CUS_NUM = " + i + " AND OWNER.VCL_NUM=VEHICLE.VCL_NUM");
			while (myRs.next()) {
				vcl = new Vehicle(myRs.getInt("VCL_NUM"), myRs.getString("VCL_MAKE"), myRs.getString("VCL_MODEL"), myRs.getInt("VCL_YEAR"), myRs.getString("VCL_MISC"));
				vclsList.addElement(vcl);
			}
		} catch (SQLException eVclGet) {
			eVclGet.printStackTrace();
			System.out.println("Error retrieving vehicles");
		}
		return vclsList;
	}
	
	//Retrieve vehicle ownership
	public Ownership getOwn(Customer cus, Vehicle vcl) {
		Ownership own = null;
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM OWNER WHERE CUS_NUM = " + cus.getNum() + " AND VCL_NUM = " + vcl.getNum() + ";");
			while (myRs.next()) {
				own = new Ownership(myRs.getString("VIN"), myRs.getInt("CUS_NUM"), myRs.getInt("VCL_NUM"), myRs.getInt("OWN_MILES"), myRs.getString("OWN_RECORD"));
			}	
		} catch (SQLException eOwnGet) {
			eOwnGet.printStackTrace();
			System.out.println("Error retrieving ownership");
		}
		return own;
	}
	
	//Update customer info in database
	public void updateCusInfo(Customer cus) {
		try {
			myStmt = con.createStatement();
			myStmt.executeUpdate(cus.updateString());
		} catch (SQLException eCusUpdate) {
			eCusUpdate.printStackTrace();
			System.out.println("Error updating customer");
		}
	}
}
