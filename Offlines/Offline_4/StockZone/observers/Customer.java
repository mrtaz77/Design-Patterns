package observers;

import java.util.Scanner;

import DataTransferObjects.LoginDTO;
import util.SocketWrapper;

public class Customer {
	private UserType type = UserType.CUSTOMER;
	private String name;
	private boolean isLoggedIn = false;

	public UserType getType() {
		return type;
	}

	public String getName() { return name; }

	public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

	public Customer(String name,String serverAddress, int serverPort) {
        try {
			this.name = name;
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
		String serverAddress = "127.0.0.1";
        int serverPort = 33333;
		try(Scanner scanner = new Scanner(System.in)){
			System.out.println("Login");
			while(true){
				var tokens = scanner.nextLine().split(" ");
				if(tokens.length == 2 && tokens[0].equalsIgnoreCase("login")){
					new Customer(tokens[1],serverAddress, serverPort);
					break;
				}else {
					System.out.println("Please login first");
				}
			}
		}
	}
}
