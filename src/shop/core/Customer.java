package shop.core;

public class Customer {
	//Customer attributes
	private int num;
	private String lName;
	private String fName;
	private String contact;
	
	//Customer entity constructor
	public Customer(int newNum, String newFName, String newLName, String newContact) {
		num = newNum;
		fName = newFName;
		lName = newLName;
		contact = newContact;
	}
	
	//Return customer's index
	public int getNum() {
		return num;
	}
	
	//Set customer's index
	public void setNum(int newNum) {
		num = newNum;
	}
	
	//Return customer's first name
	public String getFName() {
		return fName;
	}
	
	//Set customer's first name
	public void setFName(String newFName) {
		fName = newFName;
	}
	
	//Return customer's last name
	public String getLName() {
		return lName;
	}
	
	//Set customer's last name
	public void setLName(String newLName) {
		lName = newLName;
	}
	
	//Return customer's contact info
	public String getContact() {
		return contact;
	}
	
	//Set customer's contact info
	public void setContact(String newContact) {
		contact = newContact;
	}
	
	//Get customer's name as string
	@Override
	public String toString() {
		//return String.format("Customer [num=%s, fName=%s, lName=%s, contact=%s]", num, fName, lName, contact);
		return fName + " " + fName;
	}
	
	//Customer search query builder
	public String updateString() {
		return num + ", " + fName + ", " + lName + ", " + contact;
	}
}