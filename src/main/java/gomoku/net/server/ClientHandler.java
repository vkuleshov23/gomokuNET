package gomoku.net.server;

import gomoku.net.utils.*;
import gomoku.net.commands.*;
import gomoku.net.interfaces.*;

import gomoku.logs.Log;

import java.io.IOException;

import java.net.Socket;

public class ClientHandler implements Runnable {
	
	ClientBase clients;
	MessageHandler msgHandler;
	private Log clHandlerLog;

	ClientHandler(Log log, ClientBase clientBase, Socket socket) throws IOException {
		this.clHandlerLog = log;
		this.clients = clientBase;
		this.setMsgHandler(socket);
	}

	private void setMsgHandler(Socket socket) {
		try {
			msgHandler = new MessageHandler(clHandlerLog, socket, this.setRandomName(), clients);
		} catch (IOException e) {
			clHandlerLog.warning(e.getMessage() + '\n');
			// e.getMessage();
			// e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			this.handling();
		} finally {
			if(!msgHandler.isQuit()) {
				msgHandler.closeSocket();
			}
		}
	}

	private String setRandomName() {
		while (true){
			String name = RandomString.random();
			if(!clients.isContain(name)) {
				return name;
			}
		}
	}

	private void handling() {
		try {
			msgHandler.setMeToBase();
			msgHandler.sendToMe("Your name is: " + msgHandler.getName() + '\n');

			clHandlerLog.info(msgHandler.getName() + " join to client base\n");
			
			recieverLoop();
		} catch(IOException e) {
			clHandlerLog.warning(e.getMessage() + '\n');
			System.out.println( e.getMessage() );
			// e.printStackTrace();
		} finally {
			msgHandler.deleteMeFromAll();
			msgHandler.closeSocket();
			clHandlerLog.info(msgHandler.getName() + " left the server\n");
		}
	}

	private void recieverLoop() throws IOException {
		while(!msgHandler.isQuit()) {
			String data = msgHandler.getDataFromClient();
			DataPacket packet = new DataPacket(data);
			ICommand command = defineCommand(packet);
			command.execute();
		}
	}

	private ICommand defineCommand(DataPacket packet) {
		ICommand command;
		switch(packet.getCommand()) {
			case "@quit":
				command = new Quit(clHandlerLog, msgHandler);
				break;
			case "@crList":
				command = new GetCreators(clHandlerLog, msgHandler);
				break;
			case "@name":
				command = new ChangeName(clHandlerLog, msgHandler, packet.getMSG());
				break;
			case "@create":
				command = new Create(clHandlerLog, msgHandler);
				break;
			case "@connect":
				command = new Connect(clHandlerLog, msgHandler, packet.getMSG());
				break;
			case "@getName":
				command = new GetName(clHandlerLog, msgHandler);
				break;
			default:
				command = new EmptyCmd(clHandlerLog, msgHandler);
				break;
		}
		return command;
	}
}