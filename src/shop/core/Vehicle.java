package shop.core;

public class Vehicle {
	//Vehicle attributes
	private int num;
	private String make;
	private String model;
	private int year;
	private String misc;
	
	//Vehicle entity constructor
	public Vehicle(int newNum, String newMake, String newModel, int newYear, String newMisc) {
		num = newNum;
		make = newMake;
		model = newModel;
		year = newYear;
		misc = newMisc;
	}
	
	//Return vehicle's index
	public int getNum() {
		return num;
	}
	
	//Set vehicle's index
	public void setNum(int newNum) {
		num = newNum;
	}
	
	//Return vehicle's manufacturer
	public String getMake() {
		return make;
	}
	
	//Set vehicle's manufacturer
	public void setMake(String newMake) {
		make = newMake;
	}
	
	//Return vehicle's model
	public String getModel() {
		return model;
	}
	
	//Set vehicle's model
	public void setModel(String newModel) {
		model = newModel;
	}
	
	//Return vehicle's year
	public int getYear() {
		return year;
	}
	
	//Set vehicle's year
	public void getYear(int newYear) {
		year = newYear;
	}
	
	//Return vehicle's package
	public String getMisc() {
		return misc;
	}
	
	//Set vehicle's package
	public void setMisc(String newMisc) {
		misc = newMisc;
	}
	
	//Get vehicle as a string
	@Override
	public String toString() {
		//return String.format("Vehicle [num=%s, make=%s, model=%s, year=%s, misc=%s]", num, make, model, year, misc);
		return year + " " + make + " " + model;
	}
}
