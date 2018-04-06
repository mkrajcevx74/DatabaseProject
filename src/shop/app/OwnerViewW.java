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
		
		JButton commitButton = new JButton("Commit");
		commitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mileageText.setEditable(false);
			}
		});
		commitButton.setBounds(270, 215, 137, 25);
		contentPane.add(commitButton);
		
		JButton editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mileageText.setEditable(true);
			}
		});
		editButton.setBounds(270, 177, 137, 25);
		contentPane.add(editButton);
		
		
		JButton btnRecommendations = new JButton("Recommendations");
		btnRecommendations.setBounds(270, 13, 137, 25);
		contentPane.add(btnRecommendations);
		
		JLabel lblNewLabel_2 = new JLabel(cus.toString());
		lblNewLabel_2.setBounds(108, 5, 108, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(vcl.toString());
		lblNewLabel_3.setBounds(108, 34, 123, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel(vin);
		lblNewLabel_4.setBounds(108, 63, 116, 16);
		contentPane.add(lblNewLabel_4);

		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(24, 154, 207, 86);
		contentPane.add(textArea);
		
		getOwner(c, vin);
		mileageText = new JTextField(Integer.toString(owner.getMiles()));
		mileageText.setBounds(108, 89, 123, 22);
		contentPane.add(mileageText);
		mileageText.setColumns(10);
		mileageText.setEditable(false);
		
		

		
	}
	
	public void getOwner(Connection con, String vin) {
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM OWNER WHERE VIN = \"" + vin + "\"");
			myRs.next();
			owner = new Owner(myRs.getString("VIN"), myRs.getInt("CUS_NUM"), myRs.getInt("VCL_NUM"), myRs.getInt("OWN_MILES"), myRs.getString("OWN_RECORD"));
			
			//mileageText = new JTextField(owner.getMiles());
			//mileageText.setBounds(108, 89, 123, 22);
			//contentPane.add(mileageText);
			//mileageText.setColumns(10);
		} catch (SQLException eCusGet) {
			eCusGet.printStackTrace();
			System.out.println("Customer retrieval failure");
		}
	}
}
