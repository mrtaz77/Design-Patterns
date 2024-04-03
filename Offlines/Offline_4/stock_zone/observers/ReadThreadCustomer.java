package observers;

import util.SocketWrapper;

public class ReadThreadCustomer implements Runnable {
    private Thread thread;
    private SocketWrapper socketWrapper;
	private boolean confirmLogout = false;

    public ReadThreadCustomer(SocketWrapper SocketWrapper) {
        this.socketWrapper = SocketWrapper;
        this.thread = new Thread(this,"ReadThreadCustomer");
        thread.start();
    }

    public void run() {
        try {
            while (!confirmLogout) {
                Object o = socketWrapper.read();
                System.out.print(o);
				if(o instanceof String) {
					var str = ((String)o);
					if(str.equals("Logout successfull"))confirmLogout = true;
				}
				System.out.print("\n> ");
			}
        } catch (Exception e) {
            System.out.println("Found error in " + thread.getName());
			e.printStackTrace();
        } 
    }
}



