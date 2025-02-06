
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class ScoreBoard {
	
			JFrame 				scoreFrame				= new JFrame();
			FileHandler 		fileHandler 			= new FileHandler();
			String 				imageIconString 		= ".//res//scoretable.png";
			String 				readUserInfoFromFile 	= "src\\userScores.txt";
	static	ArrayList<String> 	userList 				= new ArrayList<String>();
	
	// method to show the score board frame
	public void showScoreBoard() {
		scoreFrame.setTitle("Score Table");
		scoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
		scoreFrame.setResizable(false);
		scoreFrame.setSize(350, 400);
		
		setScorePanel();
		
		fileHandler.setImageIcon(imageIconString, scoreFrame);
	}
	
	// method to set the list of the player results
	private void setScorePanel() {
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		fileHandler.userFileReader(readUserInfoFromFile, userList);
		if(userList.isEmpty()) {
			JLabel userLabel = new JLabel("No players found.");
			scorePanel.add(userLabel);
		}else {
			for(String i: userList) {
				JLabel userLabel = new JLabel(i);
				scorePanel.add(userLabel);
			}
		}
		scoreFrame.add(scorePanel);		
		scoreFrame.setVisible(true);
	}
}
