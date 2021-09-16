package br.idp.quotationmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class QuotationmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuotationmanagementApplication.class, args);
	}

}
