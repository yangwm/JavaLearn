//:

public class TestPloy3 {
	public static void main(String[] args) {
		Super1 s = new Sub();
		System.out.println(s.a);	// shadow, attribute no have ploy
		s.print();
	}
}

class Super1 {
	int a = 2;
	public void print() {
		System.out.println("Super1");
	}
}

class Sub extends Super1 {
	int a = 3;
	public void print() {
		System.out.println("Sub");
	}
}
