package br.idp.quotationmanagement.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.idp.quotationmanagement.controller.QuoteController;
import br.idp.quotationmanagement.controller.dto.StockAllDto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StockService {

	// private String url = "http://localhost:8080";

	@Value("${stock-manager.url}")
	private String url;

	private RestTemplate restTemplate = new RestTemplate();

	Logger log = LoggerFactory.getLogger(QuoteController.class);

	@Cacheable(value = "getStockAll")
	public List<StockAllDto> getStockAll() {
		log.info("Acessando a API para capturar todos os stock");

		String URL = url + "/stock/";
		StockAllDto[] stockAllDto = restTemplate.getForObject(URL, StockAllDto[].class);

		return Arrays.asList(stockAllDto);
	}

	@Cacheable(value = "getStockId")
	public StockAllDto getStockId(String id) {
		log.info("Acessando a API para capturar um stock especifico");

		String URL = url + "/stock/" + id;
		return restTemplate.getForObject(URL, StockAllDto.class);

	}

	public void notificationOfRegister() {

		HttpHeaders titleHeaders = new HttpHeaders();
		titleHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		JSONObject dataJsonObject = new JSONObject();
		
		dataJsonObject.put("host", "localhost");
		dataJsonObject.put("port", "8081");
		
		HttpEntity<String> request = new HttpEntity<String>(dataJsonObject.toString(), titleHeaders);
		restTemplate.postForObject(url + "/notification", request, String.class);

	}
	

}
