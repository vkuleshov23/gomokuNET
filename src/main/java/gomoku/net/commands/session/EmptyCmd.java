package gomoku.net.commands.session;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;

import java.io.IOException;

import gomoku.logs.Log;

public class EmptyCmd implements ICommand {

	private Log log;
	private MessageHandler msgHandler;

	public EmptyCmd(Log sessionLog, MessageHandler handler) {
		// System.out.println("#INIT EmptyCMD");
		this.log = sessionLog;
		msgHandler = handler;
	}

	@Override
	public void execute() throws IOException {
		log.warning("Unidentified command at session from " + msgHandler.getName() + '\n');
	}
}