package com.example.demo.entity;

public class LoginResponse extends Response {
	
	public LoginResponse() {
		// TODO Auto-generated constructor stub
	}
	
	private User user;

	public LoginResponse(String status, String message, User user) {
		super(status, message);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
	// getters and setters
}