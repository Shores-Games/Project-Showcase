package adtLinkedList;

import java.util.ArrayList;
import java.util.Iterator;

public class Main {
	public static void main(String[] args) {
		// variables
		CustomLinkedList linkedList = new CustomLinkedList();// linked list
		ArrayList<String> file = linkedList.readFile("test");// capture each line of a text file as a string in an arraylist
		ArrayList<Integer> numbers = linkedList.parseOutNumbers(file); // strip all identifiable numbers into an int arraylist

		// load all stripped number into linked list
		for (int i : numbers) {
			linkedList.insert(i);// create link in linkedlist for each stripped int
		}

		// check for 2
		System.out.print("\nDoes the linked list contain " + 2 + ": " + linkedList.contains(2) + "\n"); // contains call

		// Iterate and display elements
		Iterator<Integer> iterator = linkedList.iterator(); // custom iterator
		while (iterator.hasNext()) {
			System.out.print(iterator.next() + " "); // iterates through arraylist
		}
		// delete an instance of 2
		System.out.println(); // formatting
		linkedList.deletion(2);

		// Iterate and display elements
		Iterator<Integer> iterator2 = linkedList.iterator(); // custom iterator
		while (iterator2.hasNext()) {
			System.out.print(iterator2.next() + " "); // iterates through arraylist
		}

		// check for 2 again
		System.out.print("\nDoes the linked list contain " + 2 + ": " + linkedList.contains(2));

	}
}
