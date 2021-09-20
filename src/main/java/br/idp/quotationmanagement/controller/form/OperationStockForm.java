package br.idp.quotationmanagement.controller.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.idp.quotationmanagement.model.Quoteop;
import br.idp.quotationmanagement.model.Quote;

public class OperationStockForm {

	@NotNull
	@NotEmpty
	private String stockId;

	@NotNull
	// @NotEmpty
	private Map<LocalDate, BigDecimal> quotes;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<LocalDate, BigDecimal> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<LocalDate, BigDecimal> quotes) {
		this.quotes = quotes;
	}

	public Quoteop convertList() {

		Quoteop quoteop = new Quoteop(stockId);
		quotes.forEach((timeNow, price) -> quoteop.addQuote(new Quote(quoteop, timeNow, price)));
		return quoteop;
	}

}
