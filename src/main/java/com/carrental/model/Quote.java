package com.carrental.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "quote")
public class Quote {
	
	@Id
	Long quoteId;
	
	@Transient
    public static final String SEQUENCE_NAME = "quote_sequence";
	
	Long customerId;
	
	String firstName;
	
	String lastName;
	
	String email;
	
	String contactNumber;
	
	String pickUpLoc;
	
	String dropOffloc;
	
	Date pickUpDate;
	
	Date dropOffDate;
	
	float totalPrice;
	
	long vin;
	
	float userPrice;
	
	String quoteStatus;
	
	

	public String getQuoteStatus() {
		return quoteStatus;
	}

	public void setQuoteStatus(String quoteStatus) {
		this.quoteStatus = quoteStatus;
	}

	public Long getQuoteId() {
		return quoteId;
	}

	public void setQuoteId(Long quoteId) {
		this.quoteId = quoteId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPickUpLoc() {
		return pickUpLoc;
	}

	public void setPickUpLoc(String pickUpLoc) {
		this.pickUpLoc = pickUpLoc;
	}

	public String getDropOffloc() {
		return dropOffloc;
	}

	public void setDropOffloc(String dropOffloc) {
		this.dropOffloc = dropOffloc;
	}

	public Date getPickUpDate() {
		return pickUpDate;
	}

	public void setPickUpDate(Date pickUpDate) {
		this.pickUpDate = pickUpDate;
	}

	public Date getDropOffDate() {
		return dropOffDate;
	}

	public void setDropOffDate(Date dropOffDate) {
		this.dropOffDate = dropOffDate;
	}

	public float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public long getVin() {
		return vin;
	}

	public void setVin(long vin) {
		this.vin = vin;
	}

	public float getUserPrice() {
		return userPrice;
	}

	public void setUserPrice(float userPrice) {
		this.userPrice = userPrice;
	}
	
	
	
	

}
