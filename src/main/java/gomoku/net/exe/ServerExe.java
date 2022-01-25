package gomoku.net.exe;

import gomoku.net.server.Server;
import gomoku.net.utils.NETparam;

import java.io.IOException;

public class ServerExe {
	public static void main(String[] args) {
		try {
			// System.out.println("MAIN");
			Server serv = new Server(NETparam.serverPort);
			serv.run();
			// System.out.println("END MAIN");
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		}
	}
}