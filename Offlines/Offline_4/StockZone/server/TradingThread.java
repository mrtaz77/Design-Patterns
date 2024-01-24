package server;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import DataTransferObjects.LoginDTO;
import DataTransferObjects.LogoutDTO;
import DataTransferObjects.StockDTO;
import stock.Stock;
import util.SocketWrapper;

public class TradingThread implements Runnable {
	private SocketWrapper socketWrapper;
	private Socket userSocket;
	private Server server;
	private Thread thread;
	private ConcurrentHashMap<String, SocketWrapper> network;
	
	private ConcurrentHashMap<String, Stock> stockTable;
	private ConcurrentHashMap<String, String> passwordTable;

	public TradingThread(Server server,SocketWrapper socketWrapper,Socket usersSocket) {
		this.server = server;
		this.socketWrapper = socketWrapper;
		this.userSocket = usersSocket;
		stockTable = server.getStockTable();
		passwordTable = server.getPasswordTable();
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
				}else if(object instanceof StockDTO){
					processStockDTO((StockDTO)object);
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

	private void processStockDTO(StockDTO object) {
		
	}

	public void processLoginDTO(LoginDTO loginDTO){
		boolean isValidLogin = checkCredentials(loginDTO);
		loginDTO.setStatus(isValidLogin);

		if(isValidLogin){
			
		}
	}

	public boolean checkCredentials(LoginDTO loginDTO){
		if(passwordTable.containsKey(loginDTO.getName())){
			return loginDTO.getPassword().equals(passwordTable.get(loginDTO.getName()));
		}
		return false;
	}
}
