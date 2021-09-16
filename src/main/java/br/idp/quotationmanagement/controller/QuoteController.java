package br.idp.quotationmanagement.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;



import br.idp.quotationmanagement.controller.dto.StockAllDto;
import br.idp.quotationmanagement.controller.dto.StockDto;
import br.idp.quotationmanagement.controller.form.StockForm;
import br.idp.quotationmanagement.controller.repository.QuoteRepository;
import br.idp.quotationmanagement.model.Quote;
import br.idp.quotationmanagement.service.QuotesService;
import br.idp.quotationmanagement.service.StockService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/quote")
@Slf4j
public class QuoteController {

	@Autowired
	private StockService stockService;
	@Autowired
	private QuotesService quotesService;
	@Autowired
	private QuoteRepository quoteRepository;

	Logger log = LoggerFactory.getLogger(QuoteController.class);

	@GetMapping("/{id}")
	public ResponseEntity<?> listId(@PathVariable String id) {
		log.info("Request {}", id);

		List<Quote> quotes = quotesService.findByStockId(id);

		if (quotes.isEmpty()) {
			log.info("O stock" + id + "não existe");
			JSONObject message = new JSONObject();
			message.put("Warning: ", "The Stock "+ id +"doesn't exist");
			return ResponseEntity.status(404).body(message.toString());
		}
		log.info("Stock existe!");
		return ResponseEntity.ok(new StockDto(id, quotes));
	}

	@GetMapping
	public ResponseEntity<List<StockDto>> listAll() {
		List<StockAllDto> stockAllDto = stockService.listAll();
		List<StockDto> stockDto = new ArrayList<StockDto>();

		stockAllDto.forEach(stock -> {
			List<Quote> quotes = quotesService.findByStockId(stock.getId());
			stockDto.add(new StockDto(stock.getId(), quotes));
		});
		log.info("Essas são todas as stocks");
		return ResponseEntity.ok(stockDto);

	}

	public ResponseEntity<?> create(@RequestBody @Valid StockForm form,
			UriComponentsBuilder uriComponentsBuilder){
		
		StockAllDto stockAllDto = stockService.listById(form.getStockId());
		if(stockAllDto == null) {
			log.info("O stock não existe");
			JSONObject message = new JSONObject();
			message.put("Warning: ", "The Stock  doesn't exist");
			return ResponseEntity.status(404).body(message.toString());
			
		}
		
		quotesService.quoteSave(form.convertList());
		
		URI uri  = uriComponentsBuilder.path("quote/{id}").buildAndExpand(form.getStockId()).toUri();
		List<Quote> listQuotes = quotesService.findByStockId(form.getStockId());
		log.debug("Request{}", listQuotes);
		
		return ResponseEntity.created(uri).body(new StockDto(form.getStockId(), listQuotes));
	
	}

}
