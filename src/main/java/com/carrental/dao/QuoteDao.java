package com.carrental.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
	
	public List<Quote> getAllQuotes(){
		return this.mongoTemplate.findAll(Quote.class);
	}
	
	
	public Quote updateQuote(Quote quote) {
		Query quoteQuery = new Query();
		quoteQuery.addCriteria(Criteria.where("vin").is(quote.getQuoteId()));
		Quote q = this.mongoTemplate.findOne(quoteQuery,Quote.class);
		
		Update update = new Update();
		update.set("quoteStatus", quote.getQuoteStatus());
		
		this.mongoTemplate.updateFirst(quoteQuery, update, Quote.class);
		return this.mongoTemplate.findOne(quoteQuery,Quote.class);
	}
	
}
