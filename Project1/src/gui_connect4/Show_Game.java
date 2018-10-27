package gui_connect4;

/**
 * Connect4_Gui Show the game Class
 * @author Paul SAHOSSI
 */

public class Show_Game {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				
				Connect4_Gui gui = new Connect4_Gui();
			}
			
	});
		
		
		
	}

}
