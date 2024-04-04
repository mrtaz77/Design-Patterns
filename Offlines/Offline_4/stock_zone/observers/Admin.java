package observers;

import util.SocketWrapper;

public class Admin extends User {

    public Admin(String serverAddress, int serverPort) {
        try {
            var socketWrapper = new SocketWrapper(serverAddress, serverPort);
            new ReadThreadAdmin(socketWrapper);
            new WriteThreadAdmin(socketWrapper, this);
        } catch (Exception e) {
            System.out.println("Error in admin");
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new Admin(serverAddress, serverPort);
    }
}


