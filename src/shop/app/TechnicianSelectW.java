package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;

import shop.core.Technician;

public class TechnicianSelectW extends JFrame{
	//Component variables
	private JPanel contentPane;
	
	//Connection variables
	Connection con;
	Statement myStmt = null;
	ResultSet myRs = null;
	
	//Entity variables
	Technician tec = null;

	//Employee selection screen constructor
	public TechnicianSelectW(Connection c) {
		//Parameter declarations
		con = c;
		
		//Panel initialization
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Technician label
		JLabel lblTec = new JLabel("Technician:");
		lblTec.setBounds(31, 13, 77, 16);
		contentPane.add(lblTec);
		
		//Contact number label
		JLabel lblContact = new JLabel("Contact:");
		lblContact.setBounds(31, 99, 56, 16);
		contentPane.add(lblContact);
		
		//Rating label
		JLabel lblRating = new JLabel("Rating:");
		lblRating.setBounds(31, 126, 56, 16);
		contentPane.add(lblRating);
		
		//Wage label
		JLabel lblWage = new JLabel("Wage:");
		lblWage.setBounds(31, 158, 56, 16);
		contentPane.add(lblWage);
		
		//Contact display
		JLabel lblContactDisplay = new JLabel("New label");
		lblContactDisplay.setBounds(144, 100, 150, 16);
		contentPane.add(lblContactDisplay);
		
		//Rating display
		JLabel lblRatingDisplay = new JLabel("New label");
		lblRatingDisplay.setBounds(144, 129, 150, 16);
		contentPane.add(lblRatingDisplay);
		
		//Wage display
		JLabel lblWageDisplay = new JLabel("New label");
		lblWageDisplay.setBounds(144, 158, 150, 16);
		contentPane.add(lblWageDisplay);
		
		//Technician display box
		JComboBox<Technician> comboBox_Tec = new JComboBox<Technician>();
		comboBox_Tec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblContactDisplay.setText(((Technician) comboBox_Tec.getSelectedItem()).getContact());
				lblRatingDisplay.setText(Float.toString(((Technician) comboBox_Tec.getSelectedItem()).getRating()));
				lblWageDisplay.setText(Float.toString(((Technician) comboBox_Tec.getSelectedItem()).getWage()));
			}
		});
		comboBox_Tec.setBounds(120, 10, 274, 22);
		contentPane.add(comboBox_Tec);
		setTecsBox(comboBox_Tec);
		
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
	
	//Return technicians
	public ComboBoxModel<Technician> getTechnicians() {
		DefaultComboBoxModel<Technician> tecList = new DefaultComboBoxModel<Technician>();
		try {
			myStmt = con.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM TECHNICIAN;");
			while (myRs.next()) {
				tec = new Technician(myRs.getInt("EMP_NUM"), myRs.getString("EMP_FNAME"), myRs.getString("EMP_LNAME"),
						myRs.getString("EMP_CONTACT"), myRs.getInt("EMP_RATING"), myRs.getInt("EMP_RATING_COUNT"), myRs.getInt("EMP_WAGE"));
				tecList.addElement(tec);
			}
		} catch (SQLException eTecGet) {
			eTecGet.printStackTrace();
			System.out.println("Technician retrieval failure");
		}
		return tecList;
	}
	
	//Populate technicians box
	public void setTecsBox(JComboBox<Technician> TecsBox) {
		TecsBox.setModel(getTechnicians());
		TecsBox.setSelectedIndex(0);
		tec = (Technician) TecsBox.getSelectedItem();
	}
}
