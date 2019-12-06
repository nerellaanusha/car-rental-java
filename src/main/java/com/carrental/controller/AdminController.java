package com.carrental.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

import com.carrental.dao.BookingDao;
import com.carrental.dao.CarRepo;
import com.carrental.dao.CouponDao;
import com.carrental.dao.LocationDao;
import com.carrental.dao.QuoteDao;
import com.carrental.dao.UserRepo;
import com.carrental.message.request.LoginForm;
import com.carrental.message.request.QuoteReq;
import com.carrental.message.request.UpdateCar;
import com.carrental.message.response.JwtResponse;
import com.carrental.message.response.ResponseMessage;
import com.carrental.model.Booking;
import com.carrental.model.Car;
import com.carrental.model.Coupon;
import com.carrental.model.Location;
import com.carrental.model.Quote;
import com.carrental.security.services.NextSequenceService;

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
	
	@Autowired
	BookingDao bookingDao;
	
	@Autowired
	QuoteDao quoteDao;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
	NextSequenceService nextSequenceService;
	
	
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
	
//	@GetMapping("/zipcode/{zipcode}")
//	public List<Car> getCarsByZipcode(@PathVariable Integer zipcode){
//		
//		return this.carRepo.getCarsByZipcode(zipcode);
//	}
	
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
			if(this.couponDao.getCouponByCode(coupon.getCouponCode()) == null) {
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
			
			if(this.couponDao.getCouponByCode(coupon.getCouponCode()) == null) {
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
	
	@PostMapping("/updateQuote")
	public ResponseEntity<?> updateQuote(@RequestBody Quote quote){
		
		if("CONFIRMED".equals(quote.getQuoteStatus())) {
			Booking booking = new Booking();
			booking.setBookingId(nextSequenceService.getNextSequence(Booking.SEQUENCE_NAME));
			booking.setBookingStatus("CONFIRMED");
			booking.setContactNumber(quote.getContactNumber());
			booking.setCustomerId(quote.getCustomerId());
			booking.setDropOffDate(quote.getDropOffDate());
			booking.setDropOffloc(quote.getDropOffloc());
			booking.setEmail(quote.getEmail());
			booking.setFirstName(quote.getFirstName());
			booking.setLastName(quote.getLastName());
			booking.setPickUpDate(quote.getPickUpDate());
			booking.setPickUpLoc(quote.getPickUpLoc());
			booking.setTotalPrice(quote.getTotalPrice());
			
			bookingDao.reserveCar(booking);
			quoteDao.updateQuote(quote);
			
			try {
				SimpleMailMessage msg = new SimpleMailMessage();
		        msg.setTo(quote.getEmail());

		        msg.setSubject("Car Rental - Quote Status Update");
		        StringBuilder result = new StringBuilder();
		        result.append("Congrats !!!!, The Quote that you submitted with Reference Number:"+quote.getQuoteId()+ " is Approved");
		        result.append(System.getProperty("line.separator"));
		        result.append("Your booking Reference number is: "+booking.getBookingId());
		        msg.setText(result.toString());

		        javaMailSender.send(msg);
				}catch(Exception e) {
					return new ResponseEntity<>(new ResponseMessage("Error While Sending Email"), HttpStatus.PARTIAL_CONTENT);
				}
			return new ResponseEntity<>(new ResponseMessage("Quote Update successfully!"), HttpStatus.OK);
			
			
		} else {
			quote.setQuoteStatus("DECLINED");
			quoteDao.updateQuote(quote);
			
			try {
				SimpleMailMessage msg = new SimpleMailMessage();
		        msg.setTo(quote.getEmail());

		        msg.setSubject("Car Rental - Quote Status Update");
		        StringBuilder result = new StringBuilder();
		        result.append("Unfortunately, The Quote that you submitted with Reference Number:"+quote.getQuoteId()+ "is Declined");
		        result.append(System.getProperty("line.separator"));
		        result.append("Please try with higher price");
		        msg.setText(result.toString());

		        javaMailSender.send(msg);
				}catch(Exception e) {
					return new ResponseEntity<>(new ResponseMessage("Error While Sending Email"), HttpStatus.PARTIAL_CONTENT);
				}
			return new ResponseEntity<>(new ResponseMessage("Quote Update successfully!"), HttpStatus.OK);
			
		}
	}
	
	
	@GetMapping("/allQuotes")
	public List<Quote> allQuotes(){
		List<Quote> quotes = null;
		quotes = quoteDao.getAllQuotes();
		return quotes.stream().filter(quote -> "PENDING".equals(quote.getQuoteStatus())).collect(Collectors.toList());
	}


}
