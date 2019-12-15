/* Jeremy Thaller P4
 * A class to create a generic, rectangular game board.
 * In this project it will be used as the board for the Game of Life.  
 */
public class Board {
	int [][] board;
	private int numRows;
	private int numCols;

	//CTORS
	//Creates a board based on number of rows and columns
	//If rows or cols given is negative, just uses absolute value of it
	//NumRows is the y axis. number of horizontals
	//NumCols is the x axis. number of verticals 
	public Board(int numRows, int numCols){
		if (numRows >= 0 && numCols >= 0){
		this.numRows = numRows;
		this.numCols = numCols;
		board = new int[numRows][numCols];
		}
		else board = new int[Math.abs(numRows)][Math.abs(numCols)]; 
	}

	//CTORS
	//Creates square board based on the number of rows 
	public Board(int numRows){
		this.numRows = numRows;
	}

	//CTORS
	//Creates a board based off a pre-existing board, a matrix
	public Board(int[][] board){
		this.board = board;
	}

	//returns number of rows
	public int getRows(){
		return numRows;
	}

	//returns number of columns
	public int getCols(){
		return numCols;
	}



	//changes the value at the spot on the board
	//Params: must know the row, column, and type of piece
	//returns: an int to determine if replacement was successful
	//returns -1 if cannot set spot
	public int setSpot(int row, int col, int pieceType){
		if (isValid(row, col)){
			board [row][col] = pieceType;
			return pieceType;
		}
		return -1;
	}

	//gets the value at a spot
	//params: int row, int col. (must know y,x coordinate)
	//returns: int (value of the spot)
	//YO! don't be stupid, someoone forgot to check if this spot is valid
	public int getSpot(int row, int col){
		if(isValid(row,col))
			return board [row][col];
		return 0;
	}

	//removes the spot (sets value of spot to zero
	//params: int row, int col (must know y,x coordinate)
	//returns: int spot, (the original spot as an int)
	public int remove(int row, int col){
		if(isValid(row,col)){
			setSpot(row, col, 0); //removes spot by setting it to zero
			//returns original spot as int
			int spot = board [row][col];
			return spot;}
		return -1;
	}

	//checks if the spot is occurpied
	//params: int row, int col (must know y,x coordinate)
	//returns: boolean (true if is occupied, false if not)
	public boolean isOccupied(int row, int col){
		//		int spot = getSpot(row, col); //spot is the value at a spot
		//		if (spot == 1)
		//			return true; // if spot == 1, a piece is there
		//		else
		//			return false;
		return (getSpot(row,col) == 1);
	}

	//clears board
	//params: none
	//steps through each row and column and sets value equal to zero
	//returns: nothing.
	public void clear(){
		for(int row =0; row <= numRows -1; row++){
			for (int col = 0; col <= numCols -1; col++){
				setSpot(row,col,0);
			}
		}
	}
	
	//Checks if spot given is valid
	//params: int row, int col (the spot to check)
	//reuturns: boolean.  true if the spot being checked is valid
	public boolean isValid(int row, int col){
		if (row >= numRows || row < 0 || col >=numCols || col < 0)
			return false;
		else
			return true;
	}
	
	//Params: none
	//Reuturns: a string representation of the board
	public String toString()
	{ 
		String s = "";
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[row].length; col++) {
				s += " " + board[row][col];
			}
			s += "\r\n";
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println("  BOARD \n---------");
		Board a = new Board(3,4); // sets size of board. (Numrow, Numcol)
		a.setSpot(0,2,1);
		a.setSpot(0,0,1);
		a.setSpot(1, 1, 1);
		a.setSpot(1, 4, 2); // invalid to test 
		a.setSpot(2, 2, 1);
		System.out.print(a.toString());
		//most tests in Tester.java



	}

}


