package gomoku.net.commands;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;

import java.io.IOException;

import gomoku.logs.Log;

public class Quit implements ICommand {
	MessageHandler msgHandler;
	Log log;

	public Quit(Log clHlog, MessageHandler handler) {
		msgHandler = handler;
		log = clHlog;
	}

	@Override
	public void execute() throws IOException {
		log.info(msgHandler.getName() + " quit the server\n");
		msgHandler.closeSocket();
	}
}