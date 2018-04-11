package shop.core;

public class Schedule {
	//Shift attributes
	private int num;
	private String date;
	private String startTime;
	private String endTime;
	private int empNum;
	
	//Schedule entity constructor
	public Schedule(int newNum, String newDate, String newStartTime, String newEndTime, int newEmpNum) {
		num = newNum;
		date = newDate;
		startTime = newStartTime;
		endTime = newEndTime;
		empNum = newEmpNum;
	}
	
	//Return schedule index
	public int getNum() {
		return num;
	}
	
	//Set schedule index
	public void setNum(int newNum) {
		num = newNum;
	}
	
	//Return schedule date
	public String getDate() {
		return date;
	}
	
	//Set schedule date
	public void setDate(String newDate) {
		date = newDate;
	}
	
	//Return schedule start time
	public String getStartTime() {
		return startTime;
	}
	
	//Set schedule start time
	public void setStartTime(String newStartTime) {
		startTime = newStartTime;
	}
	
	//Return schedule end time
	public String getEndTime() {
		return endTime;
	}
	
	//Set schedule end time
	public void setEndTime(String newEndTime) {
		endTime = newEndTime;
	}
	
	//Return scheduled technician index
	public int getEmpNum() {
		return empNum;
	}
	
	//Set scheduled employee index
	public void setEmpNum(int newEmpNum) {
		empNum = newEmpNum;
	}
	
	public String toString() {
		return date + " from " + startTime + " to " + endTime;
	}
}
