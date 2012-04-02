//: structural.adapter.TestAdapter2.java

package structural.adapter;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;

public class TestAdapter2 {
	public static void main(String[] args) {
		Properties ps = new Properties();
		ps.setProperty("P1","aaa");
		ps.setProperty("P2","bbb");
		ps.setProperty("P3","ccc");
		Enumeration<?> e = ps.propertyNames();
		Adapter a = new Adapter(e);
		print(a);
	}
	
	public static void print(Iterator<Object> it) {
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}

class Adapter implements Iterator<Object> {
	Enumeration<?> e;
	public Adapter(Enumeration<?> e) {
		this.e = e;
	}
	public boolean hasNext() {
		return this.e.hasMoreElements();
	}
	public Object next() {
		return this.e.nextElement();
	}
	public void remove() {
	}
}
