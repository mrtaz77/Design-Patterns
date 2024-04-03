package observers;

import java.io.IOException;
import java.util.Scanner;

import DataTransferObjects.*;
import util.SocketWrapper;

public class AdminWriteThread implements Runnable {
	private Thread thread;
	private SocketWrapper socketWrapper;
	private Scanner scanner;
	private Admin admin;

	public AdminWriteThread(Admin admin, SocketWrapper socketWrapper, Scanner scanner) {
		this.admin = admin;
		this.scanner = scanner;
		this.socketWrapper = socketWrapper;
		this.thread = new Thread(this,"AdminWriteThread");
		thread.start();
	}

	@Override
	public void run() {
		try {
			System.out.println("In run of admin write thread");
			while (admin.isLoggedIn()) {
				System.out.println("********* Stock Menu *************");
				String command = scanner.nextLine().trim();
				if (command.equalsIgnoreCase("LOGOUT")) {
					socketWrapper.write(new LogoutDTO(admin.getName()));
					admin.setLoggedIn(false);
				}
				else {
					String[] tokens = command.split("\\s+");

					if (tokens.length == 3) {
						String action = tokens[0];
						String stockName = tokens[1];
						double value;

						try {
							value = Double.parseDouble(tokens[2]);
						} catch (NumberFormatException e) {
							System.out.println("Invalid value. Please enter a valid numeric value.");
							return;
						}

						switch (action) {
							case "I":
								socketWrapper.write(new StockInitUpdateDTO(UpdateType.IncrementPrice, stockName, value));
								break;
							case "D":
								socketWrapper.write(new StockInitUpdateDTO(UpdateType.DecrementPrice, stockName, value));
								break;
							case "C":
								socketWrapper.write(new StockInitUpdateDTO(UpdateType.ChangeQuantity, stockName, value));
								break;
							default:
								System.out.println("Invalid action. Usage: I <stock> <value>, D <stock> <value>, C <stock> <value>");
						}
					}else if(tokens[1] == "V") {
						socketWrapper.write(new ViewDTO("admin"));
					} 
					else {
						System.out.println("Usage error. Wrong number of arguments. Usage: I <stock> <value>, D <stock> <value>, C <stock> <value>");
					}
				}
			}
		}catch (IOException e) {
			System.out.println("Exception in admin write thread");
			e.printStackTrace();
		}
	}
}
