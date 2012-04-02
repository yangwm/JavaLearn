//: structural/proxy/TestDelegate.java

package structural.proxy;

public class TestDelegate{
    public static void main(String[] args){
        A a=new A();
        a.methodA();
    }
}

class B{
    public void methodB(){
        System.out.println("methodB");
    }
}
class A{
    B b=new B();
    public void methodA(){
        b.methodB();
    }
}
