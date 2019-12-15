//Runge-Kutta 4th order was buggy in mathematica so I did it in Java, too
public class RK4 {
	private static double y = 1; //set as one here for y0
	private static double x =0; //set as 0 here for x0
	private double k1;
	private double k2;
	private double k3;
	private double k4;
	
	//returns the slope. Change the return based on given ODE 
	private double dydx(double x,double y){
		return 1 - y*y;
	}

	
	//h is step size
	public void runge_kutta(double xmin,double xmax,double h){
		int n=0;
		while(n < xmax/h){
//			System.out.println("{" + x + ","+y+"}");
			k1 = dydx(x, y); 
			k2 = dydx(x + h/2, y + k1*h/2);
			k3 = dydx(x + h/2, y + k2*h/2);
			k4 = dydx(x + h, y + h*k3);
			y  = y + (h/6)*(k1 + 2*(k2 + k3) + k4);
			x = x + h;
			n++;
		}
//		System.out.println("(" + x + ","+ y +")");
	}

	public static void main(String[] args){
		RK4 e = new RK4();
		e.runge_kutta(0,50,.1);
		System.out.println(y);
//		System.out.println("y("+x+") = "+y);
//		System.out.println("e^-4 = " + Math.exp(-4));
	}
/*                 OUTPUT
 * 	------------------------------------------
 * y(4.000000000004016) = 0.01831279216995025
   e^-4 = 0.01831563888873418
 */
}