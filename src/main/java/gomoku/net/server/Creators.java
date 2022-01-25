package gomoku.net.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
// import java.lang.NullPointerException;

import gomoku.model.Gomoku;

public class Creators {
	
	public static ConcurrentHashMap<String, Gomoku> creators  = new ConcurrentHashMap<String, Gomoku>();

	public static void set(String client, Gomoku game) {
		creators.put(client, game);
	}

	public static void remove(String client) {
		creators.remove(client);
	}

	public static boolean isContain(String client) {
		return creators.containsKey(client);
	}

	public static String getCreators() {
		String list = "";
		for(Map.Entry<String, Gomoku> pair : creators.entrySet()) {
			list += (pair.getKey() + "\n");
		}
		if(list.equals("")) {
			return "[X]";
		}
		return list.trim();
	}

	public static Gomoku getGame(String client) {
		return creators.get(client);
	}
}