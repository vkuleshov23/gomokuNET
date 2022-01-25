package gomoku.net.server;

import java.io.IOException;
import java.net.Socket;

import gomoku.logs.Log;

public class MessageHandler {

	private String myName;
	private ClientBase clients;
	private ClientStream stream;

	public MessageHandler(Log log, Socket clientSocket, String name, ClientBase clientBase) throws IOException {
		myName = name;
		clients = clientBase;
		stream = new ClientStream(log, clientSocket);
	}

	public void setName(String name) {
		myName = name;
	}

	public String getName() {
		return myName;
	}

	public void setMeToBase() throws IOException{
		if(!clients.set(myName, stream)) {
			throw new IOException("[x] Such name is Already Exist");
		}
	}	

	public void deleteMeFromAll() {
		clients.delete(myName);
		Creators.remove(myName);
	}

	public String getDataFromClient() {
		return stream.getData();
	}

	public void sendToAll(String data) throws IOException {
		if(!this.alone()) {
			for (String name : clients.getNames()) {
				if(name != myName) {
					this.sendToName(name, myName + ": " + data);
				}
			}
		}
	}

	public void sendToMe(String data) throws IOException {
		stream.sendToMe(data);
	}

	public void sendToName(String name, String data) throws IOException {
		try {
			if(!this.alone()) {
				ClientStream recipient = clients.getClientStream(name);
				stream.send(data, recipient);
			}
		} catch (NullPointerException err) {
			stream.sendToMe(err.getMessage());
		}	
	}

	public boolean otherExist(String name) {
		return clients.isContain(name);
	}

	private boolean alone() throws IOException {
		if(clients.isAlone()){
			stream.sendToMe("[!] You are alone");
			return true;
		}
		return false;
	}

	public boolean tryRename(String name) throws IOException {
		try {
			clients.rename(myName, name);
			myName = name;
			return true;
		} catch (NullPointerException err) {
			stream.sendToMe(err.getMessage());
		}
		return false;
	}

	public void closeSocket() {
		stream.close();
	}

	public boolean isQuit() {
		return stream.isClosed();
	}
}