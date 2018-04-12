package shop.core;

public class Technician {
	//Technician attributes
	private int num;
	private String fName;
	private String lName;
	private String contact;
	private float rating;
	private int rCount;
	private float wage;
	
	//Employee entity constructor
	public Technician(int newNum, String newFName, String newLName, String newContact, float newRating, int newRCount, float newWage) {
		num = newNum;
		fName = newFName;
		lName = newLName;
		contact = newContact;
		rating = newRating;
		rCount = newRCount;
		wage = newWage;
	}
	
	//Return technician's index
	public int getNum() {
		return num;
	}
		
	//Set technician's index
	public void setNum(int newNum) {
		num = newNum;
	}
	
	//Return technician's first name
	public String getFName() {
		return fName;
	}
	
	//Set technician's first name
	public void setFName(String newFName) {
		fName = newFName;
	}
	
	//Return technician's last name
	public String getLName() {
		return lName;
	}
	
	//Set technician's last name
	public void setLName(String newLName) {
		lName = newLName;
	}
	
	//Return technician's contact info
	public String getContact() {
		return contact;
	}
	
	//Set technician's contact info
	public void setContact(String newContact) {
		contact = newContact;
	}
	
	//Return technician's rating
	public float getRating() {
		return rating;
	}
	
	//Set technician's rating
	public void setRating(float newRating) {
		rating = newRating;
	}
	
	//Return technician's rating count
	public int getRCount() {
		return rCount;
	}
	
	//Set technician's rating count
	public void setRCount(int newRCount) {
		rCount = newRCount;
	}
	
	//Update technician's rating
	public void updateRating(int newRating) {
		float totalRating = rating * rCount;
		rCount++;
		rating = ((newRating + totalRating)/rCount);
	}
	
	//Return technician's wage
	public float getWage() {
		return wage;
	}
	
	//Set technician's wage
	public void setWage(float newWage) {
		wage = newWage;
	}
	
	//Get technician's full name string
	@Override
	public String toString() {
		return fName + " " + lName;
	}
	
	//Technician update statement
	public String updateString() {
		return "UPDATE TECHNICIAN SET EMP_FNAME = \"" + fName + "\", EMP_LNAME = \"" + lName + "\", EMP_CONTACT = \"" + contact + 
				"\", EMP_RATING = " + rating + ", EMP_RATING_COUNT = " + rCount + ", EMP_WAGE = " + wage + "WHERE EMP_NUM = " + num + ";";
	}
}
