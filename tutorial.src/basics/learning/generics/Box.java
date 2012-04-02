package basics.learning.generics;

//public class Box {
//
//    private Object object;
//
//    public void add(Object object) {
//        this.object = object;
//    }
//
//    public Object get() {
//        return object;
//    }
//}

///**
// * Generic version of the Box class. 
// */
//public class Box<T> {
//
//    private T t; // T stands for "Type"          
//
//    public void add(T t) {
//        this.t = t;
//    }
//
//    public T get() {
//        return t;
//    }
//}

///**
// * This version introduces a generic method.
// */
//public class Box<T> {
//
//    private T t;          
//
//    public void add(T t) {
//        this.t = t;
//    }
//
//    public T get() {
//        return t;
//    }
//
//    public <U> void inspect(U u){
//        System.out.println("T: " + t.getClass().getName());
//        System.out.println("U: " + u.getClass().getName());
//    }
//
//    public static void main(String[] args) {
//        Box<Integer> integerBox = new Box<Integer>();
//        integerBox.add(new Integer(10));
//        integerBox.inspect("some text");
//    }
//}

/**
 * This version introduces a bounded type parameter.
 */
public class Box<T> {

    private T t;          

    public void add(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public <U extends Number> void inspect(U u){
        System.out.println("T: " + t.getClass().getName());
        System.out.println("U: " + u.getClass().getName());
    }

    public static void main(String[] args) {
        Box<Integer> integerBox = new Box<Integer>();
        integerBox.add(new Integer(10));
        integerBox.inspect("some text"); // error: this is still String! 
    }
}
