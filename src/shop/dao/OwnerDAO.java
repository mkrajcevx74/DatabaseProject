package shop.dao;

import java.util.*;
import java.sql.*;
import shop.core.Owner;

public class OwnerDAO extends EntityDAO{
	public OwnerDAO(String host, String user, String password, String dbName, String dburl) throws Exception {
		super(host, user, password, dbName, dburl);
	}
	
	@Override
	public List<Object> getAllEntities() throws Exception {
		List<Object> list = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from owners");
			
			while(myRs.next()) {
				Owner temp = convertRowToEntity(myRs);
				list.add(temp);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public List<Owner> searchOwners(String vin) throws Exception {
		List<Owner> list = new ArrayList<>();
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			vin += "%";
			myStmt = myConn.prepareStatement("select * from owners where vin like ?");
			myStmt.setString(1, vin);
			myRs = myStmt.executeQuery();
			
			while(myRs.next()) {
				Owner temp = convertRowToEntity(myRs);
				list.add(temp);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	@Override
	protected Owner convertRowToEntity(ResultSet myRs) throws SQLException {
		String vin = myRs.getString("vin");
		int cNum = myRs.getInt("cus_num");
		int vNum = myRs.getInt("vcl_num");
		int year = myRs.getInt("own_miles");
		String record = myRs.getString("own_record");
		
		Owner temp = new Owner(vin, cNum, vNum, year, record);
		return temp;
	}
}