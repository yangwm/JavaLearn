//: creational.singleton.TestSingleton.java

package creational.singleton;

public class TestSingleton {
  public static void main(String[] args) {
	  EagerHusband ha1 = EagerHusband.newInstance();
	  EagerHusband ha2 = EagerHusband.newInstance();
	  
	  LazyHusband hb1 = LazyHusband.newInstance();
	  LazyHusband hb2 = LazyHusband.newInstance();
  }
}

class EagerHusband { // Eager
	public static EagerHusband instance = new EagerHusband();
	public static EagerHusband newInstance() {
		System.out.println("EagerHusband newInstance()");
		return instance;
	}
	private EagerHusband() {
		System.out.println("EagerHusband()");
	}
}

// think multi thread, could be create multi Object, so need synchronized
class LazyHusband { // Lazy
	private static LazyHusband instance = null;
	public static synchronized LazyHusband newInstance() { // need ...
		System.out.println("LazyHusband newInstance()");
		if (instance == null) {
			instance = new LazyHusband();
		}
		return instance;
	}
	private LazyHusband() {
		System.out.println("LazyHusband()");
	}
}
