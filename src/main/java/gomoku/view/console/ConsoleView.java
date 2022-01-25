package gomoku.view.console;

import java.util.Scanner;
import gomoku.view.ANSI;

public class ConsoleView {

	private static final int size = 15;
	private char[][] board;
	private boolean X = true;

	ConsoleView() {
		this.board = new char[size][size];
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				this.board[i][j] = ' ';
			}
		}
	}
	
	public void doMove(int x, int y){
		if( (x >= 0 && x < this.size) && (y >= 0 || y < this.size)) {
			if(this.X){
				board[x][y] = 'X';
			} else {
				board[x][y] = 'O';
			}
			this.X = !this.X;
		}
	}

	@Override
	public String toString(){
		String c = ANSI.CYAN + "|" + ANSI.RESET;
		String stick = ("   " + ANSI.CYAN + "-------------------------------------------------------------\n" + ANSI.RESET);
		StringBuilder str = new StringBuilder();
		str.append(ANSI.YELLOW + "     1   2   3   4   5   6   7   8   9  10  11  12  13  14  15" + ANSI.RESET + "\n");
		str.append(stick);
		for(int i = 0; i < this.size; i++){
			str.append(ANSI.YELLOW);
			if(i < 9){
				str.append(i+1 + "  " );
			} else {
				str.append(i+1 + " ");
			}
			str.append(ANSI.RESET);
			str.append(c + " ");
			for (int j = 0; j < this.size; j++) {
				str.append(this.addColorToSymb(this.board[i][j]));
				str.append(" " + c + " ");
			}
			str.append('\n');
			str.append(stick);
		}
		return str.toString();
	}

	public String addColorToSymb(char symb) {
		if(symb == 'X') {
			return ("" + ANSI.RED + symb + ANSI.RESET);
		} else {
			return ("" + ANSI.PURPLE + symb + ANSI.RESET);
		}
	}
}