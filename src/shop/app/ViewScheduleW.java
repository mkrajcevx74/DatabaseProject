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
import shop.core.Owner;

import java.awt.event.ActionEvent;

public class ViewScheduleW extends JFrame {
	//Component vars
	//JLabel lblTechnician;
	
	//Connection vars
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	

	String date;
	String timeRange;

	Schedule shift = null;
	Technician tech = null;
	Appointment apt = null;
	Owner own = null;

	private JPanel contentPane;

	public ViewScheduleW(Connection c) {
		con = c;
		
		//Panel vars
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Date label
		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.LEFT);
		lblDate.setBounds(31, 29, 120, 14);
		contentPane.add(lblDate);
		
		//Time label
		JLabel lblTime = new JLabel("Time:");
		lblTime.setBounds(31, 82, 120, 14);
		contentPane.add(lblTime);

		//Technician head label
		JLabel lblOnStaff = new JLabel("Technician on staff:");
		lblOnStaff.setHorizontalAlignment(SwingConstants.LEFT);
		lblOnStaff.setBounds(31, 127, 120, 14);
		contentPane.add(lblOnStaff);
		
		//Technician display label
		JLabel lblTechnician = new JLabel("Technician");
		lblTechnician.setHorizontalAlignment(SwingConstants.LEFT);
		lblTechnician.setBounds(190, 127, 180, 14);
		contentPane.add(lblTechnician);
		
		//Appointment head label
		JLabel lblAppointmentSchd = new JLabel("Appointment Scheduled: ");
		lblAppointmentSchd.setHorizontalAlignment(SwingConstants.CENTER);
		lblAppointmentSchd.setBounds(31, 172, 120, 14);
		contentPane.add(lblAppointmentSchd);
		
		//Appointment display label
		JLabel lblAppointment = new JLabel("No appointment scheduled");
		lblAppointment.setBounds(190, 172, 180, 14);
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
		comboBox_Times.setBounds(190, 79, 180, 20);
		contentPane.add(comboBox_Times);
		
		//Dates box
		JComboBox<String> comboBox_Dates = new JComboBox<String>();
		comboBox_Dates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date = comboBox_Dates.getSelectedItem().toString();
				setTimesBox(comboBox_Times);
			}
		});
		comboBox_Dates.setBounds(190, 26, 180, 20);
		contentPane.add(comboBox_Dates);
		
		//Home button
		JButton btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppHomeW ahw = new AppHomeW(con);
				ahw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnHome.setBounds(31, 227, 89, 23);
		contentPane.add(btnHome);
		
		//View appointment button
		JButton btnViewAppointment = new JButton("View Appointment");
		btnViewAppointment.setBounds(190, 227, 135, 23);
		contentPane.add(btnViewAppointment);
		
		//Initialize boxes
		setDatesBox(comboBox_Dates);
		setTimesBox(comboBox_Times);
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
	
	//Populate dates box
	public void setDatesBox(JComboBox<String> DatesBox) {
		DatesBox.setModel(getDates());
		DatesBox.setSelectedIndex(0);
		date = DatesBox.getSelectedItem().toString();
	}
	
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
	
	//Populate times box
	public void setTimesBox(JComboBox<String> TimesBox) {
		TimesBox.setModel(getTimes(date));
		TimesBox.setSelectedIndex(0);
		timeRange = TimesBox.getSelectedItem().toString();
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
				own = new Owner(myRs.getString("VIN"), myRs.getInt("CUS_NUM"), myRs.getInt("VCL_NUM"), myRs.getInt("OWN_MILES"), myRs.getString("OWN_RECORD"));
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
				myRs = myStmt.executeQuery("SELECT CUSTOMER.*, VEHICLE.*, SERVICE.* FROM APPOINTMENT, OWNER, CUSTOMER, VEHICLE, SERVICE WHERE OWNER.VIN = \"" + own.getVin() + 
						"\" AND OWNER.CUS_NUM = CUSTOMER.CUS_NUM AND OWNER.VCL_NUM = VEHICLE.VCL_NUM AND APPOINTMENT.SERV_NUM =rooroo SERVICE.SERV_NUM;");
				myRs.next();
				cus = new Customer(myRs.getInt("CUS_NUM"), myRs.getString("CUS_FNAME"), myRs.getString("CUS_LNAME"), myRs.getString("CUS_CONTACT"));
				vcl = new Vehicle(myRs.getInt("VCL_NUM"), myRs.getString("VCL_MAKE"), myRs.getString("VCL_MODEL"), myRs.getInt("VCL_YEAR"), myRs.getString("VCL_MISC"));
				serv = new Service(myRs.getInt("SERV_NUM"), myRs.getString("SERV_DESC"));
			} catch (SQLException eDisplayGet) {
				eDisplayGet.printStackTrace();
				System.out.println("Error retrieving appointment display");
			}
		}
		return display;
	}
}