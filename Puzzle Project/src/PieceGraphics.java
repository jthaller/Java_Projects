import java.awt.image.BufferedImage;

public class PieceGraphics{

	private BufferedImage currentImage;
	private BufferedImage image1;
	private BufferedImage image2;
	private BufferedImage image3;
	private BufferedImage image4;
	private Piece piece;
	private int numRotations;

	public PieceGraphics(int north, int east, int south, int west, BufferedImage image1,BufferedImage image2, BufferedImage image3, BufferedImage image4){
		piece = new Piece(north, east, south, west);
		this.image1 = image1;
		this.image2 = image4;
		this.image3 = image3;
		this.image4 = image2;
		this.currentImage = image1;
		numRotations=0;
	}
	
	public void updateNumRotations(){
		this.numRotations=piece.getNumRotations();
	}

	public Piece getPiece(){
		return piece;
	}

	public int getNumRotations(){
		return piece.getNumRotations();
	}

	public void rotate(int numRotations){

		while(numRotations<0){
			numRotations+=4;
		}
		while(numRotations>=4){
			numRotations-=4;
		}
		piece.rotate(numRotations);
		this.numRotations+=numRotations;
		while(this.numRotations>=4){
			this.numRotations-=4;
		}
		if(this.numRotations==0){
			currentImage = image1;
		}
		else if(this.numRotations==1){
			currentImage = image2;
		}
		else if(this.numRotations==2){
			currentImage = image3;
		}
		else if(this.numRotations==3){
			currentImage = image4;
		}
	}
	
	public BufferedImage getBufferedImage(){
		return currentImage;
	}
	

	public static void main(String[] args){
	}

	public boolean isPiece(Piece piece){
		return super.equals(piece);
	}
}
