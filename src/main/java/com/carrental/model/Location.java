package com.carrental.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "location")
public class Location {
	
	public Integer zipcode;
	
	public String locationName;

	public Integer getZipcode() {
		return zipcode;
	}

	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	

}
