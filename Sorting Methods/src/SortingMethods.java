import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
public class SortingMethods {

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

	public static void mergeSort( int  [] array){
		if (array.length <= 1) return;
		int [] firstHalf = new int[array.length/2];
		int[] secondHalf = new int[array.length - firstHalf.length];
		
		int [] firstHalfCopy = new int[firstHalf.length];
		
		//arraycopy second half
		mergeSort(firstHalf);
		mergeSort(secondHalf);
		mergeSort(firstHalf, secondHalf);
	}
	
	public static void mergeSort(int[] firstHalf, int[] secondHalf){
		int[] array = null;
		int iFirst =0;
		int iSecond = 0;
		int j=0;
		while(iFirst < firstHalf.length && iSecond < secondHalf.length){
			if (firstHalf[iFirst] < secondHalf[iSecond]){
				array[j] = firstHalf[iFirst];
				iFirst++;
			}
			array[j] = secondHalf[iSecond];
			iSecond++;
		}
		j++;
	}
	






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



	public static void main(String[] args) {
		int[] a = {1,4,3,5,0,9,9,1,2,2,3,4,19};
		int[] b = {1,4,6,3,5,0,0,1,13,19,7,11};
		System.out.println("test");
		ArrayList<String> a1 = new ArrayList<String>();
		a1.add("GRapes"); a1.add("banana"); a1.add("bae"); a1.add("fetus"); a1.add("apple");
		//				bogoSort(b);
		//				System.out.println(arrayToString(b));
		System.out.println(a1);
		insertionSort(a1);
		System.out.println(a1);
		
		System.out.println(reverse("Monkey"));
	}

}
