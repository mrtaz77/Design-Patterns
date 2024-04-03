package server;

import util.InputValidator;
import util.SocketWrapper;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import DataTransferObjects.LoginDTO;
import DataTransferObjects.LogoutDTO;
import DataTransferObjects.StockInitUpdateDTO;
import DataTransferObjects.StockUpdateConfirmDTO;
import DataTransferObjects.SubscriptionDTO;
import DataTransferObjects.UpdateType;
import DataTransferObjects.ViewDTO;
import stock.Stock;

public class ReadThreadServer implements Runnable, InputValidator {
    private Thread thread;
    private SocketWrapper socketWrapper;
    private Server server;
	private boolean isConnected = true;
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
            while (isConnected) {
                Object object = socketWrapper.read();
                if(object instanceof LoginDTO){
					processLoginDTO((LoginDTO)object);
				}else if(object instanceof StockInitUpdateDTO){
					processStockDTO((StockInitUpdateDTO)object);
				}else if(object instanceof ViewDTO){
					processViewDTO((ViewDTO)object);
				}else if(object instanceof LogoutDTO){
					processLogoutDTO((LogoutDTO)object);
				}else if(object instanceof SubscriptionDTO){
					processSubscriptionDTO((SubscriptionDTO)object);
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
		if(userName.startsWith("admin")){
			for (Map.Entry<String, Stock> entry : stockTable.entrySet()) {
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

	private void processSubscriptionDTO(SubscriptionDTO subscription) throws IOException{
		var userName = subscription.getUserName();
		var stockName = subscription.getStockName();
		var status = subscription.isSubscribed();

		if(stockPresent(stockName, stockTable)){
			try{
				if(status){
					stockSubscriberTable.get(stockName).add(userName);
					network.get(userName).write("Congratulations ! You are now subscribed to "+stockName);
				}else {
					stockSubscriberTable.get(stockName).remove(userName);
					network.get(userName).write("You have been unsubscribed from "+stockName);
				}
				notifyAdmins(subscription);
				server.setUpdateCount(server.getUpdateCount() + 1);
			}catch(Exception e){
				socketWrapper.write(e.getMessage());
			}
		}else socketWrapper.write("No stock with name " + stockName + " in the database");
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

	private void processStockDTO(StockInitUpdateDTO object) throws IOException {
		var type = object.getType();
		var stockName = object.getStockName();
		var amount = object.getAmount();

		if(stockPresent(stockName, stockTable)){
			try{
				var dto = new StockUpdateConfirmDTO(stockName,type,amount,amount);
				boolean success = false;
				switch (type) {
					case IncrementPrice:
						isNonNegative(amount, "increasing price of stock " + stockName);
						dto = incrementPrice(amount, stockName);
						success = true;
						break;
					case DecrementPrice:
						isDecrementGreaterThanStockPrice(amount,stockTable.get(stockName));
						dto = decrementPrice(amount, stockName);
						success = true;
						break;
					case ChangeQuantity:
						isNonNegative(amount, "changing quantity " + stockName);
						dto = changeCount(amount, stockName);
						success = true;
						break;
					default:
						notifyAdmins("Invalid change");
						socketWrapper.write("Invalid change");
						break;
				}
				if(success){
					notifyAdmins(dto);
					notifySubscribers(dto);
					server.setUpdateCount(server.getUpdateCount() + 1);
				}
			}catch(Exception e){
				socketWrapper.write(e.getMessage());
			}
		}else socketWrapper.write("No stock with name " + stockName + " in the database");
	}

	private void notifySubscribers(StockUpdateConfirmDTO dto) throws IOException {
		var subscribers = stockSubscriberTable.get(dto.getStockName());
        if (subscribers != null) {
            for (String subscriberKey : subscribers) {
                var socketWrapper = network.get(subscriberKey);
                if (socketWrapper != null) {
                    socketWrapper.write(dto);
                }
            }
        }
	}

	private StockUpdateConfirmDTO incrementPrice(Double amount, String stockName) {
		var prevPrice = stockTable.get(stockName).getPrice();
		var newPrice = prevPrice + amount;
		stockTable.get(stockName).setPrice(newPrice);
		return new StockUpdateConfirmDTO(stockName, UpdateType.IncrementPrice, prevPrice, newPrice);
	}

	private StockUpdateConfirmDTO decrementPrice(Double amount, String stockName) {
		var prevPrice = stockTable.get(stockName).getPrice();
		var newPrice = prevPrice - amount;
		stockTable.get(stockName).setPrice(newPrice);
		return new StockUpdateConfirmDTO(stockName, UpdateType.DecrementPrice, prevPrice, newPrice);
	}

	private StockUpdateConfirmDTO changeCount(Double amount, String stockName) {
		var prevQuantity = stockTable.get(stockName).getQuantity();
		var newQuantity = amount;
		stockTable.get(stockName).setPrice(newQuantity);
		return new StockUpdateConfirmDTO(stockName, UpdateType.ChangeQuantity, prevQuantity, newQuantity);
	}

	private void processLogoutDTO(LogoutDTO logoutDTO) throws IOException {
		server.setUserCount(server.getUserCount() - 1);
		var socketWrapper = network.remove(logoutDTO.getName());
		notifyAdmins(logoutDTO);
		socketWrapper.write("Logout successfull");
		isConnected = false;
	}
}



