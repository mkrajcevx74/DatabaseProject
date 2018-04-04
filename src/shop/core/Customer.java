package shop.core;

public class Customer {
	public int num;
	public String lName;
	public String fName;
	public String contact;
	
	public Customer(int num, String fName, String lName, String contact) {
		super();
		this.num = num;
		this.fName = fName;
		this.lName = lName;
		this.contact = contact;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public String getFName() {
		return fName;
	}
	
	public void setFName(String fName) {
		this.fName = fName;
	}
	
	public String getLName() {
		return lName;
	}
	
	public void setLName(String lName) {
		this.lName = lName;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@Override
	public String toString() {
		//return String.format("Customer [num=%s, fName=%s, lName=%s, contact=%s]", num, fName, lName, contact);
		return getFName() + " " + getLName();
	}
	
	public String updateString() {
		return getNum() + ", " + getFName() + ", " + getLName() + ", " + getContact();
	}
}