package observers;

import util.SocketWrapper;

public class Customer extends User {

	public Customer(String serverAddress, int serverPort) {
        try {
            var socketWrapper = new SocketWrapper(serverAddress, serverPort);
			new ReadThreadCustomer(socketWrapper);
			new WriteThreadCustomer(this, socketWrapper);
        } catch (Exception e) {
            System.out.println("Error in customer");
            e.printStackTrace();
        }
    }

	public static void main(String[] args) {
		new Customer(serverAddress, serverPort);
	}
}
