package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

public class AppHomeW extends JFrame {
	//Component variables
	private JPanel contentPane;
	
	//Create home screen
	public AppHomeW(Connection con) {
		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Head label
		JLabel lblAppHome = new JLabel("Home");
		lblAppHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblAppHome.setBounds(154, 30, 130, 14);
		contentPane.add(lblAppHome);
		
		//Customer select button
		JButton btnSelectACustomer = new JButton("Select a Customer");
		btnSelectACustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerSelectW csw = new CustomerSelectW(con);
				csw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnSelectACustomer.setBounds(140, 80, 160, 25);
		contentPane.add(btnSelectACustomer);
		
		//Employee select button
		JButton btnSelectTec = new JButton("Select a Technician");
		btnSelectTec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TechnicianSelectW tsw = new TechnicianSelectW(con);
				tsw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnSelectTec.setBounds(140, 135, 160, 25);
		contentPane.add(btnSelectTec);
		
		//View schedule button
		JButton btnViewSchedule = new JButton("View Schedule");

		btnViewSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScheduleViewW vsw = new ScheduleViewW(con, null,null);
				vsw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnViewSchedule.setBounds(140, 190, 160, 25);

		contentPane.add(btnViewSchedule);
	}

}
