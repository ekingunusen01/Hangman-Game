
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class UserInfoFrame implements ActionListener {
	
	private 	static 		JFrame 			frame;
	private 	static 		JTextField 		nameText, timeText, dateText, playedStringText, stringSizeText, elapsedTimeText;
				static 		String 			name, time, date, playedString, stringSize, elapsedTime, userInfo;
	private 	static 		JPanel 			panel;
	private 	static 		JLabel 			nameLabel 			= new JLabel("Name"), 
											timeLabel			= new JLabel("Time (hh:mm)"), 
											dateLabel			= new JLabel("Date (dd.mm.yyyy)"), 
											playedStringLabel	= new JLabel("Played String"), 
											stringSizeLabel		= new JLabel("Size of the String"), 
											elapsedTimeLabel	= new JLabel("Elapsed Time");
	private 	static 		JButton 		button				= new JButton("Submit");
							String 			imagePath 		= ".//res//infoimage.png";
							FileHandler 	fileHandler 		= new FileHandler();
							String 			writeUserInfoToFile = "src\\userScores.txt";
							long			elapsedTimeMillis	= HangmanGame.finalTime - HangmanGame.startTime;
							int				systemTime			= (int)(elapsedTimeMillis / 1000);
	
	// method to set and show the form window
	public void showForm() {
		panel = new JPanel();
		panel.setLayout(null);
		frame = new JFrame();
		frame.setTitle("Enter User Info");
		frame.setSize(300,330);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.add(panel);
		
		setNameLabel();
		setTimeLabel();
		setDateLabel();
		setPlayedStringLabel();
		setStringSizeLabel();
		setElapsedTimeLabel();
		setButton();
		
		frame.setVisible(true);
		fileHandler.setImageIcon(imagePath, frame);
	}
	
	// method to set the name section of the form 
	private void setNameLabel() {
		nameLabel.setBounds(10, 20, 100, 25);
		panel.add(nameLabel);
		nameText = new JTextField(40);
		nameText.setBounds(150, 20, 125, 20);
		panel.add(nameText);
	}
	
	// method to set the time section of the form
	private void setTimeLabel() {
		timeLabel.setBounds(10, 60, 100, 25);
		panel.add(timeLabel);
		timeText = new JTextField(40);
		timeText.setBounds(150, 60, 125, 20);
		timeText.setText(TimeHandler.setTime());
		panel.add(timeText);
	}
	
	// method to set the date section of the form
	private void setDateLabel() {
		dateLabel.setBounds(10, 100, 100, 25);
		panel.add(dateLabel);
		dateText = new JTextField(40);
		dateText.setBounds(150, 100, 125, 20);
		dateText.setText(TimeHandler.setDate());
		panel.add(dateText);
	}
	
	// method to set the hidden word section of the form
	private void setPlayedStringLabel() {
		playedStringLabel.setBounds(10, 140, 100, 25);
		panel.add(playedStringLabel);
		playedStringText = new JTextField(40);
		playedStringText.setBounds(150, 140, 125, 20);
		
		// below line makes the 1st index of the string to upper case, rest lower case
		String result = HangmanGame.hiddenWord.substring(0, 1).toUpperCase() + HangmanGame.hiddenWord.substring(1).toLowerCase();
		playedStringText.setText(result);
		panel.add(playedStringText);
	}
	
	// method to set the hidden string's size section of the form
	private void setStringSizeLabel() {
		stringSizeLabel.setBounds(10, 180, 100, 25);
		panel.add(stringSizeLabel);
		stringSizeText = new JTextField(40);
		stringSizeText.setBounds(150, 180, 125, 20);
		stringSizeText.setText(Integer.toString(HangmanGame.hiddenWordLength));
		panel.add(stringSizeText);
	}
	
	// method to set the elapsed time section of the form
	private void setElapsedTimeLabel() {
		elapsedTimeLabel.setBounds(10, 220, 100, 25);
		panel.add(elapsedTimeLabel);
		elapsedTimeText = new JTextField(40);
		elapsedTimeText.setBounds(150, 220, 125, 20);
		elapsedTimeText.setText(Integer.toString(systemTime));
		panel.add(elapsedTimeText);
	}
	
	// method to set the submit button
	private void setButton() {
		button.setBounds(90, 260, 100, 20);
		if(button.getActionListeners().length == 0) {
			button.addActionListener(new UserInfoFrame());
		}
		panel.add(button);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == button) {
			name = nameText.getText();
			time = timeText.getText();
			date = dateText.getText();
			playedString = playedStringText.getText();
			stringSize = stringSizeText.getText();
			elapsedTime = elapsedTimeText.getText();
			
			if (name.isEmpty() || time.isEmpty() || date.isEmpty() || 
				playedString.isEmpty() || stringSize.isEmpty() || elapsedTime.isEmpty()) {
				JOptionPane.showMessageDialog(null, "All text spaces in the form must be filled.", "Error", JOptionPane.ERROR_MESSAGE, null);
			}else {
				try {
					userInfo = name + ',' + time + ',' + date + ',' + playedString + ',' + stringSize + ',' + elapsedTime;
					fileHandler.writeToFile(writeUserInfoToFile, userInfo);
					frame.dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}	


