package com.example.demo.entity;

public class Response {
	
	public Response() {
		// TODO Auto-generated constructor stub
	}
	
	private String status;
	private String message;

	public Response(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	// getters and setters
}