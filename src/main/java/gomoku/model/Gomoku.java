package gomoku.model;
import gomoku.history.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.io.IOException;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Iterator;

public class Gomoku implements Serializable{
	
	private static final int size = 15;
	private static final int countForWin = 5;
	private boolean drawFlag;
	private char[][] board;
	private boolean player;
	private History history;
	private boolean aiFlag;
	private GomokuAI ai;
	
	private class GomokuAI implements Serializable{
		private HashSet<ListElement> posMove;
		
		public GomokuAI(){
			this.posMove = new HashSet<ListElement>();
			this.loadHistory();
		}
		
		public void loadHistory(){
			for(Element el : getHistory().getList()){
				this.addPossibleMoves(new Coordinates(el.getX(), el.getY()));
			}
			this.checkPossibleMoves();
		}

		public Coordinates findMove(){
			this.checkAndAddPossibleMoves();
			ArrayList<ListElement> maxCostMoves = maxCostList(this.posMove.iterator());
			ListElement element = maxCostMoves.get((int)(Math.random() * maxCostMoves.size()));
			addPossibleMoves(element.getCoordinates());
			return element.getCoordinates();
		}

		private void checkAndAddPossibleMoves() {
			Coordinates lastMove = this.getLastMove();	
			this.checkPossibleMoves();
			this.addPossibleMoves(lastMove);
		}

		private Coordinates getLastMove() {
			if(getHistory().getSize() > 0){
				return new Coordinates(getHistory().getLastX(), getHistory().getLastY());
			} else {
				return new Coordinates(8, 8);
			}
		}

		private ArrayList<ListElement> maxCostList(Iterator<ListElement> iter) {
			int maxSum = this.calculateSUM(this.posMove.iterator());
			ArrayList<ListElement> maxCostMoves = new ArrayList<ListElement>();
			while(iter.hasNext()){
				ListElement element = iter.next();
				if(element.getSum() == maxSum){
					maxCostMoves.add(element);
				}
			}
			return maxCostMoves;
		}

		private int calculateSUM(Iterator<ListElement> iter) {
			int maxSum = 0;
			while(iter.hasNext()){
				ListElement element = iter.next();
				int posSum = this.calculateMaxCost(element.getX(), element.getY());
				element.setSum(posSum);
				// System.out.println(element + ", sum: " + element.getSum());
				if(posSum > maxSum)
					maxSum = posSum;
			}
			return maxSum;
		}

		private void addPossibleMoves(Coordinates lastMove){
			for(int x = lastMove.getX()-1; x <= lastMove.getX()+1; x++){
				if(x >= 0 && x < getSize()){
					for(int y = lastMove.getY()-1; y <= lastMove.getY()+1; y++){
						if(y >= 0 && y < getSize()){
							if(getElement(x, y) == ' '){
								this.posMove.add(new ListElement(x, y));
							}
						}
					}
				}
			}
		}
		private void checkPossibleMoves(){
			Iterator<ListElement> iter = this.posMove.iterator();
			while(iter.hasNext()){
				ListElement element = iter.next();
				if(getElement(element.getX(), element.getY()) != ' '){
					iter.remove();
				}
			}
		}
		private int calculateMaxCost(int x, int y){
			int sum = 0;
			for(int i = 0; i < 4; i++){
				String line = this.getMoveLine(x, y, i);
				if(line.length() < 5)
					continue;
				// System.out.println("LINE: " + line);
				for(int strategy = 0; strategy < 2; strategy++){ // 0 - attack; 1 - defence
					char stratChar = getStrategyChar(strategy);
					int curSum = startegyCost(line, stratChar);
					curSum = addIfAttack(curSum, strategy);
					sum += curSum;
				}
			}
			return sum;
		}

		private int addIfAttack(int cost, int strategy) {
			if(strategy == 0)
				cost += cost * 1.1;
			return cost;
		}

		private int startegyCost(String line, char stratChar) {
			int curSum = 0;
			for(int k = 0; k < PatternList.size; k++){
				try{
					// System.out.println(line + " : " + PatternList.getPattern(k, stratChar).getPattern());
					curSum += compareWithPattern(line, PatternList.getPattern(k, stratChar), stratChar);
				} catch (IOException e) {
					System.out.println(e.getMessage());
					return 0;
				}
			}
			return curSum;
		}

		private char getStrategyChar(int strategy) {
			if(strategy == 0)
				return getCurPlayerChar();
			else
				return getEmenyPlayerChar();
		}

		private String getMoveLine(int x, int y, int i) {
			String line = "";
			for(int j = -4; j <= 4; j++){
				switch(i){
					case(0):
						if(x + j >= 0 && x + j < getSize())
							line += (j == 0) ? '*' : getElement(x + j, y);
						break;
					case(1):
						if(y + j >= 0 && y + j < getSize())
							line += (j == 0) ? '*' : getElement(x, y + j);
						break;
					case(2):
						if(x + j >= 0 && x + j < getSize())
							if(y + j >= 0 && y + j < getSize())
								line += (j == 0) ? '*' : getElement(x + j, y + j);
						break;
					case(3):
						if(x - j >= 0 && x - j < getSize())
							if(y + j >= 0 && y + j < getSize())
								line += (j == 0) ? '*' : getElement(x - j, y + j);
						break;
				}
			}
			return line;
		}

		private int compareWithPattern(String s, Pattern pattern, char stratChar){
			
			// System.out.println("pattern length: " + pattern.getPattern().length() + " | line length: " + s.length());
			if(pattern.getPattern().length() > s.length())
				return 0;

			int sum = 0;
			int offset = getAstrixIndex(s);
			s = changeAstrixToStrat(s, stratChar);
			int patternStartPos = offset - pattern.getPattern().length()-1;
			// System.out.println("Line: " + s);
			for(;patternStartPos <= offset; patternStartPos++) {
				if(patternStartPos < 0){
					continue;
				} else if(patternStartPos + pattern.getPattern().length() > s.length()){
					break;
				} else {
					if(pattern.getPattern().regionMatches(0, s, patternStartPos, pattern.getPattern().length())){
						sum += pattern.getSum();
					} else {
						continue;
					}
				}
			}
			return sum;
		}
		private int getAstrixIndex(String line){
			int i = 0;
			for(char c : line.toCharArray()){
				if(c == '*'){
					return i;
				}
				i++;
			}
			return i;
		}
		private String changeAstrixToStrat(String line, char stratChar){
			String newLine = "";
			for(char c : line.toCharArray()){
				if(c == '*'){
					c = stratChar;
				}
				newLine += c;
			}
			return newLine;
		}
	}

	public Gomoku(boolean aiFlag){
		history = new History();

		this.board = new char[size][size];
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				this.board[i][j] = ' ';
			}
		}
		this.aiFlag = aiFlag;
		// if(this.aiFlag){
			ai = new GomokuAI();
		// }
		this.player = true;
		this.drawFlag = false;
	}
	public boolean getAIflag(){
		return this.aiFlag;
	}
	public int aiMove(){
		// if(aiFlag){
			Coordinates crd = ai.findMove();
			// System.out.print("AI ");
			return this.move(crd);
		// }
		// return 0;
	}
	public Coordinates aiCoordinates() {
		return ai.findMove();
	}
	public int move(Coordinates xy){
		return this.move(xy.getX(), xy.getY());
	}
	public int move(int x, int y){
		if(!this.checkMove(x, y)){
			return -1;
		}
		history.addMove(x, y, this.changeBoard(x, y, player));
		
		if(this.checkWin())
			return 1;
		if(history.getSize() == size*size){
			this.itIsDraw();
			return 1;
		}
		this.changePlayer();
		return 0;
	}
	private char changeBoard(int x, int y, boolean player){
		if(player == true){
			this.board[x][y] = 'X';
			return 'X';
		}
		else{
			this.board[x][y] = 'O';
			return 'O';
		}
	}
	private boolean checkMove(int x, int y){
		if(x < 0 || x >= size && y < 0 || y >= size)
			return false;
		if(this.board[x][y] != ' ')
			return false;
		return true;
	}
	public void undo(){
		this.undoMove();
		if(aiFlag){
			this.undoMove();
		}
	}
	private void undoMove(){
		if(history.getSize() != 0){
			Element el = history.getLast();
			this.board[el.getX()][el.getY()] = ' ';
			char c = el.getPlayer();
			if(c == 'X'){
				this.player = true;
			} else {
				this.player = false;
			}
			history.deleteMove();
		}
	}
	private boolean checkWin(){
		char c = getCurPlayerChar();

		int counter = 0;

		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				if(c == this.board[i][j])
					counter++;
				else
					counter = 0;
				if(counter == this.countForWin)
					return true;
			}
			counter = 0;
		}

		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(c == this.board[j][i])
					counter++;
				else 
					counter = 0;
				if(counter == this.countForWin)
					return true;
			}
			counter = 0;
		}

		for(int length = this.countForWin-1; length < size; length++){
			for(int j = 0, i = length; j <= length; j++, i--){
				if(c == this.board[i][j])
					counter++;
				else 
					counter = 0;
				if(counter == this.countForWin)
					return true;
			}
			counter = 0;
		}

		for (int length = size, start = 0; length > this.countForWin-1; length--, start++){
			for(int j = 0, i = start; j < length; j++, i++){
				if(c == this.board[i][j])
					counter++;
				else
					counter = 0;
				if(counter == this.countForWin)
					return true;
			}
			counter = 0;
		}

		for (int length = size - 1, start = 1; length > this.countForWin-1; length--, start++) {
			for(int j = start, i = size - 1, k = 0; k < length; j++, k++){
				if(c == this.board[i-k][j])
					counter++;
				else 
					counter = 0;
				if(counter == this.countForWin)
					return true;
			}
			counter = 0;
		}
		for (int length = size - 1, start = 1; length > this.countForWin-1; length--, start++) {
			for(int j = start, i = 0, k = 0; k < length; j++, k++){
				if(c == this.board[i+k][j])
					counter++;
				else 
					counter = 0;
				if(counter == this.countForWin)
					return true;
			}
			counter = 0;
		}

		return false;
	}
	public History getHistory(){
		return history;
	}
	public boolean checkDraw(){
		return this.drawFlag;
	}
	public void itIsDraw(){
		this.drawFlag = true;
	}
	public String getWinner(){
		if(this.checkDraw()) return "         Draw";
		else if(this.player == true) return "Player X is winner";
		else return "PLayer O is winner";
	}
	public int getSize(){ return this.size; }
	public char getElement(int x, int y){
		return this.board[x][y];
	}
	public boolean getPlayer(){
		return this.player;
	}
	public char getCurPlayerChar(){
		if(this.player == true)
			return 'X';
		else 
			return 'O';
	}
	public char getEmenyPlayerChar(){
		if(this.player == true)
			return 'O';
		else 
			return 'X';
	}
	private void changePlayer(){
		this.player = !this.player;
	}
}