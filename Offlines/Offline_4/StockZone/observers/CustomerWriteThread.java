package observers;

import java.io.IOException;
import java.util.Scanner;

import DataTransferObjects.LogoutDTO;
import DataTransferObjects.SubscriptionDTO;
import DataTransferObjects.ViewDTO;
import util.SocketWrapper;

public class CustomerWriteThread implements Runnable {
	private Thread thread;
	private SocketWrapper socketWrapper;
	private Customer customer;

	public CustomerWriteThread(Customer customer, SocketWrapper socketWrapper) {
		this.customer = customer;
		this.socketWrapper = socketWrapper;
		this.thread = new Thread(this,"CustomerWriteThread");
		thread.start();
	}

	@Override
	public void run() {
		try(Scanner scanner = new Scanner(System.in)) {
			System.out.println("********* Stock Menu *************");
			while (true) {
				while(!scanner.hasNextLine());
				String command = ((String)scanner.nextLine());
				if (command.equalsIgnoreCase("LOGOUT")) {
					socketWrapper.write(new LogoutDTO(customer.getName()));
					customer.setLoggedIn(false);
				}else if(command.length() > 0) {
					String[] tokens = command.split(" ");
					if (tokens.length == 2) {
						String action = tokens[0];
						String stockName = tokens[1];
						switch (action) {
							case "S":
								socketWrapper.write(new SubscriptionDTO(customer.getName(),stockName,true));
								break;
							case "U":
								socketWrapper.write(new SubscriptionDTO(customer.getName(),stockName,false));
								break;
							default:
								System.out.println("Invalid action. Usage: S <stock> , U <stock> <value>");
						}
					}else if(tokens[0] == "V") {
						socketWrapper.write(new ViewDTO(customer.getName()));
					} 
					else {
						System.out.println("Usage error. Wrong number of arguments. Usage: S <stock> , U <stock> <value>, V");
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Exception in customer write thread");
			e.printStackTrace();
		} finally {
			try {
				socketWrapper.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
