package com.carrental.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "booking")
public class Booking {
	
	private long bookingId;
	
	private Integer customerId;
	
	private Integer zipcode;
	
	private String bookingStatus;

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getZipcode() {
		return zipcode;
	}

	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	
	
	
	
	
	

}
