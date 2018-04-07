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
	private String x = "didnt work.";
	
	
	Owner owner = null;

	private JPanel contentPane;
	private JTextField mileageText;
	private JTextArea carRecord;
	private JTextArea recArea;
	private JButton commitButton;
	private JButton editButton;
	

	public OwnerViewW(Connection c, String vin, Customer cus, Vehicle vcl) {
		
		con = c;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		getOwner(vin);
		
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
		cusField.setBounds(108, 5, 150, 16);
		contentPane.add(cusField);
		
		JLabel vclField = new JLabel(vcl.toString());
		vclField.setBounds(108, 34, 154, 16);
		contentPane.add(vclField);
		
		JLabel vinField = new JLabel(vin);
		vinField.setBounds(108, 63, 150, 16);
		contentPane.add(vinField);
		
		JLabel lblNewLabel_2 = new JLabel("Recommendations:");
		lblNewLabel_2.setBounds(270, 13, 137, 16);
		contentPane.add(lblNewLabel_2);
		
		mileageText = new JTextField(Integer.toString(owner.getMiles()));
		mileageText.setBounds(108, 89, 123, 22);
		contentPane.add(mileageText);
		mileageText.setColumns(10);
		mileageText.setEditable(false);
		
		carRecord = new JTextArea(owner.getRecord());
		carRecord.setBounds(24, 154, 238, 46);
		carRecord.setEditable(false);
		contentPane.add(carRecord);
		
		recArea = new JTextArea(getRecommendations(vin));
		recArea.setBounds(270, 42, 137, 66);
		recArea.setEditable(false);
		contentPane.add(recArea);
		
		commitButton = new JButton("Commit");
		commitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mileageText.setEditable(false);
				carRecord.setEditable(false);
				updateInfo( vin, cus.getNum(), vcl.getNum(), (int) Integer.parseInt(mileageText.getText()), carRecord.getText());
				commitButton.setVisible(false);
				editButton.setVisible(true);
			}
		});
		
		editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mileageText.setEditable(true);
				carRecord.setEditable(true);
				commitButton.setVisible(true);
				editButton.setVisible(false);
			}
		});
		
		editButton.setBounds(270, 153, 137, 25);
		contentPane.add(editButton);
		commitButton.setBounds(270, 153, 137, 25);
		contentPane.add(commitButton);
		commitButton.setVisible(false);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerProfileW cpw = new CustomerProfileW(con, cus);
				cpw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		backButton.setBounds(24, 215, 137, 25);
		contentPane.add(backButton);
		
		JButton btnSchedule = new JButton("Schedule");
		btnSchedule.setBounds(270, 215, 137, 25);
		contentPane.add(btnSchedule);
		
		
		
		
	}
	
	//set up a connection to owner class using the vin of the vehicle passed
	public void getOwner(String vin) {
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
	
	//update information in db
	public void updateInfo(String vin, int cusN, int vclN, int vclMile, String ownRecord) {
		try {
			owner = new Owner(vin, cusN, vclN, vclMile, ownRecord);
			myStmt.executeUpdate("UPDATE OWNER SET " + owner.updateString() + " WHERE VIN = \"" + vin  + "\";");
		} catch (SQLException eOwnInsert) {
			eOwnInsert.printStackTrace();
			System.out.println("Owner Insertion Failure");
		}
	}
	
	//get recommendations for the vehicle
	public String getRecommendations(String vin) {
		String service = "";
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT SERVICE.SERV_DESC FROM OWNER, RECOMMENDATION, SERVICE WHERE OWNER.VIN = \"" + vin + 
					"\" AND OWNER.VIN = RECOMMENDATION.VIN AND RECOMMENDATION.SERV_NUM = SERVICE.SERV_NUM;");
			while (myRs.next()) {
				service = service + (myRs.getString("SERV_DESC") + "\n");
				
			}
		} catch (SQLException eRecGet) {
			eRecGet.printStackTrace();
			System.out.println("Failed to retreive recs.");
		}
		if (service.equals("")) {
			service = "No Recommendations.";
			return service;
		}
		else {
			return service;
		}
		
		
	}
}
