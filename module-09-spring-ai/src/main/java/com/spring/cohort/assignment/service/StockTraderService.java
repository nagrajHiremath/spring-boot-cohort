package com.spring.cohort.assignment.service;

import org.springframework.stereotype.Service;

@Service
public class StockTraderService {

    public String buyAppleStock(Integer quantity) {
        return "Apple stock purchased.";
    }

    public String sellAppleStock(Integer quantity) {
        return "Apple stock sold.";
    }

    public String getAppleStockPrice() {
        return "Apple Stock Price: 150 USD";
    }

}
