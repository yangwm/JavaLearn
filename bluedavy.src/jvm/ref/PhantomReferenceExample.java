/**
 * 
 */
package jvm.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @author yangwm in Mar 26, 2010 1:14:18 PM
 */
public class PhantomReferenceExample {

    ReferenceQueue<Object> aRefQueue = new ReferenceQueue<Object>();
    PhantomReference<Object> aRef = null;
    Object a = null;
    
    public void execute(){
        a = new Object();
        aRef = new PhantomReference<Object>(a, aRefQueue);
        
        // 执行代码
        
        a = null;
    }

}
