//: behavioral/chain/TestChain.java

package behavioral.chain;

public class TestChain {
	public static void main(String[] args) {
		String pass1 = "123456";
		String pass2 = "1234567";
		String personId = "362502198508115555";
		String email = "jxfzywm@163.com.cn";
		
		register(pass1,pass2,personId,email);
	}
	public static void register(String pass1,String pass2,String personId,String email) {
		Filter f1 = new PasswordFilter1();
		Filter f2 = new PasswordFilter2();
		Filter f3 = new PersonFilter();
		Filter f4 = new EmailFilter();
		
		f1.setNext(f2);
		f2.setNext(f3);
		f3.setNext(f4);
		System.out.println(f1.doFilter(pass1,pass2,personId,email));
		
		
		Filter tf = null;
		Filter f = new PasswordFilter1();
		tf = f;
		f = new PasswordFilter2();
		tf.setNext(f);
		
	}
}

abstract class Filter {
	Filter next;
	public Filter getNext() {
		return next;
	}
	public void setNext(Filter next) {
		this.next = next;
	}
	public String doFilter(String pass1,String pass2,String personId,String email) {
		System.out.println("this:" + this);
		System.out.println("next:" + next);
		if (next == null) return "Success! ";
		return next.doFilter(pass1,pass2,personId,email);
	}
}
class PasswordFilter1 extends Filter {
	@Override
	public String doFilter(String pass1, String pass2, String personId, String email) {
		if (!(pass1.equals(pass2)))	return "two password not same! ";
		return super.doFilter(pass1, pass2, personId, email);
	}
}
class PasswordFilter2 extends Filter {
	@Override
	public String doFilter(String pass1, String pass2, String personId, String email) {
		if (pass1.length() != 6 || pass2.length() != 6) return "length not 6! ";
		return super.doFilter(pass1, pass2, personId, email);
	}
}
class PersonFilter extends Filter {
	@Override
	public String doFilter(String pass1, String pass2, String personId, String email) {
		if (personId.length() != 18) return "PersonID not true! ";
		return super.doFilter(pass1, pass2, personId, email);
	}	
}
class EmailFilter extends Filter {
	@Override
	public String doFilter(String pass1, String pass2, String personId, String email) {
		int i1 = email.indexOf("@");
		int i2 = email.indexOf(".");
		if (i1 == -1 || i2 == -1 || i2-i1 <= 1 || i1 == 0 || i2 == email.length())
			return "email not true! ";
		return super.doFilter(pass1, pass2, personId, email);
	}	
}
