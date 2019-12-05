package com.carrental.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carrental.dao.UserRepo;
import com.carrental.message.request.LoginForm;
import com.carrental.message.request.SignUpForm;
import com.carrental.message.response.JwtResponse;
import com.carrental.message.response.ResponseMessage;
import com.carrental.model.Role;
import com.carrental.model.RoleName;
import com.carrental.model.User;
import com.carrental.repository.RoleRepository;
import com.carrental.repository.UserRepository;
import com.carrental.security.jwt.JwtProvider;
import com.carrental.security.services.NextSequenceService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthRestAPIs {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	NextSequenceService nextSequenceService;
	
	@Autowired
	UserRepo userRepo;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userRepo.findByUserName(loginRequest.getUsername());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(),user.getId(),user.getFirstName()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		
		try {
			
		if(userRepo.findByUserName(signUpRequest.getUserName())!=null && 
				userRepo.findByUserName(signUpRequest.getUserName()).getEmail() != null) {
			return new ResponseEntity<>(new ResponseMessage("Username is already taken!"),
					HttpStatus.CONFLICT);
		}

		if (userRepo.findByEmail(signUpRequest.getEmail()) != null && 
				userRepo.findByEmail(signUpRequest.getEmail()).getEmail() != null) {
			return new ResponseEntity<>(new ResponseMessage("Email is already in use!"),
					HttpStatus.CONFLICT);
		}

	
		User user = new User(signUpRequest.getFirstName(), signUpRequest.getLastName(),signUpRequest.getUserName(),
				signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()));
		
		user.setId(nextSequenceService.getNextSequence(User.SEQUENCE_NAME));
		user.setRole("ROLE_USER");

		userRepository.save(user);

		
		
		}catch( Exception e) {
			e.printStackTrace();
			System.out.println();
		}
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signUpRequest.getUserName(), signUpRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User user = userRepo.findByUserName(signUpRequest.getUserName());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(),
				user.getId(),user.getFirstName()));
		
		//return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
		
	}
	
	
	
}