package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import shop.core.Customer;
import shop.core.Vehicle;
import shop.core.Ownership;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class OwnerViewW extends JFrame {
	//Component variables
	private JPanel contentPane;
	private JTextField textField_Mileage;
	private JButton btnConfirm;
	private JButton btnEdit;
	private JTextArea textArea_Record;
	
	//Connection variables
	private Connection con;
	private Statement myStmt = null;
	private ResultSet myRs = null;
	
	Ownership own;

	public OwnerViewW(Connection c, Ownership o) {
		//Parameter pass
		con = c;
		own = o;
		Customer cus = getCustomer(own);
		Vehicle vcl = getVehicle(own);

		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		//Customer label
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(25, 15, 70, 16);
		contentPane.add(lblCustomer);
		
		//Vehicle label
		JLabel lblVehicle = new JLabel("Vehicle:");
		lblVehicle.setBounds(25, 40, 70, 16);
		contentPane.add(lblVehicle);
		
		//VIN label
		JLabel lblVIN = new JLabel("VIN:");
		lblVIN.setBounds(25, 65, 70, 16);
		contentPane.add(lblVIN);
		
		//Mileage label
		JLabel lblMileage = new JLabel("Mileage:");
		lblMileage.setBounds(25, 90, 70, 16);
		contentPane.add(lblMileage);
		
		//History label
		JLabel lblHistory = new JLabel("Car History:");
		lblHistory.setBounds(25, 115, 70, 16);
		contentPane.add(lblHistory);
		
		//Appointments label
		JLabel lblApts = new JLabel("Appointments:");
		lblApts.setHorizontalAlignment(SwingConstants.CENTER);
		lblApts.setBounds(265, 15, 150, 16);
		contentPane.add(lblApts);
		
		//Customer display label
		JLabel lblCusDisplay = new JLabel(cus.toString());	    		  
		lblCusDisplay.setBounds(105, 15, 150, 16);
		contentPane.add(lblCusDisplay);
		
		//Vehicle display label
		JLabel lblVclDisplay = new JLabel(vcl.toString());
		lblVclDisplay.setBounds(105, 40, 150, 16);
		contentPane.add(lblVclDisplay);
		
		//VIN display label
		JLabel vlblVINDisplay = new JLabel(own.getVin());
		vlblVINDisplay.setBounds(105, 65, 150, 16);
		contentPane.add(vlblVINDisplay);
		
		//Mileage text field
		textField_Mileage = new JTextField(Integer.toString(own.getMiles()));
		textField_Mileage.setBounds(105, 90, 130, 22);
		contentPane.add(textField_Mileage);
		textField_Mileage.setColumns(10);
		textField_Mileage.setEditable(false);
		
		//Record text field
		textArea_Record = new JTextArea(own.getRecord());
		textArea_Record.setBounds(25, 140, 210, 70);
		textArea_Record.setEditable(false);
		textArea_Record.setLineWrap(true);
		textArea_Record.setWrapStyleWord(true);
		contentPane.add(textArea_Record);
		
		//Appointments text field
		JTextArea textArea_Apts = new JTextArea(aptsDisplay(own));
		textArea_Apts.setRows(8);
		textArea_Apts.setBounds(260, 40, 150, 170);
		textArea_Apts.setLineWrap(true);
		textArea_Apts.setWrapStyleWord(true);
		textArea_Apts.setEditable(false);
		contentPane.add(textArea_Apts);
		
		//Services window button
		JButton btnServices = new JButton("Services");
		btnServices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ServiceViewW svw = new ServiceViewW(con, own);
				svw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnServices.setBounds(294, 225, 130, 25);
		contentPane.add(btnServices);
		
		//Edit owner info button
		btnEdit = new JButton("Edit Info");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_Mileage.setEditable(true);
				textArea_Record.setEditable(true);
				btnConfirm.setVisible(true);
				btnEdit.setVisible(false);
			}
		});
		btnEdit.setBounds(154, 225, 130, 25);
		contentPane.add(btnEdit);
		
		//Update owner info button
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				own = new Ownership(own.getVin(), own.getCNum(), own.getVNum(), ((int) Integer.parseInt(textField_Mileage.getText())), textArea_Record.getText());
				textField_Mileage.setEditable(false);
				updateInfo(own);
				textArea_Record.setEditable(false);
				btnConfirm.setVisible(false);
				btnEdit.setVisible(true);
			}
		});
		btnConfirm.setBounds(154, 225, 130, 25);
		contentPane.add(btnConfirm);
		btnConfirm.setVisible(false);
		
		//Back button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerProfileW cpw = new CustomerProfileW(con, cus);
				cpw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnBack.setBounds(10, 225, 130, 25);
		contentPane.add(btnBack);
	}
	
	//Update owner info in database
	public void updateInfo(Ownership own) {
		own = new Ownership(own.getVin(), own.getCNum(), own.getVNum(), ((int) Integer.parseInt(textField_Mileage.getText())), textArea_Record.getText());
		try {
			myStmt = con.createStatement();
			myStmt.executeUpdate(own.updateString());
		} catch (SQLException eOwnInsert) {
			eOwnInsert.printStackTrace();
			System.out.println("Owner insertion failure");
		}
	}
	
	//Get customer from owner
	public Customer getCustomer(Ownership own) {
		Customer cus = null;
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery(own.selectCusString());
			myRs.next();
			cus = new Customer(myRs.getInt("CUS_NUM"), myRs.getString("CUS_FNAME"), myRs.getString("CUS_LNAME"), myRs.getString("CUS_CONTACT"));
		} catch (SQLException eCusGet) {
			eCusGet.printStackTrace();
			System.out.println("Error retrieving customer");
		}
		return cus;
	}
	
	//Get vehicle from owner
	public Vehicle getVehicle(Ownership own) {
		Vehicle vcl = null;
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery(own.selectVclString());
			myRs.next();
			vcl = new Vehicle(myRs.getInt("VCL_NUM"), myRs.getString("VCL_MAKE"), myRs.getString("VCL_MODEL"), myRs.getInt("VCL_YEAR"), myRs.getString("VCL_MISC"));
		} catch (SQLException eVclGet) {
			eVclGet.printStackTrace();
			System.out.println("Error retrieving vehicle");
		}
		return vcl;
	}
	
	//Display appointments
	public String aptsDisplay(Ownership tempOwn) {
		String aptsString = "";
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery(tempOwn.selectAptString());
			while (myRs.next()) {
				aptsString += myRs.getDate("SHIFT_DAY") + ":\n" + myRs.getString("SERV_DESC") + "\n";
			}
		} catch (SQLException eAptsGet) {
			eAptsGet.printStackTrace();
			System.out.println("Error retrieving appointments");
		}
		return aptsString;
	}
}
