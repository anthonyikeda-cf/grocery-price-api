package com.example.grocerypriceapi;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PriceCalculatorApplication.class)
public class CalculatorServiceTest {

    @Autowired
    CalculatorService service;

    @DisplayName("Test calculations are consistent")
    @Test
    public void testCalculateTotals() {
        List<LineItem> items = new ArrayList<>();

        LineItem item1 = new LineItem("asdfgh", 4, null);
        LineItem item2 = new LineItem("vbghj", 2, null);

        items.add(item1);
        items.add(item2);

        List<LineItem> results = this.service.calculateTotals(items);
        assertThat(results).isNotEmpty().hasSize(items.size());
    }
}
