package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Ownership;
import shop.core.Service;


public class ServiceViewW extends JFrame {
	//Component variables
	private JPanel contentPane;
	private ButtonGroup groop;
	private JRadioButton rdbtnViewAllServices;
	private JRadioButton rdbtnViewRecs;
	private JRadioButton rdbtnAddRecs;
	JComboBox<Service> comboBox;
	
	//Connection variables
	private Connection con;
	private Statement myStmt = null;
	private ResultSet myRs = null;

	//Entity variables
	private Ownership own;
	private Service serv;
	private JButton btnAddRec;
	private JButton btnRemoveRec;
	
	public ServiceViewW(Connection c, Ownership o) {
		//Parameter assignment
		con = c;
		own = o;
		
		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Head label
		JLabel lblServices = new JLabel("Services");
		lblServices.setHorizontalAlignment(SwingConstants.CENTER);
		lblServices.setBounds(167, 11, 89, 14);
		contentPane.add(lblServices);
		
		//Select label
		JLabel lblSelectAService = new JLabel("Select a service:");
		lblSelectAService.setBounds(20, 85, 109, 14);
		contentPane.add(lblSelectAService);
		
		//Radio button action listener
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setServices(comboBox);
				System.out.println("shit");
			}
		};
		
		//View all services radio button
		rdbtnViewAllServices = new JRadioButton("View all services");
		rdbtnViewAllServices.addActionListener(listener);
		rdbtnViewAllServices.setBounds(20, 121, 210, 23);
		contentPane.add(rdbtnViewAllServices);
		
		//View recommended services
		rdbtnViewRecs = new JRadioButton("View recommended services");
		rdbtnViewRecs.addActionListener(listener);
		rdbtnViewRecs.setBounds(20, 147, 210, 23);
		contentPane.add(rdbtnViewRecs);
		
		//Add recommendation radio button
		rdbtnAddRecs = new JRadioButton("Add recommendation");
		rdbtnAddRecs.addActionListener(listener);
		rdbtnAddRecs.setBounds(20, 173, 210, 23);
		contentPane.add(rdbtnAddRecs);
		
		//Initialize radio button group + listener
		groop = new ButtonGroup();
		groop.add(rdbtnViewAllServices);
		groop.add(rdbtnViewRecs);
		groop.add(rdbtnAddRecs);
		rdbtnViewAllServices.setSelected(true);
		
		//Services box
		comboBox = new JComboBox<Service>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serv = (Service) comboBox.getSelectedItem();
			}
		});
		comboBox.setBounds(167, 82, 220, 20);
		contentPane.add(comboBox);
		setServices(comboBox);
		
		//Schedule appointment button
		JButton btnSchedule = new JButton("Schedule");
		btnSchedule.setBounds(294, 225, 130, 25);
		contentPane.add(btnSchedule);
		
		//Add recommendation button
		btnAddRec = new JButton("Recommend");
		btnAddRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddRec.setBounds(294, 225, 130, 25);
		contentPane.add(btnAddRec);
		
		//Remove recommendation button
		btnRemoveRec = new JButton("Remove");
		btnRemoveRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRemoveRec.setBounds(154, 225, 130, 25);
		contentPane.add(btnRemoveRec);
		
		//Back button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OwnerViewW ovw = new OwnerViewW(con, own);
				ovw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnBack.setBounds(10, 225, 130, 25);
		contentPane.add(btnBack);
	}
	
	//Return services
	public ComboBoxModel<Service> getServices() {
		DefaultComboBoxModel<Service> servList = new DefaultComboBoxModel<Service>();
		String query = "SELECT DISTINCT SERVICE.* FROM SERVICE";
		if (rdbtnViewRecs.isSelected()) {
			System.out.println(2);
			query += ", RECOMMENDATION WHERE RECOMMENDATION.VIN = \"" + own.getVin() + "\" AND RECOMMENDATION.SERV_NUM = SERVICE.SERV_NUM;";
		} else if (rdbtnAddRecs.isSelected()) {
			System.out.println(3);
			query += ", RECOMMENDATION WHERE SERVICE.SERV_NUM NOT IN (SELECT SERV_NUM FROM RECOMMENDATION WHERE VIN = \"" + own.getVin() + "\");";
		} else {
			System.out.println(1);
			query += ";";
		}
		try {
			System.out.println(query);
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery(query);
			while (myRs.next()) {
				serv = new Service(myRs.getInt("SERV_NUM"), myRs.getString("SERV_DESC"));
				servList.addElement(serv);
			}
		} catch (SQLException eServGet) {
			eServGet.printStackTrace();
			System.out.println("Error retrieving services");
		}
		System.out.println("model returned");
		return servList;
	}
	
	//Populate services box
	public void setServices(JComboBox<Service> servBox) {
		servBox.setModel(getServices());
		servBox.setSelectedIndex(0);
		serv = (Service) servBox.getSelectedItem();
	}
	
	//
	public void addAppointment() {

	}
	
	//
	public void addRecommendation() {
		
	}
	
	//
	public void removeRecommmendation() {
		
	}
}