import java.util.Scanner;

import passenger.*;

public class Spaceship {

	private static Passenger passenger;
	private static boolean isLoggedIn , quit;

	private static void init() {
		System.out.println("Welcome to the Skeld !!!\nLogin to access...");
		isLoggedIn = false;
		quit = false;
	}

	private static void invalidArguments(String command) {
		System.out.println("Invalid number of arguments for command: " + command);
	}

	private static void login(Passenger passenger) {
		System.out.println("Welcome Crewmate!");
		if(passenger instanceof ImposterDecorator) System.out.println("We won't tell anyone; you are an imposter.");
		isLoggedIn = true;
	}

	private static void logout(Passenger passenger) {
		System.out.println("Bye Bye crewmate.");
		if(passenger instanceof ImposterDecorator) System.out.println("See you again Comrade Imposter.");
		isLoggedIn = false;
	}

	public static void main(String[] args) {
		init();

		try (Scanner scanner = new Scanner(System.in)) {
			String command = "";
			while (true) {
				command = scanner.nextLine();
				executeCommand(command);
				if(quit)break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void executeCommand(String command) {
		String[] tokens = command.split(" ");

		String role = tokens[0].toLowerCase();

		switch (role) {
			case "login":
				if (tokens.length != 2) {
					invalidArguments(command);
					break;
				}
			
				if (isLoggedIn) {
					System.out.println(passenger.name() + " is already logged in.");
					break;
				}
			
				String roleToken = tokens[1];
			
				if (roleToken.startsWith("crew") || roleToken.startsWith("imp")) {
					passenger = new Crewmate(roleToken);
					if(roleToken.startsWith("imp"))passenger = new ImposterDecorator(passenger);
					login(passenger);
				} else {
					System.out.println("Invalid login.");
				}
				break;
			case "repair":
				if (tokens.length != 1) {
					invalidArguments(command);
				} else if (isLoggedIn) {
					passenger.repair();
				} else {
					System.out.println("Not logged in.");
				}
				break;
			case "work":
				if (tokens.length != 1) {
					invalidArguments(command);
				} else if (isLoggedIn) {
					passenger.work();
				} else {
					System.out.println("Not logged in.");
				}
				break;
			case "logout":
				if (tokens.length != 1) {
					invalidArguments(command);
				} else if (isLoggedIn) {
					logout(passenger);
					passenger = null;
				} else {
					System.out.println("Not logged in.");
				}
				break;
			case "q":
				if(isLoggedIn) {
					System.out.println("Please log out first.");
				} else {
					System.out.println("Exiting the Skeld...");
					quit = true;
				}
				break;
			default:
				System.out.println("Unknown command: " + command);
		}
	}

}
