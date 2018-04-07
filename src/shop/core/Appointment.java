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
	
	//Return appointment's vin
	public String getVin() {
		return vin;
	}
	
	//Set appointment's vin
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
}
