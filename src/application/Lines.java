package application;

public class Lines {
	
	private int id;
	
	private String location;
	private String destination;
	private String company;
	private String time;
	private String days;
	
	
	public Lines(int id,String location,String destination,String time,String days,String company) {
	super();
	this.id = id;
	this.company = company;
	this.destination = destination;
	this.time = time;
	this.location = location;
	this.days = days;
	
}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public String getDays() {
		return days;
	}


	public void setDays(String days) {
		this.days = days;
	}
		
	
	
	
	
}
