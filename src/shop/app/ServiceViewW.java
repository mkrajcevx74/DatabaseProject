package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Ownership;
import shop.core.Recommendation;
import shop.core.Service;


public class ServiceViewW extends JFrame {
	//Component variables
	private JPanel contentPane;
	private JLabel lblMessage;
	private ButtonGroup groop;
	private JRadioButton rdbtnViewAll;
	private JRadioButton rdbtnViewRecs;
	private JRadioButton rdbtnAddRecs;
	private JButton btnSchedule;
	private JButton btnAddRec;
	private JButton btnDelRec;
	JComboBox<Service> comboBox;
	
	//Connection variables
	private Connection con;
	private Statement myStmt = null;
	private ResultSet myRs = null;

	//Entity variables
	private Ownership own;
	private Service serv = null;
	
	public ServiceViewW(Connection c, Ownership o) {
		//Variable declaration
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
		
		//Action confirm message label
		lblMessage = new JLabel("*message*");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setBounds(167, 59, 220, 14);
		contentPane.add(lblMessage);
		lblMessage.setVisible(false);
		
		
		
		//Radio button action listener
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setServices(comboBox);
				setButtons();
			}
		};
		
		//View all services radio button
		rdbtnViewAll = new JRadioButton("View all services");
		rdbtnViewAll.addActionListener(listener);
		rdbtnViewAll.setBounds(20, 121, 210, 23);
		contentPane.add(rdbtnViewAll);
		
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
		groop.add(rdbtnViewAll);
		groop.add(rdbtnViewRecs);
		groop.add(rdbtnAddRecs);
		rdbtnViewAll.setSelected(true);
		
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
		btnSchedule = new JButton("Schedule");
		btnSchedule.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ScheduleViewW svw = new ScheduleViewW(con, own, ((Service) comboBox.getSelectedItem()));
				if (rdbtnViewRecs.isSelected()) {
					deleteRecommendation();
				}
				svw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnSchedule.setBounds(294, 225, 130, 25);
		contentPane.add(btnSchedule);
		
		//Add recommendation button
		btnAddRec = new JButton("Recommend");
		btnAddRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRecommendation();
				setServices(comboBox);
			}
		});
		btnAddRec.setBounds(294, 225, 130, 25);
		contentPane.add(btnAddRec);
		btnAddRec.setVisible(false);
		
		//Remove recommendation button
		btnDelRec = new JButton("Remove");
		btnDelRec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteRecommendation();
				setServices(comboBox);
			}
		});
		btnDelRec.setBounds(154, 225, 130, 25);
		contentPane.add(btnDelRec);
		btnDelRec.setVisible(false);
		
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
			query += ", RECOMMENDATION WHERE RECOMMENDATION.VIN = \"" + own.getVin() + "\" AND RECOMMENDATION.SERV_NUM = SERVICE.SERV_NUM;";
		} else if (rdbtnAddRecs.isSelected()) {
			query += ", RECOMMENDATION WHERE SERVICE.SERV_NUM NOT IN (SELECT SERV_NUM FROM RECOMMENDATION WHERE VIN = \"" + own.getVin() + "\");";
		} else {
			query += ";";
		}
		try {
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
		return servList;
	}
	
	//Populate services box
	public void setServices(JComboBox<Service> servBox) {
		servBox.setModel(getServices());
		if (servBox.getItemCount() > 0) {
			servBox.setSelectedIndex(0);
		}
	}
	
	//Update buttons
	public void setButtons() {
		if (rdbtnViewAll.isSelected()) {
			btnSchedule.setVisible(true);
			btnAddRec.setVisible(false);
			btnDelRec.setVisible(false);
		} else if (rdbtnViewRecs.isSelected()) {
			btnSchedule.setVisible(true);
			btnAddRec.setVisible(false);
			btnDelRec.setVisible(true);
		} else {
			btnSchedule.setVisible(false);
			btnAddRec.setVisible(true);
			btnDelRec.setVisible(false);
		}
	}
	
	//Add recommended service
	public void addRecommendation() {
		try {
			Recommendation rec = new Recommendation(0, own.getVin(), serv.getNum());
			myStmt = con.createStatement();
			myStmt.executeUpdate(rec.addRecStmt());
			lblMessage.setText("Recommendation added");
		} catch (SQLException eRecAdd) {
			eRecAdd.printStackTrace();
			System.out.println("Error adding recommendation");
		}
		
	}
	
	//Delete recommended service
	public void deleteRecommendation() {
		try {
			Recommendation rec = new Recommendation(0, own.getVin(), serv.getNum());
			myStmt = con.createStatement();
			myStmt.executeUpdate(rec.delRecStmt());
			lblMessage.setText("Recommendation removed");
		} catch (SQLException eRecDel) {
			eRecDel.printStackTrace();
			System.out.println("Error deleting recommendation");
		}
	}
}