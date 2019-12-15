import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame {

	private PieceGraphics currentPiece;
	private Puzzle puzzle;
	private BoardGraphics pieceBankBoard;
	private BoardGraphics puzzleBoard;
	private JFrame puzzleFrame;
	private boolean isPuzzleBoard;
	private Point p;

	public Frame(PieceGraphics[][] pieces){
		puzzle = new Puzzle(3,3,pieces[0][0].getPiece(),pieces[0][1].getPiece(),pieces[0][2].getPiece(),pieces[1][0].getPiece(),pieces[1][1].getPiece(),pieces[1][2].getPiece(),pieces[2][0].getPiece(),pieces[2][1].getPiece(),pieces[2][2].getPiece());
		pieceBankBoard = new BoardGraphics(pieces,false);
		puzzleBoard = new BoardGraphics(true);
		puzzleFrame = new JFrame();
		puzzleFrame.setVisible(true);
		puzzleFrame.setSize(1068,384);
		puzzleFrame.setResizable(false);
		puzzleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		puzzleFrame.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				currentPiece = getPiece(arg0.getPoint());
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				if(currentPiece!=null){
					setPiece(arg0.getPoint());
				}
				currentPiece=null;
				pieceBankBoard.repaint();
				puzzleBoard.repaint();
			}

		});
		
		puzzleFrame.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				p = arg0.getPoint();
				puzzleFrame.repaint();
				System.out.println(p);
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}); 

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout());
		buttonPanel.setBackground(Color.RED);
		JButton solve = new JButton();
		solve.setText("SOLVE");
		final JButton clear = new JButton();
		clear.setText("CLEAR");
		buttonPanel.add(solve);
		buttonPanel.add(clear);

		puzzleFrame.setLayout(new GridLayout(1,3));
		puzzleFrame.add(pieceBankBoard);
		puzzleFrame.add(puzzleBoard);
		puzzleFrame.add(buttonPanel);

		solve.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				clear.doClick();
				puzzle.solve();
				for(int i = 0; i<3; i++){
					for(int j = 0; j<3; j++){
						Piece piece = puzzle.getCell(i,j);
						PieceGraphics pieceG = pieceBankBoard.getPieceGraphics(piece);
						pieceG.updateNumRotations();
						puzzleBoard.add(pieceBankBoard.removePiece(pieceG, puzzle));
					}
				}
			}
		});


		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				PieceGraphics nextPiece = puzzleBoard.getNextPiece();
				while(nextPiece!=null){
					pieceBankBoard.add(nextPiece);
					nextPiece = puzzleBoard.getNextPiece();
				}
				puzzleFrame.repaint();

				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						puzzle.clearCell(i, j);
					}
				}

				
			}
		});
		
		JPanel glassPane = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)(g);
				g2.drawImage(Frame.this.currentPiece.getBufferedImage(), p.x, p.y, null);
				System.out.println("hello");
			}
		};
		glassPane.setOpaque(false);
		glassPane.setVisible(true);
		puzzleFrame.setGlassPane(glassPane);
	} //end constructor

	public void swap(Point p){

	}

	private void setPiece(Point p){
		int xCoor = p.x;
		int yCoor = p.y;
		if(xCoor<357){
			if(pieceBankBoard.getPiece(xCoor-3,yCoor-26)==currentPiece){
				currentPiece.rotate(1);
			}
			if(pieceBankBoard.getPiece(xCoor-3,yCoor-26)==null){
				pieceBankBoard.setPiece(xCoor-3, yCoor-26, currentPiece);
				
				if(isPuzzleBoard){
					puzzleBoard.removePiece(currentPiece, puzzle);
				}
				else
					pieceBankBoard.removePiece(currentPiece, puzzle);
			}
		}

		else{
			xCoor-=357;
			yCoor-=26;
			if(xCoor<60||xCoor>270||yCoor<60||yCoor>270)
				return;
			if(puzzleBoard.getPiece(xCoor,yCoor)==null&&puzzleBoard.pieceFits(xCoor,yCoor,puzzle,currentPiece)){
				puzzleBoard.setPiece(xCoor,yCoor,currentPiece, puzzle);
				if(isPuzzleBoard){
					puzzleBoard.removePiece(currentPiece, puzzle);
				}
				else
					pieceBankBoard.removePiece(currentPiece, puzzle);
			}
			else if(puzzleBoard.getPiece(xCoor, yCoor)==currentPiece){
				currentPiece.rotate(1);
			}
		}
		pieceBankBoard.repaint();
		puzzleBoard.repaint();
	}

	private PieceGraphics getPiece(Point p){
		int xCoor = p.x;
		int yCoor = p.y;
		if(xCoor<357){ 
			isPuzzleBoard = false;
			return pieceBankBoard.getPiece(xCoor-3,yCoor-26);
		}
		else{
			isPuzzleBoard = true;
			return puzzleBoard.getPiece(xCoor-357,yCoor-26);
		}

	}

	public static void main(String[] args){
		ImageLoader images = new ImageLoader();
		new Frame(images.getPieceGraphics());
	}

}
