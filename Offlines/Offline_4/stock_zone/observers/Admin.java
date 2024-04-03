package observers;

import util.SocketWrapper;

import java.util.Scanner;

import DataTransferObjects.LoginDTO;

public class Admin extends User {

    public Admin(String serverAddress, int serverPort) {
        try {
            System.out.println("Login");
            Scanner scanner = new Scanner(System.in);
			var isAdmin = false;
			while (!isAdmin) {
				var tokens = scanner.nextLine().split(" ");
				isAdmin = tokens.length == 2 && tokens[0].equalsIgnoreCase("login") && tokens[1].startsWith("admin");
				if (!isAdmin) {
					System.out.println("Please login first");
				}else {
					this.name = tokens[1];
					this.type = UserType.ADMIN;
				}
			}
            SocketWrapper socketWrapper = new SocketWrapper(serverAddress, serverPort);
            var loginDTO = new LoginDTO(name,true);
			socketWrapper.write(name);
			socketWrapper.write(loginDTO);
			System.out.println(socketWrapper.read());
            new ReadThreadAdmin(socketWrapper);
            new WriteThreadAdmin(socketWrapper, name);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        new Admin(serverAddress, serverPort);
    }
}


