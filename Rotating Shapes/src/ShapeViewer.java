//Jeremy Thaller
//Sowntharya Ayyappan
/*
 * Outer class that views a shape from the Shape class on a
 * collective JFrame. Includes multiple buttons (rotate, reverse,
 * change color, add line, stop, reset), a slider, and a panel that
 * includes the ShapeComponent.
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;


import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.util.ArrayList;
import java.util.Hashtable;

public class ShapeViewer {
	private  ArrayList<Shape> shapes;
	private double width;
	private double height;
	JFrame f = new JFrame();
	Timer timer;
	private int FPS;
	private double THETA = .0175;

	//Getter for width of shape, returns width. No parameters.
	public double getWidth()
	{
		return this.width;
	}
	//Setter for width of shape, no returns. Parameter: double width = new width
	public void setWidth(double width){
		this.width = width;
	}
	//Getter for height of shape, returns height. No parameters.
	public double getHeight(){
		return this.height;
	}
	//Setter for height of shape, no returns. Parameter: double height = new height
	public void setHeight(double height){
		this.height = height;
	}
	//Getter for theta (needed for rotating shape), returns theta. No parameters.
	public double getTHETA(){
		return THETA;
	}
	//Setter for theta, no returns. Parameter: double THETA = new theta 
	public void setTHETA(double THETA){
		this.THETA = THETA;
	}
	/*
	 * Ctor for ShapeViewer on a given width and height
	 * 
	 * Parameters:
	 * double width = width of screen
	 * double height = height of screen 
	 */
	public ShapeViewer(double width, double height) {	
		this.width = width;
		this.height = height;
	}
	//Getter for shapes, returns shapes. No parameters.
	public ArrayList<Shape> getShapes(){
		return shapes;
	}
	/*
	 * Inner class ShapeComponent that extends JComponent.
	 * Creates a graphical version of the shape.
	 * Redefines paintComponent so that it draws a shape 
	 * according to the dimensions of the screen and given color.
	 * Initial color is black.
	 */
	public class ShapeComponent extends JComponent {
		public void paintComponent(Graphics g)
		{
			Graphics2D g2 = (Graphics2D) g;
			for(int i=0; i < shapes.size(); i++ ){
				g2.setColor(shapes.get(i).getColor());
				for(int j=0; j < shapes.get(i).drawShape(getWidth(),getHeight()).size(); j++)
				{
					g2.draw(shapes.get(i).drawShape(getWidth(),getHeight()).get(j));
				}
			}

		}

	}
	/*
	 * Collectively runs all methods in order to create a JFrame with the buttons and sliders on it.
	 * Sets initial size of JFrame to 600,600 with a minimum size of 520, 127.
	 * Design used is BorderLayout.
	 * 
	 * Creates a timer in order to start the rotating process. 
	 * Creates a ShapeComponent that is then put on a panel and then added to the JFrame. 
	 * Creates a slider in order to change the speed of the rotating process. 
	 * Creates a Rotate button that starts rotating the shape (plus starts the timer).
	 * Creates a Reverse button that reverses the way the shape rotates.
	 * Creates a Change Color button that randomly changes the color of the shape.
	 * Creates a Add Line button that adds a new line to the panel.
	 * Creates a Reset button that resets the screen to only leave the original line.
	 * 
	 * Starting point of all shapes is set to 0,0 and 1,1 which is then added to an arraylist that is set as the shape.
	 * Adds a ComponentListener that changes the height and width of the shape component if the JFrame is resized.
	 */
	public void run(){

		f.setSize(600,600);
		f.setMinimumSize(new Dimension(520,127));
		f.setTitle("Rotation Project");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		timer = new Timer(15,new TimerListener()); 

		ShapeComponent comp = new ShapeComponent(); 

		int FPS_MIN = 0;
		int FPS_MAX = 25; 
		int FPS_INIT = 10; 
		JSlider speed = new JSlider(JSlider.HORIZONTAL,
				FPS_MIN, FPS_MAX, FPS_INIT);
		speed.addChangeListener(new SliderListenerSpeed()); 
		speed.setMajorTickSpacing(10);
		speed.setMinorTickSpacing(2);
		speed.setPaintTicks(true);
		speed.setPaintLabels(true);
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( FPS_MIN ), new JLabel("Fast") );
		labelTable.put( new Integer( FPS_MAX ), new JLabel("Slow") );
		speed.setLabelTable( labelTable );



		JButton rotateButton = new JButton("Rotate");
		rotateButton.addActionListener(new RotateListener());

		JButton reverseButton = new JButton("Reverse");
		reverseButton.addActionListener(new ReverseListener());

		JButton colorButton = new JButton("Change Color");
		colorButton.addActionListener(new ColorListener());

		JButton addShapeButton = new JButton("Add Line");
		addShapeButton.addActionListener(new AddShapeListener());

		JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ResetListener());

		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new StopListener());

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(rotateButton);
		buttonPanel.add(reverseButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(colorButton);
		buttonPanel.add(addShapeButton);
		buttonPanel.add(resetButton);


		JPanel sliderPanel = new JPanel();
		sliderPanel.add(speed);



		ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
		points.add(new Point2D.Double(0, 0)); //starting pt1
		points.add(new Point2D.Double(1,1)); //starting pt2


		Shape shape = new Shape(points);
		shapes = new ArrayList<Shape>();
		shapes.add(shape);


		f.addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e){
				setHeight(f.getContentPane().getHeight());
				setWidth(f.getSize().getWidth());
			}
		});

		f.getContentPane().add(BorderLayout.CENTER, comp);
		f.getContentPane().add(BorderLayout.NORTH, sliderPanel);
		f.getContentPane().add(BorderLayout.SOUTH, buttonPanel);

		f.setVisible(true);

	}
	/*
	 * Inner class TimerListener that rotates the shape and repaints the frame, so that when the timer is called
	 * the shape will be rotating.
	 */
	class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			rotate();
			f.repaint();
		}
	}
	/*
	 * Inner class RotateListener that starts the timer (according to TimerListener). 
	 * Is used when user presses the Rotate Button
	 */
	class RotateListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			timer.start();
		}
	}
	/*
	 * Inner class ReverseListener that changes theta so that the shape rotates in the opposite direction.
	 * Is used when user presses the Reverse Button.
	 */
	class ReverseListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setTHETA(getTHETA()*-1);
			f.repaint();
		}
	}
	/*
	 * Calls the rotate method in Shape class that rotates all lines in the ArrayList of shapes
	 * so that all shapes will be rotating when called.
	 */
	public void rotate(){ 
		for(int i=0; i < shapes.size(); i++ ){
			shapes.get(i).rotate(THETA);
		}
	}
	/*
	 * Inner class SliderListenerSpeed that if the slider is moved a delay will be set to the timer
	 * so that the speed will go faster or slower according to the user.
	 */
	class SliderListenerSpeed implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();

			if (!source.getValueIsAdjusting()) {
				timer.setDelay((int)source.getValue());
				f.repaint();
			}
		}
	}
	/*
	 * Inner class StopListener that stops the timer (according to TimerListener) and stops rotating the shapes on the screen.
	 * Is used when user presses the stop button.
	 */
	class StopListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			timer.stop();
		}
	}
	/*
	 * Inner class AddShapeListener that adds a new shape to the ArrayList of shapes
	 * at the same starting point (0,0) and (1,1) with the same color.
	 * Is used when user presses the Add Line button.
	 */
	class AddShapeListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
			points.add(new Point2D.Double(0, 0));
			points.add(new Point2D.Double(1,1));
			shapes.add(new Shape(points));
			f.repaint();
		}
	}
	/*
	 * Inner class ResetListener that clears all shape from the screen, adds a single line at (0,0) and (1,1).
	 * Is used when user presses the reset button.
	 */
	class ResetListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			shapes.clear();
			ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
			points.add(new Point2D.Double(0, 0)); //starting pt1
			points.add(new Point2D.Double(1,1)); //starting pt2
			Shape shape = new Shape(points);
			shapes = new ArrayList<Shape>();
			shapes.add(shape);
			timer.stop();
			f.repaint();
		}
	}
	/*
	 * Inner class ColorListener that creates a random color and sets all shapes on screen to the created color.
	 * Is used when user presses the Change Color button.
	 */
	class ColorListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			int red = (int) (Math.random() * 256);
			int green = (int) (Math.random() * 256);
			int blue = (int) (Math.random() * 256);
			for (int i =0; i<shapes.size();i++){
				Color randomColor = new Color(red, green, blue);
				shapes.get(i).setColor(randomColor); 
			}
		}
	}
	//Tests the functionalities of the ShapeViewer class.
	public static void main(String[] args){
		ShapeViewer a = new ShapeViewer(520,600);
		a.run();
	}

}
