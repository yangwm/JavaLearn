package basics.learning.inheritances;

public class Subclass extends Superclass {
    public void printMethod() { //overrides printMethod in Superclass
        super.printMethod();
        System.out.println("Printed in Subclass");
    }
    
    public static void main(String[] args) {
        Subclass s = new Subclass();
        s.printMethod();    
    }

}
