/**
 * 
 */
package jvm.ref;

import java.lang.ref.SoftReference;

/**
 * @author yangwm in Mar 26, 2010 1:06:49 PM
 */
public class SoftReferenceExample {

    SoftReference<Object> aRef = null;
    Object a = null;
    
    public void execute(){
        if((aRef == null)||(aRef.get() == null)){
            a = new Object();
            aRef = new SoftReference<Object>(a);
        } else{
            a = aRef.get();
        }
        
        // 执行代码
        
        a = null;
    }

}
