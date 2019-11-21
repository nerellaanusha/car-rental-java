package com.carrental.message.request;

public class UpdateCar {

	long vin;

	Integer zipcode;
	
	float price;

	public long getVin() {
		return vin;
	}

	public void setVin(long vin) {
		this.vin = vin;
	}

	public Integer getZipcode() {
		return zipcode;
	}

	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
