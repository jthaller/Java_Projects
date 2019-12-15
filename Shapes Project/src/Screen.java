//Jeremy Thaller P4
//Class to create and manage the screen
public class Screen {

	private char [][] screen; //char [][] azard;
	private char borderChar = 'X'; //defaults to X
	private char character = '0'; //default 0
	private int height;
	private int width;

	//note to self, height is the same thing as number of rows

	//constructor
	//creates screen
	//params: int height and int width
	public Screen(int height, int width){
		this.height = height;
		this.width = width;
		this.screen = new char[height][width];

	}

	//constructor
	//creates default screen of size 0x0 if not params
	//calls 2 param constructor and with params of (0,0)
	public Screen(){
		this(0,0);
	}

	//constructor
	//params: char[][] screen
	public Screen(char[][] screen){
		this.screen = screen;
		for (int i = 0; i < height; i++){
			for (int j =0; j < width; j++){
				screen[i][j] = ' ';
			}
		}

	}

	//sets the border character if you want to change it easily
	//params: takes in char borderChar
	//returns: nothing
	public void setBorderChar(char borderChar){
		this.borderChar = borderChar;
	}

	//sets the char character. Needed for lines
	//params: takes in char character
	//returns nothing
	public void setChar(char character){
		this.character = character;
	}

	//returns character. needed for lines
	//params: none
	// returns: char character
	public char getChar(){
		return character;
	}

	//prints the board. This includes the border and screen
	//params: none
	//returns: none
	public void draw(){
		for(int i = 0; i < width +1; i++)
			System.out.print(borderChar + " ");//i is width
		System.out.println(borderChar);
		for (int j = 0; j < height; j++){
			System.out.print(borderChar + " ");
			for (int i =0; i < width; i++){
				System.out.print(screen[j][i] + " ");
			}
			System.out.print(borderChar + " \n");
		}
		for(int i = 0; i < width +1; i++)
			System.out.print(borderChar + " ");//i is width
		System.out.println(borderChar);
	}


	//takes the screen and clears it.  
	//this takes the screen and sets each character within it to be empty
	//params: Screen screen
	//returns nothing
	public void clearScreen(Screen screen){
		for(int height = 0; height < screen.height; height++){
			for(int width = 0; width < screen.width; width++){
				paintAt(height, width, ' ');
			}

		}

	}

	//method to paint a pixel. Sets a coordinate to a character
	//params: int height, int width, char c.  These are the location and character 
	//returns nothing
	public void paintAt(int height, int width, char c){
		if( isValid(height,width) ) 
			screen [height][width] = c;
	}

	//sets height of screen
	//params: int height
	//returns nothing
	public void setHeight(int height){
		this.height = height;
	}

	//sets width of screen
	//params: int width
	// returns nothing 
	public void setWidth(int width){
		this.width = width;
	}

	//sets a screen by knowing a 2d array of chars 
	//params: char [][] screen.  THis is a 2d array of chars
	//the char would be '' to set the screen to be empty
	//returns nothing
	public void setScreen(char [][] screen){
		this.screen = screen;
	}

	//gets the height of a screen (including border)
	//params: char [][] screen
	//returns the height
	public int getHeight(char[][]screen){
		return height;

		//is this right? seems like might not be screen. or return just height.
		//one might be right, not both

	}

	//gets the width of a screen (including the border)
	//params:char [][] screen. a 2d array of chars
	//returns:
	public int getWidth(char[][]screen){
		return width;
	}


	//I don't think I ever actually use this
	public boolean isValid(int height, int width){
		if (height >= this.height || height < 0 || width >=this.width || width < 0)
			return false;
		else
			return true;
	}


	public static void main(String[] args) {
		//tests for lots of stuff
		Screen a = new Screen(10,18);
		a.paintAt(9,9, 'O');
		a.paintAt(9, 0, '0');

		HLine H = new HLine(5);
		H.setChar('0');
		H.paintOn(a, 3,4 );

		VLine v = new VLine(6);
		v.setChar('J');
		v.paintOn(a,0,2);

		Frame r = new Frame(4,5, 'F');
		r.paintOn(a,9,3);

		Box b = new Box(3,3,'B');
		b.paintOn(a,3,0);

		TextLine t = new TextLine("Test 8783");
		t.paintOn(a,8,0);

		a.setBorderChar('*');
		
		a.draw();
		
		a.clearScreen(a);

		a.draw();
	}
}