package com.carrental.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.carrental.model.User;

@Repository
public class CarRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public User addCar(String userName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(userName));
		return mongoTemplate.findOne(query, User.class);
	}

}
