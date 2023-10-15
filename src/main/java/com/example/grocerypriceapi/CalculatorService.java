package com.example.grocerypriceapi;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculatorService {

    List<LineItem> calculateTotals(List<LineItem> lineItems) {
        return lineItems.stream().map(items -> {
            String productId = items.productId();
            Integer qty = items.qty();
            Float totalCost = Double.valueOf(3.20 * qty).floatValue();
            return new LineItem(productId, qty, totalCost);
        }).toList();
    }
    List<TaxItem> calculationTaxes(List<LineItem> lineItems) {
        return lineItems.stream().map(items -> {
            float taxPercentage = 0.09f;
            Integer qty = items.qty();
            float totalCost = Double.valueOf(3.20 * qty).floatValue();
            return new TaxItem("GST", taxPercentage, totalCost * (1+taxPercentage));
        }).toList();
    }
}
