//Jeremy THaller p4
//abstract class taht will be extended by box and frame
public abstract class Rectangle extends Shape {
	private int height;
	private int width;

	//ctor
	//params: char c
	public Rectangle(char c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	//ctor
	//params: none
	//does nothing. meant to be overridden 
	public Rectangle() {
	}

	//ctor
	//params: int height, int width, char c
	public Rectangle(int height, int width, char c){
		this.height=height;
		this.width=width;
		this.c = c;
	}

	//get width
	//params: none
	//returns: width
	public int getWidth(){
		return width;
	}

	//gets height
	//params: none
	//returns height
	public int getHeight(){
		return height;
	}

}
