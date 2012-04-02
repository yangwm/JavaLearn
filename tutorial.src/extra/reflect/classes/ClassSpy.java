package extra.reflect.classes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Member;
import static java.lang.System.out;

enum ClassMember { CONSTRUCTOR, FIELD, METHOD, CLASS, ALL }

public class ClassSpy {
    public static void main(String... args) {
        try {
            Class<?> c = Class.forName(args[0]);
            out.format("Class:%n  %s%n%n", c.getCanonicalName());
    
            Package p = c.getPackage();
            out.format("Package:%n  %s%n%n",
                   (p != null ? p.getName() : "-- No Package --"));
    
            for (int i = 1; i < args.length; i++) {
                switch (ClassMember.valueOf(args[i])) {
                case CONSTRUCTOR:
                    printMembers(c.getConstructors(), "Constructor");
                    break;
                case FIELD:
                    printMembers(c.getFields(), "Fields");
                    break;
                case METHOD:
                    printMembers(c.getMethods(), "Methods");
                    break;
                case CLASS:
                    printClasses(c);
                    break;
                case ALL:
                    printMembers(c.getConstructors(), "Constuctors");
                    printMembers(c.getFields(), "Fields");
                    printMembers(c.getMethods(), "Methods");
                    printClasses(c);
                    break;
                default:
                    assert false;
                }
            }
    
            // production code should handle these exceptions more gracefully
        } catch (ClassNotFoundException x) {
            x.printStackTrace();
        }
    }

    private static void printMembers(Member[] mbrs, String s) {
        out.format("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field)
                out.format("  %s%n", ((Field)mbr).toGenericString());
            else if (mbr instanceof Constructor)
                out.format("  %s%n", ((Constructor<?>)mbr).toGenericString());
            else if (mbr instanceof Method)
                out.format("  %s%n", ((Method)mbr).toGenericString());
        }
        if (mbrs.length == 0)
            out.format("  -- No %s --%n", s);
        out.format("%n");
    }
    
    private static void printClasses(Class<?> c) {
        out.format("Classes:%n");
        Class<?>[] clss = c.getClasses();
        for (Class<?> cls : clss)
            out.format("  %s%n", cls.getCanonicalName());
        if (clss.length == 0)
            out.format("  -- No member interfaces, classes, or enums --%n");
        out.format("%n");
    }
}
/* {args:java.nio.channels.ReadableByteChannel METHOD}
Class:
  java.lang.ClassCastException

Package:
  java.lang

Constructor:
  public java.lang.ClassCastException()
  public java.lang.ClassCastException(java.lang.String)
*///~
/* {args:java.lang.ClassCastException CONSTRUCTOR}
Class:
  java.nio.channels.ReadableByteChannel

Package:
  java.nio.channels

Methods:
  public abstract int java.nio.channels.ReadableByteChannel.read(java.nio.ByteBuffer) throws java.io.IOException
  public abstract void java.nio.channels.Channel.close() throws java.io.IOException
  public abstract boolean java.nio.channels.Channel.isOpen()
*///~
/* {args:extra.reflect.classes.ClassMember FIELD METHOD}
Class:
  extra.reflect.classes.ClassMember

Package:
  extra.reflect.classes

Fields:
  public static final extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.CONSTRUCTOR
  public static final extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.FIELD
  public static final extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.METHOD
  public static final extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.CLASS
  public static final extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.ALL

Methods:
  public static extra.reflect.classes.ClassMember extra.reflect.classes.ClassMember.valueOf(java.lang.String)
  public static extra.reflect.classes.ClassMember[] extra.reflect.classes.ClassMember.values()
  public final int java.lang.Enum.hashCode()
  public final int java.lang.Enum.compareTo(E)
  public int java.lang.Enum.compareTo(java.lang.Object)
  public final java.lang.String java.lang.Enum.name()
  public final boolean java.lang.Enum.equals(java.lang.Object)
  public java.lang.String java.lang.Enum.toString()
  public static <T> T java.lang.Enum.valueOf(java.lang.Class<T>,java.lang.String)
  public final java.lang.Class<E> java.lang.Enum.getDeclaringClass()
  public final int java.lang.Enum.ordinal()
  public final native java.lang.Class<?> java.lang.Object.getClass()
  public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
  public final void java.lang.Object.wait() throws java.lang.InterruptedException
  public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
  public final native void java.lang.Object.notify()
  public final native void java.lang.Object.notifyAll()
*///~
