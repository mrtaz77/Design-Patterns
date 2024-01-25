package observers;

import java.util.Scanner;

import DataTransferObjects.LoginDTO;
import util.SocketWrapper;

public class Admin implements InputValidator {
	private UserType type = UserType.ADMIN;
	private boolean isLoggedIn = false;

	public UserType getType() {
		return type;
	}

	public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

	public Admin(String serverAddress, int serverPort, Scanner scanner) {
        try {
            var socketWrapper = new SocketWrapper(serverAddress, serverPort);
			var loginDTO = new LoginDTO("Admin",true);
			System.out.println("");
            socketWrapper.write(loginDTO);
			new AdminReadThread(socketWrapper);
			new AdminWriteThread(this, socketWrapper, scanner);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		String serverAddress = "127.0.0.1";
        int serverPort = 3000;
		try(Scanner scanner = new Scanner(System.in)){
			System.out.println("Login");
			while(true){
				var tokens = scanner.nextLine().split(" ");
				if(tokens.length == 2 && tokens[0].equalsIgnoreCase("login") && tokens[1].equals("Admin")){
					new Admin(serverAddress, serverPort,scanner);
					break;
				}else {
					System.out.println("Please login first");
				}
			}
		}
	}
}