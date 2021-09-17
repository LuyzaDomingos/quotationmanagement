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
import br.idp.quotationmanagement.controller.repository.QuoteRepository;
import br.idp.quotationmanagement.model.Operation;
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

	@GetMapping("/{stockId}")
	public ResponseEntity<?> listId(@PathVariable String id) {
		log.info("Stock Request {}", id);

		Optional<List<Operation>> operations = quoteRepository.findByStockId(id);

		if (!operations.isPresent()) {
			log.info("Stock id não eh valida");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageErrorDto("stockId", "Nao consta regitrado stock com o id de" + id));
		}
		return ResponseEntity.ok(OperationStockDto.convert(operations.get()));
	}

	@GetMapping
	public ResponseEntity<?> listAll() {
		List<Operation> operations = quoteRepository.findAll();
		return ResponseEntity.ok(OperationStockDto.convert(operations));

	}

	@PostMapping
	@Transactional
	public ResponseEntity<?> create(@RequestBody @Valid OperationStockForm form,
			UriComponentsBuilder uriComponentsBuilder) {

		if (stockService.getStockId(form.getStockId()) == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new MessageErrorDto("stockId", "Nao consta regitrado stock com o id de" + form.getStockId()));
		}

		Operation operation = form.convertList();
		quoteRepository.save(operation);
		URI uri = uriComponentsBuilder.path("/quote/{stockId}").buildAndExpand(operation.getStockId()).toUri();
		return ResponseEntity.created(uri).body(new OperationStockDto(operation));

	}

}