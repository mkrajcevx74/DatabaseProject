package shop.dao;

import java.util.*;
import java.sql.*;
import shop.core.Vehicle;

public class VehicleDAO extends EntityDAO {
	public VehicleDAO() throws Exception {
		super();
	}
	
	@Override
	public List<Object> getAllEntities() throws Exception {
		List<Object> list = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("select * from vehicle");
			
			while(myRs.next()) {
				Vehicle temp = convertRowToEntity(myRs);
				list.add(temp);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}

	public List<Vehicle> searchVehicles(String model) throws Exception {
		List<Vehicle> list = new ArrayList<>();
		
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			model += "%";
			myStmt = myConn.prepareStatement("select * from vehicle where vcl_model like ?");
			myStmt.setString(1, model);
			myRs = myStmt.executeQuery();
			
			while(myRs.next()) {
				Vehicle temp = convertRowToEntity(myRs);
				list.add(temp);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	@Override
	protected Vehicle convertRowToEntity(ResultSet myRs) throws SQLException {
		int num = myRs.getInt("vcl_num");
		String make = myRs.getString("vcl_make");
		String model = myRs.getString("vcl_model");
		int year = myRs.getInt("vcl_year");
		String misc = myRs.getString("vcl_misc");
		
		Vehicle temp = new Vehicle(num, make, model, year, misc);
		return temp;
	}

	public static void main(String[] args) throws Exception {
		VehicleDAO dao = new VehicleDAO();
		System.out.println(dao.searchVehicles("9-3"));
		System.out.println(dao.getAllEntities());
	}
}