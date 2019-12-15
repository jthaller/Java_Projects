/*Jeremy Thaller P4
 *Class to control the rules of the game of life and print it
 *Rules: Game is played on rectangular board.
 *Each square is either empty or occupied.
 *Beginning squares are defined, then the game runs on its own.
 *A new cell is born on an empty square if it is surrounded
 *by exactly three occupied neighboring cells.
 *An occupied cell dies if it is surrounded by four or more neighbors.
 *An occupied cell also dies if it is surrounded by exactly zero neighbors.
 *A neighbor is an occupant of an adjacent square, including diagnols.  
 */

//import java.util.Random;

public class GameOfLife {

	private Board board;
	private int genNum;
	private final boolean hesDeadJim = false;
	
	//CTOR
	//Creates a game of life given the number of rows a columns to be used
	public GameOfLife(int row, int col){
		Board board = new Board(row, col);
		this.board = board;
	}

	//CTOR 
	//Creates a gameOfLife based on one dimension of board -- a Square board
	public GameOfLife(int size){
		Board board = new Board(size);
		this.board = board;
	}
	
	//CTOR
	//Creates a gameOfLife based off a 2-D matrix
	public GameOfLife(int[][] a){
		Board board = new Board(a);
		this.board = board;
	}


	//takes board and fills it randomly with 1's
	//Params: none
	//returns: none
	public void randomize(){
		for(int row = 0; row < board.getRows(); row++){
			for(int col = 0; col < board.getCols(); col++){
				if ((int)(Math.random() + .5) == 1)
					setCell(row, col,true);
				else setCell(row, col, false);

			}
		}
	}



	//creates the board for the next generation
	//calls on all the other methods of the game
	public void nextGeneration(){
		//		boolean gen[][] = new boolean[board.getRows()][board.getCols()];  // do i even need this?
		int [][] copy = new int[board.getRows()][board.getCols()];
		for (int row=0; row<board.getRows(); row++){
			for (int col=0; col<board.getCols(); col++){
				copy[row][col] = board.getSpot(row, col);
			}
		}
		for (int row = 0; row< board.getRows(); row++)
		{ 
			for (int col = 0; col < board.getCols(); col++){
				if (willLive(row,col)){
					copy [row][col] = 1;
				}
				else copy [row][col] = 0;
			}
		}

		for (int row=0; row<board.getRows(); row++){
			for (int col=0; col<board.getCols(); col++){
				board.setSpot(row,col, copy[row][col]);
			}

		}
		genNum++;
	}
	
	//Sets the spot of cell in GOL
	//Params: int row, int col, and bool LiveOrDead
		// need to know where and whether of not it's alive
	//no returns
	public void setCell(int row, int col, boolean liveOrDead){
		if (liveOrDead)
			board.setSpot(row, col, 1);
		else
			board.setSpot(row, col, 0);
	}


	//method for getting the number of rows on the board
	// Params: none
	// returns: int.  This is the number of rows
	public int getRows(){
		return board.getRows();
	}

	//method for getting the number of columns on the board
	// Params: none
	// returns: int.  This is the number of columns
	public int getCols(){
		return board.getCols();
	}


	//method for getting generation number
	//params:none
	//returns: int.  This is the generation number
	public int getGenNum(){
		return genNum;
	}

	//method to check if the cell is alive or not
	// params: int row, int col.  this is the position of the cell being checked
	// returns: boolean. Returns true if alive, false if dead.
	public boolean isAlive(int row, int col){
		if (board.isOccupied(row, col)) //==true
			return true;
		//		if (board.isOccupied(row, col)== false)
		//			return false;
		else return false;
	}


	//method for counting number of neighbors
	//params: int row, int col // this is the position of the cell being tested
	//returns: int neighbors.  this is the number of alive neighbors 
	public int countNeighbors(int row, int col){
		int neighbors = 0; 

		for (int i = row -1; i<= row +1; i++)
		{ 
			for (int j = col -1; j <= col +1; j++)
			{
				if (isAlive(i, j) == true)
				{
					neighbors++;
				}
			} //the isAlive method checks for array out of bounds exceptions,
			//so I shouldn't have to check if the spot being checked is on the board, here
		}
		if (board.getSpot(row, col) == 1)
			return neighbors -1;
		return neighbors;


	}

	/* method to check if the cell will live based on the rules of the game of life
	 * params: int row, int col // this is the position of a cell
	 * return: boolean
	 */
	public boolean willLive(int row, int col){
		int neighbors =  countNeighbors(row, col);
		if (board.isValid(row, col))
		{
			if (isAlive(row, col) == true && neighbors >= 4)
				return hesDeadJim; //(boolean false)
			if (isAlive(row, col) == true && neighbors == 0)
				return hesDeadJim;
			if (isAlive(row, col) == false && neighbors == 3)
				return true;
			if (isAlive(row, col) == true && (neighbors < 4 && neighbors > 0)) //&& neighbors !=3
				return true;
		}
		return false; // return false if the spot is not on the board
	}

	//Calls on the toString in the board class
	//No Params
	//Returns a string representation of the board
	public String toString(){
		return board.toString();
	}

	public static void main(String[] args) {
		System.out.println("The Game of Life");
		System.out.println("----------------" + "\r\n");

		GameOfLife world = new GameOfLife(4,4); //size of board (# of rows, # of columns)
		world.randomize();

		for (int i = 0; i < 11; i++){ //when i<11 prints 10 generations
			System.out.println("Generation: " + world.getGenNum() + "\r\n" + "-------------");
			System.out.print(world + "\n");
			world.nextGeneration();


		}
	}

}
