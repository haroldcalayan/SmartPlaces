package com.pipalapipapalapi.smartplaces.model;

public class Toggle {
	
	private int id;
	
	private int status;
	
	private double latitude;
	
	private double longitude;
	
	private int trigger;
	
	private int wifiState;
	
	private int mobileDataState;
	
	private int ringerState;
	
	private int bluetoothState;
	
	private int airplaneModeState;

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

	public int getWifiState() {
		return wifiState;
	}

	public void setWifiState(int wifiState) {
		this.wifiState = wifiState;
	}

	public int getMobileDataState() {
		return mobileDataState;
	}

	public void setMobileDataState(int mobileDataState) {
		this.mobileDataState = mobileDataState;
	}

	public int getRingerState() {
		return ringerState;
	}

	public void setRingerState(int ringerState) {
		this.ringerState = ringerState;
	}

	public int getBluetoothState() {
		return bluetoothState;
	}

	public void setBluetoothState(int bluetoothState) {
		this.bluetoothState = bluetoothState;
	}

	public int getAirplaneModeState() {
		return airplaneModeState;
	}

	public void setAirplaneModeState(int airplaneModeState) {
		this.airplaneModeState = airplaneModeState;
	}

	@Override
	public String toString() {
		return "Toggle [id=" + id + ", status=" + status + ", latitude="
				+ latitude + ", longitude=" + longitude + ", trigger="
				+ trigger + ", wifiState=" + wifiState + ", mobileDataState="
				+ mobileDataState + ", ringerState=" + ringerState
				+ ", bluetoothState=" + bluetoothState + ", airplaneModeState="
				+ airplaneModeState + "]";
	}
	
	
	
}
