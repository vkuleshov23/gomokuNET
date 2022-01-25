package gomoku.net.commands;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;
import gomoku.net.server.Creators;
import gomoku.logs.Log;

import java.io.IOException;


public class GetCreators implements ICommand {

	MessageHandler msgHandler;
	Log log;

	public GetCreators(Log clHlog, MessageHandler handler) {
		msgHandler = handler;
		log = clHlog;
	}

	@Override
	public void execute() throws IOException {
		msgHandler.sendToMe(Creators.getCreators());
		log.info(msgHandler.getName() + " requested creators list\n");
	}
}