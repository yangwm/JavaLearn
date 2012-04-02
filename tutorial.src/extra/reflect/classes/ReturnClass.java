package extra.reflect.classes;

import java.lang.reflect.Field;

public class ReturnClass {
    static class SuperClass {
        public static void main(String[] args) {
            Class<?> c = javax.swing.JButton.class.getSuperclass();
            System.out.println(c);
        }
    }
    
    static class Classes {
        public static void main(String[] args) {
            Class<?>[] c = Character.class.getClasses();
            for (Class<?> cl : c) {
                System.out.println(cl);
            }
        }
    }
    
    static class DeclaredClasses {
        public static void main(String[] args) {
            Class<?>[] c = Character.class.getDeclaredClasses();
            for (Class<?> cl : c) {
                System.out.println(cl);
            }
        }
    }
    
    static class FieldClass {
        public static void main(String[] args) throws SecurityException, NoSuchFieldException {
            Field f = System.class.getField("out");
            Class<?> c = f.getDeclaringClass();
            System.out.println(c);
        }
    }
    
    static class MyClass {
        static Object o = new Object() { public void m() {} };
        static Class<?> c = o.getClass().getEnclosingClass();
        
        public static void main(String[] args) {
            System.out.println(c);
        }
        
    }

    static class EnclosingClass {
        public static void main(String[] args) {
            Class<?> c = Thread.State.class.getEnclosingClass();
            System.out.println(c);
        }
    }
}
