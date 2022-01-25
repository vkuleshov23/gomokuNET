package gomoku.client;

import gomoku.view.console.ConsoleGame;
import java.io.IOException;
import java.net.UnknownHostException;

public class ConsoleGameExe{
	public static void main(String args[]) {
			ConsoleGame game = null;
		try {
			game = new ConsoleGame();
			game.begin();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		// } catch (UnknownHostException uhe) {
		// 	System.out.println(uhe.getMessage());
		} 
	}
}