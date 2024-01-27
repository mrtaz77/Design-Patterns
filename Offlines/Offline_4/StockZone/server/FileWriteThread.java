package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import stock.Stock;

public class FileWriteThread implements Runnable {
	private static final String OUTPUT_FILE_NAME = "init_stocks.txt";

	private int updateCount;

	private ConcurrentHashMap<String, Stock> stockTable;
	private ConcurrentHashMap<String, Vector<String>> stockSubscriberTable = new ConcurrentHashMap<>();

	private Server server;
	private Thread thread;

	public FileWriteThread(Server server){
		this.server = server;
		stockTable = server.getStockTable();
		stockSubscriberTable = server.getStockSubscriberTable();
		thread = new Thread(this,"FileWriteThread");
		thread.start();
	}

	@Override
	public void run() {
		while(true){
			if(updateCount < server.getUpdateCount()){
				Thread stockThread = new Thread(this::writeStocksToFile);
				stockThread.start();
				System.out.println("Stock table updated; Number of changes: " + String.valueOf(server.getUpdateCount() - updateCount));
			}
		}
	}

	public void writeStocksToFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME))) {
			for (Map.Entry<String, Stock> entry : stockTable.entrySet()) {
				Stock stock = entry.getValue();
				String stockInfo = stock.toString();
	
				Vector<String> subscribers = stockSubscriberTable.get(stock.getName());
				if (!(subscribers == null || subscribers.isEmpty())) {
					stockInfo += " " + String.join(" ", subscribers);
				}
	
				writer.write(stockInfo);
				writer.newLine();
			}
			System.out.println(stockTable.size() + " stocks written to " + OUTPUT_FILE_NAME);
		} catch (IOException e) {
			System.out.println("Exception while writing to file " + OUTPUT_FILE_NAME);
			e.printStackTrace();
		}
	}

}
