package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.util.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VehicleSelectW extends JFrame {
	
	Connection con;

	private JPanel contentPane;

	public VehicleSelectW(Connection c) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectAVehicle = new JLabel("Select a vehicle to add");
		lblSelectAVehicle.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectAVehicle.setBounds(146, 11, 154, 14);
		contentPane.add(lblSelectAVehicle);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(244, 144, 180, 20);
		contentPane.add(comboBox);
		
		JLabel lblManufacturer = new JLabel("Manufacturer");
		lblManufacturer.setBounds(54, 34, 65, 14);
		contentPane.add(lblManufacturer);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(10, 59, 180, 20);
		contentPane.add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(244, 59, 180, 20);
		contentPane.add(comboBox_2);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(10, 144, 180, 20);
		contentPane.add(comboBox_3);
		
		JLabel lblModel = new JLabel("Model");
		lblModel.setBounds(318, 34, 28, 14);
		contentPane.add(lblModel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(69, 117, 46, 14);
		contentPane.add(lblNewLabel);
	}

}
