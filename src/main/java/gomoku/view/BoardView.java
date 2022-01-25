// package gomoku.view;
// import gomoku.history.*;
// import gomoku.saves.*;
// import gomoku.model.*;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.io.IOException;

// public class BoardView extends JFrame{
	
// 	private static final int panelButtonNum = 5;
// 	public static final boolean ai = true;
// 	BoardPanel bp;

// 	BoardView(String title){
// 		super(title);
// 		this.creating(new Gomoku(!ai));
// 	}
// 	BoardView(String title, Gomoku game){
// 		super(title);
// 		this.creating(game);
// 	}
// 	BoardView(String title, boolean artIntel){
// 		super(title);
// 		this.creating(new Gomoku(artIntel));
// 	}
// 	public void creating(Gomoku game){
// 		this.getContentPane().setBackground( new Color(26,24,24) );
// 		bp = new BoardPanel(game, this);
        
//         Container c = getContentPane();
//         bp.setBounds(0, 50, 1000, 950);

// 		JButton[] panelButton = new JButton[panelButtonNum];
	
// 		String[] panelButtonName = new String[]{"Hint", "Undo", "Save", "History", "Quit to Menu"};

// 		for(int i = 0, step = 160, x = 100; i < panelButtonNum; i++, x += step){
// 			panelButton[i] = new JButton(panelButtonName[i]);
// 			panelButton[i].setBounds(x, 10, step-4, 35);
// 			panelButton[i].setBackground(new Color(170, 169, 158));
// 			panelButton[i].setForeground(new Color(0,0,0));
// 			c.add(panelButton[i], BorderLayout.NORTH);
// 		}
// 		panelButton[0].addActionListener(new HintActListener(bp));
// 		panelButton[1].addActionListener(new UndoActListener(bp));
// 		panelButton[2].addActionListener(new SaveActListener(bp));
// 		panelButton[3].addActionListener(new HistoryActListener(bp));
// 		panelButton[4].addActionListener(new QuitActListener(this));

		
//         c.add(bp, BorderLayout.CENTER);

// 		this.setSize(1000, 1000);
// 		this.setLayout(new BorderLayout());
// 		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 		this.setResizable(false);
// 		this.setVisible(true);
// 	}

// 	public static class QuitActListener implements ActionListener{
// 		JFrame forClosing;
// 		QuitActListener(JFrame e){ forClosing = e;}
// 		@Override
// 		public void actionPerformed(ActionEvent e){
// 			this.forClosing.dispose();
// 			System.out.println("Go to Main Menu...");
// 			new MainMenu("Gomoku");
// 		}
// 	}
// 	public static class HistoryActListener implements ActionListener{
// 		BoardPanel forTakeHist;
// 		HistoryActListener(BoardPanel e){ forTakeHist = e;}
// 		@Override
// 		public void actionPerformed(ActionEvent e){
// 			System.out.println(this.forTakeHist.getGame().getHistory());
// 			new HistoryMenu("History", forTakeHist);
// 		}
// 	}
// 	public static class UndoActListener implements ActionListener{
// 		BoardPanel forUndoMove;
// 		UndoActListener(BoardPanel e){ forUndoMove = e;}
// 		@Override
// 		public void actionPerformed(ActionEvent e){
// 			this.forUndoMove.getGame().undo();
// 			this.forUndoMove.repaint();
// 			System.out.println("Undo.");
// 		}
// 	}
// 	public static class HintActListener implements ActionListener{
// 		BoardPanel forUndoMove;
// 		HintActListener(BoardPanel e){ forUndoMove = e;}
// 		@Override
// 		public void actionPerformed(ActionEvent e){
// 			this.forUndoMove.autoMove();
// 			this.forUndoMove.repaint();
// 			System.out.println("Hint.");
// 		}
// 	}
// 	public static class SaveActListener implements ActionListener{
// 		BoardPanel forSaveGame;
// 		SaveActListener(BoardPanel e){ forSaveGame = e;}
// 		@Override
// 		public void actionPerformed(ActionEvent e){
// 			System.out.println("Open Save Menu");
// 			new SaveMenu("Save File Menu", forSaveGame);
// 			System.out.println("Done!");
// 		}
// 	}
// }