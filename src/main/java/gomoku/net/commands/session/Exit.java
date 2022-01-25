package gomoku.net.commands.session;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;

import java.io.IOException;

import gomoku.logs.Log;

public class Exit implements ICommand {

	private MessageHandler msgHandler;
	private Log log;

	public Exit(Log sessionLog, MessageHandler handler) {
		msgHandler = handler;
		log = sessionLog;
	}

	@Override
	public void execute() throws IOException {
		log.info(msgHandler.getName() + " left the session\n");
		// msgHandler.closeSocket();
	}
}