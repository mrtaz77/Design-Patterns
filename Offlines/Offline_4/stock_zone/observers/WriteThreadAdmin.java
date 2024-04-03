package observers;

import util.SocketWrapper;

import java.io.IOException;
import java.util.Scanner;

public class WriteThreadAdmin implements Runnable {

	private Thread thread;
	private SocketWrapper socketWrapper;
	String name;

	public WriteThreadAdmin(SocketWrapper SocketWrapper, String name) {
		this.socketWrapper = SocketWrapper;
		this.name = name;
		this.thread = new Thread(this);
		thread.start();
	}

	@Override
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
