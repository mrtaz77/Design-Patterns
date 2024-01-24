package observers;

import stock.Stock;

public class Admin implements InputValidator {
	private UserType type = UserType.ADMIN;
	public UserType getType() {
		return type;
	}
	public void increasePrice(Stock stock,Double increment) {
		isNonNegative(increment,"increasing price of stock "+ stock.getName());
		stock.setPrice(stock.getPrice() + increment);
	}
	public void decreasePrice(Stock stock,Double decrement) {
		isDecrementGreaterThanStockPrice(decrement, stock);
		stock.setPrice(stock.getPrice() - decrement);
	}
	public void changeCount(Stock stock,int count) {
		isNonNegative((double)count,"changing count of stock "+ stock.getName());
		stock.setQuantity(count);
	}
}