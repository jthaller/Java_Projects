//Jeremy Thaller p4
//class calls on all the other classes in the project to create an interactive experience, using all functionality
//the program includes 2 menues, can paint vlines, hlines, textlines, boxes, and frames.
//it can also clear the screen, create a screen, 
import java.util.Scanner;

public class Emulator extends Screen {

	private static Scanner s = new Scanner(System.in);
	private static Screen board;


	//Method returns a positive integer. this is only used for creating the board
	//accounts for non numbers
	//params: string string 
	//returns: (a positive) int
	public static int getPosInt(String string){
		int posInt;
		do{
			System.out.println(string);
			while (!s.hasNextInt() || s.hasNext(" ") || s.hasNext("\n")){ 
				System.out.println("Invalid Input");
				System.out.println(string);
				s.nextLine();}
			posInt = s.nextInt();
			s.nextLine();
			if (posInt <= 100 && posInt >= 0)
				return posInt;
			System.out.println("Invalid Input");
		}  while(true);
	}

	//Methods returns an actual integer. it can be positive or negative. caps it at +-1000000000
	//accounts for non numbers
	//params: string string
	//returns: an int
	public static int getRealInt(String string){
		int realInt;
		do{
			System.out.println(string);
			//			while (!s.hasNextInt() || s.hasNext(" ")){ 
			while (!s.hasNextInt()){
				System.out.println("Invalid Input");
				System.out.println(string);
				s.nextLine();}
			realInt = s.nextInt();
			s.nextLine();
			if (realInt <= 1000000000 && realInt >= -1000000000)
				return realInt;
			System.out.println("Invalid Input");
		}  while(true);
	}


	//method to return a positive int. includes 0 and caps at 1000000000 so program doesn't crash
	//accounts for non numbers
	//params: string string
	//returns: a positive integer from 0 to 1000000000
	public static int getLargeInt(String string){
		int LargeInt;
		do{
			System.out.println(string);
			//			while (!s.hasNextInt() || s.hasNext(" ")){ 
			while (!s.hasNextInt()){
				System.out.println("Invalid Input");
				System.out.println(string);
				s.nextLine();}
			LargeInt = s.nextInt();
			s.nextLine();
			if (LargeInt <= 1000000000 && LargeInt >= 0)
				return LargeInt;
			System.out.println("Invalid Input");
		}  while(true);
	}

	//gets a real char. selects the first char entered (doesn't includes spaces or tabs)
	//params: string string
	//returns: char
	public static char getRealChar(String string){
		Scanner r = new Scanner(System.in); 
		do{
			System.out.println(string);
			//			s = r.nextLine();
			if (r.hasNext())
				return r.next().charAt(0);
			else System.out.println("Invalid Input");
		}
		while(true);
	}

	//gets valid text. all text is valid, except tabs. replaces all tabs with spaces
	//params: string string
	//returngs: string answer
	public static String getValidText(String string){
		String entered;
		Scanner r = new Scanner(System.in);
		do
		{
			System.out.println(string);
			entered = r.nextLine();
			String answer = entered.replaceAll("	", " ");
			return answer;
		}
		while(true);
	}

	//gets the number chosen by the user. number must be from 1 to 6 inclusive. accounts for non numbers
	//params: string string
	//returns: int chosen
	public static int getShapeNumFromList(String string){
		int chosen;
		do{
			System.out.println(string);

			while (!s.hasNextInt()){ 
				System.out.println("Invalid Input");
				System.out.println(string);
				//				s.nextLine();
				//				s.nextLine();
				//				s.nextLine();
				//				s.nextLine();
				s.nextLine();
			}
			chosen = s.nextInt();
			if (chosen == 1 || chosen == 2 || chosen == 3 || chosen == 4 || chosen ==5 || chosen ==6)
				return chosen;
			else System.out.println("Invalid Input");
		}
		while(true);
	}

	//method calls the important methods, which in turn call the other methods to use
	//loops through thingsToDo until the user quits the program
	//params: none
	//returns: none
	public void run(){
		System.out.println("WELCOME TO THE SHAPES PROJECT \n-----------------------------");
		System.out.println("First Let's Create a Screen \n");
		makeBoard();
		board.draw();
		do{
			thingsToDo();
			board.draw();
		}
		//		while(goAgain());
		while(true);
	}


	//first method called by the run method. this method creates a board
	//params: none
	//returns:none
	public static void makeBoard(){
		int h = getPosInt("Enter a height (int between 0 and 100)");
		int w = getPosInt("Enter a Width (int between 0 and 100)");
		board = new Screen(h,w);
	}

	//method is the first menue. this includes the shapes you can draw and #6 is more options 
	//params: none
	//returns:none
	public static void thingsToDo(){ 
		System.out.println("\nHere are the shape options: \n 1. Horizontal Line \n 2. Vertical Line \n 3. Frame \n 4. Box \n 5. Text \n 6. More Options"); //make 6 options menue instead
		int shapeChosen = getShapeNumFromList("Which shape would you like to draw? \n Choose a number from above");
		if (shapeChosen == 1)
			doHLine();
		if (shapeChosen == 2)
			doVLine();
		if (shapeChosen == 3)
			doFrame();
		if (shapeChosen == 4)
			doBox();
		if (shapeChosen == 5)
			doTextLine();
		if (shapeChosen == 6)
			optionsMenue();
	}

	//method to create an HLine based on user input
	//params: none
	//returns:none
	public static void doHLine(){
		int x = getRealInt("Please pick an x coordinate");
		int y = getRealInt("Please pick a y coordinate");
		int z = getLargeInt("Please pick a size for the line");
		char c = getRealChar("Please choose a character");
		HLine H = new HLine(z);
		board.setChar(c);
		H.paintOn(board, x,y );
		return;
	}

	//method to create a vLine based on user input
	//params: none
	//returns:none
	public static void doVLine(){
		int x = getRealInt("Please pick an x coordinate");
		int y = getRealInt("Please pick a y coordinate");
		int z = getLargeInt("Please pick a size for the line");
		char c = getRealChar("Please choose a character");
		board.setChar(c);
		VLine V = new VLine(z);
		V.paintOn(board, x,y );

	}

	//method to create a frame based on user input
	//params: none
	//returns:none
	public static void doFrame(){
		int x = getRealInt("Please pick an x coordinate");
		int y = getRealInt("Please pick a y coordinate");
		int w = getLargeInt("Please pick a width");
		int h = getLargeInt("Please pick a height");
		char c = getRealChar("Please pick a character");
		Frame F = new Frame(h,w, c);
		F.paintOn(board,x,y);
	}

	//method to create a box based on user input
	//params: none
	//returns:none
	public static void doBox(){
		int x = getRealInt("Please pick an x coordinate");
		int y = getRealInt("Please pick a y coordinate");
		int w = getLargeInt("Please pick a width");
		int h = getLargeInt("Please pick a height");
		char c = getRealChar("Please pick a character");
		Box B = new Box(h,w, c);
		B.paintOn(board,x,y);
	}

	//method to create a textLine based on user input
	//params: none
	//returns:none
	public static void doTextLine(){
		int x = getRealInt("Please pick an x coordinate");
		int y = getRealInt("Please pick a y coordinate");
		String text = getValidText("Please write something to enter");
		TextLine T = new TextLine(text);
		T.paintOn(board,x,y);
	}

	//this is the 2nd menue. includes options such as change border char, clear screen, make new screen, print screen, and quit program
	//params: none
	//returns:none
	public static void optionsMenue(){
		int shapeChosen = getShapeNumFromList("\nChoose an option from below: (pick a number) \n 1. Change Border Character \n 2. Clear Screen \n 3. Abandon Screen and Create New One \n 4. Create a Shape \n 5. Print Screen \n 6. Quit Program");
		if (shapeChosen == 1){
			chooseBorderChar("Please Select a border character");
			board.draw();
			optionsMenue();}
		if (shapeChosen == 2){
			board.clearScreen(board);
			board.draw();
			optionsMenue();}
		if (shapeChosen == 3){
			makeBoard();
//			optionsMenue();
			board.draw();
			thingsToDo();}
		if (shapeChosen == 4)
			thingsToDo();
		if (shapeChosen == 5){
			board.draw();
			optionsMenue();}
		if (shapeChosen == 6)
			System.out.println("GoodBye!");
		System.exit(0);
	}

	//method to set the border character
	//params: string string
	//returns:none
	public static void chooseBorderChar(String string){
		char c = getRealChar(string);
		board.setBorderChar(c);
	}

	public static void main(String[] args){
		Emulator e = new Emulator();
		e.run();
	}

}


