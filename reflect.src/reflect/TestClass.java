package reflect;
//:TestClass

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestClass {
    
    static class First {
        public static void main(String[] args) throws Exception{
            Class<?> c=Student.class;
            System.out.println(c);
            
            Object s=new Student();
            Class<?> c2=s.getClass();
            System.out.println(c2);
        }
    }
    /*
    class reflect.Student
    StudentR()
    class reflect.Student
    */
    
    static class Second {
        public static void main(String[] args) throws Exception{
            String className="javax.swing.JFrame";
            Class<?> c=Class.forName(className);
            System.out.println("canonicalName = " + c.getCanonicalName());
            System.out.println("name = " + c.getName());
            System.out.println("simpleName = " + c.getSimpleName());
        }
    }
    /*
    canonicalName = javax.swing.JFrame
    name = javax.swing.JFrame
    simpleName = JFrame
    */
    
    static class Third {
        public static void main(String[] args) throws Exception{
            Class<?> c=Student.class;
            Method[] ms1=c.getDeclaredMethods();
            System.out.println(">>>>>>>>>>>methods...");
            for(Method m:ms1){
                System.out.println(m.getName());
            }
            System.out.println(">>>>>>>>>>>fields...");
            Field[] fs=c.getDeclaredFields();
            for(Field f:fs){
                System.out.println(f.getName());
            }
            
            System.out.println(">>>>>>>>>>>...");
            Student s=new Student();
            s.setName("Zhang3");
            Field f=s.getClass().getDeclaredField("name");
            System.out.println(f.get(s));
            System.out.println(f.getType().getCanonicalName());
        }
    }
    /*
    >>>>>>>>>>>methods...
    getAge
    setAge
    eat
    toString
    getName
    setName
    study
    >>>>>>>>>>>fields...
    name
    age
    Student()
    Zhang3
    java.lang.String

    */
    
    static class Forth {
        public static void main(String[] args) throws Exception{
            Class<?> c=Student.class;
            Object o1=c.newInstance();
            System.out.println(o1.getClass());
            
            Class<?>[] cs={String.class, int.class};
            Constructor<?> con=c.getConstructor(cs);
            Object[] os={"Zhang3",new Integer(30)};
            Object o2=con.newInstance(os);
        
            Method m=c.getDeclaredMethod("eat");
            m.setAccessible(true);
            m.invoke(o2);
            
            Method m2=c.getDeclaredMethod("study", String.class);
            m2.setAccessible(true);
            m2.invoke(o2,"English");
            
            Field f=c.getDeclaredField("age");
            f.setAccessible(true);
            System.out.println(f.get(o2));
        }
    }
    /*
    Student()
    class Student
    Student(name,age)
    Student Zhang3  eat
    Student study English
    30
    
	*/
}
