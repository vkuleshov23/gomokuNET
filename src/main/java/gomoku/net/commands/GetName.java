package gomoku.net.commands;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;

import gomoku.logs.Log;

import java.io.IOException;

public class GetName implements ICommand {

	MessageHandler handler;
	Log log;

	public GetName(Log clHlog, MessageHandler msgHandler) {
		this.handler = msgHandler;
		log = clHlog;
	}

	@Override
	public void execute() throws IOException {
		this.handler.sendToMe(this.handler.getName());
		log.info(handler.getName() + " requested his name\n");
	}
}