package server;

import util.SocketWrapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import stock.Stock;

public class Server {

	private static final String INPUT_FILE_NAME = "init_stocks.txt";

    private ConcurrentHashMap<String, Stock> stockTable = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Vector<String>> stockSubscriberTable = new ConcurrentHashMap<>();

	private ServerSocket serverSocket;
    private ConcurrentHashMap<String, SocketWrapper> network = new ConcurrentHashMap<>();

    private volatile int userCount = 0;
	private volatile int updateCount = 0;

	public synchronized void setUserCount(int userCount) {this.userCount = userCount;}
    public synchronized void setUpdateCount(int updateCount) {this.updateCount = updateCount;}

    Server() {
		Thread stockThread = new Thread(this::readStocksFromFile);
        stockThread.start();
		new FileWriteThread(this);
        try {
			System.out.println("Server started...");
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Found " + e.getMessage() + " while running server...");
			e.printStackTrace();
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        SocketWrapper SocketWrapper = new SocketWrapper(clientSocket);
        String clientName = (String) SocketWrapper.read();
        network.put(clientName, SocketWrapper);
        new ReadThreadServer(this, SocketWrapper);
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

    public static void main(String args[]) {
        System.out.println("Welcome to StockZone!!!");
        new Server();
    }
}
