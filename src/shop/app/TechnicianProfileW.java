package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

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
	
	Technician tec;

	public TechnicianProfileW(Connection c, Technician t, boolean isClose) {
		con = c;
		tec = t;
		
		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTec = new JLabel(tec.toString());
		lblTec.setHorizontalAlignment(SwingConstants.CENTER);
		lblTec.setBounds(154, 30, 130, 14);
		contentPane.add(lblTec);
		
		//Contact number label
		JLabel lblContact = new JLabel("Contact:");
		lblContact.setBounds(25, 60, 100, 16);
		contentPane.add(lblContact);
		
		//Rating label
		JLabel lblRating = new JLabel("Rating:");
		lblRating.setBounds(25, 95, 100, 16);
		contentPane.add(lblRating);
		
		//Wage label
		JLabel lblWage = new JLabel("Wage:");
		lblWage.setBounds(25, 130, 100, 16);
		contentPane.add(lblWage);
		
		//Contact text field
		textField_Contact = new JTextField(tec.getContact());
		textField_Contact.setBounds(140, 60, 160, 20);
		contentPane.add(textField_Contact);
		textField_Contact.setColumns(10);
		textField_Contact.setEditable(false);
		
		//Rating display
		JLabel lblRatingDisplay = new JLabel(Float.toString(tec.getRating()));
		lblRatingDisplay.setBounds(140, 96, 160, 16);
		contentPane.add(lblRatingDisplay);
		
		//Wage display
		JLabel lblWageDisplay = new JLabel(Float.toString(tec.getWage()));
		lblWageDisplay.setBounds(140, 130, 160, 16);
		contentPane.add(lblWageDisplay);
		
		//Edit info button
		btnEdit = new JButton("Edit Info");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField_Contact.setEditable(true);
				btnEdit.setVisible(false);
				btnConfirm.setVisible(true);
			}
		});
		btnEdit.setBounds(310, 56, 100, 25);
		contentPane.add(btnEdit);
		btnEdit.setVisible(false);
		
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
		btnConfirm.setBounds(310, 56, 100, 25);
		contentPane.add(btnConfirm);
		btnConfirm.setVisible(false);
		
		//New rating slider
		JSlider slider = new JSlider(1, 10);
		slider.setSnapToTicks(true);
		slider.setBounds(74, 155, 285, 45);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setVisible(false);
		contentPane.add(slider);
		
		//Rate button
		JButton btnRate = new JButton("Rate");
		btnRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tec.updateRating(slider.getValue());
				updateTecInfo(tec);
				AppHomeW ahw = new AppHomeW(con);
				ahw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnRate.setBounds(294, 225, 130, 25);
		contentPane.add(btnRate);
		btnRate.setVisible(false);
		
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
			slider.setVisible(true);
			btnRate.setVisible(true);
			btnHome.setVisible(true);
		} else {
			btnEdit.setVisible(true);
			btnBack.setVisible(true);
		}
	}

	//Update technician info in database
	public void updateTecInfo(Technician tempTec) {
		try {
			myStmt = con.createStatement();
			myStmt.executeUpdate(tempTec.updateString());
		} catch (SQLException eTecUpdate) {
			eTecUpdate.printStackTrace();
			System.out.println("Error updating technician");
		}
	}
}
