package com.carrental.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coupon")
public class Coupon {
	
	public String couponCode;
	
	public Integer dicountPercentage;
	
	public Integer dollarDiscount;

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Integer getDicountPercentage() {
		return dicountPercentage;
	}

	public void setDicountPercentage(Integer dicountPercentage) {
		this.dicountPercentage = dicountPercentage;
	}
	
	
	

}
