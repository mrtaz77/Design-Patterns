package util;

import java.util.concurrent.ConcurrentHashMap;

import stock.Stock;

public interface InputValidator {
	default void isNonNegative(Double value,String purpose) throws IllegalArgumentException{
		if(value < 0) throw new IllegalArgumentException("Invalid input for " + purpose );
	}
	default void isDecrementGreaterThanStockPrice(Double value,Stock stock) throws IllegalArgumentException {
		isNonNegative(value,"decreasing price of stock " + stock.getName());
		if(stock.getPrice() < value) throw new IllegalArgumentException("Decrement greater than the current price "+stock.getPrice()+" of stock " + stock.getName());
	}
	default boolean stockPresent(String stockName,ConcurrentHashMap<String, Stock> stocks) {
		return stocks.containsKey(stockName);
	}
}