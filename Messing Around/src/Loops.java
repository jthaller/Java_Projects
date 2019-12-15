
public class Loops {

	
	
	public static void middleNum(int n1, int n2, int n3){
		 int nMiddle = 0;
		 
		   if (n2 < n1 && n1 < n3)//This series of if statements will find the middle number
		 
		    nMiddle = n1;
		 
		   if (n1 < n2 && n2 < n3)
		 
		    nMiddle = n2;
		 
		   if (n1 < n3 && n3 < n2)
		 
		    nMiddle = n3;
		 
		   if (n3 < n1 && n1 < n2)
		 
		    nMiddle = n1;
		 
		   if (n3 < n2 && n2 < n1)
		 
		    nMiddle = n2;
		 
		   if (n2 < n3 && n3 < n1)
		 
		    nMiddle = n3;
		   System.out.println(nMiddle);
		}
	public Loops (int a, int b){
		this.a = a;
		this.b = b;
	}
	
	int sum;
	int a;
	int b;
	
	public int getSum(){
		return sum;
	}
public void strangeSum(int a, int b){
	
	for (int sum = a; a < b; a++){
		sum += a;
		System.out.println(sum);
	}
	
	
}


	public static void main (String [] args){
		
		middleNum(1,16,5);
		Loops s = new Loops(2,7);
		s.strangeSum(2, 4);
		
		
		
	}
}
