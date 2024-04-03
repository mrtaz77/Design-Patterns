package observers;

import java.util.Scanner;

import DataTransferObjects.LoginDTO;
import util.SocketWrapper;

public class Customer extends User {

	public Customer(String serverAddress, int serverPort) {
        try {
			System.out.println("Login");
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			var isLogin = false;
			while (!isLogin) {
				var tokens = scanner.nextLine().split(" ");
				isLogin = tokens.length == 2 && tokens[0].equalsIgnoreCase("login");
				if (!isLogin) {
					System.out.println("Please login first");
				}else {
					this.name = tokens[1];
					this.type = UserType.CUSTOMER;
				}
			}
            var socketWrapper = new SocketWrapper(serverAddress, serverPort);
			var loginDTO = new LoginDTO(name,true);
			socketWrapper.write(name);
            socketWrapper.write(loginDTO);
			System.out.println(socketWrapper.read());
			new ReadThreadCustomer(socketWrapper);
			new WriteThreadCustomer(this, socketWrapper);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		new Customer(serverAddress, serverPort);
	}
}
