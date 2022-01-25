package gomoku.net.commands;

import gomoku.net.interfaces.ICommand;

import gomoku.logs.Log;
import gomoku.net.server.MessageHandler;

import java.io.IOException;

public class EmptyCmd implements ICommand {

	Log log;
	MessageHandler msgHandler;

	public EmptyCmd(Log clHlog, MessageHandler handler) {
		msgHandler = handler;
		log = clHlog;
	}

	@Override
	public void execute() throws IOException {
		log.warning(msgHandler.getName() + " send non-existant command\n");
	}
}