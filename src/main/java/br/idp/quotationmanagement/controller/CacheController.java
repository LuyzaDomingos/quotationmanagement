package br.idp.quotationmanagement.controller;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.idp.quotationmanagement.service.StockService;

@RestController
@RequestMapping("/stockcache")
public class CacheController {

	Logger log = LoggerFactory.getLogger(QuoteController.class);

	@Autowired
	public CacheController(StockService stockServices) {
		
		stockServices.notificationOfRegister();

	}

	@DeleteMapping
	@Transactional
	@Caching(evict = { @CacheEvict(value = "getStockAll", allEntries = true),
			@CacheEvict(value = "getStockAll", allEntries = true) })
	public ResponseEntity<?> cacheClear() {
		log.info("Limpando cache....");
		return ResponseEntity.ok().build();
	}

}
