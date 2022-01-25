package gomoku.logs;

import java.util.logging.*;
import java.io.IOException;

public class Log {
	private Logger log;

	public Log(String name) throws IOException {
		log = Logger.getLogger(name);
		FileHandler file = new FileHandler("src/main/java/gomoku/logs/" + name);
		log.addHandler(file);
	}

	public void info(String data) {
		synchronized(log) {
			log.info(data);
		}
	}

	public void warning(String data) {
		synchronized(log) {
			log.warning(data);
		}
	}

	public void severe(String data) {
		synchronized(log) {
			log.severe(data);
		}
	}
}