package com.carrental.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.carrental.model.Car;
import com.carrental.model.Coupon;
import com.carrental.model.Location;

@Service
public class LocationDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Location addLocation(Location loc) {
		mongoTemplate.save(loc,"location");
		return loc;
	}
	
	public List<Location> getAllLocations(){
		return this.mongoTemplate.findAll(Location.class);
	}
	
	public Location getLocationByZip(Location loc) {
		Query locQuery = new Query();
		locQuery.addCriteria(Criteria.where("zipcode").is(loc.getZipcode()));
		return this.mongoTemplate.findOne(locQuery,Location.class);
	}
	
	public Location deleteLocation(Integer loc) {
		Query deleteLoc = new Query();
		deleteLoc.addCriteria(Criteria.where("zipcode").is(loc));
		return this.mongoTemplate.findAndRemove(deleteLoc, Location.class);
	}

}
