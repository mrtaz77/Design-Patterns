package observers;

import java.io.IOException;

import DataTransferObjects.InterruptDTO;
import util.SocketWrapper;

public class CustomerReadThread implements Runnable {
	private SocketWrapper socketWrapper;
	private Thread thread;
	private volatile boolean isActive;

	public CustomerReadThread(SocketWrapper socketWrapper) {
		this.socketWrapper = socketWrapper;
		this.thread = new Thread(this,"CustomerReadThread");
		isActive = true;
		thread.start();
	}

	@Override
	public void run() {
		try {
			while(isActive) {
				Object obj = socketWrapper.read();
				if(obj instanceof InterruptDTO)processInterruptDTO();
				else System.out.println(obj);
			}
		}catch (Exception e) {
            System.out.println("Exception in admin read thread");
            e.printStackTrace();
        } finally {
            try {
                socketWrapper.close();
            } catch (IOException e) {
				System.out.println("Exception while closing admin connection");
                e.printStackTrace();
            }
        }	
	}

	private void processInterruptDTO() {
		try {
			socketWrapper.close();
			isActive = false;
		} catch (IOException e) {
			System.out.println("Exception while closing socket");
			e.printStackTrace();
		}	
	}
}
