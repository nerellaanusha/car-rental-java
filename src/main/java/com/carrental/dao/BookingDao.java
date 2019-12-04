package com.carrental.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.carrental.model.Booking;
import com.carrental.model.Car;


@Service
public class BookingDao {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Booking reserveCar(Booking booking) {
		mongoTemplate.save(booking,"booking");
		return booking;
	}
	
	public List<Booking> getBookingsByCustomer(Long id){
		Query bookings = new Query();
		bookings.addCriteria(Criteria.where("customerId").is(id));
		return this.mongoTemplate.find(bookings,Booking.class);
	}
	
	public List<Booking> getAllBookings(){
		return this.mongoTemplate.findAll(Booking.class);
	}
	
}
