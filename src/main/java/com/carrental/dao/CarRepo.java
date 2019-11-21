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
import com.carrental.model.User;

@Service
public class CarRepo {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Car addCar(Car car) {
		mongoTemplate.save(car,"car");
		return car;
	}
	
	public List<Car> getCarsByZipcode(Integer zipcode){
		Query zipcodeQuery = new Query();
		zipcodeQuery.addCriteria(Criteria.where("zipcode").is(zipcode));
		return this.mongoTemplate.find(zipcodeQuery,Car.class);
	}
	
	public List<Car> getAllCars(){
		return this.mongoTemplate.findAll(Car.class);
	}
	
	public Car getCarByVin(long vin) {
		Query carVin = new Query();
		carVin.addCriteria(Criteria.where("vin").is(vin));
		return this.mongoTemplate.findOne(carVin,Car.class);
	}
	
	public Car updateCar(UpdateCar car) {
		Query carVin = new Query();
		carVin.addCriteria(Criteria.where("vin").is(car.getVin()));
		Car vehicle = this.mongoTemplate.findOne(carVin,Car.class);
		
		Update update = new Update();
		update.set("zipcode", car.getZipcode());
		update.set("price", car.getPrice());
		
		this.mongoTemplate.updateFirst(carVin, update, Car.class);
		return this.mongoTemplate.findOne(carVin,Car.class);
	}
	
	public Car deleteCar(long vin) {
		Query deleteCar = new Query();
		deleteCar.addCriteria(Criteria.where("vin").is(vin));
		return this.mongoTemplate.findAndRemove(deleteCar, Car.class);
	}

}
