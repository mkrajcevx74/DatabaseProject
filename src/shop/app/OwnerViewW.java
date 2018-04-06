package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class OwnerViewW extends JFrame {
	//from this view you want to be able to see all of the information for the owners vehicles
	// this view is what appears from the customer select or profile screen
	Connection con;

	private JPanel contentPane;

	public OwnerViewW(Connection c, String vin) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCustomer = new JLabel("Customer:");
		lblCustomer.setBounds(52, 5, 65, 16);
		contentPane.add(lblCustomer);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(121, 41, 163, 22);
		contentPane.add(comboBox);
		
		JLabel lblVehicle = new JLabel("Vehicle:");
		lblVehicle.setBounds(53, 44, 56, 16);
		contentPane.add(lblVehicle);
	}
}
