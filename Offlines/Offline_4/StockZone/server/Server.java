package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;

import stock.Stock;
import util.SocketWrapper;

public class Server {

    private static final String INPUT_FILE_NAME = "init_stocks.txt";
	private static final String PASSWORD_FILE_NAME = "init_passwords.txt";

	private ConcurrentHashMap<String, Stock> stockTable = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, String> passwordTable = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, String> stockUserTable = new ConcurrentHashMap<>();

	protected ServerSocket serverSocket;
    private ConcurrentHashMap<String, SocketWrapper> network = new ConcurrentHashMap<>();

    private volatile int userCount = 0;
	private volatile int updateCount = 0;

    Server() {
		Thread stockThread = new Thread(this::readStocksFromFile);
		Thread passwordThread = new Thread(this::readPasswordsFromFile);
		
        stockThread.start();
		passwordThread.start();

		try {
			stockThread.join();
			passwordThread.join();
		}catch (InterruptedException e) {
			System.out.println("Exception while joining threads");
			e.printStackTrace();
		}


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

    public ConcurrentHashMap<String, String> getPasswordTable() {
        return passwordTable;
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

    public void readStocksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into tokens
                String[] tokens = line.split("\\s+");

                // Check if the line has the expected number of tokens
                if (tokens.length == 3) {
                    String stockName = tokens[0];
                    int quantity = Integer.parseInt(tokens[1]);
                    double price = Double.parseDouble(tokens[2]);

                    // Create Stock object
                    Stock stock = new Stock(stockName, quantity, price);

                    // Add Stock object to ConcurrentHashMap
                    stockTable.put(stockName, stock);
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

	public void readPasswordsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PASSWORD_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into tokens
                String[] tokens = line.split("\\s+");

                // Check if the line has the expected number of tokens
                if (tokens.length == 2) {
                    String userName = tokens[0];
                    String password = tokens[1];
					passwordTable.put(userName, password);
                } else {
                    System.out.println("Invalid line in the input file: " + line);
                }
            }
            System.out.println(passwordTable.entrySet().size() + " users are currently registered.");
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