package adtLinkedList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CustomLinkedList {
	private Node head;

	// add function
	public void insert(int data) {
		// checks if list is started and starts list
		if (head == null) {
			head = new Node(data);
			head.data = data;
			// adds node if it has already been started
		} else {
			Node oldfirst = head;
			head = new Node(data);
			head.next = oldfirst;
		}

	}

	// verify function
	public boolean contains(int data) {
		// variables
		boolean affirm = false;
		LinkedListIterator linkIt = new LinkedListIterator();

		// steps through iterator and pings if data is present
		while (linkIt.hasNext()) {
			if (linkIt.current.data == data) {
				affirm = true;
				return affirm;
			}
			linkIt.next();
		}

		return affirm;
	}

	// removal function
	public void deletion(int data) {
		// checks if the item is present
		if (this.contains(data)) {
			// iterator
			LinkedListIterator linkIt = new LinkedListIterator();

			// checks to see if the first item is
			if (head.data == data) {
				head = head.next;
				System.out.println("removed");
				return;
			}

			// loops through iterator
			while (linkIt.hasNext()) {

				// checks all items after first
				if (linkIt.current.next.data == data) {
					linkIt.current.next = linkIt.current.next.next;
					System.out.println("removed");
					return;
				}
				linkIt.next();
			}
			// fail case
		} else {
			System.out.println("That data isn't present in the list?");
		}

	}

	public ArrayList<String> readFile(String fileName) {

		ArrayList<String> file = new ArrayList<String>();

		try {
			// variables
			FileReader fileRead = new FileReader(fileName + ".txt");
			BufferedReader buffRead = new BufferedReader(fileRead);

			// first line
			String line = buffRead.readLine();

			while (line != null) {
				file.add(line);
				// read next line
				line = buffRead.readLine();
			}

			buffRead.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;
	}

	public ArrayList<Integer> parseOutNumbers(ArrayList<String> file) {

		// result
		ArrayList<Integer> numbers = new ArrayList<Integer>();

		for (String line : file) {

			// variables
			Stack<Integer> stack = new Stack<Integer>();
			String concat = "";
			char[] characters = new char[line.length() + 1];

			// load expression into a char array
			for (int i = 0; i < line.length(); i++) {
				characters[i] = line.charAt(i);
			}
			characters[characters.length - 1] = ' ';

			// parse through char array
			for (int i = 0; i < characters.length; i++) {
				// load stack with digits
				if (Character.isDigit(characters[i])) {
					stack.push(Integer.parseInt(String.valueOf(characters[i])));

					// create multi-digit numbers
				} else if (!Character.isDigit(characters[i]) && !stack.isEmpty()) {

					// loop while stack has digits
					while (!stack.isEmpty()) {
						// concatenates multi-digit numbers
						concat = Integer.toString(stack.pop()) + concat;
					}

					// checks to see if concatenation is a legitimate number
					try {
						numbers.add(Integer.valueOf(concat));
					} catch (Exception e) {
						System.out.println("Error Occured in reading file, parsable numbers added to sort");
						return numbers;
					}

					// resets concatenation variable
					concat = "";
				}
			}
		}
		return numbers;
	}

	public Iterator<Integer> iterator() {
		return new LinkedListIterator();
	}

	private class Node {
		int data;
		Node next;

		Node(int data) {
			this.data = data;
			this.next = null;
		}
	}

	private class LinkedListIterator implements Iterator<Integer> {
		private Node current = head;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			int data = current.data;
			current = current.next;
			return data;
		}
	}

}
