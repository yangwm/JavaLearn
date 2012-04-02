//: behavioral/immutable/TestImmuable.java

package behavioral.immutable;

import java.util.HashMap;
import java.util.Map;

public class TestImmuable {
	public static void main(String[] args) {
		A.getA(1);
		A.getA(1);
	}
}

class A {
	private final int M;	// immuable
	private static Map<Integer, A> map = new HashMap<Integer, A>();
	
	public A(int m) {
		this.M = m;
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("A{M=");
        sb.append(M);
        sb.append("}");
        return sb.toString();
    }
	
	public static A getA(int value) {
//		if (map.containsKey(new Integer(value))) {
//			return (A)map.get(new Integer(value));
//		} else {
//			A a = new A(value);
//			map.put(new Integer(value), a);
//			System.out.println("put >>> " + a);
//			return a;
//		}
		
		A a = map.get(new Integer(value));
		if (null == a) {
			a = new A(value);
			map.put(new Integer(value), a);
			System.out.println("put >>> " + a);
		}
		return a;
	}
}
