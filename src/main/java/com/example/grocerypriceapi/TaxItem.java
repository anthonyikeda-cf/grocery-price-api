package com.example.grocerypriceapi;

public record TaxItem(String taxType, Float taxPercentage, Float totalCost) {
}
