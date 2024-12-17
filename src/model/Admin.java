package model;

public class Admin extends User{

	public Admin(String id, String email, String username, String password, String role) {
		super(id, email, username, password, role);
	}
	
	public Admin( String email, String username, String password, String role) {
		super(email, username, password, role);
		// TODO Auto-generated constructor stub
	}
	

}
