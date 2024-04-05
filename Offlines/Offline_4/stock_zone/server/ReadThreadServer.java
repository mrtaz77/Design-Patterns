package server;

import util.InputValidator;
import util.SocketWrapper;
import util.StockTableDisplay;

import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import DataTransferObjects.*;
import stock.Stock;

public class ReadThreadServer implements Runnable, InputValidator, StockTableDisplay {
    private Thread thread;
    private SocketWrapper socketWrapper;
    private Server server;
	private boolean isConnected = true;
	private ConcurrentHashMap<String, SocketWrapper> network;
	
	private ConcurrentHashMap<String, Stock> stockTable;
	private ConcurrentHashMap<String, Vector<String>> stockSubscriberTable;
	private ConcurrentHashMap<String, Vector<String>> subscriberNotificationTable = new ConcurrentHashMap<>();

    public ReadThreadServer(Server server, SocketWrapper SocketWrapper) {
        this.server = server;
        this.socketWrapper = SocketWrapper;
		stockTable = server.getStockTable();
		stockSubscriberTable = server.getStockSubscriberTable();
		subscriberNotificationTable = server.getSubscriberNotificationTable();
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
					displayStockInfo(((ViewDTO)object).getName());
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

	private void displayStockInfo(String userName) throws IOException {
		var stocks = new Vector<Stock>();
		if(userName.startsWith("admin")){
			for (Map.Entry<String, Stock> entry : stockTable.entrySet()) {
				stocks.add(entry.getValue());
			}
		}else {
			for (Map.Entry<String, Vector<String>> subscriberEntry : stockSubscriberTable.entrySet()) {
				String stockName = subscriberEntry.getKey();
				Vector<String> subscribers = subscriberEntry.getValue();
	
				if (subscribers.contains(userName)) {
					Stock stock = stockTable.get(stockName);
					if (stock != null) {
						stocks.add(stock);
					}
				}
			}
		}
		network.get(userName).write(toString(stocks));
	}

	private void processSubscriptionDTO(SubscriptionDTO subscription) throws IOException{
		var userName = subscription.getUserName();
		var stockName = subscription.getStockName();
		var status = subscription.isSubscribed();
		var message = new StringBuilder(userName);

		if(stockPresent(stockName, stockTable)){
			try{
				if(status){
					stockSubscriberTable.get(stockName).add(userName);
					network.get(userName).write("Congratulations ! You are now subscribed to "+stockName);
					message.append(" subscribed to ");
				}else {
					stockSubscriberTable.get(stockName).remove(userName);
					network.get(userName).write("You have been unsubscribed from "+stockName);
					message.append(" unsubscribed from ");
				}
				message.append(stockName);
				if(!notifyAdmins(subscription))addNotification("admin1",message.toString());
				server.setSubscriberUpdateCount(server.getSubscriberUpdateCount() + 1);
			}catch(Exception e){
				socketWrapper.write(e.getMessage());
			}
		}else socketWrapper.write("No stock with name " + stockName + " in the database");
	}


	public void processLoginDTO(LoginDTO loginDTO) throws IOException{
		System.out.println(loginDTO.getName() + " just logged in...");
		server.setUserCount(server.getUserCount() + 1);
		network.put(loginDTO.getName(), socketWrapper);
		displayStockInfo(loginDTO.getName());
		System.out.println("Sending all stock infos to " + loginDTO.getName());
		if(!notifyAdmins(loginDTO))addNotification("admin1", "\n: " + loginDTO.getName() + " just logged in");
	}

	private boolean notifyAdmins(Object obj) throws IOException{
		var adminOnline = false;
		for (Map.Entry<String, SocketWrapper> entry : network.entrySet()) {
            if (entry.getKey().startsWith("admin")) {
				adminOnline = true;
                entry.getValue().write(obj);
            }
        }
		return adminOnline;
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
						dto = changeCount(Math.round(amount), stockName);
						success = true;
						break;
					default:
						if(!notifyAdmins("Invalid change")) addNotification("admin1", "Invalid change" );
						socketWrapper.write("Invalid change");
						break;
				}
				if(success){
					if(!notifyAdmins(dto)) addNotification("admin1", dto.toString());
					notifySubscribers(dto);
					server.setStockTableUpdateCount(server.getStockTableUpdateCount() + 1);
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
		System.out.println(stockTable);
		return new StockUpdateConfirmDTO(stockName, UpdateType.IncrementPrice, prevPrice, newPrice);
	}

	private StockUpdateConfirmDTO decrementPrice(Double amount, String stockName) {
		var prevPrice = stockTable.get(stockName).getPrice();
		var newPrice = prevPrice - amount;
		stockTable.get(stockName).setPrice(newPrice);
		return new StockUpdateConfirmDTO(stockName, UpdateType.DecrementPrice, prevPrice, newPrice);
	}

	private StockUpdateConfirmDTO changeCount(long newQuantity, String stockName) {
		var prevQuantity = stockTable.get(stockName).getQuantity();
		stockTable.get(stockName).setQuantity(newQuantity);
		return new StockUpdateConfirmDTO(stockName, UpdateType.ChangeQuantity, prevQuantity, newQuantity);
	}

	private void processLogoutDTO(LogoutDTO logoutDTO) throws IOException {
		server.setUserCount(server.getUserCount() - 1);
		var socketWrapper = network.remove(logoutDTO.getName());
		if(!notifyAdmins(logoutDTO))addNotification("admin1", "\n: " + logoutDTO.getName() + " just logged out");
		socketWrapper.write("Logout successfull");
		isConnected = false;
	}
	
	private void addNotification(String name, String notification) {
		subscriberNotificationTable.computeIfAbsent(name, k -> new Vector<>());
		subscriberNotificationTable.get(name).add(notification);
	}
}