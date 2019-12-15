//Jeremy thaller P4
//Creates a line of text on the screen
//class extends the shape class
public class TextLine extends Shape {
	private String s;

	//ctor
	//params: none
	public TextLine() {
	}
	
	//ctor
	//params: string s
	public TextLine(String s){
		this.s = s;
	}
	
	//paints the text on the screen
	//params: screen screen, intx, int y
	//returns: none
	public void paintOn(Screen screen, int x, int y){
		 
		for (int i = 0; i< s.length(); i++)
				screen.paintAt(y, x+i, s.charAt(i));
	}
	
	//calls the other painton method with the default x,y as 0,0
	//params: screen screen
	//returns: none
	public void paintOn(Screen screen){
		paintOn(screen, 0,0);
}

	public static void main(String[] args) {
		Screen a = new Screen(10,10);
		TextLine T = new TextLine("test");
		T.setChar('0');
		T.paintOn(a,6,0);
		a.draw();
	}
}
	
	