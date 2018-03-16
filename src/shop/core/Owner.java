package shop.core;

public class Owner {
	public String vin;
	public int cNum;
	public int vNum;
	public int miles;
	public String record;
	
	public Owner(String vin, int cNum, int vNum, int miles, String record) {
		super();
		this.vin = vin;
		this.cNum = cNum;
		this.vNum = vNum;
		this.miles = miles;
		this.record = record;
	}
	
	public String getVin() {
		return vin;
	}
	
	public void setVin(String vin) {
		this.vin = vin;
	}
	
	public int getCNum() {
		return cNum;
	}
	
	public void setCNum(int cNum) {
		this.cNum = cNum;
	}
	
	public int getVNum() {
		return vNum;
	}
	
	public void setVNum(int vNum) {
		this.vNum = vNum;
	}
	
	public int getMiles() {
		return miles;
	}
	
	public void setMiles(int miles) {
		this.miles = miles;
	}
	
	public String getRecord() {
		return record;
	}
	
	public void setRecord(String record) {
		this.record = record;
	}
	
	@Override
	public String toString() {
		return String.format("Owner [vin=%s, cNum=%s, vNum=%s, miles=%s,record=%s]", vin, cNum, vNum, miles, record);
	}
}
