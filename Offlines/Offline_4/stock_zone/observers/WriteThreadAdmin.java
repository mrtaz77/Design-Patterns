package observers;

import util.SocketWrapper;

import java.io.IOException;
import java.util.Scanner;

import DataTransferObjects.StockInitUpdateDTO;
import DataTransferObjects.UpdateType;
import DataTransferObjects.ViewDTO;

public class WriteThreadAdmin implements Runnable {

	private Thread thread;
	private SocketWrapper socketWrapper;
	Admin admin;

	public WriteThreadAdmin(SocketWrapper SocketWrapper, Admin admin) {
		this.socketWrapper = SocketWrapper;
		this.admin = admin;
		this.thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		try {
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			System.out.println("********* Stock Menu *************");
			while (true) {
				System.out.print("> ");
				var command = input.nextLine();
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

	private void processCommand(String command) throws IOException{
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
		}else if(tokens[0] == "V") socketWrapper.write(new ViewDTO(admin.getName()));
		else System.out.println("Usage error. Wrong number of arguments. Usage: I <stock> <value>, D <stock> <value>, C <stock> <value>");
	}
}
