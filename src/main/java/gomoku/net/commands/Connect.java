package gomoku.net.commands;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;
import gomoku.net.server.Creators;
import gomoku.net.server.GameSession;

import gomoku.logs.Log;

import gomoku.model.Gomoku;

import java.io.IOException;

public class Connect implements ICommand {

	MessageHandler msgHandler;
	String enemyName;
	Log log;

	public Connect(Log clHlog, MessageHandler handler, String enemy) {
		msgHandler = handler;
		enemyName = enemy;
		log = clHlog;
	}

	@Override
	public void execute() throws IOException {
		if(Creators.isContain(enemyName)){
			this.msgHandler.sendToMe("OK");
			Gomoku game = Creators.getGame(enemyName);
			Creators.remove(enemyName);
			this.msgHandler.sendToName(enemyName, "@connect " + msgHandler.getName());
			// log.info(msgHandler.getName() + " connect to " + enemyName + '\n');
			this.connectSessionAndBegin(game);
		} else {
			this.msgHandler.sendToMe("ERROR");
			// log.warning(msgHandler.getName() + " wanted to connect to non-existent player\n");
		}

	}
	private void connectSessionAndBegin(Gomoku game) throws IOException {
		GameSession session = new GameSession(log, game, msgHandler, enemyName);
		session.begin();
	}
}