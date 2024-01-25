package DataTransferObjects;

import java.io.Serializable;


public class StockInitUpdateDTO implements Serializable{
	private UpdateType type;
	private String stockName = "";
	private Double amount = 0.0;

	public StockInitUpdateDTO(UpdateType type, String stockName, Double amount) {
		this.type = type;
		this.stockName = stockName;
		this.amount = amount;
	}

	public UpdateType getType() {
        return type;
    }

    public void setType(UpdateType type) {
        this.type = type;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}