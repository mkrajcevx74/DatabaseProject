package shop.core;

public class Recommendation {
	//recommendation attributes
	private int recNum;
	private String vin;
	private int servNum;
	
	//class entity constructor
	public Recommendation(int newRecNum, String newVin, int newServNum) {
		recNum = newRecNum;
		vin = newVin;
		servNum = newServNum;
	}
	//Return recommendations number
	public int getRecNum() {
		return recNum;
	}
		
	//Set recommendations number
	public void setNum(int newNum) {
		recNum = newNum;
	}
		
	//Return VIN
	public String getVIN() {
		return vin;
	}
		
	//Set VIN
	public void setVIN(String newVIN) {
		vin = newVIN;
	}
	
	//Return service number
	public int getServNum() {
		return servNum;
	}
			
	//Set service number
	public void setServNum(int newServiceNum) {
		servNum = newServiceNum;
	}
	
	//Add recommendation query
	public String  addRecStmt() {
		return "INSERT INTO RECOMMENDATION VALUES(NULL, \"" +  vin + "\", "  + servNum + ");";
	}
	
	public String delRecStmt() {
		return "DELETE FROM RECOMMENDATION WHERE VIN = \"" + vin + "\" AND SERV_NUM = " + servNum + ";";
	}
}
