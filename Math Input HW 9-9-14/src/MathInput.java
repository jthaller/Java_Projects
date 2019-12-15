import java.util.Scanner;


public class MathInput {

	public static void main(String[] args) {

		System.out.println("Insert an Integer:"); 
		// shows text output
		Scanner s = new Scanner(System.in);
		int a = s.nextInt(); 
		System.out.println("Enter Another Integer:"); //Shows text first line
		int b = s.nextInt();
		System.out.print(a + " / " + b + " = " + a /b); //Print operations of answer 
		System.out.print(" with a remainder of "); //Print next line of answer
		System.out.print(a%b); //Print the remainder

		

	}

}
