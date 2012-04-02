/**
 * 
 */
package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 基本知识点思考， 以下5个输出，哪些会打印true
 * 
 * @author yangwm Jul 28, 2010 8:48:18 PM
 */
public class BasicKnowledge {

    static class A {
        public A(String b) {
            this.b = b;
        }
        String b;
    }
    public static void funAStr(A a) {
        a.b = "abcd";
    }
    public static void funA(A a) {
        A t = new A("abcdefg");
        a = t;
    }
    
    public static void main(String[] args) {
        Integer int1 = 123;
        Integer int2 = Integer.valueOf("123");
        System.out.println(int1 == int2);
        
        String str1 = "123";
        String str2 = String.valueOf("123");
        System.out.println(str1 == str2);
        
        A a1 = new A("abc");
        A a2 = a1;
        A a3 = a1;
        funAStr(a2);
        System.out.println(a2.b.equals(a3.b));
        
        A a4 = new A("abc");
        A a5 = new A("abc");
        funA(a5);
        System.out.println(a4.b.equals(a5.b));
        
        List<String> list = new ArrayList<String>();
        List<String> list1 = Collections.synchronizedList(list);
        List<String> list2 = Collections.synchronizedList(list);
        list1.add("yangwm");
        System.out.println(list1.size() == list2.size());
    }
    
}

/*
true
true
true
true
true

小解：
1.基本类型的包装类的值比较
2.串池 // String.valueOf("123") or str2 = "123" ??? 
3.a2与a3指向的是同一对象 
4.改变传引用的地址 
5.看jdk源码，Collections.synchronizedList内部实现，只是进行mutex，SynchronizedList的list直接指向传入list  

*/

