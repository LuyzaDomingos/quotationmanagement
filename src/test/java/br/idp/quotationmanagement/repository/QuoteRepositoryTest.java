package br.idp.quotationmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.idp.quotationmanagement.model.Operation;
import br.idp.quotationmanagement.model.Quote;


@DataJpaTest
@ActiveProfiles("test")
class QuoteRepositoryTest {
	
	@Autowired
	private QuoteRepository quoteRespository;

	@Test
	public void loadingOperationById() {
		
		Operation operation = new Operation("vale5");
		
		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(operation, LocalDate.of(2021, 9, 10), new BigDecimal("30")));
//		quotes.add(new Quote(operation, LocalDate.of(2021, 8, 29), new BigDecimal("5000")));
		
		
		Optional<List<Operation>> operationOptional = quoteRespository.findByStockId("vale5");
		assertTrue(operationOptional.isPresent());

		List<Operation> opList  = operationOptional.get();
		assertEquals(opList.get(0).getStockId(), "vale5");
		
	}

}
