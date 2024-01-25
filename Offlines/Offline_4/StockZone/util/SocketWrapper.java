package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper {
	private final Socket socket;
	private final ObjectOutputStream objectOutputStream;
	private final ObjectInputStream objectInputStream;

	public SocketWrapper(String socketName,int port) throws IOException {
		this.socket = new Socket(socketName, port);
		objectInputStream = new ObjectInputStream(socket.getInputStream());
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
	}

	public SocketWrapper(Socket socket) throws IOException {
		this.socket = socket;
		objectInputStream = new ObjectInputStream(socket.getInputStream());
		objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
	}

	public Object read() throws IOException, ClassNotFoundException {
        return objectInputStream.readUnshared();
    }

	public void write(Object obj) throws IOException {
		objectOutputStream.writeUnshared(obj);
		objectOutputStream.flush();  // Ensure the data is sent immediately
	}

	public void close() throws IOException {
		objectInputStream.close();
		objectOutputStream.close();
		socket.close();
	}
}
