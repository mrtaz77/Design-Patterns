package observers;

import java.util.Scanner;

import DataTransferObjects.LoginDTO;
import util.SocketWrapper;

public class Admin extends User {

	public Admin(String name,String serverAddress, int serverPort, Scanner scanner) {
        try {
			this.name = name;
			this.type = UserType.ADMIN;
            var socketWrapper = new SocketWrapper(serverAddress, serverPort);
			var loginDTO = new LoginDTO("Admin",true);
            socketWrapper.write(loginDTO);
			isLoggedIn = true;
			new AdminReadThread(socketWrapper);
			new AdminWriteThread(this, socketWrapper,scanner);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		try(Scanner scanner = new Scanner(System.in)){
			System.out.println("Login");
			while(true){
				var tokens = scanner.nextLine().split(" ");
				if(tokens.length == 2 && tokens[0].equalsIgnoreCase("login") && tokens[1].startsWith("admin")){
					new Admin(tokens[1],serverAddress, serverPort, scanner);
					break;
				}else {
					System.out.println("Please login first");
				}
			}
		}
	}
}