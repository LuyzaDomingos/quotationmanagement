package br.idp.quotationmanagement.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.containsString;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.idp.quotationmanagement.controller.dto.StockAllDto;
import br.idp.quotationmanagement.service.StockService;
import jdk.jfr.ContentType;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
class QuoteControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	
	private List<StockAllDto> stockDtoList;
	
	@MockBean
	private StockService stockService;

	@BeforeEach
	public void setUp() {

		stockDtoList = new ArrayList<>();
		stockDtoList.add(new StockAllDto("vale5", "Vale do Rio Doce PN"));
		stockDtoList.add(new StockAllDto("petr4", "Petrobras PN"));
	}

	@Test
	public void creatingNewOperation() throws Exception {

		Mockito.when(stockService.getStockId("vale5")).thenReturn(stockDtoList.get(0));

		JSONObject quote = new JSONObject();
		JSONObject quoteBody = new JSONObject();

		quote.put("2021-09-20", "123");
		quote.put("2020-09-21", "456");
		quote.put("2019-09-21", "789");

		quoteBody.put("stockId", "vale5");
		quoteBody.put("quotes", quote);

		mockMvc.perform(MockMvcRequestBuilders.post("/quote").content(quoteBody.toString()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("stockId")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("2021-09-20")));

	}

	@Test
	public void returnAllOperations() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/quote"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("stockId")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));
				
	}
	

	public void returnOperationsId() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/quote/vale5"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string(containsString("id")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("stockId")))
				.andExpect(MockMvcResultMatchers.content().string(containsString("quotes")));

	}

}
