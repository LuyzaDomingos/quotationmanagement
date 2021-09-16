package br.idp.quotationmanagement.controller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.idp.quotationmanagement.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long>{
	 List<Quote> findByStockId(String id);

}
