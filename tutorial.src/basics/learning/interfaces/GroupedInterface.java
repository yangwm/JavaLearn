package basics.learning.interfaces;

public interface GroupedInterface extends Interface1,
Interface2, Interface3 {

    // constant declarations
    double E = 2.718282;  // base of natural logarithms
    
    // method signatures
    void doSomething (int i, double x);
    int doSomethingElse(String s);

}

interface Interface1 {
    
}

interface Interface2 {
    
}

interface Interface3 {
    
}
