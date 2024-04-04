package observers;

import util.SocketWrapper;

import java.io.IOException;
import java.util.Scanner;

import DataTransferObjects.LoginDTO;
import DataTransferObjects.LogoutDTO;
import DataTransferObjects.StockInitUpdateDTO;
import DataTransferObjects.UpdateType;
import DataTransferObjects.ViewDTO;

public class WriteThreadAdmin implements Runnable {

	private Thread thread;
	private SocketWrapper socketWrapper;
	private Admin admin;
	private boolean confirmLogout = false;


	public WriteThreadAdmin(SocketWrapper socketWrapper, Admin admin) {
		this.socketWrapper = socketWrapper;
		this.admin = admin;
		this.thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.print("********* Stock Menu *************\n> ");
			while (!confirmLogout) {
				var command = scanner.nextLine();
				if(admin.isLoggedIn)processStockCommands(command);
				else processLoginCommands(command);
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void processLoginCommands(String command) throws IOException {
		var tokens = command.split(" ");
		var isLoggedIn = tokens.length == 2 && tokens[0].equalsIgnoreCase("login") && tokens[1].startsWith("admin");
		if (isLoggedIn) {
			admin.name = tokens[1];
			admin.type = UserType.ADMIN;
			admin.isLoggedIn = true;
			socketWrapper.write(admin.name);
			var loginDTO = new LoginDTO(admin.name,true);
			socketWrapper.write(loginDTO);
		}
		else {
			System.out.println("Please login first");
		}	
	}

	private void processStockCommands(String command) throws IOException{
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
				case "I" -> socketWrapper.write(new StockInitUpdateDTO(UpdateType.IncrementPrice, stockName, value));
				case "D" -> socketWrapper.write(new StockInitUpdateDTO(UpdateType.DecrementPrice, stockName, value));
				case "C" -> socketWrapper.write(new StockInitUpdateDTO(UpdateType.ChangeQuantity, stockName, value));
				default -> System.out.println("Invalid action. Usage: I <stock> <value>, D <stock> <value>, C <stock> <value>");
			}
		}else if(command.equals("V")) socketWrapper.write(new ViewDTO(admin.getName()));
		else if(command.equalsIgnoreCase("LOGOUT")) {
			socketWrapper.write(new LogoutDTO(admin.getName()));
			admin.isLoggedIn = false;
			confirmLogout = true;
		}
		else System.out.println("Usage error. Wrong number of arguments. Usage: I <stock> <value>, D <stock> <value>, C <stock> <value>");
	}
}
