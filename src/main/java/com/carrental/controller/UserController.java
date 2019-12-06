package com.carrental.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.client.RestTemplate;

import com.carrental.dao.BookingDao;
import com.carrental.dao.CarRepo;
import com.carrental.dao.CouponDao;
import com.carrental.dao.QuoteDao;
import com.carrental.message.request.BookingReq;
import com.carrental.message.request.HomePageRequest;
import com.carrental.message.request.QuoteReq;
import com.carrental.message.response.CarResponse;
import com.carrental.message.response.NearByCar;
import com.carrental.message.response.ResponseMessage;
import com.carrental.message.response.ZipCodeResponse;
import com.carrental.message.response.Zipcode;
import com.carrental.model.Booking;
import com.carrental.model.Car;
import com.carrental.model.Coupon;
import com.carrental.model.Quote;
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
	QuoteDao quoteDao;
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	
	
	@PostMapping("/getCarsOnZipcode")
	public ResponseEntity<?> getCarsByZipcode(@RequestBody HomePageRequest homepageReq){
		
		List<CarResponse> carsResp= new ArrayList<CarResponse>();
		try {
		
		String apiKey = "ogNnaGqAGPRh5dkjowTER8gLUdnQwgYqF69PEf2EttNL5f7rEdDmVf0QkpkJT0D9";
		String uri = "https://www.zipcodeapi.com/rest/"+apiKey+"/radius.json/"+homepageReq.getPickUpLoc()+"/20/mile";
	     
	    RestTemplate restTemplate = new RestTemplate();
	    ZipCodeResponse result = restTemplate.getForObject(uri, ZipCodeResponse.class);
	    List<Zipcode> zipcodeObjs = result.getZip_codes();
	    List<Integer> zipcodes = zipcodeObjs.stream().map(zip -> Integer.valueOf(zip.getZip_code())).collect(Collectors.toList());
	    zipcodes.add(homepageReq.getPickUpLoc()); 
	  
	    List<Car> cars = this.carRepo.getCarsByZipcode(zipcodes);
		
		
		if(cars.size() > 1) {
			long difference =  (homepageReq.getDropOffDate().getTime()-homepageReq.getPickUpDate().getTime())/86400000;
	        long days =  Math.abs(difference);
	        
	        CarResponse carRes = null;
	        for(Car car: cars) {
		        	if(homepageReq.getPickUpLoc().equals(car.getZipcode())) {
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
		        	for(Car veh: cars) {
		        		if(veh.getCarModel().equalsIgnoreCase(car.getCarModel()) && !veh.getZipcode().equals(car.getZipcode())) {
		        			NearByCar nearByCar = new NearByCar();
		        			nearByCar.setPrice(veh.getPrice());
		        			nearByCar.setZipcode(veh.getZipcode());
		        			carRes.getNearByCars().add(nearByCar);
		        		}	
		        	}
		        	
		        	carsResp.add(carRes);
		        }
	        }
		}
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ResponseMessage("Error While Fetching Cars"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	        
	            
		return new ResponseEntity<>(new ResponseMessage("Found Cars"), HttpStatus.OK).ok(carsResp);  
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
	
	
	@PostMapping("/submitQuote")
	public ResponseEntity<?> submitQuote(@RequestBody QuoteReq quoteReq){
		
		Quote quote = new Quote();
		quote.setContactNumber(quoteReq.getContactNumber());
		quote.setCustomerId(quoteReq.getCustomerId());
		quote.setDropOffDate(quoteReq.getDropOffDate());
		quote.setDropOffloc(quoteReq.getDropOffloc());
		quote.setPickUpDate(quoteReq.getPickUpDate());
		quote.setPickUpLoc(quoteReq.getPickUpLoc());
		quote.setEmail(quoteReq.getEmail());
		quote.setFirstName(quoteReq.getFirstName());
		quote.setLastName(quoteReq.getLastName());
		quote.setQuoteStatus("PENDING");
		quote.setVin(quoteReq.getVin());
		quote.setTotalPrice(quoteReq.getActualPrice());
		quote.setUserPrice(quoteReq.getUserPrice());
		quote.setEmail(quoteReq.getEmail());
		quote.setQuoteId(nextSequenceService.getNextSequence(Quote.SEQUENCE_NAME));
		
		quoteDao.submitQuote(quote);
		
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(quoteReq.getEmail());

	        msg.setSubject("Car Rental - Quote Submitted");
	        StringBuilder result = new StringBuilder();
	        result.append("Your quote is submitted. Quote Reference Number:"+quote.getQuoteId());
	        result.append(System.getProperty("line.separator"));
	        result.append("Please allow 2-3 business days to get response and you will receive an email when quote is approved/declined");
	        msg.setText(result.toString());

	        javaMailSender.send(msg);
			}catch(Exception e) {
				return new ResponseEntity<>(new ResponseMessage("Error While Sending Email"), HttpStatus.PARTIAL_CONTENT);
			}
		
		return new ResponseEntity<>(new ResponseMessage("Quote Submitted Succesfully"), HttpStatus.OK);
	}
	
	
	

}
