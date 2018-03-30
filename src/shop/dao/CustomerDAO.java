package shop.dao;

import java.util.*;
import java.sql.*;
import shop.core.Customer;

public class CustomerDAO extends EntityDAO {
	public CustomerDAO(String host, String user, String password, String dbName, String dburl) throws Exception {
		super();
	}
	
	@Override
	public List<Object> getAllEntities() throws Exception {
		List<Object> list = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from customer");
			
			while(myRs.next()) {
				Customer temp = convertRowToEntity(myRs);
				list.add(temp);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}

	public List<Customer> searchCustomers(String lName) throws Exception {
		List<Customer> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			lName += "%";
			myStmt = myConn.prepareStatement("select * from customer where cus_lname like ?");
			myStmt.setString(1, lName);
			myRs = myStmt.executeQuery();
			
			while(myRs.next()) {
				Customer temp = convertRowToEntity(myRs);
				list.add(temp);
			}

			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	@Override
	protected Customer convertRowToEntity(ResultSet myRs) throws SQLException {
		int num = myRs.getInt("cus_num");
		String fName = myRs.getString("cus_fname");
		String lName = myRs.getString("cus_lname");
		String contact = myRs.getString("cus_contact");
		
		Customer temp = new Customer(num, fName, lName, contact);
		return temp;
	}
}
