package br.idp.quotationmanagement.controller.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import br.idp.quotationmanagement.model.Quote;
public class StockDto {
	
	private String id;
	private Map<String,String> quoteMap = new HashMap<String, String>();
	
	public StockDto(String id, List<Quote> quotes) {
		this.id = id;
		quoteToMap(quotes);
	}
	

	public String getId() {
		return id;
	}

	public Map<String, String> getQuoteMap() {
		return quoteMap;
	}


	private void quoteToMap(List<Quote> quotes) {
		quotes.forEach(q->{
			String date = q.getDate().toString();
			String value = q.getValue().toBigInteger().toString();
			this.quoteMap.put(date,value);
		});
		
	}

}
