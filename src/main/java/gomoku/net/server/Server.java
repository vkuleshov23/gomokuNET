package gomoku.net.server;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gomoku.logs.Log;
import gomoku.net.utils.*;

public class Server {
	
	private Log log;
	private ServerSocket socket;
	private ExecutorService executor;
	private ClientBase activeClients;

	public Server(int port) throws IOException {
		log = new Log(this.getClass().getName());
		this.socket = new ServerSocket(port);
		this.activeClients = new ClientBase();
		this.executor = Executors.newCachedThreadPool();
	}

	public void run() throws IOException {
		try {
			log.info("server start\n");
			this.clientLoop();
		} catch (IOException e) {
			e.getMessage();
			e.printStackTrace();
		} finally {
			log.info("server end\n");
			activeClients.disableAll();
			executor.shutdown();
		}
	}

	private void clientLoop() throws IOException {
		while(true) {
			Socket clientSocket = socket.accept();
			ClientHandler newClient = new ClientHandler(log, activeClients, clientSocket);
			executor.submit(newClient);
		}
	}
}