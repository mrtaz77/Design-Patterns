package observers;

import java.util.Scanner;

import DataTransferObjects.LoginDTO;
import util.SocketWrapper;

public class Customer extends User {

	public Customer(String name,String serverAddress, int serverPort) {
        try {
			this.name = name;
			type = UserType.CUSTOMER;
            var socketWrapper = new SocketWrapper(serverAddress, serverPort);
			var loginDTO = new LoginDTO(name,true);
            socketWrapper.write(loginDTO);
			isLoggedIn = true;
			new CustomerReadThread(socketWrapper);
			new CustomerWriteThread(this, socketWrapper);
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
				if(tokens.length == 2 && tokens[0].equalsIgnoreCase("login")){
					scanner.close();
					new Customer(tokens[1],serverAddress, serverPort);
					break;
				}else {
					System.out.println("Please login first");
				}
			}
		}
	}
}
