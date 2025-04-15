package linkedList;

public class Node {
	
	// variables
	private Student student;
	private Node previous;
	private Node next;

	// constructor
	public Node(Student student, Node previous, Node next) {
		super();
		this.student = student;
		this.previous = previous;
		this.next = next;
	}

}
