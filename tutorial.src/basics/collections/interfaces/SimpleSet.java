package basics.collections.interfaces;

// {args: }
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SimpleSet {

	public static void main(String[] args) {
		Set<Type> s1 = new HashSet<Type>();
		s1.add(new Type("yangwm"));
		s1.add(new Type("yangwm2"));
		s1.add(new Type("xiasi"));

		Set<Type> s2 = new HashSet<Type>();
		s2.add(new Type("xiasi"));
		s2.add(new Type("xiasi2"));

		Set<Type> symmetricDiff = new HashSet<Type>(s1);
		symmetricDiff.addAll(s2);
		System.out.println("symmetricDiff=" + symmetricDiff);
		Set<Type> tmp = new HashSet<Type>(s1);
		tmp.retainAll(s2);
		System.out.println("tmp=" + tmp);
		symmetricDiff.removeAll(tmp);
		System.out.println("symmetricDiff=" + symmetricDiff);
		
		SortedSet<Type> ss = new TreeSet<Type>();
		ss.add(new Type("yangwm"));
		ss.add(new Type("yangmq"));
        System.out.println("ss=" + ss);
	}

}

class Type implements Comparable<Type> {
	String name;
	public Type(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Type)) {
			return false;	
		}
		Type type = (Type)obj;
		return this.name.equals(type.name);		
	}
	@Override
	public int hashCode() {
		return 31 * name.hashCode();
	}
	@Override
	public String toString() {
		return name;
	}
	
	//@Override // Comparable<Type>
	public int compareTo(Type n) {
		return name.compareTo(n.name);
	}
	/**/
}

