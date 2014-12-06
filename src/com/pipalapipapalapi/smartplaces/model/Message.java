package com.pipalapipapalapi.smartplaces.model;

public class Message {
	
	private int id;
	
	private int status;
	
	private double latitude;
	
	private double longitude;
	
	private int trigger;
	
	private String recipientNumber;
	
	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getTrigger() {
		return trigger;
	}

	public void setTrigger(int trigger) {
		this.trigger = trigger;
	}

	public String getRecipientNumber() {
		return recipientNumber;
	}

	public void setRecipientNumber(String recipientNumber) {
		this.recipientNumber = recipientNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", status=" + status + ", latitude="
				+ latitude + ", longitude=" + longitude + ", trigger="
				+ trigger + ", recipientNumber=" + recipientNumber
				+ ", message=" + message + "]";
	}

}
