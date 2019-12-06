package com.carrental.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coupon")
public class Coupon {
	
	public String couponCode;
	
	public Integer discountPercentage;
	
	public Integer dollarDiscount;
	
	public Date expiryDate;
	
	

	public Integer getDollarDiscount() {
		return dollarDiscount;
	}

	public void setDollarDiscount(Integer dollarDiscount) {
		this.dollarDiscount = dollarDiscount;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

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
