//: 
// 
public class TestProtected {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Student1 s = new Student1();
		System.out.println(s);
		System.out.println(s.clone());
	}
}

class Student1 {
	protected Object clone() {
		try {
			return super.clone();
		} catch(CloneNotSupportedException e) {
			return new Integer(1);
		}
		
	}
}
