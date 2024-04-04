package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import stock.Stock;

public class DatabaseInit {

	private static final String STOCKS_FILE = "stocks.txt";
	private static final String STOCK_SUBSCRIBERS_FILE = "stock_subscriber.txt";
	private static final String SUBSCRIBER_NOTFICATIONS_FILE = "subscriber_notification.txt";

	private ConcurrentHashMap<String, Stock> stockTable;
	private ConcurrentHashMap<String, Vector<String>> stockSubscriberTable = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Vector<String>> subscriberNotificationTable = new ConcurrentHashMap<>();

	public DatabaseInit(Server server) {
		stockTable = server.getStockTable();
		stockSubscriberTable = server.getStockSubscriberTable();
		subscriberNotificationTable = server.getSubscriberNotificationTable();
		initTables();
		displayTables();
	}

	private void initTables() {
		readStocksFromFile();
		readNotificationsOfSubscribersFromFile();
		readSubscribersOfStocksFromFile();
	}

	private void displayTables() {
		System.out.println("# of Stocks : " + stockTable.size() + "\n");
		for (Map.Entry<String, Vector<String>> subscriberEntry : stockSubscriberTable.entrySet()) {
			System.out.println("# of subscribers for stock " + subscriberEntry.getKey() + " : " + String.valueOf(subscriberEntry.getValue().size()));
		}
		System.out.println();
		for (Map.Entry<String, Vector<String>> notificationEntry : subscriberNotificationTable.entrySet()) {
			System.out.println("# of subscribers for user " + notificationEntry.getKey() + " : " + String.valueOf(notificationEntry.getValue().size()));
		}
		System.out.println();
	}

	private void readStocksFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(STOCKS_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\s+");
				if (tokens.length == 3) {
					String stockName = tokens[0];
					int quantity = Integer.parseInt(tokens[1]);
					double price = Double.parseDouble(tokens[2]);
					Stock stock = new Stock(stockName, quantity, price);
					stockTable.put(stockName, stock);
				} else {
					System.out.println("Invalid line in the stocks file: " + line);
				}
			}
		} catch (IOException e) {
			System.out.println("Exception while reading stocks file " + STOCKS_FILE);
			e.printStackTrace();
		}
	}

	private void readSubscribersOfStocksFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(STOCK_SUBSCRIBERS_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.split("\\s+");
				if (tokens.length == 2) {
					String stockName = tokens[0];
					String subscriberName = tokens[1];
					stockSubscriberTable.computeIfAbsent(stockName, k -> new Vector<>());
					stockSubscriberTable.get(stockName).add(subscriberName);
				} else {
					System.out.println("Invalid line in the stock_subscriber file: " + line);
				}
			}
		} catch (IOException e) {
			System.out.println("Exception while reading stock_subscriber file " + STOCK_SUBSCRIBERS_FILE);
			e.printStackTrace();
		}
	}

	private void readNotificationsOfSubscribersFromFile() {
		try (BufferedReader reader = new BufferedReader(new FileReader(SUBSCRIBER_NOTFICATIONS_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(" ", 2);
				if (parts.length == 2) {
					String subscriberName = parts[0];
					String notification = parts[1];
					subscriberNotificationTable.computeIfAbsent(subscriberName, k -> new Vector<>()).add(notification);
				}
			}
		} catch (IOException e) {
			System.out.println("Exception while reading subscriber_notifications file " + SUBSCRIBER_NOTFICATIONS_FILE);
			e.printStackTrace();
		}
	}
}
