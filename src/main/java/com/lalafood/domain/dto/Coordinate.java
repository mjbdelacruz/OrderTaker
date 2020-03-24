package com.lalafood.domain.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Coordinate {

	@Min(value = -90, message = "Latitude value should be between -90 and +90")
	@Max(value = 90, message = "Latitude value should be between -90 and +90")
	private Double latitude;

	@Min(value = -180, message = "Longitude value should be between -180 and +180")
	@Max(value = 180, message = "Longitude value should be between -180 and +180")
	private Double longitude;

	public Coordinate(String lat, String lng) {
		latitude = Double.parseDouble(lat);
		longitude = Double.parseDouble(lng);
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public String getCoordinateAsString() {
		return this.latitude + "," + this.longitude;
	}
}
