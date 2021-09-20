package br.idp.quotationmanagement.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class QuoteopTest {

	@Test
	public void creatingOperation() {
		Quoteop quoteop = new Quoteop("vale5");

		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(quoteop, LocalDate.of(2021, 9, 10), new BigDecimal("30")));
		quotes.add(new Quote(quoteop, LocalDate.of(2021, 8, 29), new BigDecimal("5000")));

		assertEquals(quotes.size(), 2);
		assertEquals(quotes.get(0).getTimeNow(), LocalDate.of(2021, 9, 10));
		assertEquals(quotes.get(1).getTimeNow(), LocalDate.of(2021, 8, 29));

	}

}
