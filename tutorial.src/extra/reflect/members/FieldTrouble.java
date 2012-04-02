
package extra.reflect.members;

import java.lang.reflect.Field;

public class FieldTrouble {
  public Integer val;

  public static void main(String... args) {
		FieldTrouble ft = new FieldTrouble();
		try {
	    Class<?> c = ft.getClass();
	    Field f = c.getDeclaredField("val");
	    int i = 42;
	    //
	    f.setInt(ft, i);               // IllegalArgumentException
	    // int.class.isAssignableFrom(int.class);
	    
	    // f.set(ft, new Integer(43));				// OK 

      // production code should handle these exceptions more gracefully
		} catch (NoSuchFieldException x) {
		    x.printStackTrace();
	 	} catch (IllegalAccessException x) {
	 	    x.printStackTrace();
		}
  }
}
/* (output):
Exception in thread "main" java.lang.IllegalArgumentException: Can not set java.
lang.Integer field reflect.members.FieldTrouble.val to (int)42
        at sun.reflect.UnsafeFieldAccessorImpl.throwSetIllegalArgumentException(
UnsafeFieldAccessorImpl.java:146)
        at sun.reflect.UnsafeFieldAccessorImpl.throwSetIllegalArgumentException(
UnsafeFieldAccessorImpl.java:170)
        at sun.reflect.UnsafeObjectFieldAccessorImpl.setInt(UnsafeObjectFieldAcc
essorImpl.java:96)
        at java.lang.reflect.Field.setInt(Field.java:802)
        at reflect.members.FieldTrouble.main(FieldTrouble.java:14)
*///:
