package reflect;
public class Student {
	public String name;
	private int age;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString(){
		return "Student "+name+"  "+age;
	}
	public void study(String course){
		System.out.println("Student study "+course);
	}
	private void eat(){
		System.out.println("Student "+name+"  eat");
	}
	
	public Student(){
		System.out.println("Student()");
	}
	public Student(String name,int age){
		this.name=name;
		this.age=age;
		System.out.println("Student(name,age)");
	}
}
