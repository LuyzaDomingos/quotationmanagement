package br.idp.quotationmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Quote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String stockId;
	private BigDecimal price;
	private LocalDate timenow;

	public Quote(String stockId, LocalDate date, BigDecimal value) {
		this.stockId = stockId;
		this.price = value;
		this.timenow = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public BigDecimal getValue() {
		return price;
	}

	public void setValue(BigDecimal value) {
		this.price = value;
	}

	public LocalDate getDate() {
		return timenow;
	}

	public void setDate(LocalDate date) {
		this.timenow = date;
	}

}
