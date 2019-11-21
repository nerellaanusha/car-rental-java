package com.carrental.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.carrental.message.request.UpdateCar;
import com.carrental.model.Car;
import com.carrental.model.Coupon;
import com.carrental.model.Location;

@Service
public class CouponDao {

	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Coupon addCoupon(Coupon coupon) {
		mongoTemplate.save(coupon,"coupon");
		return coupon;
	}
	
	public Coupon updateCoupon(Coupon coupon) {
		Query couponQuery = new Query();
		couponQuery.addCriteria(Criteria.where("couponCode").is(coupon.getCouponCode()));
		Coupon vehicle = this.mongoTemplate.findOne(couponQuery,Coupon.class);
		
		Update update = new Update();
		update.set("dicountPercentage", coupon.getDicountPercentage());
		update.set("dollarDiscount", coupon.getDicountPercentage());
		
		this.mongoTemplate.updateFirst(couponQuery, update, Coupon.class);
		return this.mongoTemplate.findOne(couponQuery,Coupon.class);
	}
	
	public Coupon getCouponByCode(Coupon coupon) {
		Query couponQuery = new Query();
		couponQuery.addCriteria(Criteria.where("couponCode").is(coupon.getCouponCode()));
		return this.mongoTemplate.findOne(couponQuery,Coupon.class);
	}
	
	public List<Coupon> getAllCoupons(){
		return this.mongoTemplate.findAll(Coupon.class);
	}
	
	public Coupon deleteCoupon(String coupon) {
		Query deleteCoupon = new Query();
		deleteCoupon.addCriteria(Criteria.where("couponCode").is(coupon));
		return this.mongoTemplate.findAndRemove(deleteCoupon, Coupon.class);
	}
}
