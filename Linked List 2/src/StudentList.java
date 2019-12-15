/*Jeremy Thaller  P7
 * 	
 * StudentList Class
 * 	Creates a linked-list of students
 * methods:Ctors, insertByLastName, insertByAverage, sortByLastname, sortByAverage,
 * 		removeStudent, toString
 * 
 * includes a private static inner class for StudentNode
 */
public class StudentList {

	private StudentNode head;


	/*
	 * private static inner class to Create StudentNode
	 */
	private static class StudentNode{
		public Student value;
		public StudentNode next; 
		
		public StudentNode(Student s){
			value = s;
			next = null;
		}

		public StudentNode(Student s, StudentNode next){
			value = s;
			this.next = next;
		}
	}

	/*
	 * Ctor: StudentList()
	 * 	Params: None 
	 */
	public StudentList()
	{
		head = null;
	}

	/*
	 * Ctor: 
	 * 	Params: StudentNode n
	 *  returns: none
	 */
	public StudentList(StudentNode n)
	{
		head = n;
	}

	/*
	 * Ctor
	 * 	Params: Student s
	 *  returns: none
	 */
	public StudentList(Student s)
	{
		head = new StudentNode(s);
	}

	//getter
	//params: none
	//returns: StudentNode head
	public StudentNode getHead(){
		return head;}


	/*
	 * insertByLastName
	 * 	Adds the student given into list by lastname
	 * 	Assumes list is already sorted by lastname for proper placement
	 *  Calls insertByLastName(StudentNode s)
	 * 	Params: Student s
	 * 	Returns: none
	 */
	public void insertByLastName(Student s)
	{
		insertByLastName(new StudentNode(s));
	}

	/*
	 * insertByLastName 
	 * 	Adds the student given into list by last name
	 * 	Assumes list is already sorted by last name for proper placement
	 * 	Params: StudentNode s
	 * 	Returns: none
	 */
	public void insertByLastName(StudentNode s)
	{
		if(head == null)
		{
			head =s;
			return;
		}
		if(s.value.compareByName(head.value) < 0) 
		{
			s.next =head;
			head = s;
			return;
		}
		for(StudentNode temp = head; temp.next != null; temp = temp.next)
		{
			if(s.value.compareByName(temp.next.value)<0)
			{
				StudentNode x = temp.next;
				temp.next =s;
				s.next = x;
				return;
			}
			if(temp.next.next == null) 
			{
				temp.next.next = s;
				s.next = null;
				return;
			}
		}
		head.next = s;
		s.next = null;
	}



	/*
	 * insertByAverage
	 * 	Adds the student given into list by average
	 * 	Assumes list is already sorted by average for proper placement
	 *  Calls insertByAverage(StudentNode s)
	 * 	Params: Student s
	 * 	Returns: none
	 * 	
	 */
	public void insertByAverage(Student s)
	{
		insertByAverage(new StudentNode(s));
	}

	/*
	 * insertByAverage
	 * 	Adds the student given into list by average
	 * 	Assumes the list is already sorted by average for proper placement
	 * 	Params: StudenNodet s
	 * 	Returns: none
	 */
	public void insertByAverage (StudentNode s)
	{
		if(head == null)
		{
			head =s;
			return;
		}
		if(s.value.getGPA() < (head.value.getGPA())) 
		{
			s.next = head;
			head = s;
			return;
		}
		for(StudentNode temp = head; temp.next != null; temp = temp.next)
		{
			if(s.value.getGPA() < temp.next.value.getGPA())
			{
				StudentNode x = temp.next;
				temp.next = s;
				s.next = x;
				return;
			}
			if(temp.next.next == null) 
			{
				temp.next.next = s;
				s.next = null;
				return;
			}
		}
		head.next = s;
		s.next = null;
	}

	/*
	 * removeStudent
	 * 	Removes student sent in parameter from list if it exists
	 * 	Params: Student s
	 * 	Returns: none
	 */
	public void removeStudent(Student s)
	{
		for(StudentNode temp = head; temp.next != null; temp = temp.next)
		{
			if(temp.next.value.equals(s))
			{
				temp.next =temp.next.next;
				return;
			}
		}
	}

	/*
	 * sortByAverage
	 * 	Sorts the list by average
	 * 		creates a new list, and adds all other nodes back in by average
	 * 			(calls insertByAverage)
	 * 	Params: none
	 * 	Returns: none
	 */
	public void sortByAverage()
	{
		StudentNode first = head;
		head = head.next;
		StudentList sorted = new StudentList(first);
		while(head != null)
		{
			StudentNode temp = head;
			head = head.next;
			sorted.insertByAverage(temp);
		}
		head = sorted.getHead();
	}

	/*
	 * sortByLastName
	 * 	Sorts the list by last name
	 * 		creates a new list, and adds all other nodes back in by last name
	 * 			(calls insertByLastName)
	 * 	Params: none
	 * 	Returns: none
	 */
	public void sortByLastName()
	{
		StudentNode first = head;
		head = head.next;
		StudentList sorted = new StudentList(first);
		while(head != null)
		{
			StudentNode temp = head;
			head = head.next;
			sorted.insertByLastName(temp);
		}
		head = sorted.getHead();
	}

	/*
	 * Returns a string representation of the list
	 * 	Params: none
	 * 	Returns: String
	 */
	public String toString()
	{
		String s = "";
		for(StudentNode stud = head; stud != null; stud = stud.next)
		{
			Student a = stud.value;
			s += a.toString() + "\n";
		}
		return s;
	}


	public static void main(String[] args)
	{
		Student a = new Student("Thomas", "Edgars", 89);
		Student b = new Student("Jennifer", "Smith", 86);
		Student c = new Student ("Harold", "Umberton", 78);
		Student d = new Student ("Frank", "Martin", 60);
		Student e = new Student("Jeremy", "Andrews", 83);
		Student f = new Student("Laura", "Roberts", 93);
		Student g = new Student ("Adele", "Lincoln", 85);
		Student h = new Student("Peter", "Smith", 91);
		Student i = new Student("Larry", "Peterson", 72);

		StudentList studList = new StudentList();
		studList.insertByLastName(a);
		studList.insertByLastName(b);
		studList.insertByLastName(c);
		studList.insertByLastName(d);
		studList.insertByLastName(e);
		studList.insertByLastName(f);
		studList.insertByLastName(g); 
		studList.insertByLastName(h);
		studList.insertByLastName(i);
		System.out.println("Students by Last Name: \r" +studList + "\r");

		studList.removeStudent(d);
		System.out.println("Remove Frank Martin: \r" + studList + "\r");


		studList.sortByAverage();
		System.out.println("Sort List by Average: \r" +studList + "\r");


		studList.insertByAverage(new Student("Alice", "Henderson",90));
		System.out.println("Add Alice Henderson: \r" + studList + "\r");


		studList.sortByLastName();
		System.out.println("Sort List by Last Name \r" + studList);

	}
}
