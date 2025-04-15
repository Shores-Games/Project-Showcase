package linkedList;

public class Student {

	// variables
	private double GPA;
	private String name;
	private String address;
	
	
	//---------------- Getters and Setters
	public double getGPA() {
		return GPA;
	}

	public void setGPA(double gPA) {
		GPA = gPA;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	//---------------- Getters and Setters


	// constructor
	public Student(double GPA, String name, String address) {
		super();
		this.GPA = GPA;
		this.name = name;
		this.address = address;
	}

}
