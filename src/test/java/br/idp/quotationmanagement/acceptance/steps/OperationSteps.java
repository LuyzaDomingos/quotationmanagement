package br.idp.quotationmanagement.acceptance.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import br.idp.quotationmanagement.controller.QuoteController;
import br.idp.quotationmanagement.controller.dto.StockAllDto;
import br.idp.quotationmanagement.model.Quote;
import br.idp.quotationmanagement.model.Quoteop;
import br.idp.quotationmanagement.repository.QuoteRepository;
import br.idp.quotationmanagement.service.StockService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.experimental.PackagePrivate;

public class OperationSteps {

	private ResponseEntity<?> responseEntity;

	private QuoteController quoteController;

	@Mock
	private StockService stockService;

	@Mock
	private QuoteRepository quoteRepository;

	@Given("Operations register on database")
	public void operations_register_on_database() {

		Quoteop quoteop = new Quoteop("vale5");
		Quoteop quote = new Quoteop("petr4");
		List<Quoteop> operation = new ArrayList<>();
		operation.add(quoteop);
		operation.add(quote);

		List<Quote> quotes = new ArrayList<>();
		quotes.add(new Quote(quoteop, LocalDate.of(2021, 9, 10), new BigDecimal("30")));
		quotes.add(new Quote(quote, LocalDate.of(2021, 8, 29), new BigDecimal("5000")));

		MockitoAnnotations.openMocks(this);
		Mockito.when(stockService.getStockId("vale5")).thenReturn(new StockAllDto("vale5", "Vale do Rio Doce PN"));
		Mockito.when(stockService.getStockId("petr4")).thenReturn(new StockAllDto("petr4", "Petrobras PN"));
		Mockito.when(quoteRepository.findByStockId("vale5")).thenReturn(Optional.of(operation));
		Mockito.when(quoteRepository.findByStockId("petr4")).thenReturn(Optional.of(operation));

		quoteController = new QuoteController(stockService, quoteRepository);

	}

	@When("the are required with the stock id {string}")
	public void the_are_required_with_the_stock_id(String string) {
		responseEntity = quoteController.listId(string);

	}

	@Then("the response is the http status {int}")
	public void the_response_is_the_http_status_status(Integer int1) {
		assertEquals(int1, responseEntity.getStatusCode().value());
	}

}
