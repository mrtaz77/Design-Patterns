package observers;

import util.SocketWrapper;

import java.io.IOException;

import DataTransferObjects.LoginDTO;

public class ReadThreadAdmin implements Runnable {
    private Thread thread;
    private SocketWrapper SocketWrapper;

    public ReadThreadAdmin(SocketWrapper SocketWrapper) {
        this.SocketWrapper = SocketWrapper;
        this.thread = new Thread(this,"AdminReadThread");
        thread.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = SocketWrapper.read();
                processObject(o);
            }
        } catch (Exception e) {
            System.out.println("Exception in admin read thread");
            e.printStackTrace();
        } finally {
            try {
                SocketWrapper.closeConnection();
            } catch (IOException e) {
				System.out.println("Exception while closing admin connection");
                e.printStackTrace();
            }
        }
    }

	private void processObject(Object obj) {
		if(obj instanceof LoginDTO) processLoginDTO(obj);
		else System.out.println(obj);
	}

	private void processLoginDTO(Object obj) {
		var loginDTO = (LoginDTO) obj;
		var userName = loginDTO.getName();
		System.out.println(userName + " just logged in");
	}
}



