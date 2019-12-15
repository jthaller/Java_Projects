//Jeremy THaller P4
//Class entends the line class.  Creates a Vertical Line
public class VLine extends Line {

	//ctor
	//params: int length
	//calls super
	public VLine(int length){
		super(length);}

	//ctor
	//params:none
	//calls super
	public VLine(){
		super();
	}

	//paints a vertical line on the screen
	//params: screen screen, int x, int y
	//returns none
	public void paintOn(Screen screen, int x, int y){
		for (int j = 0; j< getLength(); j++)
			screen.paintAt(j+y, x, screen.getChar());
	}

	//calls other paint on method with default x,y as 0,0
	//params: screen screen
	public void paintOn(Screen screen){
		paintOn(screen, 0,0);
	}
	public static void main(String[][] args){
		Screen a = new Screen(10,10);
		VLine V = new VLine(5);
		V.setChar('0');
		V.paintOn(a, 6,0 );
		a.draw();

	}
}
