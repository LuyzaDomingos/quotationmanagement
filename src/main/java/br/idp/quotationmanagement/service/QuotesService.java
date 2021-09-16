package br.idp.quotationmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.idp.quotationmanagement.controller.repository.QuoteRepository;
import br.idp.quotationmanagement.model.Quote;


@Service
public class QuotesService {
	
	QuoteRepository quoteRepository;

	@Autowired
	public QuotesService(QuoteRepository quoteRepository) {
		this.quoteRepository = quoteRepository;
	}
	
	
	public void quoteSave(List<Quote> quotes) {
		quoteRepository.saveAll(quotes);
	}
	
	public List<Quote> findByStockId(String id){
		return quoteRepository.findByStockId(id);
	}
	
	
}
