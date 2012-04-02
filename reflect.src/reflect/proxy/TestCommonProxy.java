/**
 * 
 */
package reflect.proxy;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author yangwm in Nov 20, 2009 11:36:32 PM
 */
public class TestCommonProxy {

    public static void main(String[] args) {
        IA ia = new ClassA();
        ClassB b = new ClassB(ia);
        b.ma();
    }

}
/*
path=/D:/study/tempProject/JavaLearn/classes/
ma

*/

interface IA{
    void ma();
    void mb();
}
class ClassA implements IA{
    public void ma(){
        System.out.println("ma");
    }
    public void mb(){
        System.out.println("mb");
    }
}
class ClassB implements IA{
    IA obj; 
    PrintWriter out;
    public ClassB(IA obj){
        this.obj=obj;
        try {
            String path = MyHandler.class.getResource("").getPath();
            System.out.println("path=" + path);
            
            out=new PrintWriter(path + "log1.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void ma(){
        obj.ma();
        out.println("invoke " + "ClassB's ma  method");
        out.flush();
    }
    public void mb(){
        obj.mb();
        out.println("invoke " + "ClassB's mb method");
        out.flush();
    }
}
