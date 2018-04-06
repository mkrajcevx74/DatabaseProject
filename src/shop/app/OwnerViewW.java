package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import shop.core.Vehicle;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class OwnerViewW extends JFrame {

	Connection con;

	private JPanel contentPane;

	public OwnerViewW(Connection c, String vin, Vehicle v) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
