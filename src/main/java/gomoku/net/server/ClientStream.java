package gomoku.net.server;

import java.io.IOException;
import java.net.Socket;

import gomoku.logs.Log;

public class ClientStream {
	private Socket socket;
	private Sender sender;
	private Reciever reciever;
	private Log clSreamLog;

	public ClientStream(Log log, Socket clientSocket) throws IOException {
		clSreamLog = log;
		socket = clientSocket;
		sender = new Sender(clientSocket.getOutputStream());
		reciever = new Reciever(clSreamLog, clientSocket.getInputStream());
		clSreamLog.info(socket + " is available\n");
	}

	public String getData() {
		return reciever.getData();
	}

	public void sendToMe(String data) throws IOException {
		sender.sendToMe(data);
	}

	public void send(String data, ClientStream stream) throws IOException {
			sender.send(data, stream.sender);
	}

	public void close() {
		try{
			this.closeStreams();
			socket.close();
			clSreamLog.info(socket + " closed\n");
		} catch(IOException e) {
			clSreamLog.severe(e.getMessage() + '\n');
			// System.out.println("OutputStream Closing Error");
		}
	}

	public void closeStreams() throws IOException {
		this.sender.closeOutput();
		this.reciever.closeInput();
	}

	public boolean isClosed() {
		return socket.isClosed();
	}

	@Override
	public boolean equals(Object o) {
		if(this == o){
			return true;
		} else if(getClass() != o.getClass()) {
			return false;
		} else {
			ClientStream stream = (ClientStream)o;
			return this.sender == stream.sender;
		}
	}

	@Override
	public int hashCode() {
		return this.sender.hashCode();
	}
}