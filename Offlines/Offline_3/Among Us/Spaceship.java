import java.util.Scanner;

import adapter.ImposterAdapter;
import passenger.Crewmate;
import spaceMonster.Imposter;

public class Spaceship {

	private static Crewmate passenger;
	private static boolean isLoggedIn , quit;

	private static void init() {
		System.out.println("Welcome to the Skeld !!! Login to access...");
		isLoggedIn = false;
		quit = false;
	}

	private static void invalidArguments(String command) {
		System.out.println("Invalid number of arguments for command: " + command);
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
					System.out.println(passenger.getName() + " is already logged in.");
					break;
				}
			
				String roleToken = tokens[1];
			
				if (roleToken.startsWith("crew") || roleToken.startsWith("imp")) {
					if (roleToken.startsWith("crew")) {
						passenger = new Crewmate(roleToken);
					} else {
						Imposter imp = new Imposter(roleToken);
						passenger = new ImposterAdapter(imp);
					}
			
					passenger.login();
					isLoggedIn = true;
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
					passenger.logout();
					passenger = null;
					isLoggedIn = false;
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
