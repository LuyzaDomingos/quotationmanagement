package br.idp.quotationmanagement.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.idp.quotationmanagement.model.Quote;

public class StockForm {

	@NotNull
	@NotEmpty
	private String stockId;

	@NotNull
	@NotEmpty
	private Map<String, String> quotes;

	public String getStockId() {
		return stockId;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}
	
	public List<Quote> convertList(){
		List<Quote> listQuotes = new ArrayList<>();
		
		for(Map.Entry<String, String> q : this.quotes.entrySet()) {
			LocalDate date = LocalDate.parse(q.getKey());
			BigDecimal value = new BigDecimal(q.getValue());
			listQuotes.add(new Quote(this.stockId,date,value));
		}
		return listQuotes;
		
	}

}
