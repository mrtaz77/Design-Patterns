package observers;

import java.util.Scanner;

import DataTransferObjects.LoginDTO;
import util.SocketWrapper;

public class Admin {
	private UserType type = UserType.ADMIN;
	private boolean isLoggedIn = false;
	private String name;

	public String getName() { return name; }

	public UserType getType() {
		return type;
	}

	public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

	public Admin(String name,String serverAddress, int serverPort, Scanner scanner) {
        try {
			this.name = name;
            var socketWrapper = new SocketWrapper(serverAddress, serverPort);
			System.out.println("After initialization of socketwrapper");
			var loginDTO = new LoginDTO("Admin",true);
            socketWrapper.write(loginDTO);
			isLoggedIn = true;
			new AdminReadThread(socketWrapper);
			new AdminWriteThread(this, socketWrapper, scanner);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		String serverAddress = "127.0.0.1";
        int serverPort = 33333;
		try(Scanner scanner = new Scanner(System.in)){
			System.out.println("Login");
			while(true){
				var tokens = scanner.nextLine().split(" ");
				if(tokens.length == 2 && tokens[0].equalsIgnoreCase("login") && tokens[1].startsWith("admin")){
					new Admin(tokens[1],serverAddress, serverPort,scanner);
					break;
				}else {
					System.out.println("Please login first");
				}
			}
		}
	}
}