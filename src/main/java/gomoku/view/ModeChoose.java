// package gomoku.view;
// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;

// public class ModeChoose extends JFrame {
// 	private static final int size = 3;

// 	public ModeChoose( String title){
// 		super(title);
// 		this.creating();
// 	}

// 	public void creating(){
// 		this.getContentPane().setBackground( new Color(26,24,24) );
// 		JButton[] mchButton = new JButton[size];

// 		mchButton[0] = new JButton("Single Player");
// 		mchButton[1] = new JButton("Two Players");
// 		mchButton[2] = new JButton("Back");

// 		mchButton[0].addActionListener(new SinglePlayerActListener(this));
// 		mchButton[1].addActionListener(new TwoPlayerActListener(this));
// 		mchButton[2].addActionListener(new BackActListener(this));

// 		for(int i = 0, j = 100, step = 150; i < size; i++, j += step ){
// 			mchButton[i].setBounds(100, j, 400, 50);
// 			mchButton[i].setForeground(new Color(0,0,0));
// 			mchButton[i].setBackground(new Color(170, 169, 158));
// 		}
// 		for(int i = 0; i < size; i++){
// 			this.add(mchButton[i]);
// 		}
// 		this.setSize(600, 600);
// 		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 		this.setLayout(null);
// 		this.setResizable(false);
// 		this.setVisible(true);
// 	}

// 	public static class BackActListener implements ActionListener{
// 		JFrame forClosing;
// 		BackActListener(JFrame e){ forClosing = e;}
// 		@Override
// 		public void actionPerformed(ActionEvent e){
// 			this.forClosing.dispose();
// 			System.out.println("Go to Main Menu...");
// 			new MainMenu("Gomoku");
// 		}
// 	}

// 	public static class TwoPlayerActListener implements ActionListener{
// 		JFrame forClosing;
// 		TwoPlayerActListener(JFrame e){ forClosing = e;}
// 		@Override
// 		public void actionPerformed(ActionEvent e){
// 			this.forClosing.dispose();
// 			System.out.println("New Game with 2 players");
// 			new BoardView("Gomoku Game Board");
// 		}
// 	}
// 	public static class SinglePlayerActListener implements ActionListener{
// 		JFrame forClosing;
// 		SinglePlayerActListener(JFrame e){ forClosing = e;}
// 		@Override
// 		public void actionPerformed(ActionEvent e){
// 			this.forClosing.dispose();
// 			System.out.println("New Single Player Game");
// 			new BoardView("Gomoku Game Board", true);
// 		}
// 	}
// }