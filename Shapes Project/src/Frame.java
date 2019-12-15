//Jeremy Thaller p4
//extends the rectangle class. Class is used to create a frame. 
//a frame is a rectangle without any filling
//if it were an oreo, no one would like it
public class Frame extends Rectangle {

	//ctor
	//params:char c
	//calls super and overrides
	public Frame(char c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	
	//ctor
	//params: none
	public Frame() {
	}

	//ctor:
	//params: int height, int width, char c
	//calls super and overrides
	public Frame(int height, int width, char c) {
		super(height, width, c);
	}

	//paints a frame on the screen
	//param:screen screen, int x, int y
	//returns: none
	public void paintOn(Screen screen, int x, int y){
		//top. includes left most point. does NOT include right top corner
//		if( screen.isValid(getHeight() + y,getWidth() + x) ) {
			for (int i = 0; i < getWidth()-1; i++)
				screen.paintAt(y, x+i, c);
			//sides.  Does NOT include top left corner. DOES include top right corner. 
			//does NOT include base at all
			for (int j = 0; j < getHeight() -1; j++){
				screen.paintAt(y+j,x,c);
				screen.paintAt(y+j, x + getWidth()-1, c); // not sure why i need a minus 1
			}
			//bottom. INCLUDES both bottom end-points
			for(int i = 0; i < getWidth(); i++)
				screen.paintAt(y+getHeight()-1, x+i, c); //DITTO 	
		}
//	}
	
	//paint on sets default coords as 0,0 and calls oth painton method
	//params: screen screen
	//reutrns none
	public void paintOn(Screen screen){
	paintOn(screen,0,0);
	}
	


	public static void main(String[] args) {
		Screen a = new Screen(10,10);
		Frame b = new Frame(5,5,'0');
		b.paintOn(a, 1, 1);
		a.draw();

	}

}
