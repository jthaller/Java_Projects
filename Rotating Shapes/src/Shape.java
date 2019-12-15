//Jeremy Thaller
//Sowntharya Ayyappan

//Class that organizes the mechanics of rotating lines.
//This includes a method to rotate lines based off of rotation matrices,
//a method to create an arraylist of lines (a shape),
//as well as getters, setters, and constructors for the center coordinates and color.
//The class standardizes length to be between 0 and 1.
//

import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Shape {

	private ArrayList<Point2D.Double> points; 
	private double centerX;
	private double centerY;
	private Color color = Color.BLACK;
	
	//Getter for Color of shape, returns color. No parameters. 
	public Color getColor(){
		return this.color;
	}

	//params: Color Color
	//void. sets color c
	public void setColor(Color color){
		this.color = color;
	}

	//params: none
	//returns: double centerX
	public double getCenterX(){
		return this.centerX;}

	//params: none
	//returns: double centerY
	public double getCenterY(){
		return this.centerY;}


	//Ctor
	//Params: arrayList of point2D.Double
	//creates a shape when given a set of points. Also sets the center

	public Shape(ArrayList<Point2D.Double> points){
		this.points=points;
		double x =0;
		double y =0;
		for(int i = 0; i<points.size();i++)
		{
			x+= points.get(i).getX();
			y+= points.get(i).getY();

		} //sets the center based on the center of the shape (collection of lines)
		this.centerX = x/points.size(); //if 2 points, it's a line and center is divided by 2. if triangle, divided by 3, etc.
		this.centerY = y/points.size();
	}

	//ctor:
	//params: arrayList<point2D.Double> points, point2D.Double center 
	//creates a shape if you already know its center
	public Shape(ArrayList<Point2D.Double> points, Point2D.Double center){
		this.points=points;
		center.setLocation(centerX,centerY);
	}


	//Rotate method based off rotation matrices
	//params: double theta
	//theta is the angle in radians used to rotate. 
	//A positive theta will rotate counterclockwise. A Negative theta will rotate clockwise.
	public void rotate(double theta){
		for(int i = 0; i < points.size(); i++)
		{
			double x = Math.cos(theta)*(points.get(i).getX()- centerX) 
					+ Math.sin(theta)*(points.get(i).getY() -centerY) + centerX;
			double y = -Math.sin(theta)*(points.get(i).getX()- centerX) 
					+ Math.cos(theta)*(points.get(i).getY() -centerY) + centerY;
			points.get(i).setLocation(x, y);  
		}
	}


	//drawShape method
	//params: double width, double height. these are the width and height of the frame being drawn in
	//The method takes in the height and width of the screen, 
	//then takes the arrayList<Point2D.Double> points
	//and combines them into an ArrayList<Line2D.Double> lines.
	//this is "get lines"
	public ArrayList<Line2D.Double> drawShape(double width, double height){

		ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>();
		for(int i=0; i< points.size()-1; i++){

			lines.add(new Line2D.Double(points.get(i).getX()*width,
					points.get(i).getY()*height,
					points.get(i+1).getX()*width, //standardized so it's 0 to 1. makes things easier
					points.get(i+1).getY()*height));
		}

		lines.add(new Line2D.Double(points.get(0).getX()*width,
				points.get(0).getY()*height,
				points.get(points.size()-1).getX()*width,
				points.get(points.size()-1).getY()*height));
		return lines;
	}
	/*
	 * Tests the funcionalities of the Shape class
	 */
	public static void main(String[] args){
		Point2D.Double a = new Point2D.Double(1,0);
		ArrayList<Point2D.Double> b = new ArrayList<Point2D.Double>();
		b.add(a);
		Shape test = new Shape(b);
		test.rotate((Math.PI)/2);
		System.out.println(b);
	}
}
