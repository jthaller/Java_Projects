/*
 * Represents a mathematical linear equation
 * in two variables in slope-intercept (y=mx+b)
 * form. 
 * 
 * Updated 9/16/2014
 */

public class LinearEquation 
{

	private double slope;
	private double yInt;
	
	/*
	 * Constructor to build a LinearEquation object
	 * based upon a slope and y-intercept.
	 * Parameters:
	 * 	double slope = the slope of the line
	 * 	double yIntercept = y-int of the line
	 */
	public LinearEquation(double slope, double yIntercept)
	{
		this.slope = slope;
		this.yInt = yIntercept;
	}
	
	/*
	 * Constructor based on two ordered pairs
	 * Parameters (all doubles):
	 * 	x1 = x coord of first point
	 * 	y1 = y coord of 1st point
	 * 	x2 = x coord of 2nd point
	 * 	y2 = y coord of 2nd point
	 */
	public LinearEquation(double x1, double y1, double x2, double y2){
		slope = (y2 - y1) / (x2 - x1);
		yInt = y1 - slope * x1;
	}
	
	/*
	 * Default constructor (ctor)
	 */
	public LinearEquation(){
		slope = 1;
		//yInt = 0; //no need; primitive class variables default to 0
	}
	
	
	/*
	 * Returns a String representation of the
	 * LinearEquation object as "y = mx + b"
	 */
	public String toString(){
		String str = "y = " + slope + "x +" + yInt;
		str = str + slope + "x +";
		str = yInt;
		
		return str;
	}
	
	/*
	 * Calculates the y-value of an ordered pair on the
	 * line given the x-value
	 * 
	 * Parameter:
	 * 	double x = the x-coordinate
	 * 
	 *  Returns the value of the y-coordinate
	 */
	public double getY(double x){
		double y = slope * x + yInt;
		return y;
	}
	
	/*
	 * Calculates the x-value of an ordered pair on the
	 * line given the y-value
	 * 
	 * Parameter:
	 * 	double y = the y-coordinate
	 * 
	 *  Returns the value of the x-coordinate
	 */
	public double getX(double y){
//		return (y - yInt) / slope;
		double x = (y - yInt) / slope;
		return x;
	}
	
	/*
	 * Finds the inverse of this LinearEquation object
	 * 
	 * Returns a LinearEquation object containing the inverse equation
	 */
	public LinearEquation inverse(){
		double newSlope = 1 / this.slope;
		double newYInt = -this.yInt / this.slope;
		LinearEquation inv = new LinearEquation(newSlope, newYInt);
		return inv;
	}
	
	/*
	 * Finds the composition of 2 linear equation functions, f(g(x)).
	 * This object is treated as the f(x) and the formal parameter is
	 * the g(x).
	 * 
	 * Parameter:
	 * 	LinearEquation inner = the 'inner' function of the composition
	 * 
	 * Returns a LinearEquation object containing the composition equation
	 */
	public LinearEquation compose(LinearEquation inner){
		double newSlope = this.slope * inner.slope;
		double newYInt = this.slope * inner.yInt + this.yInt;
		LinearEquation comp = new LinearEquation(newSlope, newYInt);
		return comp;
	}
	
	/*
	 * Getter for slope field (accessor methods)
	 */
	public double getSlope(){
		return slope;
	}
	
	/*
	 * Setter for slope (mutator or modifier)
	 */
	public void setSlope(double newSlope){
		slope = newSlope;
	}

	public double getyInt() {
		return yInt;
	}

	public void setyInt(double yInt) {
		this.yInt = yInt;
	}
	
}




