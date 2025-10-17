package co.edu.unbosque.util;

public class Student {
	
	private String name;
	private int semester;
	public Student() {
		// TODO Auto-generated constructor stub
	}
	public Student(String name, int semester) {
		super();
		this.name = name;
		this.semester = semester;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	
	@Override
	public String toString() {
		return "Student [name=" + name + ", semester=" + semester + "]\n";
	}
	
	
}
