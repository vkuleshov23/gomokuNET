package gomoku.view.console;

import gomoku.client.Client;
import gomoku.view.ANSI;

import java.util.Scanner;
import java.io.IOException;
import java.net.UnknownHostException;

public class ConsoleGame {

	private Client client;
	private ConsoleView view;
	private Scanner scan;
	private boolean myMoveTurn;
	private char mark;
	private boolean quit;
	private boolean inGame;

	private String[] buttons = {
		"me",
		"name",
		"create",
		"connect",
		"list",
		"quit"
	};

	public ConsoleGame() throws UnknownHostException, IOException {
		this.client = new Client();
		this.view = new ConsoleView();
		this.quit = false;
		this.myMoveTurn = true;
		this.mark = ' ';
		this.inGame = false;
		this.scan = new Scanner(System.in);
	}

	public void begin() throws IOException {
		System.out.println(ANSI.YELLOW + "Welcome to the game " + ANSI.BLUE + "Gomoku" + ANSI.YELLOW + " player!" + ANSI.RESET);
		this.printName();
		while(!quit) {
			System.out.println(this.printButtons());
			String data = (scan.nextLine()).trim();
			switch(data) {
				case "me":
					this.printName();
					break;
				case "name":
					this.changeName();
					break;
				case "create":
					this.createSession();
					break;
				case "connect":
					this.connectToSession();
					break;
				case "list":
					this.printCreatorsList();
					break;
				case "quit":
					this.quitGame();
					break;	
				default:
					System.out.println(this.printError(data));
					break;
			}
		}
		scan.close();
		client.breakConnection();
	}

	private void quitGame() throws IOException {
		client.sendQuit();
		quit = true;
	}

	private void printName() throws IOException {
		System.out.println(ANSI.YELLOW + "Your name: " + ANSI.BLUE + this.getName() + ANSI.RESET);
	}

	private String getName() throws IOException {
		return client.getName();
	}

	private String printButtons() {
		StringBuilder str = new StringBuilder();
		str.append(ANSI.RED + "| " + ANSI.RESET);
		for(int i = 0; i < this.buttons.length; i++) {
			str.append(ANSI.GREEN + this.buttons[i] + ANSI.RESET);
			str.append(ANSI.RED + " | " + ANSI.RESET);
		}
		return str.toString();
	}

	private String printError(String data) {
		return ANSI.RED + "[X] No such command exist! " + ANSI.RESET + data;
	}

	private void changeName() throws IOException {
		System.out.print(ANSI.YELLOW + "Input your's name: " + ANSI.BLUE);
		String name = (scan.nextLine()).trim();
		if(name.length() > 0) {
			String msg = this.client.sendName(name);
			String prefix = msg.substring(0,3);
			msg = msg.substring(3);
			if(prefix.equals("[x]")){
				System.out.println(ANSI.RED + prefix + ANSI.RESET + msg);
			} else {
				System.out.println(ANSI.GREEN + prefix + ANSI.RESET + msg);
			}
		}
	}

	private void createSession() throws IOException {
		CreateWaitingBreaker breaker = new CreateWaitingBreaker(this.scan, this.client);
		try {
			breaker.start();
			if(client.sendCreate()){
				breaker.disable();
				this.setX();
				this.game();
			} else {
				System.out.println(ANSI.RED + "[X] The game session was closed by client" + ANSI.RESET);
			}
		} finally {
			breaker.disable();
		}
	}

	private void setX() {
		this.myMoveTurn = true;
		this.mark = 'X';
	}

	private void connectToSession() throws IOException {
		if(this.printCreatorsList() == true) {
			System.out.print("Input " + ANSI.GREEN + "chanel name" + ANSI.RESET + " or input " + ANSI.RED + "break" + ANSI.RESET + " to stop: ");
			System.out.print(ANSI.BLUE);
			String name = scan.nextLine();
			System.out.print(ANSI.RESET);
			this.connection(name);
		}
	}

	private boolean printCreatorsList() throws IOException {
		String creators = client.getCreateList();
		if(creators.equals("[X]")) {
			System.out.println(ANSI.RED + "[X] No open sessions" + ANSI.RESET);
			return false;
		} else {
			System.out.println("Creators:\n" + ANSI.YELLOW + "##########\n\n" + ANSI.BLUE + creators);
			System.out.println(ANSI.YELLOW + "\n##########" + ANSI.RESET);
			return true;
		}
	}

	private void connection(String name) throws IOException {
		if(!name.equals("break")) {
			if(client.sendConnect(name)){
				this.setO();
				this.game();
			} else {
				System.out.println(ANSI.RED + "[X] No such name exist!" + ANSI.RESET);
			}
		}
	}

	private void setO() {
		this.myMoveTurn = false;
		this.mark = 'O';
	}

	private void game() throws IOException {
		try {
			this.flushScanner();
			System.out.println(view);
			this.inGame = true;
			while(this.inGame) {
				if(myMoveTurn == true) {
					this.doMove();
				} else { 
					this.enemyMove();
				}
				myMoveTurn = !myMoveTurn;
			}
		} finally {
			this.endSession();
		}
	}

	private void doMove() throws IOException {
		this.flushScanner();
		System.out.println(ANSI.YELLOW + "If you want stop the game, input " + ANSI.BLUE + "leave" + ANSI.RESET);
		System.out.println("You are: " + view.addColorToSymb(mark) + ANSI.GREEN + "\nInput coordinates" + ANSI.RESET + " or " + ANSI.YELLOW + "hint" + ANSI.RESET + ": ");
		while(true) {		
			try{
				if(scanMoveCommand()) 
					break;
			} catch(NumberFormatException nfe) {
				System.out.println(ANSI.RED + "[X] Invalid " + ANSI.GREEN + "coordinates" + ANSI.RED + " or " + ANSI.YELLOW + "hint" + ANSI.RED + " command" + ANSI.RESET);
			}
		}
	}

	private boolean scanMoveCommand() throws NumberFormatException, IOException {
		String data = scan.nextLine();
		switch(data){
			case "hint":
				return this.hint();
			case "leave":
				return this.leave();
			default:
				return this.move(data);
		}
	}

	private boolean hint() throws IOException {
		String answer = this.client.sendHint();
		String[] res = (answer).split(" ");
		return this.statusCheck(Integer.parseInt(res[0]), Integer.parseInt(res[1]), Integer.parseInt(res[2]));
	}

	private boolean leave() throws IOException {
		client.sendLeave();
		this.endSession();
		this.inGame = false;
		return true;
	}

	private boolean move(String crd) throws IOException, NumberFormatException {
		String[] coordinates = (crd).split(" ");
		if(coordinates.length != 2)
			return this.statusCheck(-1,-1,-1);
		int x = Integer.parseInt(coordinates[0]);
		int y = Integer.parseInt(coordinates[1]);
		x = x-1; y = y-1;
		int res = client.sendMove(x, y);
		return this.statusCheck(res, x, y);
	}

	private boolean statusCheck(int status, int x, int y) {
		switch(status) {
			case 1:
				this.inGame = false;
			case 0:
				view.doMove(x, y);
				System.out.println(view);
				return true;
			case -1:
				System.out.print(ANSI.RED + "[X] Error move or unexpected problems...\n" + ANSI.RESET + "Try again: ");
				return false;
			case -2:
				System.out.println(ANSI.RED + "[X] ENEMY LEAVE THE GAME!");
				this.inGame = false;
				return true;
		}
		return false;
	}

	private void enemyMove() throws IOException {
		LeaveWaitingBreaker breaker = new LeaveWaitingBreaker(this.scan, this.client);
		try {
			breaker.start();
			System.out.println(ANSI.PURPLE + "[!] Wait enemy move..." + ANSI.RESET);
			String answer = client.recieveData();
			if(!this.enemyExit(answer)) {
				String move = isEnd(answer);
				String[] crds = (move).split(" ");
				view.doMove(Integer.parseInt(crds[0]), Integer.parseInt(crds[1]));
				System.out.println(view);
			} else {
				System.out.println(ANSI.RED + "[X] Session was closed!" + ANSI.RESET);
				this.inGame = false;
			}
		} finally {
			breaker.disable();
		}
	}

	private boolean enemyExit(String answer) {
		return answer.equals("LEAVE");
	}

	private String isEnd(String answer) throws IOException {
		if(answer.equals("END")){
			this.inGame = false;
			answer = client.recieveData();			
		}
		return answer;
	}

	private void endSession() throws IOException {
		this.inGame = false;
		this.client.sendExit();
		this.view = new ConsoleView();
		this.flushScanner();
	}

	private void flushScanner() {
    	this.scan = new Scanner(System.in);
    }
}