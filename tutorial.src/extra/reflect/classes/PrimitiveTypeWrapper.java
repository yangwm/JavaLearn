package extra.reflect.classes;

public class PrimitiveTypeWrapper {
    
    static class DoubleType {
        public static void main(String[] args) {
            Class<?> c = Double.TYPE;
            System.out.println(c);
            
            c = double.class;
            System.out.println(c);
        }
    }
    
    static class VoidType {
        public static void main(String[] args) {
            Class<?> c = Void.TYPE;
            System.out.println(c);
        }
    }
}
