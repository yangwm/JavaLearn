package basics.learning.inheritances;

public class Cat extends Animal {
    public static void testClassMethod() {
        System.out.println("The class method in Cat.");
    }
    
    public static void testClassMethod(String arg) {
        System.out.println("The class method in Cat.");
    }
    
    // if protected, compile-time error
    public void testInstanceMethod() {
        System.out.println("The instance method in Cat.");
    }
    
    public void testInstanceMethod(String arg) {
        System.out.println(arg + "The instance method in Cat.");
    }

    public static void main(String[] args) {
        Cat myCat = new Cat();
        Animal myAnimal = myCat;
        
        Animal.testClassMethod(); // <=> myAnimal.testClassMethod();
        myAnimal.testInstanceMethod();
        
        new Animal().testInstanceMethod();
    }

}
