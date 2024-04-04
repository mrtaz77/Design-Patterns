package observers;

import util.SocketWrapper;

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
				if(o instanceof String) {
					var str = ((String)o);
					if(str.equals("Logout successfull"))break;
				}
				System.out.print("\n> ");
            }
        } catch (Exception e) {
            System.out.println("Exception in admin read thread");
            e.printStackTrace();
        }
    }

	private void processObject(Object obj) {
		if(obj instanceof LoginDTO) processLoginDTO(obj);
		else if(obj instanceof SubscriptionDTO) processSubscriptionDTO(obj);
		else if(obj instanceof StockUpdateConfirmDTO) processConfirmationDTO(obj);
		else if(obj instanceof LogoutDTO) processLogoutDTO(obj);
		else System.out.print(obj);
	}

	private void processLoginDTO(Object obj) {
		var loginDTO = (LoginDTO) obj;
		var userName = loginDTO.getName();
		System.out.print("\n: " + userName + " just logged in");
	}

	private void processLogoutDTO(Object obj) {
		var logoutDTO = (LogoutDTO)obj;
		var userName = logoutDTO.getName();
		System.out.print("\n: " + userName + " just logged out");
	}

	private void processConfirmationDTO(Object obj) {
		var confirmationDTO = (StockUpdateConfirmDTO) obj;
		System.out.print("\n: " + confirmationDTO + " successfully");
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
		System.out.print("\n: " + out);
	}
}



