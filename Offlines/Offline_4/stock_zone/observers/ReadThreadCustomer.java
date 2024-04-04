package observers;

import DataTransferObjects.ViewDTO;
import util.SocketWrapper;

public class ReadThreadCustomer implements Runnable {
    private Thread thread;
    private SocketWrapper socketWrapper;

    public ReadThreadCustomer(SocketWrapper SocketWrapper) {
        this.socketWrapper = SocketWrapper;
        this.thread = new Thread(this,"ReadThreadCustomer");
        thread.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
				if(!(o instanceof ViewDTO)) System.out.print("\n: ");
                System.out.print(o);
				if(o instanceof String) {
					var str = ((String)o);
					if(str.equals("Logout successfull"))break;
				}
				System.out.print("\n> ");
			}
        } catch (Exception e) {
            System.out.println("Found error in " + thread.getName());
			e.printStackTrace();
        } 
    }
}



