import squint.*;
import javax.swing.*;
import java.util.Random;
import java.awt.*;
import java.util.ArrayList;

public class Board extends GUIManager{ 
    private String currentWord;
    private BoggleButton lastClicked;

    private JButton lastLetterClicked;
    public final int WIDTH = 4;
    public final int HEIGHT = 4;

    private Lexicon dictionary = new Lexicon();
    private GameWindow boggleWindow;

    private Random numberChooser = new Random();
    private BoggleButton [] allButtons = new BoggleButton[ WIDTH*HEIGHT ];
    private StringList wordsPlayed = new StringList();

    /* Ctor: Board
     * Params: GameWindow 
     * 
     * Ctor sets the instant GameWindow wordsPlayed 
     */
    public Board( GameWindow controlWindow ){
        contentPane.setLayout( new GridLayout( HEIGHT,WIDTH) );
        for ( int y = 0; y < HEIGHT; y++ ) {
            for ( int x = 0; x < WIDTH; x++ ) {
                BoggleButton button = new BoggleButton( y, x );
                button.setColor( "Black" );
                allButtons[ y*WIDTH + x ] = button;
                contentPane.add( button );
            }
        }

        boggleWindow = controlWindow;
    }

    public void buttonClicked( JButton which ) {

        BoggleButton whichButton = (BoggleButton) which;

        //if first button clicked
        if ( lastClicked == null ) {

            lastClicked = whichButton;
            whichButton.setRed();
            currentWord = whichButton.getText();

            boggleWindow.updateCurrentWord( currentWord );

        } //if not first clicked and hasn't been clicked before and is a legal move
        else if ( lastClicked.isAdjacentTo( whichButton ) ) {
            
            if ( whichButton.getColor().equals( "Blue" ) ) {
            lastClicked = null;
            boggleWindow.updateCurrentWord(""); //clear current word
            resetBoardColor();
            } else {
            whichButton.setRed();
            lastClicked.setBlue();
            lastClicked = whichButton;
            currentWord = currentWord + whichButton.getText();
            boggleWindow.updateCurrentWord(currentWord);
          
        }

        }
        //if you clicked same button twice
        else if ( whichButton == lastClicked ) {
            lastClicked = null;
            resetBoardColor();

            if ( dictionary.contains( currentWord) ) {
                if ( wordsPlayed.contains( currentWord) ) {

                } else {
                    boggleWindow.addNewWordToList( currentWord );
                    //addNewWordToList(currentWord);
                    wordsPlayed = new StringList( currentWord, wordsPlayed );
                    int points = this.wordScore( currentWord );
                    boggleWindow.adjustScore( points, 0 );
                    if ( boggleWindow.isConnected() ) {
                    boggleWindow.sendWord( currentWord );
                }
                }
            }
            boggleWindow.updateCurrentWord(""); //clear current word

            //wordScore(currentWord);
        }
        else{ //if the button has already been clicked but it isn't an adjacent one
            lastClicked = null;
            resetBoardColor();
            currentWord = ""; //needed? probably not
            boggleWindow.updateCurrentWord(""); //clear current word
        }
        //still doesn't work if button is adj but already clicked

    }
    
    public void resetBoardColor(){
        for ( int x = 0; x<16; x++ ) {
                allButtons[x].setBlack();
            }
    }
    
    private String getLetters(){
        return currentWord;
    }

    public String randomizeLetters(String[][] letters){
        String randomLetters = "";
        for (int size = 16; size>0; size-- ) {
            int row =  numberChooser.nextInt(size);
            int col =  numberChooser.nextInt(6);
            String letter = letters[row][col];
            randomLetters = randomLetters + letter;
            for (int column = 0; column<6; column++ ) {
                letters[row][column]=letters[size-1][column];
            }
        }
        return randomLetters;
    }

    public void setLetters( String desiredLetters ) {
        for( int number = 0; number<16; number++ ) {    
            allButtons[number].setText(desiredLetters.substring( number, number + 1) );
            allButtons[number].setFont(new Font("Times New Roman MS", Font.BOLD, 20));
        }
    }
    //given a word, returns number of points for that word
    //based on the rules
    public int wordScore(String word){
        if(word.length() >8){
            return 11;
        } else if ( word.length() == 7) {
            return 5;
        } else if (word.length() == 6) {
            return 3;
        } else if (word.length() == 5) {
            return 2;
        } else if ( word.length() == 4) {
            return 1;
        } else if ( word.length() == 3) {
            return 1;
        } else {
            return 0;
        }
    }

    public void disable() {
        for ( int y = 0; y < HEIGHT; y++ ) {
            for ( int x = 0; x < WIDTH; x++ ) {
                allButtons[ y*WIDTH + x ].setEnabled( false );                
            }
        }
    }

    public void enable() {
        for ( int y = 0; y < HEIGHT; y++ ) {
            for ( int x = 0; x < WIDTH; x++ ) {
                allButtons[ y*WIDTH + x ].setEnabled( true );                
            }
        }  
    }
    
    public boolean checkWord( String word ) {
        return wordsPlayed.contains( word );
    }
}