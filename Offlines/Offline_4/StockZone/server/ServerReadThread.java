package server;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import DataTransferObjects.LoginDTO;
import DataTransferObjects.LogoutDTO;
import DataTransferObjects.StockInitUpdateDTO;
import stock.Stock;
import util.SocketWrapper;

public class ServerReadThread implements Runnable {
	private SocketWrapper socketWrapper;
	private Socket userSocket;
	private Server server;
	private Thread thread;
	private ConcurrentHashMap<String, SocketWrapper> network;
	
	private ConcurrentHashMap<String, Stock> stockTable;
	private ConcurrentHashMap<String, Vector<String>> stockSubscriberTable = new ConcurrentHashMap<>();

	public ServerReadThread(Server server,SocketWrapper socketWrapper,Socket usersSocket) {
		this.server = server;
		this.socketWrapper = socketWrapper;
		this.userSocket = usersSocket;
		stockTable = server.getStockTable();
		stockSubscriberTable = server.getStockSubscriberTable();
		network = server.getNetwork();
		thread = new Thread(this,"TradingThread");
		thread.start();
	}

	@Override
	public void run() {
		try{
			while(true){
				Object object = socketWrapper.read();
				if(object instanceof LoginDTO){
					processLoginDTO((LoginDTO)object);
				}else if(object instanceof StockInitUpdateDTO){
					processStockDTO((StockInitUpdateDTO)object);
				}else if(object instanceof LogoutDTO){
					processLogoutDTO((LogoutDTO)object);
				}
			}
		}catch(Exception e){
			System.out.println("Exception while trading stocks");
			e.printStackTrace();
		}finally{
			System.out.println("Terminating Stock Exchange");
			try {
				socketWrapper.close();
			} catch (IOException e) {
				System.out.println("Exception while closing socketWrapper in trading thread");
				e.printStackTrace();
			}
		}
	}

	private void processLogoutDTO(LogoutDTO object) {
		server.setUserCount(server.getUserCount() - 1);
		
	}

	private void processStockDTO(StockInitUpdateDTO object) {
		
	}

	public void processLoginDTO(LoginDTO loginDTO){
		
	}
}
