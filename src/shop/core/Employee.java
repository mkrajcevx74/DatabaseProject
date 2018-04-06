package shop.core;

public class Employee {
	private int num;
	private String fName;
	private String lName;
	private String contact;
	private float rating;
	private int rCount;
	private float wage;
	
	public Employee(int newNum, String newFName, String newLName, String newContact, float newRating, int newRCount, float newWage) {
		num = newNum;
		fName = newFName;
		lName = newLName;
		contact = newContact;
		rating = newRating;
		rCount = newRCount;
		wage = newWage;
	}
	
	//Return employee's index
	public int getNum() {
		return num;
	}
		
	//Set employee's index
	public void setNum(int newNum) {
		num = newNum;
	}
	
	//Return employee's first name
	public String getFName() {
		return fName;
	}
	
	//Set employee's first name
	public void setFName(String newFName) {
		fName = newFName;
	}
	
	//Return employee's last name
	public String getLName() {
		return lName;
	}
	
	//Set employee's last name
	public void setLName(String newLName) {
		lName = newLName;
	}
	
	//Return employee's contact info
	public String getContact() {
		return contact;
	}
	
	//Set employee's contact info
	public void setContact(String newContact) {
		contact = newContact;
	}
	
	//Return employee's rating
	public float getRating() {
		return rating;
	}
	
	//Set employee's rating
	public void setRating(float newRating) {
		rating = newRating;
	}
	
	//Return employee's rating count
	public int getRCount() {
		return rCount;
	}
	
	//Set employee's rating count
	public void setRCount(int newRCount) {
		rCount = newRCount;
	}
	
	//Update employee's rating
	public void updateRating(int newRating) {
		float totalRating = rating * rCount;
		rCount++;
		rating = (newRating + totalRating)/rCount;
	}
	
	//Return employee's wage
	public float getWage() {
		return wage;
	}
	
	//Set employee's wage
	public void setWage(float newWage) {
		wage = newWage;
	}
}
