package server;

import util.NetworkingConstants;
import util.SocketWrapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import stock.Stock;

public class Server implements NetworkingConstants {

    private ConcurrentHashMap<String, Stock> stockTable = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Vector<String>> stockSubscriberTable = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Vector<String>> subscriberNotificationTable = new ConcurrentHashMap<>();

	private ServerSocket serverSocket;
    private ConcurrentHashMap<String, SocketWrapper> network = new ConcurrentHashMap<>();

    private volatile int userCount = 0;
	private volatile int stockTableUpdateCount = 0;
	private volatile int subscriberUpdateCount = 0;
	private volatile int notificationUpdateCount = 0;

	public synchronized void setUserCount(int userCount) {this.userCount = userCount;}
    public synchronized void setStockTableUpdateCount(int stockTableUpdateCount) {this.stockTableUpdateCount = stockTableUpdateCount;}
	public synchronized void setSubscriberUpdateCount(int subscriberUpdateCount) {this.subscriberUpdateCount = subscriberUpdateCount;}
	public synchronized void setNotificationUpdateCount(int notificationUpdateCount) {this.notificationUpdateCount = notificationUpdateCount;}

    Server() {
		DatabaseInit.getInstance(this);
		new FileWriteThread(this);
        try {
			System.out.println("Server started...");
            serverSocket = new ServerSocket(serverPort);
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

	public ConcurrentHashMap<String, Vector<String>> getSubscriberNotificationTable() {
		return subscriberNotificationTable;
	}
    
	public int getUserCount() {
        return userCount;
    }

    public int getStockTableUpdateCount() { return stockTableUpdateCount; }

	public int getNotificationUpdateCount() { return notificationUpdateCount; }

	public int getSubscriberUpdateCount() { return subscriberUpdateCount; }

	public ServerSocket getServerSocket() {
		return serverSocket;
	}
    public static void main(String args[]) {
        System.out.println("Welcome to StockZone!!!");
        new Server();
    }
}
