/**
 * 
 */
package jvm.ref;

/**
 * @author yangwm in Mar 26, 2010 1:48:20 PM
 */
public class StrongReferenceExample {
    
    Object a = null;
    
    public void execute(){
        a = new Object();
        
        // 执行代码
    }
}
