//Jeremy Thaller P4
//abstract class  creates a line.  Has methods to be overriden by HLine and VLine
//class is also used by the VLine and HLine classes.
public abstract class Line extends Shape {
	private int length;

	//gets length
	//params: none
	//returns: int length
	public int getLength(){
		return length;
	}

	//ctor:
	//params: none
	public Line(){
		length = 0;
	}
	//ctor:
	//params: int length
	public Line(int length){
		this.length = length;
	}

	//sets length
	//params: int length
	//returns: none
	public void setLength(int length){
		this.length = length;
	}
}


