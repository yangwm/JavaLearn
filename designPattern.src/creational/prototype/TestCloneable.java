//: creational.prototype.TestCloneable.java

package creational.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TestCloneable {
	public static void main(String[] args) throws CloneNotSupportedException {
		User u1 = new User("123456", new Father());
		User u2 = (User)u1.clone();
		
		// compare address
		System.out.println(u1 == u2);
		System.out.println(u1.f == u2.f);
		
		// 
		System.out.println("u1.password=" + u1.password);
		System.out.println("u2.password=" + u2.password);
		System.out.println("After modify ");
		u2.password = "654321";
		System.out.println("u1.password=" + u1.password);
		System.out.println("u2.password=" + u2.password);
	}
}
class Father implements Cloneable, Serializable {
}
class User implements Cloneable, Serializable {
	String password;
	Father f;
	public User(String password,Father f) {
		this.password = password;
		this.f = f;
	}
	public Object clone() throws CloneNotSupportedException {
//		return super.clone(); // shawdon copy
		
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		
		Object o = null;
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bo);
			out.writeObject(this);
			out.flush();
			byte[] bs = bo.toByteArray();
			
			ByteArrayInputStream bi = new ByteArrayInputStream(bs);
			in = new ObjectInputStream(bi);
			o = in.readObject();
			return o;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

