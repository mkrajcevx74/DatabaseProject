package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Customer;
import shop.core.Owner;

public class VehicleSelectW extends JFrame {
	//Component vars
	private JPanel contentPane;
	private JTextField vinField;
	
	//Connection vars
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	//Class vars
	int vclNum = 1;
	String make;
	String model;
	int year;
	String misc;
	int cusNum;
	


	public VehicleSelectW(Connection c, Customer cus) {
		cusNum = cus.getNum();
		con = c;
		
		//Panel vars
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
		lblMake.setBounds(64, 34, 65, 14);
		contentPane.add(lblMake);
		
		//Model label
		JLabel lblModel = new JLabel("Model:");
		lblModel.setHorizontalAlignment(SwingConstants.CENTER);
		lblModel.setBounds(318, 34, 28, 14);
		contentPane.add(lblModel);
		
		//Year label
		JLabel lblYear = new JLabel("Year:");
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setBounds(72, 119, 46, 14);
		contentPane.add(lblYear);
		
		//Package label
		JLabel lblPackage = new JLabel("Package:");
		lblPackage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPackage.setBounds(310, 119, 46, 14);
		contentPane.add(lblPackage);
		
		//VIN label
		JLabel lblVin = new JLabel("VIN:");
		lblVin.setHorizontalAlignment(SwingConstants.CENTER);
		lblVin.setBounds(72, 188, 46, 14);
		contentPane.add(lblVin);
		
		
		
		//Package box
		JComboBox<String> comboBox_Misc = new JComboBox<String>();
		comboBox_Misc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				misc = comboBox_Misc.getSelectedItem().toString();
			}
		});
		comboBox_Misc.setBounds(244, 144, 180, 20);
		contentPane.add(comboBox_Misc);
		
		//Year box
		JComboBox<Integer> comboBox_Years = new JComboBox<Integer>();
		comboBox_Years.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				year = (int) comboBox_Years.getSelectedItem();
				setMiscBox(comboBox_Misc);
			}
		});
		comboBox_Years.setBounds(10, 144, 180, 20);
		contentPane.add(comboBox_Years);
		
		//Model box
		JComboBox<String> comboBox_Models = new JComboBox<String>();
		comboBox_Models.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model = comboBox_Models.getSelectedItem().toString();
				setYearsBox(comboBox_Years);
				setMiscBox(comboBox_Misc);
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
				setYearsBox(comboBox_Years);
				setMiscBox(comboBox_Misc);
			}
		});
		comboBox_Makes.setBounds(10, 59, 180, 20);
		contentPane.add(comboBox_Makes);
		
		//Initialize boxes
		setMakesBox(comboBox_Makes);
		setModelsBox(comboBox_Models);
		setYearsBox(comboBox_Years);
		setMiscBox(comboBox_Misc);
		
		
		//VIN field
		vinField = new JTextField();
		vinField.setBounds(10, 213, 180, 22);
		contentPane.add(vinField);
		vinField.setColumns(10);
		
		//Add vehicle button
			JButton btnAddVehicle = new JButton("Add Vehicle");
			btnAddVehicle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						myStmt = con.createStatement();
						myRs = myStmt.executeQuery("SELECT VCL_NUM FROM VEHICLE WHERE VCL_MAKE = \"" + make + "\" AND VCL_MODEL = \"" + model + "\" AND VCL_YEAR = " + year + " AND VCL_MISC = \"" + misc + "\";");
						myRs.next();
						vclNum = myRs.getInt("VCL_NUM");
					} catch (SQLException e10) {
						e10.printStackTrace();
						System.out.println("Error retrieving vehicle number");
					}
					try {
						Owner owner = new Owner(vinField.getText(), cusNum, vclNum, 0, "");
						myStmt.executeUpdate("INSERT INTO OWNER VALUES(" + owner.updateString() + ");");
					} catch (SQLException e9) {
						e9.printStackTrace();
					}
				}
			});
			btnAddVehicle.setBounds(285, 199, 89, 23);
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
		btnCancel.setBounds(285, 233, 89, 23);
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
		}
		return makesList;
	}
	
	//Populate makes box
	public void setMakesBox(JComboBox<String> makesBox) {
		makesBox.setModel(getMakes());
		makesBox.setSelectedIndex(0);
		make = makesBox.getSelectedItem().toString();
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
		}
		return mdlsList;
	}
	
	//Populate models box
	public void setModelsBox(JComboBox<String> modelsBox) {
		modelsBox.setModel(getModels(make));
		modelsBox.setSelectedIndex(0);
		model = modelsBox.getSelectedItem().toString();
	}
	
	//Return years
	public ComboBoxModel<Integer> getYears(String model) {
		DefaultComboBoxModel<Integer> yearsList = new DefaultComboBoxModel<Integer>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_YEAR FROM VEHICLE WHERE VCL_MODEL = \"" + model + "\";");
			while(myRs.next()) {
				yearsList.addElement(myRs.getInt("VCL_YEAR"));
			}
		} catch (SQLException eYearsGet) {
			eYearsGet.printStackTrace();
		}
		return yearsList;
	}
	
	//Populate years box
	public void setYearsBox(JComboBox<Integer> yearsBox) {
		yearsBox.setModel(getYears(model));
		yearsBox.setSelectedIndex(0);
		year = (int) yearsBox.getSelectedItem();
	}
	
	//Return packages
	public ComboBoxModel<String> getMisc(int year) {
		DefaultComboBoxModel<String> mscsList = new DefaultComboBoxModel<String>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_MISC FROM VEHICLE WHERE VCL_YEAR = " + year + ";");
			while(myRs.next()) {
				mscsList.addElement(myRs.getString("VCL_MISC"));
			}
		} catch (SQLException eMiscsGet) {
			eMiscsGet.printStackTrace();
		}
		return mscsList;
	}
	
	//Populate packages box
	public void setMiscBox(JComboBox<String> miscBox) {
		miscBox.setModel(getMisc(year));
		miscBox.setSelectedIndex(0);
		misc = miscBox.getSelectedItem().toString();
	}
}
