
public class Practice {

	//merge and place in alphabetical order. guarunteed to be in alphabetical order given
	//aghiw, abbrx --> aabbghirwx
	public static String merge(String a, String b){
		
		if (a == null || a.length() ==0) return b;
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


public static void main(String[] args){
	
	System.out.println(reverseNoB("because"));
	System.out.println(reverseNoB("bobcat"));
}
}