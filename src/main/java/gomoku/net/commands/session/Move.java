package gomoku.net.commands.session;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;
import gomoku.net.server.Creators;
import gomoku.net.server.GameSession;

import gomoku.model.Gomoku;

import java.io.IOException;

import gomoku.logs.Log;

public class Move implements ICommand {

	private MessageHandler msgHandler;
	private Gomoku game;
	private String move;
	private String enemyName;
	private Log log;

	public Move(Log sessionLog, Gomoku gomoku, MessageHandler handler, String enemy, String move) {
		this.msgHandler = handler;
		this.game = gomoku;
		this.enemyName = enemy;
		this.move = move;
		this.log = sessionLog;
	}

	@Override
	public void execute() throws IOException {
		String[] coords = move.split(" ");
		log.info(msgHandler.getName() + " => move: " + move + " to " + enemyName + '\n');
		synchronized(game){
			try {
				int res = game.move(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
				this.sendResults(res);
			} catch (NumberFormatException nfe) {
				log.severe("NOT INTEGER DATA FOR MOVE FROM " + msgHandler.getName() + '\n');
			}
		}
	}

	private void sendResults(int res) throws IOException {
		if(!msgHandler.otherExist(enemyName)) {
			msgHandler.sendToMe("EXIT");
			return;
		}
		switch(res){
			case 0:
				msgHandler.sendToMe("OK");
				msgHandler.sendToName(enemyName, move);
				break;
			case 1:
				msgHandler.sendToName(enemyName, "END");
				msgHandler.sendToMe("END");
				log.info("game " + enemyName + " and " + msgHandler.getName() + " is END | " + msgHandler.getName() + " is winner!\n");
				try{
					Thread.sleep(100);
				} catch (InterruptedException ie){

				}
				msgHandler.sendToName(enemyName, move);
				break;
			case -1:
				log.warning("ERROR move from PLAYER\n");
				msgHandler.sendToMe("ERROR");
				break;
		}
	}
}