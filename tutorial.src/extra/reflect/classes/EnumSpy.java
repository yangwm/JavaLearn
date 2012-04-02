package extra.reflect.classes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Member;
import java.util.List;
import java.util.ArrayList;
import static java.lang.System.out;

public class EnumSpy {
    private static final String fmt = "  %11s:  %s %s%n";

    public static void main(String... args) {
        try {
            Class<?> c = Class.forName(args[0]);
            if (!c.isEnum()) {
                out.format("%s is not an enum type%n", c);
                return;
            }
            out.format("Class:  %s%n", c);
    
            Field[] flds = c.getDeclaredFields();
            List<Field> cst = new ArrayList<Field>();  // enum constants
            List<Field> mbr = new ArrayList<Field>();  // member fields
            for (Field f : flds) {
                if (f.isEnumConstant())
                    cst.add(f);
                else
                    mbr.add(f);
            }
            if (!cst.isEmpty())
                print(cst, "Constant");
            if (!mbr.isEmpty())
                print(mbr, "Field");
    
            Constructor<?>[] ctors = c.getDeclaredConstructors();
            for (Constructor<?> ctor : ctors) {
                out.format(fmt, "Constructor", ctor.toGenericString(),
                synthetic(ctor));
            }
    
            Method[] mths = c.getDeclaredMethods();
            for (Method m : mths) {
                out.format(fmt, "Method", m.toGenericString(),
                synthetic(m));
            }
    
            // production code should handle this exception more gracefully
        } catch (ClassNotFoundException x) {
            x.printStackTrace();
        }
    }

    private static void print(List<Field> lst, String s) {
        for (Field f : lst) {
            out.format(fmt, s, f.toGenericString(), synthetic(f));
        }
    }

    private static String synthetic(Member m) {
        return (m.isSynthetic() ? "[ synthetic ]" : "");
    }
}
/* {args:extra.reflect.classes.ClassMember}
Class:  class extra.reflect.classes.ClassMember
     Constant:  public static final extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.CONSTRUCTOR 
     Constant:  public static final extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.FIELD 
     Constant:  public static final extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.METHOD 
     Constant:  public static final extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.CLASS 
     Constant:  public static final extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.ALL 
        Field:  private static final extra.reflect.classes.ClassMember[] extra.reflect.classes.ClassMember.ENUM$VALUES [ synthetic ]
  Constructor:  private extra.reflect.classes.ClassMember(java.lang.String,int) 
       Method:  public static extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.valueOf(java.lang.String) 
       Method:  public static extra.reflect.classes.ClassMember[] extra.reflect.classes.ClassMember.values() 
*///~
