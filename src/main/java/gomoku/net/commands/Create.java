package gomoku.net.commands;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;
import gomoku.net.server.Creators;
import gomoku.net.server.GameSession;

import gomoku.logs.Log;

import gomoku.model.Gomoku;

import java.io.IOException;

public class Create implements ICommand {

	MessageHandler msgHandler;
	Log log;

	public Create(Log clHlog, MessageHandler handler) {
		msgHandler = handler;
		log = clHlog;
	}

	@Override
	public void execute() throws IOException {
		Gomoku game = new Gomoku(true);
		Creators.set(msgHandler.getName(), game);
		// log.info(msgHandler.getName() + " create a new game\n");
		this.creteSessionAndBegin(game);
	}
	private void creteSessionAndBegin(Gomoku game) throws IOException {
		GameSession session = new GameSession(log, game, msgHandler);
		session.begin();
	}
}