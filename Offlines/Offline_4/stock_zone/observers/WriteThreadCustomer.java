package observers;

import util.SocketWrapper;

import java.io.IOException;
import java.util.Scanner;

public class WriteThreadCustomer implements Runnable {

    private Thread thread;
    private SocketWrapper socketWrapper;
    private Customer customer;

    public WriteThreadCustomer(Customer customer, SocketWrapper socketWrapper) {
        this.customer = customer;
        this.socketWrapper = socketWrapper;
        this.thread = new Thread(this,"CustomerWriteThread");
        thread.start();
    }

    public void run() {
        try {
            Scanner input = new Scanner(System.in);
			System.out.println("********* Stock Menu *************");
            while (true) {
				System.out.print("> ");
				var line = input.nextLine();
                socketWrapper.write(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



