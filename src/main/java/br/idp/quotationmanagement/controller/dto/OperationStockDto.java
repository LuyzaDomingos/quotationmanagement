package br.idp.quotationmanagement.controller.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.idp.quotationmanagement.model.Operation;
import br.idp.quotationmanagement.model.Quote;
public class OperationStockDto {
	
	private String id;
	private String stockId;
	private Map<LocalDate,String> quotes = new HashMap<LocalDate, String>();
	
	public OperationStockDto(Operation operation) {
		this.id = operation.getUuid().toString();
		this.stockId = operation.getStockId();
		operation.getQuotes().forEach(q->quotes.put(q.getTimeNow(), q.getPrice().toString()));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<LocalDate, String> getQuotes() {
		return quotes;
	}

	public void setQuoteMap(Map<LocalDate, String> quoteMap) {
		this.quotes = quoteMap;
	}
	
	
	public static List<OperationStockDto> convert(List<Operation> operation){
		return operation.stream().map(OperationStockDto::new).collect(Collectors.toList());
	}
	
	
	
	
	
	



}
