package application;

public class Admins {
	
	private int id;
	private String username;
	private String password;
	
	public Admins(int id,String username, String password) {
	super();
	this.id = id;
	this.username = username;
	this.password = password;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
