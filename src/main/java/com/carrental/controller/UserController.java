package com.carrental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.dao.CarRepo;
import com.carrental.message.request.HomePageRequest;
import com.carrental.model.Car;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	CarRepo carRepo;
	
	@PostMapping("/getCarsOnZipcode")
	public List<Car> getCarsByZipcode(@RequestBody HomePageRequest homepageReq){
		
		List<Car> cars = this.carRepo.getCarsByZipcode(homepageReq.getPickUpLoc());
		
		if(cars.size() > 1) {
			long difference =  (homepageReq.getDropOffDate().getTime()-homepageReq.getPickUpDate().getTime())/86400000;
	        long days =  Math.abs(difference);
	        System.out.println("-----------------------Days:"+days);
		}
		return cars;
	}

}
