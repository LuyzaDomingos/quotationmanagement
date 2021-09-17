package br.idp.quotationmanagement.controller.dto;

import java.io.Serializable;

public class StockAllDto {

	private String stockId;
	private String description;

	public StockAllDto(String stockId, String description) {

		this.stockId = stockId;
		this.description = description;
	}

	public StockAllDto() {
		super();
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
