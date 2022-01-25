package gomoku.net.commands.session;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;

import java.io.IOException;

import gomoku.logs.Log;

public class LeaveBoth implements ICommand {

	private MessageHandler msgHandler;
	private Log log;
	private String enemyName;

	public LeaveBoth(Log sessionLog, MessageHandler handler, String enemy) {
		msgHandler = handler;
		log = sessionLog;
		enemyName = enemy;
	}

	@Override
	public void execute() throws IOException {
		msgHandler.sendToMe("LEAVE");
		msgHandler.sendToName(enemyName, "LEAVE");
		log.info(msgHandler.getName() + " leave the session\n");
		// msgHandler.closeSocket();
	}
}