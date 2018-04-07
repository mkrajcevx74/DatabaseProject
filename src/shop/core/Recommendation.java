package shop.core;

public class Recommendation {
	//recommendation attributes
	private int recNum;
	private int vin;
	private int servNum;
	
	//class entity constructor
	public Recommendation(int newRecNum, int newVin, int newServNum) {
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
		
	//Return vin num
	public int getVIN() {
		return vin;
	}
		
	//Set vin num
	public void setVIN(int newVIN) {
		vin = newVIN;
	}
	
	//Return vin num
	public int getServNum() {
		return servNum;
	}
			
	//Set vin num
	public void setServNum(int newServiceNum) {
		servNum = newServiceNum;
	}

}
