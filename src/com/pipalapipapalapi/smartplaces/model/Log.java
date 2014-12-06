package com.pipalapipapalapi.smartplaces.model;

public class Log {
	
	private int id;
	
	private String module;
	
	private int dateTimeInMillis;
	
	private String location;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getDateTimeInMillis() {
		return dateTimeInMillis;
	}

	public void setDateTimeInMillis(int dateTimeInMillis) {
		this.dateTimeInMillis = dateTimeInMillis;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", module=" + module + ", dateTimeInMillis="
				+ dateTimeInMillis + ", location=" + location + "]";
	}
	
	

}
