import java.util.NoSuchElementException;

public class StudentList2 {

	private static StudentNode head;

	public StudentList2()
	{
		head = null;
	}

	public boolean isEmpty()
	{
		return head == null;
	}

	/**
	 *  Inserts a new node at the beginning of this list.
	 *
	 */
	public void addFirst(Student item)
	{
		head = new StudentNode(item, head);
	}

	/**
	 *  Returns the first element in the list.
	 *
	 */
	public Student getFirst()
	{
		if(head == null) throw new NoSuchElementException();

		return head.data;
	}

	/**
	 *  Removes the first element in the list.
	 *
	 */
	public Student removeFirst()
	{
		Student tmp = getFirst();
		head = head.next;
		return tmp;
	}

	/**
	 *  Inserts a new node to the end of this list.
	 *
	 */
	public void addLast(Student item)
	{
		if( head == null)
			addFirst(item);
		else
		{
			StudentNode temp = head;
			while(temp.next != null) temp = temp.next;

			temp.next = new StudentNode(item, null);
		}
	}

	/**
	 *  Returns the last element in the list.
	 *
	 */
	public Student getLast()
	{
		if(head == null) throw new NoSuchElementException();

		StudentNode tmp = head;
		while(tmp.next != null) tmp = tmp.next;

		return tmp.data;
	}
	/**
	 *  Removes all nodes from the list.
	 *
	 */
	public void clear()
	{
		head = null;
	}

	/**
	 *  Returns true if this list contains the specified element.
	 *
	 */
	public boolean contains(Student x)
	{
		for(Student tmp : this)
			if(tmp.equals(x)) return true;

		return false;
	}
	/**
	 *  Returns the data at the specified position in the list.
	 *
	 */
	public Student get(int pos)
	{
		if (head == null) throw new IndexOutOfBoundsException();

		StudentNode tmp = head;
		for (int k = 0; k < pos; k++) tmp = tmp.next;

		if( tmp == null) throw new IndexOutOfBoundsException();

		return tmp.data;
	}

	public Object copy()
	{
		StudentList2 twin = new StudentList2();
		StudentNode tmp = head;
		while(tmp != null)
		{
			twin.addFirst( tmp.data );
			tmp = tmp.next;
		}

		return twin.reverse();
	}

	public StudentList reverse()
	{
		StudentList list = new StudentList();
		StudentNode tmp = head;
		while(tmp != null)
		{
			StudentList.addFirst( tmp.data );
			tmp = tmp.next;
		}
		return list;
	}

	
	
	
	public String toString()
	   {
	      StringBuffer result = new StringBuffer();
	      for(Student x : this)
	      	result.append(x + " ");

	      return result.toString();
	   }
	//	insertByLastName,
	//	removeStudent, 
	//	sortByAverage, 
	//	insertByAverage, 
	//	and sortByLastName




	private static class StudentNode{
		//piece of data and node for each node
		//note no arrays, or linked lists, or arraylists, etc.
		public Student data;
		public StudentNode next;

		public StudentNode(Student data, StudentNode next){
			this.data = data;
			this.next = next;
		}

		public void addFirst(Student item){
			head = new StudentNode(item, head);
		}

		//		public StudentNode(){
		//			
		//		}

		//		//ctor
		//		//do i need both params?
		//		public StudentNode(Student student, StudentNode ref){ 
		//			this.student = student;
		//			this.ref = ref;
		//		}
		//
		//		//dteeo  tlcege nd? theuriIbt's  ip
		//		public String toString(){
		//			String s = "";
		//			s+= student + ",";
		//			s+= ref;
		//			return s;
		//		}





		public static void main(String[] args) {
			Student Edgars = new Student("Edgars", "Thomas", 89);
			Student SmithJ = new Student("Smith", "Jennifer", 86);
			Student Umberton = new Student("Umberton", "Harold", 78);
			Student Martin = new Student("Martin", "Frank", 60);
			Student Andrews = new Student("Andrews", "Jeremy", 83);
			Student Roberts = new Student("Roberts", "Laura", 93);
			Student Lincoln = new Student("Lincoln", "Adele", 85);
			Student SmithP = new Student("Smith", "Peter", 91);
			Student Peterson = new Student("Peterson", "Larry", 72);

			StudentList2 list = new StudentList2();
			list.addLast(Edgars); 
			list.addLast(SmithJ);
			list.addLast(Umberton);
			list.addLast(Martin);
			list.addLast(Andrews);
			list.addLast(Roberts);
			list.addLast(Lincoln);
			list.addLast(SmithP);
			list.addLast(Peterson);
			System.out.println(list);
		}


	}

}
