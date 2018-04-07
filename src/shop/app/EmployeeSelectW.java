package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;


public class EmployeeSelectW extends JFrame{
	//Component vars
	private JPanel contentPane;
	
	//Connection vars
		Connection con;
		Statement myStmt = null;
		ResultSet myRs = null;

		//Employee selection screen constructor
		public EmployeeSelectW(Connection c) {
			//Panel ini
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			con = c;
		}
}
