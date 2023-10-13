package com.example.grocerypriceapi;


public record LineItem(String productId, Integer qty, Float totalCost) {
}
