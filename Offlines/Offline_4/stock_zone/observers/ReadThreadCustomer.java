package observers;

import util.SocketWrapper;

import java.io.IOException;

public class ReadThreadCustomer implements Runnable {
    private Thread thread;
    private SocketWrapper SocketWrapper;

    public ReadThreadCustomer(SocketWrapper SocketWrapper) {
        this.SocketWrapper = SocketWrapper;
        this.thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = SocketWrapper.read();
                System.out.println(o);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                SocketWrapper.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



