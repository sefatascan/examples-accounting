package com.sefa.accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AccountingApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountingApplication.class, args);
	}

}
