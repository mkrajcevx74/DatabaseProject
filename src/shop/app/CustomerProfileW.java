package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.util.*;

import shop.core.Customer;
import shop.core.Vehicle;

public class CustomerProfileW extends JFrame {

	private JPanel contentPane;
	
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;

	int listSize = 0;
	
	public CustomerProfileW(Connection c, int cusNum) {
		con = c;
		Vehicle[] vclList = getVehicles(cusNum);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(186, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblSelectAVehicle = new JLabel("Owned vehicle:");
		lblSelectAVehicle.setBounds(65, 71, 84, 17);
		contentPane.add(lblSelectAVehicle);
		
		JComboBox comboBox = new JComboBox(vclList);
		comboBox.setBounds(217, 69, 143, 20);
		contentPane.add(comboBox);
		
		JButton btnSelectVehicle = new JButton("Select Vehicle");
		btnSelectVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					myStmt = con.createStatement();
					myRs = myStmt.executeQuery("SELECT VIN FROM OWNER WHERE CUS_NUM = " + cusNum + " AND VCL_NUM = " + vclList[comboBox.getSelectedIndex()].getNum());
					String vin = myRs.getString("VIN");
					OwnerViewW ovw = new OwnerViewW(con, vin);
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
		
		JButton btnAddAVehicle = new JButton("Add a Vehicle");
		btnAddAVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VehicleSelectW vsw = new VehicleSelectW(con, cusNum);
				vsw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnAddAVehicle.setBounds(151, 197, 105, 23);
		contentPane.add(btnAddAVehicle);
	}
	
	public Vehicle[] getVehicles(int i) {
		ArrayList<Vehicle> tempL = new ArrayList<Vehicle>();
		Vehicle vcl;
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT VIN, VEHICLE.VCL_NUM, VCL_MAKE, VCL_MODEL, VCL_YEAR, VCL_MISC FROM OWNER, VEHICLE WHERE OWNER.CUS_NUM = " + i + " AND OWNER.VCL_NUM=VEHICLE.VCL_NUM");
			while (myRs.next()) {
				vcl = new Vehicle(myRs.getInt("VCL_NUM"), myRs.getString("VCL_MAKE"), myRs.getString("VCL_MODEL"), myRs.getInt("VCL_YEAR"), myRs.getString("VCL_MISC"));
				tempL.add(vcl);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			System.out.println("Customer's vehicle retrieval failure");
		}
		listSize = tempL.size();
		Vehicle[] tempA = new Vehicle[listSize];
		return tempL.toArray(tempA);
	}
}
