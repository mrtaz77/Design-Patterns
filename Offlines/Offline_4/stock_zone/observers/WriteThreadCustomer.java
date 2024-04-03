package observers;

import util.SocketWrapper;

import java.io.IOException;
import java.util.Scanner;

import DataTransferObjects.SubscriptionDTO;
import DataTransferObjects.ViewDTO;

public class WriteThreadCustomer implements Runnable {

	private Thread thread;
	private SocketWrapper socketWrapper;
	private Customer customer;

	public WriteThreadCustomer(Customer customer, SocketWrapper socketWrapper) {
		this.customer = customer;
		this.socketWrapper = socketWrapper;
		this.thread = new Thread(this, "WriteCustomerThread");
		thread.start();
	}

	public void run() {
		try {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.println("********* Stock Menu *************");
			while (true) {
				System.out.print("> ");
				var command = scanner.nextLine();
				processCommand(command);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				socketWrapper.closeConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void processCommand(String command) throws IOException {
		String[] tokens = command.split(" ");
		if (tokens.length == 2) {
			String action = tokens[0];
			String stockName = tokens[1];
			switch (action) {
				case "S" -> socketWrapper.write(new SubscriptionDTO(customer.getName(), stockName, true));
				case "U" -> socketWrapper.write(new SubscriptionDTO(customer.getName(), stockName, false));
				default -> System.out.println("Invalid action. Usage: S <stock> , U <stock>");
			}
		} else if (tokens[0] == "V") socketWrapper.write(new ViewDTO(customer.getName()));
		else System.out.println("Usage error. Wrong number of arguments. Usage: S <stock> , U <stock>, V");
	}
}
