package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import shop.core.Customer;
import shop.core.Vehicle;
import shop.core.Owner;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class OwnerViewW extends JFrame {
	
	//Connection vars
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	
	Owner owner = null;

	private JPanel contentPane;
	private JTextField mileageText;

	public OwnerViewW(Connection c, String vin, Customer cus, Vehicle vcl) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		getOwner(c, vin);
		
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(24, 5, 72, 16);
		contentPane.add(lblCustomer);
		
		JLabel lblVehicle = new JLabel("Vehicle:");
		lblVehicle.setBounds(24, 34, 72, 16);
		contentPane.add(lblVehicle);
		
		JLabel lblNewLabel = new JLabel("VIN:");
		lblNewLabel.setBounds(24, 63, 72, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mileage:");
		lblNewLabel_1.setBounds(24, 92, 72, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblCarHistory = new JLabel("Car History:");
		lblCarHistory.setBounds(24, 121, 72, 16);
		contentPane.add(lblCarHistory);
		
		JLabel cusField = new JLabel(cus.toString());	    		  
		cusField.setBounds(108, 5, 108, 16);
		contentPane.add(cusField);
		
		JLabel vclField = new JLabel(vcl.toString());
		vclField.setBounds(108, 34, 123, 16);
		contentPane.add(vclField);
		
		JLabel vinField = new JLabel(vin);
		vinField.setBounds(108, 63, 116, 16);
		contentPane.add(vinField);
		
		mileageText = new JTextField(Integer.toString(owner.getMiles()));
		mileageText.setBounds(108, 89, 123, 22);
		contentPane.add(mileageText);
		mileageText.setColumns(10);
		mileageText.setEditable(false);
		
		JTextArea carRecord = new JTextArea(owner.getRecord());
		carRecord.setBounds(24, 154, 207, 86);
		carRecord.setEditable(false);
		contentPane.add(carRecord);
		
		JButton commitButton = new JButton("Commit");
		commitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mileageText.setEditable(false);
				carRecord.setEditable(false);
				System.out.println(Integer.parseInt(mileageText.getText()));
				updateInfo( vin, cus.getNum(), vcl.getNum(), (int) Integer.parseInt(mileageText.getText()), carRecord.getText());
			}
		});
		commitButton.setBounds(270, 215, 137, 25);
		contentPane.add(commitButton);
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mileageText.setEditable(true);
				carRecord.setEditable(true);
			}
		});
		editButton.setBounds(270, 177, 137, 25);
		contentPane.add(editButton);
		
		
		JButton btnRecommendations = new JButton("Recommendations");
		btnRecommendations.setBounds(270, 13, 137, 25);
		contentPane.add(btnRecommendations);
		
		
		
		
	}
	
	public void getOwner(Connection con, String vin) {
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM OWNER WHERE VIN = \"" + vin + "\";");
			myRs.next();
			owner = new Owner(myRs.getString("VIN"), myRs.getInt("CUS_NUM"), myRs.getInt("VCL_NUM"), myRs.getInt("OWN_MILES"), myRs.getString("OWN_RECORD"));
		} catch (SQLException eOwn) {
			eOwn.printStackTrace();
			System.out.println("Owner Failure");
		}
	}
	
	
	public void updateInfo(String vin, int cusN, int vclN, int vclMile, String ownRecord) {
		try {
			owner = new Owner(vin, cusN, vclN, vclMile, ownRecord);
			myStmt.executeUpdate("UPDATE OWNER SET " + owner.updateString() + " WHERE VIN = \"" + vin  + "\";");
		} catch (SQLException eOwnInsert) {
			eOwnInsert.printStackTrace();
			System.out.println("Owner Insertion Failure");
		}
	}
}
