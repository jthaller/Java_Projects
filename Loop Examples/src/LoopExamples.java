//Jeremy Thaller Period 4
//A java project to test various types of loops. 
//Has a method for calculating factorials based on user input
//Prints a table with length, width, and height of rectangles. Width ranges from 3 to 6. Height ranges from 4 to 7.
//Has a method to calculate and print the height of an object based on the given initial velocity as compared to
//the velocity found from the equation s(t) = .05

import java.math.BigInteger;
import java.util.Scanner;


public class LoopExamples {

	//Method prompts user to enter a natural number. Returns its factorial and prints it.
	//Then calls on factorialAgain method, which asks the user whether they'd like to enter another number
	public void factorial(){
		do 
		{ 
			Scanner s = new Scanner(System.in); 
			int number;
			do // do while loop to ask for user input for factorial. Repeats until user enters a non negative integer
			{
				System.out.println("Enter a natural number to find its Factorial");
				number = s.nextInt();
			}
			while(number < 0); 

			if (number >= 0)
			{
				BigInteger factorial = BigInteger.valueOf(1);
				int i = number;

				while (i > 0) //while loop to calculate factorial
				{
					factorial = factorial.multiply(BigInteger.valueOf(i));
					i-- ;
				}
				System.out.println(number + "!" + " = " + factorial);
			}	
		}
		while (factorialAgain());
	}


	//method is called by the factorial method. Asks for user input on whether or not they'd like find another factorial.
	private boolean factorialAgain()
	{
		String answer;
		Scanner r = new Scanner(System.in); 
		//scanner r is the user response for whether or not they'd like to enter another int
		do
		{
			System.out.println("Would you like to enter another natural number?");
			System.out.println("(Enter yes or no)");
			answer = r.nextLine();
		}
		while(!answer.equalsIgnoreCase("Yes")&&!answer.equalsIgnoreCase("No"));

		if(answer.equalsIgnoreCase("Yes"))
			return true;

		else;
		return false;
	}

	// method prints a table with the length, width, and area of a rectangle.  
	public static void rectangles()
	{
		System.out.println('\n' + " AREAS OF RECTANGLES");
		System.out.println("Width   " + "Length   " + "Area");
		System.out.println("---------------------");

		for(int width = 3; width <= 6; width++)  //range of width
		{  
			for (int length = 4; length <= 7; length++) //range of length
			{  
				System.out.println(" " + width + "        " + length + "       " + width*length);
			}
		}
	}


	//takes an initial velocity as the parameter. 
	//prints the position every 1 second based on the simulation (which is calculated every .01 seconds)
	//and on the function.
	public void simulation(double initialVelocity)
	{
		double g = 9.81; 							// gravity constant in meters per seconds squared
		double t = 0;    							// time
		double deltaT = 0.01;    					// change in time 
		double v = initialVelocity;  				// velocity
		double s = 0;  								// position (height in meters)
		double deltaS = 0; 						    // change in position
		double sT = 0;  							// Position from function
		boolean done = false;

		//Prints header for the output 
		System.out.println('\n' + "Time             Position in meters		Position in meters"); 
		System.out.println("(Sec)            (Simulation)       		(Function)"); // loop takes into account changes
		System.out.println("------------------------------------------------------------"); // just plug into s(t) equation


		//If initial velocity is negative, prints an error message and breaks out of method
		if (initialVelocity < 0){
			System.out.println("Initial velocity cannot be negative" + '\n' + "when the projectile is fired up");
			return;
		}

		while (!done)
		{
			//Prints out position every full second using floating points time.
			if ((t % 1) < .01)
			{
				System.out.println((int)t + "             "
						+ s + "             " + sT);
			}
			//updates the time
			t =  t + deltaT;

			//calculates the position incrementally. The Simulation method.
			v = v - g*deltaT;
			deltaS = v*deltaT;
			s = s + deltaS;

			// Calculate position using the function method.
			sT = (-0.5 * g * t * t) + (initialVelocity * t);

			if (s < 0) //quits method when position is less than zero (hits ground)
				done = true;
		}
	}

	public static void main(String[] args)
	{
		LoopExamples l = new LoopExamples();
		l.factorial();
		LoopExamples.rectangles();
		LoopExamples s = new LoopExamples();
		s.simulation(25.8); //enter a double as the initial velocity in meters per second 

	}

}

/* Output for factorial method
 * 
Enter a natural number to find its Factorial
-8
Enter a natural number to find its Factorial
8
8! = 40320
Would you like to enter another natural number?
(Enter yes or no)
yes
Enter a natural number to find its Factorial
0
0! = 1
Would you like to enter another natural number?
(Enter yes or no)
Yes
Enter a natural number to find its Factorial
75
75! = 24809140811395398091946477116594033660926243886570122837795894512655842677572867409443815424000000000000000000
Would you like to enter another natural number?
(Enter yes or no)
no
 */



/* Output for rectangles method
 * 
 *  AREAS OF RECTANGLES
Width   Length   Area
---------------------
 3        4       12
 3        5       15
 3        6       18
 3        7       21
 4        4       16
 4        5       20
 4        6       24
 4        7       28
 5        4       20
 5        5       25
 5        6       30
 5        7       35
 6        4       24
 6        5       30
 6        6       36
 6        7       42
 */



/* Output for simulation method with initial velocity of 25.8 m/s
 * 
 * Time             Position in meters		Position in meters
(Sec)            (Simulation)       		(Function)
------------------------------------------------------------
0             0.0             0.0
1             20.84595000000006             20.89500000000001
2             31.881900000000158             31.980000000000008
3             33.07056900000025             33.21820950000007
4             24.388419000000344             24.585109500000556
5             5.896269000000445             6.14200950000145
*/
