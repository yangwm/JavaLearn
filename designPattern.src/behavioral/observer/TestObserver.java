//: behavioral/observer/TestObserver.java

package behavioral.observer;

import java.util.*;

public class TestObserver {
	public static void main(String[] args) {
		Subject s=new Subject();
		Observer o1=new Observer1();
		Observer o2=new Observer2();
		s.addObserver(o1);
		s.addObserver(o2);
		s.fire();
	}
}
class Subject{
	Collection<Observer> os=new ArrayList<Observer>();
	public void addObserver(Observer o){
		os.add(o);
	}
	public void fire(){
		for(Observer o:os){
			o.method();
		}
	}
	
}
interface Observer{
	void method();
}
class Observer1 implements Observer{
	public void method(){
		System.out.println("Observer1 ");
	}
}
class Observer2 implements Observer{
	public void method(){
		System.out.println("Observer2 ");
	}
}
