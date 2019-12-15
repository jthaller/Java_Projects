//Jeremy Thaller P4
// A class to find the mean, median, mode, and range of a set of numbers (an array)
import java.util.ArrayList;

public class Statistics {


	/* Params:
	 * 	 double[] a, input array of doubles
	 * sorts a copy of the array, 
	 * Returns: double [] b, an array of type double that is sorted least to greatest 
	 * 
	 * code borrowed from Mr. Marshall's ArrayExamples class
	 */
	public static double[] sortArray(double[] a){
		double[]b = copyArray(a);
		for(int outer = 0; outer < b.length; outer++){
			for(int inner = outer + 1; inner < b.length; inner++){
				if(b[inner] < b[outer]){
					double temp = b[inner];
					b[inner] = b[outer];
					b[outer] = temp;
				}
			}
		}
		return b;
	}

	/*	Params: double [] a, 
	 *	
	 *	 takes in an array of type double
	 *	returns: double [] b, a copy of the array
	 */	
	public static double[] copyArray(double[] a){
		double [] b = new double[a.length];
		for (int i=0; i<a.length; i++)
			b[i] = a[i];
		return b;
	}

	//Params: arrayIn, double[], input is an array of doubles
	//uses a copy of the array to calculate the mean
	//returns: the mean as a double
	public static double mean(double[] arrayIn){
		double[] temp = copyArray(arrayIn);
		double sum = 0;
		for(int  i = 0; i < temp.length; i++){
			sum +=temp[i];
		}
		double mean = sum / temp.length;
		return mean;
	}

	/*Params: arrayIn, double[], takes in an array of type double
	 *
	 *copies the array, then sorts that copy, then calculates the range 
	 *if the array is empty, returns 0.
	 *returns: range, double, 
	 */

	public static double range(double[] arrayIn){
		double []b = copyArray(arrayIn);
		double [] temp = sortArray(b);
		//if there's an empty array, returns the number zero
		if (temp.length == 0){
			double median = 0;
			return median;}

		double smallest = 0;
		double greatest = 0;
		smallest = temp[0];
		greatest = temp[temp.length -1];
		double range = greatest - smallest; 
		return range;
	}

	/*takes in an array of type double
	 *
	 *copies the array, then sorts that copy, then calculates the median
	 *
	 *returns: median (the median as a double)
	 */

	public static double median(double [] arrayIn){
		double []b = copyArray(arrayIn);
		double [] temp = sortArray(b);

		//if array is empty it returns zero
		if (temp.length == 0){
			double median = 0;
			return median;}

		if (temp.length % 2 == 0){
			//if array has an even amount of numbers
			double middleNumSmall = temp[((temp.length / 2) - 1)];
			double middleNumBig = temp[(temp.length / 2 )];
			double median = (middleNumSmall + middleNumBig) / 2;
			return median;

		}else

			//if array has an odd amount of numbers
			return temp[(temp.length -1)/2];

	}

	/* Params:
	 * 	arrayIn: double[], input array of doubles
	 * Returns:
	 * 	ArrayList<Double>, ArrayList of modes of array
	 * 		
	 * modes are values that occur the highest number of times
	 *  if more than half of values are mode, returns empty ArrayList
	 * 	if array is length 0, returns empty ArrayList
	 */
	public static ArrayList<Double> mode(double[] arrayIn)
	{
		double []b = copyArray(arrayIn);
		double [] temp = sortArray(b);
		ArrayList<Double> mode= new ArrayList<Double> ();
		int count = 0;
		int numUnique = 0;
		for (int i = 0, j; i < temp.length; i++)
		{
			int countTemp = 0;
			for (j = i; (j < temp.length) && (temp[i] == temp[j]) ; j++)
			{
				countTemp += 1;
				if (countTemp > count)
					count = countTemp;
			}
			numUnique +=1;
			i = j-1;
		}

		for (int i = 0, j; i < temp.length;i++)
		{
			int countTemp = 0;
			for ( j = i; (j < temp.length) && (temp[i] == temp [j]); j++)																									
			{
				countTemp += 1;
				if (countTemp == count)
					mode.add(temp[i]);
			}
			i = j-1;

		}
		//returns empty array if number of modes is greater than
		//	half the number of total values
		if(mode.size() > (numUnique/2))
			return new ArrayList<Double> ();

		return mode;
	}


	//all output done in Tests.java 
	public static void main(String[] args) {

	}
}

