package shop.dao;

import java.util.*;
import java.sql.*;
import java.io.*;

public class DAO {
	protected Connection con;
	
	String host;
	String user;
	String password;
	String dbName;
	String dburl;
	
	public DAO(String u, String p) {
		host = "localhost";
		dbName = "carInfo";
		user = u;
		password = p;
		dburl = "jdbc:mysql://" + host + "/"+ dbName + "?user=" + user + "&password=" + password;
	}
	
	public Connection getCon() {
		try {
			con = DriverManager.getConnection(dburl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
