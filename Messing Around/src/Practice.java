
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Practice {

	//	public int[] randomSort(int [] array){
	//		Random r = new Random();
	//		for (int i=0; i<array.length; i++)
	//			array[0] = array [(r.nextInt(array.length))];
	//
	//		//r.nextInt(lista.size()) would generate a random index between 0 and size - 1.
	//	}


	//	import java.util.ArrayList;
	//	import java.util.Collections;
	public ArrayList<Integer> randomSort(ArrayList<Integer> a) {
		Collections.shuffle(a);
		for (int i =0; i < a.size(); i++){
			if (a.get(i) > a.get(i+1))
				return randomSort(a);}
		return a;}

	private int[] randomSort(int[] array)
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
		for(int i=1;i<array.length;i++)
			if(array[i]<array[i-1])
				return randomSort(array);
		return array;
	}


	private void bogo(int[] arr)
	{
		//Keep a track of the number of shuffles
		int shuffle=1;
		for(;!isSorted(arr);shuffle++)
			shuffle(arr);
		//Boast
		System.out.println("This took "+shuffle+" shuffles.");
	}
	private void shuffle(int[] arr)
	{
		//Standard Fisher-Yates shuffle algorithm
		int i=arr.length-1;
		while(i>0)
			swap(arr,i--,(int)(Math.random()*i));
	}
	private void swap(int[] arr,int i,int j)
	{
		int temp=arr[i];
		arr[i]=arr[j];
		arr[j]=temp;
	}
	private boolean isSorted(int[] arr)
	{

		for(int i=1;i<arr.length;i++)
			if(arr[i]<arr[i-1])
				return false;
		return true;
	}


	//merge and place in alphabetical order. guarunteed to be in alphabetical order given
	//aghiw, abbrx --> aabbghirwx
	public static String merge(String a, String b){

		if (a == null || a.length() ==0) return b;
		if (b == null || b.length() ==0) return a; 
		if (a.charAt(0) < b.charAt(0))
			return a.charAt(0) + merge(a.substring(1), b);
		return b.charAt(0) + merge(a, b.substring(1));
	}

	//	public static String merge(String a, String b, String merged){
	//		if (a.length() < 1 || b.length() < 1)
	//			return merged;
	//		
	//		
	//		return a.
	//	}


	//because --> esuace
	public static String reverseNoB(String a){
		if (a.length() < 1)
			return "";

		if (a.charAt(a.length()-1) == 'b'){
			return "" + reverseNoB(a.substring(0,a.length()-1));
		}
		return a.charAt(a.length()-1) + reverseNoB(a.substring(0,a.length() -1));
	}

	//return reverseNo8(a.substring(1));
	//return reverseNoB(a.substring(1)) + a.charAt(0);

	public static String scrambleWord(String word){
		if (word.length() < 1) return "";
		if (word.length() >1){
		if (word.charAt(0)=='A' && word.charAt(1) != 'A')
			return "" + word.charAt(1) + word.charAt(0) +scrambleWord(word.substring(2));}
		return "" + word.charAt(0) + scrambleWord(word.substring(1));

	}
	


	public static void main(String[] args){

		//		System.out.println(reverseNoB("because"));
		//		System.out.println(reverseNoB("bobcat"));
		//		System.out.println(merge("aba","aaaaabbc"));
		//		int[] array = {1,5,6,3,6,8,3,6,5,3};

		System.out.println(scrambleWord("TAN"));
		System.out.println(scrambleWord("ABRACADABRA"));
		System.out.println(scrambleWord("WHOA"));
		System.out.println(scrambleWord("AARDVARK"));
		System.out.println(scrambleWord("EGGS"));
		System.out.println(scrambleWord("A"));
		System.out.println(scrambleWord(""));
		System.out.println("above should be blank");

	}
}