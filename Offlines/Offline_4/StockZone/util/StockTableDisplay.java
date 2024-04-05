package util;

import java.util.Vector;

import stock.Stock;

public interface StockTableDisplay {
	default public String vectorOfStocksToString(Vector<Stock> stocks){
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
