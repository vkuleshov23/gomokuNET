package gomoku.net.server;

import gomoku.net.utils.*;
import gomoku.net.commands.session.*;
import gomoku.net.interfaces.*;
import gomoku.model.Gomoku;

import gomoku.logs.Log;

import java.io.IOException;

import java.net.Socket;

public class GameSession {
	
	private MessageHandler msgHandler;
	private String enemyName;
	private boolean inGame = true;
	private Gomoku game;
	private Log sessionLog;

	public GameSession(Log log, Gomoku game, MessageHandler handler) throws IOException {
		this.msgHandler = handler;
		this.enemyName = this.waitPlayer();
		this.game = game;
		this.createLogger(log);
	}

	public GameSession(Log log, Gomoku game, MessageHandler handler, String enemy) throws IOException {
		this.msgHandler = handler;
		this.enemyName = enemy;
		this.game = game;
		this.createLogger(log);
	}

	private void createLogger(Log log) {
		sessionLog = log;
	}

	private String waitPlayer() throws IOException {
		while(true) {
			String data = msgHandler.getDataFromClient();
			DataPacket packet = new DataPacket(data);
			switch(packet.getCommand()){
				case "@connect":
					// sessionLog.info(msgHandler.getName() + " finally waited " + packet.getMSG() + '\n');
					return packet.getMSG();
				default:
					this.msgHandler.sendToMe("[X]");
					Creators.remove(msgHandler.getName());
					this.inGame = false;
					return "[X]";
			}
		}
	}

	public void begin() throws IOException {
		while(this.inGame) {
			String data = msgHandler.getDataFromClient();
			DataPacket packet = new DataPacket(data);
			ICommand command = defineCommand(packet);
			command.execute();
		}
	}

	public ICommand defineCommand(DataPacket packet) {
		ICommand command;
		switch(packet.getCommand()) {
			case "@exit":
				this.inGame = false;
				command = new Exit(sessionLog, msgHandler);
				break;
			case "@move":
				command = new Move(sessionLog, game, msgHandler, enemyName, packet.getMSG());
				break;
			case "@hint":
				command = new Hint(sessionLog, game, msgHandler, enemyName);
				break;
			case "@leave":
				command = new Leave(sessionLog, msgHandler, enemyName);
				break;
			case "@leaveBoth":
				command = new LeaveBoth(sessionLog, msgHandler, enemyName);
				break;
			default:
				command = new EmptyCmd(sessionLog, msgHandler);
				break;
		}
		return command;
	}
}