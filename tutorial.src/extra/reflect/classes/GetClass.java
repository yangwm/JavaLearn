package extra.reflect.classes;

import java.util.HashSet;
import java.util.Set;

public class GetClass {

    static class StringClass {
        public static void main(String[] args) {
            Class<?> c = "foo".getClass();
            System.out.println(c);
            
        }
    }
    
    static class ConsoleClass {
        public static void main(String[] args) {
            Class<?> c = System.console().getClass();
            System.out.println(c);
        }
    }
    
    static class EnumClass {
        enum E {
            A, B 
        }
        
        public static void main(String[] args) {
            Class<?> c = E.A.getClass();
            System.out.println(c);
        }
    }
    
    static class BytesClass {
        public static void main(String[] args) {
            byte[] bytes = new byte[1024];
            Class<?> c = bytes.getClass();
            System.out.println(c);
        }
    }
    
    static class SetClass {
        public static void main(String[] args) {
            Set<String> s = new HashSet<String>();
            Class<?> c = s.getClass();
            System.out.println(c);
        }
    }
    
    static class BooleanClass {
        public static void main(String[] args) {
            Class<?> c = null;
            
            boolean b = false;
            //c = b.getClass();   // compile-time error
            System.out.println(c);
            
            c = boolean.class;  // correct
            System.out.println(c);
            
            Boolean b2 = new Boolean(b);
            c = b2.getClass();
            System.out.println(c);
        }
    }

    static class PrintStreamClass {
        public static void main(String[] args) {
            Class<?> c = java.io.PrintStream.class;
            System.out.println(c);
        }
    }
    
    static class IntArrayArrayArrayClass {
        public static void main(String[] args) {
            Class<?> c = int[][][].class;
            System.out.println(c);
        }
    }
    
    static class StringArrayClass {
        public static void main(String[] args) {
            Class<?> c = String[].class;
            System.out.println(c);
        }
    }
}
