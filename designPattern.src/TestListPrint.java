//: 

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TestListPrint {
	public static void main(String[] args) {
		//Collection objects = new ArrayList();
		//List objects = new ArrayList();
		Set objects = new HashSet();
		print(objects);
	}
	
	public static void print(Collection collection) {
		System.out.println("Iterator : ");
		Iterator it = collection.iterator();
		while (it.hasNext()) {
			Object o = it.next();
			System.out.println(o);
		}
	}
	
	public static void print(List list) {
		System.out.println("For : ");
		for (int i = 0; i < list.size(); i++) {
			Object o = list.get(i);
			System.out.println(o);
		}
	}
}

