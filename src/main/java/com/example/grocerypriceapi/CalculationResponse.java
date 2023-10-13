package com.example.grocerypriceapi;

import java.util.List;

public record CalculationResponse(String calculationId, List<LineItem> calculations, List<TaxItem> taxes) {
}
