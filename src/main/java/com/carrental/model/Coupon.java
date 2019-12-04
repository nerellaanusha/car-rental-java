package com.carrental.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coupon")
public class Coupon {
	
	public String couponCode;
	
	public Integer discountPercentage;
	
	public Integer dollarDiscount;

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public Integer getDicountPercentage() {
		return discountPercentage;
	}

	public void setDicountPercentage(Integer dicountPercentage) {
		this.discountPercentage = dicountPercentage;
	}
	
	
	

}
