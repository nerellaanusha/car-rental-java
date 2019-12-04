package com.carrental.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.dao.BookingDao;
import com.carrental.dao.CarRepo;
import com.carrental.dao.CouponDao;
import com.carrental.message.request.BookingReq;
import com.carrental.message.request.HomePageRequest;
import com.carrental.message.response.CarResponse;
import com.carrental.message.response.ResponseMessage;
import com.carrental.model.Booking;
import com.carrental.model.Car;
import com.carrental.model.Coupon;
import com.carrental.security.services.NextSequenceService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	CarRepo carRepo;
	
	@Autowired
	CouponDao couponDao;
	
	@Autowired
	NextSequenceService nextSequenceService;
	
	@Autowired
	BookingDao bookingDao;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	
	
	@PostMapping("/getCarsOnZipcode")
	public List<CarResponse> getCarsByZipcode(@RequestBody HomePageRequest homepageReq){
		
		List<Car> cars = this.carRepo.getCarsByZipcode(homepageReq.getPickUpLoc());
		List<CarResponse> carsResp= new ArrayList<CarResponse>();
		
		if(cars.size() > 1) {
			long difference =  (homepageReq.getDropOffDate().getTime()-homepageReq.getPickUpDate().getTime())/86400000;
	        long days =  Math.abs(difference);
	        
	        CarResponse carRes = null;
	        for(Car car: cars) {
	        	carRes = new CarResponse();
	        	carRes.setBaggage(car.getBaggage());
	        	carRes.setCapacity(car.getCapacity());
	        	carRes.setCarMake(car.getCarMake());
	        	carRes.setCarModel(car.getCarModel());
	        	carRes.setImage(car.getImage());
	        	carRes.setPrice(car.getPrice());
	        	carRes.setTotalPrice(days * car.getPrice());
	        	carRes.setTotalPriceWithTax(days * car.getPrice()  +  (days * car.getPrice()* 0.10));
	        	carRes.setType(car.getType());
	        	carRes.setVin(car.getVin());
	        carRes.setTax(new Float(10));
	        	carRes.setZipcode(car.getZipcode());
	        	carsResp.add(carRes);
	        }
		}
		return carsResp;
	}
	
	@GetMapping("/coupon/{code}")
	public ResponseEntity<?> getCoupon(@PathVariable String code) {
		
		Coupon coupon = this.couponDao.getCouponByCode(code);
		if(coupon == null) {
			return new ResponseEntity<>(new ResponseMessage("No Coupon"), HttpStatus.EXPECTATION_FAILED);
		}
		
		return new ResponseEntity<>(new ResponseMessage("Coupon Found"), HttpStatus.OK).ok(coupon);
		
	}
	
	@PostMapping("/reserveCar")
	public ResponseEntity<?> reserveCar(@RequestBody BookingReq bookingReq){
		
		Booking booking = new Booking();
		booking.setBookingId(nextSequenceService.getNextSequence(Booking.SEQUENCE_NAME));
		booking.setBookingStatus("CONFIRMED");
		booking.setContactNumber(bookingReq.getContactNumber());
		booking.setCustomerId(bookingReq.getCustomerId());
		booking.setDropOffDate(bookingReq.getDropOffDate());
		booking.setDropOffloc(bookingReq.getDropOffloc());
		booking.setEmail(bookingReq.getEmail());
		booking.setFirstName(bookingReq.getFirstName());
		booking.setLastName(bookingReq.getLastName());
		booking.setPickUpDate(bookingReq.getPickUpDate());
		booking.setPickUpLoc(bookingReq.getPickUpLoc());
		booking.setTotalPrice(bookingReq.getTotalPrice());
		
		bookingDao.reserveCar(booking);
		
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(bookingReq.getEmail());

	        msg.setSubject("Car Rental Confirmation");
	        msg.setText("Your reservation is confirmed. Booking Reference Number:"+booking.getBookingId());

	        javaMailSender.send(msg);
			}catch(Exception e) {
				return new ResponseEntity<>(new ResponseMessage("Error While Sending Email"), HttpStatus.PARTIAL_CONTENT);
			}
		
		return new ResponseEntity<>(new ResponseMessage("Booking Succesfull"), HttpStatus.OK).ok(booking.getBookingId());
	}
	
	@GetMapping("/retreiveBookings/{customerId}")
	public List<Booking> retreiveBookings(@PathVariable Long customerId) {
		return bookingDao.getBookingsByCustomer(customerId);
	}
	
	
	
	

}
