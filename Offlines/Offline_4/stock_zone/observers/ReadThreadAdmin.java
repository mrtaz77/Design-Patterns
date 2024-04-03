package observers;

import util.SocketWrapper;

import java.io.IOException;

import DataTransferObjects.LoginDTO;
import DataTransferObjects.LogoutDTO;
import DataTransferObjects.StockUpdateConfirmDTO;
import DataTransferObjects.SubscriptionDTO;

public class ReadThreadAdmin implements Runnable {
    private Thread thread;
    private SocketWrapper SocketWrapper;

    public ReadThreadAdmin(SocketWrapper SocketWrapper) {
        this.SocketWrapper = SocketWrapper;
        this.thread = new Thread(this,"ReadThreadAdmin");
        thread.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = SocketWrapper.read();
                processObject(o);
            }
        } catch (Exception e) {
            System.out.println("Exception in admin read thread");
            e.printStackTrace();
        } finally {
            try {
                SocketWrapper.closeConnection();
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
		else System.out.println(obj);
	}

	private void processLoginDTO(Object obj) {
		var loginDTO = (LoginDTO) obj;
		var userName = loginDTO.getName();
		System.out.println("\n: " + userName + " just logged in\n> ");
	}

	private void processLogoutDTO(Object obj) {
		var logoutDTO = (LogoutDTO)obj;
		var userName = logoutDTO.getName();
		System.out.println("\n: " + userName + " just logged out\n> ");
	}

	private void processConfirmationDTO(Object obj) {
		var confirmationDTO = (StockUpdateConfirmDTO) obj;
		System.out.println("\n: " + confirmationDTO + " successfully\n> ");
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
		System.out.println("\n: " + out + "\n> ");
	}
}



