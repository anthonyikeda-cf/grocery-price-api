package com.example.grocerypriceapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/calculation/v1")
public class PriceCalculatorController {
    
    private final Logger log = LoggerFactory.getLogger(getClass().getCanonicalName());
    private final Map<String, CalculationResponse> responseMap = new HashMap<>();

    @PostMapping()
    public ResponseEntity<Mono<Void>> createCalculation(@RequestBody CalculationRequest request) {
        log.debug("Calculating item costs for customer {}", request.getCustomerId());
        String calculationId = UUID.randomUUID().toString();

        switch(request.getCalculationType()) {
            case TOTALS:
                List<LineItem> calculatedItems = calculateTotals(request.getLineItems());
                responseMap.put(calculationId, new CalculationResponse(calculationId, calculatedItems, null));
                break;
            case TAXES:
                List<TaxItem> taxedItems = calculationTaxes(request.getLineItems());
                responseMap.put(calculationId, new CalculationResponse(calculationId, null, taxedItems));
                break;
            case ALL:
                List<LineItem> calcItems = calculateTotals(request.getLineItems());
                List<TaxItem> taxItems = calculationTaxes(request.getLineItems());
                responseMap.put(calculationId, new CalculationResponse(calculationId, calcItems, taxItems));
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create(String.format("/calculation/v1/%s", calculationId))).build();
    }

    @GetMapping("/{calculationId}")
    public ResponseEntity<Mono<CalculationResponse>> getCalculation(@PathVariable("calculationId") String calculationId) {
        log.debug("Returning calculated value for {}", calculationId);
        var calculation = this.responseMap.get(calculationId);
        return ResponseEntity.ok(Mono.just(calculation));
    }

    private List<LineItem> calculateTotals(List<LineItem> lineItems) {
        return lineItems.stream().map(items -> {
            String productId = items.productId();
            Integer qty = items.qty();
            Float totalCost = Double.valueOf(3.20 * qty).floatValue();
            return new LineItem(productId, qty, totalCost);
        }).toList();
    }

    private List<TaxItem> calculationTaxes(List<LineItem> lineItems) {
        return lineItems.stream().map(items -> {
            Float taxPercentage = 0.09f;
            Integer qty = items.qty();
            Float totalCost = Double.valueOf(3.20 * qty).floatValue();
            return new TaxItem("GST", taxPercentage, totalCost * (1+taxPercentage));
        }).toList();
    }
}
