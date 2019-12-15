//Jeremy Thaller P4
//Class to test the Statistics Class methods
public class Tests {

	/*Params: double [] a, takes in an array of type double
	 * Returns: s, a string version of a parameter,
	 * 			complete with brackets and commas
	 */
	public static String arrayToString(double [] a){
		String s = "[ ";
		for (int i = 0; i < a.length; i++)
		{
			if (i != a.length -1 )
				s += a[i] + ", ";
			else
				s += a[i]+ " " ;
		}
		s += "]";
		return s;
	}

	public static void main(String [] args){
		double [] a = {-4, -1};
		double [] b = {4,-1.0, 2.5, 2};
		double [] c = {1,1,2,3.0,4};
		double [] d = {};
		double [] e = {0};
		double [] f = {4.5, 2.7, 4, 0};
		double [] g = {0,0,0,1};
		double [] h = {2,2,4,4,5};
		double [] i = {3,3,4,4,5,-1,-2};


		//Prints Original Arrays
		System.out.println("------ORIGINAL ARRAYS------");
		System.out.println("The original array A is: " +arrayToString(a));
		System.out.println("The original array B is: " +arrayToString(b));
		System.out.println("The original array C is: " +arrayToString(c));
		System.out.println("The original array D is: " +arrayToString(d));
		System.out.println("The original array E is: " +arrayToString(e));
		System.out.println("The original array F is: " +arrayToString(f));
		System.out.println("The original array G is: " +arrayToString(g));
		System.out.println("The original array H is: " +arrayToString(h));
		System.out.println("The original array I is: " +arrayToString(i));
		System.out.println();

		//Prints mean of arrays for the arrays wanted to be tested
		System.out.println("------MEAN OF ARRAYS------");
		System.out.println("The mean of A is: " +Statistics.mean(a)); 
		System.out.println("The mean of B is: " +Statistics.mean(b)); 
		System.out.println("The mean of C is: " +Statistics.mean(c)); 
		System.out.println("The mean of D is: " +Statistics.mean(d)); 
		System.out.println("The mean of E is: " +Statistics.mean(e)); 
		System.out.println("The mean of F is: " +Statistics.mean(f)); 
		System.out.println("The mean of I is: " +Statistics.mean(i));
		System.out.println();

		//Prints median of the arrays for the arrays wanted to be tested
		System.out.println("------MEDIAN OF ARRAYS------");
		System.out.println("The median of A is: " +Statistics.median(a));
		System.out.println("The median of B is: " +Statistics.median(b));
		System.out.println("The median of C is: " +Statistics.median(c));
		System.out.println("The median of D is: " +Statistics.median(d));
		System.out.println("The median of E is: " +Statistics.median(e));
		System.out.println("The median of F is: " +Statistics.median(f));
		System.out.println();

		//Prints the range of arrays for the arrays wanted to be tested
		System.out.println("------RANGE OF ARRAYS------");
		System.out.println("The range of A is: " +Statistics.range(a)); 
		System.out.println("The range of B is: " +Statistics.range(b));
		System.out.println("The range of C is: " +Statistics.range(c)); 
		System.out.println("The range of D is: " +Statistics.range(d)); 
		System.out.println("The range of E is: " +Statistics.range(e)); 
		System.out.println("The range of F is: " +Statistics.range(f)); 
		System.out.println("The range of I is: " +Statistics.range(i)); 
		System.out.println();

		//Prints the mode of arrays for the arrays wanted to be tested
		System.out.println("------MODE OF ARRAYS------");
		System.out.println("The mode of B is: " +Statistics.mode(b));
		System.out.println("The mode of C is: " +Statistics.mode(c));
		System.out.println("The mode of D is: " +Statistics.mode(d));
		System.out.println("The mode of E is: " +Statistics.mode(e));
		System.out.println("The mode of G is: " +Statistics.mode(g));
		System.out.println("The mode of H is: " +(Statistics.mode(h)));
		System.out.println("The mode of I is: " +Statistics.mode(i));

	}

}
