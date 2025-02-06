
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class FileHandler {
	
	// method to handle the input file of the game
	public void wordFileReader(String fileName, ArrayList<String> wordList, ArrayList<String> tipList, 
						   ArrayList<String> notAvailableList, ArrayList<String> countdownList) {
		String readLine = "";
		try {
			try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
				while((readLine = reader.readLine()) != null) {
					String[] row = readLine.split(",");	
					tipList.add(row[0]);
					notAvailableList.add(row[1]);
					wordList.add(row[2]);
					countdownList.add(row[3]);
				}
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// method that will read the file which contains the information filled by the users at the end of the game
	public void userFileReader(String fileName, ArrayList<String> users) {
		String readLine = "";
		try {
			try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
				while((readLine = reader.readLine()) != null) {
					String[] row = readLine.split(",");	
					users.add(row[0] + ' ' + row[1] + ' ' + row[2] + ' ' + row[3] + ' ' + row[4] + ' ' + row[5]);
				}
			}
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// method to write to a given file
	public void writeToFile(String fileName, String userInfo) throws IOException{	
		
		// with the "true" parameter below, FileWriter enabled to write to the new line.
		try (FileWriter fileWriter = new FileWriter(fileName, true); 
			 PrintWriter users = new PrintWriter(fileWriter)){
			users.println(userInfo);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
	}
	
	// method to set frame images
	public void setImageIcon(String imageString, JFrame frame) {
		ImageIcon iconImage = new ImageIcon(imageString);		
		frame.setIconImage(iconImage.getImage());
	}
	
	// method to handle the "figure" image 
	public void setImage(String imageString, JPanel panel) {
		ImageIcon imageIcon = new ImageIcon(imageString);
		JLabel imageLabel = new JLabel(imageIcon);
		panel.add(imageLabel, BorderLayout.CENTER);
	}
	
	// method to process the sound file from the given file path and prepare it for the use
	public void handleSoundFile(String soundFile) {
		try {
			File file = new File(soundFile);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		}catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
