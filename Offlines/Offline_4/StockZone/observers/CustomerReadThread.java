package observers;

import java.io.IOException;

import DataTransferObjects.InterruptDTO;
import util.SocketWrapper;

public class CustomerReadThread implements Runnable {
	private SocketWrapper socketWrapper;
	private Thread thread;

	public CustomerReadThread(SocketWrapper socketWrapper) {
		this.socketWrapper = socketWrapper;
		this.thread = new Thread(this,"CustomerReadThread");
		thread.start();
	}

	@Override
	public void run() {
		try {
			while(true) {
				Object obj = socketWrapper.read();
				if(obj instanceof InterruptDTO)processInterruptDTO();
				else System.out.println(obj);
			}
		}catch (Exception e) {
            System.out.println("Exception in customer read thread");
            e.printStackTrace();
        } finally {
            processInterruptDTO();
        }	
	}

	private void processInterruptDTO() {
		try {
			socketWrapper.close();
		} catch (IOException e) {
			System.out.println("Exception while closing socket");
			e.printStackTrace();
		}	
	}
}
