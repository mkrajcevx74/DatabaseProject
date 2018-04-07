package shop.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import shop.core.Customer;

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
			
			JLabel lblNewLabel = new JLabel("Technician:");
			lblNewLabel.setBounds(31, 13, 77, 16);
			contentPane.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("Number:");
			lblNewLabel_1.setBounds(31, 42, 56, 16);
			contentPane.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("First Name:");
			lblNewLabel_2.setBounds(31, 71, 77, 16);
			contentPane.add(lblNewLabel_2);
			
			JLabel lblNewLabel_3 = new JLabel("Last Name:");
			lblNewLabel_3.setBounds(31, 100, 77, 16);
			contentPane.add(lblNewLabel_3);
			
			JLabel lblNewLabel_4 = new JLabel("Contact");
			lblNewLabel_4.setBounds(31, 129, 56, 16);
			contentPane.add(lblNewLabel_4);
			
			JLabel lblNewLabel_5 = new JLabel("Rating");
			lblNewLabel_5.setBounds(31, 158, 56, 16);
			contentPane.add(lblNewLabel_5);
			
			JLabel lblNewLabel_6 = new JLabel("Rating Count:");
			lblNewLabel_6.setBounds(31, 187, 89, 16);
			contentPane.add(lblNewLabel_6);
			
			JLabel lblNewLabel_12 = new JLabel("Wage:");
			lblNewLabel_12.setBounds(31, 216, 56, 16);
			contentPane.add(lblNewLabel_12);
			
			JLabel lblEmpNum = new JLabel("New label");
			lblEmpNum.setBounds(144, 45, 89, 16);
			contentPane.add(lblEmpNum);
			
			JLabel lblNewLabel_8 = new JLabel("New label");
			lblNewLabel_8.setBounds(144, 71, 56, 16);
			contentPane.add(lblNewLabel_8);
			
			JLabel lblNewLabel_9 = new JLabel("New label");
			lblNewLabel_9.setBounds(144, 100, 56, 16);
			contentPane.add(lblNewLabel_9);
			
			JLabel lblNewLabel_10 = new JLabel("New label");
			lblNewLabel_10.setBounds(144, 129, 56, 16);
			contentPane.add(lblNewLabel_10);
			
			JLabel lblNewLabel_11 = new JLabel("New label");
			lblNewLabel_11.setBounds(144, 158, 56, 16);
			contentPane.add(lblNewLabel_11);
			
			JLabel lblNewLabel_13 = new JLabel("New label");
			lblNewLabel_13.setBounds(144, 187, 56, 16);
			contentPane.add(lblNewLabel_13);
			
			JLabel lblNewLabel_14 = new JLabel("New label");
			lblNewLabel_14.setBounds(144, 216, 56, 16);
			contentPane.add(lblNewLabel_14);
			
			JComboBox comboBox_Tec = new JComboBox();
			comboBox_Tec.setBounds(120, 10, 274, 22);
			contentPane.add(comboBox_Tec);
			comboBox_Tec.setModel(getTechnicians());
			
			con = c;
			
			public ComboBoxModel<Techinician> getTechnicians() {
				DefaultComboBoxModel<Techinician> tecList = new DefaultComboBoxModel<Techinician>();
				Techinician tec;
				try {
					myStmt = con.createStatement();
					myRs = myStmt.executeQuery("SELECT * FROM TECHNICIAN;");
					while (myRs.next()) {
						tec = new Technician(myRs.getInt("EMP_NUM"), myRs.getString("EMP_FNAME"), myRs.getString("EMP_LNAME"), myRs.getString("EMP_CONTACT"));
						tecList.addElement(tec);
					}
				} catch (SQLException eTecGet) {
					eTecGet.printStackTrace();
					System.out.println("Technician retrieval failure");
				}
				return tecList;
				
			}
		}
}
