package DataTransferObjects;

import java.io.Serializable;

public class SubscriptionDTO implements Serializable {
	private boolean isSubscribed = false;
	private String userName;
	private String stockName;

	public SubscriptionDTO(String userName, String stockName, boolean isSubscribed) {
		this.userName = userName;
		this.stockName = stockName;
		this.isSubscribed = isSubscribed;
	}

	public String getUserName() { return userName; }
	public void setUserName(String userName) { this.userName = userName; }

	public String getStockName() { return stockName; }
	public void setStockName(String stockName) { this.stockName = stockName; }

	public boolean isSubscribed() { return isSubscribed; }
	public void setSubscribed(boolean isSubscribed) { this.isSubscribed = isSubscribed; }
}
