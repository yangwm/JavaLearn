
package extra.reflect.members;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import static java.lang.System.out;

enum Spy { BLACK , WHITE }

public class FieldModifierSpy {
  volatile int share;
  int instance;
  class Inner {}

  public static void main(String... args) {
		try {
	    Class<?> c = Class.forName(args[0]);
	    int searchMods = 0x0;
	    for (int i = 1; i < args.length; i++) {
				searchMods |= modifierFromString(args[i]);
	    }

	    Field[] flds = c.getDeclaredFields();
	    out.format("Fields in Class '%s' containing modifiers:  %s%n",
		       c.getName(),
		       Modifier.toString(searchMods));
	    boolean found = false;
	    for (Field f : flds) {
				int foundMods = f.getModifiers();
				// Require all of the requested modifiers to be present
				if ((foundMods & searchMods) == searchMods) {
				    out.format("%-8s [ synthetic=%-5b enum_constant=%-5b ]%n",
					       f.getName(), f.isSynthetic(),
					       f.isEnumConstant());
				    found = true;
				}
	    }
	
	    if (!found) {
				out.format("No matching fields%n");
	    }
	
	        // production code should handle this exception more gracefully
		} catch (ClassNotFoundException x) {
		    x.printStackTrace();
		}
  }

  private static int modifierFromString(String s) {
		int m = 0x0;
		if ("public".equals(s))           m |= Modifier.PUBLIC;
		else if ("protected".equals(s))   m |= Modifier.PROTECTED;
		else if ("private".equals(s))     m |= Modifier.PRIVATE;
		else if ("static".equals(s))      m |= Modifier.STATIC;
		else if ("final".equals(s))       m |= Modifier.FINAL;
		else if ("transient".equals(s))   m |= Modifier.TRANSIENT;
		else if ("volatile".equals(s))    m |= Modifier.VOLATILE;
		return m;
  }
}
/*
args:{java reflect.members.FieldModifierSpy reflect.members.FieldModifierSpy volatile}
output:
Fields in Class 'reflect.members.FieldModifierSpy' containing modifiers:  volatile
share    [ synthetic=false enum_constant=false ]
args:{java reflect.members.FieldModifierSpy reflect.members.FieldModifierSpy$Inner final}
output:
Fields in Class 'reflect.members.FieldModifierSpy$Inner' containing modifiers: final
this$0   [ synthetic=true  enum_constant=false ]
*///:
