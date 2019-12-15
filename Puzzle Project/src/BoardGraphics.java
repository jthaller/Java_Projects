/*
 * Authors: Danny Bloom
 * 			Lydia Sanchetta
 * 			Tucker Penney
 * Date: April 6, 2015
 * Description: JComponent that is a board.
 */

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JComponent;


@SuppressWarnings("serial")
public class BoardGraphics extends JComponent {

	private final int width = 3;
	private final int height = 3;
	private boolean isPuzzle;
	private PieceGraphics[][] pieceGraphics;
	private Frame frame;

	public boolean isPuzzle() {
		return isPuzzle;
	}

	public void setPuzzle(boolean isPuzzle) {
		this.isPuzzle = isPuzzle;
	}

	public BoardGraphics(PieceGraphics[][] pieceGraphics, boolean isPuzzle){
		this.isPuzzle = isPuzzle;
		this.pieceGraphics=pieceGraphics;
	} //end ctor 1

	public BoardGraphics(boolean isPuzzle){
		pieceGraphics = new PieceGraphics[width][height];
		this.isPuzzle = isPuzzle;
		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				pieceGraphics[i][j] = null;
			}
		}
	}
	
	public boolean pieceFits(int xCoor, int yCoor, Puzzle puzzle, PieceGraphics piece){
		System.out.println(puzzle.isValidFit((xCoor-60)/70,(yCoor-60)/70,piece.getPiece()));
		return puzzle.isValidFit((yCoor-60)/70,(xCoor-60)/70,piece.getPiece());
	}

	public PieceGraphics clearCell(int row, int col){
		PieceGraphics temp = pieceGraphics[row][col];
		pieceGraphics[row][col] = null;
		return temp;
	}
	
	
	//	public PieceGraphics setCell(int row, int col, PieceGraphics piece){
	//		return pieceGraphics.get(row).set(col, piece);
	//	}



	/*
	 * Ctor 1: Takes a width and a height (ie 3 x 3 board)
	 */

	public PieceGraphics getPieceGraphics(Piece piece){
		for(PieceGraphics[] pieceGs: pieceGraphics){
			for(PieceGraphics pieceG: pieceGs)
				if(pieceG.isPiece(piece)){
					return pieceG;
				} //end if
		} //end for loop
		return null;
	} //end getPieceGraphics

	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		if(isPuzzle){
			for(int i = 0; i<4; i++){
				g2.drawLine(60+i*70,60,60+i*70,270);
			}
			for(int i = 0; i<4; i++){
				g2.drawLine(60, 60+i*70, 270, 60+i*70);
			}
		}
		else{
			for(int i = 0; i<3; i++){
				for(int j = 0; j<3; j++){
					g2.drawRect(24+118*i, 24+118*j, 70, 70);
				}
			}

		}
		int locationX;
		int locationY;
		for(int i = 0; i<width; i++){
			for(int j = 0; j<height; j++){
				if(pieceGraphics[j][i]!=null){

					if (isPuzzle==true) { 
						locationX = i * 70 + 36;
						locationY = j * 70 + 36;
					}

					else {
						locationX = i * 118;
						locationY = j * 118;
					}
					g2.drawImage((Image)pieceGraphics[j][i].getBufferedImage(), locationX, locationY, null);
				}
			}
		}
	} //end paintComponent 

	public PieceGraphics getPiece(int x, int y){
		if(isPuzzle){
			if(x<60||x>264||y<60||y>264){
				return null;
			}
			else return pieceGraphics[(y-60)/70][(x-60)/70];
		}
		else{
			return pieceGraphics[y/118][x/118];
		}

	}

	public void setPiece(int x, int y, PieceGraphics piece, Puzzle puzzle){
		if(isPuzzle){
			pieceGraphics[(y-60)/70][(x-60)/70] = piece;
			puzzle.setCell((y-60)/70,(x-60)/70,piece.getPiece());
			
		}
		else{
			pieceGraphics[y/118][x/118] = piece;
		}
	}
	
	public void setPiece(int x, int y, PieceGraphics piece){
			pieceGraphics[y/118][x/118] = piece;
	}

	public PieceGraphics removePiece(PieceGraphics piece, Puzzle puzzle){
		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				if(pieceGraphics[i][j]!=null){
					if(pieceGraphics[i][j].equals(piece)){
						PieceGraphics temp = pieceGraphics[i][j];
						pieceGraphics[i][j] = null;
						puzzle.clearCell(j, i);
						return temp;
					}
				}
			}
		}
		return null;
	}
	
	public PieceGraphics getNextPiece(){
		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				if(pieceGraphics[i][j]!=null){
					PieceGraphics piece = pieceGraphics[i][j];
					pieceGraphics[i][j] = null;
					return piece;
				}
			}
		}
		return null;
	}
	
	public void add(PieceGraphics piece){
		for(int i = 0;i<3;i++){
			for(int j = 0; j<3; j++){
				if(pieceGraphics[i][j]==null){
					pieceGraphics[i][j]=piece;
					return;
				}
			}
		}
	}
	
	
	public static void main(String[] args){
		//		ImageLoader images = new ImageLoader();
		//		BoardGraphics board = new BoardGraphics(images.getPieceGraphics(),false);
		//		JFrame frame = new JFrame();
		//		frame.setSize(220,250);
		//		frame.setVisible(true);
		//		frame.setResizable(true);
		//		frame.add(board);

	}
} //end public class BoardGraphics extends JComponent
