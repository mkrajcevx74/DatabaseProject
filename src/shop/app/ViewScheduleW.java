package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Schedule;
import shop.core.Technician;

import java.awt.event.ActionEvent;

import shop.core.Schedule;

public class ViewScheduleW extends JFrame {
	//Component vars
	JLabel lblTechnician;
	
	//Connection vars
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	int shiftNum;
	String date;
	String timeRange;

	private JPanel contentPane;

	public ViewScheduleW(Connection c) {
		con = c;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate.setBounds(31, 29, 120, 14);
		contentPane.add(lblDate);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(31, 82, 120, 14);
		contentPane.add(lblTime);
		
		JComboBox comboBox_Times = new JComboBox();
		comboBox_Times.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeRange = comboBox_Times.getSelectedItem().toString();
				
				
			}
		});
		comboBox_Times.setBounds(190, 79, 180, 20);
		contentPane.add(comboBox_Times);
		
		JComboBox comboBox_Dates = new JComboBox();
		comboBox_Dates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date = comboBox_Dates.getSelectedItem().toString();
				setTimesBox(comboBox_Times, lblTechnician);
			}
		});
		comboBox_Dates.setBounds(190, 26, 180, 20);
		contentPane.add(comboBox_Dates);
		
		//Initialize boxes
		setDatesBox(comboBox_Dates);
		setTimesBox(comboBox_Times, lblTechnician);
		
		JLabel lblOnStaff = new JLabel("Technician on staff:");
		lblOnStaff.setHorizontalAlignment(SwingConstants.LEFT);
		lblOnStaff.setBounds(31, 127, 120, 14);
		contentPane.add(lblOnStaff);
		
		lblTechnician = new JLabel("Technician");
		lblTechnician.setHorizontalAlignment(SwingConstants.LEFT);
		lblTechnician.setBounds(190, 127, 180, 14);
		contentPane.add(lblTechnician);
		
		JLabel lblAppointmentSchd = new JLabel("Appointment Scheduled: ");
		lblAppointmentSchd.setHorizontalAlignment(SwingConstants.CENTER);
		lblAppointmentSchd.setBounds(31, 172, 120, 14);
		contentPane.add(lblAppointmentSchd);
		
		JLabel lblAppointmetn = new JLabel("No appointment scheduled");
		lblAppointmetn.setBounds(190, 172, 180, 14);
		contentPane.add(lblAppointmetn);
		
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHome.setBounds(31, 227, 89, 23);
		contentPane.add(btnHome);
		
		JButton btnViewAppointment = new JButton("View Appointment");
		btnViewAppointment.setBounds(190, 227, 135, 23);
		contentPane.add(btnViewAppointment);
	}
	
	public ComboBoxModel<String> getDates() {
		DefaultComboBoxModel<String> datesList = new DefaultComboBoxModel<String>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT DISTINCT SHIFT_DAY FROM SCHEDULE;");
			while(myRs.next()) {
				datesList.addElement(myRs.getString("SHIFT_DAY"));
			}
		} catch (SQLException eDatesGet) {
			eDatesGet.printStackTrace();
			System.out.println("Failure to retrieve schedule dates");
		}
		return datesList;
	}
	
	public void setDatesBox(JComboBox<String> DatesBox) {
		DatesBox.setModel(getDates());
		DatesBox.setSelectedIndex(0);
		date = DatesBox.getSelectedItem().toString();
	}
	
	public ComboBoxModel<String> getTimes(String date) {
		DefaultComboBoxModel<String> timesList = new DefaultComboBoxModel<String>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT SHIFT_TIME, SHIFT_TIME_END FROM SCHEDULE WHERE SHIFT_DAY = \"" + date + "\";");
			while(myRs.next()) {
				timesList.addElement(myRs.getString("SHIFT_TIME") + " to " + myRs.getString("SHIFT_TIME_END"));
			}
		} catch (SQLException eTimesGet) {
			eTimesGet.printStackTrace();
			System.out.println("Failure to retrieve schedule times");
		}
		return timesList;
	}
	
	public void setTimesBox(JComboBox<String> TimesBox, JLabel tech) {
		TimesBox.setModel(getTimes(date));
		TimesBox.setSelectedIndex(0);
		timeRange = TimesBox.getSelectedItem().toString();
		tech.setText(getTechnician().toString());
	}
	
	
	public Technician getTechnician() {
		Technician tech = null;
		try{
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT SHIFT_NUM WHERE SHIFT_DAY = \"" + date + "\" AND SHIFT_TIME = \"" + timeRange.substring(0, 7) + "\";");
			shiftNum = myRs.getInt("SHIFT_NUM");
			try {
				myRs = myStmt.executeQuery("SELECT TECHNICIAN.* FROM TECHNICIAN, SCHEDULE WHERE SHIFT_NUM = " + shiftNum + " AND TECHNICIAN.EMP_NUM = SCHEDULE.EMP_NUM");
				tech = new Technician(shiftNum, myRs.getString("EMP_FNAME"), myRs.getString("EMP_LNAME"),
						myRs.getString("EMP_CONTACT"), myRs.getFloat("EMP_RATING"), myRs.getInt("EMP_RATING_COUNT"), myRs.getFloat("EMP_WAGE"));
			} catch (SQLException eTechGet) {
				eTechGet.printStackTrace();
				System.out.println("Error retrieving technician");
			}
		} catch (SQLException eShiftGet) {
			eShiftGet.printStackTrace();
			System.out.println("Error retrieving shift index");
		}
		return tech;
	}
}
