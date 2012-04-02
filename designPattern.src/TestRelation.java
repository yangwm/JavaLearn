public class TestRelation{
    public static void main(String[] args){
        Computer c1=new Computer();
        Person1 p1=new Person1(c1);
        p1.method();
        
        Person2 p2=new Person2();
        p2.method(new Computer());
    }
}
class Person1{
    Computer c;
    public Person1(Computer c){
        this.c=c;
    }
    public void method(){
    	System.out.print("person1 put ");
        c.powerOn();
    }
}
class Person2{
    public void method(Computer c){
    	System.out.print("person2 put ");
        c.powerOn();
    }
}
class Computer{
    public void powerOn(){
    	System.out.println("computer power on... ");
    }
}
