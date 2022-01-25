package gomoku.net.commands;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;

import java.io.IOException;
import gomoku.logs.Log;

public class ChangeName implements ICommand {

	MessageHandler msgHandler;
	String name;
	Log log;

	public ChangeName(Log clHlog, MessageHandler handler, String newName) {
		msgHandler = handler;
		name = newName;
		log = clHlog;
	}

	@Override
	public void execute() throws IOException {
		String oldName = msgHandler.getName();
		if(msgHandler.tryRename(name)) {
			msgHandler.sendToMe("[!] Your new Name is '" + name + "'");
			log.info(oldName + " changed name to => " + name + '\n');
		}
	}
}