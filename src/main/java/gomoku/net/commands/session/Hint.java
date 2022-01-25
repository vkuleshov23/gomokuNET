package gomoku.net.commands.session;

import gomoku.net.interfaces.ICommand;
import gomoku.net.server.MessageHandler;
import gomoku.net.server.Creators;
import gomoku.net.server.GameSession;

import gomoku.model.Gomoku;
import gomoku.model.Coordinates;

import java.io.IOException;

import gomoku.logs.Log;

public class Hint implements ICommand {

	private MessageHandler msgHandler;
	private Gomoku game;
	private String enemyName;
	private Log log;
	
	public Hint(Log sessionLog, Gomoku gomoku, MessageHandler handler, String enemy) {
		this.msgHandler = handler;
		this.game = gomoku;
		this.enemyName = enemy;
		this.log = sessionLog;
	}

	@Override
	public void execute() throws IOException {
		synchronized(game){
			Coordinates crd = game.aiCoordinates();
			int res = game.move(crd);
			log.info(msgHandler.getName() + " use hint: " + crd + " to " + enemyName + '\n');
			this.sendResults(res, crd);
		}
	}

	private void sendResults(int res, Coordinates crd) throws IOException {
		if(!msgHandler.otherExist(enemyName)) {
			msgHandler.sendToMe("EXIT");
			return;
		}
		switch(res){
			case 0:
				msgHandler.sendToMe("OK");
				try{
					Thread.sleep(100);
				} catch (InterruptedException ie){}
				this.sendMoves(crd.toString());
				break;
			case 1:
				msgHandler.sendToMe("END");
				msgHandler.sendToName(enemyName, "END");
				log.info("game " + enemyName + " and " + msgHandler.getName() + " is END | " + msgHandler.getName() + " is winner!\n");
				try{
					Thread.sleep(100);
				} catch (InterruptedException ie){}
				this.sendMoves(crd.toString());
				break;
			case -1:
				log.severe("ERROR move from AI\n");
				msgHandler.sendToMe("ERROR");
				break;
		}
	}
	
	private void sendMoves(String move) throws IOException {
		msgHandler.sendToMe(move);
		msgHandler.sendToName(enemyName, move);
	}
}