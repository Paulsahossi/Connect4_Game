package gui_connect4;

/**
 * Connect4_Gui Class
 * @author Paul SAHOSSI
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;




public class Connect4_Gui extends JFrame{
	private JPanel jpMain;
	private JLabel statusBar;
	Connect4Board jpBoard;
	ScoreBoard scoreBoard;
	private int numOfConnect;
	
	
	private Player currPlayer;
	private Player player1;
	private Player player2;
	private int [] Tracker = {5,5,5,5,5,5,5};
	private final ImageIcon yellowIcon =  new ImageIcon(getClass().getResource("Yellow.png"));

	
	public Connect4_Gui () {
		player1 = new Player ("Red", new ImageIcon(getClass().getResource("Red.jpg")));
		player2 = new Player ("Blue", new ImageIcon(getClass().getResource("Blue.png")));
		currPlayer = player1;
		
	    // Setup the status bar (JLabel) to display status message
	    statusBar = new JLabel();
	    statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 18));
	    statusBar.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
	    statusBar.setForeground(Color.DARK_GRAY);
	    statusBar.setHorizontalAlignment(SwingConstants.CENTER);
//	    statusBar.setBackground(Color.CYAN);
		
		scoreBoard = new ScoreBoard();
		jpMain = new JPanel();
		jpMain.setLayout (new BorderLayout());
		jpBoard = new Connect4Board();
		jpBoard.setBorder(null);
		jpMain.add(jpBoard);
		jpMain.add(statusBar, BorderLayout.PAGE_START); // same as SOUTH
		jpMain.add(scoreBoard, BorderLayout.SOUTH);
		
		

		add(jpMain);
		setTitle("'Connect 4'");
		setSize(450,550);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);	
	}
	

	private class ScoreBoard extends JPanel{
		private JLabel scoreR;
		private JLabel scoreB;

	
		public ScoreBoard() {
			player1.addDraw();
			scoreR = new JLabel("Red : 0");
			scoreR.setForeground(Color.RED);
		    scoreR.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 18));
		    scoreR.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
			scoreB = new JLabel("Blue : 0");
			scoreB.setForeground(Color.BLUE);
		    scoreB.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 18));
		    scoreB.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 10));
		    add(scoreR);
		    add(scoreB);
		}
	}

	private class Connect4Board extends JPanel implements GameBoardInterface, GamePlayerInterface, ActionListener {
		private JButton [] button; 
		private JLabel [][] board;
		private final int ROWS = 6;
		private final int COLS = 7;
//		private int numOfConnect;
		
		public Connect4Board() {
			setLayout(new GridLayout(ROWS +1, COLS));
			board = new JLabel[ROWS][COLS];
			button = new JButton[COLS];
			displayButtons();
			displayBoard();
			clearBoard();
			statusBar.setText("Red play first!");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try { 
				JButton btnClicked = (JButton) e.getSource();//find out which button was clicked

				for(int btnI=0; btnI <= board.length; btnI++) {
					if (btnClicked.equals(button [btnI])){
						int row = Tracker[btnI];
						board[row][btnI].setIcon(currPlayer.getPlayerIcon());
						Tracker[btnI]--;//decrement tracker at index							
					}
					
				}

				if(isWinner() == true){//check if currPlayer isWinner
					currPlayer.addNumWins();
					if(!player1.equals(currPlayer)) {
						player1.addNumLosses();
					}
					if(!player2.equals(currPlayer)) {
						player2.addNumLosses();
					}
					scoreBoard.scoreR.setText(player1.toString());
					scoreBoard.scoreB.setText(player2.toString());
					JOptionPane.showMessageDialog(null, "WINNER is "+ currPlayer);//display the currPlayer as winner
					int dialogResult = JOptionPane.showConfirmDialog (null, "Play again?", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(dialogResult == JOptionPane.YES_OPTION){//ask to play again yes/no
					  clearBoard();//clear board
					} else {
						System.exit(0);
					}
				}
				else if(isFull() == true){//check if full
					System.out.println(isFull());
					JOptionPane.showMessageDialog(null,"Game Over, No Winner" + "!" + " DRAW" + "!");
					int dialogResult = JOptionPane.showConfirmDialog (null, "Play again?", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(dialogResult == JOptionPane.YES_OPTION){
					  clearBoard();//clear board
					  
					} else {
						System.exit(0);
					}
				}
				takeTurn();	
			}
			catch(Exception ex){ 
                warningMessage();
            }
			
	
		}

		
		private void warningMessage() {
			JFrame frameWarning = new JFrame();           
	        JOptionPane.showMessageDialog(frameWarning,
	        "Invalid Movement !!\nThis column is not empty. Please try again", "Warning",
	        JOptionPane.WARNING_MESSAGE);
		}
		


		@Override
		public void displayButtons() {
			for(int col=0; col<= board.length; col++){
				button[col] = new JButton();
				button[col].setIcon(new ImageIcon(getClass().getResource("Green.jpg")));
				button[col].addActionListener(this);
				add(button[col]);
            	
			 	}
			}
			

		//displaying the top buttons. 
		@Override
		public void displayBoard() {
			for(int row=0; row<board.length; row++){
				for(int col=0; col<board[row].length; col++){
					board[row][col] = new JLabel();
					board[row][col].setBorder(new LineBorder(Color.GRAY));
					board[row][col].setHorizontalAlignment(SwingConstants.CENTER);
					board[row][col].setVerticalAlignment(SwingConstants.CENTER);
					add(board[row][col]);
				}
			}
		}

		@Override
		public void clearBoard() {
			for(int row=0; row<board.length; row++){
				for(int col=0; col<board[row].length; col++){
					board[row][col].setIcon(yellowIcon);//clearing the button 	
				}
			}
			for( int i = 0; i< Tracker.length; i++) {
				Tracker[i]=5;//Reseting the board
			}
//			numOfConnect = Integer.parseInt(JOptionPane.showInputDialog("How many Connect?"));
		}

		
		@Override
		public boolean isFull() {
			for(int row=0; row < board.length; row++){
				for(int col=0; col< board[row].length; col++){
					if(board[row][col].getIcon().equals(yellowIcon)){
						return false;//board has an empty slot... not full
						
					}
				}
			}
			
			return true;
			
		}
		
		@Override
		public void takeTurn() {
			
			if(currPlayer.equals(player1)){
				currPlayer = player2;
				statusBar.setText(currPlayer.getName() + " turn's");
			}
			else{
				currPlayer = player1;
				statusBar.setText(currPlayer.getName() + " turn's");
			}
		}
		

		@Override
		public boolean isWinner() {
			if (isWinnerInRow()) {
				return true;
			}
			if(isWinnerInCol()) {
				return true;
			}
			if (isWinnerInFirstUpDiag()) {
				return true;
			}
			if(isWinnerInSectUpDiag()) {
				return true;
			}
			

		return false;
		}
		
		public boolean isWinnerInRow(){
			for(int row=0; row < board.length; row++){
				int numMatchesInRow = 0; //reset on the next row
				for(int col=0; col< board[row].length; col++){
//					System.out.println(row +","+col);
					if( board[row][col].getIcon().equals(currPlayer.getPlayerIcon())){
						numMatchesInRow++;
					}
					else {
						numMatchesInRow = 0;
					}
					if(numMatchesInRow == 4){
					return true;
					}
				}
			}
			return false;
		}
		
		public boolean isWinnerInCol(){
			
			for(int col=0; col <= board.length; col++){
				int numMatchesInCol = 0; //reset on the next row
				for(int row=0; row < 6 ; row++){
//					System.out.println(row +","+col);
					if( board[row][col].getIcon().equals(currPlayer.getPlayerIcon())){
						numMatchesInCol++;
					}
					else {
						numMatchesInCol = 0;
					}
				
					if(numMatchesInCol == 4){
					return true;
				}
				}
			}
			return false;
		}
		
		
		public boolean isWinnerInFirstUpDiag(){
			
			
			for ( int startRow = 3; startRow < 6; startRow++){
				int row = startRow;
				int col = 0;
				int numMatchesInFirstUpDiag = 0;
				while ( row > -1 && col < 7){
//					System.out.println(row +","+col);
	
					if( board[row][col].getIcon().equals(currPlayer.getPlayerIcon())){
						numMatchesInFirstUpDiag++;
						if(numMatchesInFirstUpDiag == 4){
							return true;
						}
					}
					else {
						numMatchesInFirstUpDiag = 0;
					}
					row--;
					col++;
				}
			}
		
			return false;
		}
		
		
		public boolean isWinnerInSectUpDiag(){
			
			
			for ( int startRow = 1; startRow < 4; startRow++){
				int row = 5 ;
				int col = startRow;
				int numMatchesInSecUpDiag = 0;
				while ( row > -1 && col < 7){
//					System.out.println(row +","+col);
					if( board[row][col].getIcon().equals(currPlayer.getPlayerIcon())){
						numMatchesInSecUpDiag++;
						if(numMatchesInSecUpDiag == 4){
							return true;
						}
					}
					else {
						numMatchesInSecUpDiag = 0;
					}
					row--;
					col++;
				}
			}
		
			return false;

		}
	
		
	}
		
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

