package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Customer;
import shop.core.Technician;

public class TechnicianProfileW extends JFrame {
	//Component variables
	private JPanel contentPane;
	private JButton btnEdit;
	private JButton btnConfirm;
	private JTextField textField_Contact;
		
	//Connection variables
	private Connection con;
	private Statement myStmt = null;
	private ResultSet myRs = null;
	

	public TechnicianProfileW(Connection c, Technician tec, boolean isClose) {
		con = c;
		
		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTec = new JLabel(tec.toString());
		lblTec.setHorizontalAlignment(SwingConstants.CENTER);
		lblTec.setBounds(144, 11, 134, 14);
		contentPane.add(lblTec);
		
		//Contact number label
		JLabel lblContact = new JLabel("Contact:");
		lblContact.setBounds(31, 69, 56, 16);
		contentPane.add(lblContact);
		
		//Rating label
		JLabel lblRating = new JLabel("Rating:");
		lblRating.setBounds(31, 96, 56, 16);
		contentPane.add(lblRating);
		
		//Wage label
		JLabel lblWage = new JLabel("Wage:");
		lblWage.setBounds(31, 123, 56, 16);
		contentPane.add(lblWage);
		
		//Contact text field
		textField_Contact = new JTextField(tec.getContact());
		textField_Contact.setBounds(144, 67, 108, 20);
		contentPane.add(textField_Contact);
		textField_Contact.setColumns(10);
		textField_Contact.setEditable(false);
		
		//Rating display
		JLabel lblRatingDisplay = new JLabel(Float.toString(tec.getRating()));
		lblRatingDisplay.setBounds(144, 96, 108, 16);
		contentPane.add(lblRatingDisplay);
		
		//Wage display
		JLabel lblWageDisplay = new JLabel(Float.toString(tec.getWage()));
		lblWageDisplay.setBounds(144, 123, 108, 16);
		contentPane.add(lblWageDisplay);
		
		//Edit info button
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_Contact.setEditable(true);
				btnEdit.setVisible(false);
				btnConfirm.setVisible(true);
			}
		});
		btnEdit.setBounds(325, 64, 90, 25);
		contentPane.add(btnEdit);
		
		//Confirm update button
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tec.setContact(textField_Contact.getText());
				updateTecInfo(tec);
				textField_Contact.setEditable(false);
				btnConfirm.setVisible(false);
				btnEdit.setVisible(true);
			}
		});
		btnConfirm.setBounds(325, 64, 90, 25);
		contentPane.add(btnConfirm);
		btnConfirm.setVisible(false);
		
		//New rating slider
		JSlider slider = new JSlider();
		slider.setBounds(75, 150, 275, 16);
		contentPane.add(slider);
		slider.setVisible(false);
		
		//Back button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TechnicianSelectW tsw = new TechnicianSelectW(con);
				tsw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnBack.setBounds(10, 225, 130, 25);
		contentPane.add(btnBack);
		btnBack.setVisible(false);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppHomeW ahw = new AppHomeW(con);
				ahw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnHome.setBounds(10, 225, 130, 25);
		contentPane.add(btnHome);
		btnHome.setVisible(false);
		
		if(isClose) {
			btnHome.setVisible(true);
		} else {
			btnBack.setVisible(true);
		}
	}

	//Update technician info in database
	public void updateTecInfo(Technician tec) {
		try {
			myStmt = con.createStatement();
			myStmt.executeUpdate(tec.updateString());
		} catch (SQLException eTecUpdate) {
			eTecUpdate.printStackTrace();
			System.out.println("Error updating technician");
		}
	}
}
