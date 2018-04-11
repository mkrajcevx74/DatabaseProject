package shop.core;

public class Service {
	//Service attributes
	private int num;
	private String desc;
	
	//Service entity constructor
	public Service(int newNum, String newDesc) {
		num = newNum;
		desc = newDesc;
	}
	
	//Return service index
	public int getNum() {
		return num;
	}
	
	//Set service index
	public void setNum(int newNum) {
		num = newNum;
	}
	
	//Return service description
	public String getDesc() {
		return desc;
	}
	
	//Set service description
	public void setString(String newDesc) {
		desc = newDesc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
}
