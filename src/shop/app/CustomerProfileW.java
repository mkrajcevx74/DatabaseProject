package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Customer;
import shop.core.Vehicle;

public class CustomerProfileW extends JFrame {
	//Component vars
	private JPanel contentPane;
	
	//Connection vars
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	String vin;

	public CustomerProfileW(Connection c, Customer cus) {
		//Panel ini
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		con = c;
		
		//Title label
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(186, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		//Vehicle label
		JLabel lblSelectAVehicle = new JLabel("Owned vehicle:");
		lblSelectAVehicle.setBounds(65, 71, 84, 17);
		contentPane.add(lblSelectAVehicle);
		
		//Vehicle box
		JComboBox<Vehicle> comboBox = new JComboBox<Vehicle>();
		comboBox.setBounds(217, 69, 143, 20);
		contentPane.add(comboBox);
		comboBox.setModel(getVehicles(cus.getNum()));
		
		//Select owned vehicle button
		JButton btnSelectVehicle = new JButton("Select Vehicle");
		btnSelectVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					myStmt = con.createStatement();
					myRs = myStmt.executeQuery("SELECT VIN FROM OWNER WHERE CUS_NUM = " + cus.getNum() + " AND VCL_NUM = " + ((Vehicle) comboBox.getSelectedItem()).getNum());
					while (myRs.next()) {
						vin = myRs.getString("VIN");
					}	
					OwnerViewW ovw = new OwnerViewW(con, vin, cus, ((Vehicle) comboBox.getSelectedItem()));
					ovw.setVisible(true);
					((Window) contentPane.getTopLevelAncestor()).dispose();
				} catch (SQLException e4) {
					e4.printStackTrace();
					System.out.println(4);
				}
			}
		});
		btnSelectVehicle.setBounds(151, 139, 115, 23);
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
		btnAddAVehicle.setBounds(151, 197, 105, 23);
		contentPane.add(btnAddAVehicle);
	}
	
	//Return owned vehicles
	public ComboBoxModel<Vehicle> getVehicles(int i) {
		DefaultComboBoxModel<Vehicle> vclsList = new DefaultComboBoxModel<Vehicle>();
		Vehicle vcl;
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT VIN, VEHICLE.VCL_NUM, VCL_MAKE, VCL_MODEL, VCL_YEAR, VCL_MISC FROM OWNER, VEHICLE WHERE OWNER.CUS_NUM = " + i + " AND OWNER.VCL_NUM=VEHICLE.VCL_NUM");
			while (myRs.next()) {
				vcl = new Vehicle(myRs.getInt("VCL_NUM"), myRs.getString("VCL_MAKE"), myRs.getString("VCL_MODEL"), myRs.getInt("VCL_YEAR"), myRs.getString("VCL_MISC"));
				vclsList.addElement(vcl);
			}
		} catch (SQLException eVclGet) {
			eVclGet.printStackTrace();
			System.out.println("Customer's vehicle retrieval failure");
		}
		return vclsList;
	}
}
