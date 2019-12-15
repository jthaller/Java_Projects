//Jeremy Thaller Period 4
//A java project to test various types of loops.  

import java.math.BigInteger;
import java.util.Scanner;


public class LoopExamples {

	//	static int length;
	//	static int width;
	//	static int area;

	//Method prompts user to enter a natural number. Returns its factorial.
	//Then calls on factorialAgain method, which asks the user whether they'd like to play again
	public void factorial(){
		do{ 
			Scanner s = new Scanner(System.in);
		int number;
		System.out.println("Enter a natural number to find its Factorial");
		number = s.nextInt();
		if (number >= 0){
		BigInteger factorial = BigInteger.valueOf(1);
		int i = number;

		while (i > 0){
			factorial = factorial.multiply(BigInteger.valueOf(i));
			i-- ;
		}
		System.out.println(number + "!" + " = " + factorial);
		}	while (factorialAgain());
	
		
			
	}


	private boolean factorialAgain(){
		String answer;
		Scanner r = new Scanner(System.in); 
		//scanner r is the user response for whether or not they'd like to enter another int
		do{
			System.out.println("Would you like to enter another natural number?");
			System.out.println("(Enter yes or no)");
			answer = r.nextLine();
		}while(!answer.equalsIgnoreCase("Yes")&&!answer.equalsIgnoreCase("No"));

		if(answer.equalsIgnoreCase("Yes"))
			return true;

		else;
		return false;
	}

	public static void rectangles(){
		System.out.println(" AREAS OF RECTANGLES");
		System.out.println("Width   " + "Length   " + "Area");
		System.out.println("---------------------");

		for(int width = 3; width <= 6; width++){
			for (int length = 4; length <= 7; length++){
				System.out.println(" " + width + "        " + length + "       " + width*length);
			}


		}
	}

	public static void main(String[] args) {
		LoopExamples l = new LoopExamples();
		l.factorial();
		LoopExamples m = new LoopExamples();
		m.rectangles();

	}

}
