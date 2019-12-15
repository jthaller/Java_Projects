/*Jeremy Thaller and Zenon Nieduzak
 * Period 7
 * 
 * Each piece of a puzzle must go in a specific spot 
 * on a predetermined sized grid.  This Board class acts
 * as that grid. The class bases the Board off a a 2D Matrix of type piece.
 * Class includes numerous methods to add, remove, set, clear pieces/spots.
 * Class also contains a toString() to create a string representation of the 
 * board.
 */
/** I have made the BLUE */ 


public class Board {
	private int height;
	private int width;
	private Piece[][] board;
	
	/*Ctor: Board
	 * Params: int width, int height
	 * these are the dimensions of the board
	 * returns: none
	 */
	public Board(int width, int height){
		this.width = width;
		this.height = height;
		board = new Piece[width][height];
	}

	//ctor: Board
	//Params: int dim
	//this is the height and width of the board
	public Board(int dim){
		this(dim,dim); 
	}

	//params: none
	//returns int height
	// this is the height of the board
	public int getHeight(){
		return height;
	}
	
	//params: none
	//returns int width
	// this is the width of the board
	public int getWidth(){
		return width;
	}

	//void clear:
	//params: none
	// clears the board by setting each cell to null
	public void clear(){
		for(int i = 0; i< board.length -1; i++){
			for(int j = 0; j<board.length -1; j++){
				board[i][j] = null;
			}
		}
	}

	
	//Params: int row, int col (this is the desired location)
	//returns: Piece
	//it returns what piece WAS there
	//or it returns null if cell not on board
	public Piece clearCell(int row, int col){
		if (isValid(row,col)){
			Piece cleared = getCell(row, col);
			board [row][col] = null;
			return cleared;}
		return null;
	}

	//changes the value at the spot on the board
	//Params: must know the row, column, and piece
	//Returns: piece that was there
	//Returns: null if empty or not valid spot to set
	public Piece setCell(int row, int col, Piece in){
		if (isValid(row, col)){
			Piece out = getCell(row,col);
			board[row][col] = in;
			return out;
		}
		return null;
	}

	//Params: int row, int Col (this is the location)
	//returns: Piece that's there
	//return returns null if cell not on board
	public Piece getCell(int row, int col){
		if(isValid(row,col))
			return  board[row][col];
		return null;
	}


	/*
	 * Params: Piece piece
	 * Returns: boolean
	 * Takes a piece and searches through
	 * the entire board to determine if it is 
	 * already on the board.
	 * Returns True if piece on board
	 * Returns false if piece is not on board
	 */
	public boolean pieceOnBoard(Piece piece){
		for (int i =0; i<height;i++){
			for (int j=0; j<width; j++){
				Piece temp = board[i][j];
				if (isSamePiece(piece, temp)){
					return true;
				}
			}
		}
		return false;
	}

	//helper method: this method will only be used in this class
	//Params: Piece piece, and Piece temp
	//Checks to see if the two pieces compared are the same
	//returns true if same piece
	//returns false if they are different pieces
	private boolean isSamePiece(Piece piece, Piece temp){
		while (true){
			if (piece.getNorth() != temp.getNorth())
				return false;
			if (piece.getEast() != temp.getEast())
				return false;
			if (piece.getSouth() != temp.getSouth())
				return false;
			if (piece.getWest() != temp.getWest())
				return false;
			return true;
		}
	}


	/*
	 * Params: int row, int col
	 * returns: boolean
	 * return true if the spot is occupied.
	 * returns false if empty or invalid spot
	 */
	public boolean isOccupied(int row, int col){
		if(isValid(row,col)){
			if (board[row][col] == null)
				return false;
			return true;
		}
		return false;
	}

	public boolean isValid(int row, int col){
		return row >=0 && row <this.height && col >=0 && col<this.width;
	}

	
	/*Parms: None
	 * Returns: String 
	 * Returns a string representation of the board with the pieces/blanks spaces currently on it
	 * Don't mess with the code. This took forever to get right.
	 */
	public String toString(){
		String s = "\t\t\t Board\n";
				s += "\t";
		for (int row = 0; row<height; row++){
			for(int col = 0; col < width; col++) {
				if (getCell(row,col) == null){
					s +="N:X" + "\t " + "\t";
				}
				else s+= "N:" + getCell(row,col).getNorth() + "\t" + "\t";
			}
			s += "\r";
			for(int col = 0; col < width; col++) {
				if (getCell(row,col) == null){
					s += "W:X" + "\t\t" + "E:X";
				}
				else s+= "W:"+getCell(row,col).getWest() + "\t\t" + "E:"+getCell(row,col).getEast();
			}
			s += "\r\t";
			for(int col = 0; col < width; col++) {
				if (getCell(row,col) == null){
					s += "S:X" + "\t" + "\t";
				}
				else s+= "S:"+getCell(row,col).getSouth() + "\t" + "\t";
			}
			s += "\r\t";
		}
		return s;
	}


	public static void main(String[] args) {
		final int IN_CLUB = -1;
		final int IN_DIAMOND = -2;
		final int IN_HEART = -3;
		final int IN_SPADE = -4;
		final int OUT_CLUB = 1;
		final int OUT_DIAMOND = 2;
		final int OUT_HEART = 3;
		final int OUT_SPADE = 4;

		Piece pp1 = new Piece(OUT_CLUB,OUT_HEART,IN_DIAMOND,IN_CLUB);
		Piece pp2 = new Piece(OUT_SPADE,OUT_DIAMOND,IN_SPADE,IN_HEART);
		Piece pp3 = new Piece(OUT_HEART,OUT_SPADE,IN_SPADE,IN_CLUB);
		Piece pp4 = new Piece(OUT_HEART,OUT_DIAMOND,IN_CLUB,IN_CLUB);
		Piece pp5 = new Piece(OUT_SPADE,OUT_SPADE,IN_HEART,IN_CLUB);
		Piece pp6 = new Piece(OUT_HEART,OUT_DIAMOND,IN_DIAMOND,IN_HEART);
		Piece pp7 = new Piece(OUT_SPADE,OUT_DIAMOND,IN_HEART,IN_DIAMOND);
		Piece pp8 = new Piece(OUT_CLUB,OUT_HEART,IN_SPADE,IN_HEART);
		Piece pp9 = new Piece(OUT_CLUB,IN_CLUB,IN_DIAMOND,OUT_DIAMOND);
		


		Board b1 = new Board(3);
		//		System.out.println(b1.getHeight());
		//		System.out.println(b1.getWidth());
		//		System.out.println(b1.isValid(0, 0));

		b1.setCell(0, 0, pp3);
		b1.setCell(0, 1, pp4);
		b1.setCell(2, 0, pp5);
		b1.setCell(1, 1, pp6);
		b1.setCell(2, 2, pp7);
		//		System.out.println(b1.getCell(0,0));
		System.out.println(b1.toString()); 
		
		pp3.rotate(2);
		System.out.println(b1.toString()); 

	}

}
