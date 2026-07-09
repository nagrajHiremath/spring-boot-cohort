package com.spring.cohort.assignment.tools;

import com.spring.cohort.assignment.service.StockTraderService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class StockTraderTools {

    private final StockTraderService stockTraderService;

    @Tool(
            name = "buy_apple_stock_tool",
            description = "Buy a specific quantity of Apple (AAPL) stock for the user."
    )
    // FIX: Accept a parameter so the LLM can pass the extracted value
    public String buyAppleStock(
            @ToolParam(description = "The number of shares to buy") int quantity
    ) {
        return stockTraderService.buyAppleStock(quantity);
    }

    @Tool(
            name = "sell_apple_stock_tool",
            description = "Sell a specific quantity of Apple (AAPL) stock for the user."
    )
    // FIX: Accept a parameter so the LLM can pass the extracted value
    public String sellAppleStock(
            @ToolParam(description = "The number of shares to sell") int quantity
    ) {
        return stockTraderService.sellAppleStock(quantity);
    }

    @Tool(
            name = "get_apple_stock_price_tool",
            description = "Get the current market price of Apple (AAPL) stock."
    )

    public String getAppleStockPrice() {
        return stockTraderService.getAppleStockPrice();
    }
}
