
package extra.reflect.members;

import java.lang.reflect.Field;

public class FieldTroubleToo {
  public final boolean b = true;

  public static void main(String... args) {
		FieldTroubleToo ft = new FieldTroubleToo();
		try {
		    Class<?> c = ft.getClass();
		    Field f = c.getDeclaredField("b");
	 	    //f.setAccessible(true);  // solution
		    f.setBoolean(ft, Boolean.FALSE);   // IllegalAccessException
	
	        // production code should handle these exceptions more gracefully
		} catch (NoSuchFieldException x) {
		    x.printStackTrace();
		} catch (IllegalArgumentException x) {
		    x.printStackTrace();
		} catch (IllegalAccessException x) {
		    x.printStackTrace();
		}
  }
}
/* (output):
java.lang.IllegalAccessException: Can not set final boolean field reflect.member
s.FieldTroubleToo.b to (boolean)false
        at sun.reflect.UnsafeFieldAccessorImpl.throwFinalFieldIllegalAccessExcep
tion(UnsafeFieldAccessorImpl.java:55)
        at sun.reflect.UnsafeFieldAccessorImpl.throwFinalFieldIllegalAccessExcep
tion(UnsafeFieldAccessorImpl.java:63)
        at sun.reflect.UnsafeQualifiedBooleanFieldAccessorImpl.setBoolean(Unsafe
QualifiedBooleanFieldAccessorImpl.java:78)
        at java.lang.reflect.Field.setBoolean(Field.java:686)
        at reflect.members.FieldTroubleToo.main(FieldTroubleToo.java:15)
*///:
