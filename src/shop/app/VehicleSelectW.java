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
	private JLabel lblInvalid;
	private JTextField vinField;
	
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
		
		//Titel label
		JLabel lblTitle = new JLabel("Add a Vehicle");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(154, 30, 130, 14);
		contentPane.add(lblTitle);
		
		//Manufacturer label
		JLabel lblMake = new JLabel("Manufacturer:");
		lblMake.setHorizontalAlignment(SwingConstants.CENTER);
		lblMake.setBounds(25, 60, 180, 14);
		contentPane.add(lblMake);
		
		//Model label
		JLabel lblModel = new JLabel("Model:");
		lblModel.setHorizontalAlignment(SwingConstants.CENTER);
		lblModel.setBounds(230, 60, 180, 14);
		contentPane.add(lblModel);
		
		//Year label
		JLabel lblYear = new JLabel("Year:");
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setBounds(25, 110, 180, 14);
		contentPane.add(lblYear);
		
		//Package label
		JLabel lblPackage = new JLabel("Package:");
		lblPackage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPackage.setBounds(230, 110, 180, 14);
		contentPane.add(lblPackage);
		
		//VIN label
		JLabel lblVin = new JLabel("VIN:");
		lblVin.setHorizontalAlignment(SwingConstants.CENTER);
		lblVin.setBounds(129, 160, 180, 14);
		contentPane.add(lblVin);
		
		//Invalid label
		lblInvalid = new JLabel("*Invalid information*");
		lblInvalid.setHorizontalAlignment(SwingConstants.CENTER);
		lblInvalid.setBounds(129, 210, 180, 14);
		contentPane.add(lblInvalid);
		lblInvalid.setVisible(false);
		
		//Package box
		JComboBox<String> comboBox_Misc = new JComboBox<String>();
		comboBox_Misc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				misc = comboBox_Misc.getSelectedItem().toString();
				getVehicle();
			}
		});
		comboBox_Misc.setBounds(230, 130, 180, 20);
		contentPane.add(comboBox_Misc);
		
		//Year box
		JComboBox<Integer> comboBox_Years = new JComboBox<Integer>();
		comboBox_Years.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				year = (int) comboBox_Years.getSelectedItem();
				setComboBox(comboBox_Misc, 4);
			}
		});
		comboBox_Years.setBounds(25, 130, 180, 20);
		contentPane.add(comboBox_Years);
		
		//Model box
		JComboBox<String> comboBox_Models = new JComboBox<String>();
		comboBox_Models.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model = comboBox_Models.getSelectedItem().toString();
				setComboBox(comboBox_Years, 3);
			}
		});
		comboBox_Models.setBounds(230, 80, 180, 20);
		contentPane.add(comboBox_Models);
		
		//Manufacturer box
		JComboBox<String> comboBox_Makes = new JComboBox<String>();
		comboBox_Makes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				make = comboBox_Makes.getSelectedItem().toString();
				setComboBox(comboBox_Models, 2);
			}
		});
		comboBox_Makes.setBounds(25, 80, 180, 20);
		contentPane.add(comboBox_Makes);
		
		//Initialize boxes
		setComboBox(comboBox_Makes, 1);
		
		
		//VIN field
		vinField = new JTextField();
		vinField.setBounds(129, 180, 180, 22);
		vinField.setColumns(10);
		contentPane.add(vinField);
		
		//Add vehicle to database button
		JButton btnAddVehicle = new JButton("Add Vehicle");
		btnAddVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (addOwnership()) {
					CustomerProfileW cpw = new CustomerProfileW(con, cus);
					cpw.setVisible(true);
					((Window) contentPane.getTopLevelAncestor()).dispose();
				}
			}
		});
		btnAddVehicle.setBounds(294, 225, 130, 25);
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
		btnCancel.setBounds(10, 225, 130, 25);
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
	
	//Populate combo box
	public void setComboBox(JComboBox box, int n) {
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		if (n==1) {
			m = (DefaultComboBoxModel<String>) getMakes();
		} else if (n==2) {
			m = (DefaultComboBoxModel<String>) getModels(make);
		} else if (n==3) {
			m = (DefaultComboBoxModel<Integer>) getYears(model);
		} else if (n==4) {
			m = (DefaultComboBoxModel<String>) getMisc(year);
		}
		box.setModel(m);
		if(box.getItemCount() > 0) {
			box.setSelectedIndex(0);
		}
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
	public boolean addOwnership() {
		Ownership own = new Ownership("NULL", cus.getNum(), vcl.getNum(), 0, "");
		if(!vinField.getText().equals("")) {
			own.setVin("\"" + vinField.getText() + "\"");
		}
		try {
			myStmt.executeUpdate(own.insertString());
			return true;
		} catch (SQLException eOwnAdd) {
			eOwnAdd.printStackTrace();
			System.out.println("Error adding ownership");
			lblInvalid.setVisible(true);
			return false;
		}
	}
}
