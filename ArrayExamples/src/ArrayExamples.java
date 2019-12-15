import java.util.Random;


public class ArrayExamples {

	/*
	 * Counts the number of even values in an array
	 * of int values
	 * 
	 * Parameter:
	 * 	int[] vals = the array of integer values
	 * 
	 * Returns: the tally of even values in vals
	 */
	public static int countEvens(int[] vals){
		int tally = 0;
//		for(int i = 0; i < vals.length; i++){
//			if(vals[i] % 2 == 0){
//				tally++;
//			}
//		}
		
		for(int val: vals){
			if(val % 2 == 0)
				tally++;
		}
		
		return tally;
	}
	
	/*
	 * Returns an array containing only the even
	 * values of the array parameter
	 * 
	 * Parameter:
	 * 	int[] vals = the array in which to find even values
	 * 
	 * Returns: an int array containing the even values 
	 */
	public static int[] getEvens(int[] vals){
		int[] evens = new int[countEvens(vals)];
		int i = 0;
		for(int n: vals){
			if(n % 2 == 0)
				evens[i++] = n;
		}
		return evens;
	}
	
	public static void main(String[] args) {
		
		int[][] a = {{2, 4, 5, 7, 8, 9, 3, 6},
					 {1, 3, 5, 7, 9},
					 {2, 4, 6, 8, 10}};
		
		for(int[] arr: a){
			System.out.println(arrayToString(arr));
			System.out.println("The number of even values: " + countEvens(arr));
			System.out.println("Only the evens: " + arrayToString(getEvens(arr)));
			System.out.println("\n");
		}
		
		System.out.println("Before sorting: " + arrayToString(a[0]));
		sortArray(a[0]);
		System.out.println("After sorting: " + arrayToString(a[0]));
		
		
		int[][] b = new int[3][4];
		randomizeMatrix(b);
		System.out.println(matrixToString(b));
	}
	
	public static void randomizeMatrix(int[][] m){
		Random r = new Random();
		
		for(int row = 0; row < m.length; row++){
			for(int col = 0; col < m[row].length; col++){
				m[row][col] = r.nextInt(10);
			}
		}
	}
	
	public static String matrixToString(int[][] m){
		String s = "";
		for(int[] row: m){
			for(int num: row){
				s += num + " ";
			}
			s += "\n";
		}
		return s;
	}
	
	public static void sortArray(int[] a){
		for(int outer = 0; outer < a.length; outer++){
			for(int inner = outer + 1; inner < a.length; inner++){
				if(a[inner] < a[outer]){
					int temp = a[inner];
					a[inner] = a[outer];
					a[outer] = temp;
				}
			}
		}
	}
	
	
	
	
	public static String arrayToString(int[] a){
		String s = "";
		for(int n: a)
			s += n + " ";
		return s;
	}

}
