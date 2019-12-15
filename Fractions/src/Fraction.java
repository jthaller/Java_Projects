/* Jeremy Thaller
 * Period 4
 * Program to perform basic math with fractions (and mixed numbers)
 */

public class Fraction {

	private int numer;
	private int denom;
	//Code used from: http://stackoverflow.com/questions/4009198/java-get-greatest-common-divisor
	private int GCD(int a, int b) {
		a = Math.abs(a);
		if (b==0) return a;
		return GCD(b,a%b);
	}


	//int a is numerator.  int b is denominator. to reduce fraction,
	//the numerator is divided by the greatest common denominator
	//of the numerator and denominator.  The same is done for the denominator.
	//if clauses with absolute values are used to give proper notation when negatives are used
	public Fraction(int a, int b){
		this.numer = a / GCD(a, b);
		this.denom = b / GCD(a, b);
		if (b == 0){
			this.numer = Math.abs(a);
			this.denom = 0;
		}
		else{
			int newNumer = a / GCD(a, b);
			int newDenom = b / GCD(a, b);

			if (newNumer < 0 && newDenom < 0){
				this.numer = Math.abs(newNumer);
				this.denom = Math.abs(newDenom);
			}
			else if (a > 0 && b < 0){
				this.numer = -newNumer;
				this.denom = -newDenom;
			}
			else {
				this.numer = newNumer;}
		}

	}
	//class data. numer is numerator. denom is denominator
	public Fraction(int a){
		this.numer = a;
		this.denom = 1;
	}

	public String toString(){
		String str;
		if (denom == 1){
			str = ""+numer;

		}
		if (numer == 0 && denom <0){
			str = "0";
		}
		else{ 
			str = numer + "/" + denom;
		}
		if (denom == 0){
			str = "undefined";
		}
		return str;
	}
	//sets denominator. Not really used in Fraction class. included to set good habits
	public void setNumer(int newNumer){
		int numer = newNumer;
	}
	//sets denominator. Not really used in Fraction class. included to set good habits
	public void setDenom(int newDenom){
		int denom = newDenom;
	}

	public int getNumer(){
		return numer;
	}

	public int getDenom(){
		return denom;
	}

	// Adding Fractions together. First need common denominator
	//done by multiplying numer by denom
	public Fraction add(Fraction a){
		int newNumer = ((a.numer * denom) + (numer*a.denom));
		int newDenom = denom *a.denom;
		Fraction x = new Fraction (newNumer, newDenom);
		return x;

	}
	// Method to subtract fractions 
	public Fraction subtract(Fraction a){
		//		int newNumer = ((a.numer * denom) - (numer*a.denom));
		//		int newDenom = denom *a.denom;
		int newNumer = ((this.numer*a.denom) - (this.denom*a.numer));
		int newDenom = this.denom *a.denom;
		Fraction x = new Fraction (newNumer, newDenom);
		return x;
	}
	// Method to multiply fractions
	public Fraction multiply(Fraction a){
		int newNumer = (a.numer * numer);
		int newDenom = a.denom*denom;
		Fraction X = new Fraction (newNumer, newDenom);
		return X;
	}
	// Method to divide fractions
	public Fraction divide(Fraction a){
		int newNumer = (numer *a.denom);
		int newDenom = (a.numer *denom);
		Fraction X = new Fraction (newNumer, newDenom);
		return X;
	}
	// method to turn fraction a to decimal
	// turns into double to give decimal value
	public double toDecimal(){
		double decimal = (double)numer/denom;
		return decimal;
	}
	// method to return reciprocal of fraction a.
	// reciprocal is now denominator over numerator 
	public Fraction reciprocal(){

		int newNumer = denom;
		int newDenom = numer;
		Fraction X = new Fraction (newNumer, newDenom);
		return X;
	}
	// Testing Method 
	public static void main(String[] args)
	{
		Fraction a = new Fraction (1,4); // insert fraction (numerator, denominator) or (integer) here.
		Fraction b = new Fraction (0,-1); // insert fraction (numerator, denominator) or (integer) here.
	
		// next two lines print equation and answer for addition
		System.out.print(a + " + " + b + " = ");
		System.out.println(a.add(b));
		//next two lines print equation and answer for subtraction 
		System.out.print(a + " - " + b + " = ");
		System.out.println(a.subtract(b));
		//next two lines print equation and answer for multiplication
		System.out.print(a + " * " + b + " = ");
		System.out.println(a.multiply(b));
		//next two lines print equation and answer for division
		System.out.print(a + " / " + b + " = ");
		System.out.println(a.divide(b));
		// next two lines print fraction a as the decimal equivalent
		System.out.print(a + " as a decimal is ");
		System.out.println(a.toDecimal());
		// next two lines print fraction b as a decimal
		System.out.print(b + " as a decimal is ");
		System.out.println(b.toDecimal());
		// next two lines print the reciprocal of the fraction  a
		System.out.print("The reciprocal of " + a + " is ");
		System.out.println(a.reciprocal());
		//next two lines print the reciprocal of the fraction b
		System.out.print("The reciprocal of " + b + " is ");
		System.out.println(b.reciprocal());
		// Next section is the tester for the Mixed numbers class
		// the mixed numbers simplify with the output

		MixedNumber c = new MixedNumber(1,2,4); //insert mixed number (whole, numerator, denominator) or (numerator, denominator) or (integer)
		MixedNumber d = new MixedNumber(4,3,2); //insert mixed number (whole, numerator, denominator) or (numerator, denominator) or (integer)

		// next two lines print mixed numbers addition for a + b
		System.out.print(c + " + " + d + " = ");
		System.out.println(c.addMixed(d));
		//next two lines print mixed number subtraction for a - b
		System.out.print(c + " - " + d + " = ");
		System.out.println(c.subtractMixed(d));
		// next two lines print mixed number multiplication for a*b
		System.out.print(c + " * " + d + " = ");
		System.out.println(c.multiplyMixed(d));
		//next two lines print mixed number division for a/b
		System.out.print(c + " " + " / "  + d + " = ");
		System.out.println(c.divideMixed(d));
		//next two lines turn the mixed number c into a decimal
		System.out.print(c + " as a decimal" + " = ");
		System.out.println(c.toDecimalMixed());
		//next two lines print mixed number c as its reciprocal
		System.out.print("the reciprocal of " + c + " = ");
		System.out.println(c.reciprocalMixed());
		//next two lines print mixed number d as its reciprocal
		System.out.print("The reciprocal of " + d + " = ");
		System.out.println(d.reciprocalMixed());






	}			
}




