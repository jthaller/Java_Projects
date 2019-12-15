import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Sorting {
	/* SelectionSort
	 * InsertionSort
	 * MergeSort
	 * QuickSort
	 */


	//I took this from my stats project
	public static String arrayToString(int [] a){
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

	public static void selectionSort(int[] array){

		for(int i=0; i<array.length-1; i++){
			int first = i;
			for( int j=i+1; j<array.length; j++)
				if (array [first] > array [j])
					first = j;
			int temp = array[first];
			array[first]=array[i];
			array[i]= temp;
		}
	}

	public static void selectionSort(ArrayList<String> array){
		for(int i=0; i<array.size()-1; i++){
			int first = i;
			for( int j=i+1; j<array.size(); j++)
				if (array.get(j).compareToIgnoreCase(array.get(first))<0)
					first = j;
			String temp = array.get(first);
			array.set(first, array.get(i));
			array.set(i, temp);
		}
	}

	public static String reverse(String str){
		
		String b = "";
		if (str.length() < 1) return "";
		return str.charAt(str.length()-1) + reverse(str.substring(str.length()-2));
	}



	public static void insertionSort( int [] array){
		for (int i = 1; i < array.length; i++){
			int key = array[i];
			int j;

			for(j = i - 1; (j >= 0) && (key < array[j]); j--)
				array[j+1] = array[j];
			array[j+1] = key;    
		}
	}
	public static void insertionSort( ArrayList<String> array){
		for (int i = 1; i < array.size(); i++){
			String key = array.get(i);
			int j;
			for(j = i - 1; (j >= 0) && (key.compareToIgnoreCase(array.get(j))) < 0; j--)
				array.set(j+1, array.get(j));
			array.set(j+1, key);
		}
	}


	
	public static void mergeSort(int [] array){
		if (array.length <= 1) return;
		 mergeSort(array, 0, array.length-1);
	}
	
	
	 public static void mergeSort(int [] array, int start, int end) {
	        int mid;     
	        int left;       
	        int right;      
	        int temp;       

	        if (start < end) {
	            mid = (start + end) / 2;
	            mergeSort(array, start, mid);
	            mergeSort(array, mid + 1, end);

	            // Merge sorted arrays together
	            left = start;
	            right = mid + 1;

	            while (left <= mid && right <= end) {
	               
	                if (array[left] > array[right]) {
	                    temp = array[right];

	                    // shift left array right one 
	                    for (int i=right-1; i>=left; i--) {
	                        array[i+1] = array[i];
	                    }

	                    // shove smaller number in
	                    array[left] = temp;
	                    right++;
	                    mid++;
	                }
	                left++;
	            }
	        }
	    }

	 public static void mergeSort(ArrayList<String> array){
			if (array.size() <= 1) return;
			 mergeSort(array, 0, array.size()-1);
		}
		
		
		 public static void mergeSort(ArrayList<String> array, int start, int end) {
		        int mid;     
		        int left;       
		        int right;      
		        String temp;       

		        if (start < end) {
		            mid = (start + end) / 2;
		            mergeSort(array, start, mid);
		            mergeSort(array, mid+1, end);

		            // Merge sorted arrays together
		            left = start;
		            right = mid + 1;

		            while (left <= mid && right <= end) {
		               
		                if (array.get(left).compareTo(array.get(right)) > 0) {
		                    temp = array.get(right);

		                    // shift left array right one 
		                    for (int i=right-1; i>=left; i--) {
		                        array.set(i+1, array.get(i));
		                    }

		                    // shove smaller number in
		                    array.set(left, temp);
		                    right++;
		                    mid++;
		                }
		                left++;
		            }
		        }
		    }
		 
		 


		   // Reorganizes the given list so all elements less than the first are 
		   // before it and all greater elements are after it.                   
//		   public static int partition(int A[], int f, int l)
//		   {
//			  int pivot = A[f];
//		      while (f < l)
//		      {
//		         while (A[f] < pivot) f++;
//		         while (A[l] > pivot) l--;
//		         int temp = A[f];
//			      A[f] = A[l];
//			      A[l] = temp;
//		      }
//		      return f;
//		   }




	public ArrayList<Integer> bogoSort(ArrayList<Integer> a) {
		Collections.shuffle(a);
		for (int i =0; i < a.size(); i++){
			if (a.get(i) > a.get(i+1))
				return bogoSort(a);}
		return a;
	}

	public static int[] bogoSort(int[] array)
	{
		int index, temp;
		Random random = new Random();
		for (int i = array.length - 1; i > 0; i--)
		{
			index = random.nextInt(i + 1);
			temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
		for(int i=1; i<array.length; i++)
			if(array[i]<array[i-1])
				return bogoSort(array);
		return array;
	}
	
	
	 public static void quickSort(int [] array){
		 if (array.length <= 1) return;
		 quickSort(array, 0, array.length -1);
	 }
	 
	 
	 public static void quickSort(int [] array, int low, int high) 
	    {
	        int i = low, j = high;
	        int temp;
	        int pivot = array [(low + high) / 2];
	 
	        /** partition **/
	        while (i <= j) 
	        {
	            while (array [i] < pivot)
	                i++;
	            while (array [j] > pivot)
	                j--;
	            if (i <= j) 
	            {
	                /** swap **/
	                temp = array[i];
	                array[i] = array[j];
	                array[j] = temp;
	 
	                i++;
	                j--;
	            }
	        }
	 
	        /** recursively sort lower half **/
	        if (low < j)
	            quickSort(array, low+1, j);
	        /** recursively sort upper half **/
	        if (i < high)
	            quickSort(array, i, high-1);
	    }
	 
	 
	 public static void quickSort(ArrayList<String> array){
		 if (array.size() <=1) return;
		 quickSort(array, 0, array.size()-1);
	 }
	 public static void quickSort(ArrayList<String> array, int low, int high) 
	    {
		 int i = low, j = high;
	        String temp;
	        String pivot = array.get((low + high) / 2);
	 
	        /** partition **/
	        while (i <= j) 
	        {
	        	while (array.get(i).compareTo(pivot) < 0)
	                i++;
	        	while (array.get(j).compareTo(pivot) > 0)
	                j--;
	            if (i <= j) 
	            {
	                /** swap **/
	                temp = array.get(i);
	                array.set(i, array.get(j));
	                array.set(j, temp);
	 
	                i++;
	                j--;
	            }
	        }
	 
	        /** recursively sort lower half **/
	        if (low < j)
	            quickSort(array, low-1, j);
	        /** recursively sort upper half **/
	        if (i < high)
	            quickSort(array, i, high+1);
	    }



	public static void main(String[] args) {
		int[] a = {1,4,3,5,0,9,9,1,2,2,3,4,19};
		int[] b = {1,4,6,3,5,0,0,1,13,19,7,11};
		int[] c = {9};
		System.out.println("test");
		ArrayList<String> a1 = new ArrayList<String>();
		a1.add("GRapes"); a1.add("banana"); a1.add("bae"); a1.add("apple"); a1.add("fetus"); 
		//				bogoSort(b);
		//				System.out.println(arrayToString(b));
		System.out.println(arrayToString(a));
		quickSort(a);
		System.out.println(arrayToString(a));
	
	}



}
