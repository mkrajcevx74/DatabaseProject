package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.util.*;
import java.awt.EventQueue;

import shop.core.Owner;

public class VehicleSelectW extends JFrame {

	private JPanel contentPane;
	
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	int vclNum = 1;
	String make;
	String model;
	int year;
	String misc;
	
	private JTextField vinField;

	public VehicleSelectW(Connection c, int cusNum) {
		
		con = c;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelect = new JLabel("Select a vehicle to add");
		lblSelect.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelect.setBounds(146, 11, 154, 14);
		contentPane.add(lblSelect);
		
		JLabel lblMake = new JLabel("Manufacturer:");
		lblMake.setBounds(64, 34, 65, 14);
		contentPane.add(lblMake);
		
		JLabel lblModel = new JLabel("Model:");
		lblModel.setHorizontalAlignment(SwingConstants.CENTER);
		lblModel.setBounds(318, 34, 28, 14);
		contentPane.add(lblModel);
		
		JLabel lblYear = new JLabel("Year:");
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setBounds(72, 119, 46, 14);
		contentPane.add(lblYear);
		
		JLabel lblPackage = new JLabel("Package:");
		lblPackage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPackage.setBounds(310, 119, 46, 14);
		contentPane.add(lblPackage);
		
		JLabel lblVin = new JLabel("VIN:");
		lblVin.setHorizontalAlignment(SwingConstants.CENTER);
		lblVin.setBounds(72, 188, 46, 14);
		contentPane.add(lblVin);
		
		JComboBox<String> comboBox_Misc = new JComboBox<String>();
		comboBox_Misc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				misc = comboBox_Misc.getSelectedItem().toString();
			}
		});
		comboBox_Misc.setBounds(244, 144, 180, 20);
		contentPane.add(comboBox_Misc);
		
		JComboBox<Integer> comboBox_Years = new JComboBox<Integer>();
		comboBox_Years.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				year = (int) comboBox_Years.getSelectedItem();
				comboBox_Misc.setModel(getMiscs(year));
			}
		});
		comboBox_Years.setBounds(10, 144, 180, 20);
		contentPane.add(comboBox_Years);
		
		JComboBox<String> comboBox_Models = new JComboBox<String>();
		comboBox_Models.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model = comboBox_Models.getSelectedItem().toString();
				comboBox_Years.setModel(getYears(model));
				comboBox_Misc.setModel(new DefaultComboBoxModel<String>());
			}
		});
		comboBox_Models.setBounds(244, 59, 180, 20);
		contentPane.add(comboBox_Models);
		
		JComboBox<String> comboBox_Makes = new JComboBox<String>(getMakes());
		comboBox_Makes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				make = comboBox_Makes.getSelectedItem().toString();
				comboBox_Models.setModel(getModels(make));
				comboBox_Years.setModel(new DefaultComboBoxModel<Integer>());
				comboBox_Misc.setModel(new DefaultComboBoxModel<String>());
			}
		});
		comboBox_Makes.setBounds(10, 59, 180, 20);
		contentPane.add(comboBox_Makes);
		
		vinField = new JTextField();
		vinField.setBounds(10, 213, 180, 22);
		contentPane.add(vinField);
		vinField.setColumns(10);
		
		
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
					System.out.println(owner);
					myStmt.executeUpdate("INSERT INTO OWNER VALUES(" + owner.updateString() + ");");
				} catch (SQLException e9) {
					e9.printStackTrace();
				}
			}
		});
		btnAddVehicle.setBounds(286, 213, 89, 23);
		contentPane.add(btnAddVehicle);
	}
	
	public ComboBoxModel<String> getMakes() {
		DefaultComboBoxModel<String> makesList = new DefaultComboBoxModel<String>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_MAKE FROM VEHICLE");
			while(myRs.next()) {
				makesList.addElement(myRs.getString("VCL_MAKE"));
			}
		} catch (SQLException e5) {
			e5.printStackTrace();
		}
		return makesList;
	}
	
	public ComboBoxModel<String> getModels(String make) {
		DefaultComboBoxModel<String> mdlsList = new DefaultComboBoxModel<String>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_MODEL FROM VEHICLE WHERE VCL_MAKE = \"" + make + "\";");
			while(myRs.next()) {
				mdlsList.addElement(myRs.getString("VCL_MODEL"));
			}
		} catch (SQLException e6) {
			e6.printStackTrace();
		}
		return mdlsList;
	}
	
	public ComboBoxModel<Integer> getYears(String model) {
		DefaultComboBoxModel<Integer> yearsList = new DefaultComboBoxModel<Integer>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_YEAR FROM VEHICLE WHERE VCL_MODEL = \"" + model + "\";");
			while(myRs.next()) {
				yearsList.addElement(myRs.getInt("VCL_YEAR"));
			}
		} catch (SQLException e7) {
			e7.printStackTrace();
		}
		return yearsList;
	}
	
	public ComboBoxModel<String> getMiscs(int year) {
		DefaultComboBoxModel<String> mscsList = new DefaultComboBoxModel<String>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT VCL_MISC FROM VEHICLE WHERE VCL_YEAR = " + year + ";");
			while(myRs.next()) {
				mscsList.addElement(myRs.getString("VCL_MISC"));
			}
		} catch (SQLException e8) {
			e8.printStackTrace();
		}
		return mscsList;
	}
}
