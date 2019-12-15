import java.util.ArrayList;

//Jeremy Thaller and Zenon Nieduzak P7
//Class to create and define a piece
//the class also contains methods to rotate a piece
//and gain information about it, such as the
//shape and location of its protrusions

public class Piece {

	//the number of rotations of a piece is [0,3]
	private int numRotations;

	public static final int IN_CLUB = -1;
	public static final int IN_DIAMOND = -2;
	public static final int IN_HEART = -3;
	public static final int IN_SPADE = -4;
	public static final int OUT_CLUB = 1;
	public static final int OUT_DIAMOND = 2;
	public static final int OUT_HEART = 3;
	public static final int OUT_SPADE = 4;

	private ArrayList<Integer> piece = new ArrayList<Integer>(4);


	/*Ctor: Piece
	 * Params: int north, int east, int south, int west
	 * Takes in the 4 values for 4 edges of a piece
	 * returns: none
	 */
	public Piece(int north, int east, int south, int west){
		//		piece.clear(); // not sure if needed, but this is for good measure
		piece.add(north);
		piece.add(east);
		piece.add(south);
		piece.add(west);
	}

	//params: none
	//Returns: int (the value of of a piece at north position)
	public int getNorth(){
		return piece.get(0);
	}
	
	//params: none
	//Returns: int (the value of of a piece at east position)
	public int getEast(){
		return piece.get(1);
	}

	//params: none
	//Returns: int (the value of of a piece at south position)
	public int getSouth(){
		return piece.get(2);
	}

	//params: none
	//Returns: int (the value of of a piece at west position)
	public int getWest(){
		return piece.get(3);
	}

	//positive means rotate clockwise. negative is counterclockwise
	public void rotate(int numRot){
		if (numRot == 0)
			return;
		if (numRot %4 == 1 || numRot %4 == -3){ //rotate clockwise by one rotation or cclockwise for 3 rotations
			int temp = piece.get(3);
		
			piece.set(3,piece.get(2));
			piece.set(2,piece.get(1));
			piece.set(1,piece.get(0));
			piece.set(0,temp);
			numRotations+= numRot; //update numRotations
			if (numRotations == 4) //Make sure numRotations is [0,3]
				numRotations =0; 
		}

		if (numRot %4 == 2 || numRot %4 == -2){ //rotate clockwise or cclockwise by 2 rotations
			int temp = piece.get(3);
			int temp2 = piece.get(2); 
			piece.set(2,piece.get(0));
			piece.set(3,piece.get(1));
			piece.set(1, temp);
			piece.set(0,temp2);
			
			numRotations+= numRot;
			if (numRotations == 4)
				numRotations =0;
		}
		if (numRot %4 == 3 || numRot %4 == -1){ //rotate clockwise by 3 rotations or cclockwise by 1 rotation
			int temp = piece.get(3);
			piece.set(1, piece.get(2));
			piece.set(2,piece.get(3));
			piece.set(3,piece.get(0));
			piece.set(0,temp);
			
			numRotations+= numRot; 
			if (numRotations == 4)
				numRotations =0;
		}
	}

	//Params: None
	//Returns: int numRotations
	//Returns the number of rotations of a piece 
	//NumRotations is an int between 0 and 3 inclusive 
	public int getNumRotations(){
		return numRotations;
	}


	/*params: none
	 * Returns: String 
	 * returns a string representation of a piece
	 * 
	 * the returned string looks like a diamond:
	 * e.g.
	 *       N: value
	 *  W: Value      E: Value
	 *       S: Value
	 */
	public String toString(){
		if (piece == null)
		return nullString();
		
		String str = "\tN:" + getNorth()
				+ "\rW:" + getWest() + " \t\tE:"
				+ getEast() + "\r\tS:" + getSouth(); 
		return str;
	}

	/*
	 * Helper method for the toString();
	 * Returns a string representation 
	 * of an "empty piece" (a blank spot):
	 * 
	 * e.g.     N: Null
	 *      W: NUll    E:Null
	 *          S: Null
	 *
	 */
	private String nullString(){
		String str = "\tN:Null"
				+ "\rW:Null" +  " \t\tE:Null"
				+ "\r\tS:Null"; 
		return str;
	}
	
	public static void main(String[] args) {

		Piece pp1 = new Piece(OUT_CLUB,OUT_HEART,IN_DIAMOND,IN_CLUB);
		Piece pp2 = new Piece(OUT_SPADE,OUT_DIAMOND,IN_SPADE,IN_HEART);
		Piece pp3 = new Piece(OUT_HEART,OUT_SPADE,IN_SPADE,IN_CLUB);
		Piece pp4 = new Piece(OUT_HEART,OUT_DIAMOND,IN_CLUB,IN_CLUB);
		Piece pp5 = new Piece(OUT_SPADE,OUT_SPADE,IN_HEART,IN_CLUB);
		Piece pp6 = new Piece(OUT_HEART,OUT_DIAMOND,IN_DIAMOND,IN_HEART);
		Piece pp7 = new Piece(OUT_SPADE,OUT_DIAMOND,IN_HEART,IN_DIAMOND);
		Piece pp8 = new Piece(OUT_CLUB,OUT_HEART,IN_SPADE,IN_HEART);
		Piece pp9 = new Piece(OUT_CLUB,IN_CLUB,IN_DIAMOND,OUT_DIAMOND);
		
		
//		System.out.println(pp3.toString());
//		pp3.rotate(-2);
//		System.out.println(pp3.toString());

		//		System.out.println("orig north:" + pp2.getNorth());
		//		pp2.rotate(-7);
		//		System.out.println("rotate 1 east:" +pp2.getEast());
		//		System.out.println("rotate 1 north:" +pp2.getNorth());
		//		pp2.rotate(-1);
		//		System.out.println("rotate -1 north:" +pp2.getNorth());
		//		pp2.rotate(-3);
		//		System.out.println("rotate -3 north:" +pp2.getNorth());
		//
		//		System.out.println("Piece pp1 is:" + pp1.toString());
		//		pp1.rotate(1);
		//		System.out.println(pp1.toString());
		//		System.out.println("NumRotations =" + pp1.getNumRotations());
		//		pp1.rotate(3);
		//		System.out.println(pp1.getNumRotations());
		//		System.out.println(pp1.toString());





	}

}
