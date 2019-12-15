//Jeremy Thaller
//Period 4
//Class to perform basic math functions with mixed numbers
//returns answers as improper fractions, unless otherwise stated in output

public class MixedNumber {

	private int whole;
	private Fraction frac;

	//to get fraction
	public Fraction getFraction(){
		return frac;
	}
	//to set fraction
	public void setFraction(Fraction frac){
		this.frac = frac;
	}
	//to get the whole number first integer
	public int getWhole(){
		return whole;
	}
	//to sett the hwole number first integer
	public void setWhole(int whole){
		this.whole = whole;
	}
	//constructor to correct for negative integer inputs on numerator and denominator
	// for 3 inputs
	// the sign of a mixed number is determined by the leading int
	public MixedNumber(Fraction a){
		if (Math.abs(a.getNumer()) > Math.abs(a.getDenom())&& a.getDenom() != 0){
			whole = a.getNumer()/a.getNumer();
			int newNumer = a.getNumer()%a.getDenom();
			//denominator does not change when converting to mixed number
			//thus, denominator can still be found a b.getDenom()
			frac = new Fraction(newNumer,a.getDenom());
		}
		if (Math.abs(a.getNumer()) <= Math.abs(a.getDenom())){
			whole = 0;
			this.frac = a;
		}
	}
	//constructor for mixed numbers with 3 inputs
	//corrects for negatives because the sign of a mixed number 
	//is determined by the leading int
	public MixedNumber(int whole, int numer, int denom){
		
		if (whole > 0){
			numer = Math.abs(numer);
			denom = Math.abs(denom);
			numer = (whole * denom) + numer;
			this.whole = numer / denom;
			this.frac = new Fraction(numer%denom, denom);
		}
		else if (whole < 0){
			numer = Math.abs(numer);
			denom = Math.abs(denom);
			numer = whole * denom + -numer;
			this.whole = numer / denom;
			this.frac = new Fraction(numer%denom, denom);
		}
		else if (denom == 0){
			this.whole = 0;
			this.frac = new Fraction(1, 0);}
		else if (whole == 0){
			numer = Math.abs(numer);
			denom = Math.abs(denom);
			this.frac = new Fraction(numer / denom);
		}
	}
	
	//constructor if only one number is used
	public MixedNumber(int a){
		whole = a;
		this.frac = new Fraction(0, 1);
	}
	//constructor for two inputs
	//corrects for negatives so the leading integer displays the negative
	public MixedNumber(int numer, int denom){
		if (whole < 0){
			whole = numer/denom;
			this.frac = new Fraction(-Math.abs(numer)%Math.abs(denom), denom);
		}
		else if (whole > 0){
			whole = numer/denom;
			this.frac = new Fraction(numer%denom, denom);
		}
		else if (whole == 0){
			this.frac = new Fraction(numer, denom);}
	}

	public String toString(){
		String mixed;

		if (frac.getDenom() == 0){
			mixed = "Undefined";
		}
		else{

			if (whole != 0 && frac.getNumer() != 0){
				Fraction printFrac = new Fraction(Math.abs(frac.getNumer()), Math.abs(frac.getDenom()));
				mixed = whole + " " + printFrac;
			}
			else if (whole == 0 && frac.getNumer() != 0 ){
				mixed = "" + frac;
			}
			else{
				mixed = " " + whole;
			}

		}
		return mixed;
	}

	//method to add mixed numbers
	//corrects for negatives because the sign of a mixed number 
	//is determined by the leading int
	public MixedNumber addMixed(MixedNumber a){
		MixedNumber sum;
		if (frac.getDenom() == 0){
			sum = new MixedNumber(0,0);
		}
		else{
			int newNumer = a.frac.getDenom() * (this.whole * this.frac.getDenom() + this.frac.getNumer())
					+ this.frac.getDenom() * (a.whole * a.frac.getDenom() + a.frac.getNumer());
			int newDenom = this.frac.getDenom() * a.frac.getDenom();
			sum = new MixedNumber(newNumer, newDenom);
		}
		return sum;
	}

	//method to subtract mixed numbers
	public MixedNumber subtractMixed(MixedNumber a){
		MixedNumber difference;
		if (frac.getDenom() == 0){
			difference = new MixedNumber(0,0);
		}
		else{
			int newNumer = a.frac.getDenom() * (this.whole * this.frac.getDenom() + this.frac.getNumer())
					- this.frac.getDenom() * (a.whole * a.frac.getDenom() + a.frac.getNumer());
			int newDenom = this.frac.getDenom() * a.frac.getDenom();
			difference = new MixedNumber(newNumer, newDenom);
		}
		return difference;
	}

	//method to multiply mixed numbers
	public MixedNumber multiplyMixed(MixedNumber a){
		MixedNumber product;
		if (frac.getDenom() == 0){
			product = new MixedNumber(0,0);
		}
		else{
			int newNumer = (this.whole * this.frac.getDenom() + this.frac.getNumer())
					* (a.whole * a.frac.getDenom() + a.frac.getNumer());
			int newDenom = this.frac.getDenom() * a.frac.getDenom();
			product = new MixedNumber(newNumer, newDenom);
		}
		return product;
	}

	//method to divide mixed numbers
	public MixedNumber divideMixed(MixedNumber a){
		MixedNumber quotient;
		if (frac.getDenom() == 0){
			quotient = new MixedNumber(0,0);
		}
		else{
			int newNumer = a.frac.getDenom() * (this.whole*this.frac.getDenom() + this.frac.getNumer());
			int newDenom = this.frac.getDenom() * (a.whole*a.frac.getDenom() + a.frac.getNumer());
			quotient = new MixedNumber(newNumer, newDenom);
		}
		return quotient;
	}

	//method to find reciprocal of mixed numbers
	public MixedNumber reciprocalMixed(){
		int newDenom = frac.getDenom() * whole + frac.getNumer();
		int newNumer = frac.getDenom();
		MixedNumber reciprocal = new MixedNumber(newNumer, newDenom);
		return reciprocal;
	}

	//method to turn mixed number into a decimal
	public double toDecimalMixed(){
		double decimal;
		if (frac.getDenom() == 0){
			decimal =  (double)1/frac.getDenom();
		}
		else{
			decimal = whole + (double)frac.getNumer()/frac.getDenom();
		};
		return decimal;
	}
}

