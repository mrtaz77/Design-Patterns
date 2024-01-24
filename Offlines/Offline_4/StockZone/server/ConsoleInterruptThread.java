package server;

import java.io.IOException;
import java.util.Scanner;

import DataTransferObjects.InterruptDTO;

public class ConsoleInterruptThread implements Runnable {
	private Server server;
	private Thread thread;

	public ConsoleInterruptThread(Server server){
		this.server = server;
		thread = new Thread(this,"ConsoleInterruptThread");
		thread.start();
	}

	@Override
	public void run() {
		try(var scanner = new Scanner(System.in)){
			while(true){
				var line = scanner.nextLine();
				if(line.equalsIgnoreCase("stop")){
					var interruptDTO = new InterruptDTO(true);
					disconnectUsers(interruptDTO);
					terminateServer();
				}
			}
		}
	}

	public void disconnectUsers(InterruptDTO interruptDTO){
		for(String user:server.getNetwork().keySet()){
			try{
				server.getNetwork().get(user).write(interruptDTO);
			}catch(IOException e){
				System.out.println("Exception while disconnecting user " + user);
				e.printStackTrace();
			}
		}
	}

	public void terminateServer(){
		try{
			server.serverSocket.close();
		}catch(IOException e){
			System.out.println("Exception while terminating server");
			e.printStackTrace();;
		}
	}
}
