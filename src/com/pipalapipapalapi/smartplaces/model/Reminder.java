package com.pipalapipapalapi.smartplaces.model;

public class Reminder {

	private int id;
	
	private int status;
	
	private double latitude;
	
	private double longitude;
	
	private int trigger;
	
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Toggle [id=" + id + ", status=" + status + ", latitude="
				+ latitude + ", longitude=" + longitude + ", trigger="
				+ trigger + ", message=" + message + "]";
	}

}
