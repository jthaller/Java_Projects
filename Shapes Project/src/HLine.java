//Jeremy THaller P4
//this Class extends the line class
//Class creates a horizontal line.  allocates most of its work to other classes.
public class HLine extends Line {

	//ctor
	//params:int length
	//calls super and overrides
	public HLine(int length){
		super(length);
	}
	
	//ctor
	//params:none
	//calls the default super
	public HLine(){
		super();
	}
	
	//paints a horizontal line on the screen
	//params:screen screen, int x, inty
	//returns none
	public void paintOn(Screen screen, int x, int y){
		 
		for (int i = 0; i< getLength(); i++)
				screen.paintAt(y, i+x, screen.getChar());
	}
	
	//calls other painton method with default x,y as 0,0
	//params: screen screen
	//returns: none
	public void paintOn(Screen screen){
		paintOn(screen, 0,0);
	}



public static void main(String[][] args){
	System.out.println("Test");
	Screen a = new Screen(10,10);
	HLine H = new HLine(5);
	H.setChar('0');
	H.paintOn(a, 6,0 );
	a.draw();

}
}