package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Schedule;
import shop.core.Service;
import shop.core.Technician;
import shop.core.Vehicle;
import shop.core.Appointment;
import shop.core.Customer;
import shop.core.Ownership;

public class ScheduleViewW extends JFrame {
	//Component variables
	private JPanel contentPane;
	
	//Connection variables
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	//Entity variables
	String date;
	String timeRange;
	Schedule shift = null;
	Technician tech = null;
	Appointment apt = null;
	Ownership own = null;

	public ScheduleViewW(Connection c) {
		con = c;
		
		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Date label
		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate.setBounds(10, 29, 120, 14);
		contentPane.add(lblDate);
		
		//Time label
		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(10, 82, 120, 14);
		contentPane.add(lblTime);

		//Technician head label
		JLabel lblOnStaff = new JLabel("Technician on staff:");
		lblOnStaff.setHorizontalAlignment(SwingConstants.LEFT);
		lblOnStaff.setBounds(10, 127, 120, 14);
		contentPane.add(lblOnStaff);
		
		//Technician display label
		JLabel lblTechnician = new JLabel("Technician");
		lblTechnician.setHorizontalAlignment(SwingConstants.LEFT);
		lblTechnician.setBounds(154, 127, 270, 14);
		contentPane.add(lblTechnician);
		
		//Appointment head label
		JLabel lblAppointmentSchd = new JLabel("Appointment Scheduled: ");
		lblAppointmentSchd.setBounds(10, 165, 167, 14);
		contentPane.add(lblAppointmentSchd);
		
		//Appointment display label
		JLabel lblAppointment = new JLabel("No appointment scheduled");
		lblAppointment.setBounds(31, 190, 393, 14);
		contentPane.add(lblAppointment);
		
		//Times box
		JComboBox<String> comboBox_Times = new JComboBox<String>();
		comboBox_Times.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeRange = comboBox_Times.getSelectedItem().toString();
				getSchedule();
				getTechnician();
				lblTechnician.setText(tech.toString());
				getAppointment();
				getOwn();
				lblAppointment.setText(getAptDisplay());
			}
		});
		comboBox_Times.setBounds(154, 79, 180, 20);
		contentPane.add(comboBox_Times);
		
		//Dates box
		JComboBox<String> comboBox_Dates = new JComboBox<String>();
		comboBox_Dates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date = comboBox_Dates.getSelectedItem().toString();
				setComboBox(comboBox_Times, 2);
			}
		});
		comboBox_Dates.setBounds(154, 26, 180, 20);
		contentPane.add(comboBox_Dates);
		setComboBox(comboBox_Dates, 1);
		
		//Home button
		JButton btnHome = new JButton("Back");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppHomeW ahw = new AppHomeW(con);
				ahw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnHome.setBounds(10, 225, 130, 25);
		contentPane.add(btnHome);
	}
	
	//Return dates
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
	
//	//Populate dates box
//	public void setDatesBox(JComboBox<String> DatesBox) {
//		DatesBox.setModel(getDates());
//		DatesBox.setSelectedIndex(0);
//	}
	
	//Return times
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
	
	//Populate combo box
	public void setComboBox(JComboBox<String> box, int n) {
		DefaultComboBoxModel<String> m = new DefaultComboBoxModel<String>();
		if (n==1) {
			m = (DefaultComboBoxModel<String>) getDates();
		} else if (n==2) {
			m = (DefaultComboBoxModel<String>) getTimes(date);
		}
		box.setModel(m);
		if(box.getItemCount() > 0) {
			box.setSelectedIndex(0);
		}
	}
	
	//Get selected schedule as entity
	public void getSchedule() {
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM SCHEDULE WHERE SHIFT_DAY = \"" + date + "\" AND SHIFT_TIME = \"" + timeRange.substring(0, 8) + "\";");
			myRs.next();
			shift = new Schedule(myRs.getInt("SHIFT_NUM"), myRs.getString("SHIFT_DAY"), myRs.getString("SHIFT_TIME"), myRs.getString("SHIFT_TIME_END"), myRs.getInt("EMP_NUM"));
		} catch (SQLException eShiftGet) {
			eShiftGet.printStackTrace();
			System.out.println("Error retrieving shift index");
		}
	}
	
	//Get scheduled technician
	public void getTechnician() {
		try {
			myRs = myStmt.executeQuery("SELECT TECHNICIAN.* FROM TECHNICIAN, SCHEDULE WHERE SHIFT_NUM = " + shift.getNum() + " AND TECHNICIAN.EMP_NUM = SCHEDULE.EMP_NUM");
			myRs.next();
			tech = new Technician(shift.getNum(), myRs.getString("EMP_FNAME"), myRs.getString("EMP_LNAME"),
					myRs.getString("EMP_CONTACT"), myRs.getFloat("EMP_RATING"), myRs.getInt("EMP_RATING_COUNT"), myRs.getFloat("EMP_WAGE"));
		} catch (SQLException eTechGet) {
			eTechGet.printStackTrace();
			System.out.println("Error retrieving technician");
		}
	}
	
	//Get scheduled appointment
	public void getAppointment() {
		try {
			myRs = myStmt.executeQuery("SELECT * FROM APPOINTMENT WHERE SHIFT_NUM = " + shift.getNum());
			if (myRs.next()) {
				apt = new Appointment(myRs.getInt("APT_NUM"), myRs.getString("VIN"), shift.getNum(), myRs.getInt("SERV_NUM"));
			} else { apt = null; }
		} catch (SQLException eAppGet) {
			eAppGet.printStackTrace();
			System.out.println("Error retrieving appointment");
		}
	}
	
	//Get appointment owner
	public void getOwn() {
		if (apt != null) {
			try {
				myRs = myStmt.executeQuery("SELECT * FROM OWNER WHERE VIN = \"" + apt.getVin() + "\";");
				myRs.next();
				own = new Ownership(myRs.getString("VIN"), myRs.getInt("CUS_NUM"), myRs.getInt("VCL_NUM"), myRs.getInt("OWN_MILES"), myRs.getString("OWN_RECORD"));
			} catch(SQLException eGetOwner) {
				eGetOwner.printStackTrace();
				System.out.println("Error retrieving owner");
			}
		} else { own = null; }
	}
	
	//Get appointment display string
	public String getAptDisplay() {
		String display = "No appointment scheduled";
		Customer cus = null;
		Vehicle vcl = null;
		Service serv = null;
		if (own != null) {
			try {
				myRs = myStmt.executeQuery(apt.aptDisplayQuery());
				myRs.next();
				cus = new Customer(myRs.getInt("CUS_NUM"), myRs.getString("CUS_FNAME"), myRs.getString("CUS_LNAME"), myRs.getString("CUS_CONTACT"));
				vcl = new Vehicle(myRs.getInt("VCL_NUM"), myRs.getString("VCL_MAKE"), myRs.getString("VCL_MODEL"), myRs.getInt("VCL_YEAR"), myRs.getString("VCL_MISC"));
				serv = new Service(myRs.getInt("SERV_NUM"), myRs.getString("SERV_DESC"));
				display = serv.getDesc() + " for " + cus + "'s " + vcl;
			} catch (SQLException eDisplayGet) {
				eDisplayGet.printStackTrace();
				System.out.println("Error retrieving appointment display");
			}
		}
		return display;
	}
}