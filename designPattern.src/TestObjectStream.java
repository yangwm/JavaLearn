//: 

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TestObjectStream {
	public static void main(String[] args) {
		Teacher1 t = new Teacher1(20);
		
		try {
			String path = TestObjectStream.class.getResource("").getPath();
			FileOutputStream fos = new FileOutputStream(path + "teacher.dat");
			ObjectOutputStream out = new ObjectOutputStream(fos);
			out.writeObject(t);
			
			t.setAge(30);
			//out.writeObject(t); // not OK, writeOjbect will write this.age == 20
			//out.writeObject(t.clone()); // OK, this.age == 30
			out.writeObject(new Teacher1(t)); // OK, this.age == 30
			out.close();
			
			FileInputStream fis = new FileInputStream(path + "teacher.dat");
			ObjectInputStream in = new ObjectInputStream(fis);
			
			Teacher1 t1 = (Teacher1) in.readObject();
			Teacher1 t2 = (Teacher1) in.readObject();
			in.close();
			
			System.out.println(t1.age);
			System.out.println(t2.age);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}

class Teacher1 implements Serializable, Cloneable {
	int age;

	public Teacher1(int age) {
		super();
		this.age = age;
	}
	
	public Teacher1(Teacher1 teacher1) {
		super();
		this.age = teacher1.age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	protected Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
