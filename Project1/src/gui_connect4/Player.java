package gui_connect4;

/**
 * Connect4_Gui Player Class
 * @author Paul SAHOSSI
 */

import javax.swing.ImageIcon;

public class Player {

	private String playerName;
	private ImageIcon playerIcon;
	private int numGames;
	private int numWins;
	private int numLosses;

	public Player(){
		playerName = "PlayDoe";
		playerIcon = new ImageIcon("Empty");
		numGames = 0;
		numWins = 0;
		numLosses = 0;
	}
	public Player(String name, ImageIcon icon){
		numGames = 0;
		numWins = 0;
		numLosses = 0;
		playerName = name;
		playerIcon = icon;
	}

	public void addNumWins(){
		numWins++;
		numGames++;
	}
	public void addNumLosses(){
		numLosses++;
		numGames++;
	}
	public void addDraw(){
		numGames++;//not a win or a loss.. but a game was played
	}
	public int getNumWins(){
		return numWins;
	}

	public int getNumLosses(){
		return numLosses;
	}
	public int getNumGames(){
		return numGames;
	}
	
	public ImageIcon getPlayerIcon(){
		return playerIcon;
	}
	public String getName(){
		return playerName;
	}
	//think about whether you want to allow a setter for name and symbol
	
	public boolean equals(Object o){
		if(o instanceof Player){
			Player otherPlayer = (Player)o;
			if(this.playerName.equalsIgnoreCase(otherPlayer.playerName)){
				if(((ImageIcon) this.playerIcon).equals(otherPlayer.playerIcon)){
					if(this.numGames == otherPlayer.numGames){
						if(this.numLosses == otherPlayer.numLosses){
							if(this.numWins == otherPlayer.numWins){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	public String toString(){
		String s = playerName + " : " + numWins;
				
//				"Player [ name= " + playerName + ", " +
//				" wins= " + numWins + ", " + 
//				" losses= " + numLosses + ", " + 
//				" total games= " + numGames + " ]";
		return s;
	}
}