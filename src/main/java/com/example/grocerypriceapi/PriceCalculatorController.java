package com.example.grocerypriceapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class PriceCalculatorController {
    
    private final Logger log = LoggerFactory.getLogger(getClass().getCanonicalName());

    @PostMapping()
    public ResponseEntity<Mono<Void>> createCalculation(@RequestBody CalculationRequest request) {
        log.debug("Calculating item costs for customer {}", request.getCustomerId());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{calculationId}")
    public ResponseEntity<Mono<CalculationResponse>> getCalculation(@PathVariable("calculationId") String calculationId) {
        log.debug("Returning calculated value for {}", calculationId);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
