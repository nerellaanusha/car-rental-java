package com.carrental.message.response;

public class CarResponse {

	long vin;
	
	String carMake;
	
	String carModel;
	
	Integer capacity;
	
	Integer baggage;
	
	String image;
	
	Integer zipcode;
	
	float price;
	
	String type;
	
	double totalPrice;
	
	double totalPriceWithTax;
	
	
	
	public double getTotalPriceWithTax() {
		return totalPriceWithTax;
	}

	public void setTotalPriceWithTax(double totalPriceWithTax) {
		this.totalPriceWithTax = totalPriceWithTax;
	}

	float tax;
	

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public long getVin() {
		return vin;
	}

	public void setVin(long vin) {
		this.vin = vin;
	}

	public String getCarMake() {
		return carMake;
	}

	public void setCarMake(String carMake) {
		this.carMake = carMake;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Integer getBaggage() {
		return baggage;
	}

	public void setBaggage(Integer baggage) {
		this.baggage = baggage;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double d) {
		this.totalPrice = d;
	}
	
}
