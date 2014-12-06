package com.pipalapipapalapi.smartplaces.model;

public class Place {

	private String title;
	private double latitude;
	private double longitude;
	private String iconUrl;
	private double distance;
	
	public String getTitle() {
		return title;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public double getDistance() {
		return distance;
	}

	private Place(Place.Builder placeBuilder) {
		this.title = placeBuilder.title;
		this.latitude = placeBuilder.latitude;
		this.longitude = placeBuilder.longitude;
		this.iconUrl = placeBuilder.iconUrl;
		this.distance = placeBuilder.distance;
	}
	
	public static class Builder {
		
		private String title;
		private double latitude;
		private double longitude;
		private String iconUrl;
		private double distance;
		
		public Place.Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		
		public Place.Builder setLatitude(double latitude) {
			this.latitude = latitude;
			return this;
		}
		
		public Place.Builder setLongitude(double longitude) {
			this.longitude = longitude;
			return this;
		}
		
		public Place.Builder setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
			return this;
		}
		
		public Place.Builder setDistance(double distance) {
			this.distance = distance;
			return this;
		}
		
		public Place build() {
			return new Place(this);
		}
		
	}
	
}
