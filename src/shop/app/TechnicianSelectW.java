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
	private Connection con;
	private Statement myStmt = null;
	private ResultSet myRs = null;
	
	//Entity variables
	private Technician tec = null;

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
		JLabel lblTec = new JLabel("Select a technician:");
		lblTec.setHorizontalAlignment(SwingConstants.CENTER);
		lblTec.setBounds(82, 58, 274, 16);
		contentPane.add(lblTec);
		
		//Technician display box
		JComboBox<Technician> comboBox_Tec = new JComboBox<Technician>();
		comboBox_Tec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tec = (Technician) comboBox_Tec.getSelectedItem();
			}
		});
		comboBox_Tec.setBounds(82, 85, 274, 22);
		contentPane.add(comboBox_Tec);
		setTecsBox(comboBox_Tec);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TechnicianProfileW tpw = new TechnicianProfileW(con, tec);
				tpw.setVisible(true);
				((Window) contentPane.getTopLevelAncestor()).dispose();
			}
		});
		btnSelect.setBounds(294, 225, 130, 25);
		contentPane.add(btnSelect);
		
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
