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
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("********* Stock Menu *************");
			while (customer.isLoggedIn()) {
				if (scanner.hasNextLine()) {
					String command = scanner.nextLine();
					if (command.equalsIgnoreCase("LOGOUT")) {
						socketWrapper.write(new LogoutDTO(customer.getName()));
						customer.setLoggedIn(false);
					} else {
						processCommand(command);
					}
				}
			}
		}catch (IOException e) {
			System.out.println("Exception in customer write thread");
			e.printStackTrace();
		}finally {
			scanner.close();
		}
	}

	private void processCommand(String command)throws IOException {
		String[] tokens = command.split("\\s+");

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
		}else if(tokens[1] == "V") {
			socketWrapper.write(new ViewDTO(customer.getName()));
		} 
		else {
			System.out.println("Usage error. Wrong number of arguments. Usage: S <stock> , U <stock> <value>");
		}
	}

}
