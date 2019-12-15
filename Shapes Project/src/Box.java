//Jeremy Thaller P4
//class that entends rectangle.  Used to create a Box. a box is a filled in rectangle
public class Box extends Rectangle {

	//ctor
	//params: char c
	public Box(char c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	//ctor
	//params: none
	public Box() {
		// TODO Auto-generated constructor stub
	}

	//ctor
	//params: int height, int width, char c
	//calls the super and overrides it
	public Box(int height, int width, char c) {
		super(height, width, c);
	}
	
	//paints a box on the screen
	//params: screen screen, int x, int y
	//returns: nothing
	public void paintOn(Screen screen, int x, int y){
		for (int i = 0; i < getWidth(); i++){
			for (int j = 0; j < getHeight(); j++){
				screen.paintAt(y+i,x+j,c);
			}
		}
	}
	
	public static void main(String[][] args){
		Screen a = new Screen(10,10);
		Box b = new Box(5,5,'0');
		b.paintOn(a, 1, 1);
		a.draw();

	}

}
