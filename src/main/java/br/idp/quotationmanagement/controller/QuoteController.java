package br.idp.quotationmanagement.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.idp.quotationmanagement.controller.dto.MessageErrorDto;
import br.idp.quotationmanagement.controller.dto.OperationStockDto;
import br.idp.quotationmanagement.controller.form.OperationStockForm;
import br.idp.quotationmanagement.model.Quoteop;
import br.idp.quotationmanagement.repository.QuoteRepository;
import br.idp.quotationmanagement.service.StockService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/quote")
@Slf4j
public class QuoteController {

	@Autowired
	private StockService stockService;
	@Autowired
	private QuoteRepository quoteRepository;

	Logger log = LoggerFactory.getLogger(QuoteController.class);

	public QuoteController(StockService stockService, QuoteRepository quoteRepository) {

		this.stockService = stockService;
		this.quoteRepository = quoteRepository;
	}

	@GetMapping("/{stockId}")
	public ResponseEntity<?> listId(@PathVariable("stockId") String stockId) {
		log.info("Stock Request {}", stockId);

		Optional<List<Quoteop>> quoteops = quoteRepository.findByStockId(stockId);

		if (quoteops.isEmpty()) {
			log.info("Stock id não foi encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageErrorDto("stockId", "Nao foi encontrado stock com o id de" + stockId));
		}
		return ResponseEntity.ok(OperationStockDto.convert(quoteops.get()));

	}

	@GetMapping
	public ResponseEntity<?> listAll() {
		List<Quoteop> quoteops = quoteRepository.findAll();
		return ResponseEntity.ok(OperationStockDto.convert(quoteops));

	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> create(@RequestBody @Valid OperationStockForm form,
			UriComponentsBuilder uriComponentsBuilder) {

		if (stockService.getStockId(form.getStockId()) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new MessageErrorDto("stockId", "Nao existe stock registrado com o id de " + form.getStockId()));
		}

		Quoteop quoteop = form.convertList();

		if (quoteop.getQuotes().isEmpty()) {
			log.warn("Quoteop received contains zero quotes");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageErrorDto("quotes", "Campo vazio"));
		}

		quoteRepository.save(quoteop);
		URI uri = uriComponentsBuilder.path("/quote/{stockId}").buildAndExpand(quoteop.getStockId()).toUri();
		return ResponseEntity.created(uri).body(new OperationStockDto(quoteop));

	}

}
