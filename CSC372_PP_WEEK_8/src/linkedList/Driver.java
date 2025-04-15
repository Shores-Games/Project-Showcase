package linkedList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Driver {

	// Global Variables
	private static Scanner scan = new Scanner(System.in);
	public static LinkedList<Student> ll = new LinkedList<Student>();

	public static String testDouble(String input) {
		// test if input is an double, abort if not
		try {

			input = String.valueOf(Double.parseDouble(input));
			return input;
		} catch (NumberFormatException e) {
			// abort message and abort
			System.out.println("That was not an appropriate input");
			input = "bad";
			return input;
		}
	}

	// keeps the GPA between a value of 0.0 and 5.0
	public static double checkRange(double input) {

		// keeps double in range
		if (input < 0.0) {
			System.out.println("input adjusted up to 0.0");
			input = 0.0;
			return input;
		} else if (input > 5.0) {
			System.out.println("input adjusted down to 5.0");
			input = 5.0;
			return input;
		} else {
			return input;
		}
	}

	// this method takes inputs, validates them, and creates a student.
	public static Student createStudent() {

		// variables
		double GPA;
		String name;
		String address;
		String userInput;
		int counter = 0;

		// capture the three inputs
		System.out.println("What is the students name?");
		name = scan.nextLine();
		System.out.println("What is the students address?");
		address = scan.nextLine();
		System.out.println("What is the students GPA? (Please input a number with two trailing decimals)"
				+ "\n any value above a 5.0 or below a 0.0 will be adjusted within an acceptable range.");
		userInput = scan.nextLine();

		// test to make sure the GPA is a proper input
		userInput = testDouble(userInput);

		// allows for 10 chances for user to input GPA
		while (userInput.equals("bad") && counter < 10) {
			// prompts user for GPA
			System.out.println("What is the students GPA? (Please input a number with two trailing decimals)"
					+ "\nany value above a 5.0 or below a 0.0 will be adjusted within an acceptable range."
					+ "\n10 bad attempts will set the GPA to an average 2.5.");
			userInput = scan.nextLine();

			// tests to see if double is good, if it is it exits the loop
			userInput = testDouble(userInput);
			// attempt tracker
			counter++;
		}

		// defaults to 2.5 if bad input present
		if (counter < 10) {
			GPA = Double.parseDouble(userInput);
		} else {
			System.out.println("Maximum alloted tries, GPA set to an average 2.5");
			GPA = 2.5;
		}

		// keeps GPA in acceptable ranges
		GPA = checkRange(GPA);

		// creates student and returns it
		Student student = new Student(GPA, name, address);
		return student;
	}

	// calls on the student creator and fills in linked list
	public static void populateList() {
		// Variables
		boolean onSwitch = true;
		String userInput = "n";

		// loops through making and adding students to the linked list
		while (onSwitch) {

			System.out.println("Please add your student to the linked list.");
			Student tempStudent = createStudent();
			ll.add(tempStudent);

			System.out.println("If you would like to put another student into the linked list "
					+ "\nplease enter 'y' -any other input will end the program and poduce a text file containing the "
					+ "\nstudents in alphabetical name order.");
			userInput = scan.nextLine();

			// exit condition
			if (!userInput.equals("y")) {
				onSwitch = false;
			}
		}

	}

	// selection sort that sorts by name
	public static LinkedList<Student> selectionSort(LinkedList<Student> ll) {

		NameComparator nameComp = new NameComparator();
		Student temp;

		// selection sort by name
		for (int i = 0; i < ll.size(); i++) {
			for (int j = i + 1; j < ll.size(); j++) {
				if (nameComp.compare(ll.get(j), ll.get(i)) < 0) {
					temp = ll.get(i);
					ll.set(i, ll.get(j));
					ll.set(j, temp);
				}
			}
		}

		return ll;
	}

	// pulls each list item and creates a readable string
	public static String printList(LinkedList<Student> ll) {

		Student student;
		String list = "";

		for (int i = 0; i < ll.size(); i++) {
			student = ll.get(i);

			list = list + "\n" + "Name: " + student.getName() + " Address: " + student.getAddress() + " GPA: "
					+ student.getGPA();
		}

		return list;
	}

	// creates text file in the same file that hosts the classes
	public static void createTxt() {

		try {
			// variable representing a file
			File sortedStudents = new File("sortedStudents.txt");
			// checks if file already exists
			if (sortedStudents.createNewFile()) {
				System.out.println("File created: " + sortedStudents.getName());
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	// writes to the created file
	public static void writeToTxt() {

		try {
			// variables
			FileWriter myWriter = new FileWriter("sortedStudents.txt");
			String studentPrint = printList(ll);

			// open a writer to write to the file then close
			myWriter.write(studentPrint);
			myWriter.close();

			// success message
			System.out.println("Successfully wrote to the file.");

			// failure message
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	// Main Function
	public static void main(String[] args) {

		// Creates list of students provided by user
		populateList();
		// Sort linked list by name
		ll = selectionSort(ll);
		// Create and write to text
		createTxt();
		writeToTxt();

		// close scanner
		scan.close();

	}

}
