package com.example.grocerypriceapi;

import java.util.List;

public class CalculationRequest {
    
    private String customerId;
    private List<LineItem> lineItems;

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<LineItem> getLineItems() {
        return this.lineItems;
    }

    public void setLineItems(List<LineItem> items) {
        this.lineItems = items;
    }
}
