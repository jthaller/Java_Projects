


public class Person {
	
	private String name;
	private double height;
	
	
	public Person(String name, double height){
		this.name = name;
		this.height = height;
	}
	
	public void grow(double growth){
		
		double newHeight = height + growth;
		height = newHeight;
	}	
	
	public void setHeight(double newHeight){
		height = newHeight;
	}
	
	public void setName(String newName){
		name = newName;
	}
	public String getName(){
		return name;
	}

	public double getHeight(){
		return height;
	}
	


	
	public static void main(String[] args){
		Person X = new Person("Fred", 4.8);
		X.grow(1.4);
		X.setName("Barney Rubble");
		System.out.println(X.getName() + " is " + X.getHeight() + "ft tall");
		
	}
}


