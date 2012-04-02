/**
 * 
 */
package jvm.ref;

import java.lang.ref.WeakReference;

/**
 * @author yangwm in Mar 26, 2010 1:07:22 PM
 */
public class WeakReferenceExample {
    
    WeakReference<Object> aRef = null;
    Object a = null;
    
    public void execute() {
        if (aRef == null || aRef.get() == null) {
            a = new Object();
            aRef = new WeakReference<Object>(a);
        }
        
        //执行代码
        
        a = null;
    }
    
}
