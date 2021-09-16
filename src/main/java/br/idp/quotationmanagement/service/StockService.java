package br.idp.quotationmanagement.service;


import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.idp.quotationmanagement.controller.QuoteController;
import br.idp.quotationmanagement.controller.dto.StockAllDto;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class StockService {
	
	
	private String url = "http://localhost:8080";
	private RestTemplate restTemplate = new RestTemplate();
	
	Logger log = LoggerFactory.getLogger(QuoteController.class);

	@Cacheable(value = "stockList")
	public List<StockAllDto> listAll(){
		log.info("Capturando todos os stocks e populando a cache");
		
		String URL = url +"/stock";
		StockAllDto [] stockAllDto = restTemplate.getForObject(URL, StockAllDto[].class);
		
		return Arrays.asList(stockAllDto);
	}
	
	@Cacheable(value = "stock")
	public StockAllDto listById(String id){
		log.info("Capturando stock um a um");
		
		String URL = url +"/stock"+ id;
		StockAllDto  stockAllDto = restTemplate.getForObject(URL, StockAllDto.class);
		
		return stockAllDto;
	}
	
	
	
	public void notificationOfRegister() {
		HttpHeaders titleHeaders = new HttpHeaders();
		
		titleHeaders.setContentType(MediaType.APPLICATION_JSON);
		JSONObject dataJsonObject  = new JSONObject();
		dataJsonObject.put("host", "localhost");
		dataJsonObject.put("port", "8081");
		HttpEntity<String> request = new HttpEntity<String>(dataJsonObject.toString(),titleHeaders);
		restTemplate.postForObject(url + "/notification", request, String.class);
		
		
	}
	
	

}
