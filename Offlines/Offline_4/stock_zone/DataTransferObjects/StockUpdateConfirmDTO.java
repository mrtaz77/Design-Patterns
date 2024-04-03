package DataTransferObjects;

import java.io.Serializable;

import util.DoubleToString;

public class StockUpdateConfirmDTO implements Serializable,DoubleToString {
	private String stockName;
	private UpdateType updateType;
	private double prevAmount;
	private double newAmount;

	public StockUpdateConfirmDTO(String stockName, UpdateType updateType, double prevAmount, double newAmount) {
        this.stockName = stockName;
        this.updateType = updateType;
        this.prevAmount = prevAmount;
        this.newAmount = newAmount;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(UpdateType updateType) {
        this.updateType = updateType;
    }

    public double getPrevAmount() {
        return prevAmount;
    }

    public void setPrevAmount(double prevAmount) {
        this.prevAmount = prevAmount;
    }

    public double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(double newAmount) {
        this.newAmount = newAmount;
    }

	@Override
	public String toString() {
		var out = new StringBuilder();
		switch(updateType){
			case IncrementPrice :
			case DecrementPrice :
				out.append("Price of ");
				break;
			case ChangeQuantity:
				out.append("Quantity of ");
			default:
				break;
		}
		out.append(stockName)
		.append(" changed from ")
		.append(doubleToString(prevAmount))
		.append(" to ")
		.append(doubleToString(newAmount));
		return out.toString();
	}
}
