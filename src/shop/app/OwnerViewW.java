package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.util.*;

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
		lblCustomer.setBounds(52, 5, 72, 16);
		contentPane.add(lblCustomer);
		
		JLabel lblVehicle = new JLabel("Vehicle:");
		lblVehicle.setBounds(52, 50, 56, 16);
		contentPane.add(lblVehicle);
		
		JLabel lblNewLabel = new JLabel("Vehicle Number:");
		lblNewLabel.setBounds(52, 95, 108, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mileage:");
		lblNewLabel_1.setBounds(52, 140, 56, 16);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.setBounds(270, 215, 137, 25);
		contentPane.add(btnNewButton);
		
		JLabel lblCarHistory = new JLabel("Car History:");
		lblCarHistory.setBounds(52, 185, 72, 16);
		contentPane.add(lblCarHistory);
		
		JButton btnRecommendations = new JButton("Recommendations");
		btnRecommendations.setBounds(270, 181, 137, 25);
		contentPane.add(btnRecommendations);
	}
}
