package observers;

import util.SocketWrapper;

import java.io.IOException;
import java.util.Scanner;

import DataTransferObjects.LoginDTO;
import DataTransferObjects.LogoutDTO;
import DataTransferObjects.SubscriptionDTO;
import DataTransferObjects.ViewDTO;

public class WriteThreadCustomer implements Runnable {

	private Thread thread;
	private SocketWrapper socketWrapper;
	private Customer customer;
	private boolean confirmLogout = false;

	public WriteThreadCustomer(Customer customer, SocketWrapper socketWrapper) {
		this.customer = customer;
		this.socketWrapper = socketWrapper;
		this.thread = new Thread(this, "WriteCustomerThread");
		thread.start();
	}

	public void run() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.print("********* Stock Menu *************\n> ");
			while (!confirmLogout) {
				var command = scanner.nextLine();
				if(customer.isLoggedIn)processStockMenuCommands(command);
				else processLoginCommands(command);
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println("Found error in " + thread.getName());
			e.printStackTrace();
		}
	}

	private void processStockMenuCommands(String command) throws IOException {
		String[] tokens = command.split(" ");
		if (tokens.length == 2) {
			String action = tokens[0];
			String stockName = tokens[1];
			switch (action) {
				case "S" -> socketWrapper.write(new SubscriptionDTO(customer.getName(), stockName, true));
				case "U" -> socketWrapper.write(new SubscriptionDTO(customer.getName(), stockName, false));
				default -> System.out.println("Invalid action. Usage: S <stock> , U <stock>");
			}
		} else if (command.equals("V")) socketWrapper.write(new ViewDTO(customer.getName()));
		else if (command.equalsIgnoreCase("LOGOUT")) {
			socketWrapper.write(new LogoutDTO(customer.getName()));
			customer.isLoggedIn = false;
			confirmLogout = true;
		}
		else System.out.println("Usage error. Wrong number of arguments. Usage: S <stock> , U <stock>, V");
	}

	private void processLoginCommands(String command) throws IOException{
		var tokens = command.split(" ");
		var isLoggedIn = tokens.length == 2 && tokens[0].equalsIgnoreCase("login");
		if (isLoggedIn) {
			customer.name = tokens[1];
			customer.type = UserType.CUSTOMER;
			customer.isLoggedIn = true;
			socketWrapper.write(customer.name);
			var loginDTO = new LoginDTO(customer.name,true);
			socketWrapper.write(loginDTO);
		}
		else {
			System.out.println("Please login first");
		}
	}
}
