package observers;

import java.io.IOException;

import DataTransferObjects.*;
import util.SocketWrapper;

public class AdminReadThread implements Runnable {
	private SocketWrapper socketWrapper;
	private Thread thread;
	private volatile boolean isActive;

	public AdminReadThread(SocketWrapper socketWrapper) {
		this.socketWrapper = socketWrapper;
		this.thread = new Thread(this,"AdminReadThread");
		isActive = true;
		thread.start();
	}

	@Override
	public void run() {
		try {
			while(isActive) {
				Object obj = socketWrapper.read();
				processObject(obj);
			}
		}catch (Exception e) {
            System.out.println("Exception in admin read thread");
            e.printStackTrace();
        } finally {
            try {
                socketWrapper.close();
            } catch (IOException e) {
				System.out.println("Exception while closing admin connection");
                e.printStackTrace();
            }
        }
	}

	private void processObject(Object obj) {
		if(obj instanceof LoginDTO) processLoginDTO(obj);
		else if(obj instanceof SubscriptionDTO) processSubscriptionDTO(obj);
		else if(obj instanceof StockUpdateConfirmDTO) processConfirmationDTO(obj);
		else if(obj instanceof LogoutDTO) processLogoutDTO(obj);
		else if(obj instanceof InterruptDTO) processInterruptDTO();
		else System.out.println(obj);
	}

	private void processInterruptDTO() {
		try {
			socketWrapper.close();
			isActive = false;
		} catch (IOException e) {
			System.out.println("Exception while closing socket");
			e.printStackTrace();
		}	
	}

	private void processLogoutDTO(Object obj) {
		var logoutDTO = (LogoutDTO)obj;
		var userName = logoutDTO.getName();
		System.out.println(userName + " just logged out");
	}

	private void processConfirmationDTO(Object obj) {
		var confirmationDTO = (StockUpdateConfirmDTO) obj;
		System.out.println(confirmationDTO + " successfully");
	}

	private void processSubscriptionDTO(Object obj) {
		var subscriptTionDTO = (SubscriptionDTO) obj;
		var userName = subscriptTionDTO.getUserName();
		var isSubscribed = subscriptTionDTO.isSubscribed();
		var stockName = subscriptTionDTO.getStockName();
		var out = new StringBuilder(userName); 
		if(isSubscribed)out.append(" subscribed to ");
		else out.append(" unsubscribed from ");
		out.append(stockName);
		System.out.println(out);
	}

	private void processLoginDTO(Object obj) {
		var loginDTO = (LoginDTO) obj;
		var userName = loginDTO.getName();
		System.out.println(userName + " just logged in");
	}
}
