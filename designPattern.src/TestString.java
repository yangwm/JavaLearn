//:
// 

public class TestString {
	public static void main(String[] args) {
		String s1 = new String("abc");
		String s2 = new String("abc");
		System.out.println(s1 == s2);
		
		String s3 = "abc";
		String s4 = "abc";
		System.out.println(s3 == s4);
		
		s1 = s1.intern();
		s2 = s2.intern();
		System.out.println(s1 == s2);
		//System.out.println(s1);
		
		String s = "a";
		s += "b" + "c" + "d" + "e";
		System.out.println(s);
		
		StringBuffer sb = new StringBuffer("a");
		sb.append("b");
		sb.append("c");
		sb.append("d");
		sb.append("e");
		String str = sb.toString();
		System.out.println(str);
	}

}
