package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import stock.Stock;

public class FileWriteThread implements Runnable {
	private static final String STOCKS_FILE = "stocks.txt";
	private static final String STOCK_SUBSCRIBERS_FILE = "stock_subscriber.txt";
	private static final String SUBSCRIBER_NOTFICATIONS_FILE = "subscriber_notification.txt";


	private int stockUpdateCount = 0;
	private int subscriberUpdateCount = 0;
	private int notificationUpdateCount = 0;

	private ConcurrentHashMap<String, Stock> stockTable;
	private ConcurrentHashMap<String, Vector<String>> stockSubscriberTable;
	private ConcurrentHashMap<String, Vector<String>> subscriberNotificationTable;

	private Server server;
	private Thread thread;

	public FileWriteThread(Server server){
		this.server = server;
		stockTable = server.getStockTable();
		stockSubscriberTable = server.getStockSubscriberTable();
		subscriberNotificationTable = server.getSubscriberNotificationTable();
		thread = new Thread(this,"FileWriteThread");
		thread.start();
	}

	@Override
	public void run() {
		while(true){
			checkStockUpdates();
			checkSubscriptionUpdates();
			checkNotifications();
		}
	}

	public void checkStockUpdates() { 
		if(stockUpdateCount < server.getStockTableUpdateCount()){
			writeStocksToFile();
			System.out.println("Stock table updated; Number of changes: " + String.valueOf(server.getStockTableUpdateCount() - stockUpdateCount));
			stockUpdateCount = server.getStockTableUpdateCount();
		}
	}

	public void checkSubscriptionUpdates() {
		if(subscriberUpdateCount < server.getSubscriberUpdateCount()){
			writeSubscriptionsToFile();
			System.out.println("Subscription table updated; Number of changes: " + String.valueOf(server.getSubscriberUpdateCount() - subscriberUpdateCount));
			subscriberUpdateCount = server.getSubscriberUpdateCount();
		}
	}

	public void checkNotifications() {
		if(notificationUpdateCount < server.getNotificationUpdateCount()){
			writeNotificationsToFile();
			System.out.println("Notification table updated; Number of changes: " + String.valueOf(server.getNotificationUpdateCount() - notificationUpdateCount));
			notificationUpdateCount = server.getNotificationUpdateCount();
		}
	}

	public void writeNotificationsToFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(SUBSCRIBER_NOTFICATIONS_FILE))) {
			for (Map.Entry<String, Vector<String>> entry : subscriberNotificationTable.entrySet()) {
				var subscriber = entry.getKey();
				var notifications = entry.getValue();	
				for(String notification : notifications) {
					writer.write(subscriber + " " + notification + "\n");
				}
				System.out.println(subscriber + " has " + notifications.size() + " notifications");
			}
		} catch (IOException e) {
			System.out.println("Exception while writing to file " + SUBSCRIBER_NOTFICATIONS_FILE);
			e.printStackTrace();
		}
	}

	public void writeSubscriptionsToFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(STOCK_SUBSCRIBERS_FILE))) {
			for (Map.Entry<String, Vector<String>> entry : stockSubscriberTable.entrySet()) {
				var stockName = entry.getKey();
				var subscribers = entry.getValue();	
				for(String subscriber : subscribers) {
					writer.write(subscriber + " " + stockName + "\n");
				}
				System.out.println(subscribers.size() + " subscriptions of stock " + stockName + " written to " + STOCK_SUBSCRIBERS_FILE);
			}
		} catch (IOException e) {
			System.out.println("Exception while writing to file " + STOCK_SUBSCRIBERS_FILE);
			e.printStackTrace();
		}	
	}

	public void writeStocksToFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(STOCKS_FILE))) {
			for (Map.Entry<String, Stock> entry : stockTable.entrySet()) {
				Stock stock = entry.getValue();
				String stockInfo = stock.toString();	
				writer.write(stockInfo);
				writer.newLine();
			}
			System.out.println(stockTable.size() + " stocks written to " + STOCKS_FILE);
		} catch (IOException e) {
			System.out.println("Exception while writing to file " + STOCKS_FILE);
			e.printStackTrace();
		}
	}

}
