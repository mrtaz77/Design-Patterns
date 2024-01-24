package DataTransferObjects;

import java.io.Serializable;

import stock.Stock;

public class StockDTO implements Serializable{
	private boolean status = false;
	private Stock stock;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}
