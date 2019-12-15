import squint.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.JOptionPane;

public class GameWindow extends GUIManager{
    private final int WINDOW_WIDTH = 720, WINDOW_HEIGHT = 600;

    private JProgressBar pbar = new JProgressBar(JProgressBar.VERTICAL);
    private JLabel nameLabel = new JLabel( "Your name:" );
    private JTextField name = new JTextField(15);
    private JLabel groupLabel = new JLabel( "Partner Group:" );
    private JTextField groupCode = new JTextField(15);
    //jpanels
    private JTextArea currentLettersUsed = new JTextArea();
    private JLabel opponent = new JLabel( "Opponent                 "  );
    private JLabel score = new JLabel("Score=");
    private JLabel wordsLabel = new JLabel( "Words Found: " );
    private JTextArea words = new JTextArea(40,20); //words used by you already
    private int yourScore;
    private int opponentScore;
    //private String player;
    //private String opponent;
    private JButton startConcede = new JButton( "Start" ); 
    private JButton findPartner = new JButton( "Find Partner" );
    private Board gameBoard = new Board( this );

    private PaceMaker timer;

    private NetConnection connection;

    int progress = 0;

    private final String [][] cubeSides =
        new String[][]{
            { "A", "A", "C", "I", "O", "T" },
            { "T", "Y", "A", "B", "I", "L" },
            { "J", "M", "O", "Qu", "A", "B" },
            { "A", "C", "D", "E", "M", "P" },
            { "A", "C", "E", "L", "S", "R" },
            { "A", "D", "E", "N", "V", "Z" },
            { "A", "H", "M", "O", "R", "S" },
            { "B", "F", "I", "O", "R", "X" },
            { "D", "E", "N", "O", "S", "W" },
            { "D", "K", "N", "O", "T", "U" },
            { "E", "E", "F", "H", "I", "Y" },
            { "E", "G", "I", "N", "T", "V" },
            { "E", "G", "K", "L", "U", "Y" },
            { "E", "H", "I", "N", "P", "S" },
            { "E", "L", "P", "S", "T", "U" },
            { "G", "I", "L", "R", "U", "W" }
        };
    public GameWindow(){
        this.createWindow( WINDOW_WIDTH, WINDOW_HEIGHT );

        contentPane.setLayout( new BorderLayout() );
        contentPane.add( pbar, BorderLayout.EAST );

        contentPane.add( gameBoard, BorderLayout.CENTER );

        JPanel scoreBoard = new JPanel();
        scoreBoard.add( opponent );
        scoreBoard.add( score );
        contentPane.add( scoreBoard, BorderLayout.NORTH );

        JPanel wordsUsed = new JPanel();
        //JTextArea currentLettersUsed = new JTextArea();
        wordsUsed.add(new JScrollPane(currentLettersUsed));
        wordsUsed.setLayout( new BoxLayout(wordsUsed, BoxLayout.Y_AXIS) );
        currentLettersUsed.setEditable( false );
        wordsUsed.add( wordsLabel);
        wordsUsed.add( words ); 
        words.setEditable( false );

        contentPane.add( wordsUsed, BorderLayout.WEST );

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout( new GridLayout( 2,3 ) );
        buttonPanel.add( startConcede );
        buttonPanel.add( nameLabel );
        buttonPanel.add( groupLabel );
        buttonPanel.add( findPartner );
        buttonPanel.add( name );
        buttonPanel.add( groupCode );
        contentPane.add( buttonPanel, BorderLayout.SOUTH );
        
        gameBoard.disable();

    }

    /* Method: ButtonClicked(  )
     * Params: JButton which
     * Returns: void
     * 
     * Method activated whenever a button is clicked.
     * Responds accordingly.
     */
    public void buttonClicked(JButton which ){
        if (which == startConcede ) {
            //if game hasn't started yet, you need to set the board
            //and swap start/concede button
            if (startConcede.getText().equals( "Start" )){
                gameBoard.enable();
                String randomLetters = gameBoard.randomizeLetters(cubeSides);
                gameBoard.setLetters( randomLetters );
                startConcede.setText( "Concede" );
                timer = new PaceMaker( 1, this );
                pbar.setMinimum( 0 );
                pbar.setMaximum( 180 );
                gameBoard.enable();
                progress = 180;
                yourScore = 0;
                opponentScore = 0;
            }
            //if game is going going on, you want to stop it.
            //swap start/concede settings. Don't want to mix up board yet
            else {
                startConcede.setText( "Start" );
                timer.stop();
                words.setText( "" );
                currentLettersUsed.setText( "" );
                gameBoard.resetBoardColor();
                //should probably add a make all buttons black again method in Board and call it here
            }
        }

        else if (which == findPartner){
            connection = new NetConnection( "limia.cs.williams.edu", 13417 );
            connection.addMessageListener( this );
            connection.out.println( "PLAY " + "\"" + name.getText().toString() + "\"" + " " + "\"" + groupCode.getText().toString() + "\"" );
            name.setEditable( false );
            groupCode.setEditable( false );
            gameBoard.disable();
        }

    }

    /* Method: adjustScore(  )
     * Params: int yourPoints, int opponentPoints
     * Returns: void
     * 
     * Method adjusts the instance varaibles yourScore and opponentScore.
     * 
     * if match game. Need to tell if you scored the point or if your
     * opponent did. If you did, you get positive points and the 
     * other person gets negative points.
     */

    public void adjustScore(int yourPoints, int opponentsPoints){
        yourScore = yourScore + yourPoints;
        opponentScore = opponentScore + opponentsPoints;
        score.setText( "Score:" + yourScore + "vs." + opponentScore );
    }

    
    /* Method: addNewWordToList(  )
     * Params: String s
     * Returns: none
     * 
     * Adds a word to the JTextArea words
     */
    public void addNewWordToList(String s){
        words.append(s + "\n");
    }

    /* Method: Tick()
     * Params: none
     * Returns: none
     * 
     * Every time the timer ticks, the method changes the JProgressBar pbar.
     * The GameBoard gets disabled when the the procress runs out.
     */
    public void tick() {
        progress--;
        pbar.setValue( progress );
        if (progress == 0 ) {
            if ( connection == null ) {
                gameBoard.disable();
                JOptionPane.showMessageDialog( null, "You are in a Superposition of Win and Loss", "Schrodinger's Competition" , JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }

    /* Method: dataAvaialabe()
     * Params: none
     * Returns: none
     * 
     * Method automatically called when data arrives through the network.
     * Method responds accordingly for each time of data retrieved
     */
    public void dataAvailable() {
        String stanza = connection.in.nextLine();
        StringList opponentsWords = new StringList();
        if ( stanza.startsWith( "START" ) ) {
            String opponentName = stanza.substring( 6 );
            opponent.setText( opponentName );
            String serversLetters = "";
            for ( int line = 0; line<16; line++ ) {
                stanza = connection.in.nextLine();
                serversLetters = serversLetters + stanza;
            }
            gameBoard.setLetters( serversLetters );
            startConcede.setText( "Concede" );
            timer = new PaceMaker( 1, this );
            
            
            pbar.setMinimum( 0 );
            pbar.setMaximum( 180 );
            gameBoard.enable();
            progress = 180;

        } else if ( stanza.startsWith( "WORD" ) ) {
            String newWord = stanza.substring( 5 );

            //Checks whether we have used that word already
            if ( gameBoard.checkWord( newWord ) ) {
                int theirPoints = gameBoard.wordScore( newWord );
                this.adjustScore( -theirPoints, 0 );
            } else {
                int theirPoints = gameBoard.wordScore( newWord );
                this.adjustScore( 0, theirPoints );
            }
            opponentsWords = new StringList( newWord, opponentsWords );
        }
    }

    /* Method: connectionClosed()
     * Params: none
     * Returns: none
     * 
     * Method called automatically when connection is closed.
     * A dialog Box pops up telling you whether or not you won the game.
     */
    public void connectionClosed() {
        connection.close();
        gameBoard.disable();
        if ( yourScore > opponentScore ) {
            JOptionPane.showMessageDialog( null, "You Win!!!", "Game Over" , JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            JOptionPane.showMessageDialog( null, "You Lose", "Game Over" , JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    /* Method: sendWord( )
     * Params: String word
     * Returns: none
     * 
     * Method sends a word to your opponent over the network
     */
    public void sendWord( String word ) {
        connection.out.println( "WORD " + word );
    }

    /* Method: isConnected()
     * Params: none
     * Returns: Boolean
     * 
     * Returns true if you are connected to the network.
     * Returns false if you are not connected to the network
     */
    public boolean isConnected() {
        if ( connection == null ) {
            return false;
        } else {
            return true;
        }
    }
    
    /* Method: updateCurrentWord( )
     * Params: String s
     * Returns: none
     *
     * Method changes the text in the textbox to display the letters you have selected
     * thus far in the current word.
     */
    public void updateCurrentWord(String s){
        currentLettersUsed.setText(s);
    }
    

}
