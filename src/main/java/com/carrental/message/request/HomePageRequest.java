package com.carrental.message.request;

import java.util.Date;

public class HomePageRequest {

	Integer pickUpLoc;
	
	Date dropOffDate;
	
	Date pickUpDate;
	
	Integer dropOffLoc;

	public Integer getPickUpLoc() {
		return pickUpLoc;
	}

	public void setPickUpLoc(Integer pickUpLoc) {
		this.pickUpLoc = pickUpLoc;
	}

	public Date getDropOffDate() {
		return dropOffDate;
	}

	public void setDropOffDate(Date dropOffDate) {
		this.dropOffDate = dropOffDate;
	}

	public Date getPickUpDate() {
		return pickUpDate;
	}

	public void setPickUpDate(Date pickUpDate) {
		this.pickUpDate = pickUpDate;
	}

	public Integer getDropOffLoc() {
		return dropOffLoc;
	}

	public void setDropOffLoc(Integer dropOffLoc) {
		this.dropOffLoc = dropOffLoc;
	}

	
	
	
	
	
}
