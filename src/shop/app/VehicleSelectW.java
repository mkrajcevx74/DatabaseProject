package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Customer;
import shop.core.Vehicle;
import shop.core.Ownership;

public class VehicleSelectW extends JFrame {
	//Component variables
	private JPanel contentPane;
	
	//Connection variables
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	//Entity variables
	Vehicle vcl;
	String make;
	String model;
	int year;
	String misc;
	
	Customer cus;

	public VehicleSelectW(Connection c, Customer u) {
		//Parameter declarations
		con = c;
		cus = u;
		
		//Panel variables
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Head label
		JLabel lblSelect = new JLabel("Select a vehicle to add");
		lblSelect.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelect.setBounds(146, 11, 154, 14);
		contentPane.add(lblSelect);
		
		//Manufacturer label
		JLabel lblMake = new JLabel("Manufacturer:");
		lblMake.setHorizontalAlignment(SwingConstants.CENTER);
		lblMake.setBounds(52, 36, 101, 14);
		contentPane.add(lblMake);
		
		//Model label
		JLabel lblModel = new JLabel("Model:");
		lblModel.setHorizontalAlignment(SwingConstants.CENTER);
		lblModel.setBounds(306, 36, 60, 14);
		contentPane.add(lblModel);
		
		//Year label
		JLabel lblYear = new JLabel("Year:");
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setBounds(78, 90, 46, 14);
		contentPane.add(lblYear);
		
		//Package label
		JLabel lblPackage = new JLabel("Package:");
		lblPackage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPackage.setBounds(297, 90, 84, 14);
		contentPane.add(lblPackage);
		
		//VIN label
		JLabel lblVin = new JLabel("VIN:");
		lblVin.setHorizontalAlignment(SwingConstants.CENTER);
		lblVin.setBounds(193, 146, 60, 14);
		contentPane.add(lblVin);
		
		//Package box
		JComboBox<String> comboBox_Misc = new JComboBox<String>();
		comboBox_Misc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				misc = comboBox_Misc.getSelectedItem().toString();
				getVehicle();
			}
		});
		comboBox_Misc.setBounds(244, 115, 180, 20);
		contentPane.add(comboBox_Misc);
		
		//Year box
		JComboBox<Integer> comboBox_Years = new JComboBox<Integer>();
		comboBox_Years.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				year = (int) comboBox_Years.getSelectedItem();
				setMiscBox(comboBox_Misc);
			}
		});
		comboBox_Years.setBounds(10, 115, 180, 20);
		contentPane.add(comboBox_Years);
		
		//Model box
		JComboBox<String> comboBox_Models = new JComboBox<String>();
		comboBox_Models.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model = comboBox_Models.getSelectedItem().toString();
				setYearsBox(comboBox_Years);
			}
		});
		comboBox_Models.setBounds(244, 59, 180, 20);
		contentPane.add(comboBox_Models);
		
		//Manufacturer box
		JComboBox<String> comboBox_Makes = new JComboBox<String>();
		comboBox_Makes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				make = comboBox_Makes.getSelectedItem().toString();
				setModelsBox(comboBox_Models);
			}
		});
		comboBox_Makes.setBounds(10, 59, 180, 20);
		contentPane.add(comboBox_Makes);
		
		//Initialize boxes
		setMakesBox(comboBox_Makes, comboBox_Models, comboBox_Years, comboBox_Misc);
		
		
		//VIN field
		JTextField vinField = new JTextField();
		vinField.setBounds(129, 171, 180, 22);
		contentPane.add(vinField);
		vinField.setColumns(10);
		
		//Add vehicle to database button
		JButton btnAddVehicle = new JButton("Add Vehicle");
		btnAddVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addOwnership(vinField.getText());
				CustomerProfileW cpw = new CustomerProfileW(con, cus);
				cpw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnAddVehicle.setBounds(323, 227, 101, 23);
		contentPane.add(btnAddVehicle);
		
		//Cancel button
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerProfileW cpw = new CustomerProfileW(con, cus);
				cpw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnCancel.setBounds(10, 227, 101, 23);
		contentPane.add(btnCancel);
	}
	
	//Return makes
	public ComboBoxModel<String> getMakes() {
		DefaultComboBoxModel<String> makesList = new DefaultComboBoxModel<String>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_MAKE FROM VEHICLE");
			while(myRs.next()) {
				makesList.addElement(myRs.getString("VCL_MAKE"));
			}
		} catch (SQLException eMakesGet) {
			eMakesGet.printStackTrace();
			System.out.print("Error retrieving makes");
		}
		return makesList;
	}
	
	//Populate makes box
	public void setMakesBox(JComboBox<String> makesBox, JComboBox<String> modelsBox, JComboBox<Integer> yearsBox, JComboBox<String> miscBox) {
		makesBox.setModel(getMakes());
		makesBox.setSelectedIndex(0);
	}
	
	//Return models
	public ComboBoxModel<String> getModels(String make) {
		DefaultComboBoxModel<String> mdlsList = new DefaultComboBoxModel<String>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_MODEL FROM VEHICLE WHERE VCL_MAKE = \"" + make + "\";");
			while(myRs.next()) {
				mdlsList.addElement(myRs.getString("VCL_MODEL"));
			}
		} catch (SQLException eModelsGet) {
			eModelsGet.printStackTrace();
			System.out.print("Error retrieving models");
		}
		return mdlsList;
	}
	
	//Populate models box
	public void setModelsBox(JComboBox<String> modelsBox) {
		modelsBox.setModel(getModels(make));
		modelsBox.setSelectedIndex(0);
	}
	
	//Return years
	public ComboBoxModel<Integer> getYears(String model) {
		DefaultComboBoxModel<Integer> yearsList = new DefaultComboBoxModel<Integer>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_YEAR FROM VEHICLE WHERE VCL_MAKE = \"" + make + "\" AND VCL_MODEL = \"" + model + "\";");
			while(myRs.next()) {
				yearsList.addElement(myRs.getInt("VCL_YEAR"));
			}
		} catch (SQLException eYearsGet) {
			eYearsGet.printStackTrace();
			System.out.print("Error retrieving years");
		}
		return yearsList;
	}
	
	//Populate years box
	public void setYearsBox(JComboBox<Integer> yearsBox) {
		yearsBox.setModel(getYears(model));
		yearsBox.setSelectedIndex(0);
	}
	
	//Return packages
	public ComboBoxModel<String> getMisc(int year) {
		DefaultComboBoxModel<String> mscsList = new DefaultComboBoxModel<String>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_MISC FROM VEHICLE WHERE VCL_MAKE = \"" + make + "\" AND VCL_MODEL = \"" + model + "\" AND VCL_YEAR = " + year + ";");
			while(myRs.next()) {
				mscsList.addElement(myRs.getString("VCL_MISC"));
			}
		} catch (SQLException eMiscsGet) {
			eMiscsGet.printStackTrace();
			System.out.print("Error retrieving packages");
		}
		return mscsList;
	}
	
	//Populate packages box
	public void setMiscBox(JComboBox<String> miscBox) {
		miscBox.setModel(getMisc(year));
		miscBox.setSelectedIndex(0);
	}
	
	//Get vehicle entity
	public void getVehicle() {
		try {
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_NUM FROM VEHICLE WHERE VCL_MAKE = \"" + make + "\" AND VCL_MODEL = \"" + model + "\" AND VCL_YEAR = " + year + " AND VCL_MISC = \"" + misc + "\";");
			while (myRs.next()) {
				vcl = new Vehicle(myRs.getInt("VCL_NUM"), make, model, year, misc);
			}
		} catch (SQLException eVclGet) {
			eVclGet.printStackTrace();
			System.out.println("Error retrieving vehicle");
		}
	}
	
	//Add ownership to database
	public void addOwnership(String vin) {
		Ownership owner = new Ownership(vin, cus.getNum(), vcl.getNum(), 0, "");
		try {
			myStmt.executeUpdate(owner.insertString());
		} catch (SQLException eOwnAdd) {
			eOwnAdd.printStackTrace();
			System.out.println("Error adding ownership");
		}
	}
}
