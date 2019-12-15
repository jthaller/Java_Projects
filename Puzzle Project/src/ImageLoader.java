import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageLoader {

	private PieceGraphics[][] pieces;

	public ImageLoader(){
		pieces = new PieceGraphics[3][3];
		BufferedImage image11 = null;
		try {
			image11 = ImageIO.read(getClass().getResource("/piece_1.1.png"));
		} catch (IOException e) {
		}
		BufferedImage image12 = null;
		try {
			image12 = ImageIO.read(getClass().getResource("/piece_1.2.png"));
		} catch (IOException e) {
		}
		BufferedImage image13 = null;
		try {
			image13 = ImageIO.read(getClass().getResource("/piece_1.3.png"));
		} catch (IOException e) {
		}
		BufferedImage image14 = null;
		try {
			image14 = ImageIO.read(getClass().getResource("/piece_1.4.png"));
		} catch (IOException e) {
		}
		PieceGraphics pieceG1 = new PieceGraphics(Piece.OUT_CLUB, Piece.OUT_HEART, Piece.IN_DIAMOND, Piece.IN_CLUB, image11,image12,image13,image14);
		pieces[0][0] = pieceG1;


		//Piece 2
		BufferedImage image21 = null;
		try {
			image21 = ImageIO.read(getClass().getResource("/piece_2.1.png"));
		} catch (IOException e) {
		}
		BufferedImage image22 = null;
		try {
			image22 = ImageIO.read(getClass().getResource("/piece_2.2.png"));
		} catch (IOException e) {
		}
		BufferedImage image23 = null;
		try {
			image23 = ImageIO.read(getClass().getResource("/piece_2.3.png"));
		} catch (IOException e) {
		}
		BufferedImage image24 = null;
		try {
			image24 = ImageIO.read(getClass().getResource("/piece_2.4.png"));
		} catch (IOException e) {
		}
		PieceGraphics pieceG2 = new PieceGraphics(Piece.OUT_SPADE, Piece.OUT_DIAMOND, Piece.IN_SPADE, Piece.IN_HEART,image21,image22,image23,image24);
		pieces[0][1] = pieceG2;


		//Piece 3
		BufferedImage image31 = null;
		try {
			image31 = ImageIO.read(getClass().getResource("/piece_3.1.png"));
		} catch (IOException e) {
		}
		BufferedImage image32 = null;
		try {
			image32 = ImageIO.read(getClass().getResource("/piece_3.2.png"));
		} catch (IOException e) {
		}
		BufferedImage image33 = null;
		try {
			image33 = ImageIO.read(getClass().getResource("/piece_3.3.png"));
		} catch (IOException e) {
		}
		BufferedImage image34 = null;
		try {
			image34 = ImageIO.read(getClass().getResource("/piece_3.4.png"));
		} catch (IOException e) {
		}
		PieceGraphics pieceG3 = new PieceGraphics(Piece.OUT_HEART, Piece.OUT_SPADE, Piece.IN_SPADE, Piece.IN_CLUB,image31,image32,image33,image34);
		pieces[0][2] = pieceG3;


		//Piece 4
		BufferedImage image41 = null;
		try {
			image41 = ImageIO.read(getClass().getResource("/piece_4.1.png"));
		} catch (IOException e) {
		}
		BufferedImage image42 = null;
		try {
			image42 = ImageIO.read(getClass().getResource("/piece_4.2.png"));
		} catch (IOException e) {
		}
		BufferedImage image43 = null;
		try {
			image43 = ImageIO.read(getClass().getResource("/piece_4.3.png"));
		} catch (IOException e) {
		}
		BufferedImage image44 = null;
		try {
			image44 = ImageIO.read(getClass().getResource("/piece_4.4.png"));
		} catch (IOException e) {
		}
		PieceGraphics pieceG4 = new PieceGraphics(Piece.OUT_HEART, Piece.OUT_DIAMOND, Piece.IN_CLUB, Piece.IN_CLUB,image41,image42,image43,image44);
		pieces[1][0] = pieceG4;

		//Piece 5
		BufferedImage image51 = null;
		try {
			image51 = ImageIO.read(getClass().getResource("/piece_5.1.png"));
		} catch (IOException e) {
		}
		BufferedImage image52 = null;
		try {
			image52 = ImageIO.read(getClass().getResource("/piece_5.2.png"));
		} catch (IOException e) {
		}
		BufferedImage image53 = null;
		try {
			image53 = ImageIO.read(getClass().getResource("/piece_5.3.png"));
		} catch (IOException e) {
		}
		BufferedImage image54 = null;
		try {
			image54 = ImageIO.read(getClass().getResource("/piece_5.4.png"));
		} catch (IOException e) {
		}
		PieceGraphics pieceG5 = new PieceGraphics(Piece.OUT_SPADE, Piece.OUT_SPADE, Piece.IN_HEART, Piece.IN_CLUB,image51,image52,image53,image54);
		pieces[1][1] = pieceG5;

		//Piece 6
		BufferedImage image61 = null;
		try {
			image61 = ImageIO.read(getClass().getResource("/piece_6.1.png"));
		} catch (IOException e) {
		}
		BufferedImage image62 = null;
		try {
			image62 = ImageIO.read(getClass().getResource("/piece_6.2.png"));
		} catch (IOException e) {
		}
		BufferedImage image63 = null;
		try {
			image63 = ImageIO.read(getClass().getResource("/piece_6.3.png"));
		} catch (IOException e) {
		}
		BufferedImage image64 = null;
		try {
			image64 = ImageIO.read(getClass().getResource("/piece_6.4.png"));
		} catch (IOException e) {
		}
		PieceGraphics pieceG6 = new PieceGraphics(Piece.OUT_HEART, Piece.OUT_DIAMOND, Piece.IN_DIAMOND, Piece.IN_HEART,image61,image62,image63,image64);
		pieces[1][2] = pieceG6;

		//Piece 7
		BufferedImage image71 = null;
		try {
			image71 = ImageIO.read(getClass().getResource("/piece_7.1.png"));
		} catch (IOException e) {
		}
		BufferedImage image72 = null;
		try {
			image72 = ImageIO.read(getClass().getResource("/piece_7.2.png"));
		} catch (IOException e) {
		}
		BufferedImage image73 = null;
		try {
			image73 = ImageIO.read(getClass().getResource("/piece_7.3.png"));
		} catch (IOException e) {
		}
		BufferedImage image74 = null;
		try {
			image74 = ImageIO.read(getClass().getResource("/piece_7.4.png"));
		} catch (IOException e) {
		}
		PieceGraphics pieceG7 = new PieceGraphics(Piece.OUT_SPADE, Piece.OUT_DIAMOND, Piece.IN_HEART, Piece.IN_DIAMOND,image71,image72,image73,image74);
		pieces[2][0] = pieceG7;

		//Piece 8
		BufferedImage image81 = null;
		try {
			image81 = ImageIO.read(getClass().getResource("/piece_8.1.png"));
		} catch (IOException e) {
		}
		BufferedImage image82 = null;
		try {
			image82 = ImageIO.read(getClass().getResource("/piece_8.2.png"));
		} catch (IOException e) {
		}
		BufferedImage image83 = null;
		try {
			image83 = ImageIO.read(getClass().getResource("/piece_8.3.png"));
		} catch (IOException e) {
		}
		BufferedImage image84 = null;
		try {
			image84 = ImageIO.read(getClass().getResource("/piece_8.4.png"));
		} catch (IOException e) {
		}
		PieceGraphics pieceG8 = new PieceGraphics(Piece.OUT_CLUB, Piece.OUT_HEART, Piece.IN_SPADE, Piece.IN_HEART,image81,image82,image83,image84);
		pieces[2][1] = pieceG8;

		//Piece 9
		BufferedImage image91 = null;
		try {
			image91 = ImageIO.read(getClass().getResource("/piece_9.1.png"));
		} catch (IOException e) {
		}
		BufferedImage image92 = null;
		try {
			image92 = ImageIO.read(getClass().getResource("/piece_9.2.png"));
		} catch (IOException e) {
		}
		BufferedImage image93 = null;
		try {
			image93 = ImageIO.read(getClass().getResource("/piece_9.3.png"));
		} catch (IOException e) {
		}
		BufferedImage image94 = null;
		try {
			image94 = ImageIO.read(getClass().getResource("/piece_9.4.png"));
		} catch (IOException e) {
		}
		PieceGraphics pieceG9 = new PieceGraphics(Piece.OUT_DIAMOND, Piece.OUT_CLUB, Piece.IN_CLUB, Piece.IN_DIAMOND,image91,image92,image93,image94);
		pieces[2][2] = pieceG9;
	}

	public PieceGraphics[][] getPieceGraphics(){
		return pieces;
	}

	public static void main(String[] args){

	}
}
