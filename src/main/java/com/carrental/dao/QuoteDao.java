package com.carrental.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import com.carrental.model.Quote;

@Service
public class QuoteDao {

	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	public Quote submitQuote(Quote quote) {
		mongoTemplate.save(quote,"quote");
		return quote;
	}
	
}
