package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import stock.Stock;
import util.SocketWrapper;

public class Server {

    private static final String INPUT_FILE_NAME = "init_stocks.txt";

	private ConcurrentHashMap<String, Stock> stockTable = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Vector<String>> stockSubscriberTable = new ConcurrentHashMap<>();

	private ServerSocket serverSocket;
    private ConcurrentHashMap<String, SocketWrapper> network = new ConcurrentHashMap<>();

    private volatile int userCount = 0;
	private volatile int updateCount = 0;

    Server() {
		Thread stockThread = new Thread(this::readStocksFromFile);
		
        stockThread.start();


    }

	public void initSocket() {
		try {
			serverSocket = new ServerSocket(3000);
			System.out.println("Server started...");
			while(true){
				var userSocket = serverSocket.accept();


			}


		}catch(IOException e) {
			
		}
	}

	public ConcurrentHashMap<String, Stock> getStockTable() {
        return stockTable;
    }

	public ConcurrentHashMap<String, Vector<String>> getStockSubscriberTable() {
		return stockSubscriberTable;
	}

    public ConcurrentHashMap<String, SocketWrapper> getNetwork() {
        return network;
    }

    public int getUserCount() {
        return userCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

    public void readStocksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                if (tokens.length >= 3) {
                    String stockName = tokens[0];
                    int quantity = Integer.parseInt(tokens[1]);
                    double price = Double.parseDouble(tokens[2]);

                    Stock stock = new Stock(stockName, quantity, price);

                    stockTable.put(stockName, stock);

                    stockSubscriberTable.computeIfAbsent(stockName, k -> new Vector<>());

                    for (int i = 3; i < tokens.length; i++) {
                        stockSubscriberTable.get(stockName).add(tokens[i]);
                    }
                } else {
                    System.out.println("Invalid line in the input file: " + line);
                }
            }
            System.out.println(stockTable.size() + " stocks available.");
        } catch (IOException e) {
            System.out.println("Exception while reading stock file " + INPUT_FILE_NAME);
            e.printStackTrace();
        }
    }

	public synchronized void setUserCount(int userCount) {this.userCount = userCount;}
    public synchronized void setUpdateCount() {this.updateCount = updateCount;}

    public static void main(String[] args) {
        System.out.println("Welcome to StockZone!!!");
        new Server();
    }
}