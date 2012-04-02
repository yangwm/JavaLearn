/**
 * 
 */
package reflect;

import java.lang.reflect.Method;

/**
 * 
 * 
 * @author yangwm Apr 19, 2010 4:38:42 PM
 */
public class MethodInvokeTest {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) 
            throws Exception {
        Class<?> c;
        c = Class.forName("xml.config.ClassConfig");
        Method m = c.getMethod("setIp", new Class[] {String.class});
        m.invoke(c, new Object[] {"localhost"});
        
        m = c.getMethod("getIp", new Class[] {});
        Object o = m.invoke(c, new Object[] {});
        System.out.println(o);
    }

}
