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
		lblCustomer.setBounds(24, 5, 72, 16);
		contentPane.add(lblCustomer);
		
		JLabel lblVehicle = new JLabel("Vehicle:");
		lblVehicle.setBounds(24, 34, 56, 16);
		contentPane.add(lblVehicle);
		
		JLabel lblNewLabel = new JLabel("Vehicle Number:");
		lblNewLabel.setBounds(24, 63, 108, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mileage:");
		lblNewLabel_1.setBounds(24, 92, 56, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblCarHistory = new JLabel("Car History:");
		lblCarHistory.setBounds(24, 121, 72, 16);
		contentPane.add(lblCarHistory);
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.setBounds(270, 215, 137, 25);
		contentPane.add(btnNewButton);
		
		JButton btnRecommendations = new JButton("Recommendations");
		btnRecommendations.setBounds(270, 13, 137, 25);
		contentPane.add(btnRecommendations);
		
		JLabel lblNewLabel_2 = new JLabel(cusName);
		lblNewLabel_2.setBounds(108, 5, 56, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(92, 34, 56, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBounds(144, 63, 56, 16);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBounds(92, 92, 56, 16);
		contentPane.add(lblNewLabel_5);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(24, 154, 207, 86);
		contentPane.add(textArea);
	}
}
