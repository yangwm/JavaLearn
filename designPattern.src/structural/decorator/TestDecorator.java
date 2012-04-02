//: structural/decorator/TestDecorator.java

package structural.decorator;

public class TestDecorator {
	public static void main(String[] args) {
		Teacher t1 = new SimpleTeacher();
		Teacher t2 = new JavaTeacher(t1);
		Teacher t3 = new CppTeacher2(t2);
		t3.teach();
	}
}
abstract class Teacher {
	public abstract void teach();
}
class SimpleTeacher extends Teacher {
	public void teach() {
		System.out.println("Good Good Study, Day Day Up");
	}
}
class JavaTeacher extends Teacher {
	Teacher teacher;
	public JavaTeacher(Teacher t) {
		this.teacher = t;
	}
	public void teach() {
		teacher.teach();
		System.out.println("Teach Java");
	}
}
class CppTeacher2 extends Teacher {
	Teacher teacher;
	public CppTeacher2(Teacher t) {
		this.teacher = t;
	}
	public void teach() {
		teacher.teach();
		System.out.println("Teach Cpp");
	}
}
class OracleTeacher extends Teacher {
	Teacher teacher;
	public OracleTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public void teach() {
		System.out.println("teach Oracle.");
		teacher.teach();
	}
}

