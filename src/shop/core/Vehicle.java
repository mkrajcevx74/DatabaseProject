package shop.core;

public class Vehicle {
	public int num;
	public String make;
	public String model;
	public int year;
	public String misc;
	
	public Vehicle(int num, String make, String model, int year, String misc) {
		super();
		this.num = num;
		this.make = make;
		this.model = model;
		this.year = year;
		this.misc = misc;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public int getYear() {
		return year;
	}
	
	public void getYear(int year) {
		this.year = year;
	}
	
	public String misc() {
		return misc;
	}
	
	@Override
	public String toString() {
		//return String.format("Vehicle [num=%s, make=%s, model=%s, year=%s, misc=%s]", num, make, model, year, misc);
		return getYear() + " " + getMake() + " " + getModel();
	}
}
