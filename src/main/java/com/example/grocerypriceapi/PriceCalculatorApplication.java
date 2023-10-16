package com.example.grocerypriceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;

@SpringBootApplication
@EnableReactiveMethodSecurity
public class PriceCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceCalculatorApplication.class, args);
	}

}
