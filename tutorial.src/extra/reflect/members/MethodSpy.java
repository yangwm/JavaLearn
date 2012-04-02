
package extra.reflect.members;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import static java.lang.System.out;

public class MethodSpy {
  private static final String  fmt = "%24s: %s%n";

  // for the morbidly curious
  <E extends RuntimeException> void genericThrow() throws E {}

  public static void main(String... args) {
		try {
	    Class<?> c = Class.forName(args[0]);
	    Method[] allMethods = c.getDeclaredMethods();
	    for (Method m : allMethods) {
				if (!m.getName().equals(args[1])) {
				    continue;
				}
				out.format("%s%n", m.toGenericString());
		
				out.format(fmt, "ReturnType", m.getReturnType());
				out.format(fmt, "GenericReturnType", m.getGenericReturnType());
		
				Class<?>[] pType  = m.getParameterTypes();
				Type[] gpType = m.getGenericParameterTypes();
				for (int i = 0; i < pType.length; i++) {
				    out.format(fmt,"ParameterType", pType[i]);
				    out.format(fmt,"GenericParameterType", gpType[i]);
				}
		
				Class<?>[] xType  = m.getExceptionTypes();
				Type[] gxType = m.getGenericExceptionTypes();
				for (int i = 0; i < xType.length; i++) {
				    out.format(fmt,"ExceptionType", xType[i]);
				    out.format(fmt,"GenericExceptionType", gxType[i]);
				}
	    }

      // production code should handle these exceptions more gracefully
		} catch (ClassNotFoundException x) {
		    x.printStackTrace();
		}
  }
}
/*
args:{java reflect.members.MethodSpy java.lang.Class getConstructor}
output:
public java.lang.reflect.Constructor<T> java.lang.Class.getConstructor(java.lang
.Class<?>[]) throws java.lang.NoSuchMethodException,java.lang.SecurityException
              ReturnType: class java.lang.reflect.Constructor
       GenericReturnType: java.lang.reflect.Constructor<T>
           ParameterType: class [Ljava.lang.Class;
    GenericParameterType: java.lang.Class<?>[]
           ExceptionType: class java.lang.NoSuchMethodException
    GenericExceptionType: class java.lang.NoSuchMethodException
           ExceptionType: class java.lang.SecurityException
    GenericExceptionType: class java.lang.SecurityException
*///:
