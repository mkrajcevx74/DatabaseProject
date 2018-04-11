package shop.core;

public class Appointment {
	//Appointment attributes
	private int num;
	private String vin;
	private int shiftNum;
	private int servNum;
	
	//Appointment entity constructor
	public Appointment(int newNum, String newVin, int newShiftNum, int newServNum) {
		num = newNum;
		vin = newVin;
		shiftNum = newShiftNum;
		servNum = newServNum;
	}
	
	//Return appointment index
	public int getNum() {
		return num;
	}
	
	//Set appointment index
	public void setNum(int newNum) {
		num = newNum;
	}
	
	//Return appointment's VIN
	public String getVin() {
		return vin;
	}
	
	//Set appointment's VIN
	public void setVin(String newVin) {
		vin = newVin;
	}
	
	//Return appointment's shift index
	public int getShiftNum() {
		return shiftNum;
	}
	
	//Set appointment's shift index
	public void setShiftNum(int newShiftNum) {
		shiftNum = newShiftNum;
	}
	
	//Return appointment's service index
	public int getServnum() {
		return servNum;
	}
	
	//Set appointment's service index
	public void setServNum(int newServNum) {
		servNum = newServNum;
	}
	
	//Appointment display search query
	public String displayAptQuery() {
		return "SELECT CUSTOMER.*, VEHICLE.*, SERVICE.* FROM APPOINTMENT, OWNER, CUSTOMER, VEHICLE, SERVICE WHERE APPOINTMENT.SHIFT_Num = " + shiftNum  +
				" AND OWNER.VIN = APPOINTMENT.VIN AND OWNER.CUS_NUM = CUSTOMER.CUS_NUM AND OWNER.VCL_NUM = VEHICLE.VCL_NUM AND SERVICE.SERV_NUM = APPOINTMENT.SERV_NUM;"; 
	}
	
	//Appointment add statement
	public String addAptStmt() {
		return "INSERT INTO APPOINTMENT VALUES (NULL, \"" + vin + "\", " + shiftNum + ", " + servNum + ");";
	}
	
	//Appointment delete statement
	public String delAptStmt() {
		return "DELETE FROM APPOINTMENT WHERE VIN = \"" + vin + "\" AND SHIFT_NUM = " + shiftNum + " AND SERV_NUM = " + servNum + ";";
	}
}
