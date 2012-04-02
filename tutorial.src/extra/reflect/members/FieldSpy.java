
package extra.reflect.members;

import java.lang.reflect.Field;
import java.util.List;

public class FieldSpy<T> {
  public boolean[][] b = {{ false, false }, { true, true } };
  public String name  = "Alice";
  public List<Integer> list;
  public T val;
  private int count  = 2;
  
  public static void main(String... args) {
		try {
		    Class<?> c = Class.forName(args[0]);
		    Field f = c.getField(args[1]);
		    System.out.format("Type: %s%n", f.getType());
		    System.out.format("GenericType: %s%n", f.getGenericType());
	
	        // production code should handle these exceptions more gracefully
		} catch (ClassNotFoundException x) {
		    x.printStackTrace();
		} catch (NoSuchFieldException x) {
		    x.printStackTrace();
		}
  }
}
/*
args:{java reflect.members.FieldSpy reflect.members.FieldSpy count}
output:
java.lang.NoSuchFieldException: count
        at java.lang.Class.getField(Class.java:1520)
        at reflect.members.FieldSpy.main(FieldSpy.java:17)
*///:
