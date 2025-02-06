
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GUI {
	
	static 	JPanel 			infoPanel 		= new JPanel();
	static  JPanel 			stringPanel 	= new JPanel();
	static 	JPanel 			keyboardPanel 	= new JPanel();
	static  JPanel 			dudePanel 		= new JPanel();
	static	FileHandler 	fileHandler 	= new FileHandler();
	static 	JLabel 			tiplabel	 	= new JLabel();
	static 	JLabel 			notinstringlabel= new JLabel();
	static	JLabel 			statuslabel 	= new JLabel();
	static 	JLabel 			countdownLabel 	= new JLabel();
	static 	String 			imageString 	= ".//res//hangmansaydamdegil.png";
	static	String 			image1 			= ".//res//broImage1.png";
	static	String 			image2 			= ".//res//broImage2.png";
	static	String 			image3 			= ".//res//broImage3.png";
	static	String 			image4 			= ".//res//broImage4.png";
	static	String 			image5 			= ".//res//broImage5.png";
	static	String 			image6 			= ".//res//broImage6.png";
	static	String 			image0 			= ".//res//broImageFull.png";
	static 	String 			soundFile		= ".//res//Mouse_Click_Sound_Effect.wav";
	static 	HangmanGame 	game 			= new HangmanGame();
	static 	Timer 			timer;
	
	// method to initialize the keyboard
	public static void initializeKeyboard() {
		keyboardPanel.removeAll();
		String[] keyboardCharacters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
		 		  					   "J", "K", "L", "M", "N", "O", "P", "Q", "R",
		 		  					   "S", "T", "U", "V", "W", "X", "Y", "Z"};
		
		for(String letter: keyboardCharacters) {
			JButton button = new JButton(letter);
			button.setEnabled(HangmanGame.isGamePlayingFlag == 1);
			button.addActionListener(e -> {
				game.checkLetters(letter.charAt(0));
				fileHandler.handleSoundFile(soundFile);
			});
			keyboardPanel.add(button);
		}
	}
	
	// main method to initialize the frame and the game
	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setTitle("Hangman Game");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setSize(750,425);
		frame.setResizable(false);
		
		setMenuBar(frame);
		setInfoPanel(frame);
		setStringPanel(frame);
		setKeyboardPanel(frame);
		setFigureImagePanel(frame);
		
		frame.setVisible(true);													
		frame.getContentPane();
		
		fileHandler.setImageIcon(imageString, frame);
	}
	
	// method to set the menu elements in the frame
	private static void setMenuBar(JFrame frame) {

		JMenuBar 	menuBar 	= new JMenuBar	();
		JMenu 		fileMenu 	= new JMenu		("File");				
		JMenu 		helpMenu 	= new JMenu		("Help");
		JMenuItem 	newGame 	= new JMenuItem	("New Game");
		JMenuItem 	resetGame 	= new JMenuItem	("Reset Game");
		JMenuItem 	scoreTable 	= new JMenuItem	("Score Table");
		JMenuItem 	quit 		= new JMenuItem	("Quit");
		JMenuItem 	about 		= new JMenuItem	("About");
		
		menuBar.add(fileMenu);							
		menuBar.add(helpMenu);
		frame.setJMenuBar(menuBar);	
		
		KeyStroke ctrlqkeystroke = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK);
		quit.setAccelerator(ctrlqkeystroke);
		
		newGame.addActionListener(new ActionListener() {						
			@Override															
			public void actionPerformed(ActionEvent e) {
				HangmanGame newGame = new HangmanGame();
				newGame.setupGame();
				updateTipLabel(tiplabel);
				updateNotInStringLabel(notinstringlabel);
			}
		});
		// if user clicks the Reset Game menu item, user can play with the same word but mistake count and count down clock will set to start state
		resetGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(HangmanGame.isGamePlayingFlag == 0) {
					JOptionPane.showMessageDialog(null, "There is no game in progress to reset", null, JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Resetting the game...", null, JOptionPane.INFORMATION_MESSAGE);
					game.resetGame();
				}
			}
		});
		// user can display other users in-game performance as a list without ranking
		scoreTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScoreBoard scoreboard = new ScoreBoard();
				scoreboard.showScoreBoard();
			}
		});
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "You are quitting...", null, JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		});
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Name and Surname: Ekin Günüþen\n"
						+ "Contact email: ekin.gunusen@std.yeditepe.edu.tr\n", "About the Developer", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		
		fileMenu.add(newGame);										
		fileMenu.add(resetGame);	
		fileMenu.add(scoreTable);
		fileMenu.add(quit);
		helpMenu.add(about);
	}
	
	// method to set the info panel (which includes tip, not-in-string, status, count down sections) of the frame
	private static void setInfoPanel(JFrame frame) {
		infoPanel.setLayout(null);
		infoPanel.setBounds(0, 0, 450, 180);
		
		setTipLabel();
		setNotInStringLabel();
		setStatusLabel();
		setCountdownLabel();
		
		frame.add(infoPanel);
	}
	
	// method to set the hidden string panel of the frame
	private static void setStringPanel(JFrame frame) {
		stringPanel.setBounds(0, 180, 450, 60);
		for(int i = 0; i < 10; i++) {
			JTextField textField = new JTextField(2);
			textField.setHorizontalAlignment(JTextField.CENTER);
			textField.setFocusable(false);
			stringPanel.add(textField);
		}
		frame.add(stringPanel);
	}
	
	// method to display the initialized keyboard to the frame
	private static void setKeyboardPanel(JFrame frame) {
		keyboardPanel.setBounds(0, 240, 450, 115);
		initializeKeyboard();
		frame.add(keyboardPanel);
	}
	
	// method to display the figure image of the frame
	private static void setFigureImagePanel(JFrame frame) {
		dudePanel.setLayout(new BorderLayout());
		dudePanel.setBounds(450, 0, 300, 375);
		fileHandler.setImage(image0, dudePanel);
		frame.add(dudePanel);
	}
	
	// method to display the hidden word's tip to the panel
	private static void setTipLabel() {
		tiplabel.setText("Tip: ");
		tiplabel.setFont(new Font("Arial", Font.BOLD, 16));
		tiplabel.setBounds(10, 20, 440, 40);
		infoPanel.add(tiplabel);
	}
	
	// method to display the hidden word's not-in-string letters to the panel
	private static void setNotInStringLabel() {
		notinstringlabel.setText("Not-in-String: ");
		notinstringlabel.setFont(new Font("Arial", Font.BOLD, 16));
		notinstringlabel.setBounds(10, 90, 300, 30);
		infoPanel.add(notinstringlabel);
	}
	
	// method to display the status of the game to the panel
	private static void setStatusLabel() {
		updateStatusText(statuslabel, HangmanGame.statusFlag);
		statuslabel.setFont(new Font("Arial", Font.BOLD, 16));
		statuslabel.setBounds(10, 140, 150, 30);
		infoPanel.add(statuslabel);
	}
	
	// method to display the count down time for the word to the panel
	public static void setCountdownLabel() {
		countdownLabel.setText("Countdown: " + HangmanGame.countdownTime);
		countdownLabel.setFont(new Font("Arial", Font.BOLD, 16));
		countdownLabel.setBounds(250, 140, 150, 30);
		infoPanel.add(countdownLabel);
		
		// to get a proper result, timer will stopped if there is any.
		if(timer != null) {
			timer.stop();
		}
		//				1000ms = 1s
		timer = new Timer(1000, new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	// if game is active, timer method runs
	        	if(HangmanGame.isGamePlayingFlag == 1) {
	        		if (HangmanGame.countdownTime > 0) {
	        			HangmanGame.countdownTime--;
		                countdownLabel.setText("Countdown: " + HangmanGame.countdownTime);
		            } else {
		                ((Timer) e.getSource()).stop(); 
		                HangmanGame.statusFlag = 1; 
		                HangmanGame.isGamePlayingFlag = 0;
		                updateStatusText(statuslabel, HangmanGame.statusFlag);
		                game.endGame();
		            }
	        	}
	        }
	    });
		countdownLabel.setVisible(true);
		timer.setInitialDelay(0);	// timer starts immediately without any delay
		timer.start();
	}
	
	// method to update the status label for the status of the game
	public static void updateStatusText(JLabel label, int flag) {
		String statusText = "Status:";
		if(flag == 0) {
			label.setText("<html>" + statusText + "<font color='blue'> In Progress</font></html>");
		}else if(flag == 1) {
			label.setText("<html>" + statusText + "<font color='red'> You LOSE</font></html>");
		}else if(flag == 2) {
			label.setText("<html>" + statusText + "<font color='green'> You WON</font></html>");
		}
	}
	
	// method to update the image as the user makes mistakes
	public static void setImageWithCount(JPanel panel, int wrongCount) {
		panel.removeAll();
		if(wrongCount == 6) {
			fileHandler.setImage(image1, panel);
		}else if(wrongCount == 5) {
			fileHandler.setImage(image2, panel);
		}else if(wrongCount == 4) {
			fileHandler.setImage(image3, panel);
		}else if(wrongCount == 3) {
			fileHandler.setImage(image4, panel);
		}else if(wrongCount == 2) {
			fileHandler.setImage(image5, panel);
		}else if(wrongCount == 1) {
			fileHandler.setImage(image6, panel);
		}else if(wrongCount == 0) {
			fileHandler.setImage(image0, panel);
		}
		panel.revalidate();
		panel.repaint();
	}
	
	// method to update the hidden word panel as user makes correct guesses
	public static void updateStringsPanel(char guessedLetter) {
		stringPanel.removeAll();
		stringPanel.revalidate();
		stringPanel.repaint();
		
		for(int i = 0; i < HangmanGame.hiddenWordLength; i++) {
			if(HangmanGame.hiddenWord.charAt(i) == guessedLetter) {
				HangmanGame.guessedLetters[i] = guessedLetter;
			}
		}
		for(int i = 0; i < HangmanGame.hiddenWordLength; i++) {
			JTextField textField = new JTextField(2);
			textField.setHorizontalAlignment(JTextField.CENTER);
			textField.setFocusable(false);
			if (HangmanGame.guessedLetters[i] != '_') {
	            textField.setText(String.valueOf(HangmanGame.guessedLetters[i]));
	        }
			stringPanel.add(textField);
		}
		stringPanel.revalidate();
		stringPanel.repaint();
	}
	
	// method to update tip label on the panel
	private static void updateTipLabel(JLabel label) {
		String tip = HangmanGame.hiddenWordTip;
		label.setText("<html><div style='width: 400px;'>Tip: " + tip + "</div></html>");
	}
	
	// method to update the not-in-string label on the panel
	private static void updateNotInStringLabel(JLabel label) {
		String nis = HangmanGame.notInStringChars;
		label.setText("Not-in-String: " + nis);
	}
}
