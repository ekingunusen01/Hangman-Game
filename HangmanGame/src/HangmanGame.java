
import java.util.*;


public class HangmanGame {
	
	static	ArrayList<String> 		gameWordPool 		= new ArrayList<String>();
	static 	ArrayList<String>		wordTipPool			= new ArrayList<String>();
	static 	ArrayList<String>		notInStringPool		= new ArrayList<String>();
	static 	ArrayList<String>		countdownPool		= new ArrayList<String>();
	static 	int 					wrongGuessCountTotal= 6;
	static 	int 					statusFlag 			= 0;
	static 	int 					countdownTime 		= 0;
	static 	int 					hiddenWordLength 	= 0;
	static 	int 					isGamePlayingFlag	= 0; 
	static 	String 					hiddenWordTip 		= "";
	static 	String 					hiddenWord;
	static 	String 					notInStringChars 	= "";
	static 	char[]					guessedLetters;
	static	FileHandler 			fileHandler 		= new FileHandler();
			String 					readWordsFromFile 	= "src\\hangmanGameWords.csv";
	static 	long					startTime;
	static 	long					finalTime;
	
	public void setupGame() {
		fileHandler.wordFileReader(readWordsFromFile, gameWordPool, wordTipPool, notInStringPool, countdownPool);
		selectRandomWord();
		resetGame();
		GUI.initializeKeyboard();
	}
	
	// method to select a random word and its other elements for the game
	private void selectRandomWord() {
		Random random = new Random();
		int index = random.nextInt(gameWordPool.size());
		hiddenWord = gameWordPool.get(index);
		hiddenWordLength = hiddenWord.length();
		hiddenWordTip = wordTipPool.get(index);
		notInStringChars = notInStringPool.get(index);
		countdownTime = Integer.parseInt(countdownPool.get(index));
	}
	
	// method to validate the letters and win condition
	public void checkLetters(char guessedLetter) {
		if(statusFlag == 0) {
			boolean isCorrect = false;
			
			for(int i = 0; i < HangmanGame.hiddenWordLength; i++) {
				if(HangmanGame.hiddenWord.charAt(i) == guessedLetter) {
					HangmanGame.guessedLetters[i] = guessedLetter;
					GUI.updateStringsPanel(guessedLetter);
					isCorrect = true;
				}
			}
			if(!isCorrect) {
				wrongGuessCountTotal--;
				GUI.setImageWithCount(GUI.dudePanel, wrongGuessCountTotal);
			}
			if(wrongGuessCountTotal == 0) {
				statusFlag = 1;
				GUI.updateStatusText(GUI.statuslabel, statusFlag);
				endGame();
			}
			if(isWordGuessed()) {
				statusFlag = 2;
				GUI.updateStatusText(GUI.statuslabel, statusFlag);
				endGame();
			}
		}	
	}

	// method to handle the end of the game
	public void endGame() {
		isGamePlayingFlag = 0;
		finalTime = System.currentTimeMillis();
		UserInfoFrame newUser = new UserInfoFrame();
		newUser.showForm();
	}
	
	// method to reset the settings of the game
	public void resetGame() {
		wrongGuessCountTotal= 6;
		statusFlag 			= 0;
		isGamePlayingFlag 	= 1;
		
		countdownTime = Integer.parseInt(countdownPool.get(gameWordPool.indexOf(hiddenWord)));
		guessedLetters = new char[hiddenWordLength];
		Arrays.fill(guessedLetters, '_');
		GUI.setImageWithCount(GUI.dudePanel, wrongGuessCountTotal);
		GUI.updateStringsPanel('\0');
		GUI.setCountdownLabel();
		GUI.updateStatusText(GUI.statuslabel, statusFlag);
		startTime = System.currentTimeMillis();
	}
	
	// a validation method to get the correctness of the guess
	private boolean isWordGuessed() {
	    for (int i = 0; i < HangmanGame.hiddenWordLength; i++) {
	        if (HangmanGame.guessedLetters[i] == '_') {
	            return false; 
	        }
	    }
	    return true; 
	}
}
