import java.awt.geom.Point2D;
import java.util.ArrayList;

/*
 * Puzzle class (actually runs the game) 
Some form of storing puzzle Pieces
private Board board
Puzzle(int width, int height, Pieces… pieces)
Piece clearCell(int row, int col)
Piece setCell(int row, int col, Piece piece)
Piece getCell(int row, int col)
ArrayList<Piece> pieceBank() // gives back list of remaining pieces
boolean isSolved()
void solve() //algorithm, can have helper methods
boolean isValidFit(int row, int col, Piece piece)
String toString()
 */
public class Puzzle {

	private Board board;
	private ArrayList<Piece> PiecesNotOnBoard;

	public Puzzle(int width, int height, Piece...pieces){
		this.board = new Board(width, height);
		this.PiecesNotOnBoard = new ArrayList<Piece>();
		for(int i=0; i<pieces.length;i++){
			//			System.out.println(pieces[i]);
			PiecesNotOnBoard.add(pieces[i]);
		}
	}

		public Piece clearCell(int row, int col){
			if(board.isValid(row, col)){
				if(board.isOccupied(row, col)){
					PiecesNotOnBoard.add(getCell(row, col));
//					System.out.println(getCell(row,col));
					return board.clearCell(row, col);
				}
			}
			return null;
		}

//	public void clearCell(int row, int col){
//		if(board.isValid(row, col)){
//			if(board.isOccupied(row, col)){
//				PiecesNotOnBoard.add(getCell(row, col));
//				//				System.out.println(getCell(row,col));
//				board.clearCell(row, col);
//			}
//		}
//		//	return null;
//	}



	public int getHeight(){
		return board.getHeight();
	}


	public int getWidth(){
		return board.getWidth();
	}

	public Piece setCell(int row , int col, Piece piece){
		//		System.out.println(PiecesNotOnBoard);
		if(isValidFit(row, col, piece)){
			if(board.isOccupied(row, col)){
				PiecesNotOnBoard.add(board.getCell(row, col));
			}
			if(PiecesNotOnBoard.contains(piece)){
				PiecesNotOnBoard.remove(piece);
			}
			return board.setCell(row, col, piece);
		}	
		//		System.out.println(PiecesNotOnBoard);

		return null;
	}	

	public Piece getCell(int row, int col){
		if(board.isValid(row, col))
			if(board.isOccupied(row, col))
				return board.getCell(row,  col);
		return null;
	}

	public ArrayList<Piece> pieceBank(){
		return PiecesNotOnBoard; 
	}

	public boolean isSolved(){
		for(int i = 0; i<board.getHeight();i++){
			for(int j = 0; j<board.getWidth();j++){
				if(board.getCell(i, j)==null)
					return false;
			}
		}
		return true;
	}



	//	private Point2D nextOpenCell(){
	//		if (PiecesNotOnBoard.size() == 0){
	//			Point2D done = new Point2D.Double(-1, -1);
	//			return done;
	//
	//		}
	//		for(int i=0; i<board.getHeight();i++){
	//			for(int j=0; j<board.getWidth();j++){
	//				if (board.getCell(i, j)==null){
	//					Point2D found = new Point2D.Double(i,j);
	//					return found;
	//				}
	//			}
	//
	//		}
	//		Point2D done = new Point2D.Double(-1, -1); //bs return needed
	//		return done;
	//	}

	//	public void solve(){
	//		clear();
	//	
	////						while(PiecesNotOnBoard.size() != 0)
	//		//		System.out.println("Cleared start over");
	//		Psolve(); //PSolve(0,0);
	//		//	}
	//	}
	//
	//		private void Psolve(){
	//		
	////			System.out.println(PiecesNotOnBoard);
	////			System.out.println(board.toString());
	//			if (PiecesNotOnBoard.size() == 0){
	////				System.out.println("done 1");
	//				return;}
	//			int row = (int)nextOpenCell().getX(); 
	//			int col = (int)nextOpenCell().getY();
	//			for(int i = PiecesNotOnBoard.size()-1; i>-1; i--){
	////				if(PiecesNotOnBoard.size() ==0) System.out.println("finished?");
	//				Piece test = PiecesNotOnBoard.get(i);
	//
	//				for(int j=0; j<4; j++){
	//					if (isValidFit(row,col,test)){
	//						setCell(row,col,test);
	//						if (PiecesNotOnBoard.size() ==0){
	////							System.out.println("done 2");
	////							System.out.println(PiecesNotOnBoard);
	////							System.out.println(PiecesNotOnBoard.size());
	////							System.out.println(board.toString()); /**only gets here once*/
	////							System.exit(0);
	//							return;
	//						}
	//						Psolve();
	//						if(isSolved()) return; //wouldn't this be unreachable???
	//					}
	//					test.rotate(1);
	//				}
	//				clearCell(row,col);
	//			}
	//		}



	//	loop over the piecesNOtOnBoard
	//	loop over 3 rotations
	//	try and place the piece.
	//	if you placed it recurse
	//	if you're finished return true
	//	if you get passed that
	//	then take out the piece you just placed
	//	rotate it
	//	and then if you get out of all the loops return false
	//	and at the start of the helper if you're done return true
	//you could be tricksy and pass your next index as a param recursively
	//or you could just have a function to find the 
	//next empty place on the board
	public void solve(){
		clear(); //first thing to do is clear board
		bestSolve(0); 
	}

	/*break the board up into 9 spaces. like below
	 * [0 1 2]
	 * |3 4 5|
	 * [6 7 8]
	 * 
	 * index/3 gives row, index%3 gives col
	 */
	private void bestSolve(int index){
		if (PiecesNotOnBoard.size() == 0){
			return;}

		for(int i = PiecesNotOnBoard.size()-1; i>-1; i--){ // loop over pieces not on board 
			//				if(PiecesNotOnBoard.size() ==0) System.out.println("finished?");
			Piece test = PiecesNotOnBoard.get(i);

			for(int j=0; j<4; j++){ //loop over rotations
				if (isValidFit(index/3,index%3,test)){ //if it fits...   
					setCell(index/3,index%3,test); //put the piece in the spot
					if (PiecesNotOnBoard.size() ==0){ //if puzzle's done, finish
						return;
					}
					bestSolve(index+1); // if piece fit, great, recurse
					if(isSolved()) return; //if puzzle's solved, get out
				}
				//				System.out.println("rotated\n" + getCell(row,col).toString());
				test.rotate(1); //if piece didn't fit, rotate it
			}
			//				System.out.println("done clear");
			clearCell(index/3,index%3); //if nothing fits, get rid of previous spot
		}
	}


	public boolean isOccupied(int row, int col){
		if(board.isValid(row, col)){
			if(board.isOccupied(row, col)){
				return true;
			}
		}
		return false;
	}

	public boolean isValidFit(int row, int col, Piece piece){
		if(!isValid(row,col)){
			return false;
		}

		if(isValid(row-1, col)){
			if(board.getCell(row-1, col)!=null&&board.getCell(row-1, col).getSouth()+piece.getNorth()!=0){
				return false;
			}
		}
		if(isValid(row, col-1)){
			if(board.getCell(row,col-1)!=null&&board.getCell(row,col-1).getEast()+piece.getWest()!=0){
				return false;
			}
		}
		if(isValid(row+1, col)){
			if(board.getCell(row+1, col)!=null&&board.getCell(row+1,col).getNorth()+piece.getSouth()!=0){
				return false;
			}
		}
		if(isValid(row, col+1)){
			if(board.getCell(row, col+1)!=null&&board.getCell(row,col+1).getWest()+piece.getEast()!=0){
				return false;
			}
		}
		return true;
	}

	private boolean isValid(int row, int col) {
		return board.isValid(row, col);
	}

	public String toString(){
		return board.toString();
	}

	public void clear(){
		for(int i=0; i<board.getWidth();i++){
			for(int j=0; j<board.getHeight();j++){
				clearCell(i, j);
			}
		}
	}

	//	private static int getNum(){
		//		return PiecesNotOnBoard.size();
	//	}
	public static void main(String[] args){

		Piece pp1 = new Piece(1,3,-2,-1);
		Piece pp3 = new Piece(4,2,-4,-3);
		Piece pp2 = new Piece(3,4,-4,-1);
		Piece pp4 = new Piece(3,2,-1,-1);
		Piece pp5 = new Piece(4,4,-3,-1);
		Piece pp6 = new Piece(3,2,-2,-3);
		Piece pp8 = new Piece(4,2,-3,-2);
		Piece pp9 = new Piece(1,3,-4,-3);
		Piece pp7 = new Piece(2,1,-1,-2);
		Puzzle a = new Puzzle(3, 3, pp1, pp2, pp3, pp4, pp5, pp6, pp7, pp8, pp9);
		Puzzle b = new Puzzle(1,1, pp1,pp2,pp3);
		System.out.println(a.toString());
		System.out.println("\n");
		//		a.setCell(0, 0, pp2);
		//		a.setCell(0, 2, pp1);
		//		a.setCell(0, 0, pp3);
		//		System.out.println(a.toString());
		//		System.out.println(a.nextOpenCell());
		//		System.out.println(a.pieceBank());
		//		a.setCell(0, 0, pp4);
		//		System.out.println(a.toString());
		//		System.out.println(a.pieceBank());
		//		a.setCell(1, 0, pp1);
		//		System.out.println(a.toString());
		//		a.setCell(0, 1, pp7);
		//		System.out.println(a.toString());
		//		a.clear();
		//		System.out.println(a.toString());
		a.solve();
		//		System.out.println(getNum());
		//		a.setCell(0,0,pp1);
		System.out.println(a.toString());
		//		System.out.println(getNum());
		//		a.setCell(0, 2, pp2);
		//		System.out.println(a.toString());
		//		System.out.println(getNum());
		//		a.setCell(2,0,pp3);
		//		System.out.println(a.toString());
		//	
		//		System.out.println(getNum());
		//		a.clear();
		//		System.out.println(a.toString());
		//		System.out.println(getNum());
	}

}







//if(board.isValid(row, col)){
//if(row==0){
//	if(col==0){
//		if(board.getCell(row+1, col)!=null&&board.getCell(row, col+1)!=null){
//			if(piece.getSouth()+board.getCell(row, col+1).getNorth()==0&& piece.getEast()+board.getCell(row+1, col).getWest()==0){
//				return true;
//			}
//		}
//	}
//	if(board.getCell(row+1, col)!=null&&board.getCell(row, col+1)!=null&&board.getCell(row, col-1)!=null){
//		if(piece.getSouth()+board.getCell(row, col+1).getNorth()==0&& piece.getEast()+board.getCell(row+1, col).getWest()==0 && piece.getWest()+board.getCell(row-1, col).getEast()==0){
//			return true;
//		}
//	}
//}
//if(col==0){
//	if(board.getCell(row+1, col)!=null&&board.getCell(row, col+1)!=null&&board.getCell(row-1, col)!=null){
//		if(piece.getSouth()+board.getCell(row, col+1).getNorth()==0&& piece.getEast()+board.getCell(row+1, col).getWest()==0 && piece.getNorth()+board.clearCell(row, col+1).getSouth()==0){
//			return true;
//		}
//	}
//}
//if(row==board.getWidth()){
//	if(col==board.getHeight()){
//		if(board.getCell(row-1, col)!=null&&board.getCell(row, col-1)!=null){
//			if(piece.getNorth()+board.getCell(row, col-1).getSouth()==0&&piece.getWest()+board.getCell(row-1, col).getEast()==0){
//				return true;
//			}
//		}
//	}
//	if(board.getCell(row-1, col)!=null&&board.getCell(row, col-1)!=null&&board.getCell(row, col+1)!=null){
//		if(piece.getNorth()+board.getCell(row, col-1).getSouth()==0&&piece.getWest()+board.getCell(row-1, col).getEast()==0&&piece.getEast()+board.getCell(row, col+1).getWest()==0){
//			return true;
//		}
//	}
//}
//if(col==board.getHeight()){
//	if(board.getCell(row-1, col)!=null&&board.getCell(row, col-1)!=null&&board.getCell(row, col)!=null){
//		if(piece.getNorth()+board.getCell(row, col-1).getSouth()==0&&piece.getWest()+board.getCell(row-1, col).getEast()==0&&piece.getSouth()+board.getCell(row+1, col).getWest()==0){
//			return true;
//		}
//	}
//}
//if(piece.getSouth()+board.getCell(row, col+1).getNorth()==0&& piece.getEast()+board.getCell(row+1, col).getWest()==0 && piece.getNorth()+board.clearCell(row, col+1).getSouth()==0&&piece.getWest()+board.getCell(row+1, col).getEast()==0){
//	return true;
//}else
//	return false;
//}
//return false;

//public void solve3(){
//	for(int i=0; i<board.getHeight();i++){
//		for(int j=0; j<board.getWidth();j++){
//			for(int k=0; k<PiecesNotOnBoard.size();k++){
//				for(int l=0; l<3;l++){
//					setCell(i,j,PiecesNotOnBoard.get(k));
//					//						System.out.println(board.toString());
//					if(!isOccupied(i, j)){
//						PiecesNotOnBoard.get(k).rotate(1);
//					}
//					if(isOccupied(i, j)){
//						l=5;
//						k=PiecesNotOnBoard.size();
//					}
//				}
//			}
//		}
//	}
//	//		System.out.println(pieceBank());
//	if(isSolved()==false){
//		clear();
//		Piece piece = PiecesNotOnBoard.get(0);
//		PiecesNotOnBoard.remove(0);
//		PiecesNotOnBoard.add(piece);
//		solve();
//	}
//}

//public void solve4(){
//int i,j;
//setCell(0,0, PiecesNotOnBoard.get(0)); //pick first piece to start with
//for (j=1;j<board.getWidth(); j++){ //build first row of solution
//	//remove any attached pieces e/w here [0][j]
//
//}
//for (i =1; i<board.getWidth();i++){
//	//remove any attached n/s here [i][0]
//	for(j=1;j<board.getHeight(); j++){
//		//remove attached pieces [i][j]
//	}
//}
//}

//public void solve5(){
//if(PiecesNotOnBoard.size() == 0)
//	return;
//setCell(0,0,PiecesNotOnBoard.get(0)); //sets this as corner. assumes correct
//Psolve5();
//
//}

//public void Psolve5(){
//for(int rot=0; rot<4; rot++){
//	for(int i=0; i<PiecesNotOnBoard.size(); i++){
//		int row = (int)nextOpenCell().getX(); int col = (int)nextOpenCell().getY();
//		for(int j=0; rot<4; j++){
//			if(setCell((int)nextOpenCell().getX(), (int)nextOpenCell().getY(),PiecesNotOnBoard.get(i)) != null)
//				if(PiecesNotOnBoard.size() == 0)
//					return;
//			solve5();
//		}
//		clearCell(row,col);
//	}
//	if (board.getCell(0,0).getNumRotations() != 0){
//		board.getCell(0,0).rotate(1);
//		solve5();
//	}
//}
//if(!isSolved()){
//	clear();
//	Piece piece = PiecesNotOnBoard.get(0);
//	PiecesNotOnBoard.remove(0);
//	PiecesNotOnBoard.add(piece);
//	solve5();
//}
//}

//public void solve2(){
//		for(int i=0; i<board.getHeight();i++){
//			for(int j=0; j<board.getWidth();j++){
//				for(int k=0; k<PiecesNotOnBoard.size();k++){
//					for(int l=0; l<3;l++){
//						setCell(i,j,PiecesNotOnBoard.get(k));
//						//						System.out.println(board.toString());
//						if(!isOccupied(i, j)){
//							PiecesNotOnBoard.get(k).rotate(1);
//						}
//						if(isOccupied(i, j)){
//							l=5;
//							k=PiecesNotOnBoard.size();
//						}
//					}
//				}
//			}
//		}
//		//		System.out.println(pieceBank());
//		if(isSolved()==false){
//			clear();
//			Piece piece = PiecesNotOnBoard.get(0);
//			PiecesNotOnBoard.remove(0);
//			PiecesNotOnBoard.add(piece);
//			solve();
//		}
//	}