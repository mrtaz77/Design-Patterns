package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper {
	private Socket socket;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	public SocketWrapper(String serverAddress,int port) throws IOException {
		this.socket = new Socket(serverAddress, port);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
	}

	public SocketWrapper(Socket socket) throws IOException {
		this.socket = socket;
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		objectInputStream = new ObjectInputStream(socket.getInputStream());
	}

	public Object read() throws IOException, ClassNotFoundException {
        return objectInputStream.readUnshared();
    }

	public void write(Object obj) throws IOException {
		objectOutputStream.writeUnshared(obj);
	}

	public void close() throws IOException {
		objectInputStream.close();
		objectOutputStream.close();
	}
}
