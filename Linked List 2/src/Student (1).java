/*
 * Student.java
 * Rohan Kadambi
 * Period 6
 * 2014-05-12
 */
public class Student {
	private String firstName;
	private String lastName;
	private int gpa;
	
	public Student(String fName, String LName, int gpa){
		firstName = fName;
		lastName = LName;
		this.gpa = gpa;
	}
	public String getFirstName(){return firstName;}
	public String getLastName(){return lastName;}
	public int getGPA(){return gpa;}
	
	public void setFirstName(String fName)	{
		firstName =fName;
	}
	public void setLastName(String lName){
		lastName = lName;
	}
	public void setGPA(int gpa){
		this.gpa = gpa;
	}
	/*
	 * compareByName
	 * 	returns the value of the compareTo method in the string class of two students when comparing their
	 * 		last names, if they have the same last name, their first names's comparison is returned
	 * 	Params: Student s
	 * 	Returns: none
	 */
	public int compareByName(Student s)
	{
		if(getLastName().compareToIgnoreCase(s.getLastName()) == 0)
		{
			return getFirstName().compareToIgnoreCase(s.getFirstName());
		}
		return getLastName().compareToIgnoreCase(s.getLastName());
	}
	
	/*
	 * equals
	 * 	Returns true if the student sent in the param is the same as the given student
	 * 	Params: Student s
	 * 	Returns: none
	 */
	public boolean equals(Student s)
	{
		return (getFirstName().compareTo(s.getFirstName()) == 0)
				&&(getLastName().compareTo(s.getLastName()) == 0)
				&&(getGPA() == s.getGPA());
	}
	
	public String toString(){
		return firstName + " " + lastName + " average = " + gpa;
	}

}
