/*
 * A class to test all of the functionalities
 * of the LinearEquation class.
 * Updated 9/16/2014
 */

public class LineTester {

	public static void main(String[] args) {
		LinearEquation a = new LinearEquation(5, 7);
		LinearEquation b = new LinearEquation(0,0,1,2);
		LinearEquation c = new LinearEquation();
		
		System.out.println(a.toString());
		System.out.println(b);
		System.out.println(c);
		double num = 4;
		System.out.println("When x=" + num + " in " + a +
				", y=" + a.getY(num));
		
		System.out.println(a.compose(a.inverse()));
		System.out.println(a.inverse().compose(a));

	}

}







