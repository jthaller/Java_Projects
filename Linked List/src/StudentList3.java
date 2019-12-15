import java.util.NoSuchElementException;


public class StudentList{


	public static StudentListNode head;

	public StudentList() {
		head = null;
	}

	public Student removeFront(){
		if(head == null){
			throw new IndexOutOfBoundsException();
		}else{
			Student temp = head.data;
			head = head.next;  
			return temp;
		}
	}

	public void insertLastName(Student stud){
		if(head == null)
			head = new StudentListNode(stud);

		StudentListNode newHead = new StudentListNode(head.data);
		StudentListNode pointer = head.next;

		// loop through each element in the list
		while (pointer != null) {
			// insert this element to the new list

			StudentListNode innerPointer = newHead;
			StudentListNode next = pointer.next;

			if (pointer.data.getLastName().compareTo(newHead.data.getLastName()) > 0) {
				StudentListNode oldHead = newHead;
				newHead = pointer;
				newHead.next = oldHead;
			} else {
				while (innerPointer.next != null) {

					if (pointer.data.getLastName().compareTo(innerPointer.data.getLastName()) >0 && pointer.data.getLastName().compareTo(innerPointer.data.getLastName()) <= 0){
						StudentListNode oldNext = innerPointer.next;
						innerPointer.next = pointer;
						pointer.next = oldNext;
					}

					innerPointer = innerPointer.next;
				}

				if (innerPointer.next == null && pointer.data.getLastName().compareToIgnoreCase(innerPointer.data.getLastName()) > 0 ) {
					innerPointer.next = pointer;
					pointer.next = null;
				}
			}

			// finally
			pointer = next;
		}
	}










	//		
	//		else{
	//			StudentListNode current = head;
	//			while(current != null && head.data.getLastName().compareTo(stud.getLastName()) > 0){
	//				StudentListNode key = head;
	//				Student j;
	//
	//				while(current.next != null)
	//					current.next = current;
	//				current.next = key;
	//			}

	//			if(head.data.getLastName().compareTo(stud.getLastName()) > 0){
	//				head = head.next;
	//				insertLastName(stud);}
	////			if (head.data.getLastName().compareTo(stud.getLastName()) == 0){//if doesn't work it's not there
	//				StudentListNode newNode = new StudentListNode(stud);
	//			newNode.next = head;
	//			head = newNode;
	//			





	public void insertFront(Student stud){
		if(head == null){
			head = new StudentListNode(stud);
		}else{
			StudentListNode newNode = new StudentListNode(stud);
			newNode.next = head;
			head = newNode;
		}       
	}

	public void insertBack(Student data){
		if(head == null){
			head = new StudentListNode(data);
		}else{
			StudentListNode newNode = new StudentListNode(data);
			StudentListNode current = head;
			while(current.next != null){
				current = current.next;
			}
			current.next = null;
		}       
	}

	public StudentListNode Search(Student stud){
		if (head == null)
			return null;
		boolean found = false;
		StudentListNode current = head;
		while(current != null){
			if(current.data.getLastName() == stud.getLastName() 
					&& current.data.getFirstName() == stud.getFirstName() 
					&& current.data.getGPA() == stud.getGPA()){
				found = true;
				break; //baddd
			}
			current = current.next;
		}
		return current;
	}



	public Student removeBack(){
		if(head == null){
			throw new IndexOutOfBoundsException();
		}else if (head.next == null){
			Student temp = head.data;
			head = null;
			return temp;
		}else{

			StudentListNode current = head;
			while(current.next.next != null){
				current = current.next;
			}
			Student temp = current.next.data;
			current.next = null;
			return temp;
		}       
	}

	public String toString(){
		String retStr = "Contents:\n";

		StudentListNode current = head;
		while(current != null){
			retStr += current.data + "\n";
			current = current.next;

		}

		return retStr;
	}

	public StudentListNode getHead() {
		return head;
	}

	public void setHead(StudentListNode head) {
		this.head = head;
	}

	/*
	 * i need to use something like this at some point
	 */
	public static void insertionSort(Student stud){
		StudentListNode current = head;
		while(current != null){
			StudentListNode key = head;
			Student j;

			while(current.next != null)
				current.next = current;
			current.next = key;
		}



		//		for (int i = 1; i < array.length; i++){
		//			int key = array[i];
		//			int j;
		//
		//			for(j = i - 1; (j >= 0) && (key < array[j]); j--)
		//				array[j+1] = array[j];
		//			array[j+1] = key;    
	}



	private static class StudentListNode
	{
		public Student data;
		public StudentListNode next;


		public StudentListNode(Student stud)
		{
			this.data = stud;
			this.next = null;
		}

		public String toString(){
			return "" + data + next;
		}
	}



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

		StudentList list = new StudentList();
		list.insertLastName(Edgars); 
		list.insertLastName(SmithJ);
		list.insertLastName(Peterson);
		//		list.insertBack(Peterson);
		//		list.insertBack(Lincoln);
		//		list.insertBack(SmithJ);
		//		System.out.println(list.Search(Lincoln));
		System.out.println(list);
		list.removeBack();
		System.out.println(list);
		//		list.removeFront();
		//		System.out.println(list);

	}


}

//private static StudentNode first;
//
//public void add(Student stud){
//		
//}
////Then sort by what ever is just toAdd = head; 
////head = null; 
////for all(to add) insertby whatever
//public static void insertionSort( int [] array){
//	for (int i = 1; i < array.length; i++){
//		int key = array[i];
//		int j;
//
//		for(j = i - 1; (j >= 0) && (key < array[j]); j--)
//			array[j+1] = array[j];
//		array[j+1] = key;    
//	}
//}
//
////	insertByLastName,
////	removeStudent, 
////	sortByAverage, 
////	insertByAverage, 
////	and sortByLastName
//
//public StudentList(){
//	first = null;
//}
//
//public Student getFirst(){
//	if(first == null)
//		throw new NoSuchElementException();
//	return first.data;
//}
//
//public static Student removeFirst(){
//	if(first == null)
//		throw new NoSuchElementException();
//	Student stud = first.data;
//	first = first.next;
//	return stud;
//}
//
//public static void addFirst(Student stud){
//	StudentNode newNode = new StudentNode();
//	newNode.data = stud;
//	newNode.next = first;
//	first = newNode;
//
//}
//
//
//private static class StudentNode{
//	//piece of data and node for each node
//	//note no arrays, or linked lists, or arraylists, etc.
//	public Student data;
//	public StudentNode next;
//
//	//		public StudentNode(){
//	//			
//	//		}
//
//	//		//ctor
//	//		//do i need both params?
//	//		public StudentNode(Student student, StudentNode ref){ 
//	//			this.student = student;
//	//			this.ref = ref;
//	//		}
//	//
//	//		//dteeo  tlcege nd? theuriIbt's  ip
//	//		public String toString(){
//	//			String s = "";
//	//			s+= student + ",";
//	//			s+= ref;
//	//			return s;
//	//		}
//
//	
//		//would implement ListIterator
//		private StudentNode position;
//		private StudentNode previous;
//
//		public LinkedListIterator(){
//			position = null;
//			previous = null;
//		}
//
//		public Student next(){
//			if (!hasNext())
//				throw new NoSuchElementException();
//			previous = position;
//
//			if(position == null)
//				position = first;
//			else 
//				position = position.next;
//
//			return position.data;
//		}
//
//
//		private boolean hasNext() {
//			if(position == null)
//				return first != null;
//			else
//				return position.next != null;
//		}
//
//		public void add(Student stud){
//			if (position == null){
//				addFirst(stud);
//				position = first;
//			}
//			else{
//				StudentNode newNode = new StudentNode();
//				newNode.data = stud;
//				newNode.next = position.next;
//				position.next = newNode;
//				position = newNode;
//			}
//			previous = position;
//		}
//
//		public void remove(){
//			if (previous == position)
//				throw new IllegalStateException();
//			if (position == first)
//				removeFirst();
//			else{
//				previous.next = position.next;
//			}
//			position = previous;
//		}
//
//		public void set(Student stud){
//			if (position == null)
//				throw new NoSuchElementException();
//			position.data = stud;
//		}
//	}
//}









//public void append(int val) {
//    Node tmpNode = head;
//    while (tmpNode.next != null) {
//        tmpNode = tmpNode.next;
//    }
//    tmpNode.next = new Node(val);
//}
//
//public void insert(int val) {
//    Node currentNode = head;
//    Node nextNode = head.next;
//
//    if (currentNode.num > val) {
//        Node tmpNode = head;
//        head = new Node(val);
//        head.next = tmpNode;
//        return;
//    }
//
//    if (nextNode != null && nextNode.num > val) {
//        currentNode.next = new Node(val);
//        currentNode.next.next = nextNode;
//        return;
//    }
//
//    while (nextNode != null && nextNode.num < val) {
//        currentNode = nextNode;
//        nextNode = nextNode.next;
//    }
//
//    currentNode.next = new Node(val);
//    currentNode.next.next = nextNode;
//}
//
//public void delete(int val) {
//    Node prevNode = null;
//    Node currNode = head;
//
//    if (head.num == val) {
//        head = head.next;
//        return;
//    }
//
//    while (currNode != null && currNode.num != val) {
//        prevNode = currNode;
//        currNode = currNode.next;
//    }
//
//    if (currNode == null) {
//        System.out.println("A node with that value does not exist.");
//    }
//    else {
//        prevNode.next = currNode.next;
//    }
//
//}
