//: 

public class TestPloy2 {
	public static void main(String[] args) {
		int type = Integer.parseInt(args[0]);
		Super s = getObject(type);
		method(s);
	}
	
	public static Super getObject(int i) {
		if (i == 0) {
			return new Sub1();
		} else {
			return new Sub2();
		}
	}
	public static void method(Super s) {
		System.out.println("Super");
	}
	public static void method(Sub1 s) {
		System.out.println("Sub1");
	}
	public static void method(Sub2 s) {
		System.out.println("Sub2");
	}
}

class Super {	
}
class Sub1 extends Super {	
}
class Sub2 extends Super {	
}
