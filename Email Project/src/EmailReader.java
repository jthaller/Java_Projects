import squint.*;
import javax.swing.*;

public class EmailReader extends GUIManager{
	
	JFrame J = new JFrame();
	EmailReader NetConnection;
	private final int WINDOW_WIDTH = 600, WINDOW_HEIGHT = 600;
	
	//I'm putting the graphics in the constructor. Is that dumb? 
	//they should be in another class, but I'll deal with that later
	public EmailReader(){
		this.createWindow( WINDOW_WIDTH, WINDOW_HEIGHT); //this is in squint?
		
		private JTextfield username = new JTextField(10);
		contentPane.add( new JLabel("Username:"));
		contentPane.add(username);
		
		private JTextField password = new JPasswordField("10");
		contentPane.add(new JLabel("Password"));
		contentPane.add(password);
		
		contentPane.add( new JButton("Login"));
		
		contentPane.add( new)
		
		JButton login = new JButton("Login"); 
		//probably a good idea to add an if statement to change
		//the login button to a logout button
		
		
		//probably a good idea to only add the next few lines
		//if the user is logged in
		
		contentPane.add( new JLabel("Message Number to Retrieve"));
		contentPane.add( new JButton("Get Message"));
		
		ContentPane.add( new JTextArea(   )); 
		//some other thing is needed for scroll bars right?
		//and I probably need two of these
		
		/* it would be pretty easy to add a slider.  
		 * would only be useful if you had less than 20 messages 
		 * would need to create slider length and ticks based on 
		 * how many messages there are
		 */
		
		public static void ButtonClicked(){
		private EmailField
		password.getText();
		username.getText();
		}
		
		private String lineFromServer; 
		
		if ( lineFromServer.startsWith( “+OK” ) ) { 
			// invoke nextPOPResponse
			//append message to upper text area
		}
		
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
