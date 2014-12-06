package com.pipalapipapalapi.smartplaces.model;

public class Notification {
	
	private int id;
	
	private int timestamp;
	
	private int type;
	
	private String title;
	
	private String message;
	
	private int isRead;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", timestamp=" + timestamp
				+ ", type=" + type + ", title=" + title + ", message="
				+ message + ", isRead=" + isRead + "]";
	}
	
	

}
