package gomoku.tests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import gomoku.model.*;

public class GomokuTests {

	// public static int passed;
	// public static int total;
	// public static int field;

	// public static void showResult(){
	// 	if(field == 0){
	// 		System.out.println("Test is passed");
	// 	} else {
	// 		System.out.println("Test is field\nTotal: " + total + ". | " + field + " tests was field. " + passed + " tests was passed.");
	// 	}
	// }
	// public static void check(boolean res){
	// 	total++;
	// 	if(!res){
	// 		field++;
	// 		System.out.println(total + " -");
	// 	} else {
	// 		passed++;
	// 		System.out.println(total + " +");
	// 	}
	// }
	// public static void run(){
	// 	GomokuTests.moveTest1();
	// 	GomokuTests.moveTest2();
	// 	GomokuTests.moveTest3();
	// 	GomokuTests.playerChangeTest1();
	// 	GomokuTests.playerChangeTest2();
	// 	GomokuTests.checkWinTest1();
	// 	GomokuTests.checkWinTest2();
	// 	GomokuTests.checkWinTest3();
	// 	GomokuTests.checkWinTest4();
	// }
	// public static void main(String[] args){
		// System.out.println("keke");
		// GomokuTests.run();
		// GomokuTests.showResult();
	// }


	//-----------------------------//
	@Test
	public void moveTest1(){
		Gomoku game = new Gomoku(false);
		game.move(0, 0);
		assertEquals(game.getElement(0, 0),'X');
	}

	@Test
	public void moveTest2(){
		Gomoku game = new Gomoku(false);
		game.move(0, 0);
		game.move(0, 0);
		assertEquals(game.getCurPlayerChar(), 'O');
	}
	
	@Test
	public void moveTest3(){
		Gomoku game = new Gomoku(false);
		game.move(0, 0);
		game.undo();
		assertEquals(game.getElement(0, 0), ' ');
	}
	
	@Test
	public void playerChangeTest1(){
		Gomoku game = new Gomoku(false);
		assertEquals(game.getCurPlayerChar(), 'X');
	}

	@Test
	public void moveOutOfBoard1(){
		Gomoku game = new Gomoku(false);
		assertEquals(game.move(-1, -1), -1);
	}

	@Test
	public void moveOutOfBoard2(){
		Gomoku game = new Gomoku(false);
		assertEquals(game.move(16, 16), -1);
	}
	
	@Test
	public void playerChangeTest2(){
		Gomoku game = new Gomoku(false);
		game.move(0, 0);
		assertEquals(game.getCurPlayerChar(), 'O');
	}
	
	@Test
	public void checkWinTest1(){
		Gomoku game = new Gomoku(false);
		game.move(0, 0);
		game.move(0, 1);
		game.move(1, 1);
		game.move(1, 2);
		game.move(2, 2);
		game.move(2, 3);
		game.move(3, 3);
		game.move(3, 4);
		assertEquals(game.move(4, 4), 1);
	}
	
	@Test
	public void checkWinTest2(){
		Gomoku game = new Gomoku(false);
		game.move(14, 0);
		game.move(1, 2);
		game.move(13, 1);
		game.move(2, 3);
		game.move(12, 2);
		game.move(3, 4);
		game.move(11, 3);
		game.move(4, 5);
		assertEquals(game.move(10, 4), 1);
	}
	
	@Test
	public void checkWinTest3(){
		Gomoku game = new Gomoku(false);
		game.move(0, 0);
		game.move(1, 2);
		game.move(0, 1);
		game.move(12, 2);
		game.move(0, 2);
		game.move(3, 4);
		game.move(0, 3);
		game.move(4, 5);
		assertEquals(game.move(0, 4), 1);
	}
	
	@Test
	public void checkWinTest4(){
		Gomoku game = new Gomoku(false);
		game.move(0, 0);
		game.move(1, 2);
		game.move(1, 0);
		game.move(12, 2);
		game.move(2, 0);
		game.move(3, 4);
		game.move(3, 0);
		game.move(4, 5);
		assertEquals(game.move(4, 0), 1);
	}

}