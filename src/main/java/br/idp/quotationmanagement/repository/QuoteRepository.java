package br.idp.quotationmanagement.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.idp.quotationmanagement.model.Operation;

public interface QuoteRepository extends JpaRepository<Operation, UUID>{
	 Optional<List<Operation>> findByStockId(String id);

}
