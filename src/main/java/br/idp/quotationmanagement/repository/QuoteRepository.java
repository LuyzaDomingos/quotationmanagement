package br.idp.quotationmanagement.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.idp.quotationmanagement.model.Quoteop;

public interface QuoteRepository extends JpaRepository<Quoteop, UUID>{
	 Optional<List<Quoteop>> findByStockId(String id);

}
