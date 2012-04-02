package reflect.proxy;
//:TestDynamicProxy

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestDynamicProxy {

	public static void main(String[] args) throws Exception{
		// 
		IB ib = new MyClass();
		InvocationHandler handler = new MyHandler(ib);
		Object o = Proxy.newProxyInstance(ib.getClass().getClassLoader(),
				ib.getClass().getInterfaces(),handler);
		IB a = (IB)o;
		System.out.println("a point 1");
		a.m1();
	}
}
/*
path=/D:/study/tempProject/JavaLearn/classes/
a point 1
point 2
point 3
point 4

*/

class MyHandler implements InvocationHandler{
	private Object obj; // proxyed object
	PrintWriter out;
	public MyHandler(Object obj){
		this.obj=obj;
		try {
			String path = MyHandler.class.getResource("").getPath();
			System.out.println("path=" + path);
			
			out=new PrintWriter(path + "log2.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Object invoke(Object proxy, Method method, Object[] parameter) throws Throwable {
		System.out.println("point 2");
		Object returnValue=method.invoke(obj, parameter);//null;//
		System.out.println("point 4");
		out.println("invoke "+obj.getClass().getSimpleName()+"'s "+method.getName()+" method");
		out.flush();
		return returnValue;
	}
}
interface IB{
	void m1();
	void m2();
	void m3();
}
class MyClass implements IB{
	public void m1() {
		System.out.println("point 3");
	}
	public void m2() {
		System.out.println("m2()");
	}
	public void m3() {
		System.out.println("m3()");
	}
}
