package shop.dao;


import java.util.*;
import java.sql.*;
import java.io.*;

abstract public class EntityDAO {
	protected Connection myConn;
	
	String host = "localhost";
	String user;
	String password;
	String dbName = "carinfo";
	String dburl = "jdbc:mysql://" + host + "/"+ dbName + "?user=" + user + "&password=" + password;
	
	public EntityDAO() throws Exception {
		//Properties props = new Properties();
		//props.load(new FileInputStream("demo.properties"));

		host = "localhost";
		
		// Note: removed props.getProperty() from string declarations
		myConn = DriverManager.getConnection(dburl);
		System.out.println("DB connection successful to: " + dburl);
	}
	
	abstract public List<Object> getAllEntities() throws Exception;
	
	abstract protected Object convertRowToEntity(ResultSet myRs) throws Exception;
	
	protected static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {

		}
		if (myConn != null) {
			myConn.close();
		}
	}

	protected void close(Statement myStmt, ResultSet myRs) throws SQLException {
		close(null, myStmt, myRs);
	}
}