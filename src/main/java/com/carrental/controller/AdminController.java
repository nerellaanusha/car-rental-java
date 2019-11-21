package com.carrental.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.dao.CarRepo;
import com.carrental.dao.CouponDao;
import com.carrental.dao.LocationDao;
import com.carrental.dao.UserRepo;
import com.carrental.message.request.LoginForm;
import com.carrental.message.request.UpdateCar;
import com.carrental.message.response.JwtResponse;
import com.carrental.message.response.ResponseMessage;
import com.carrental.model.Car;
import com.carrental.model.Coupon;
import com.carrental.model.Location;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	CarRepo carRepo;
	
	@Autowired
	CouponDao couponDao;
	
	@Autowired
	LocationDao locationDao;
	
	
	@PostMapping("/addcar")
	public ResponseEntity<?> addCar(@RequestBody Car car) {
		
		
		if(carRepo.getCarByVin(car.getVin()) == null) {
			if(this.carRepo.addCar(car) != null) {
				return new ResponseEntity<>(new ResponseMessage("Car Added successfully!"), HttpStatus.OK);
			}
		}else {
			return new ResponseEntity<>(new ResponseMessage("Car Exist Already!"), HttpStatus.CONFLICT);
		}
		
		
		return null;
	}
	
	@GetMapping("/zipcode/{zipcode}")
	public List<Car> getCarsByZipcode(@PathVariable Integer zipcode){
		
		return this.carRepo.getCarsByZipcode(zipcode);
	}
	
	@GetMapping("/allCars")
	public List<Car> getAllCars(){
		
		return this.carRepo.getAllCars();
	}
	
	@PostMapping("/editCar")
	public ResponseEntity<?> updateCarDetails(@RequestBody UpdateCar car){
		try {
			if(this.carRepo.updateCar(car) != null) {
				return new ResponseEntity<>(new ResponseMessage("Car Updated successfully!"), HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Error while updating Car"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
	
	@DeleteMapping("/deleteCar/{vin}")
	public ResponseEntity<?> deleteCar(@PathVariable long vin){
		
		try {
			if(this.carRepo.deleteCar(vin) != null) {
				return new ResponseEntity<>(new ResponseMessage("Car Deleted successfully!"), HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Error While Deleting Car"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
	
	
	@PostMapping("/addCoupon")
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon){
		try {
			if(this.couponDao.getCouponByCode(coupon) == null) {
				if(this.couponDao.addCoupon(coupon)!= null) {
					return new ResponseEntity<>(new ResponseMessage("Coupon Added successfully!"), HttpStatus.OK);
				}
			}else {
				
			}
		}catch(Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Error while adding Coupon"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
	
	@PostMapping("/editCoupon")
	public ResponseEntity<?> editCoupon(@RequestBody Coupon coupon){
		try {
			
			if(this.couponDao.getCouponByCode(coupon) == null) {
				if(this.couponDao.updateCoupon(coupon)!= null) {
					return new ResponseEntity<>(new ResponseMessage("Coupon Updated successfully!"), HttpStatus.OK);
				}
			}
		}catch(Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Error while updating Coupon"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
	
	
	@GetMapping("/allCoupons")
	public List<Coupon> getAllCoupons(){
		return this.couponDao.getAllCoupons();
	}
	
	@GetMapping("/allLocations")
	public List<Location> getAllLocations(){
		return this.locationDao.getAllLocations();
	}
	
	@DeleteMapping("/deleteLoc/{loc}")
	public ResponseEntity<?> deleteLocation(@PathVariable Integer loc){
		
		try {
			if(this.locationDao.deleteLocation(loc) != null) {
				return new ResponseEntity<>(new ResponseMessage("Car Deleted successfully!"), HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Error While Deleting Car"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
	
	@DeleteMapping("/deleteCoupon/{coupon}")
	public ResponseEntity<?> deleteCoupon(@PathVariable String coupon){
		
		try {
			if(this.couponDao.deleteCoupon(coupon) != null) {
				return new ResponseEntity<>(new ResponseMessage("Coupon Deleted successfully!"), HttpStatus.OK);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Error While Deleting Coupon"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
	
	@PostMapping("/addLocation")
	public ResponseEntity<?> addLocation(@RequestBody Location location){
		try {
			
			if(this.locationDao.getLocationByZip(location) == null) {
				if(this.locationDao.addLocation(location)!= null) {
					return new ResponseEntity<>(new ResponseMessage("Location Added successfully!"), HttpStatus.OK);
				}
			}else {
				return new ResponseEntity<>(new ResponseMessage("Location Already Existed!"), HttpStatus.CONFLICT);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(new ResponseMessage("Error while adding Location"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}
	
	
	
	
	
	


}
