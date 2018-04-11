package shop.core;

public class Ownership {
	//Specific vehicle/ownership attributes
	private String vin;
	private int vNum;
	private int miles;
	private String record;
	
	//Owner entity constructor
	public Ownership(String newVin, int newCNum, int newVNum, int newMiles, String newRecord) {
		vin = newVin;
		cNum = newCNum;
		vNum = newVNum;
		miles = newMiles;
		record = newRecord;
	}
	
	//Return VIN
	public String getVin() {
		return vin;
	}
	
	//Set VIN
	public void setVin(String newVin) {
		vin = newVin;
	}
	
	//Return the customer's index
	public int getCNum() {
		return cNum;
	}
	
	//Set the customer's index
	public void setCNum(int newCNum) {
		cNum = newCNum;
	}
	
	//Return the vehicle's index
	public int getVNum() {
		return vNum;
	}
	
	//Set the vehicle's index
	public void setVNum(int newVNum) {
		vNum = newVNum;
	}
	
	//Return the specific vehicle's mileage
	public int getMiles() {
		return miles;
	}
	
	//Set the specific vehicle's mileage
	public void setMiles(int newMiles) {
		miles = newMiles;
	}
	
	//Return the specific vehicle's record
	public String getRecord() {
		return record;
	}
	
	//Set the specific vehicle's record
	public void setRecord(String newRecord) {
		record = newRecord;
	}
	
	
	@Override
	public String toString() {
		return String.format("Owner [vin=%s, cNum=%s, vNum=%s, miles=%s,record=%s]", vin, cNum, vNum, miles, record);
	}
	
	public String selectCusString() {
		return "SELECT CUSTOMER.* FROM OWNER, CUSTOMER WHERE VIN = \"" + vin + "\" AND OWNER.CUS_NUM = CUSTOMER.CUS_NUM;";
	}
	
	public String selectVclString() {
		return "SELECT VEHICLE.* FROM OWNER, VEHICLE WHERE VIN = \"" + vin + "\" AND OWNER.VCL_NUM = VEHICLE.VCL_NUM;";
	}
	
	//Owner insert builder
	public String insertString() {
		return "INSERT INTO OWNER VALUES(\"" + vin + "\", " + cNum + ", " + vNum + ", " + miles + ", \"" + record + "\");";
	}
	
	//Owner update builder
	public String updateString() {
		return "UPDATE OWNER SET VIN = \"" + vin + "\", CUS_NUM = \"" + cNum + "\", VCL_NUM = \"" + vNum + 
				"\", OWN_MILES = \"" + miles + "\", OWN_RECORD = \"" + record + "\" WHERE VIN = \"" + vin  + "\";";
	}
}
