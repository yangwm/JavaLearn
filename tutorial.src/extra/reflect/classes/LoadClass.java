package extra.reflect.classes;

public class LoadClass {

    static class MyLocaleServiceProviderClass {
        public static void main(String[] args) throws ClassNotFoundException {
            Class<?> c = Class.forName("com.duke.MyLocaleServiceProvider");
            System.out.println(c);
        }
    }
    
    static class DoubleArrayClass {
        public static void main(String[] args) throws ClassNotFoundException {
            Class<?> cDoubleArray = Class.forName("[D");
            System.out.println(cDoubleArray);
        }
    }
    
    static class StringArrayClass {
        public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
            Class<?> cStringArray = Class.forName("[[Ljava.lang.String;");
            System.out.println(cStringArray);
            Object o = cStringArray.newInstance();
            System.out.println(o);
        }
    }
    
}
