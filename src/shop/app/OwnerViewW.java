package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import shop.core.Customer;
import shop.core.Vehicle;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class OwnerViewW extends JFrame {

	Connection con;

	private JPanel contentPane;

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
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.setBounds(270, 215, 137, 25);
		contentPane.add(btnNewButton);
		
		JButton btnRecommendations = new JButton("Recommendations");
		btnRecommendations.setBounds(270, 13, 137, 25);
		contentPane.add(btnRecommendations);
		
		JLabel lblNewLabel_2 = new JLabel(cus.toString());
		lblNewLabel_2.setBounds(108, 5, 108, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(108, 34, 120, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		lblNewLabel_4.setBounds(108, 63, 120, 16);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		lblNewLabel_5.setBounds(108, 92, 120, 16);
		contentPane.add(lblNewLabel_5);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(24, 154, 207, 86);
		contentPane.add(textArea);

	}

}
