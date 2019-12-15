//Jeremy Thaller P4
//Abastract class to provide framework for methods that all shapes share
public abstract class Shape {
	private int y;
	private int x;
	protected char c;

	//constructor
	//params: char c
	public Shape(char c){
		this.c = c;
	}

	//ctor
	//params: none
	//sets default character to 0
	public Shape(){
		c = '0';
	}

	//sets char for shapes
	//params: char c
	//returns none
	public void setChar(char c)
	{
		this.c = c;
	}

	//calls the paintat method in screen class
	//params: screen, int x, int y
	//returns; nothing
	public void paintOn(Screen screen, int x, int y)
	{
		screen.paintAt(x, y, c);
	}

	//calls paintOn mthod with 0,0 as default x,y params
	//params: screen
	//returns: none
	public void paintOn(Screen screen)
	{
		paintOn(screen,0,0);
	}

	//gets y coord
	//params: none
	// returns: y
	public int getY()
	{
		return y;
	}

	//gets x coord
	//params: none
	// returns: x
	public int getX(){
		return x;
	}

	//sets x coord
	//params: none
	// returns: x
	public void setX()
	{
		this.x = x;
	}

	//sets y coord
	//params: none
	// returns: y
	public void setY()
	{
		this.y = y;
	}

	public static void main(String[][] args){
		Screen a = new Screen(10,18);
		a.paintAt(9,9, 'O');
		a.paintAt(9, 0, '0');

		HLine H = new HLine(5);
		H.setChar('0');
		H.paintOn(a, 3,4 );

		VLine v = new VLine(6);
		v.setChar('J');
		v.paintOn(a,0,2);

		Frame r = new Frame(4,5, 'F');
		r.paintOn(a,9,3);

		Box b = new Box(3,3,'B');
		b.paintOn(a,3,0);

		TextLine t = new TextLine("Test 8783");
		t.paintOn(a,8,0);

		a.setBorderChar('*');

		a.draw();
	}

}
