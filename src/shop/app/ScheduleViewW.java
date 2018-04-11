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
	private JButton btnScheduleApt;
	private JButton btnCloseApt;
	
	//Connection variables
	private Connection con;
	private Statement myStmt = null;
	private ResultSet myRs = null;
	
	//Entity variables
	private String date;
	private String timeRange;
	//Schedule shift = null;
	
	private boolean isGeneric = true;

	public ScheduleViewW(Connection c, Ownership own, Service serv) {
		con = c;
		if ((own!=null) && (serv!=null)) {
			isGeneric = false;
		}
		
		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Date label
		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblDate.setBounds(10, 28, 180, 14);
		contentPane.add(lblDate);
		
		//Time label
		JLabel lblTime = new JLabel("Time:");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setBounds(227, 28, 180, 14);
		contentPane.add(lblTime);

		//Technician head label
		JLabel lblOnStaff = new JLabel("Technician on staff:");
		lblOnStaff.setHorizontalAlignment(SwingConstants.LEFT);
		lblOnStaff.setBounds(10, 91, 120, 14);
		contentPane.add(lblOnStaff);
		
		//Technician display label
		JLabel lblTechnician = new JLabel("Technician");
		lblTechnician.setHorizontalAlignment(SwingConstants.LEFT);
		lblTechnician.setBounds(154, 91, 270, 14);
		contentPane.add(lblTechnician);
		
		//Appointment head label
		JLabel lblAppointmentSchd = new JLabel("Appointment Scheduled: ");
		lblAppointmentSchd.setBounds(10, 116, 167, 14);
		contentPane.add(lblAppointmentSchd);
		
		//Appointment display label
		JLabel lblAppointment = new JLabel("No appointment scheduled");
		lblAppointment.setBounds(20, 132, 393, 14);
		contentPane.add(lblAppointment);
		
		//Times box
		JComboBox<String> comboBox_Times = new JComboBox<String>();
		comboBox_Times.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeRange = comboBox_Times.getSelectedItem().toString();
				lblTechnician.setText(getTec().toString());
				lblAppointment.setText(getAptDisplay());
			}
		});
		comboBox_Times.setBounds(227, 48, 180, 20);
		contentPane.add(comboBox_Times);
		
		//Dates box
		JComboBox<String> comboBox_Dates = new JComboBox<String>();
		comboBox_Dates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				date = comboBox_Dates.getSelectedItem().toString();
				setComboBox(comboBox_Times, 2);
			}
		});
		comboBox_Dates.setBounds(10, 48, 180, 20);
		contentPane.add(comboBox_Dates);
		setComboBox(comboBox_Dates, 1);
		
		//Add appointment button
		btnScheduleApt = new JButton("Schedule");
		btnScheduleApt.setBounds(294, 225, 130, 25);
		contentPane.add(btnScheduleApt);
		btnScheduleApt.setVisible(false);
		
		//Delete appointment button
		btnCloseApt = new JButton("Close Appointment");
		btnCloseApt.setBounds(294, 178, 130, 25);
		contentPane.add(btnCloseApt);
		btnCloseApt.setVisible(false);
		
		//Home button
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppHomeW ahw = new AppHomeW(con);
				ahw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnBack.setBounds(10, 225, 130, 25);
		contentPane.add(btnBack);
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
	public Schedule getShift() {
		Schedule tempShift = null;
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM SCHEDULE WHERE SHIFT_DAY = \"" + date + "\" AND SHIFT_TIME = \"" + timeRange.substring(0, 8) + "\";");
			myRs.next();
			tempShift = new Schedule(myRs.getInt("SHIFT_NUM"), myRs.getString("SHIFT_DAY"), myRs.getString("SHIFT_TIME"), myRs.getString("SHIFT_TIME_END"), myRs.getInt("EMP_NUM"));
		} catch (SQLException eShiftGet) {
			eShiftGet.printStackTrace();
			System.out.println("Error retrieving shift index");
		}
		return tempShift;
	}
	
	//Get scheduled technician
	public Technician getTec() {
		Technician tec = null;
		Schedule tempShift = getShift();
		try {
			myRs = myStmt.executeQuery("SELECT TECHNICIAN.* FROM TECHNICIAN, SCHEDULE WHERE SHIFT_NUM = " + tempShift.getNum() + " AND TECHNICIAN.EMP_NUM = SCHEDULE.EMP_NUM");
			myRs.next();
			tec = new Technician(tempShift.getNum(), myRs.getString("EMP_FNAME"), myRs.getString("EMP_LNAME"),
					myRs.getString("EMP_CONTACT"), myRs.getFloat("EMP_RATING"), myRs.getInt("EMP_RATING_COUNT"), myRs.getFloat("EMP_WAGE"));
		} catch (SQLException eTechGet) {
			eTechGet.printStackTrace();
			System.out.println("Error retrieving technician");
		}
		return tec;
	}
	
	//Get scheduled appointment
	public Appointment getApt() {
		Appointment tempApt = null;
		Schedule tempShift = getShift();
		try {
			myRs = myStmt.executeQuery("SELECT * FROM APPOINTMENT WHERE SHIFT_NUM = " + tempShift.getNum());
			if (myRs.next()) {
				tempApt = new Appointment(myRs.getInt("APT_NUM"), myRs.getString("VIN"), tempShift.getNum(), myRs.getInt("SERV_NUM"));
			}
		} catch (SQLException eAppGet) {
			eAppGet.printStackTrace();
			System.out.println("Error retrieving appointment");
		}
		return tempApt;
	}
	
	//Get appointment owner
	public Ownership getOwn(Appointment tempApt) {
		Ownership tempOwn = null;
		if (tempApt != null) {
			try {
				myRs = myStmt.executeQuery("SELECT * FROM OWNER WHERE VIN = \"" + tempApt.getVin() + "\";");
				myRs.next();
				tempOwn = new Ownership(myRs.getString("VIN"), myRs.getInt("CUS_NUM"), myRs.getInt("VCL_NUM"), myRs.getInt("OWN_MILES"), myRs.getString("OWN_RECORD"));
			} catch(SQLException eOwnGet) {
				eOwnGet.printStackTrace();
				System.out.println("Error retrieving owner");
			}
		}
		return tempOwn;
	}
	
	//Get appointment display string
	public String getAptDisplay() {
		String display = "No appointment scheduled";
		Customer tempCus = null;
		Vehicle tempVcl = null;
		Service tempServ = null;
		Appointment tempApt = getApt();
		Ownership tempOwn = getOwn(tempApt);
		if (tempOwn != null) {
			try {
				myRs = myStmt.executeQuery(tempApt.aptDisplayQuery());
				myRs.next();
				tempCus = new Customer(myRs.getInt("CUS_NUM"), myRs.getString("CUS_FNAME"), myRs.getString("CUS_LNAME"), myRs.getString("CUS_CONTACT"));
				tempVcl = new Vehicle(myRs.getInt("VCL_NUM"), myRs.getString("VCL_MAKE"), myRs.getString("VCL_MODEL"), myRs.getInt("VCL_YEAR"), myRs.getString("VCL_MISC"));
				tempServ = new Service(myRs.getInt("SERV_NUM"), myRs.getString("SERV_DESC"));
				display = tempServ.getDesc() + " for " + tempCus + "'s " + tempVcl;
			} catch (SQLException eDisplayGet) {
				eDisplayGet.printStackTrace();
				System.out.println("Error retrieving appointment display");
			}
		}
		return display;
	}
	
	//Update buttons
	public void setButtons() {
		//If we got here from home
		if (isGeneric) {
			//If there is an appointment already scheduled
			if (getApt()!=null) {
				
			//If there is no appointment
			} else {
				
			}
		//If we got here from a customer
		} else {
			if (getApt()!=null) {
				
			} else {
				
			}
		}
	}
}