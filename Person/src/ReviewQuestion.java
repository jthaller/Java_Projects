
public class ReviewQuestion {
private int a;
private int b;

public ReviewQuestion( int x, int y){
	a = y;
	b = x;
}
public int getA(){
	return a;}

public int getB(){
	return b;
}
public int doSomething(){
	return (a - 4) - (b/2);
}

public static void main(String[] args){
	ReviewQuestion test = new ReviewQuestion(7,10);
	System.out.println(test.getA() + test.getB());
	System.out.println("Do something:" + test.doSomething());
	System.out.println("The data values: " + test.getA() + test.getB());
	String g = "fish";
	System.out.println(g.toUpperCase());
}
}
