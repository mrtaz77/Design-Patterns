package DataTransferObjects;

import java.io.Serializable;
import java.util.Vector;

import stock.Stock;
import util.DoubleToString;

public class ViewDTO implements Serializable,DoubleToString {
	private Vector<Stock> stocks = new Vector<Stock>();
	private String name;

	public ViewDTO(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setStocks(Vector<Stock> stocks) {
		this.stocks = stocks;
	}

	public boolean addStock(Stock stock) {
		return stocks.add(stock);
	}

	@Override
	public String toString() {
		var out = new StringBuilder();
		out.append("____________________________________________________________________\n");
        out.append("|     Stock      |        Price         |         Quantity         |\n");
        out.append("|________________|______________________|__________________________|\n");

        for (Stock stock : stocks) {
            out.append(String.format("|  %-12s  |   %-18.2f |   %-23d|\n", stock.getName(), stock.getPrice(), stock.getQuantity()));
        }

        out.append("|________________|______________________|__________________________|\n");

		return out.toString();
	}
}
