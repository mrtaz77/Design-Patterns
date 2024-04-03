package server;

import util.SocketWrapper;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import DataTransferObjects.LoginDTO;
import DataTransferObjects.ViewDTO;
import stock.Stock;

public class ReadThreadServer implements Runnable {
    private Thread thread;
    private SocketWrapper socketWrapper;
    private Server server;
	private ConcurrentHashMap<String, SocketWrapper> network;
	
	private ConcurrentHashMap<String, Stock> stockTable;
	private ConcurrentHashMap<String, Vector<String>> stockSubscriberTable;

    public ReadThreadServer(Server server, SocketWrapper SocketWrapper) {
        this.server = server;
        this.socketWrapper = SocketWrapper;
		stockTable = server.getStockTable();
		stockSubscriberTable = server.getStockSubscriberTable();
		network = server.getNetwork();
        this.thread = new Thread(this,"Trading Thread");
        thread.start();
    }

	@Override
    public void run() {
        try {
            while (true) {
                Object object = socketWrapper.read();
                if(object instanceof LoginDTO){
					processLoginDTO((LoginDTO)object);
				}
				else System.out.println(object);
            }
        } catch (Exception e) {
            System.out.println("Found "+ e.getMessage() + " in read thread of server" );
			e.printStackTrace();
		} finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	private void processViewDTO(ViewDTO view) throws IOException {
		var userName = view.getName();
		System.out.println(userName);
		if(userName.startsWith("admin")){
			for (Map.Entry<String, Stock> entry : stockTable.entrySet()) {
				System.out.println(entry.getValue());
				view.addStock(entry.getValue());
			}
		}else {
			for (Map.Entry<String, Vector<String>> subscriberEntry : stockSubscriberTable.entrySet()) {
				String stockName = subscriberEntry.getKey();
				Vector<String> subscribers = subscriberEntry.getValue();
	
				if (subscribers.contains(userName)) {
					Stock stock = stockTable.get(stockName);
					if (stock != null) {
						view.addStock(stock);
					}
				}
			}
		}
		network.get(userName).write(view);
	}

	public void processLoginDTO(LoginDTO loginDTO) throws IOException{
		System.out.println("Got a login");
		server.setUserCount(server.getUserCount() + 1);
		network.put(loginDTO.getName(), socketWrapper);
		var view = new ViewDTO(loginDTO.getName());
		processViewDTO(view);
		System.out.println("Sending all stock infos");
		notifyAdmins(loginDTO);
	}

	private void notifyAdmins(Object obj) throws IOException{
		for (Map.Entry<String, SocketWrapper> entry : network.entrySet()) {
            if (entry.getKey().startsWith("admin")) {
                entry.getValue().write(obj);
            }
        }
	}
}



